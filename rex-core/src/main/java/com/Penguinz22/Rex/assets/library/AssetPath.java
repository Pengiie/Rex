package com.Penguinz22.ZSurvival.assets;

import com.Penguinz22.Rex.assets.loaders.AssetLoaderParameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AssetPath {

    String value();

}
