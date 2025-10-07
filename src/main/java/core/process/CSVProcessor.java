package core.process;

import core.data.content.imported.ImportContent;
import core.data.content.processed.CSVProcessedContent;
import core.data.content.processed.DCTProcessedContent;
import core.data.content.processed.ProcessedContent;

public class CSVProcessor implements Processor {

    private final ImportContent importContent;
    private final CSVProcessedContent dataContent = new CSVProcessedContent();
    private final DCTProcessedContent dctContent = new DCTProcessedContent();

    public CSVProcessor(ImportContent content) {
        this.importContent = content;
    }

    @Override
    public void processData() {
        collectValues();
    }

    @Override
    public void processDCT() {
        addDCTHeaders();
        addDCTBody();
    }

    private void collectValues() {
        for (int i = 0; i < importContent.size(); i++) {
            dataContent.add(importContent.getValue(i));
        }
    }

    private void addDCTHeaders() {
        dctContent.getLines().add("Content-Type=" + importContent.getContentType());
        dctContent.getLines().add("Data-Type=dct");
        dctContent.getLines().add("Import-Format=csv");
        dctContent.getLines().add("First-String-Read=true");
        dctContent.getLines().add("");
    }

    private void addDCTBody() {
        for (int i = 0; i < importContent.size(); i++) {
            String name = importContent.getName(i);
            dctContent.getLines().add(name.toUpperCase() + "=${" + i + "}");
        }
    }

    @Override
    public ProcessedContent getDataContent() {
        return dataContent;
    }

    @Override
    public DCTProcessedContent getDctContent() {
        return dctContent;
    }
}
