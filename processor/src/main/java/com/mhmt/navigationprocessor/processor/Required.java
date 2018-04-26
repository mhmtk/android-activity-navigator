package com.mhmt.navigationprocessor.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface Required {
  boolean bind() default true;
}
