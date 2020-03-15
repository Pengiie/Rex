package com.Penguinz22.ZSurvival.assets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TextureAtlasParameter {

    float unitSizeX() default 16;
    float unitSizeY() default 16;

}
