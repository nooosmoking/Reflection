package edu.school21.models;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrmColumn {
    String name();

    int length() default 1;
}
