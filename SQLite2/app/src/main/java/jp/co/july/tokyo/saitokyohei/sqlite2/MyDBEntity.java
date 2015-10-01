package jp.co.july.tokyo.saitokyohei.sqlite2;

/**
 * Created by saito.kyohei on 2015/10/01.
 */
public class MyDBEntity {

    private int rowId;

    private String value;

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getRowId() {
        return rowId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}