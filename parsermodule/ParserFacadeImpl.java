package parsermodule;

import java.io.*;
import java.util.function.Predicate;

/**
 * This class is thread safe.
 */
class ParserFacadeImpl implements ParserFacade {

    private static final String ENCODING = "UTF-8";

    private final File file;

    ParserFacadeImpl(final File file) {
        this.file = file;
    }

    private synchronized String genericGetContent(Predicate<Integer> includeReadData) throws IOException {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), ENCODING))) {
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
        try (BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), ENCODING))) {
            for (int i = 0; i < content.length(); i += 1) {
                output.write(content.charAt(i));
            }
        }
    }
}