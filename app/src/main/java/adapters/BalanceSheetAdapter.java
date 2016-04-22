package adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import constants.ColorConst;
import constants.EntryTypeConst;
import constants.MiscConst;
import data.BalanceSheetEntry;
import data.Entry;
import fsms.my1stproject.com.financialstatement.R;

/**
 * Created by Anurag Kumar on 14-Apr-16.
 */
public class BalanceSheetAdapter extends ArrayAdapter<BalanceSheetEntry> {
    private Activity activity;
    private int layoutResources;
    ArrayList<BalanceSheetEntry> mydata;

    public BalanceSheetAdapter(Activity act, int resource, ArrayList<BalanceSheetEntry> data) {
        super(act, resource, data);
        activity = act;
        layoutResources = resource;
        mydata = data;
        notifyDataSetChanged();
    }

    @Override
    public BalanceSheetEntry getItem(int position) {
        return mydata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getPosition(BalanceSheetEntry item) {
        return super.getPosition(item);
    }

    @Override
    public int getCount() {
        return mydata.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        BsheetViewHolder holder;
        if(row == null || (row.getTag()) == null){
            LayoutInflater inflater = LayoutInflater.from(activity);

            row = inflater.inflate(layoutResources, null);
            holder = new BsheetViewHolder();

            holder.assetname = (TextView) row.findViewById(R.id.assetnameid);
            holder.assetvalue = (TextView) row.findViewById(R.id.assetvalueid);
            holder.liability_equityname = (TextView) row.findViewById(R.id.liabilitynameid);
            holder.liability_equityvalue = (TextView) row.findViewById(R.id.liabilityvalueid);
            holder.centertext = (TextView) row.findViewById(R.id.bsheetcentertext);

            Typeface font = Typeface.createFromAsset(activity.getAssets(), MiscConst.FONT_TIMESNEWROMAN);
            holder.assetname.setTypeface(font);
            holder.assetvalue.setTypeface(font);
            holder.liability_equityname.setTypeface(font);
            holder.liability_equityvalue.setTypeface(font);
            holder.centertext.setTypeface(font);

            row.setTag(holder);
        }
        else{
            holder = (BsheetViewHolder) row.getTag();
        }
        holder.entry = getItem(position);

        refreshView(holder);

        Entry asset = holder.entry.getAsset();
        Entry l_equity = holder.entry.getLiability_Equity();
        String titles = holder.entry.getTitle();


        if (asset != null){
            holder.assetname.setText(asset.getNameofentry());
            if(asset.getValue() == -1){
                holder.assetvalue.setVisibility(View.INVISIBLE);
            }
            holder.assetvalue.setText(String.valueOf(asset.getValue()));
            applyFilters(asset, holder.assetname, holder.assetvalue);
        }
        if(l_equity != null){
            holder.liability_equityname.setText(l_equity.getNameofentry());
            if(l_equity.getValue() == -1){
                holder.liability_equityvalue.setVisibility(View.INVISIBLE);
            }
            holder.liability_equityvalue.setText(String.valueOf(l_equity.getValue()));
            applyFilters(l_equity, holder.liability_equityname, holder.liability_equityvalue);
        }
        if(titles != null){
            holder.centertext.setText(titles);
            holder.centertext.setTextSize(22);
            holder.centertext.setPadding(0, 0, 0, 0);
            switch (titles) {
                case MiscConst.NOT_BALANCED:
                    holder.centertext.setTextColor(Color.RED);
                    break;
                case MiscConst.BALANCED:
                    holder.centertext.setTextColor((Color.parseColor(ColorConst.GREEN)));
                    break;
            }
        }

        return row;
    }

    private void applyFilters(Entry e, TextView name, TextView value) {
        switch(e.getNameofentry()){
            case EntryTypeConst.CURRENT:
            case EntryTypeConst.FIXED:
            case EntryTypeConst.OTHER:
            case EntryTypeConst.EQUITY:
                name.setTextColor(Color.BLACK);
                value.setTextColor(Color.BLACK);
                break;
            case MiscConst.TOTAL:
                name.setPadding(40, 0, 0, 0);
                break;
            case MiscConst.HEADING_EQUITY:
            case MiscConst.HEADING_ASSETS:
            case MiscConst.HEADING_LIABILITY:
                name.setTextColor(Color.parseColor(ColorConst.TITLE_COLOR));
                name.setPadding(10, 0, 0, 0);
                break;
            case MiscConst.TOTAL_ASSETS:
            case MiscConst.TOTAL_E_AND_L:
                name.setTextColor(Color.parseColor(ColorConst.YELLOW));
                value.setTextColor(Color.parseColor(ColorConst.YELLOW));
                break;
            default:
                name.setPadding(20, 0, 0, 0);
        }

    }

    private void refreshView(BsheetViewHolder holder) {
        holder.assetname.setText("");
        holder.assetvalue.setText("");
        holder.liability_equityname.setText("");
        holder.liability_equityvalue.setText("");
        holder.centertext.setText("");
        holder.assetname.setPadding(0, 0, 0, 0);
        holder.liability_equityname.setPadding(0, 0, 0, 0);
        holder.assetvalue.setVisibility(View.VISIBLE);
        holder.liability_equityvalue.setVisibility(View.VISIBLE);

        holder.assetname.setTextColor(Color.parseColor(ColorConst.PRIMARY_DARK));
        holder.assetvalue.setTextColor(Color.parseColor(ColorConst.PRIMARY_DARK));
        holder.liability_equityname.setTextColor(Color.parseColor(ColorConst.PRIMARY_DARK));
        holder.liability_equityvalue.setTextColor(Color.parseColor(ColorConst.PRIMARY_DARK));
        holder.centertext.setTextColor(Color.parseColor(ColorConst.PRIMARY_DARK));
    }

}

class BsheetViewHolder {
    TextView centertext;
    TextView assetname;
    TextView assetvalue;
    TextView liability_equityname;
    TextView liability_equityvalue;

    BalanceSheetEntry entry;
}
