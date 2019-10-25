package com.Penguinz22.RexTester;

import com.Penguinz22.Rex.Application;
import com.Penguinz22.Rex.ApplicationConfig;
import com.Penguinz22.Rex.backend.Window;
import com.Penguinz22.Rex.graphics.BatchRenderer;
import com.Penguinz22.Rex.graphics.Draw;
import com.Penguinz22.Rex.listeners.ApplicationListener;
import com.Penguinz22.Rex.utils.Color;
import com.sun.media.sound.SoftTuning;

import java.rmi.activation.ActivationGroup_Stub;
import java.util.ArrayList;
import java.util.List;

public class TreeGenTest  {

    /**private BatchRenderer draw;
    private Tree tree;

    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig();
        config.setTitle("Quality Game!");
        Application app = new Application(new TreeGenTest(), config);
    }

    @Override
    public void init() {
        this.draw = new BatchRenderer();
        this.tree = new Tree(Window.width/2-11, 50);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        Draw.clearWithColor(new Color(0, 1, 1, 1));
        draw.draw(new Color(0, 1, 0, 1), 0, 0, Window.width, 75, 0);
        tree.draw(draw);
        draw.finish();

    }

    private class Tree {

        int baseX, baseY;
        private final int height = 200;

        public Tree(int baseX, int baseY) {
            this.baseX = baseX;
            this.baseY = baseY;
        }

        public void draw(BatchRenderer renderer) {
            renderer.draw(new Color(1,0,0,1),baseX,baseY,22,height+10);

            recurse(renderer, 1, baseX, baseY+height, 20, 170, 45);
            recurse(renderer, 1, baseX, baseY+height, 20, 170, -45);
        }

        private void recurse(BatchRenderer renderer, int iteration, int x, int y, int width, int height, int angle) {
            renderer.draw(new Color(1,0,0,1),x,y,width,height,angle);

            if(iteration > 1)
                return;


            int changeX = (int) (Math.sin(Math.toRadians(angle))*height-10);
            int changeY = (int) Math.abs(Math.cos(Math.toRadians(angle))*height-10);
            System.out.println("Changes");
            System.out.println(x+" "+changeX);
            System.out.println(y+" "+changeY);
            int tempAngle = angle+(angle/2);

            renderer.draw(new Color(0, 1, 0, 1), x, y, 50, 50, 0);
            renderer.draw(new Color(1, 1, 0, 1), x+changeX, y+changeY, 50, 50, 0);

            recurse(renderer, iteration+1, x+changeX, y+changeY, 20, height, 0);

            tempAngle = angle+(angle/2);

            recurse(renderer, iteration+1, x+changeX, y+changeY, 20, height, 0);
        }
/**
        private class Branch {
            private List<Branch> children = new ArrayList<>();
            private int branchSize;
            private int x, y;
            private int girth;
            private int genome;
            private boolean active = true;
            public Branch(int genome, int x, int y, int iteration, int numberOfChildren, int branchSize, final float prevAngle) {
                if(iteration >30) {
                    active = false;
                    return;
                }
                this.branchSize = branchSize;
                this.genome = genome;
                this.x = x;
                this.y = y;

                this.girth = (int)(((float)(100-iteration)/100)*15);
                System.out.println("iter");
                System.out.println(prevAngle);
                for(int i=0;i<numberOfChildren;i++) {
                    float temp = prevAngle;
                    System.out.println("Angles");
                    float angle = temp+(i-((float)numberOfChildren/2))*((float)90/numberOfChildren);
                    System.out.println(temp);
                    System.out.println(angle);
                    int changeX = (int)(Math.cos(Math.toRadians(angle))*branchSize);
                    int changeY = (int) (Math.sin(Math.toRadians(angle))*branchSize);
                    changeX = Math.abs(changeX)*genome;
                    changeY = Math.abs(changeY);
                    System.out.println("CHange");
                    System.out.println(changeX);
                    System.out.println(changeY);
                    System.out.println(branchSize);
                    children.add(new Branch(genome,
                            x+changeX,
                            y+changeY,
                            iteration+1, (int)Math.ceil((float)iteration/10), (int)((float)(50-iteration)/50*20), angle));
                }
            }

            public int getX() {
                return x;
            }

            public int getY() {
                return y;
            }

            public void draw(BatchRenderer renderer) {
                if(!active)
                    return;

                for(int i=0;i<children.size();i++) {
                    float angle = ((float)(i+1)/children.size())*genome*90;
                    renderer.draw(new Color(1,0,0,1),children.get(i).getX(),children.get(i).getY()+15, girth, branchSize, angle);
                    children.get(i).draw(renderer);
                }
            }
        }
    }
    */
}
