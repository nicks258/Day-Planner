package com.npluslabs.dayplanner.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.npluslabs.dayplanner.Activities.MainActivity;
import com.npluslabs.dayplanner.Models.PlanerModel;
import com.npluslabs.dayplanner.R;
import com.npluslabs.dayplanner.SqliteHelper;
import com.npluslabs.dayplanner.Adapters.TeamNameAdapter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
    View view;
    SqliteHelper sqliteHelper;
    TeamNameAdapter plannerAdapter;
    Bundle taskNameBundle;
    ListView listView;
    ArrayList<PlanerModel> planerModelArrayList;
    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_dashboard, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("DashBoard");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);

        Logger.addLogAdapter(new AndroidLogAdapter());
        sqliteHelper = new SqliteHelper(getActivity());
        FloatingActionButton fab = view.findViewById(R.id.fab);
        sqliteHelper = new SqliteHelper(getActivity());
        listView = view.findViewById(R.id.list);
        Logger.i("Days-> " + getDateAgo());
         planerModelArrayList = new ArrayList<PlanerModel>();
        planerModelArrayList = sqliteHelper.getTeamName();
        plannerAdapter = new TeamNameAdapter(getActivity(),planerModelArrayList);
        listView.setAdapter(plannerAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PlanerModel model = planerModelArrayList.get(i) ;
                String taskName =  model.getTeamName();
                Logger.i("Name-> " + taskName);
                taskNameBundle = new Bundle();
                taskNameBundle.putString("task_name",taskName);
                NewTaskListFragment selectedFragment = new NewTaskListFragment();
                selectedFragment.setArguments(taskNameBundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content,selectedFragment);
//                fragmentTransaction.add(selectedFragment, "detail");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.team_name)
                        .content(R.string.name)
                        .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT)
                        .input("Enter Team Name","" , new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                                String teamName = input.toString();
                                sqliteHelper.insertTeamName(teamName);
//                                plannerAdapter.notifyAll();
//                                plannerAdapter.notify();
                                planerModelArrayList = new ArrayList<PlanerModel>();
                                PlanerModel planerModel;
                                planerModelArrayList = sqliteHelper.getTeamName();
                                plannerAdapter = new TeamNameAdapter(getActivity(),planerModelArrayList);
                                listView.setAdapter(plannerAdapter);
                                plannerAdapter.notifyDataSetChanged();
                            }
                        }).show();
//                AddTaskFragment selectedFragment = new AddTaskFragment();
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.content,selectedFragment);
//                fragmentTransaction.commit();
            }
        });
        return view;
    }
    public long getDateAgo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            String createdAt = "2018-02-28T21:00:00";
            Date date = sdf.parse(createdAt);
            Date now = new Date(System.currentTimeMillis());
            return getDateDiff(date, now, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

}
