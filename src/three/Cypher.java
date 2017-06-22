package three;

import java.util.ArrayList;
import java.util.List;

/*
Represents encrypted string value which is stored in the StringBuilder object.
Parsed String values will be stored in List<String> fields, which will be retrieved
during saving in the CSV file.
 */
public class Cypher {
//   We have 7 fields in table from task condition.
    private final int FIELDS_QTY=7;
    private StringBuilder encrypted;
    private boolean dangerous;
    private boolean fragile;
    private List<String> fields;

    public Cypher(String encrypted) {
        this.encrypted = new StringBuilder(encrypted);
        this.dangerous = false;
        this.fragile = false;
        fields=new ArrayList<>(FIELDS_QTY);
    }

    public List<String> getFields() {
        return fields;
    }

    public StringBuilder getEncrypted() {
        return encrypted;
    }

    public boolean isDangerous() {
        return dangerous;
    }

    public void setDangerous(boolean dangerous) {
        this.dangerous = dangerous;
    }

    public boolean isFragile() {
        return fragile;
    }

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }



}
