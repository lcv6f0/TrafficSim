/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 *
 * @author Luc
 */
public class Light {

    private double redSeconds, greenSeconds;
    private boolean isGreen = false;
    private Circle light;
    private int time;
    private String[] sequence;
    private int counter;
    private Timer timer = new Timer();

    public void setProperties(Properties p) {

        sequence = p.getProperty(light.getId()).split(",");
    }

    public Light(Properties properties, Circle light) {
        //Pull line associate with lightid and convert to array
        sequence = properties.getProperty(light.getId()).split(",");
        /* Pull the time and convert to integer and 
        multiply by 1000 to convert from milliseconds to seconds
         */
        time = Integer.parseInt(properties.getProperty("time")) * 1000;
        this.light = light;
        isGreen = light.getFill().equals(Paint.valueOf("green"));

        switch (sequence[counter]) {
            case "G":
                Platform.runLater(() -> {
                    light.setFill(Paint.valueOf("green"));
                });

                timer.schedule(new LightTask(), ((long) time));
                break;
            case "Y":
                Platform.runLater(() -> {
                    light.setFill(Paint.valueOf("yellow"));
                });

                timer.schedule(new LightTask(), ((long) time));
                break;
            case "R":
                Platform.runLater(() -> {
                    light.setFill(Paint.valueOf("red"));
                });

                timer.schedule(new LightTask(), ((long) time));

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
            switch (sequence[counter % sequence.length]) {
                case "G":
                    Platform.runLater(() -> {
                        light.setFill(Paint.valueOf("green"));
                    });

                    timer.schedule(new LightTask(), ((long) time));
                    break;
                case "Y":
                    Platform.runLater(() -> {
                        light.setFill(Paint.valueOf("yellow"));
                    });

                    timer.schedule(new LightTask(), ((long) time));
                    break;
                case "R":
                    Platform.runLater(() -> {
                        light.setFill(Paint.valueOf("red"));
                    });

                    timer.schedule(new LightTask(), ((long) time));

            }
            counter++;
        }
    }
}
