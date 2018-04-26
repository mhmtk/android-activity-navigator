package com.mhmt.navigationprocessor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mhmt.navigationprocessor.dummy.Cerealizable;
import com.mhmt.navigationprocessor.dummy.CharrSequence;
import com.mhmt.navigationprocessor.dummy.Parcellabble;
import com.mhmt.navigationprocessor.generated.Navigator;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    final FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        launchTestActivity();
      }
    });
  }

  private void launchDemoActivity() {
    Navigator.startDemoActivity(this, 3, "string");
  }

  private void launchTestActivity() {
    Navigator.startTestActivity(MainActivity.this,
            new int[] {5},
            new byte[] {3},
            new boolean[] {true},
            new short[] {0},
            new char[] {'a'},
            new long[] {3},
            new float[] {3.3f},
            new double[] {3.3},
            new String[] {"Test"},
            new CharSequence[] {new CharrSequence()},
            "str",
            new Parcellabble(),
            new Cerealizable(),
            5, 5,
            (byte) 2, Byte.valueOf((byte) 3),
            true, false,
            2.5, Double.valueOf((double) 2.5),
            (long) 3, Long.valueOf((long) 3),
            (float) 3.5, Float.valueOf((float) 3.5),
            (short) 4, Short.valueOf((short) 4),
            'c',
            new CharrSequence(),
            new Bundle(),
            new Parcellabble[] {new Parcellabble()});
  }

}
