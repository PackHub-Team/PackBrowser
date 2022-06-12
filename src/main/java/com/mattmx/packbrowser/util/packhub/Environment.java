package com.mattmx.packbrowser.util.packhub;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Environment {
    public EnvironmentType type() default EnvironmentType.CLIENT;
    enum EnvironmentType {
        DEV, CLIENT
    }
}
