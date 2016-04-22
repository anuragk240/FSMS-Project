package data;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Anurag on 25-03-2016.
 */
public class Entry implements Serializable {

    private static final long serializableUID = 10L;

    private String nameofentry;
    private Calendar date;
    private double value;
    private boolean isDatepresent;
    private boolean isvaluepresent;
    private String tablename;
    private String type;

    public Entry(){
        nameofentry = "";
        date = Calendar.getInstance();
        isDatepresent = false;
        isvaluepresent = false;
    }

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

    public long getDate() {
        if(isDatepresent){
            return date.getTimeInMillis();
        }
        else {
            return -1;
        }
    }

    public Calendar getCalenderDate(){
        if (isDatepresent) {
            return date;
        }
        else {
            return null;
        }
    }

    public void setDate(long date) {
        this.date.setTimeInMillis(date);
        isDatepresent = true;
    }

    public void setDate(int dd, int mm, int yyyy){
        this.date.set(yyyy, mm, dd);
        isDatepresent = true;
    }

    public void setDate(Calendar c){
        this.date = c;
        isDatepresent = true;
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
