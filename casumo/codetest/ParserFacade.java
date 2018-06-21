package casumo.codetest;

import java.io.File;

public interface ParserFacade {
    default ParserFacade getInstance(File file) {
        return new ParserFacadeImpl(file);
    }
}