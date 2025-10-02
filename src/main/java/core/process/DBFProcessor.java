package core.process;

import com.linuxense.javadbf.DBFField;
import config.Props;
import core.data.content.imported.ImportContent;
import core.data.content.processed.DBFProcessedContent;
import core.data.content.processed.DCTProcessedContent;

import java.util.ArrayList;

public class DBFProcessor implements Processor {

    private final ImportContent importContent;

    private final DBFProcessedContent dataContent;
    private final DCTProcessedContent dctContent;

    private final ArrayList<String> dbfNames = new ArrayList<>();
    private final ArrayList<Integer> lengths = new ArrayList<>();

    public DBFProcessor(ImportContent importContent) {
        this.importContent = importContent;
        dataContent = new DBFProcessedContent(new DBFField[importContent.size()], new Object[importContent.size()]);
        dctContent = new DCTProcessedContent();
    }

    @Override
    public void processData() {
        reduceNamesLength();
        checkDuplicatedDBFNames();
        formatDates();
        calculateLengths();
        addFields();
        addValues();
    }

    @Override
    public void processDCT() {
        addDCTHeaders();
        addDCTBody();
    }

    private void reduceNamesLength() {
        for (int i = 0; i < importContent.size(); i++) {
            String name = importContent.getName(i);
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

    private void formatDates() {
        if (Props.getDateFormat().equals("unchanged")) {
            return;
        }
        for (int i = 0; i < importContent.size(); i++) {
            String value = importContent.getValue(i);
            if (value.matches("^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.(19|20)\\d{2}$")) {
                String dd = value.substring(0, 2);
                String mm = value.substring(3, 5);
                String yyyy = value.substring(6, 10);
                String pattern = Props.getDateFormat().toLowerCase();
                value = pattern.replace("dd", dd).replace("mm", mm).replace("yyyy", yyyy);
                importContent.setValue(i, value);
            }
        }
    }

    private void calculateLengths() {
        for (int i = 0; i < importContent.size(); i++) {
            if (!importContent.getValue(i).isEmpty()) {
                lengths.add(importContent.getValue(i).length());
            } else {
                lengths.add(1);
            }
        }
    }

    private void addFields() {
        for (int i = 0; i < importContent.size(); i++) {
            DBFField dbffield = new DBFField();
            dbffield.setName(dbfNames.get(i));
            dbffield.setDataType((byte) 67);
            dbffield.setFieldLength(lengths.get(i));
            dataContent.getDbfFields()[i] = dbffield;
        }
    }

    private void addValues() {
        for (int i = 0; i < importContent.size(); i++) {
            dataContent.getValues()[i] = importContent.getValue(i);
        }
    }

    private void addDCTHeaders() {
        dctContent.getLines().add("Content-Type=" + importContent.getContentType());
        dctContent.getLines().add("Data-Type=dct");
        dctContent.getLines().add("Import-Format=dbf");
        dctContent.getLines().add("First-String-Read=true");
        dctContent.getLines().add("");
    }

    private void addDCTBody() {
        for (int i = 0; i < importContent.size(); i++) {
            String name = importContent.getName(i);
            String dbfName = dbfNames.get(i);
            dctContent.getLines().add(name.toUpperCase() + "=${" + dbfName.toUpperCase() + "}");
        }
    }

    @Override
    public DBFProcessedContent getDataContent() {
        return dataContent;
    }

    @Override
    public DCTProcessedContent getDctContent() {
        return dctContent;
    }
}
