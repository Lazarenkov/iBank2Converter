package core.data.content.processed;

import com.linuxense.javadbf.DBFField;

public class DBFProcessedContent extends ProcessedContent {

    private final DBFField[] dbfFields;
    private final Object[] values;

    public DBFProcessedContent(DBFField[] dbfFields, Object[] values) {
        this.dbfFields = dbfFields;
        this.values = values;
    }

    public DBFField[] getDbfFields() {
        return dbfFields;
    }

    public Object[] getValues() {
        return values;
    }
}
