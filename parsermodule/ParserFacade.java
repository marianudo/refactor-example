package parsermodule;

import java.io.File;
import java.io.IOException;

public interface ParserFacade {
    default ParserFacade getInstance(File file) {
        return new ParserFacadeImpl(file);
    }

    String getContent() throws IOException;

    String getContentWithoutUnicode() throws IOException;

    void saveContent(String content) throws IOException;
}