package data;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import java.util.ArrayList;

import adapters.EntryAdapter;
import constants.EntryTypeConst;
import constants.TableConst;
import fsms.my1stproject.com.financialstatement.R;

/**
 * Created by Anurag Kumar on 09-Apr-16.
 */
public class StatementHandler {

    private Context context;
    private Activity activity;
    private ListView listView;

    private ArrayList<Entry> dbEntry = new ArrayList<>();

    public StatementHandler(Context cont,Activity act, ListView list){
        context = cont;
        activity = act;
        listView = list;
    }

    public void refresh(String tablename){
        dbEntry.clear();
        if(String.valueOf(tablename).equals(TableConst.TABLE_INCOME_NAME)){
            getfromDB(tablename, EntryTypeConst.REVENUE);
            getfromDB(tablename, EntryTypeConst.EXPENSE);

        }
        else if(String.valueOf(tablename).equals(TableConst.TABLE_ASSET_NAME) ||
                String.valueOf(tablename).equals(TableConst.TABLE_LIABILITY_NAME)){
            getfromDB(tablename, EntryTypeConst.CURRENT);
            getfromDB(tablename, EntryTypeConst.FIXED);
            getfromDB(tablename, EntryTypeConst.OTHER);
        }
        EntryAdapter adapter = new EntryAdapter(activity, R.layout.entry_list, dbEntry);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getfromDB(String tablename, String type){

        ArrayList<Entry> entries = null;
        DatabaseHandler dba = new DatabaseHandler(context);
        entries = dba.getEntry(tablename, type);

        Entry e = new Entry();

        e.setNameofentry(type);
        e.setType(type);
        e.setDate("");
        e.setTablename(tablename);

        if(entries.isEmpty()){
            e.setValue(0);
        }
        dbEntry.add(e);


        for(int i = 0;i < entries.size(); ++i){
            Entry t = new Entry();
            t.setNameofentry(entries.get(i).getNameofentry());
            t.setValue(entries.get(i).getValue());
            t.setDate(entries.get(i).getDate());
            t.setTablename(entries.get(i).getTablename());
            t.setType(entries.get(i).getType());
            dbEntry.add(t);
        }

        if(!entries.isEmpty()){
            Entry p = new Entry();
            p.setNameofentry("Total :");
            p.setTablename(tablename);
            p.setDate("");
            p.setType(type);
            long total = 0;
            for(int i=0; i<entries.size();++i){
                total+=entries.get(i).getValue();
            }
            p.setValue(total);
            dbEntry.add(p);
        }

    }

}
