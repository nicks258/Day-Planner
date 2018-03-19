package com.npluslabs.dayplanner.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.npluslabs.dayplanner.Activities.MainActivity;
import com.npluslabs.dayplanner.Models.PlanerModel;
import com.npluslabs.dayplanner.Adapters.PlannerAdapter;
import com.npluslabs.dayplanner.R;
import com.npluslabs.dayplanner.SqliteHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListFragment extends Fragment {

    View view;
    SqliteHelper sqliteHelper;
    PlannerAdapter plannerAdapter;
    ListView listView;
    ArrayList<PlanerModel> planerModelArrayList;
    Bundle teamNameBundle,taskDetailBundle;
    String teamName;
    public TaskListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_task_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        setHasOptionsMenu(true);
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
        teamNameBundle = getArguments();
        if (teamNameBundle !=null) {
            teamName = teamNameBundle.getString("task_name");
            ((MainActivity) getActivity()).setActionBarTitle(teamName);
        }
        else {
            ((MainActivity) getActivity()).setActionBarTitle("DashBoard");
        }
        Logger.addLogAdapter(new AndroidLogAdapter());
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewtaskFragment selectedFragment = new AddNewtaskFragment();
                selectedFragment.setArguments(teamNameBundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content, selectedFragment);
//                fragmentTransaction.add(selectedFragment, "detail");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        sqliteHelper = new SqliteHelper(getActivity());
        listView = view.findViewById(R.id.list);
        planerModelArrayList = new ArrayList<PlanerModel>();
        PlanerModel planerModel;
        planerModelArrayList = sqliteHelper.getTaskList(teamName);
//        Logger.i("Name->>" + planerModelArrayList.get(0));
        plannerAdapter = new PlannerAdapter(getActivity(), planerModelArrayList);
        listView.setAdapter(plannerAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PlanerModel model = planerModelArrayList.get(i);
                taskDetailBundle  = new Bundle();
                String taskName,timeToComplete,efforts,impact,urgency,numberOfDays,probability,levelOfWork;
                taskName = model.getTaskName();
                timeToComplete = model.getTimeToComplete();
                efforts = model.getEffortsToComplete();
                impact = model.getImpact();
                urgency  = model.getUrgency();
                numberOfDays = model.getDeadLine();
                probability = model.getProbabilityofSuccess();
                levelOfWork = model.getWorkSequence();
                taskDetailBundle.putString("taskName",taskName);
                taskDetailBundle.putString("timeToComplete",timeToComplete);
                taskDetailBundle.putString("efforts",efforts);
                taskDetailBundle.putString("impact",impact);
                taskDetailBundle.putString("urgency",urgency);
                taskDetailBundle.putString("numberOfDays",numberOfDays);
                taskDetailBundle.putString("probability",probability);
                taskDetailBundle.putString("levelOfWork",levelOfWork);
                TaskDetailsFragment selectedFragment = new TaskDetailsFragment();
                selectedFragment.setArguments(taskDetailBundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content,selectedFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return false;
//    }


}