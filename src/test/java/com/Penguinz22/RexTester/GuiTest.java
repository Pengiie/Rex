package com.Penguinz22.RexTester;

import com.Penguinz22.Rex.Application;
import com.Penguinz22.Rex.ApplicationConfig;
import com.Penguinz22.Rex.Core;
import com.Penguinz22.Rex.assets.AssetLoadedCallback;
import com.Penguinz22.Rex.assets.AssetManager;
import com.Penguinz22.Rex.assets.Font;
import com.Penguinz22.Rex.assets.Texture;
import com.Penguinz22.Rex.assets.loaders.TrueTypeFontLoader;
import com.Penguinz22.Rex.graphics.Draw;
import com.Penguinz22.Rex.gui.GuiRenderer;
import com.Penguinz22.Rex.gui.animation.SlideTransition;
import com.Penguinz22.Rex.gui.animation.Transition;
import com.Penguinz22.Rex.gui.animation.TransitionDriver;
import com.Penguinz22.Rex.gui.components.*;
import com.Penguinz22.Rex.gui.constraints.*;
import com.Penguinz22.Rex.listeners.ApplicationListener;
import com.Penguinz22.Rex.utils.Color;
import com.Penguinz22.Rex.utils.fonts.TextAlignment;
import org.graalvm.compiler.lir.LIRInstruction;
import org.graalvm.compiler.nodes.NodeView;

public class GuiTest implements ApplicationListener {

    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig();
        config.setTitle("Gui Tester!");
        config.setWindowResizable(true);
        Application app = new Application(new GuiTest(), config);
    }

    private final Transition DEFAULT_TRANSITION = new Transition().setAlphaDriver(new SlideTransition(0, 1, 0.25f));

    @Override
    public void init() {
        Core.guiRenderer = new GuiRenderer();
        Core.assets = new AssetManager(true);

        Core.assets.load("test.png", Texture.class);
        Core.assets.load("test2.png", Texture.class);
        TrueTypeFontLoader.FontParameter fontParameter = new TrueTypeFontLoader.FontParameter();
        fontParameter.bitmapHeight = 1024;
        fontParameter.bitmapWidth = 1024;
        fontParameter.lineHeight = 128;
        Core.assets.load("comic.ttf", Font.class, fontParameter);

        Core.assets.finishLoading();

        GuiImage image = new GuiImage(Core.assets.get("test.png"));

        GuiConstraints constraints = new GuiConstraints();
        constraints.setXConstraint(new RelativeConstraint(0.25f));
        constraints.setYConstraint(new RelativeConstraint(0.25f));
        constraints.setHeightConstraint(new RelativeConstraint(0.25f));

        GuiBlock block = new GuiBlock(Color.orange);

        image.setMouseEnterCallback(() -> {
            image.texture = Core.assets.get("test2.png");
        });

        image.setMouseLeaveCallback(() -> {
            image.texture = Core.assets.get("test.png");
        });

        //Core.guiRenderer.getScreenComponent().add(block, constraints);

        constraints.setXConstraint(new CenterConstraint());
        constraints.setYConstraint(new CenterConstraint());
        constraints.setHeightConstraint(new RelativeConstraint(0.5f));
        constraints.setWidthConstraint(new RatioConstraint(((Texture) Core.assets.get("test.png")).getAspectRatio()));

        //block.add(image, constraints);

        GuiText text = new GuiText("Point: 10", 64, true, Color.white, Core.assets.get("comic.ttf"));
        GuiBlock block1 = new GuiBlock(Color.blue);

        constraints.setXConstraint(new RelativeConstraint(0.05f));
        constraints.setYConstraint(new RelativeConstraint(0.05f));
        constraints.setHeightConstraint(new RelativeConstraint(0.9f));
        constraints.setWidthConstraint(new RelativeConstraint(0.9f));

        text.horizAlign = TextAlignment.CENTER;
        text.vertAlign = TextAlignment.CENTER;

        //block.add(text, constraints);
        //block.add(block1, constraints);

        text.getAnimator().addAnimation(DEFAULT_TRANSITION, 0, 0);
        text.setAlpha(0);
        block1.setAlpha(0);
        block1.setMouseEnterCallback(() -> {
            text.getAnimator().playTransition(DEFAULT_TRANSITION, false);
        });
        block1.setMouseLeaveCallback(() -> {
            text.getAnimator().playTransition(DEFAULT_TRANSITION, true);
        });

        constraints.setXConstraint(new RelativeConstraint(0.25f));
        constraints.setYConstraint(new RelativeConstraint(0.75f));
        constraints.setWidthConstraint(new RelativeConstraint(0.5f));
        constraints.setHeightConstraint(new RelativeConstraint(0.2f));

        GuiText playText = new GuiText(
                "Play the game now!", 64, true, Color.white, Core.assets.get("comic.ttf"));
        GuiText text2 = new GuiText("LMAO JK IT WAS A SCAM", 64, true, Color.white, Core.assets.get("comic.ttf"));
        playText.vertAlign = TextAlignment.CENTER;
        playText.horizAlign = TextAlignment.CENTER;

        Transition transition = new Transition().setXDriver(new SlideTransition(0, 1000, 2f));

        GuiButton playButton = new GuiButton(new GuiBlock(new Color(0, 0.1f, 0.8f, 1)), playText, new GuiBlock(new Color(0, 0.5f, 0.8f, 1)), text2) {
            @Override
            public void onClick(int mouseX, int mouseY) {
                System.out.println("Play game");
            }
        };
        playButton.getAnimator().addAnimation(transition, 0, 0);
        playButton.setMouseEnterCallback(() -> {
            playButton.getAnimator().playTransition(transition, false);
        });
        playButton.setMouseLeaveCallback(() -> {
            playButton.getAnimator().playTransition(transition, true);
        });
        Core.guiRenderer.screenComponent.add(playButton, constraints);

        constraints.setYConstraint(new PixelConstraint(50));

        GuiBlock blockblock = new GuiBlock(Color.orange);
        Core.guiRenderer.screenComponent.add(blockblock, constraints);
        GuiTextBox textBox = new GuiTextBox(Core.assets.get("comic.ttf"), Color.black, Color.black, false);

        blockblock.add(new GuiBlock(Color.white),
                new GuiConstraints().setXConstraint(new RelativeConstraint(0.025f))
        .setYConstraint(new RelativeConstraint(0.025f))
        .setWidthConstraint(new RelativeConstraint(0.95f))
        .setHeightConstraint(new RelativeConstraint(0.95f)));
        blockblock.add(textBox, GuiConstraints.fullConstraint);
    }

    @Override
    public void resize(int width, int height) {
        Core.guiRenderer.resized(width, height);
    }

    @Override
    public void update() {
        Core.assets.update();
        Core.guiRenderer.updateScreen();
    }

    @Override
    public void render() {
        Draw.clear();
        Core.guiRenderer.renderScreen();
    }
}
