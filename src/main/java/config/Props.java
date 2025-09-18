package config;

import core.data.types.Format;
import exceptions.InitException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static core.data.types.Format.*;

public class Props {

    private static final Map<String, String> props = new HashMap<>();

    private Props() {
    }

    public static void init(String[] args) {
        collectOutputFormat(args);
        addDefaultProps();
        readProps();
        validateProps();
    }

    private static void collectOutputFormat(String[] args) {
        if (args[0].equalsIgnoreCase("DBF") | args[0].equalsIgnoreCase("CSV")){
            props.put("FORMAT", args[0].toUpperCase());
        } else {
            throw new InitException("Некорректный параметр запуска: " + args[0]);
        }
    }

    private static void addDefaultProps(){
        props.put("INPUT", null);
        props.put("CLEAR_INPUT", "true");
        props.put("CLEAR_OUTPUT", "true");
        switch (getFormat()) {
            case DBF -> {
                props.put("DBF_OUTPUT", null);
                props.put("DBF_DCT_OUTPUT", null);
                props.put("СHARACTERSET", null);
                props.put("DATE_FORMAT", null);
            }
            case CSV -> {
                props.put("CSV_OUTPUT", null);
                props.put("CSV_DCT_OUTPUT", null);
            }
        }
    }

    private static void readProps() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("application.properties"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.startsWith("#")) {
                    addProp(line);
                }
            }
        } catch (IOException e) {
            throw new InitException("Не удалось прочитать файл test.properties");
        }
    }

    private static void addProp(String line) {
        int split = line.indexOf("=");
        if (split > 0) {
            String key = line.substring(0, split).toUpperCase().trim();
            String value = line.substring(split + 1).trim();
            if (props.containsKey(key)) {
                props.replace(key, value);
            }
        }
    }

    private static void validateProps() {
        props.forEach((key, value) -> {
            if (value == null || value.isBlank()) {
                throw new InitException("Не задан параметр " + key + " в файле properties");
            }
        });
        String characterset = props.get("СHARACTERSET");
        if (!characterset.equals("WINDOWS-1251") & !characterset.equals("CP866")) { //TODO org.apache.commons:commons-lang3.containsAny()
            throw new InitException("Не корректное значение параметра СHARACTERSET");
        }
    }

    public static Format getFormat() {
        if (props.get("FORMAT").equals("DBF")){
            return DBF;
        } else {
            return CSV;
        }
    }

    public static String getInputPath() {
        return props.get("INPUT");
    }

    public static String getOutputPath() {
        if (getFormat()==DBF){
            return props.get("DBF_OUTPUT");
        } else {
            return props.get("CSV_OUTPUT");
        }
    }

    public static String getDCTOutputPath() {
        if (getFormat()==DBF){
            return props.get("DBF_DCT_OUTPUT");
        } else {
            return props.get("CSV_DCT_OUTPUT");
        }
    }

    public static boolean isClearInput(){
        return Boolean.parseBoolean(props.get("CLEAR_INPUT"));
    }

    public static boolean isClearOutput(){
        return Boolean.parseBoolean(props.get("CLEAR_OUTPUT"));
    }

    public static String getCharacterset(){
        return props.get("СHARACTERSET");
    }
}
