package com.Penguinz22.Rex.utils;

import org.joml.Vector4f;

import javax.swing.text.AttributeSet;

public class Color {

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

}
