package storage.saver;

import config.Props;
import core.data.content.processed.DCTProcessedContent;
import core.data.content.processed.ProcessedContent;
import exceptions.ConversionInterruptedException;
import storage.walker.InputWalker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DCTSaver implements Saver {

    DCTProcessedContent processedContent;

    public DCTSaver(ProcessedContent processedContent) {
        this.processedContent = (DCTProcessedContent) processedContent;
    }

    @Override
    public void saveToFile() {
        String fileOutput = Props.getDCTOutputPath() + "/" + InputWalker.getCurrentFileName() + ".dct";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileOutput))) {
            for (String line : processedContent.getLines()) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new ConversionInterruptedException("Ошибка при сохранении файла в " + fileOutput);
        }
    }
}
