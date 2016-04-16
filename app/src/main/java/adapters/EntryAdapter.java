package adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import constants.EntryTypeConst;
import constants.FragmentConst;
import data.Entry;
import fragments.IncomeFragment;
import fsms.my1stproject.com.financialstatement.R;
import fsms.my1stproject.com.financialstatement.TabActivity;

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
        ViewHolder holder = null;
        if(row == null || (row.getTag()) == null){
            LayoutInflater inflater = LayoutInflater.from(activity);

            row = inflater.inflate(layoutResources, null);
            holder = new ViewHolder();

            holder.entryname = (TextView) row.findViewById(R.id.entrynameid);
            holder.entryvalue = (TextView) row.findViewById(R.id.entryvalueid);
            holder.entrytext = (TextView) row.findViewById(R.id.entrytextid);

            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }
        holder.entry = getItem(position);

        refreshView(holder);

        applyFilters(holder);



        holder.entryname.setText(holder.entry.getNameofentry());
        if(holder.entry.getValue() == -1){
            holder.entryvalue.setVisibility(View.INVISIBLE);
        }
        else{
            holder.entryvalue.setText(String.valueOf(holder.entry.getValue()));
            Log.d("value adapter", holder.entry.getNameofentry()+" "+holder.entry.getValue());
        }

        return row;
    }

    private void applyFilters(ViewHolder holder) {
        switch(holder.entry.getNameofentry()){
            case EntryTypeConst.REVENUE:
            case EntryTypeConst.EXPENSE:
            case EntryTypeConst.CURRENT:
            case EntryTypeConst.FIXED:
            case EntryTypeConst.OTHER:
            case EntryTypeConst.EQUITY:
                holder.entryname.setTextAppearance(getContext(), R.style.bold_text);
                holder.entryvalue.setTextAppearance(getContext(), R.style.bold_text);
                break;
            case "Total :":
                holder.entryname.setPadding(40, 0, 0, 0);
                break;
            case "Net Income :":
                if (holder.entry.getValue() < 0){
                    holder.entryvalue.setTextColor(Color.RED);
                    holder.entryname.setTextColor(Color.RED);
                }
                else if (holder.entry.getValue() > 0) {
                    holder.entryvalue.setTextColor(Color.parseColor("#13be00"));  //Green
                    holder.entryname.setTextColor(Color.parseColor("#13be00"));   //green
                }
                else {
                    holder.entryvalue.setTextColor(Color.parseColor("#c4a700"));  //yellow
                    holder.entryname.setTextColor(Color.parseColor("#c4a700"));   //yellow
                }
                break;
            case "Total Assets :":
            case "Total Liabilities :":
            case "Total Equity :":
                holder.entryvalue.setTextColor(Color.parseColor("#c4a700"));  //yellow
                holder.entryname.setTextColor(Color.parseColor("#c4a700"));   //yellow
                break;
            default:
                holder.entryname.setPadding(20, 0, 0, 0);
        }

    }

    private void refreshView(ViewHolder holder) {
        holder.entryname.setTextAppearance(getContext(), R.style.normal_text);
        holder.entryvalue.setTextAppearance(getContext(), R.style.normal_text);
        holder.entryname.setPadding(0, 0, 0, 0);
        holder.entryvalue.setVisibility(View.VISIBLE);
    }
}


class ViewHolder{
    Entry entry;
    TextView entryname;
    TextView entryvalue;
    TextView entrytext;
}