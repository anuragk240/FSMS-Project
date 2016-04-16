package adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import constants.EntryTypeConst;
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
        BsheetViewHolder holder = null;
        if(row == null || (row.getTag()) == null){
            LayoutInflater inflater = LayoutInflater.from(activity);

            row = inflater.inflate(layoutResources, null);
            holder = new BsheetViewHolder();

            holder.assetname = (TextView) row.findViewById(R.id.assetnameid);
            holder.assetvalue = (TextView) row.findViewById(R.id.assetvalueid);
            holder.liability_equityname = (TextView) row.findViewById(R.id.liabilitynameid);
            holder.liability_equityvalue = (TextView) row.findViewById(R.id.liabilityvalueid);
            holder.centertext = (TextView) row.findViewById(R.id.bsheetcentertext);

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
            applyFilters(asset, holder.assetname, holder.assetvalue, holder.centertext);
        }
        if(l_equity != null){
            holder.liability_equityname.setText(l_equity.getNameofentry());
            if(l_equity.getValue() == -1){
                holder.liability_equityvalue.setVisibility(View.INVISIBLE);
            }
            holder.liability_equityvalue.setText(String.valueOf(l_equity.getValue()));
            applyFilters(l_equity, holder.liability_equityname, holder.liability_equityvalue, holder.centertext);
        }
        if(titles != null){
            holder.centertext.setText(titles);
            holder.centertext.setTextSize(22);
            holder.centertext.setPadding(0, 0, 0, 0);
            switch (titles) {
                case "Not Balanced!!":
                    holder.centertext.setTextColor(Color.RED);
                    break;
                case "Balanced!!":
                    holder.centertext.setTextColor((Color.parseColor("#13be00")));
                    break;
            }
        }

        return row;
    }

    private void applyFilters(Entry e, TextView name, TextView value, TextView center) {
        switch(e.getNameofentry()){
            case EntryTypeConst.CURRENT:
            case EntryTypeConst.FIXED:
            case EntryTypeConst.OTHER:
            case EntryTypeConst.EQUITY:
                name.setTextAppearance(getContext(), R.style.bold_text);
                value.setTextAppearance(getContext(), R.style.bold_text);
                break;
            case "Total :":
                name.setPadding(40, 0, 0, 0);
                break;
            case "USER'S EQUITY":
            case "ASSETS":
            case "LIABILITIES":
                name.setTextAppearance(getContext(), R.style.bold_text);
                name.setTextColor(getContext().getResources().getColor(R.color.table_title_color));
                name.setPadding(10, 0, 0, 0);
                break;
            case "Total Assets :":
            case "Total User's Equity and Liabilities :":
                name.setTextColor(Color.parseColor("#c4a700"));
                value.setTextColor(Color.parseColor("#c4a700"));
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
        holder.assetname.setTextAppearance(getContext(), R.style.normal_text);
        holder.assetvalue.setTextAppearance(getContext(), R.style.normal_text);
        holder.liability_equityname.setTextAppearance(getContext(), R.style.normal_text);
        holder.liability_equityvalue.setTextAppearance(getContext(), R.style.normal_text);
        holder.centertext.setTextAppearance(getContext(), R.style.normal_text);
        holder.assetname.setPadding(0, 0, 0, 0);
        holder.liability_equityname.setPadding(0, 0, 0, 0);
        holder.assetvalue.setVisibility(View.VISIBLE);
        holder.liability_equityvalue.setVisibility(View.VISIBLE);
        holder.assetname.setTextSize(18);
        holder.assetvalue.setTextSize(18);
        holder.liability_equityname.setTextSize(18);
        holder.liability_equityvalue.setTextSize(18);
        holder.centertext.setTextSize(18);
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
