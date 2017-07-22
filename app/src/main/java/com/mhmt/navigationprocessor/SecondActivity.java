package com.mhmt.navigationprocessor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mhmt.navigationprocessor.generated.Navigator;
import com.mhmt.navigationprocessor.processor.Required;

public class SecondActivity extends AppCompatActivity {

  @Required(bind = true) public int[] intArray;
  @Required(bind = true) public byte[] byteArray;
  @Required(bind = true) public boolean[] booleanArray;
  @Required(bind = true) public short[] shortArray;
  @Required(bind = true) public char[] charArray;
  @Required(bind = true) public long[] longArray;
  @Required(bind = true) public float[] floatArray;
  @Required(bind = true) public double[] doubleArray;

  @Required(bind = true) public String[] stringArray;

  @Required(bind = true) public String string;
  @Required(bind = true) public Parcellabble parcellabble;
  @Required(bind = true) public Cerealizable cerealizable;

  @Required(bind = true) public int intId;
  @Required(bind = true) public Integer integer;
  @Required(bind = true) public byte bite;
  @Required(bind = true) public Byte bite2;
  @Required(bind = true) public boolean booleen;
  @Required(bind = true) public Boolean booleen2;
  @Required(bind = true) public double duuble;
  @Required(bind = true) public Double duuble2;
  @Required(bind = true) public long lung;
  @Required(bind = true) public Long lung2;
  @Required(bind = true) public float flote;
  @Required(bind = true) public Float flote2;
  @Required(bind = true) public short shurt;
  @Required(bind = true) public Short shurt2;
  @Required(bind = true) public char chaar;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second);
    Log.d("Navigator", "Before bind : " + string);
    Navigator.bind(this);
    Log.d("Navigator", "After bind: ".concat(string));
//    new Intent().putExtra()
  }
}
