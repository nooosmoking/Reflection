package edu.school21;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlInput {
    String type();

    String name();

    String placeholder();
}
