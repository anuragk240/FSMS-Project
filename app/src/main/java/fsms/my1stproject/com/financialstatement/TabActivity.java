package fsms.my1stproject.com.financialstatement;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import adapters.TabsPagerAdapter;
import constants.RegistrationConst;
import constants.FragmentConst;

public class TabActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    public TabLayout tabLayout;
    private TabsPagerAdapter tabadapter;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText(FragmentConst.INCOME_STATEMENT));
        tabLayout.addTab(tabLayout.newTab().setText(FragmentConst.ASSETS));
        tabLayout.addTab(tabLayout.newTab().setText(FragmentConst.LIABILITIES));
        tabLayout.addTab(tabLayout.newTab().setText(FragmentConst.EQUITY));
        tabLayout.addTab(tabLayout.newTab().setText(FragmentConst.BALANCE_SHEET));

        tabLayout.setTabTextColors(getResources().getColorStateList(R.color.tabtextcolorselector));

        tabadapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewpager = (ViewPager) findViewById(R.id.viewpagerid);

        viewpager.setAdapter(tabadapter);     //link pager with adapter
        tabLayout.setupWithViewPager(viewpager);  //link tablayout with pager

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int current_tab = tabLayout.getSelectedTabPosition();
        switch(item.getItemId()){
            case R.id.action_settings:

                break;
            case R.id.add_button:
                if(current_tab == 4){
                    Toast.makeText(getApplicationContext(), "Not Applicable!", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent addentry = new Intent(TabActivity.this, AddEntryActivity.class);
                    addentry.putExtra(RegistrationConst.CURRENT_TAB, current_tab);
                    addentry.putExtra(RegistrationConst.ADD_UPDATE_KEY, "Add");
                    startActivity(addentry);
                }
                return true;
            case R.id.ref_button:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //on tab select listeners

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        // When the tab is selected, switch to the
        // corresponding page in the ViewPager.
        viewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
