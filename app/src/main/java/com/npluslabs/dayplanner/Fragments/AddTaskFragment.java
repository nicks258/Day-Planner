package com.npluslabs.dayplanner.Fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.npluslabs.dayplanner.Activities.MainActivity;
import com.npluslabs.dayplanner.Models.PlanerModel;
import com.npluslabs.dayplanner.R;
import com.npluslabs.dayplanner.SqliteHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends Fragment {
    View view;
    EditText taskName,timeToComplete,impact,urgency,probability_of_Success,work_Sequence,
    effortsToComplete;
    TextView deadLine;
    TextInputLayout taskNameInput,timeToCompleteInput,impactInput,urgencyInput,
            probability_of_SuccessInput,work_SequenceInput, effortsToCompleteInput;
    SqliteHelper sqliteHelper;
    Bundle teamNameBundle;
    public static final String HIGHESTSCORE = "hightestScores" ;
    public static final String TIME_TO_TAKE = "hours_to_take";
    public static final String DEADLINE_TIME = "deadline_time";
    SharedPreferences sharedPreferences;
    Button addTask;
    LocalDate start,end;
    String teamName,selectedDateTime;
    double probabilityIntValue;
    TextView teamNameTv;
    String selectedDate;
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
        taskNameInput = view.findViewById(R.id.input_task_name);
        teamNameBundle = getArguments();
        if (teamNameBundle!=null) {
            teamName = teamNameBundle.getString("task_name");
        }
        timeToComplete = view.findViewById(R.id.time_to_complete);
        timeToCompleteInput = view.findViewById(R.id.input_time_to_complete);

        effortsToComplete = view.findViewById(R.id.efforts_to_complete);
        effortsToComplete.addTextChangedListener(new MyTextWatcher(effortsToComplete));
        effortsToCompleteInput = view.findViewById(R.id.input_efforts_to_complete);

        impact = view.findViewById(R.id.imapact);
        impact.addTextChangedListener(new MyTextWatcher(impact));
        impactInput = view.findViewById(R.id.input_imapact);

        sharedPreferences = getActivity().getSharedPreferences(HIGHESTSCORE, Context.MODE_PRIVATE);

        urgency = view.findViewById(R.id.urgency);
        urgency.addTextChangedListener(new MyTextWatcher(urgency));
        urgencyInput = view.findViewById(R.id.input_urgency);

        teamNameTv = view.findViewById(R.id.team_name);
        teamNameTv.setText("Adding task in Team "+teamName);

        deadLine = view.findViewById(R.id.specific_dead_line);
//        deadLine.addTextChangedListener(new MyTextWatcher(deadLine));
//        deadLineInput = view.findViewById(R.id.input_specific_dead_line);
        myCalendar = new GregorianCalendar();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                Logger.i("Day->" + myCalendar.get(Calendar.DAY_OF_MONTH)  + "-> " +
                        myCalendar.get(Calendar.MONTH) + "-> " + myCalendar.get(Calendar.YEAR));
                String modifiedMonth = "",modifiedDate = "";
                if (myCalendar.get(Calendar.MONTH)<9)
                {
                    int selectedMonth = myCalendar.get(Calendar.MONTH) + 1;
                    modifiedMonth = "0" + selectedMonth;
                }
                else {
                    int selectedMonth = myCalendar.get(Calendar.MONTH) + 1;
                    modifiedMonth = String.valueOf(selectedMonth);
                }
                if (myCalendar.get(Calendar.DAY_OF_MONTH)<10)
                {
                    modifiedDate = "0" + myCalendar.get(Calendar.DAY_OF_MONTH);
                }
                else {
                    modifiedDate = String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH));
                }

                selectedDate = modifiedDate + "-" + modifiedMonth
                        +"-" +  myCalendar.get(Calendar.YEAR);
                selectedDateTime = myCalendar.get(Calendar.YEAR) + "-" + modifiedMonth + "-" +
                        modifiedDate + "T9:00:00";
//                        String createdAt = "2018-02-28T21:00:00";
                deadLine.setText(selectedDate);
//                end = LocalDate.parse(myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH)
//                        ,myCalendar.get(Calendar.DAY_OF_MONTH),);
//                updateLabel();
            }

        };
        deadLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity() ,date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        probability_of_Success = view.findViewById(R.id.success);
        probability_of_SuccessInput = view.findViewById(R.id.probability_input_success);
        work_Sequence = view.findViewById(R.id.work_sequence);
        work_Sequence.addTextChangedListener(new MyTextWatcher(work_Sequence));
        work_SequenceInput = view.findViewById(R.id.input_work_sequence);

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
                deadLineString = String.valueOf(getDateAgo(selectedDateTime));
                Logger.i("Add Task-> " + deadLineString);
                probabilityOfSuccessString = probability_of_Success.getText().toString();
                String temp = probabilityOfSuccessString;
                probabilityIntValue = Double.parseDouble(probabilityOfSuccessString)*10;
                probabilityOfSuccessString = String.valueOf(probabilityIntValue);
                workSequenceString = work_Sequence.getText().toString();

                if (validateOneToTen(effortsToComplete,effortsToCompleteInput,effortsToCompleteString))
                {
                    if (validateOneToTen(impact,impactInput,impactString)){
                        if (validateOneToTen(urgency,urgencyInput,urgencyString)){
                            {
                                if (validateOneToTen(work_Sequence,work_SequenceInput,workSequenceString))
                                {
                                    if (validateProbability(probability_of_Success,probability_of_SuccessInput,temp))
                                    {
                                        totalScoreCalulated();
                                    }
                                }
                            }

                        }
                    }
                }

            }
        });
        return view;
    }

    private void insertIntoDatabase(String normaliseTimeToComplete,String normaliseDeadline,Double finalScore,String levelOfWork){
//        String levelOfWork = String.valueOf(inverseLevelOfWork());
//        String normalisedTimeToComplete = normaliseTimeToComplete();
//        String normaliseTimeRemaining = normaliseDeadline();
//        String finalScore = totalScoreCalulated(normalisedTimeToComplete,normaliseTimeRemaining)
        Logger.i("Deadline-> " + deadLineString);
        PlanerModel planerModel = new PlanerModel();
        planerModel.setTaskName(taskNameString);
        planerModel.setTimeToComplete(timeToCompleteString);
        planerModel.setEffortsToComplete(effortsToCompleteString);
        planerModel.setImpact(impactString);
        planerModel.setUrgency(urgencyString);
        planerModel.setDeadLine(selectedDate);
        planerModel.setDaysLeft(deadLineString);
        planerModel.setProbabilityofSuccess(probabilityOfSuccessString);
        planerModel.setWorkSequence(levelOfWork);
        planerModel.setNormaliseTimeToComplete(normaliseTimeToComplete);
        planerModel.setNormaliseDeadline(normaliseDeadline);
        planerModel.setFinalScore(finalScore);
        planerModel.setTeamName(teamName);
        sqliteHelper.insertRecord(planerModel);
        sentToTaskList();
    }

    private void sentToTaskList(){
        TaskListFragment selectedFragment = new TaskListFragment();
        selectedFragment.setArguments(teamNameBundle);
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
//        Logger.i("");
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
                double temp = ((double)Integer.parseInt(planerModel.getDaysLeft())/highestNumberOfHours)*10;
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
    private boolean validateOneToTen(EditText editText, TextInputLayout layout, String value){
        if (value!=null) {
            int number = Integer.parseInt(value);
            if (number >= 0 && number <= 10) {
                layout.setErrorEnabled(false);
                return true;
            } else {
                layout.setError("Enter Valid Number");
                requestFocus(editText);
                return false;
            }
        }
        else {
            return false;
        }
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.efforts_to_complete:
                    validateOneToTen(effortsToComplete,effortsToCompleteInput,effortsToCompleteString);
                    break;
                case R.id.imapact:
                    validateOneToTen(impact,impactInput,impactString);
                    break;
                case R.id.urgency:
                    validateOneToTen(urgency,urgencyInput,urgencyString);
                    break;
                case R.id.work_sequence:
                    validateOneToTen(work_Sequence,work_SequenceInput,workSequenceString);
                    break;
            }

        }
    }
    private boolean validateProbability(EditText editText,TextInputLayout layout,String value)
    {
        double number = Double.parseDouble(value);
        if (number>=0 && number<=1)
        {
            layout.setErrorEnabled(false);
            return true;
        }
        else {
            layout.setError("Probability should be between 1 to 0");
            requestFocus(editText);
            return false;
        }
    }
    public long getDateAgo(String createdAt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
//            String createdAt = "2018-02-28T21:00:00";
            Date date = sdf.parse(createdAt);
            Date now = new Date(System.currentTimeMillis());
            return getDateDiff(now,date, TimeUnit.DAYS);
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
