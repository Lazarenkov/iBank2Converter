package core.data.content.processed;

import java.util.ArrayList;

public class CSVProcessedContent extends ProcessedContent {

    private ArrayList<String> values = new ArrayList<>();

    public void add(String value) {
        values.add(value);
    }

    public ArrayList<String> getValues() {
        return values;
    }
}
