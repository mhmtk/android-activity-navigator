package com.mhmt.navigationprocessor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mhmt.navigationprocessor.processor.Required;

public class SecondActivity extends AppCompatActivity {

  @Required int intId;
  @Required(bind = true) String magic;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second);
  }
}
