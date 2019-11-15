package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editTextInsert;
    EditText editTextUpdateBefore;
    EditText editTextUpdateAfter;
    EditText editTextDel;

    TextView textViewDisplay;

    Button buttonInsert;
    Button buttonUpdate;
    Button buttonDel;
    Button buttonQuery;
    Button buttonClear;

    MySQLiteOpenHelper dbHelper;
    SQLiteDatabase  db;
    public  void onDestroy() {

        super.onDestroy();
        if(db!=null)
        {
            db.close();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextInsert=findViewById(R.id.editTextInsert);
        editTextUpdateBefore=findViewById(R.id.editTextUpdateBefore);
        editTextUpdateAfter=findViewById(R.id.editTextUpdateAfter);
        editTextDel=findViewById(R.id.editTextDel);
        textViewDisplay=findViewById(R.id.textViewDisplay);

        buttonInsert=findViewById(R.id.buttonInsert);
        buttonUpdate=findViewById(R.id.buttonUpdate);
        buttonDel=findViewById(R.id.buttonDel);
        buttonQuery=findViewById(R.id.buttonQuery);
        buttonClear=findViewById(R.id.buttonClear);

        dbHelper = new MySQLiteOpenHelper(MainActivity.this,"My_Database",null,1);
        db=dbHelper.getWritableDatabase();
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert record;
                String insert_data=editTextInsert.getText().toString().trim();
                ContentValues values =new ContentValues();
                values.put("name",insert_data);
                db.insert(MySQLiteOpenHelper.TB_NAME,null,values);
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //upate record
                String before_data=editTextUpdateBefore.getText().toString().trim();
                String after_data=editTextUpdateAfter.getText().toString().trim();

                ContentValues values2 =new ContentValues();
                values2.put("name",after_data);
                db.update(MySQLiteOpenHelper.TB_NAME,values2,"name=?",new String[]{before_data});
            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete record
                String del_data=editTextDel.getText().toString().trim();
                db.delete(MySQLiteOpenHelper.TB_NAME,"name=?",new String[]{del_data});
            }
        });
        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Query all records
                Cursor cursor=db.query(MySQLiteOpenHelper.TB_NAME,new String[]{"name"},null,null,null,null,null);
                String data="";
                while(cursor.moveToNext())
                {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    data=data+"\n"+name;
                }
                textViewDisplay.setText(data);
                cursor.close();


            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextInsert.setText("");
                editTextUpdateBefore.setText("");
                editTextUpdateAfter.setText("");
                editTextDel.setText("");
                textViewDisplay.setText("");

            }
        });

    }
}
