package com.npluslabs.dayplanner.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.npluslabs.dayplanner.Activities.MainActivity;
import com.npluslabs.dayplanner.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskDetailsFragment extends Fragment {

    View view;
    public TaskDetailsFragment() {
        // Required empty public constructor
    }
    TextView taskName,timeToComplete,efforts,impact,urgency,numberOfDays,probability,leveOfWork,taskDesc,
            taskDepend,work_level,task_depend_on_me;
    Bundle taskInformation;
    double probabilityAmount,levelOfWorkAmount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_task, container, false);
        taskInformation = getArguments();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        setHasOptionsMenu(true);
        Logger.addLogAdapter(new AndroidLogAdapter());
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                Log.i("Sumt", "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("Suduh", "onKey Back listener is working!!!");
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        } );
        taskName = view.findViewById(R.id.task_name);
        timeToComplete  = view.findViewById(R.id.time_to_complete);
        efforts  = view.findViewById(R.id.efforts_to_complete);
        impact  = view.findViewById(R.id.imapact);
        task_depend_on_me = view.findViewById(R.id.task_depend_on_me);
        urgency  = view.findViewById(R.id.urgency);
        numberOfDays  = view.findViewById(R.id.deadline);
        taskDepend = view.findViewById(R.id.task_depend);
        taskDesc = view.findViewById(R.id.task_desc);
        probability  = view.findViewById(R.id.probability);
        leveOfWork  = view.findViewById(R.id.work_sequence);


        if (taskInformation!=null)
        {
            ((MainActivity) getActivity()).setActionBarTitle(taskInformation.getString("taskName"));
            probabilityAmount = Double.parseDouble(taskInformation.getString("probability"));
            probabilityAmount = probabilityAmount/10;
//            levelOfWorkAmount = Double.parseDouble(taskInformation.getString("levelOfWork"));
//            levelOfWorkAmount = 10 - levelOfWorkAmount;
            String taskDependentOnMe = taskInformation.getString("taskDependentOnMe");
            taskName.setText("Task name : "+taskInformation.getString("taskName"));
            taskDesc.setText("Task Description : "+taskInformation.getString("taskDescription"));
            taskDepend.setText(""+getResources().getString(R.string.dependent_task)+": "+taskInformation.getString("dependentTask"));
            timeToComplete.setText("Time to complete :"+taskInformation.getString("timeToComplete"));
            efforts.setText("Importance : "+taskInformation.getString("efforts"));
            impact.setText("D : "+taskInformation.getString("impact"));
            urgency.setText("Urgency : "+taskInformation.getString("urgency"));
            numberOfDays.setText("Deadline Date : "+"NA");
            probability.setText("Proability of success : "+probabilityAmount);
//            Logger.i("taskDependentOnMe->>" + taskDependentOnMe);
            leveOfWork.setText("Level of work : "+taskInformation.getString("workLevel") );
            task_depend_on_me.setText(""+getResources().getString(R.string.task_dependent_on)+": "+ taskDependentOnMe);

        }
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if(getActivity().getSupportFragmentManager().getBackStackEntryCount()>0)
                    getActivity().getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
