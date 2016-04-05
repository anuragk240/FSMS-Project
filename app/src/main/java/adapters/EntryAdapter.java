package adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
    Entry entry;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if(row == null || (row.getTag()) == null){
            Log.d("inside adapter if", String.valueOf(position));
            LayoutInflater inflater = LayoutInflater.from(activity);

            row = inflater.inflate(layoutResources, null);
            holder = new ViewHolder();

            holder.entryname = (TextView) row.findViewById(R.id.entrynameid);
            holder.entryvalue = (TextView) row.findViewById(R.id.entryvalueid);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
            Log.d("inside adapter else", String.valueOf(position));
        }
        holder.entry = getItem(position);
        holder.entryname.setText(holder.entry.getNameofentry());
        Log.d("inside adapter none", String.valueOf(position));
        if(holder.entry.getValue() == -1){
            holder.entryvalue.setVisibility(View.INVISIBLE);
        }
        else{
            holder.entryvalue.setText(String.valueOf(holder.entry.getValue()));
        }

        return row;
    }

    @Override
    public int getCount() {
        Log.d("Size of arraylist", String.valueOf(mydata.size()));
        return mydata.size();
    }
}


class ViewHolder{
    Entry entry;
    TextView entryname;
    TextView entryvalue;
}