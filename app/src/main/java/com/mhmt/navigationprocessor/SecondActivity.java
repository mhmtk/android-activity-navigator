package com.mhmt.navigationprocessor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mhmt.navigationprocessor.generated.Navigator;
import com.mhmt.navigationprocessor.processor.Required;

public class SecondActivity extends AppCompatActivity {

  @Required(bind = true) public int intId;
  @Required(bind = true) public String magic;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second);
    Log.d("Navigator", "Before bind : ".concat(magic));
    Navigator.bind(this);
    Log.d("Navigator", "After bind: ".concat(magic));
  }
}
