package fsms.my1stproject.com.financialstatement;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import adapters.TabsPagerAdapter;
import constants.EntryTypeConst;
import constants.RegistrationConst;
import constants.FragmentConst;
import constants.TableConst;
import data.DatabaseHandler;
import data.Entry;
import fragments.IncomeFragment;
import fragments.RegistrationFragment;

public class AddEntryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private EditText name, values, date;
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

        final Bundle fromactivity = getIntent().getExtras();

        int currenttab = fromactivity.getInt(RegistrationConst.CURRENT_TAB);
        dba = new DatabaseHandler(getApplicationContext());

        name = (EditText) findViewById(R.id.getname);
        values = (EditText) findViewById(R.id.getvalue);
        date = (EditText) findViewById(R.id.getdate);
        statementname = (TextView) findViewById(R.id.add_title);

        spinner = (Spinner) findViewById(R.id.spinnerid);
        save = (Button) findViewById(R.id.save_button);

        final Entry old = new Entry();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entry e = new Entry();
                if(test()){
                    e.setNameofentry(name.getText().toString());
                    Double l = new Double(values.getText().toString());
                    e.setValue(l.doubleValue());
                    e.setDate(date.getText().toString());
                    e.setTablename(Table_name);
                    e.setType(type);
                    if(fromactivity.getString(RegistrationConst.ADD_UPDATE_KEY).equals("Add")){
                        dba.addEntry(e);
                    }
                    else {
                        dba.updateEntry(e, old);
                    }
                    if(DatabaseHandler.isinserted == -1){
                        Toast.makeText(getApplicationContext(), "Duplicate Entry Name!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Successfully Saved.", Toast.LENGTH_LONG).show();
                    }
                    finish();
                }
            }
        });
        spinner.setOnItemSelectedListener(this);

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
            default:
                adapter = adapter.createFromResource(AddEntryActivity.this, R.array.liability,
                        android.R.layout.simple_spinner_item);
                statementname.setText(FragmentConst.LIABILITIES);
                Table_name = TableConst.TABLE_LIABILITY_NAME;
                break;
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Log.d("Launcher Activity ", fromactivity.getString(RegistrationConst.ADD_UPDATE_KEY));

        if(fromactivity.getString(RegistrationConst.ADD_UPDATE_KEY).equals("Update")){
            setTitle("Update Entry");
            old.setNameofentry(fromactivity.getString(TableConst.NAME));
            old.setValue(fromactivity.getDouble(TableConst.VALUE));
            old.setDate(fromactivity.getString(TableConst.DATE));
            old.setTablename(fromactivity.getString("Table Name"));
            old.setType(fromactivity.getString(TableConst.TYPE));
            name.setText(old.getNameofentry());
            values.setText(String.valueOf(old.getValue()));
            date.setText(old.getDate());
            int pos;
            switch(old.getType()){
                case EntryTypeConst.REVENUE:
                case EntryTypeConst.FIXED:
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

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String temp = parent.getItemAtPosition(position).toString();
        Log.d("selected spinner item ", temp);
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
        if(date.getText().toString().isEmpty()){
            date.setHint("*Required Field");
            result = false;
        }
        return result;
    }
}
