package edu.school21;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes(value = {"edu.school21.HtmlForm", "edu.school21.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Element userForm : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            HtmlForm htmlForm = userForm.getAnnotation(HtmlForm.class);
            stringBuilder.append("<form action = \"")
                    .append(htmlForm.action())
                    .append("\" method = \"")
                    .append(htmlForm.method())
                    .append("\">\n");

            for (Element element : userForm.getEnclosedElements()) {
                if (element.getAnnotation(HtmlInput.class) == null) {
                    continue;
                }
                HtmlInput htmlInput = element.getAnnotation(HtmlInput.class);
                stringBuilder.append("\t<input type = \"")
                        .append(htmlInput.type())
                        .append("\" name = \"")
                        .append(htmlInput.name())
                        .append("\" placeholder = \"")
                        .append(htmlInput.placeholder())
                        .append("\">\n");
            }
            stringBuilder.append("\t<input type = \"submit\" value = \"Send\">\n" +
                    "</form>\n");
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter("target/" + htmlForm.fileName()))) {
                writer.write(stringBuilder.toString());
            } catch (IOException ex) {
                System.err.println("Error while creating file");
                System.exit(-1);
            }
        }
        return false;
    }
}
