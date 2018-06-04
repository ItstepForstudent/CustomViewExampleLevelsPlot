package com.itstep.customviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.itstep.customviewdemo.custom.AnalititicsView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    AnalititicsView analititicsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        analititicsView = findViewById(R.id.analitics);

        List<Integer> levels = Arrays.asList(new Integer[]{1,2,7,2,5});
        analititicsView.setLevels(levels);
    }
}
