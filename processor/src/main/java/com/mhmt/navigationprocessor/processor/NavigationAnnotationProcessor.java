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
          builder.append("\t\t")
                 .append("activity").append(".").append(field.getSimpleName())
                 .append(" = ")
                 .append("activity.getIntent().get").append(getClassAsString(field)).append("Extra(")
                 .append("\"").append(field.getSimpleName()).append("\");\n");
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

  private String getClassAsString(final Element field) {
    final String[] split = field.asType().toString().split("\\.");
    return split[split.length-1];
  }

}
