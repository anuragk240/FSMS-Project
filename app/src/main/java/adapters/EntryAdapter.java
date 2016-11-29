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
import data.Entry;
import fsms.my1stproject.com.financialstatement.R;

/**
 * Created by Anurag on 25-03-2016.
 */
public class EntryAdapter extends ArrayAdapter<Entry> {
    Activity activity;
    int layoutResources;
    ArrayList<Entry> mydata = new ArrayList<>();


    public EntryAdapter(Activity act, int resource, ArrayList<Entry> data) {
        super(act, resource, data);
        activity = act;
        layoutResources = resource;
        mydata = data;
        notifyDataSetChanged();
    }

    @Override
    public Entry getItem(int position) {
        return mydata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getPosition(Entry item) {
        return super.getPosition(item);
    }

    @Override
    public int getCount() {
        return mydata.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        if(row == null || (row.getTag()) == null){
            LayoutInflater inflater = LayoutInflater.from(activity);

            row = inflater.inflate(layoutResources, null);
            holder = new ViewHolder();

            holder.entryname = (TextView) row.findViewById(R.id.entrynameid);
            holder.entryvalue = (TextView) row.findViewById(R.id.entryvalueid);
            //holder.entrytext = (TextView) row.findViewById(R.id.entrytextid);

            Typeface fonts = Typeface.createFromAsset(activity.getAssets(), MiscConst.FONT_TIMESNEWROMAN);
            holder.entryname.setTypeface(fonts);
            holder.entryvalue.setTypeface(fonts);
            //holder.entrytext.setTypeface(fonts);

            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }
        holder.entry = getItem(position);

        refreshView(holder);

        applyFilters(holder.entryname, holder.entryvalue, holder.entry);



        holder.entryname.setText(holder.entry.getNameofentry());
        if(holder.entry.getValue() == -1){
            holder.entryvalue.setVisibility(View.INVISIBLE);
        }
        else{
            holder.entryvalue.setText(String.valueOf(holder.entry.getValue()));
        }

        return row;
    }

    private void applyFilters(TextView entryname, TextView entryvalue, Entry entry) {
        switch(entry.getNameofentry()){
            case EntryTypeConst.REVENUE:
            case EntryTypeConst.EXPENSE:
            case EntryTypeConst.CURRENT:
            case EntryTypeConst.FIXED:
            case EntryTypeConst.OTHER:
            case EntryTypeConst.EQUITY:
                entryname.setTextColor(Color.BLACK);
                entryvalue.setTextColor(Color.BLACK);
                break;
            case MiscConst.TOTAL:
                entryname.setPadding(40, 0, 0, 0);
                break;
            case MiscConst.NET_INCOME:
                if (entry.getValue() < 0){
                    entryvalue.setTextColor(Color.RED);
                    entryname.setTextColor(Color.RED);
                }
                else if (entry.getValue() > 0) {
                    entryvalue.setTextColor(Color.parseColor(ColorConst.GREEN));  //Green
                    entryname.setTextColor(Color.parseColor(ColorConst.GREEN));   //green
                }
                else {
                    entryvalue.setTextColor(Color.parseColor(ColorConst.YELLOW));  //yellow
                    entryname.setTextColor(Color.parseColor(ColorConst.YELLOW));   //yellow
                }
                break;
            case MiscConst.TOTAL_ASSETS:
            case MiscConst.TOTAL_LIABILITIES:
            case MiscConst.TOTAL_EQUITY:
                entryvalue.setTextColor(Color.parseColor(ColorConst.YELLOW));  //yellow
                entryname.setTextColor(Color.parseColor(ColorConst.YELLOW));   //yellow
                break;
            default:
                entryname.setPadding(20, 0, 0, 0);
        }

    }

    private void refreshView(ViewHolder holder) {
        holder.entryname.setTextColor(Color.parseColor(ColorConst.PRIMARY_DARK));
        holder.entryvalue.setTextColor(Color.parseColor(ColorConst.PRIMARY_DARK));
        holder.entryname.setPadding(0, 0, 0, 0);
        holder.entryvalue.setVisibility(View.VISIBLE);

    }
}


class ViewHolder{
    Entry entry;
    TextView entryname;
    TextView entryvalue;
}