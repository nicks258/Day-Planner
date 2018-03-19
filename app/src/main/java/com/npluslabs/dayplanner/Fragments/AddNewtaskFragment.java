package com.npluslabs.dayplanner.Fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.npluslabs.dayplanner.Activities.MainActivity;
import com.npluslabs.dayplanner.Models.PlanerModel;
import com.npluslabs.dayplanner.R;
import com.npluslabs.dayplanner.SqliteHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewtaskFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    EditText taskName,timeToComplete,urgency,probability_of_Success,importance, dFactorET,workLevel,
            taskDescription;
//    TextView deadLine;
    TextInputLayout taskNameInput,timeToCompleteInput,urgencyInput, dFactorInput,
            probability_of_SuccessInput,inputImportance,input_work_sequence,input_work_level,input_task_desc;
    SqliteHelper sqliteHelper;
    Bundle teamNameBundle;
    double probabilityAmount;
    ImageView importanceOfWorkInfo;
    public static final String HIGHESTSCORE = "hightestScores" ;
    public static final String TIME_TO_TAKE = "hours_to_take";
    public static final String DEADLINE_TIME = "deadline_time";
    SharedPreferences sharedPreferences;
    Button addTask,dependentTaskList;
    LocalDate start,end;
    boolean isTaskNew = false;
    String teamName,selectedDateTime;
    double probabilityIntValue;
    TextView teamNameTv;
    String selectedDate,dependentTask="No Dependent Task";
    Calendar myCalendar;
    Bundle taskInformation = null;
    String taskNameString,timeToCompleteString,impactString,urgencyString,deadLineString
            ,probabilityOfSuccessString, dFactorString,importanceString,
            effortsToCompleteString,workLevelString;
    public AddNewtaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_add_newtask, container, false);
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
        Logger.addLogAdapter(new AndroidLogAdapter());
        ((MainActivity) getActivity()).setActionBarTitle("Add Task");
        taskInformation = getArguments();
        taskName = view.findViewById(R.id.task_name);
        sqliteHelper = new SqliteHelper(getActivity());
        taskNameInput = view.findViewById(R.id.input_task_name);
        teamNameBundle = getArguments();
        if (teamNameBundle!=null) {
            teamName = teamNameBundle.getString("task_name");
            isTaskNew = teamNameBundle.getBoolean("isNewTask");
        }
        timeToComplete = view.findViewById(R.id.time_to_complete);
        timeToCompleteInput = view.findViewById(R.id.input_time_to_complete);

        taskDescription = view.findViewById(R.id.task_desc);
        input_task_desc = view.findViewById(R.id.input_task_desc);

        importanceOfWorkInfo = view.findViewById(R.id.importance_of_work_info);
        importanceOfWorkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImportanceOfAllTask();
            }
        });

        sharedPreferences = getActivity().getSharedPreferences(HIGHESTSCORE, Context.MODE_PRIVATE);
        dependentTaskList = view.findViewById(R.id.dependent_tasklist);
        dependentTaskList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               getAllTaskList(workLevel.getText().toString());
            }
        });
        urgency = view.findViewById(R.id.urgency);
        urgency.addTextChangedListener(new AddNewtaskFragment.MyTextWatcher1(urgency));
        urgencyInput = view.findViewById(R.id.input_urgency);
        importance = view.findViewById(R.id.importance);
        inputImportance = view.findViewById(R.id.input_importance);

        workLevel = view.findViewById(R.id.work_level);
        input_work_level = view.findViewById(R.id.input_work_level);

        dFactorET = view.findViewById(R.id.work_sequence);
        dFactorInput = view.findViewById(R.id.input_work_sequence);


        probability_of_Success = view.findViewById(R.id.success);
        probability_of_SuccessInput = view.findViewById(R.id.probability_input_success);
        if (taskInformation!=null && !isTaskNew)
        {
            isTaskNew = false;
            Toast.makeText(getActivity(),"Editing task",Toast.LENGTH_LONG).show();
            ((MainActivity) getActivity()).setActionBarTitle(taskInformation.getString("taskName"));
            probabilityAmount = Double.parseDouble(taskInformation.getString("probability"));
            probabilityAmount = probabilityAmount/10;
//            levelOfWorkAmount = Double.parseDouble(taskInformation.getString("levelOfWork"));
//            levelOfWorkAmount = 10 - levelOfWorkAmount;
            taskName.setText(taskInformation.getString("taskName"));
            taskDescription.setText(taskInformation.getString("taskDescription"));
//            taskDepend.setText("Task Dependency : "+taskInformation.getString("dependentTask"));
            timeToComplete.setText(taskInformation.getString("timeToComplete"));
            importance.setText(taskInformation.getString("efforts"));
            dFactorET.setText(taskInformation.getString("impact"));
            urgency.setText(taskInformation.getString("urgency"));
            teamName = taskInformation.getString("teamName");
//            numberOfDays.setText("Deadline Date : "+"NA");
            probability_of_Success.setText(""+probabilityAmount);
            workLevel.setText(taskInformation.getString("workLevel") );

        }
        else {
            isTaskNew  = true;
            Log.i("Its Nll","Its Null");
        }
        teamNameTv = view.findViewById(R.id.team_name);
        teamNameTv.setText("Adding task in Team "+teamName);

        addTask = view.findViewById(R.id.add_task);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskNameString = taskName.getText().toString();
                timeToCompleteString = timeToComplete.getText().toString();
                importanceString = importance.getText().toString();
                urgencyString = urgency.getText().toString();
//                deadLineString = String.valueOf(getDateAgo(selectedDateTime));
//                Logger.i("Add Task-> " + deadLineString);
                probabilityOfSuccessString = probability_of_Success.getText().toString();
                String temp = probabilityOfSuccessString;
                probabilityIntValue = Double.parseDouble(probabilityOfSuccessString)*10;
                probabilityOfSuccessString = String.valueOf(probabilityIntValue);
                dFactorString = dFactorET.getText().toString();
                {
                    if (validateOneToTen(importance,inputImportance,importanceString)){
                        if (validateOneToTen(urgency,urgencyInput,urgencyString)){
                            {
                                if (validateProbability(dFactorET, dFactorInput, dFactorString))
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

    private void insertIntoDatabase(String normaliseTimeToComplete,Double finalScore){
//        String levelOfWork = String.valueOf(inverseLevelOfWork());
//        String normalisedTimeToComplete = normaliseTimeToComplete();
//        String normaliseTimeRemaining = normaliseDeadline();
//        String finalScore = totalScoreCalulated(normalisedTimeToComplete,normaliseTimeRemaining)
        Logger.i("Deadline-> " + deadLineString);
        if (Integer.parseInt(workLevel.getText().toString())>1 && dependentTask.equals("No Dependent Task"))
        {
            new MaterialDialog.Builder(getActivity())
                    .title("Dependent Task")
                    .content("Select depend task first")
                    .positiveText("Okay")
                    .show();
        }
        else
            {
            PlanerModel planerModel = new PlanerModel();
            planerModel.setTaskName(taskNameString);
            planerModel.setTimeToComplete(timeToCompleteString);
            planerModel.setUrgency(urgencyString);
            planerModel.setImportance(importanceString);
            planerModel.setdFactor(dFactorString);
            planerModel.setProbabilityofSuccess(probabilityOfSuccessString);
            planerModel.setNormaliseTimeToComplete(normaliseTimeToComplete);
            planerModel.setFinalScore(finalScore);
            planerModel.setTeamName(teamName);
            planerModel.setTaskDescription("" + taskDescription.getText().toString());
            planerModel.setWorkSequence("" + workLevel.getText().toString());
            planerModel.setDependentTask(dependentTask);

            if (isTaskNew) {
                sqliteHelper.insertNewRecord(planerModel);
                sentToTaskList();
            }
            else {
                sqliteHelper.updateTaskDetails(planerModel, taskNameString);
                sentToTaskList();
                Logger.i("Table Updated");
            }

        }
    }

    private void sentToTaskList(){
        NewTaskListFragment selectedFragment = new NewTaskListFragment();
        teamNameBundle.putString("task_name",teamName);
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

    private int inverseLevelOfWork(){
        int workSequence = 10 - Integer.parseInt(dFactorString);
        return workSequence;
    }
    private void totalScoreCalulated(){
        String normalisedTimeToComplete = normaliseTimeToComplete();
        double score = (((Double.parseDouble(importanceString)) + (Double.parseDouble(dFactorString)
                *Double.parseDouble(urgencyString))) / Double.parseDouble(normalisedTimeToComplete))
                * Double.parseDouble(probabilityOfSuccessString);
        insertIntoDatabase(normalisedTimeToComplete,score);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class MyTextWatcher1 implements TextWatcher {

        private View view;

        private MyTextWatcher1(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.work_level:
                    getAllTaskList(workLevel.getText().toString());
                    break;
                case R.id.efforts_to_complete:
                    validateOneToTen(importance,inputImportance,importanceString);
                    break;
                case R.id.urgency:
                    validateOneToTen(urgency,urgencyInput,urgencyString);
                    break;
                case R.id.work_sequence:
                    validateProbability(dFactorET, dFactorInput, dFactorString);
                    break;
                case R.id.probability:
                    validateProbability(probability_of_Success,probability_of_SuccessInput,probabilityOfSuccessString);
                    break;
            }

        }
    }

    private void showImportanceOfAllTask(){
        List<PlanerModel> taskList = new ArrayList<>();
        final List<String> taskNameList = new ArrayList<String>();
        taskList = sqliteHelper.getTaskName();

        for (int i=0;i<taskList.size();i++)
        {
            PlanerModel planerModel;
            planerModel = taskList.get(i);
            StringBuilder text = new StringBuilder();
            text.append(planerModel.getTaskName() +"-" + planerModel.getTeamName() + ":" + planerModel.getTaskDescription())
                    .append("\n").append("Task Importance :").append(planerModel.getImportance());
            Logger.i("Text->>" + text.toString());
            taskNameList.add(text.toString());
        }
        new MaterialDialog.Builder(getActivity())
                .title("Importance of all tasks ")
                .items(taskNameList)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        Toast.makeText(getContext(),"Selected " +taskNameList.get(position),Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    private void getAllTaskList(String s) {
        List<PlanerModel> taskList = new ArrayList<>();
        int workLevelNeeded = Integer.parseInt(s) - 1;
        String workLevelNeededString = String.valueOf(workLevelNeeded);
        final List<String> taskNameList = new ArrayList<String>();
        taskList = sqliteHelper.getWorkLeveltNew(workLevelNeededString);
        for (int i=0;i<taskList.size();i++)
        {
            PlanerModel planerModel;
            planerModel = taskList.get(i);
            taskNameList.add(planerModel.getTaskName() +"-" +planerModel.getTeamName());
        }
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dependent_task)
                .items(taskNameList)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        dependentTask = taskNameList.get(position);
                        Toast.makeText(getContext(),"Selected " +taskNameList.get(position),Toast.LENGTH_LONG).show();
                    }
                })
                .show();
        Logger.i("Work level->" + s);
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
            layout.setError("Should be between 1 to 0");
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
