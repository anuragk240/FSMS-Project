package fsms.my1stproject.com.financialstatement;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import adapters.TabsPagerAdapter;
import constants.ConstantStrings;
import constants.FragmentConst;

public class TabActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    private TabLayout tabLayout;
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
        switch(item.getItemId()){
            case R.id.action_settings:

                break;
            case R.id.add_button:

                return true;
            case R.id.del_button:

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
