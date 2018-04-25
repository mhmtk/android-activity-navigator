package com.mhmt.navigationprocessor.processor.error;

public class IncompatibleModifierError extends Error {

    public IncompatibleModifierError(String className, String fieldName) {
        super("Field ".concat(fieldName).concat(" in ").concat(className).concat(" should be public."));
    }
}
