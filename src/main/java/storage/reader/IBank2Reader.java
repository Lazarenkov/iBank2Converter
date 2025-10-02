package storage.reader;

import config.Props;
import core.data.content.imported.ImportContent;
import exceptions.ConversionInterruptedException;
import storage.walker.InputWalker;

import java.io.*;
import java.nio.charset.Charset;

public class IBank2Reader {

    private final ImportContent content = new ImportContent();

    public void read() {
        try (FileInputStream stream = new FileInputStream(InputWalker.getNextPath().toString());
             InputStreamReader reader = new InputStreamReader(stream, Charset.forName("windows-1251"));
             BufferedReader bufferedReader = new BufferedReader(reader);
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                parse(line.trim());
            }
        } catch (IOException e) {
            throw new ConversionInterruptedException("Ошибка при чтении файла " + InputWalker.getCurrentPath());
        }
    }

    private void parse(String line) {
        if (line.isBlank()) {
            return;
        } else if (!line.matches("^[^=][^=]*=[^=]*$")) {
            throw new ConversionInterruptedException("Некорректная строка: " + line);
        }

        int split = line.indexOf("=");
        String name = line.substring(0, split).trim();
        String value = line.substring(split + 1).trim();
        if (!Props.isParseEmpty() & value.isBlank()) {
            return;
        }
        addToContent(name, value);
    }

    private void addToContent(String name, String value) {
        if (name.equals("Content-Type")) {
            content.setContentType(value);
        } else {
            content.add(name, value);
        }
    }

    public ImportContent getContent() {
        return content;
    }
}
