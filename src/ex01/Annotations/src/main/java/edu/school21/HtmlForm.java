package edu.school21;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlForm {
    String fileName();

    String action();

    String method();
}
