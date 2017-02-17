package com.example.vraja03.okfriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vraja03.okfriend.parser.DataParsing;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataParsing dataParsing = new DataParsing();
        dataParsing.loadJSONFromAsset(this);
    }
}
