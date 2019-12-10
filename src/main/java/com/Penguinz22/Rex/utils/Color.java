package com.Penguinz22.Rex.utils;

import org.joml.Vector4f;

import javax.swing.text.AttributeSet;

public class Color {

    public static final Color white = new Color(1, 1, 1, 1);
    public static final Color black = new Color(0, 0, 0, 0);

    public static final Color red = new Color(1, 0, 0, 1);
    public static final Color green = new Color(0, 1, 0, 1);
    public static final Color blue = new Color(0, 0, 1, 1);

    public static final Color yellow = new Color(1, 1, 0, 1);
    public static final Color cyan = new Color(0, 1, 1, 1);
    public static final Color magenta = new Color(1, 0, 1, 1);

    public static final Color orange = new Color(1, 0.647f, 0, 1);


    public float r, g, b, a;

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    private Color add(Color color) {
        this.r += color.r;
        this.g += color.g;
        this.b += color.b;
        this.a += color.a;
        return clamp();
    }

    private Color clamp() {
        if(r < 0)
            r = 0;
        else if(r > 1)
            r = 1;

        if(g < 0)
            g = 0;
        else if(g > 1)
            g = 1;

        if(b < 0)
            b = 0;
        else if(b > 1)
            b = 1;

        if(a < 0)
            a = 0;
        else if(a > 1)
            a = 1;
        return this;
    }

    public Color clone() {
        return new Color(r, g, b, a);
    }
    public Vector4f toVector4f() {
        return new Vector4f(r, g, b, a);
    }

    public Color setRed(float red) {
        this.r = red;
        return this;
    }

    public Color setGreen(float green) {
        this.g = green;
        return this;
    }

    public Color setBlue(float blue) {
        this.b = blue;
        return this;
    }

    public Color setAlpha(float alpha) {
        this.a = alpha;
        return this;
    }

}
