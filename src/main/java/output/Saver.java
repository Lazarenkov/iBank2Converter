package output;

import core.data.content.ProcessedContent;

public interface Saver {

    public void saveToFile(ProcessedContent processedContent);;
}
