package core.process;

import core.data.content.processed.ProcessedContent;
import core.data.content.processed.DCTProcessedContent;

public interface Processor {

    void processData();

    void processDCT();

    ProcessedContent getDataContent();

    DCTProcessedContent getDctContent();
}
