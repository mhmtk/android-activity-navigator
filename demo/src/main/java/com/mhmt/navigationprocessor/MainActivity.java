package com.mhmt.navigationprocessor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mhmt.navigationprocessor.generated.Navigator;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);


    final CharSequence charSequence = new CharSequence() {
      @Override public int length() {
        return 0;
      }

      @Override public char charAt(final int index) {
        return 0;
      }

      @Override public CharSequence subSequence(final int start, final int end) {
        return null;
      }

      @NonNull @Override public String toString() {
        return null;
      }
    };

    final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Navigator.startSecondActivity(MainActivity.this,
                                      new int[] {5},
                                      new byte[] {3},
                                      new boolean[] {true},
                                      new short[] {0},
                                      new char[] {'a'},
                                      new long[] {3},
                                      new float[] {3.3f},
                                      new double[] {3.3},
                                      new String[] {"YOLO"},
                                      new CharSequence[] {charSequence},
                                      "str",
                                      new Parcellabble(),
                                      new Cerealizable(),
                                      5, 5,
                                      (byte) 2, Byte.valueOf((byte) 3),
                                      true, false,
                                      (double) 2.5, Double.valueOf((double) 2.5),
                                      (long) 3, Long.valueOf((long) 3),
                                      (float) 3.5, Float.valueOf((float) 3.5),
                                      (short) 4, Short.valueOf((short) 4),
                                      'c',
                                      charSequence,
                                      new Bundle(),
                                      new Parcellabble[] {new Parcellabble()});

      }
    });

  }

}
