package adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import constants.FragmentConst;
import constants.TableConst;
import fragments.BalanceSheetFragment;
import fragments.IncomeFragment;

/**
 * Created by Anurag on 18-03-2016.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if(position == 4){
            fragment = new BalanceSheetFragment();
        }
        else{
            fragment = IncomeFragment.newInstance(position);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return FragmentConst.INCOME_STATEMENT;
            case 1:
                return FragmentConst.ASSETS;
            case 2:
                return FragmentConst.LIABILITIES;
            case 3:
                return FragmentConst.EQUITY;
            case 4:
            default:
                return FragmentConst.BALANCE_SHEET;
        }
    }
}
