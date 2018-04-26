package com.mhmt.navigationprocessor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mhmt.navigationprocessor.dummy.Cerealizable;
import com.mhmt.navigationprocessor.dummy.Parcellabble;
import com.mhmt.navigationprocessor.generated.Navigator;
import com.mhmt.navigationprocessor.processor.Required;

public class TestActivity extends AppCompatActivity {

  @Required public int[] intArray;
  @Required public byte[] byteArray;
  @Required public boolean[] booleanArray;
  @Required public short[] shortArray;
  @Required public char[] charArray;
  @Required public long[] longArray;
  @Required public float[] floatArray;
  @Required public double[] doubleArray;

  @Required public String[] stringArray;
  @Required public CharSequence[] charSequenceArray;

  @Required public String string;
  @Required public Parcellabble parcellabble;
  @Required public Cerealizable cerealizable;

  @Required public int intId;
  @Required public Integer integer;
  @Required public byte bite;
  @Required public Byte bite2;
  @Required public boolean booleen;
  @Required public Boolean booleen2;
  @Required public double duuble;
  @Required public Double duuble2;
  @Required public long lung;
  @Required public Long lung2;
  @Required public float flote;
  @Required public Float flote2;
  @Required public short shurt;
  @Required public Short shurt2;
  @Required public char chaar;

  @Required public CharSequence charSequence;
  @Required public Bundle bundle;
  @Required public Parcellabble[] parcellabbleArray;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);
    Navigator.bind(this);
  }
}
