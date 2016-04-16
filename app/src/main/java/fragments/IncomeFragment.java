package fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import adapters.TabsPagerAdapter;
import constants.EntryTypeConst;
import constants.FragmentConst;
import constants.RegistrationConst;
import constants.TableConst;
import data.DatabaseHandler;
import data.Entry;
import data.StatementHandler;
import fsms.my1stproject.com.financialstatement.AddEntryActivity;
import fsms.my1stproject.com.financialstatement.R;
import fsms.my1stproject.com.financialstatement.TabActivity;

public class IncomeFragment extends Fragment {

    private DatabaseHandler dba;
    private TextView title, statementname, statementdate;
    private ListView listView;
    private StatementHandler incomehandler;

    public static final String SELECTED_TAB_KEY = "Selected Tab";
    public static final String TABLE_KEY = "Selected Table";
    //Arguments
    private String selectedtab;

    public IncomeFragment() {
        // Required empty public constructor
    }

    public static IncomeFragment newInstance(int position) {

        Bundle bundle = new Bundle();
        switch(position){
            case 0:
                bundle.putString(SELECTED_TAB_KEY, FragmentConst.INCOME_STATEMENT);
                bundle.putString(TABLE_KEY, TableConst.TABLE_INCOME_NAME);
                break;
            case 1:
                bundle.putString(SELECTED_TAB_KEY, FragmentConst.ASSETS);
                bundle.putString(TABLE_KEY, TableConst.TABLE_ASSET_NAME);
                break;
            case 2:
                bundle.putString(SELECTED_TAB_KEY, FragmentConst.LIABILITIES);
                bundle.putString(TABLE_KEY, TableConst.TABLE_LIABILITY_NAME);
                break;
            case 3:
                bundle.putString(SELECTED_TAB_KEY, FragmentConst.EQUITY);
                bundle.putString(TABLE_KEY, TableConst.TABLE_EQUITY_NAME);
                break;
            default:
                bundle.putString(SELECTED_TAB_KEY, FragmentConst.LIABILITIES);
                bundle.putString(TABLE_KEY, TableConst.TABLE_LIABILITY_NAME);
                break;
        }
        IncomeFragment fragment = new IncomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.floating_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(getUserVisibleHint()){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            dba = new DatabaseHandler(getContext());
            Entry e = (Entry) listView.getItemAtPosition(info.position);
            if(validate(e) || selectedtab.equals(FragmentConst.BALANCE_SHEET)){
                Toast.makeText(getContext(), "Not Applicable!", Toast.LENGTH_LONG).show();
                return true;
            }
            Log.d("Selected Tab ", selectedtab);
            Log.d("SelectedEntry ", e.getNameofentry());
            switch (item.getItemId()) {
                case R.id.update_button:
                    Intent updateentry = new Intent(getActivity(), AddEntryActivity.class);
                    updateentry.putExtra(RegistrationConst.CURRENT_TAB, convertToInt(selectedtab));
                    updateentry.putExtra(RegistrationConst.ADD_UPDATE_KEY, "Update");
                    updateentry.putExtra(TableConst.NAME, e.getNameofentry());
                    updateentry.putExtra(TableConst.VALUE, e.getValue());
                    updateentry.putExtra(TableConst.DATE, e.getDate());
                    updateentry.putExtra(TableConst.TYPE, e.getType());
                    updateentry.putExtra("Table Name", e.getTablename());
                    startActivity(updateentry);
                    return true;
                case R.id.del_button:
                    dba.deleteEntry(e);
                    Toast.makeText(getContext(), "No. of Rows Affected :" + DatabaseHandler.nrows, Toast.LENGTH_LONG).show();
                    incomehandler.refresh(selectedtab);
                    StatementHandler bsheet_handler =  new StatementHandler(getContext(), getActivity(),
                            BalanceSheetFragment.bsheet);
                    bsheet_handler.refresh(FragmentConst.BALANCE_SHEET);
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }
        else {
            return false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedtab = getArguments().getString(SELECTED_TAB_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_income, container, false);

        title = (TextView) root.findViewById(R.id.Titleid);
        statementname = (TextView) root.findViewById(R.id.statementnameid);
        statementdate = (TextView) root.findViewById(R.id.statementdateid);
        listView = (ListView) root.findViewById(R.id.listviewid);

        incomehandler = new StatementHandler(getContext(), getActivity(), listView);

        registerForContextMenu(listView);

        title.setText(FragmentConst.TITLE);
        statementname.setText(selectedtab);

        SimpleDateFormat dtformat = new SimpleDateFormat("LLLL MM, yyyy");
        statementdate.setText(dtformat.format(Calendar.getInstance().getTime()));

        return root;
    }

    @Override
    public void onResume() {
        incomehandler.refresh(selectedtab);
        super.onResume();
    }

    private int convertToInt(String tab){
        switch (tab){
            case FragmentConst.INCOME_STATEMENT:
                return 0;
            case FragmentConst.ASSETS:
                return 1;
            case FragmentConst.LIABILITIES:
                return 2;
            case FragmentConst.EQUITY:
            default:
                return 3;
        }
    }

    private boolean validate(Entry e){
        switch (e.getNameofentry()){
            case EntryTypeConst.CURRENT:
            case EntryTypeConst.FIXED:
            case EntryTypeConst.OTHER:
            case EntryTypeConst.REVENUE:
            case EntryTypeConst.EXPENSE:
            case EntryTypeConst.EQUITY:
            case "Total :":
                return true;
        }
        return false;
    }
}
