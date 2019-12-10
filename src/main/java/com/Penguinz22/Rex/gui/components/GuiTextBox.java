package com.Penguinz22.Rex.gui.components;

import com.Penguinz22.Rex.Core;
import com.Penguinz22.Rex.InputManager;
import com.Penguinz22.Rex.assets.Font;
import com.Penguinz22.Rex.gui.GuiRenderer;
import com.Penguinz22.Rex.gui.constraints.FinalConstraint;
import com.Penguinz22.Rex.gui.constraints.GuiConstraints;
import com.Penguinz22.Rex.gui.constraints.RelativeConstraint;
import com.Penguinz22.Rex.utils.Color;
import com.Penguinz22.Rex.utils.Key;
import com.Penguinz22.Rex.utils.annotations.RenderOrder;
import com.Penguinz22.Rex.utils.annotations.Order;
import com.Penguinz22.Rex.utils.fonts.TextAlignment;
import org.graalvm.compiler.nodes.spi.Lowerable;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class GuiTextBox extends GuiComponent {

    private GuiText guiText;
    private GuiConstraints cursorConstraints;
    private GuiBlock cursor;
    private boolean password;

    private String text = "";
    private int textOffset = 0;
    private int cursorPos = 0;

    private long startBlinkTime;
    private long blinkInterval = 500;

    public GuiTextBox(Font font, Color textColor, Color cursorColor, boolean password) {
        // Update pixel height later
        this.guiText = new GuiText("", 0, false, textColor, font);
        this.guiText.vertAlign = TextAlignment.CENTER;
        this.guiText.wordCutOff = false;
        this.cursor = new GuiBlock(cursorColor);
        this.cursor.setParent(this);
        this.password = password;

        GuiConstraints constraints = new GuiConstraints();
        constraints.setXConstraint(new RelativeConstraint(0.05f));
        constraints.setYConstraint(new RelativeConstraint(0.05f));
        constraints.setWidthConstraint(new RelativeConstraint(0.9f));
        constraints.setHeightConstraint(new RelativeConstraint(0.85f));

        this.add(this.guiText, constraints);

        this.cursorConstraints = constraints.setWidthConstraint(new FinalConstraint(4));
        this.startBlinkTime = Core.timings.getTotalMilliseconds();
    }

    @Override
    public void update() {
        // I also want to die coding this, man, anything to do with text sucks
        if((Core.timings.getTotalMilliseconds()>=this.startBlinkTime+this.blinkInterval)) {
            cursor.setVisible(!cursor.isVisible());
            this.startBlinkTime = Core.timings.getTotalMilliseconds();
        }

        // Update key inputs
        for(Key key: Core.input.getKeyPresses().keySet()) {
            if(key.isDisplayable()) {
                String[] text = getSeparatedText(cursorPos);
                if(Core.input.isKeyPressed(Key.KEY_V, InputManager.Modifier.CONTROL)) {
                    try {
                        String pastedText = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                        this.text = text[0] + pastedText + text[1];
                        cursorPos += pastedText.length();
                        updateTextOffset(pastedText);
                    } catch (UnsupportedFlavorException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    String input = (Core.input.getKeyPresses().get(key).contains(InputManager.Modifier.SHIFT)
                            | Core.input.getKeyPresses().get(key).contains(InputManager.Modifier.CAPS_LOCK)
                            ? key.getAltCharacter() : key.getCharacter());
                    this.text = text[0] + input + text[1];
                    cursorPos++;
                    updateTextOffset(input);
                }
            } else if(key == Key.KEY_BACKSPACE) {
                String[] text = getSeparatedText(cursorPos);
                if(!text[0].isEmpty()) {
                    this.text = text[0].substring(0, text[0].length()-1) + text[1];
                    cursorPos--;
                }
            }
        }

        if(Core.input.isKeyPressed(Key.KEY_RIGHT))
            if(cursorPos < text.length())
                cursorPos++;

        if(Core.input.isKeyPressed(Key.KEY_LEFT))
            if (cursorPos > 0)
                cursorPos--;

        guiText.pixelHeight = guiText.getConstraintsReference().getHeightConstraint().getValue()*0.75f;
        float scale = guiText.font.getScale((int) guiText.pixelHeight);

        // Placing cursor is correct position
        if(guiText.isMouseHovering() && Core.input.isMouseButtonPressed(0)) {
            int relativeX = (int) (Core.input.getMousePosX() - guiText.getConstraintsReference().getXConstraint().getValue());
            String charConstructor = "";
            int iteration = 0;
            for(char character: text.toCharArray()) {
                charConstructor+=character;
                int width = guiText.font.getTextWidth(charConstructor, scale, true);
                int widthLowerBound = (int) (width - (guiText.font.getCharacters().get((int) character).getSize().x*scale)/2);
                if(widthLowerBound > relativeX) {
                    cursorPos = iteration + textOffset;
                    break;
                }
                if(width > relativeX) {
                    cursorPos = iteration+1+textOffset;
                    if(cursorPos > text.length())
                        cursorPos = text.length();
                    break;
                }
                iteration++;
            }
        }

        // Make overflowed text move
        String visibleText = "";
        for (int i = textOffset; i < text.toCharArray().length; i++) {
            if(i >= text.toCharArray().length)
                break;
            char character = text.toCharArray()[i];
            int width = guiText.font.getTextWidth(visibleText+character+" ", scale, true);
            if(width>=this.guiText.getConstraintsReference().getWidthConstraint().getValue())
                break;
            else visibleText += character;
        }
        guiText.text = visibleText;

        // Adjust overflow if cursorPos requires
        if(cursorPos - textOffset < 0)
            textOffset--;
        else if(cursorPos - guiText.text.length() - textOffset > 0)
            textOffset++;

        int textWidthBeforeCursor = guiText.font.getTextWidth(getSeparatedGuiText(cursorPos - textOffset)[0], scale, false);
        this.cursorConstraints.getXConstraint().pixelOffset = textWidthBeforeCursor + 1;
    }

    @Override
    @RenderOrder(Order.AFTER)
    public void render(GuiRenderer renderer) {
        this.cursor.setConstraintsReference(this.cursorConstraints);
        this.cursor.updateSelfConstraints();
        if(this.cursor.isVisible())
            this.cursor.render(renderer);
    }

    private void updateTextOffset(String newCharacters) {
        if(cursorPos == text.length() && guiText.getConstraintsReference().getWidthConstraint().getValue() <= guiText.font
        .getTextWidth(this.guiText.text + newCharacters + " ", guiText.font.getScale((int) guiText.pixelHeight), true)) {
            textOffset+=newCharacters.length();
        }
    }

    private String[] getSeparatedText(int index) {
        if(index == 0) {
            return new String[] {"", text};
        } else {
            return new String[] {text.substring(0, index), text.substring(index, text.length())};
        }
    }

    private String[] getSeparatedGuiText(int index) {
        if(index == 0) {
            return new String[] {"", guiText.text};
        } else {
            return new String[] {guiText.text.substring(0, index), guiText.text.substring(index, guiText.text.length())};
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public GuiText getGuiText() {
        return this.guiText;
    }
}
