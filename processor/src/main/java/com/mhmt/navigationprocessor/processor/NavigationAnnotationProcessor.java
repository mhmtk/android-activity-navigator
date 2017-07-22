package com.mhmt.navigationprocessor.processor;

import java.io.IOException;
import java.io.Writer;
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
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.mhmt.navigationprocessor.processor.Required")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class NavigationAnnotationProcessor extends AbstractProcessor {

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

    // Add package and imports
    StringBuilder builder = new StringBuilder()
                                .append("package com.mhmt.navigationprocessor.generated;\n")
                                .append("import android.content.Context;\n")
                                .append("import android.content.Intent;\n");

    for (Element clazz : classVariableMap.keySet()) {
      builder.append("import com.mhmt.navigationprocessor.")
             .append(clazz.getSimpleName())
             .append(";\n\n");
    }

    builder.append("public class Navigator {\n\n"); // open class

    for (Element clazz : classVariableMap.keySet()) { //add a start method for each activity
      builder.append("\tpublic static void start").append(clazz.getSimpleName().toString()).append("(final Context context");

      StringBuilder intentBuilder = new StringBuilder();
      intentBuilder.append("\t\tIntent intent = new Intent(context, ").append(clazz.getSimpleName()).append(".class);\n");
      for (Element field : classVariableMap.get(clazz)) {
        builder.append(", ").append(field.asType().toString()).append(" ").append(field.getSimpleName());
        intentBuilder.append("\t\tintent.putExtra(")
                     .append("\"").append(field.getSimpleName()).append("\"").append(", ") // Extra name
                     .append(field.getSimpleName()) // Extra value
                     .append(");\n");
      }
      intentBuilder.append("\t\tcontext.startActivity(intent);\n");

      builder.append(") {\n") // close method signature
             .append(intentBuilder.toString()) //add method body
             .append("\t}\n\n"); //close method
    }

    for (Element clazz: classVariableMap.keySet()) {
      builder.append("\t public static void bind(final ")
             .append(clazz.getSimpleName())
             .append(" activity) {\n"); // open method

      for (Element field : classVariableMap.get(clazz)) {
        if (field.getAnnotation(Required.class).bind()) {
          if (ofClass(field, Double.class) || ofClass(field, double.class)
              || ofClass(field, Long.class) || ofClass(field, long.class)
              || ofClass(field, Float.class) || ofClass(field, float.class)) {
            builder.append("\t\t")
                   .append("activity")
                   .append(".")
                   .append(field.getSimpleName())
                   .append(" = ")
                   .append("activity.getIntent().get")
                   .append(capitalizeFirstLetter(getClassNameAsString(field)))
                   .append("Extra(")
                   .append("\"")
                   .append(field.getSimpleName())
                   .append("\"")
                   .append(", -1);\n");
          } else if (ofClass(field, Byte.class) || ofClass(field, byte.class)
                     || ofClass(field, Short.class) || ofClass(field, short.class)) {
            builder.append("\t\t")
                   .append("activity")
                   .append(".")
                   .append(field.getSimpleName())
                   .append(" = ")
                   .append("activity.getIntent().get")
                   .append(capitalizeFirstLetter(getClassNameAsString(field)))
                   .append("Extra(")
                   .append("\"")
                   .append(field.getSimpleName())
                   .append("\"")
                   .append(", (").append(lowerCaseFirstLetter(getClassNameAsString(field))).append(") -1);\n");
          } else if (ofClass(field, char.class)) {
            builder.append("\t\t")
                   .append("activity")
                   .append(".")
                   .append(field.getSimpleName())
                   .append(" = ")
                   .append("activity.getIntent().get")
                   .append(capitalizeFirstLetter(getClassNameAsString(field)))
                   .append("Extra(")
                   .append("\"")
                   .append(field.getSimpleName())
                   .append("\"")
                   .append(", 'm');\n");
          } else if (ofClass(field, Integer.class) || ofClass(field, int.class)) {
            builder.append("\t\t")
                   .append("activity")
                   .append(".")
                   .append(field.getSimpleName())
                   .append(" = ")
                   .append("activity.getIntent().getIntExtra(")
                   .append("\"")
                   .append(field.getSimpleName())
                   .append("\"")
                   .append(", -1);\n");
          } else if (ofClass(field, Boolean.class) || ofClass(field, boolean.class)) {
            builder.append("\t\t")
                   .append("activity")
                   .append(".")
                   .append(field.getSimpleName())
                   .append(" = ")
                   .append("activity.getIntent().get")
                   .append(capitalizeFirstLetter(getClassNameAsString(field)))
                   .append("Extra(")
                   .append("\"")
                   .append(field.getSimpleName())
                   .append("\"")
                   .append(", false);\n");
          } else if (isArray(field)) {
            if (isParcelableArray(field)) {
              builder.append("\t\t")
                     .append("activity")
                     .append(".")
                     .append(field.getSimpleName())
                     .append(" = (")
                     .append(field.asType().toString())
                     .append(") activity.getIntent().getParcelableArrayExtra(")
                     .append("\"")
                     .append(field.getSimpleName())
                     .append("\");\n");
            } else {
              builder.append("\t\t")
                     .append("activity")
                     .append(".")
                     .append(field.getSimpleName())
                     .append(" = ")
                     .append("activity.getIntent().get")
                     .append(capitalizeFirstLetter(getClassNameAsString(field)))
                     .append("Extra(")
                     .append("\"")
                     .append(field.getSimpleName())
                     .append("\");\n");
            }
          } else if (ofClass(field, String.class) || isBundle(field)) {
            builder.append("\t\t")
                   .append("activity").append(".").append(field.getSimpleName())
                   .append(" = ")
                   .append("activity.getIntent().get").append(capitalizeFirstLetter(getClassNameAsString(field))).append("Extra(")
                   .append("\"").append(field.getSimpleName()).append("\");\n");
          } else if (isParcelable(field)) {
            builder.append("\t\t")
                   .append("activity")
                   .append(".")
                   .append(field.getSimpleName())
                   .append(" = ")
                   .append("activity.getIntent().getParcelableExtra(")
                   .append("\"")
                   .append(field.getSimpleName())
                   .append("\"")
                   .append(");\n");
          } else if (isSerializable(field)) {
            builder.append("\t\t")
                   .append("activity")
                   .append(".")
                   .append(field.getSimpleName())
                   .append(" = (")
                   .append(field.asType().toString())
                   .append(") activity.getIntent().getSerializableExtra(")
                   .append("\"")
                   .append(field.getSimpleName())
                   .append("\"")
                   .append(");\n");
          }
        }
      }
      builder.append("\t}\n\n"); //close method
    }
    builder.append("}\n"); // close class



    try { // write the file
      JavaFileObject
          source = processingEnv.getFiler().createSourceFile("com.mhmt.navigationprocessor.generated.Navigator");
      Writer writer = source.openWriter();
      writer.write(builder.toString());
      writer.flush();
      writer.close();
    } catch (IOException e) {
      // Note: calling e.printStackTrace() will print IO errors
      // that occur from the file already existing after its first run, this is normal
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
