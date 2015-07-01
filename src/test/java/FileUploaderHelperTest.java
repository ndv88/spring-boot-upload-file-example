import demo.utils.helpers.FileUploadHelper;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by i on 08.05.15.
 */


public class FileUploaderHelperTest {

    @Test
    public void getFileExtensionTest() {
        String extension = FileUploadHelper.getFileExtension("qwerty.png");
        assertEquals("png", extension);
    }

    @Test
    public void getUniqueNameTest() {
        String newName = FileUploadHelper.getUniqueName("qwerty123.png");
        assertFalse(newName.isEmpty());

        String extension = FileUploadHelper.getFileExtension("qwerty.png");
        assertEquals("png", extension);
    }

    @Test
    public void isImageContentTypeTest() {
        String contentType1 = "image/png", contentType2 = "image/", contentType3 = "image/zzz";
        assertTrue(FileUploadHelper.isImageContentType(contentType1));
        assertFalse(FileUploadHelper.isImageContentType(contentType2));
        assertFalse(FileUploadHelper.isImageContentType(contentType3));
    }
}
