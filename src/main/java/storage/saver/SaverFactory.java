package storage.saver;

import core.data.content.processed.ProcessedContent;
import core.data.types.Format;

public class SaverFactory {

    public Saver createSaver(Format format, ProcessedContent content){
        return switch(format){
            case DBF -> new DBFSaver(content);
            case CSV -> new CSVSaver(content);
        };
    }
}
