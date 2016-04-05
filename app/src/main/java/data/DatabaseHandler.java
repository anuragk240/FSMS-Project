package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;

import java.util.ArrayList;

import constants.TableConst;

/**
 * Created by Anurag on 25-03-2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private ArrayList<Entry> data = null;

    public DatabaseHandler(Context context) {
        super(context, TableConst.DATABASE_NAME, null, TableConst.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_REVENUE_TABLE = "CREATE TABLE " + TableConst.TABLE_REVENUE_NAME + "(" +
                TableConst.NAME + " TEXT PRIMARY KEY, " + TableConst.VALUE + " LONG, " + TableConst.DATE +
                " TEXT);";

        String CREATE_EXPENSE_TABLE = "CREATE TABLE " + TableConst.TABLE_EXPENSE_NAME + "(" +
                TableConst.NAME + " TEXT PRIMARY KEY, " + TableConst.VALUE + " LONG, " + TableConst.DATE +
                " TEXT);";

        db.execSQL(CREATE_REVENUE_TABLE);
        db.execSQL(CREATE_EXPENSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableConst.TABLE_REVENUE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TableConst.TABLE_EXPENSE_NAME + ";");

        onCreate(db);
    }

    public void addEntry(Entry e){
        SQLiteDatabase db = this.getWritableDatabase();

        String tablename = e.getTablename();

        ContentValues values = new ContentValues();
        values.put(TableConst.NAME, e.getNameofentry());
        values.put(TableConst.VALUE, e.getValue());
        values.put(TableConst.DATE, e.getDate());


        db.insert(tablename, null, values);
        db.close();
    }

    public ArrayList<Entry> getEntry(String tablename){
        data = new ArrayList<Entry>();
        String QUERY = "SELECT * FROM " + tablename;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mycursor = db.query(tablename, new String[]{TableConst.NAME, TableConst.VALUE, TableConst.DATE},
                null, null, null, null, TableConst.NAME);

        if(mycursor.moveToFirst()){
            do{
                Entry entry = new Entry();
                entry.setNameofentry(mycursor.getString(mycursor.getColumnIndex(TableConst.NAME)));
                entry.setValue(mycursor.getLong(mycursor.getColumnIndex(TableConst.VALUE)));
                entry.setDate(mycursor.getString(mycursor.getColumnIndex(TableConst.DATE)));

                data.add(entry);
            }while(mycursor.moveToNext());
        }
        return data;
    }
}
