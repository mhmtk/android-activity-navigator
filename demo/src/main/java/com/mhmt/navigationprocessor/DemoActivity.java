package com.mhmt.navigationprocessor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mhmt.navigationprocessor.generated.Navigator;
import com.mhmt.navigationprocessor.processor.Required;

public class DemoActivity extends AppCompatActivity {

    @Required public int index;
    @Required public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Navigator.bind(this);
    }
}
