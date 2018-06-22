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

    private synchronized String genericGetContent(Predicate<Integer> includeReadData) throws IOException {
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = input.read()) > 0) if (includeReadData.test(data)) {
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
    public synchronized void saveContent(String content) throws IOException {
        try (BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                output.write(content.charAt(i));
            }
        }
    }
}