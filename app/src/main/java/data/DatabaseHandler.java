package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import constants.EntryTypeConst;
import constants.TableConst;

/**
 * Created by Anurag on 25-03-2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public static long isinserted;
    public static int nrows;
    private ArrayList<Entry> data = null;

    public DatabaseHandler(Context context) {
        super(context, TableConst.DATABASE_NAME, null, TableConst.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INCOME_TABLE = "CREATE TABLE " + TableConst.TABLE_INCOME_NAME + "(" +
                TableConst.NAME + " TEXT PRIMARY KEY, " + TableConst.VALUE + " LONG, " + TableConst.TYPE +
                " TEXT NOT NULL, " + TableConst.DATE + " LONG NOT NULL);";

        String CREATE_ASSET_TABLE = "CREATE TABLE " + TableConst.TABLE_ASSET_NAME + "(" +
                TableConst.NAME + " TEXT PRIMARY KEY, " + TableConst.VALUE + " LONG, " + TableConst.TYPE +
                " TEXT NOT NULL, " + TableConst.DATE + " LONG NOT NULL);";

        String CREATE_LIABILITY_TABLE = "CREATE TABLE " + TableConst.TABLE_LIABILITY_NAME + "(" +
                TableConst.NAME + " TEXT PRIMARY KEY, " + TableConst.VALUE + " LONG, " + TableConst.TYPE +
                " TEXT NOT NULL, " + TableConst.DATE + " LONG NOT NULL);";

        String CREATE_EQUITY_TABLE = "CREATE TABLE " + TableConst.TABLE_EQUITY_NAME + "(" +
                TableConst.NAME + " TEXT PRIMARY KEY, " + TableConst.VALUE + " LONG, " + TableConst.TYPE +
                " TEXT NOT NULL, " + TableConst.DATE + " LONG NOT NULL);";

        Log.d("New Tables created ", "new");

        db.execSQL(CREATE_INCOME_TABLE);
        db.execSQL(CREATE_ASSET_TABLE);
        db.execSQL(CREATE_LIABILITY_TABLE);
        db.execSQL(CREATE_EQUITY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableConst.TABLE_INCOME_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TableConst.TABLE_ASSET_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TableConst.TABLE_LIABILITY_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TableConst.TABLE_EQUITY_NAME + ";");

        onCreate(db);
    }

    public void addEntry(Entry e){
        SQLiteDatabase db = this.getWritableDatabase();

        String tablename = e.getTablename();

        ContentValues values = new ContentValues();
        values.put(TableConst.NAME, e.getNameofentry());
        values.put(TableConst.VALUE, e.getValue());
        values.put(TableConst.DATE, e.getDate());
        values.put(TableConst.TYPE, e.getType());


        isinserted = db.insert(tablename, null, values);
        db.close();
    }

    public ArrayList<Entry> getEntry(String tablename, String type){
        data = new ArrayList<>();
        String selection = TableConst.TYPE + " LIKE ?";
        String selectionArgs[] = new String[]{type};

        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("Before Query ", tablename);

        Cursor mycursor = db.query(tablename, null, selection, selectionArgs, null, null, TableConst.NAME);

        if(mycursor.moveToFirst()){
            do{
                Entry entry = new Entry();
                entry.setNameofentry(mycursor.getString(mycursor.getColumnIndex(TableConst.NAME)));
                entry.setValue(mycursor.getDouble(mycursor.getColumnIndex(TableConst.VALUE)));
                entry.setDate(mycursor.getLong(mycursor.getColumnIndex(TableConst.DATE)));
                entry.setType(mycursor.getString(mycursor.getColumnIndex(TableConst.TYPE)));
                entry.setTablename(tablename);

                data.add(entry);
            }while(mycursor.moveToNext());
        }
        db.close();
        return data;
    }

    public void deleteEntry(Entry e){
        SQLiteDatabase db = this.getWritableDatabase();
        nrows = db.delete(e.getTablename(), TableConst.NAME + " =? ", new String[]{e.getNameofentry()});
        db.close();
    }

    public void updateEntry(Entry newEntry, Entry oldEntry){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(TableConst.NAME, newEntry.getNameofentry());
        values.put(TableConst.DATE, newEntry.getDate());
        values.put(TableConst.VALUE, newEntry.getValue());
        values.put(TableConst.TYPE, newEntry.getType());

        String selection = TableConst.NAME + "=?";
        String selectionargs[] =new String[]{oldEntry.getNameofentry()};

        nrows = db.update(newEntry.getTablename(), values, selection, selectionargs);
        db.close();
    }

}
