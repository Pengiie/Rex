package com.Penguinz22.Rex.gui.components;

import com.Penguinz22.Rex.assets.Font;
import com.Penguinz22.Rex.gui.GuiRenderer;
import com.Penguinz22.Rex.utils.Color;
import com.Penguinz22.Rex.utils.fonts.TextAlignment;
import org.lwjgl.stb.STBTruetype;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiText extends GuiComponent {

    public Font font;
    public String text;

    public float pixelHeight;
    public boolean wrapText;
    public boolean adjustForWidth;
    public boolean wordCutOff = true;

    public TextAlignment horizAlign = TextAlignment.LEFT;
    public TextAlignment vertAlign = TextAlignment.TOP;

    public GuiText(String text, float pixelHeight, boolean wrapText, Color color, Font font) {
        this.text = text;
        this.pixelHeight = pixelHeight;
        this.wrapText = wrapText;
        this.adjustForWidth = false;
        setColor(color);
        this.font = font;
    }

    public GuiText(String text, Color color, Font font) {
        this.text = text;
        this.pixelHeight = 0;
        this.wrapText = false;
        this.adjustForWidth = true;
        setColor(color);
        this.font = font;
    }

    @Override
    public void render(GuiRenderer renderer) {
        // I want to die coding this
        if(adjustForWidth) {
            int componentWidth = (int) getConstraintsReference().getWidthConstraint().getValue();

            // Get scaling variables
            int pixelHeight = (int) (font.getScaleForWidth(text, componentWidth,
                    (int) getConstraintsReference().getHeightConstraint().getValue()) * font.getData().getLineHeight());
            float scale = (float) pixelHeight / (font.getData().getAscent() - font.getData().getDescent());

            // Calculate YPos according to text alignment
            float yPos = getConstraintsReference().getYConstraint().getValue();
            if(vertAlign == TextAlignment.TOP)
                yPos += getConstraintsReference().getHeightConstraint().getValue()
                        - (font.getData().getAscent() - font.getData().getDescent() + font.getData().getLineGap())*scale;
            else if(vertAlign == TextAlignment.CENTER)
                yPos += (getConstraintsReference().getHeightConstraint().getValue())/2 + -font.getData().getDescent()*scale
                        - ((font.getData().getAscent() - font.getData().getDescent())*scale)/2;
            else if(vertAlign == TextAlignment.BOTTOM)
                yPos += (-font.getData().getDescent()+font.getData().getLineGap())*scale;

            // Calculate XPos according to text alignment
            float xPos = (int) getConstraintsReference().getXConstraint().getValue();
            int width = font.getTextWidth(text, font.getScaleForWidth(text, componentWidth, (int) getConstraintsReference().getHeightConstraint().getValue()));

            if(horizAlign == TextAlignment.CENTER)
                xPos += (componentWidth-width)/2;
            else if(horizAlign == TextAlignment.RIGHT)
                xPos += (componentWidth-width);

            renderer.drawText(font, text,
                    (int) xPos,
                    (int) yPos,
                    getColor().clone().setAlpha(getAlpha()), componentWidth, (int)getConstraintsReference().getHeightConstraint().getValue());
        } else {
            if(wrapText) {
                int componentWidth = (int) getConstraintsReference().getWidthConstraint().getValue();
                int componentHeight = (int) getConstraintsReference().getHeightConstraint().getValue();

                // Check the words that will fit
                String[] words = text.split(" ");
                String toDrawText = "";

                List<String> rows = new ArrayList<>();

                float charScale = font.getScale((int) pixelHeight);

                float scale = pixelHeight / (font.getData().getAscent() - font.getData().getDescent());

                float currentLength = 0;
                int totalHeight = 0;
                for (String word : words) {
                    currentLength = font.getTextWidth(toDrawText+" "+word, charScale);
                    if(currentLength < componentWidth)
                        toDrawText += " " + word;
                    else {
                        if(totalHeight+(font.getData().getAscent() - font.getData().getDescent())*scale>componentHeight) {
                            toDrawText = "";
                            break;
                        }
                        totalHeight += (font.getData().getAscent() - font.getData().getDescent() + font.getData().getLineGap())*scale;
                        rows.add(toDrawText);
                        toDrawText = word;
                    }
                }
                if(!toDrawText.isEmpty() && totalHeight+(font.getData().getAscent() - font.getData().getDescent())*scale<componentHeight) {
                    rows.add(toDrawText);
                }
                // Calculate YPos according to text alignment
                float yPos = getConstraintsReference().getYConstraint().getValue();
                totalHeight = 0;
                for (int i = 0; i < rows.size(); i++) {
                    totalHeight += (font.getData().getAscent() - font.getData().getDescent() + font.getData().getLineGap())*scale;
                }
                if(vertAlign == TextAlignment.TOP)
                    yPos += componentHeight
                            - (font.getData().getAscent() - font.getData().getDescent() + font.getData().getLineGap())*scale;
                else if(vertAlign == TextAlignment.CENTER)
                    yPos += (componentHeight)/2 + -font.getData().getDescent()*scale
                            - ((font.getData().getAscent() - font.getData().getDescent())*scale)/2
                            + (int) (totalHeight-(font.getData().getAscent() - font.getData().getDescent() + font.getData().getLineGap())*scale)/2;
                else if(vertAlign == TextAlignment.BOTTOM)
                    yPos += totalHeight + (-font.getData().getDescent()+font.getData().getLineGap())*scale;

                for (String row : rows) {
                    // Calculate XPos according to text alignment
                    float xPos = (int) getConstraintsReference().getXConstraint().getValue();
                    int width = font.getTextWidth(row, charScale);

                    if(horizAlign == TextAlignment.CENTER)
                        xPos += (componentWidth-width)/2;
                    else if(horizAlign == TextAlignment.RIGHT)
                        xPos += (componentWidth-width);

                    float toDrawY = (yPos - rows.indexOf(row)*((font.getData().getAscent() - font.getData().getDescent() + font.getData().getLineGap())*scale));

                    renderer.drawText(font, row,
                            (int) xPos,
                            (int) toDrawY,
                            (int) pixelHeight, getColor().clone().setAlpha(getAlpha()));
                }


            } else {
                int componentWidth = (int) getConstraintsReference().getWidthConstraint().getValue();

                // Check the words that will fit
                String toDrawText = "";
                String[] words = text.split(" ");

                float charScale = font.getScale((int) pixelHeight);

                float currentLength = 0;
                for (String word : words) {
                    if(wordCutOff) {
                        currentLength = font.getTextWidth(toDrawText + " " + word, charScale);
                        if (currentLength < componentWidth)
                            if (!word.isEmpty())
                                toDrawText += " " + word;
                            else break;
                    } else {
                        toDrawText += " ";
                        boolean shouldBreak = false;
                        for(char character: word.toCharArray()) {
                            currentLength = font.getTextWidth(toDrawText + character, charScale);
                            if (currentLength < componentWidth)
                                if (!word.isEmpty())
                                    toDrawText += character;
                                else {
                                    shouldBreak = true;
                                };
                        }
                        if(shouldBreak)
                            break;
                    }
                }
                if(!toDrawText.isEmpty())
                    toDrawText = toDrawText.substring(1, toDrawText.length());

                float scale = pixelHeight / (font.getData().getAscent() - font.getData().getDescent());

                // Calculate YPos according to text alignment
                float yPos = getConstraintsReference().getYConstraint().getValue();
                if(vertAlign == TextAlignment.TOP)
                    yPos += getConstraintsReference().getHeightConstraint().getValue()
                            - (font.getData().getAscent() - font.getData().getDescent() + font.getData().getLineGap())*scale;
                else if(vertAlign == TextAlignment.CENTER)
                    yPos += (getConstraintsReference().getHeightConstraint().getValue()/2) +
                            -font.getData().getDescent()*scale
                            - ((font.getData().getAscent() - font.getData().getDescent())*scale)/2;
                else if(vertAlign == TextAlignment.BOTTOM)
                    yPos += (-font.getData().getDescent()+font.getData().getLineGap())*scale;

                // Calculate XPos according to text alignment
                float xPos = (int) getConstraintsReference().getXConstraint().getValue();
                int width = font.getTextWidth(toDrawText, charScale);

                if(horizAlign == TextAlignment.CENTER)
                    xPos += (componentWidth-width)/2;
                else if(horizAlign == TextAlignment.RIGHT)
                    xPos += (componentWidth-width);

                renderer.drawText(font, toDrawText,
                        (int) xPos,
                        (int) yPos,
                        charScale, getColor().clone().setAlpha(getAlpha()));
            }
        }
    }
}
