package storage.saver;

import com.linuxense.javadbf.DBFWriter;
import config.Props;
import core.data.content.processed.DBFProcessedContent;
import core.data.content.processed.ProcessedContent;
import exceptions.ConversionInterruptedException;
import storage.walker.InputWalker;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DBFSaver implements Saver {

    private DBFProcessedContent processedContent;

    public DBFSaver(ProcessedContent processedContent) {
        this.processedContent = (DBFProcessedContent) processedContent;
    }

    @Override
    public void saveToFile() {
        String fileOutput = Props.getOutputPath() + "/" + InputWalker.getCurrentFileName() + ".dbf";
        DBFWriter writer = new DBFWriter();
        writer.setCharactersetName(Props.getCharacterset());
        try (OutputStream output = new FileOutputStream(fileOutput)) {
            writer.setFields(processedContent.getDbfFields());
            writer.addRecord(processedContent.getValues());
            writer.write(output);
        } catch (IOException e) {
            throw new ConversionInterruptedException("Ошибка при сохранении файла в " + fileOutput);
        }
    }
}


