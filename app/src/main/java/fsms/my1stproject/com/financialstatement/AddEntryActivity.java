package fsms.my1stproject.com.financialstatement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import constants.EntryTypeConst;
import constants.MiscConst;
import constants.RegistrationConst;
import constants.FragmentConst;
import constants.TableConst;
import data.DatabaseHandler;
import data.Entry;

public class AddEntryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private EditText name, values;
    private DatePicker datePicker;
    private Button save;
    private TextView statementname;

    private String Table_name;
    private String type;
    private DatabaseHandler dba;

    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Bundle fromactivity = getIntent().getBundleExtra(MiscConst.BUNDLE_KEY);

        int currenttab = fromactivity.getInt(MiscConst.CURRENT_TAB);
        dba = new DatabaseHandler(getApplicationContext());

        name = (EditText) findViewById(R.id.getname);
        values = (EditText) findViewById(R.id.getvalue);
        datePicker = (DatePicker) findViewById(R.id.datepicker);
        statementname = (TextView) findViewById(R.id.add_title);

        spinner = (Spinner) findViewById(R.id.spinnerid);
        save = (Button) findViewById(R.id.save_button);

        Entry old = new Entry();

        if(fromactivity.getString(MiscConst.ADD_UPDATE_KEY).equals(MiscConst.UPDATE)){
            setTitle("Update Entry");

            old = (Entry) fromactivity.getSerializable(MiscConst.ENTRY_KEY);
            name.setText(old.getNameofentry());
            values.setText(String.valueOf(old.getValue()));
            datePicker.updateDate(old.getCalenderDate().get(Calendar.YEAR),
                    old.getCalenderDate().get(Calendar.MONTH), old.getCalenderDate().get(Calendar.DAY_OF_MONTH));
            int pos;
            switch(old.getType()){
                case EntryTypeConst.REVENUE:
                case EntryTypeConst.FIXED:
                case EntryTypeConst.EQUITY:
                    pos = 0;
                    break;
                case EntryTypeConst.EXPENSE:
                case EntryTypeConst.CURRENT:
                    pos = 1;
                    break;
                case EntryTypeConst.OTHER:
                default:
                    pos = 2;
                    break;
            }
            save.setText("UPDATE");
            spinner.setSelection(pos);
        }

        final Entry finalOld = old;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entry e = new Entry();
                if(test()){
                    e.setNameofentry(name.getText().toString());
                    e.setValue(Double.valueOf(values.getText().toString()));
                    e.setDate(datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());
                    e.setTablename(Table_name);
                    e.setType(type);
                    if(fromactivity.getString(MiscConst.ADD_UPDATE_KEY).equals(MiscConst.ADD)){
                        dba.addEntry(e);
                        if(DatabaseHandler.isinserted == -1){
                            Toast.makeText(getApplicationContext(), "Duplicate Entry Name!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Successfully Saved.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        dba.updateEntry(e, finalOld);
                        Toast.makeText(getApplicationContext(), "No. of Entries updated :" +
                                DatabaseHandler.nrows, Toast.LENGTH_LONG).show();
                    }
                    finish();
                }
            }
        });
        spinner.setOnItemSelectedListener(this);
        spinner.setVisibility(View.VISIBLE);
        switch(currenttab){
            case 0:
                adapter = adapter.createFromResource(AddEntryActivity.this, R.array.income_statement,
                        android.R.layout.simple_spinner_item);
                statementname.setText(FragmentConst.INCOME_STATEMENT);
                Table_name = TableConst.TABLE_INCOME_NAME;
                break;
            case 1:
                adapter = adapter.createFromResource(AddEntryActivity.this, R.array.asset,
                        android.R.layout.simple_spinner_item);
                statementname.setText(FragmentConst.ASSETS);
                Table_name = TableConst.TABLE_ASSET_NAME;
                break;
            case 2:
                adapter = adapter.createFromResource(AddEntryActivity.this, R.array.liability,
                        android.R.layout.simple_spinner_item);
                statementname.setText(FragmentConst.LIABILITIES);
                Table_name = TableConst.TABLE_LIABILITY_NAME;
                break;
            case 3:
            default:
                adapter = adapter.createFromResource(AddEntryActivity.this, R.array.equity,
                        android.R.layout.simple_spinner_item);
                statementname.setText(FragmentConst.EQUITY);
                Table_name = TableConst.TABLE_EQUITY_NAME;
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String temp = parent.getItemAtPosition(position).toString();
        switch(temp){
            case "Revenue and Gains":
                type = EntryTypeConst.REVENUE;
                break;
            case "Expenses and Losses":
                type = EntryTypeConst.EXPENSE;
                break;
            case "Fixed Assets":
            case "Fixed Liability":
                type = EntryTypeConst.FIXED;
                break;
            case "Current Assets":
            case "Current Liability":
                type = EntryTypeConst.CURRENT;
                break;
            case "Other Assets":
            case "Other Liability":
                type = EntryTypeConst.OTHER;
                break;
            case "Equity":
                type = EntryTypeConst.EQUITY;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    boolean test(){
        boolean result = true;
        if(name.getText().toString().isEmpty()){
            name.setHint("*Required Field");
            result = false;
        }
        if(values.getText().toString().isEmpty()){
            values.setHint("*Required Field");
            result = false;
        }
        return result;
    }
}
