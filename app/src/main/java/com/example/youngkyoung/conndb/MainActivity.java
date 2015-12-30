package com.example.youngkyoung.conndb;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Server.ConnectServer;

public class MainActivity extends Activity
{
    Button conbtn;
    public static EditText editxt01;
    public static TextView txt01;
    Activity act;
    ConnectServer conserv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        act = this;
        conbtn = (Button)findViewById(R.id.btn01);
        editxt01 = (EditText)findViewById(R.id.edit01);
        txt01 = (TextView)findViewById(R.id.txt01);


        conserv = new ConnectServer();


        conbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data1 = editxt01.getText().toString();

                conserv.initial(data1,act);

                //conserv.

            }
        });
    }



}
