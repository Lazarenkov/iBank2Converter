package core.data.content.imported;

import java.util.ArrayList;

public class ImportContent {

    private String contentType;

    private final ArrayList<String> names = new ArrayList<>();
    private final ArrayList<String> values = new ArrayList<>();

    public void add(String name, String value) {
        names.add(name);
        values.add(value);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int size() {
        return names.size();
    }

    public String getName(int i) {
        try{
            return names.get(i);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public String getValue(int i) {
        try{
            return values.get(i);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public String getContentType() {
        return contentType;
    }
}
