package core.data.content;

import java.util.ArrayList;

public class ImportedContent {

    private String contentType;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> dbfNames = new ArrayList<>();
    private ArrayList<String> values = new ArrayList<>();
    private ArrayList<Integer> lengths = new ArrayList<>();

    public void add(String name, String value){
        names.add(name);
        values.add(value);
    }

    public int size(){
        return names.size();
    }

    public String getContentType() {
        return contentType;
    }

    public String getName(int i) {
        return names.get(i);
    }

    public String getValue(int i) {
        return values.get(i);
    }

    public String getDbfName(int i) {
        return dbfNames.get(i);
    }

    public int getLength(int i) {
        return lengths.get(i);
    }

    public void setName(int i, String name) {
        names.set(i, name);
    }

    public void setValue(int i, String value) {
        values.set(i, value);
        lengths.set(i, value.length());
    }

    public void setDbfName(int i, String dbfName) {
        dbfNames.set(i, dbfName);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public void setDbfNames(ArrayList<String> dbfNames) {
        this.dbfNames = dbfNames;
    }

    public void setLengths(ArrayList<Integer> lengths) {
        this.lengths = lengths;
    }
}
