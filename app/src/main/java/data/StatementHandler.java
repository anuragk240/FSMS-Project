package data;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import adapters.BalanceSheetAdapter;
import adapters.EntryAdapter;
import constants.EntryTypeConst;
import constants.FragmentConst;
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
    private ArrayList<Entry> dbEntry2 = new ArrayList<>();

    public StatementHandler(Context cont,Activity act, ListView list){
        context = cont;
        activity = act;
        listView = list;
    }

    public void refresh(String fragmentname){
        String tablename;
        Entry e;
        dbEntry.clear();
        dbEntry2.clear();
        long total = 0;
        String str;
        long total_l_e = 0;
        if (fragmentname.equals(FragmentConst.BALANCE_SHEET)) {
            e = new Entry();
            e.setNameofentry("ASSETS");
            dbEntry.add(e);
            total += getData(dbEntry, TableConst.TABLE_ASSET_NAME);


            e = new Entry();
            e.setNameofentry("LIABILITIES");
            dbEntry2.add(e);
            total_l_e += getData(dbEntry2, TableConst.TABLE_LIABILITY_NAME);
            e = null;
            dbEntry2.add(e);

            e = new Entry();
            e.setNameofentry("USER'S EQUITY");
            dbEntry2.add(e);
            total_l_e += getData(dbEntry2, TableConst.TABLE_EQUITY_NAME);
            loadBalanceSheet(total, total_l_e);
        }
        else {
            switch (fragmentname) {
                case FragmentConst.INCOME_STATEMENT:
                    tablename = TableConst.TABLE_INCOME_NAME;
                    str = "Net Income :";
                    break;
                case FragmentConst.ASSETS:
                    tablename = TableConst.TABLE_ASSET_NAME;
                    str = "Total Assets :";
                    break;
                case FragmentConst.EQUITY:
                    tablename = TableConst.TABLE_EQUITY_NAME;
                    str = "Total Equity :";
                    break;
                case FragmentConst.LIABILITIES:
                    default:
                    tablename = TableConst.TABLE_LIABILITY_NAME;
                        str = "Total Liabilities :";
                    break;
            }
            total = getData(dbEntry, tablename);
            e = new Entry();
            e.setNameofentry(str);
            e.setValue(total);
            dbEntry.add(e);
            EntryAdapter adapter = new EntryAdapter(activity, R.layout.entry_list, dbEntry);

            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void loadBalanceSheet(long totalassets, long total_l_e) {
        BalanceSheetEntry e;
        ArrayList<BalanceSheetEntry> list = new ArrayList<>();
        setBSheetTitles(list);
        int i = 0;
        while (i<dbEntry.size() || i<dbEntry2.size()) {
            e = new BalanceSheetEntry();
            if (i<dbEntry.size()){
                e.setAsset(dbEntry.get(i));
            }
            if (i<dbEntry2.size()){
                e.setLiability_Equity(dbEntry2.get(i));
            }
            list.add(e);
            ++i;
        }
        Entry entry;
        e = new BalanceSheetEntry();
        entry = new Entry();
        entry.setNameofentry("Total Assets :");
        entry.setValue(totalassets);
        e.setAsset(entry);

        entry = new Entry();
        entry.setNameofentry("Total User's Equity and Liabilities :");
        entry.setValue(total_l_e);
        e.setLiability_Equity(entry);

        list.add(e);

        e = new BalanceSheetEntry();
        if (totalassets != total_l_e) {
            e.setTitle("Not Balanced!!");
        }
        else {
            e.setTitle("Balanced!!");
        }
        list.add(e);

        BalanceSheetAdapter adapter = new BalanceSheetAdapter(activity, R.layout.balancesheet_row, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private long getData(ArrayList<Entry> e, String tablename){
        long total = 0;
        if(String.valueOf(tablename).equals(TableConst.TABLE_INCOME_NAME)){
            //setStatementTitles(tablename);
            total += getfromDB(tablename, EntryTypeConst.REVENUE, e);
            total -= getfromDB(tablename, EntryTypeConst.EXPENSE, e);

        }
        else if(String.valueOf(tablename).equals(TableConst.TABLE_ASSET_NAME) ||
                String.valueOf(tablename).equals(TableConst.TABLE_LIABILITY_NAME)){
            //setStatementTitles(tablename);
            total += getfromDB(tablename, EntryTypeConst.CURRENT, e);
            total += getfromDB(tablename, EntryTypeConst.FIXED, e);
            total += getfromDB(tablename, EntryTypeConst.OTHER, e);
        }
        else if(String.valueOf(tablename).equals(TableConst.TABLE_EQUITY_NAME)){
            total += getfromDB(tablename, EntryTypeConst.EQUITY, e);
        }
        return total;
    }

    private long getfromDB(String tablename, String type, ArrayList<Entry> list){

        ArrayList<Entry> entries = null;
        DatabaseHandler dba = new DatabaseHandler(context);
        entries = dba.getEntry(tablename, type);

        Entry e = new Entry();

        e.setNameofentry(type);
        e.setType(type);
        e.setTablename(tablename);

        if(entries.isEmpty()){
            e.setValue(0);
        }
        list.add(e);


        for(int i = 0;i < entries.size(); ++i){
            Entry t = new Entry();
            t.setNameofentry(entries.get(i).getNameofentry());
            t.setValue(entries.get(i).getValue());
            Log.d("value getfromdb", String.valueOf(entries.get(i).getValue()));
            t.setDate(entries.get(i).getDate());
            t.setTablename(entries.get(i).getTablename());
            t.setType(entries.get(i).getType());
            list.add(t);
        }
        long total = 0;
        if(!entries.isEmpty()){
            Entry p = new Entry();
            p.setNameofentry("Total :");
            p.setTablename(tablename);
            p.setType(type);
            for(int i=0; i<entries.size();++i){
                total+=entries.get(i).getValue();
            }
            p.setValue(total);
            list.add(p);
        }
        return total;
    }

    private void setBSheetTitles(ArrayList<BalanceSheetEntry> list){
        BalanceSheetEntry e = new BalanceSheetEntry();

        e.setTitle(FragmentConst.BALANCE_SHEET);
        list.add(e);

        e = new BalanceSheetEntry();

        e.setTitle(FragmentConst.TITLE);
        list.add(e);

        e = new BalanceSheetEntry();

        SimpleDateFormat dtfrmat = new SimpleDateFormat("LLLL MM, yyyy");
        String date = dtfrmat.format(Calendar.getInstance().getTime());
        e.setTitle(date);
        list.add(e);

        e = new BalanceSheetEntry();
        list.add(e);
    }
}
