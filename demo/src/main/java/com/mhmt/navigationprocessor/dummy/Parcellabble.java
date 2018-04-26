package com.mhmt.navigationprocessor.dummy;

import android.os.Parcel;
import android.os.Parcelable;

public class Parcellabble implements Parcelable{

  public Parcellabble() {

  }

  protected Parcellabble(Parcel in) {
  }

  public static final Creator<Parcellabble> CREATOR = new Creator<Parcellabble>() {
    @Override
    public Parcellabble createFromParcel(Parcel in) {
      return new Parcellabble(in);
    }

    @Override
    public Parcellabble[] newArray(int size) {
      return new Parcellabble[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(final Parcel dest, final int flags) {}
}
