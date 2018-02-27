package com.npluslabs.dayplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Sumit on 22-Feb-18.
 */

public class SqliteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PlanerDatabase.db";
    public static final String TABLE_NAME = "TaskList";
    public static final String TABLE_TRANSACTION = "KHATA_TRANSACTION";
    public static final String COLUMN_ID = "ID";
    public static final String TASK_NAME = "TASK_NAME";
    public static final String TIME_TO_COMPLETE = "TIME_TO_COMPLETE";
    public static final String EFFORTS_TO_COMPLETE = "EFFORTS_TO_COMPLETE";
    public static final String IMPACT = "IMPACT";
    public static final String URGENCY = "URGENCY";
    public static final String DEADLINE_DATE = "DEADLINE_DATE";
    public static final String PROBABILITY_OF_SUCCESS = "PROBABILITY_OF_SUCCESS";
    public static final String WORK_SEQUENCE = "WORK_SEQUENCE";
    public static final String TIME_TO_COMPLETE_NORMALISE = "TIME_TO_COMPLETE_NORMALISE";
    public static final String DEADLINE_NORMALISE = "DEADLINE_NORMALISE";
    public static final String FINAL_SCORE = "FINAL_SCORE";
    public static final String COLUMN_TRANSACTION_DATE_TIME = "TRANSFER_DATE_TIME";
    public static final String COLUMN_TRANSACTION_CATEGORY = "TRANSACTION_CATEGORY";
    public static final String COLUMN_TRANSACTION_DESCRIPTION = "TRANSECTION_DESCRIPTION";
    public static final String COLUMN_SYNC = "IS_SYNCED";
    public static final String COLUMN_SCAN = "IS_SCAN";
    private SQLiteDatabase database;

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        super(context, File.separator + "sdcard"
//                + File.separator + "Android" + File.separator + "data" + File.separator
//                + "com.io.startuplabs.khata_seller"
//                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_creation_sql = "create table " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TASK_NAME + " VARCHAR, " + IMPACT + " VARCHAR, " + TIME_TO_COMPLETE +
                " VARCHAR ," + EFFORTS_TO_COMPLETE +" VARCHAR,"+ DEADLINE_DATE+ " VARCHAR,"
                + TIME_TO_COMPLETE_NORMALISE+ " VARCHAR,"+ DEADLINE_NORMALISE+ " VARCHAR," + FINAL_SCORE+ " REAL,"
                + WORK_SEQUENCE+ " VARCHAR,"+ PROBABILITY_OF_SUCCESS+ " VARCHAR," + URGENCY + " VARCHAR);";
        Log.i("table creation", table_creation_sql);
        db.execSQL(table_creation_sql);
//        db.execSQL("create table " + TABLE_TRANSACTION + " ( " + WORK_SEQUENCE +" UNSIGNED BIG INT,"
//                + DEADLINE_DATE + " INTEGER,"+ PROBABILITY_OF_SUCCESS + " VARCHAR unique,"
//                + COLUMN_SYNC+ " INTEGER," +  COLUMN_TRANSACTION_CATEGORY +" VARCHAR,"+
//                COLUMN_SCAN  + " INTEGER,"+
//                COLUMN_TRANSACTION_DATE_TIME +  " DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME')),"+
//                COLUMN_TRANSACTION_DESCRIPTION + " VARCHAR," + " FOREIGN KEY ("+ WORK_SEQUENCE +
//                ") REFERENCES " +TABLE_NAME + " ("+ TIME_TO_COMPLETE +"));");
   }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS" + TABLE_TRANSACTION);
        onCreate(db);
    }
    public String selectName(long number)
    {
        String Fname = new String();
        String name = new String();
        String Lname = new String();
        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor c = db.rawQuery("SELECT NUMBER FROM PEOPLE ", null);
        Cursor c = db.rawQuery("SELECT FIRST_NAME,LAST_NAME FROM PEOPLE WHERE NUMBER = ?", new String[] {String.valueOf(number)});
        // String name= c.getString(c.getColumnIndex("NUMBER"));
        if (c.moveToFirst()) {
            Fname = c.getString(c.getColumnIndex("FIRST_NAME"));
            Lname = c.getString(c.getColumnIndex("LAST_NAME"));
            name = Fname + Lname;

        }

        c.close();
        db.close();
        return name;
    }

    public void insertRecord(PlanerModel planerModel) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_NAME, planerModel.getTaskName());
        contentValues.put(IMPACT, planerModel.getImpact());
        contentValues.put(DEADLINE_DATE, planerModel.getDeadLine());
        contentValues.put(TIME_TO_COMPLETE, planerModel.getTimeToComplete());
        contentValues.put(PROBABILITY_OF_SUCCESS, planerModel.getProbabilityofSuccess());
        contentValues.put(URGENCY, planerModel.getUrgency());
        contentValues.put(WORK_SEQUENCE, planerModel.getWorkSequence());
        contentValues.put(EFFORTS_TO_COMPLETE,planerModel.getEffortsToComplete());
        contentValues.put(TIME_TO_COMPLETE_NORMALISE,planerModel.getNormaliseTimeToComplete());
        contentValues.put(DEADLINE_NORMALISE,planerModel.getNormaliseDeadline());
        contentValues.put(FINAL_SCORE,planerModel.getFinalScore());
        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    public void updateNormaliseTimeToComplete(String normaliseTimeToComplete,String taskName){
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIME_TO_COMPLETE_NORMALISE,normaliseTimeToComplete);
        database.update(TABLE_NAME, contentValues, TASK_NAME + " = ?", new String[]{taskName});
//        database.update(TABLE_NAME,contentValues,TASK_NAME+" = ?",new String[taskName]);
    }

    public void updateNormaliseDeadline(String normaliseDeadline,String taskName){
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DEADLINE_NORMALISE,normaliseDeadline);
        database.update(TABLE_NAME, contentValues, TASK_NAME + " = ?", new String[]{taskName});
//        database.update(TABLE_NAME,contentValues,TASK_NAME+" = ?",new String[taskName]);
    }

    public void updateScore(String updatedScore,String taskName){
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FINAL_SCORE,updatedScore);
        database.update(TABLE_NAME, contentValues, TASK_NAME + " = ?", new String[]{taskName});
//        database.update(TABLE_NAME,contentValues,TASK_NAME+" = ?",new String[taskName]);
    }

//    public void setBalance(ContactModel contact){
//        database = this.getReadableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(TASK_NAME, contact.getFirstName());
//        contentValues.put(IMPACT, contact.getLastName());
//        contentValues.put(TIME_TO_COMPLETE, contact.getNumber());
//        contentValues.put(URGENCY, contact.getSyncDateTime());
//        contentValues.put(EFFORTS_TO_COMPLETE,contact.getBalance());
//        database.insert(TABLE_NAME, null, contentValues);
//        database.close();
//    }
//    public Boolean checkTransaction(String Transaction_id){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from KHATA_TRANSACTION where "+ PROBABILITY_OF_SUCCESS + " = ?",new String[]{Transaction_id});
//        if (cursor.getCount()==0){
//            return true;
//        }
//        return false;
//    }
    public String selectPhonenumber(String rowid)
    {
        String name = "";
        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor c = db.rawQuery("SELECT NUMBER FROM PEOPLE ", null);
        Cursor c = db.rawQuery("SELECT NUMBER FROM PEOPLE WHERE ID = ?", new String[] {String.valueOf(rowid)});
        // String name= c.getString(c.getColumnIndex("NUMBER"));
        if (c.moveToFirst()) {
            name = c.getString(c.getColumnIndex("NUMBER"));
        }

        c.close();
        db.close();
        return name;
    }

//    public void inserttransactionRecord(ContactModel contact){
//        database = this.getReadableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(WORK_SEQUENCE, contact.gettransactionPhoneNumber());
//        contentValues.put(DEADLINE_DATE, contact.gettransactionAmount());
//        contentValues.put(PROBABILITY_OF_SUCCESS,contact.getTransactionid());
//        contentValues.put(COLUMN_SYNC,contact.getSync());
//        contentValues.put(COLUMN_TRANSACTION_CATEGORY,contact.getTransactionCategory());
//        contentValues.put(COLUMN_SCAN,contact.getscan_Info());
//        contentValues.put(COLUMN_TRANSACTION_DATE_TIME, contact.gettransactionDateTime());
//        contentValues.put(COLUMN_TRANSACTION_DESCRIPTION, contact.gettransactionDescription());
//        database.insert(TABLE_TRANSACTION, null,contentValues);
//        database.close();
//    }

//    public void insertRecordAlternate(ContactModel contact) {
//        database = this.getReadableDatabase();
//        database.execSQL("INSERT INTO " + TABLE_NAME + "(" + TASK_NAME + "," + IMPACT + "," + TIME_TO_COMPLETE + "," + EFFORTS_TO_COMPLETE + ") VALUES('" + contact.getFirstName() + "','" + contact.getLastName() + "','" + contact.getNumber() +"','"+ contact.getBalance() + "')");
//        database.close();
//    }

//    public  String selectbalance (String number)
//    {
//        String name = "";
//        SQLiteDatabase db = this.getReadableDatabase();
//        // Cursor c = db.rawQuery("SELECT NUMBER FROM PEOPLE ", null);
//        Cursor c = db.rawQuery("SELECT BALANCE FROM PEOPLE WHERE NUMBER = ?", new String[] {String.valueOf(number)});
//        // String name= c.getString(c.getColumnIndex("NUMBER"));
//        if (c.moveToFirst()) {
//            name = c.getString(c.getColumnIndex("BALANCE"));
//        }
//
//        c.close();
//        db.close();
//        return name;
//    }


    public ArrayList<PlanerModel> getAllRecords() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, FINAL_SCORE +" DESC");
        ArrayList<PlanerModel> contacts = new ArrayList<PlanerModel>();
        PlanerModel planerModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                planerModel = new PlanerModel();
                planerModel.setTaskName(cursor.getString(cursor.getColumnIndex(TASK_NAME)));
                planerModel.setImpact(cursor.getString(cursor.getColumnIndex(IMPACT)));
                planerModel.setTimeToComplete(cursor.getString(cursor.getColumnIndex(TIME_TO_COMPLETE)));
                planerModel.setEffortsToComplete(cursor.getString(cursor.getColumnIndex(EFFORTS_TO_COMPLETE)));
                planerModel.setDeadLine(cursor.getString(cursor.getColumnIndex(DEADLINE_DATE)));
                planerModel.setWorkSequence(cursor.getString(cursor.getColumnIndex(WORK_SEQUENCE)));
                planerModel.setProbabilityofSuccess(cursor.getString(cursor.getColumnIndex(PROBABILITY_OF_SUCCESS)));
                planerModel.setUrgency(cursor.getString(cursor.getColumnIndex(URGENCY)));
                planerModel.setNormaliseDeadline(cursor.getString(cursor.getColumnIndex(DEADLINE_NORMALISE)));
                planerModel.setNormaliseTimeToComplete(cursor.getString(cursor.getColumnIndex(TIME_TO_COMPLETE_NORMALISE)));
                planerModel.setFinalScore(cursor.getDouble(cursor.getColumnIndex(FINAL_SCORE)));
                contacts.add(planerModel);
            }
        }
        cursor.close();
        database.close();

        return contacts;
    }

    public ArrayList<PlanerModel> getTimeToCompleteHours() {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT "+TASK_NAME +","+TIME_TO_COMPLETE + " FROM " +  TABLE_NAME , null);
        ArrayList<PlanerModel> contacts = new ArrayList<PlanerModel>();
        PlanerModel planerModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                planerModel = new PlanerModel();
                planerModel.setTaskName(cursor.getString(cursor.getColumnIndex(TASK_NAME)));

                planerModel.setTimeToComplete(cursor.getString(cursor.getColumnIndex(TIME_TO_COMPLETE)));

                contacts.add(planerModel);
            }
        }
        cursor.close();
        database.close();

        return contacts;
    }



//    public void setTransactionScan(String id){
//        database = this.getReadableDatabase();
//        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_TRANSACTION+
//                " WHERE TRANSACTION_ID = ?",new String[] {String.valueOf(id)});
//        database = this.getReadableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_SCAN, 1);
//        database.update(TABLE_TRANSACTION, contentValues, PROBABILITY_OF_SUCCESS + " = ?", new String[]{id});
//        database.close();
//
//    }
//    public void setTransactionSync(String id){
//        database = this.getReadableDatabase();
//        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_TRANSACTION+
//                " WHERE TRANSACTION_ID = ?",new String[] {String.valueOf(id)});
//        database = this.getReadableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_SYNC, 1);
//        database.update(TABLE_TRANSACTION, contentValues, PROBABILITY_OF_SUCCESS + " = ?", new String[]{id});
//        database.close();
//
//    }
//
//    public ArrayList<ContactModel> gettransaction(long number) {
//        database = this.getReadableDatabase();
//        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_TRANSACTION+
//                " WHERE TRANSECTION_NUMBER = ?",new String[] {String.valueOf(number)});
//        ArrayList<ContactModel> contacts = new ArrayList<ContactModel>();
//        ContactModel contactModel;
//        if (cursor.getCount() > 0) {
//            for (int i = 0; i < cursor.getCount(); i++) {
//                cursor.moveToNext();
//                contactModel = new ContactModel();
//                contactModel.settransactionPhoneNumber(cursor.getLong(cursor.getColumnIndex(WORK_SEQUENCE)));
//                contactModel.settransactionAmount(cursor.getLong(cursor.getColumnIndex(DEADLINE_DATE)));
//                contactModel.settransactionDateTime(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSACTION_DATE_TIME)));
//                //contactModel.settransactionDescription(cursor.getString(3));
//                contacts.add(contactModel);
//            }
//        }
//        cursor.close();
//        database.close();
//        return contacts;
//
//    }

//    public long getBalance(long number)
//    {
//        ArrayList<ContactModel> contacts = gettransaction(number);
//        ContactModel contactModel;
//        long Balance =0 ;
//        for (int i = 0; i < contacts.size(); i++) {
//
//            contactModel = contacts.get(i);
//            Balance = Balance + contactModel.gettransactionAmount();
//        }
//        return Balance;
//    }
//
//
//    public ArrayList<ContactModel> getAlltransactionRecords() {
//        database = this.getReadableDatabase();
//        Cursor cursor = database.query(TABLE_NAME, //FIXME: WRONG TABLE NAME, Fuck!!!
//                null, null, null, null, null, null);
//
//        ArrayList<ContactModel> contacts = new ArrayList<ContactModel>();
//        ContactModel contactModel;
//        if (cursor.getCount() > 0) {
//            for (int i = 0; i < cursor.getCount(); i++) {
//                cursor.moveToNext();
//
//                contactModel = new ContactModel();
//                contactModel.settransactionPhoneNumber(cursor.getLong(0));
//                contactModel.settransactionAmount(cursor.getLong(1));
//                contactModel.setSyncDateTime(cursor.getString(2));
//                contactModel.settransactionDescription(cursor.getString(3));
//                contacts.add(contactModel);
//
//            }
//        }
//        cursor.close();
//        database.close();
//        return contacts;
//    }
//
//    public ArrayList<ContactModel> getToBeSyncedTransactionRecords() {
//        database = this.getReadableDatabase();
//        String rawQuery = "select * from KHATA_TRANSACTION where IS_SYNCED = 0 ";
//        Cursor cursor = database.rawQuery(rawQuery, null, null);
//
//        ArrayList<ContactModel> contacts = new ArrayList<ContactModel>();
//        ContactModel contactModel;
//        if (cursor.getCount() > 0) {
//            for (int i = 0; i < cursor.getCount(); i++) {
//                cursor.moveToNext();
//
//                contactModel = new ContactModel();
//                contactModel.settransactionPhoneNumber(cursor.getLong(cursor.
//                        getColumnIndex(WORK_SEQUENCE)));
//                contactModel.settransactionAmount(cursor.getLong(cursor.
//                        getColumnIndex(DEADLINE_DATE)));
//                contactModel.setTransactionCategory(cursor.
//                        getString(cursor.getColumnIndex(COLUMN_TRANSACTION_CATEGORY)));
//                contactModel.setTransactionid(cursor.
//                        getString(cursor.getColumnIndex(PROBABILITY_OF_SUCCESS)));
//                contactModel.setScan(cursor.getInt(cursor.getColumnIndex(COLUMN_SCAN)));
//                contacts.add(contactModel);
//
//            }
//        }
//        cursor.close();
//        database.close();
//        return contacts;
//    }
//
//
//    public ArrayList<ContactModel> getAllRecordsAlternate() {
//        database = this.getReadableDatabase();
//        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//
//        ArrayList<ContactModel> contacts = new ArrayList<ContactModel>();
//        ContactModel contactModel;
//        if (cursor.getCount() > 0) {
//            for (int i = 0; i < cursor.getCount(); i++) {
//                cursor.moveToNext();
//
//                contactModel = new ContactModel();
//                contactModel.setID(cursor.getString(0));
//                contactModel.setFirstName(cursor.getString(1));
//                contactModel.setLastName(cursor.getString(2));
//                contactModel.setNumber(cursor.getInt(3));
//                contactModel.setBalance(cursor.getInt(4));
//                contactModel.setSyncDateTime(cursor.getString(5));
//                contacts.add(contactModel);
//            }
//        }
//        cursor.close();
//        database.close();
//
//        return contacts;
//    }
//
//
//    public void updateRecord(ContactModel contact) {
//        database = this.getReadableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(TASK_NAME, contact.getFirstName());
//        contentValues.put(IMPACT, contact.getLastName());
//        contentValues.put(TIME_TO_COMPLETE,contact.getNumber());
//        contentValues.put(EFFORTS_TO_COMPLETE,contact.getBalance());
//        contentValues.put(URGENCY, contact.getSyncDateTime());
//        database.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{contact.getID()});
//        database.close();
//    }
//    public void updateBalance(ContactModel contact , String phno) {
//        database = this.getReadableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(EFFORTS_TO_COMPLETE,contact.getBalance());
//        database.update(TABLE_NAME, contentValues, TIME_TO_COMPLETE + " = ?", new String[]{phno});
//        database.close();
//    }
//
//    public void UpdateSYNC(ContactModel contact , String transaction) {
//        database = this.getReadableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_SYNC,contact.getBalance());
//        database.update(TABLE_TRANSACTION, contentValues, PROBABILITY_OF_SUCCESS + " = ?", new String[]{transaction});
//        database.close();
//    }
//
//    public void updateRecordAlternate(ContactModel contact) {
//        database = this.getReadableDatabase();
//        database.execSQL("update " + TABLE_NAME + " set " + TASK_NAME + " = '" + contact.getFirstName() + "', " + IMPACT + " = '" + contact.getLastName() + "'," + TIME_TO_COMPLETE + " = '" +contact.getNumber()+"'," + EFFORTS_TO_COMPLETE + " ='" +contact.getBalance() + "' where " + COLUMN_ID + " = '" + contact.getID() + "'");
//        database.close();
//    }
//
//    public void deleteAllRecords() {
//        database = this.getReadableDatabase();
//        database.delete(TABLE_NAME, null, null);
//        database.close();
//    }
//
//    public void deleteAllRecordsAlternate() {
//        database = this.getReadableDatabase();
//        database.execSQL("delete from " + TABLE_NAME);
//        database.close();
//    }
//
//    public void deleteRecord(ContactModel contact) {
//        database = this.getReadableDatabase();
//        database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{contact.getID()});
//        database.close();
//    }
//
//    public void deleteRecordAlternate(ContactModel contact) {
//        database = this.getReadableDatabase();
//        database.execSQL("delete from " + TABLE_NAME + " where " + COLUMN_ID + " = '" + contact.getID() + "'");
//        database.close();
//    }
//
//    public ArrayList<String> getAllTableName()
//    {
//        database = this.getReadableDatabase();
//        ArrayList<String> allTableNames=new ArrayList<String>();
//        Cursor cursor=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'",null);
//        if(cursor.getCount()>0)
//        {
//            for(int i=0;i<cursor.getCount();i++)
//            {
//                cursor.moveToNext();
//                allTableNames.add(cursor.getString(cursor.getColumnIndex("name")));
//            }
//        }
//        cursor.close();
//        database.close();
//        return allTableNames;
//    }


}
