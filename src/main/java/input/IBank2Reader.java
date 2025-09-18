package input;

import config.Props;
import core.data.content.ImportedContent;

import java.io.*;
import java.nio.charset.Charset;

public class IBank2Reader {

    private final ImportedContent content = new ImportedContent();

    public void read() {
        try (FileInputStream stream = new FileInputStream(Props.getInputPath() + "/import.txt");
             InputStreamReader reader = new InputStreamReader(stream, Charset.forName("windows-1251"));
             BufferedReader bufferedReader = new BufferedReader(reader);
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                parse(line);
            }
        } catch (IOException e) {
        }
    }

    private void parse(String line) {
        if(line.isBlank() || !line.matches("^\\s*[^\\s=][^=]*=[^=]*[^\\s=]\\s*$")){
            return;
        }

        int split = line.indexOf("=");
        String name = line.substring(0, split);
        String value = line.substring(split + 1);
        addToContent(name, value);
    }

    private void addToContent(String name, String value){
        if(name.equals("Content-Type")){
            content.setContentType(value);
        } else {
            content.add(name.trim(), value.trim());
        }
    }

    public ImportedContent getContent() {
        return content;
    }
}
