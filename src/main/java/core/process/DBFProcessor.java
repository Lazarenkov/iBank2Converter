package core.process;

import com.linuxense.javadbf.DBFField;
import core.data.content.ImportedContent;
import core.data.content.DBFProcessedContent;
import core.data.content.ProcessedContent;

import java.util.ArrayList;

public class DBFProcessor implements Processor {

    private final DBFField[] dbfFields;
    private final Object[] values;

    private ArrayList<String> dbfNames = new ArrayList<>();
    private ArrayList<Integer> lengths = new ArrayList<>();

    private final ImportedContent content;

    public DBFProcessor(ImportedContent content) {
        this.content = content;
        dbfFields = new DBFField[content.size()];
        values = new Object[content.size()];
    }

    @Override
    public ProcessedContent process() {
        reduceNamesLength();
        checkDuplicatedDBFNames();
        calculateLengths();
        addFields();
        addValues();
        return new DBFProcessedContent(dbfFields, values);
    }

    private void reduceNamesLength() {
        for (int i = 0; i < content.size(); i++) {
            String name = content.getName(i);
            if (name.length() >= 11) {
                dbfNames.add(name.substring(0, 10));
            } else {
                dbfNames.add(name);
            }
        }
    }

    private void checkDuplicatedDBFNames() {
        for (int i = 0; i < dbfNames.size(); i++) {
            String name = dbfNames.get(i);
            for (int u = 0; u < dbfNames.size(); u++) {
                if (i != u && name.equals(dbfNames.get(u))) {
                    dbfNames.set(u, addIndexToName(dbfNames.get(u)));
                }
            }
        }
    }

    private String addIndexToName(String name) {
        int index;
        try {
            index = Integer.parseInt(name.substring(9)) + 1;
        } catch (NumberFormatException e) {
            index = 1;
        }
        return name.substring(0, 9) + index;
    }

    private void calculateLengths() {
        for (int i = 0; i < content.size(); i++) {
            lengths.add(content.getValue(i).length());
        }
    }

    public void addFields() {
        for (int i = 0; i < content.size(); i++) {
            DBFField dbffield = new DBFField();
            dbffield.setName(dbfNames.get(i));
            dbffield.setDataType((byte) 67);
            dbffield.setFieldLength(lengths.get(i));
            dbfFields[i] = dbffield;
        }
    }

    private void addValues() {
        for (int i = 0; i < content.size(); i++) {
            values[i] = content.getValue(i);
        }
    }
}
