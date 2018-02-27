package com.npluslabs.dayplanner;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends Fragment {
    View view;
    EditText taskName,timeToComplete,impact,urgency,deadLine,probability_of_Success,work_Sequence,
    effortsToComplete;
    SqliteHelper sqliteHelper;
    public static final String HIGHESTSCORE = "hightestScores" ;
    public static final String TIME_TO_TAKE = "hours_to_take";
    public static final String DEADLINE_TIME = "deadline_time";
    SharedPreferences sharedPreferences;
    Button addTask;
    LocalDate start,end;
    Calendar myCalendar;
    String taskNameString,timeToCompleteString,impactString,urgencyString,deadLineString
            ,probabilityOfSuccessString,workSequenceString,
            effortsToCompleteString;
    public AddTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_task, container, false);
        Logger.addLogAdapter(new AndroidLogAdapter());
        ((MainActivity) getActivity()).setActionBarTitle("Add Task");
        taskName = view.findViewById(R.id.task_name);
        timeToComplete = view.findViewById(R.id.time_to_complete);
        effortsToComplete = view.findViewById(R.id.efforts_to_complete);
        impact = view.findViewById(R.id.imapact);
        sharedPreferences = getActivity().getSharedPreferences(HIGHESTSCORE, Context.MODE_PRIVATE);
        urgency = view.findViewById(R.id.urgency);
        deadLine = view.findViewById(R.id.specific_dead_line);
        myCalendar = new GregorianCalendar();
//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
////                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
////                Logger.i("Day->" + myCalendar.get(Calendar.DAY_OF_MONTH)  + "-> " +
////                        myCalendar.get(Calendar.MONTH) + "-> " + myCalendar.get(Calendar.YEAR));
////                end = LocalDate.parse(myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH)
////                        ,myCalendar.get(Calendar.DAY_OF_MONTH),);
////                updateLabel();
//            }
//
//        };
//        deadLine.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DatePickerDialog(getActivity() ,date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });


        probability_of_Success = view.findViewById(R.id.success);
        work_Sequence = view.findViewById(R.id.work_sequence);
        sqliteHelper = new SqliteHelper(getActivity());
        addTask = view.findViewById(R.id.add_task);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskNameString = taskName.getText().toString();
                timeToCompleteString = timeToComplete.getText().toString();
                effortsToCompleteString  = effortsToComplete.getText().toString();
                impactString = impact.getText().toString();
                urgencyString = urgency.getText().toString();
                deadLineString = deadLine.getText().toString();
                probabilityOfSuccessString = probability_of_Success.getText().toString();
                workSequenceString = work_Sequence.getText().toString();
                totalScoreCalulated();
            }
        });
        return view;
    }

    private void insertIntoDatabase(String normaliseTimeToComplete,String normaliseDeadline,Double finalScore,String levelOfWork){
//        String levelOfWork = String.valueOf(inverseLevelOfWork());
//        String normalisedTimeToComplete = normaliseTimeToComplete();
//        String normaliseTimeRemaining = normaliseDeadline();
//        String finalScore = totalScoreCalulated(normalisedTimeToComplete,normaliseTimeRemaining)
        PlanerModel planerModel = new PlanerModel();
        planerModel.setTaskName(taskNameString);
        planerModel.setTimeToComplete(timeToCompleteString);
        planerModel.setEffortsToComplete(effortsToCompleteString);
        planerModel.setImpact(impactString);
        planerModel.setUrgency(urgencyString);
        planerModel.setDeadLine(deadLineString);
        planerModel.setProbabilityofSuccess(probabilityOfSuccessString);
        planerModel.setWorkSequence(levelOfWork);
        planerModel.setNormaliseTimeToComplete(normaliseTimeToComplete);
        planerModel.setNormaliseDeadline(normaliseDeadline);
        planerModel.setFinalScore(finalScore);
        sqliteHelper.insertRecord(planerModel);
        sentToDashboard();
    }

    private void sentToDashboard(){
        DashboardFragment selectedFragment = new DashboardFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content,selectedFragment);
        fragmentTransaction.commit();
    }

    private String normaliseTimeToComplete(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int highestNumberOfHours = sharedPreferences.getInt(TIME_TO_TAKE,0);
        int inputNumberOfHours = Integer.parseInt(timeToCompleteString);
        String normaliseTimeToComplete = "";
        if (highestNumberOfHours==0)
        {
            Logger.i("Its First!!");
            editor.putInt(TIME_TO_TAKE,inputNumberOfHours);
            editor.apply();
            normaliseTimeToComplete = "1.0";
            return  (normaliseTimeToComplete);
        }
        else if (highestNumberOfHours >= inputNumberOfHours){
            double normaliseHours = -(((double)inputNumberOfHours/highestNumberOfHours)*10) + 10 + 1;
            normaliseTimeToComplete = String.valueOf(normaliseHours) ;
            Logger.i("normaliseTimeToComplete-> "+ normaliseTimeToComplete);
            return  (normaliseTimeToComplete);
        }
        else
        {
            editor.putInt(TIME_TO_TAKE,inputNumberOfHours);
            editor.commit();
            highestNumberOfHours = inputNumberOfHours;
            int normaliseHours = -((inputNumberOfHours/highestNumberOfHours)*10) + 10 + 1;
            ArrayList<PlanerModel> contacts = new ArrayList<PlanerModel>();
            contacts =  sqliteHelper.getAllRecords();
            normaliseTimeToComplete = String.valueOf(normaliseHours);
            for (int i=0;i<contacts.size();i++)
            {
                double finalScore;
                PlanerModel planerModel = new PlanerModel();
                planerModel = contacts.get(i);
                Logger.i("Name-> "+ planerModel.getTaskName() +" Hours-> " + planerModel.getTimeToComplete());
                double temp = (( double) Integer.parseInt(planerModel.getTimeToComplete())/highestNumberOfHours)*10;
                double updateNormaliseTimeToComplete = 10 - temp + 1;
                finalScore =  (planerModel.getFinalScore() - Double.parseDouble(planerModel.getNormaliseTimeToComplete()) + updateNormaliseTimeToComplete);
                sqliteHelper.updateScore(String.valueOf(finalScore),planerModel.getTaskName());
                sqliteHelper.updateNormaliseTimeToComplete(String.valueOf(updateNormaliseTimeToComplete),planerModel.getTaskName());
            }
            return  (normaliseTimeToComplete);
        }
    }

    private String normaliseDeadline(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int highestNumberOfHours = sharedPreferences.getInt(DEADLINE_TIME,0);
        int inputNumberOfHours = Integer.parseInt(deadLineString);
        String normaliseTimeToComplete = "";
        if (highestNumberOfHours==0)
        {
            Logger.i("Its First!!");
            editor.putInt(DEADLINE_TIME,inputNumberOfHours);
            editor.apply();
            normaliseTimeToComplete = "1.0";
            return (normaliseTimeToComplete);
        }
        else if (highestNumberOfHours >= inputNumberOfHours){
            double normaliseHours = -(((double)inputNumberOfHours/highestNumberOfHours)*10) + 10 + 1;
            normaliseTimeToComplete = String.valueOf(normaliseHours) ;
            Logger.i("normaliseTimeToComplete-> "+ normaliseTimeToComplete);
            return (normaliseTimeToComplete);
        }
        else
        {
            editor.putInt(DEADLINE_TIME,inputNumberOfHours);
            editor.commit();
            highestNumberOfHours = inputNumberOfHours;
            double normaliseHours = -(((double) inputNumberOfHours/highestNumberOfHours)*10) + 10 + 1;
            ArrayList<PlanerModel> contacts = new ArrayList<PlanerModel>();
            contacts =  sqliteHelper.getAllRecords();
            normaliseTimeToComplete = String.valueOf(normaliseHours);
            for (int i=0;i<contacts.size();i++)
            {
                double finalScore;
                PlanerModel planerModel = new PlanerModel();
                planerModel = contacts.get(i);
//                Logger.i("Name-> "+ planerModel.getTaskName() +" Hours-> " + planerModel.getTimeToComplete());
                double temp = ((double)Integer.parseInt(planerModel.getTimeToComplete())/highestNumberOfHours)*10;
                double updateNormaliseTimeToComplete = 10 - temp + 1;
                finalScore =  (planerModel.getFinalScore() -  Double.parseDouble(planerModel.getNormaliseDeadline()) + updateNormaliseTimeToComplete);
                sqliteHelper.updateScore(String.valueOf(finalScore),planerModel.getTaskName());
//                Logger.i("Updated Normalise-> " + temp + "final->" + updateNormaliseTimeToComplete);
                sqliteHelper.updateNormaliseDeadline(String.valueOf(updateNormaliseTimeToComplete),planerModel.getTaskName());
            }
            return (normaliseTimeToComplete);
        }

    }
//    public static int getDaysDifference(Date fromDate,Date toDate)
//    {
//        if(fromDate==null||toDate==null)
//            return 0;
//
//        return (int)( (toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
//    }
    private int inverseLevelOfWork(){
        int workSequence = 10 - Integer.parseInt(workSequenceString);
        return workSequence;
    }
    private void totalScoreCalulated(){
        int workSequence = 10 - Integer.parseInt(workSequenceString);
        String normalisedTimeToComplete = normaliseTimeToComplete();
        String normaliseTimeRemaining = normaliseDeadline();
        double score = Double.parseDouble(effortsToCompleteString) + (3*Double.parseDouble(impactString))
        + Double.parseDouble(urgencyString) + (3*Double.parseDouble(probabilityOfSuccessString))
                + workSequence + Double.parseDouble(normalisedTimeToComplete) + (2*Double.parseDouble(normaliseTimeRemaining));
         score = (double)score/8;
        insertIntoDatabase(normalisedTimeToComplete,normaliseTimeRemaining,score,String.valueOf(workSequence));
    }
}
