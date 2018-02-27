package com.npluslabs.dayplanner;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
    View view;
    SqliteHelper sqliteHelper;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_dashboard, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("DashBoard");
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTaskFragment selectedFragment = new AddTaskFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content,selectedFragment);
                fragmentTransaction.commit();
            }
        });
        SqliteHelper sqliteHelper = new SqliteHelper(getActivity());
        ListView listView = view.findViewById(R.id.list);
        ArrayList<PlanerModel> planerModelArrayList = new ArrayList<PlanerModel>();
        PlanerModel planerModel;
        planerModelArrayList = sqliteHelper.getAllRecords();
        PlannerAdapter plannerAdapter = new PlannerAdapter(getActivity(),planerModelArrayList);
        listView.setAdapter(plannerAdapter);
        return view;
    }

}
