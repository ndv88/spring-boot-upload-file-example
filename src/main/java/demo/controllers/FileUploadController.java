package demo.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import demo.configurations.FileUploadConfiguration;
import demo.utils.helpers.FileUploadHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by i on 18.02.15.
 */

@Controller
public class FileUploadController {

    private final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private FileUploadConfiguration fileUploaderConfiguration;


    private static class FileModel {
        @JsonProperty("file_name")
        public String fileName;
        public FileModel(String fileName) {
            this.fileName = fileName;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public FileModel handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {

        String name = file.getOriginalFilename();
        logger.info("try load image " + name);

        String newPhotoName = FileUploadHelper.getUniqueName(name);
        logger.info("generate new unique file name " + newPhotoName);

        if (file.isEmpty()) {
            logger.error("file " + name + " is empty");
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        if (!FileUploadHelper.isImageContentType(file.getContentType())) {
            logger.error("No supported content type " + file.getContentType());
            MediaType mediaType = MediaType.parseMediaType(file.getContentType());
            throw new HttpMediaTypeNotSupportedException(mediaType, FileUploadHelper.getImageMediaTypes());
        }

        String path = fileUploaderConfiguration.getPathToUploadFolder() + newPhotoName;
        logger.info("path to upload file - " + path);
        try {
            byte[] bytes = file.getBytes();

            BufferedOutputStream stream =
                    new BufferedOutputStream(
                            new FileOutputStream(new File(path)
                            )
                    );

            stream.write(bytes);
            stream.close();

            logger.info("file successfully save by path - " + path);

            return new FileModel(newPhotoName);
        } catch (Exception e) {
            logger.debug("error save file by path " + path, e);
            throw new Exception("No file was uploaded");
        }
    }
}
