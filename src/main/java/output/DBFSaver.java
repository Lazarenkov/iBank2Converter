package output;

import com.linuxense.javadbf.DBFWriter;
import config.Props;
import core.data.content.DBFProcessedContent;
import core.data.content.ProcessedContent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DBFSaver implements Saver {

    @Override
    public void saveToFile(ProcessedContent processedContent) {
        DBFWriter writer = new DBFWriter();
        writer.setCharactersetName(Props.getCharacterset());
        try (OutputStream output = new FileOutputStream(Props.getOutputPath() + "/export.dbf")) {
            writer.setFields(((DBFProcessedContent) processedContent).getDbfFields());
            writer.addRecord(((DBFProcessedContent) processedContent).getObjects());
            writer.write(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


