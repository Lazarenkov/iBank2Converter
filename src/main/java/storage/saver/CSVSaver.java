package storage.saver;

import config.Props;
import core.data.content.processed.CSVProcessedContent;
import core.data.content.processed.ProcessedContent;
import exceptions.ConversionInterruptedException;
import storage.walker.InputWalker;

import java.io.*;

public class CSVSaver implements Saver {

    CSVProcessedContent processedContent;

    public CSVSaver(ProcessedContent processedContent) {
        this.processedContent = (CSVProcessedContent) processedContent;
    }

    @Override
    public void saveToFile() {
        String fileOutput = Props.getOutputPath() + "/" + InputWalker.getCurrentFileName() + ".csv";
        String divider = Props.getCsvDivider();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutput), "windows-1251"))) {
            for (String value : processedContent.getValues()) {
                bw.write(value + divider);
            }
        } catch (IOException e) {
            throw new ConversionInterruptedException("Ошибка при сохранении файла в " + fileOutput);
        }
    }
}
