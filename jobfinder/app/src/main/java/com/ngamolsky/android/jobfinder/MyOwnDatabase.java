package com.ngamolsky.android.jobfinder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Administrator on 13-09-2016.
 */
public class MyOwnDatabase extends SQLiteOpenHelper {
    public static final String Db_Name = "jobs";
    public static final String Db_Table = "job_profile";
    public static final int Db_Ver = 1;

    SQLiteDatabase db;
    Context ct;
    boolean dataReturned=false;

    public MyOwnDatabase(Context ct){
        super(ct,Db_Name,null,Db_Ver);
        this.ct = ct;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+Db_Table+" (_id integer primary key autoincrement, firstname text, lastname text,age text,education text,dob text,exp text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+Db_Table);
        onCreate(sqLiteDatabase);
    }


    public void insertDatabase(String s1,String s2,String s3,String s4,String s5,String s6){
        db = getWritableDatabase();
        db.execSQL("insert into "+Db_Table+" (firstname, lastname, age, education, dob, exp) values('"+s1+"','"+s2+"','"+s3+"','"+s4+"','"+s5+"','"+s6+"');");
        Toast.makeText(ct,"Data Inserted",Toast.LENGTH_SHORT).show();
    }

    public boolean getData(){
        db = getReadableDatabase();
        Cursor cr = db.rawQuery("select * from "+Db_Table,null);
        StringBuilder str = new StringBuilder();
        if(cr.moveToNext()){
            dataReturned = true;
            String s1 = cr.getString(1);
            String s2 = cr.getString(2);
            String s3 = cr.getString(3);
            String s4 = cr.getString(4);
            String s5 = cr.getString(5);
            String s6 = cr.getString(6);

            str.append(s1+"  "+s2+"  "+s3+"  "+s4+"  "+s5+"  "+s6+"\n");
        }
        Toast.makeText(ct,str.toString(),Toast.LENGTH_SHORT).show();

        return dataReturned;

    }
}
