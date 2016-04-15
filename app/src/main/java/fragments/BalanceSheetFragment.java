package fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

import constants.EntryTypeConst;
import constants.FragmentConst;
import constants.TableConst;
import data.DatabaseHandler;
import data.Entry;
import data.StatementHandler;
import fsms.my1stproject.com.financialstatement.R;


public class BalanceSheetFragment extends Fragment {

    private ListView bsheet;
    private StatementHandler BSheet_handler;

    public BalanceSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        insertDefaultEntries();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View row;
        row = inflater.inflate(R.layout.fragment_balance_sheet, container, false);

        bsheet = (ListView) row.findViewById(R.id.balancesheetlistView);

        BSheet_handler = new StatementHandler(getContext(), getActivity(), bsheet);


        return row;
    }

    @Override
    public void onResume() {
        BSheet_handler.refresh(FragmentConst.BALANCE_SHEET);
        super.onResume();
    }

    private void insertDefaultEntries(){
        Entry e;
        DatabaseHandler dba = new DatabaseHandler(getContext());
        e = new Entry();
        e.setNameofentry("Common Stock");
        e.setValue(0);
        e.setDate(Calendar.getInstance().getTimeInMillis());
        e.setTablename(TableConst.TABLE_EQUITY_NAME);
        e.setType(EntryTypeConst.EQUITY);
        dba.addEntry(e);

        e = new Entry();
        e.setNameofentry("Treasury Stock");
        e.setValue(0);
        e.setDate(Calendar.getInstance().getTimeInMillis());
        e.setTablename(TableConst.TABLE_EQUITY_NAME);
        e.setType(EntryTypeConst.EQUITY);
        dba.addEntry(e);

        e = new Entry();

        e.setNameofentry("Retained Earning");
        e.setValue(0);
        e.setDate(Calendar.getInstance().getTimeInMillis());
        e.setTablename(TableConst.TABLE_EQUITY_NAME);
        e.setType(EntryTypeConst.EQUITY);
        dba.addEntry(e);
    }
}
