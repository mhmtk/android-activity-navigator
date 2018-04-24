package com.mhmt.navigationprocessor.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

@SupportedAnnotationTypes("com.mhmt.navigationprocessor.processor.Required")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class NavigationAnnotationProcessor extends AbstractProcessor {

  private static final ClassName intentClass = ClassName.get("android.content", "Intent");
  private static final ClassName contextClass = ClassName.get("android.content", "Context");

  @Override public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

    HashMap<Element, List<Element>> classVariableMap = new HashMap<>();
    for (Element rootElement : roundEnv.getElementsAnnotatedWith(Required.class)) {
      Element clazz = rootElement.getEnclosingElement();
      if (!classVariableMap.containsKey(clazz)) {
        ArrayList<Element> elementList = new ArrayList<>();
        elementList.add(rootElement);
        classVariableMap.put(clazz, elementList);
      } else {
        classVariableMap.get(clazz).add(rootElement);
      }
    }

    TypeSpec.Builder navigatorClassBuilder = TypeSpec.classBuilder("Navigator")
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

    for (Element clazz : classVariableMap.keySet()) { //add a start method for each activity
      MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("start".concat(clazz.getSimpleName().toString()))
              .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
              .returns(void.class)
              .addParameter(contextClass, "context")
              .addStatement("$T intent = new $T(context, $T.class)", intentClass, intentClass, clazz.asType());
      for (Element field : classVariableMap.get(clazz)) {
        methodBuilder
                .addParameter(ParameterizedTypeName.get(field.asType()), field.getSimpleName().toString())
                .addStatement("intent.putExtra($S, $L)", field.getSimpleName(), field.getSimpleName());
      }
      methodBuilder.addStatement("context.startActivity(intent)");

      navigatorClassBuilder.addMethod(methodBuilder.build());

    }

//    for (Element clazz: classVariableMap.keySet()) {
//      builder.append("\t public static void bind(final ")
//             .append(clazz.getSimpleName())
//             .append(" activity) {\n"); // open method
//
//      for (Element field : classVariableMap.get(clazz)) {
//        if (field.getAnnotation(Required.class).bind()) {
//          if (ofClass(field, Double.class) || ofClass(field, double.class)
//              || ofClass(field, Long.class) || ofClass(field, long.class)
//              || ofClass(field, Float.class) || ofClass(field, float.class)) {
//            builder.append("\t\t")
//                   .append("activity")
//                   .append(".")
//                   .append(field.getSimpleName())
//                   .append(" = ")
//                   .append("activity.getIntent().get")
//                   .append(capitalizeFirstLetter(getClassNameAsString(field)))
//                   .append("Extra(")
//                   .append("\"")
//                   .append(field.getSimpleName())
//                   .append("\"")
//                   .append(", -1);\n");
//          } else if (ofClass(field, Byte.class) || ofClass(field, byte.class)
//                     || ofClass(field, Short.class) || ofClass(field, short.class)) {
//            builder.append("\t\t")
//                   .append("activity")
//                   .append(".")
//                   .append(field.getSimpleName())
//                   .append(" = ")
//                   .append("activity.getIntent().get")
//                   .append(capitalizeFirstLetter(getClassNameAsString(field)))
//                   .append("Extra(")
//                   .append("\"")
//                   .append(field.getSimpleName())
//                   .append("\"")
//                   .append(", (").append(lowerCaseFirstLetter(getClassNameAsString(field))).append(") -1);\n");
//          } else if (ofClass(field, char.class)) {
//            builder.append("\t\t")
//                   .append("activity")
//                   .append(".")
//                   .append(field.getSimpleName())
//                   .append(" = ")
//                   .append("activity.getIntent().get")
//                   .append(capitalizeFirstLetter(getClassNameAsString(field)))
//                   .append("Extra(")
//                   .append("\"")
//                   .append(field.getSimpleName())
//                   .append("\"")
//                   .append(", 'm');\n");
//          } else if (ofClass(field, Integer.class) || ofClass(field, int.class)) {
//            builder.append("\t\t")
//                   .append("activity")
//                   .append(".")
//                   .append(field.getSimpleName())
//                   .append(" = ")
//                   .append("activity.getIntent().getIntExtra(")
//                   .append("\"")
//                   .append(field.getSimpleName())
//                   .append("\"")
//                   .append(", -1);\n");
//          } else if (ofClass(field, Boolean.class) || ofClass(field, boolean.class)) {
//            builder.append("\t\t")
//                   .append("activity")
//                   .append(".")
//                   .append(field.getSimpleName())
//                   .append(" = ")
//                   .append("activity.getIntent().get")
//                   .append(capitalizeFirstLetter(getClassNameAsString(field)))
//                   .append("Extra(")
//                   .append("\"")
//                   .append(field.getSimpleName())
//                   .append("\"")
//                   .append(", false);\n");
//          } else if (isArray(field)) {
//            if (isParcelableArray(field)) {
//              builder.append("\t\t")
//                     .append("activity")
//                     .append(".")
//                     .append(field.getSimpleName())
//                     .append(" = (")
//                     .append(field.asType().toString())
//                     .append(") activity.getIntent().getParcelableArrayExtra(")
//                     .append("\"")
//                     .append(field.getSimpleName())
//                     .append("\");\n");
//            } else {
//              builder.append("\t\t")
//                     .append("activity")
//                     .append(".")
//                     .append(field.getSimpleName())
//                     .append(" = ")
//                     .append("activity.getIntent().get")
//                     .append(capitalizeFirstLetter(getClassNameAsString(field)))
//                     .append("Extra(")
//                     .append("\"")
//                     .append(field.getSimpleName())
//                     .append("\");\n");
//            }
//          } else if (ofClass(field, String.class) || isBundle(field) || ofClass(field, CharSequence.class)) {
//            builder.append("\t\t")
//                   .append("activity").append(".").append(field.getSimpleName())
//                   .append(" = ")
//                   .append("activity.getIntent().get").append(capitalizeFirstLetter(getClassNameAsString(field))).append("Extra(")
//                   .append("\"").append(field.getSimpleName()).append("\");\n");
//          } else if (isParcelable(field)) {
//            builder.append("\t\t")
//                   .append("activity")
//                   .append(".")
//                   .append(field.getSimpleName())
//                   .append(" = ")
//                   .append("activity.getIntent().getParcelableExtra(")
//                   .append("\"")
//                   .append(field.getSimpleName())
//                   .append("\"")
//                   .append(");\n");
//          } else if (isSerializable(field)) {
//            builder.append("\t\t")
//                   .append("activity")
//                   .append(".")
//                   .append(field.getSimpleName())
//                   .append(" = (")
//                   .append(field.asType().toString())
//                   .append(") activity.getIntent().getSerializableExtra(")
//                   .append("\"")
//                   .append(field.getSimpleName())
//                   .append("\"")
//                   .append(");\n");
//          }
//        }
//      }
//      builder.append("\t}\n\n"); //close method
//    }
    JavaFile javaFile = JavaFile.builder("com.mhmt.navigationprocessor.generated", navigatorClassBuilder.build())
            .build();
    try {
      javaFile.writeTo(processingEnv.getFiler());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return true;
  }

  private boolean isParcelableArray(final Element field) {
    TypeMirror parcelable = processingEnv.getElementUtils().getTypeElement("android.os.Parcelable").asType();
    return processingEnv.getTypeUtils().isAssignable(((ArrayType) field.asType()).getComponentType(), parcelable);
  }

  private String getClassNameAsString(final Element field) {
    final String[] split = field.asType().toString().split("\\.");
    return split[split.length-1].replace("[]", "Array");
  }

  private boolean isArray(final Element element) {
    return element.asType().getKind() == TypeKind.ARRAY;
  }

  private boolean ofClass(Element element, Class clazz) {
    return clazz.getName().equals(element.asType().toString());
  }

  private String capitalizeFirstLetter(final String input) {
    return input.substring(0, 1).toUpperCase().concat(input.substring(1));
  }

  private String lowerCaseFirstLetter(final String input) {
    return input.substring(0, 1).toLowerCase().concat(input.substring(1));
  }

  private boolean isBundle(final Element field) {
    TypeMirror bundle = processingEnv.getElementUtils().getTypeElement("android.os.Bundle").asType();
    return processingEnv.getTypeUtils().isAssignable(field.asType(), bundle);
  }

  private boolean isParcelable(final Element field) {
    TypeMirror parcelable = processingEnv.getElementUtils().getTypeElement("android.os.Parcelable").asType();
    return processingEnv.getTypeUtils().isAssignable(field.asType(), parcelable);
  }

  private boolean isSerializable(final Element field) {
    TypeMirror serializable = processingEnv.getElementUtils().getTypeElement("java.io.Serializable").asType();
    return processingEnv.getTypeUtils().isAssignable(field.asType(), serializable);
  }
}
