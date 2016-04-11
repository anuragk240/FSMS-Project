package data;

/**
 * Created by Anurag on 25-03-2016.
 */
public class Entry {
    private String nameofentry;
    private String date;
    private double value;
    private boolean isvaluepresent = false;
    private String tablename;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public double getValue() {
        if(isvaluepresent){
            return  value;
        }
        else{
            return -1;
        }
    }

    public void setValue(double value) {
        this.value = value;
        isvaluepresent = true;
    }
}
