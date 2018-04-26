package com.mhmt.navigationprocessor.dummy;

import android.support.annotation.NonNull;

public class CharrSequence implements CharSequence {
    @Override public int length() {
        return 0;
    }

    @Override public char charAt(final int index) {
        return 0;
    }

    @Override public CharSequence subSequence(final int start, final int end) {
        return null;
    }

    @NonNull
    @Override public String toString() {
        return null;
    }
}
