package com.Penguinz22.ZSurvival.assets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FontParameter {

    int lineHeight() default 64;
    int bitmapWidth() default 512;
    int bitmapHeight() default 512;

}
