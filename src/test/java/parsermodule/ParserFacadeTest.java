package parsermodule;

import org.junit.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ParserFacadeTest {
    @Test
    public void testReadWriteConsistency() throws IOException {
        File file = null;

        try {
            String sampleContent = "Hello World!\u02e5"; // ˥

            file = new File(System.getProperty("user.home"), "content.txt");

            ParserFacade parser = ParserFacade.getInstance(file);

            parser.saveContent(sampleContent);

            String retrievedContent = parser.getContent();
            assertEquals(sampleContent, retrievedContent);

            String retrievedContentWithoutUnicode = parser.getContentWithoutUnicode();
            assertEquals("Hello World!", retrievedContentWithoutUnicode);
        } finally {
            assert file != null;
            file.delete();
        }
    }
}