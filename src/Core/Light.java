/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 *
 * @author Christian
 */
public class Light {

    private double redSeconds, greenSeconds;
    private boolean isGreen = false;
    private Circle light;
    private int time, yellowtime;
    private String[] changes;
    private int counter;
    private Timer timer = new Timer();

    public void setProperties(Properties p) {

        changes = p.getProperty(light.getId()).split(",");
    }

    public Light(Properties properties, Circle light) {
        changes = properties.getProperty(light.getId()).split(",");
        time = Integer.parseInt(properties.getProperty("time"));
//        yellowtime=Integer.parseInt(properties.getProperty("time"));
        yellowtime = time;

        this.light = light;
        isGreen = light.getFill().equals(Paint.valueOf("green"));

        switch (changes[counter]) {
            case "G":
                Platform.runLater(() -> {
                    light.setFill(Paint.valueOf("green"));
                });

                timer.schedule(new LightTask(), ((long) time) * 1000);
                break;
            case "Y":
                Platform.runLater(() -> {
                    light.setFill(Paint.valueOf("yellow"));
                });

                timer.schedule(new LightTask(), ((long) yellowtime) * 1000);
                break;
            case "R":
                Platform.runLater(() -> {
                    light.setFill(Paint.valueOf("red"));
                });

                timer.schedule(new LightTask(), ((long) time) * 1000);

        }
        counter++;
    }

    public void setIsGreen(boolean isGreen) {
        this.isGreen = isGreen;
    }

    public double getRedSeconds() {
        return redSeconds;
    }

    public void setRedSeconds(double redSeconds) {
        this.redSeconds = redSeconds;
    }

    public double getGreenSeconds() {
        return greenSeconds;
    }

    public void setGreenSeconds(double greenSeconds) {
        this.greenSeconds = greenSeconds;
    }

    public void stop() {
        timer.cancel();
    }

    public Circle getLight() {
        return light;
    }

    public void setLight(Circle light) {
        this.light = light;
    }

    class LightTask extends TimerTask {

        @Override
        public void run() {
            switch (changes[counter % changes.length]) {
                case "G":
                    Platform.runLater(() -> {
                        light.setFill(Paint.valueOf("green"));
                    });

                    timer.schedule(new LightTask(), ((long) time) * 1000);
                    break;
                case "Y":
                    Platform.runLater(() -> {
                        light.setFill(Paint.valueOf("yellow"));
                    });

                    timer.schedule(new LightTask(), ((long) time) * 1000);
                    break;
                case "R":
                    Platform.runLater(() -> {
                        light.setFill(Paint.valueOf("red"));
                    });

                    timer.schedule(new LightTask(), ((long) time) * 1000);

            }
            counter++;
        }
    }
}
