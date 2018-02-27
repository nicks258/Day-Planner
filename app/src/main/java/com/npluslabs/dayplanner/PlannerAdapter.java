package com.npluslabs.dayplanner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

//import static android.R.attr.data;

/**
 * Created by Sumit on 22-Feb-18.
 */

public class PlannerAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<PlanerModel> data;
    private static LayoutInflater inflater=null;

//    public ImageLoaderader imageLoader;

    public PlannerAdapter(Activity a, ArrayList<PlanerModel> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final PlanerModel planerModel = data.get(position);
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView taskTitle = (TextView)vi.findViewById(R.id.title_of_task); // title
        TextView timeToComplete = (TextView)vi.findViewById(R.id.time_to_complete); // artist name
        TextView deadLine = (TextView)vi.findViewById(R.id.specific_dead_line); // duration
//        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        TextView levelOfWork = vi.findViewById(R.id.level_of_work);

//        HashMap<String, String> song = new HashMap<String, String>();
//        song = data.get(position);

        // Setting all values in listview
        taskTitle.setText(planerModel.getTaskName());
        timeToComplete.setText(planerModel.getTimeToComplete());
        int priority = position + 1;
        deadLine.setText(""+priority);
        levelOfWork.setText(planerModel.getWorkSequence());
//        imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}