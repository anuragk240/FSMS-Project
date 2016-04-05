package fragments;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import adapters.EntryAdapter;
import constants.FragmentConst;
import constants.TableConst;
import data.DatabaseHandler;
import data.Entry;
import fsms.my1stproject.com.financialstatement.MainActivity;
import fsms.my1stproject.com.financialstatement.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {

    private DatabaseHandler dba;
    private TextView title, statementname, statementdate;
    private ListView listView;
    private ArrayList<Entry> dbEntry = new ArrayList<>();
    private EntryAdapter adapter;

    public IncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_income, container, false);
        dba = new DatabaseHandler(getContext());

        title = (TextView) root.findViewById(R.id.Titleid);
        statementname = (TextView) root.findViewById(R.id.statementnameid);
        statementdate = (TextView) root.findViewById(R.id.statementdateid);
        listView = (ListView) root.findViewById(R.id.listviewid);

        title.setText(FragmentConst.TITLE);
        statementname.setText(FragmentConst.INCOME_STATEMENT);
        //statementdate.setText("Date");
        dbEntry.clear();

        getfromDB(TableConst.TABLE_REVENUE_NAME);
        Log.d("after getfromdb rev", String.valueOf(dbEntry.size()));
        getfromDB(TableConst.TABLE_EXPENSE_NAME);
        Log.d("after getfromdb exp", String.valueOf(dbEntry.size()));

        adapter = new EntryAdapter(getActivity(), R.layout.entry_list, dbEntry);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return root;
    }

    private void getfromDB(String tablename){
        ArrayList<Entry> entries = null;
        dba = new DatabaseHandler(getContext());
        entries = dba.getEntry(tablename);
        Entry e = new Entry();

        e.setDate("");
        e.setNameofentry(tablename);
        e.setTablename(tablename);

        if(entries.isEmpty()){
            e.setValue(0);
        }
        Log.d("getting data from db",tablename);
        dbEntry.add(e);

        for(int i = 0;i < entries.size(); ++i){
            e.setNameofentry(entries.get(i).getNameofentry());
            e.setValue(entries.get(i).getValue());
            e.setDate(entries.get(i).getDate());
            e.setTablename(entries.get(i).getTablename());
            dbEntry.add(e);
        }
    }

}
