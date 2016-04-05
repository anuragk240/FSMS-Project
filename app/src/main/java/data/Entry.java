package data;

/**
 * Created by Anurag on 25-03-2016.
 */
public class Entry {
    private String nameofentry;
    private String date;
    private long value;
    private boolean isvaluepresent = false;
    private String tablename;

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNameofentry() {
        return nameofentry;
    }

    public void setNameofentry(String nameofentry) {
        this.nameofentry = nameofentry;
    }

    public long getValue() {
        if(isvaluepresent){
            return  value;
        }
        else{
            return -1;
        }
    }

    public void setValue(long value) {
        this.value = value;
        isvaluepresent = true;
    }
}
