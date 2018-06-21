package casumo.codetest;

import java.io.*;
import java.util.function.Predicate;

/**
 * This class is thread safe.
 */
class ParserFacadeImpl implements ParserFacade {

    private final File file;

    ParserFacadeImpl(final File file) {
        this.file = file;
    }

    private String genericGetContent(Predicate<Integer> predicate) throws IOException {
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = i.read()) > 0) if (predicate.test(data)) {
                output.append((char) data);
            }
            return output.toString();
        }
    }

    public String getContent() throws IOException {
        return genericGetContent(data -> true);
    }
    public String getContentWithoutUnicode() throws IOException {
        return genericGetContent(data -> data < 0x80);
    }
    public void saveContent(String content) throws IOException {
        try (BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        }
    }
}