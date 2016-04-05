package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import constants.ConstantStrings;
import constants.FragmentConst;
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
        switch(position){
            case 0:
                fragment = new IncomeFragment();
                break;
            case 1:
                fragment = new IncomeFragment();
                break;
            case 2:
            default:
                fragment = new IncomeFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
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
            default:
                return FragmentConst.BALANCE_SHEET;
        }
    }
}
