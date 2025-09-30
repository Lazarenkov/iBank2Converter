package core.data.content.processed;

import java.util.ArrayList;

public class DCTProcessedContent extends ProcessedContent {

    private ArrayList<String> lines = new ArrayList<>();

    public void add(String line) {
        lines.add(line);
    }

    public ArrayList<String> getLines() {
        return lines;
    }
}
