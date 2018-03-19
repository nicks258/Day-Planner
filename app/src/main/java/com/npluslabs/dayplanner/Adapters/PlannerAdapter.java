package com.npluslabs.dayplanner.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.system.ErrnoException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.npluslabs.dayplanner.Fragments.AddNewtaskFragment;
import com.npluslabs.dayplanner.Fragments.TaskDetailsFragment;
import com.npluslabs.dayplanner.Models.PlanerModel;
import com.npluslabs.dayplanner.R;
import com.npluslabs.dayplanner.SqliteHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


//import static android.R.attr.data;

/**
 * Created by Sumit on 22-Feb-18.
 */

public class PlannerAdapter extends BaseAdapter {
    String taskDependentOnMe="";
    private Activity activity;
    private ArrayList<PlanerModel> data;
    SqliteHelper sqliteHelper;
    private static LayoutInflater inflater=null;
    private final ViewBinderHelper binderHelper;
//    public ImageLoaderader imageLoader;

    public PlannerAdapter(Activity a, ArrayList<PlanerModel> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binderHelper = new ViewBinderHelper();
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

    public View getView(int position, View convertView, final ViewGroup parent) {
        final PlanerModel planerModel = data.get(position);
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.plan_list_row, null);
        SwipeRevealLayout swipeLayout = (SwipeRevealLayout) vi.findViewById(R.id.swipe_layout);
        View  deleteView = vi.findViewById(R.id.delete_layout);
        View thumbnail = vi.findViewById(R.id.thumbnail);
        ImageView editImageView = vi.findViewById(R.id.edit);
        TextView taskTitle = (TextView)vi.findViewById(R.id.title_of_task); // title
        TextView timeToComplete = (TextView)vi.findViewById(R.id.time_to_complete); // artist name
        TextView deadLine = (TextView)vi.findViewById(R.id.specific_dead_line); // duration

//        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
//        TextView levelOfWork = vi.findViewById(R.id.level_of_work);

//        HashMap<String, String> song = new HashMap<String, String>();
//        song = data.get(position);
//         String item = data.get(position);
//        if (item != null)

        {
            binderHelper.bind(swipeLayout, planerModel.getTaskName());
            editImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity,"Edit " + planerModel.getTaskName(),Toast.LENGTH_LONG).show();
                    Bundle taskDetailBundle  = new Bundle();
                    String taskName,timeToComplete,importance,dFactor,urgency,probability,
                            workLevel,taskDescription,dependentTask,teamName;
                    taskName = planerModel.getTaskName();
                    timeToComplete = planerModel.getTimeToComplete();
                    importance = planerModel.getImportance();
                    dFactor = planerModel.getdFactor();
                    teamName = planerModel.getTeamName();
                    workLevel = planerModel.getWorkSequence();
                    taskDescription = planerModel.getTaskDescription();
                    dependentTask = planerModel.getDependentTask();
                    urgency  = planerModel.getUrgency();
                    probability = planerModel.getProbabilityofSuccess();
                    taskDetailBundle.putString("taskName",taskName);
                    taskDetailBundle.putString("timeToComplete",timeToComplete);
                    taskDetailBundle.putString("efforts",importance);
                    taskDetailBundle.putString("impact",dFactor);
                    taskDetailBundle.putString("urgency",urgency);

                    taskDetailBundle.putString("workLevel",workLevel);
                    taskDetailBundle.putString("taskDescription",taskDescription);
                    taskDetailBundle.putString("dependentTask",dependentTask);
                    taskDetailBundle.putString("numberOfDays","");
                    taskDetailBundle.putString("probability",probability);
                    taskDetailBundle.putString("levelOfWork","");
                    taskDetailBundle.putString("teamName",teamName);
                    AddNewtaskFragment selectedFragment = new AddNewtaskFragment();
                    selectedFragment.setArguments(taskDetailBundle);
                    FragmentTransaction fragmentTransaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content,selectedFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sqliteHelper = new SqliteHelper(activity);
                    List<PlanerModel> planerModelList = sqliteHelper.taskDependentOnMe(planerModel.getTaskName());

                    for (int i=0;i<planerModelList.size();i++)
                    {

                        PlanerModel model = planerModelList.get(i);
                        Log.i("_>>>>",model.getTaskName());
                        if (i == 0) {
                            taskDependentOnMe = (model.getTaskName());
                        }
                        else {
                            taskDependentOnMe= taskDependentOnMe + "," + (model.getTaskName());
                        }
                    }
                    Bundle taskDetailBundle  = new Bundle();
                    String taskName,timeToComplete,importance,dFactor,urgency,probability,
                            workLevel,taskDescription,dependentTask;
                    taskName = planerModel.getTaskName();
                    timeToComplete = planerModel.getTimeToComplete();
                    importance = planerModel.getImportance();
                    dFactor = planerModel.getdFactor();
                    workLevel = planerModel.getWorkSequence();
                    taskDescription = planerModel.getTaskDescription();
                    dependentTask = planerModel.getDependentTask();
                    urgency  = planerModel.getUrgency();
                    probability = planerModel.getProbabilityofSuccess();
                    taskDetailBundle.putString("taskName",taskName);
                    taskDetailBundle.putString("timeToComplete",timeToComplete);
                    taskDetailBundle.putString("efforts",importance);
                    taskDetailBundle.putString("impact",dFactor);
                    taskDetailBundle.putString("urgency",urgency);
                    taskDetailBundle.putString("workLevel",workLevel);
                    taskDetailBundle.putString("taskDescription",taskDescription);
                    taskDetailBundle.putString("taskDependentOnMe", taskDependentOnMe);
                    taskDetailBundle.putString("dependentTask",dependentTask);
                    taskDetailBundle.putString("numberOfDays","");
                    taskDetailBundle.putString("probability",probability);
                    taskDetailBundle.putString("levelOfWork","");
                    TaskDetailsFragment selectedFragment = new TaskDetailsFragment();
                    selectedFragment.setArguments(taskDetailBundle);
                    FragmentTransaction fragmentTransaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content,selectedFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }

        // Setting all values in listview
        taskTitle.setText(planerModel.getTaskName());
        timeToComplete.setText(""+planerModel.getFinalScore());
        int priority = position + 1;
        deadLine.setText(""+priority);
//        levelOfWork.setText(planerModel.getWorkSequence());
//        imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in {@link android.app.Activity#onRestoreInstanceState(Bundle)}
     */
    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }
}