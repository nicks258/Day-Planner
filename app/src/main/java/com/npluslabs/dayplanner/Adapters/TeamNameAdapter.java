package com.npluslabs.dayplanner.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.npluslabs.dayplanner.Models.PlanerModel;
import com.npluslabs.dayplanner.R;

import java.util.ArrayList;

public class TeamNameAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<PlanerModel> data;
    private static LayoutInflater inflater=null;

//    public ImageLoaderader imageLoader;

    public TeamNameAdapter(Activity a, ArrayList<PlanerModel> d) {
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
            vi = inflater.inflate(R.layout.team_list_row, null);

        TextView teamTitle = (TextView)vi.findViewById(R.id.title_of_name); // title


//        HashMap<String, String> song = new HashMap<String, String>();
//        song = data.get(position);

        // Setting all values in listview
        teamTitle.setText(planerModel.getTeamName());
//        imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}