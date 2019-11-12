/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Utils.QueueException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author Christian
 */
public class Car {

    private final Roads roads;
//    private SequentialTransition gTransition;
    private TranslateTransition gTransition;

    private int currentLaneIndex;

    private final ImageView car;
    private boolean isPause;
    private long initTimer;
    private Statistics st = new Statistics();
    private String name;
    private Thread lightCheck;
    private boolean pauseRequest, resumeRequest;
    private AnchorPane mainPane;

    private Lane[] sd;
    public Thread checkForMovement;
    private boolean completed = false;
    private boolean moving;
    private int indexOfNextActLane = -1;
    private boolean wasFUll = false;
    private Car advise_car_behind;
    // var for 

    public Car(Roads roads, ImageView car) {
        this.roads = roads;
        this.car = car;
//        gTransition = new SequentialTransition();
//        gTransition.setOnFinished((d) -> {
//            st.addEntry(System.currentTimeMillis() - initTimer);
//
//            completed = true;
//        });

    }

    public String getCarName() {
        return name;
    }

    public void setCarName(String name) {
        this.name = name;
    }

    public void pauseRequest() {

        this.pauseRequest = true;
        this.resumeRequest = false;

        Car current = this;
        double nextlo;
        new Thread() {
            public void run() {
                sd[currentLaneIndex].advise(current);
            }
        }.start();

//        gTransition.pause();
        pauseRequest = false;
        isPause = true;
    }

    public void specialPauseRequest() {

        this.resumeRequest = false;

        isPause = true;
        gTransition.pause();
    }

    public boolean isPaused() {
        return isPause;
    }

    public void setMainPane(AnchorPane main) {
        this.mainPane = main;
    }

    public void advisedOfMov() {
        prepChange();

    }

    public void setAdvise_car_behind(Car advise_car_behind) {
        this.advise_car_behind = advise_car_behind;
    }

    public void resumeRequest(boolean isfirst) {
        if (isPause) {
            if (isfirst) {
                isPause = false;
                pauseRequest = false;
                resumeRequest = false;

                if (changeLane()) {

                    starter();
                    gTransition.play();
                }
            } else {
                Car carBehind = sd[currentLaneIndex].getnextCar(this);

                pauseRequest = false;
                resumeRequest = false;
                isPause = false;
                gTransition.play();

                if (carBehind != null) {
                    new Thread() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(new Random().nextInt(2000));
                                isPause = false;
                                carBehind.resumeRequest(false);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }.start();
                }

            }
        }
    }

    private int checkNextcLane(int index) {

        if (index < sd.length) {
            if (!sd[index].getName().contains("inter") && !sd[index].getName().contains("end")) {
                return index;
                //return 
            } else {
                return checkNextcLane(index + 1);
            }
        } else {
            return -1;
        }

    }

    /**
     * move from one path to anoter
     *
     * @param old
     * @param newLane
     */
    private boolean changeLane() {
        boolean success = false;
        boolean isFull = false;
        System.out.println("Chganging lane for " + name);
        if (indexOfNextActLane == -1) {
            indexOfNextActLane = checkNextcLane(currentLaneIndex + 1);
        }

        if (indexOfNextActLane != -1) {
            isFull = sd[indexOfNextActLane].isFull();

            if (!isFull) {
                if (sd[currentLaneIndex].getName().equalsIgnoreCase("lane 2")) {
                    System.out.println("");
                }
                int size = 0;
                for (int i = currentLaneIndex + 1; i < indexOfNextActLane + 1; i++) {
                    size += sd[i].getSize();
                }
                if (size > sd[indexOfNextActLane].getQCapacity()) {
                    System.out.println("Said that it was not full buut that wasnt the casase");
                    isFull = true;
                }
            }

        }
        if (!isFull) {
            if (wasFUll) {
                Car cc = sd[indexOfNextActLane].getLasCar();
                if (cc != null) {
                    if (cc.isPaused()) {
                        return false;

                    } else {

                        sd[currentLaneIndex].removeCar(this);
                        if (!sd[currentLaneIndex].isIsEnd()) {
                            currentLaneIndex++;
                            sd[currentLaneIndex].addCar(this);
                            indexOfNextActLane = -1;
                            wasFUll = false;
                            return true;

                        } else {
                            System.out.println("is end");
                            return false;
                        }

                    }
                }
            } else {

                sd[currentLaneIndex].removeCar(this);
                if (!sd[currentLaneIndex].isIsEnd()) {
                    currentLaneIndex++;
                    sd[currentLaneIndex].addCar(this);
                    indexOfNextActLane = -1;
                    wasFUll = false;
                    return true;

                } else {
                    System.out.println("is end");
                    return false;
                }

            }
        }
        wasFUll = true;
        return false;

    }

    @Override
    public String toString() {
        return name;
    }

    private void setDistance(Lane n, int distance) {
        if (n.isIsHorizontal()) {
            if (n.getEndX() - n.getBeginingX() > 0) {
                gTransition.setFromX(0);
                gTransition.setToX(distance);
            } else if (n.isIsRightToLeft()) {
                gTransition.setFromX(0);
                gTransition.setToX(-distance);

            }
            if (distance == 0) {
                gTransition.setFromY(0);
                gTransition.setToY(0);
            } else if (n.getyRate() != 0) {
                gTransition.setFromY(0);
                gTransition.setToY(n.getyRate());
            }
        } else if (n.isVertical()) {
            if (n.getEndY() - n.getBeginingy() > 0) {
                gTransition.setFromY(0);
                gTransition.setToY(distance);
            } else if (n.isIsDownToUp()) {
                gTransition.setFromY(0);
                gTransition.setToY(-distance);
            }
        } else {

            gTransition = null;
            Platform.runLater(() -> {
                car.setVisible(false);
                mainPane.getChildren().remove(car);
            });
        }
    }

    private void prox(Lane n, int lowthreshold, int tooLowthreshold) {
        double myLocation = 0;
        double carInFrontLocation = 0;
        try {

            Car carInFront = n.getCarInFront(this);
            if (n.isIsHorizontal()) {
                myLocation = car.getX();
                if (carInFront != null) {
                    carInFrontLocation = carInFront.getCarImage().getX();

                }
            } else {
                myLocation = car.getY();
                if (carInFront != null) {
                    carInFrontLocation = carInFront.getCarImage().getY();

                }
            }
            if (carInFront != null && !n.isFirst(this) && Math.abs(carInFrontLocation - myLocation) < lowthreshold) {
                if (Math.abs(carInFrontLocation - myLocation) < tooLowthreshold) {

//pause when they are too close
                    isPause = true;
                    setDistance(n, 0);

                } else {
                    isPause = false;
                    setDistance(n, 10);
                    gTransition.setDuration(Duration.millis(600));
                    if (advise_car_behind != null) {
                        advise_car_behind.advisedOfMov();
                    }
                }

            } else {
                isPause = false;
                setDistance(n, 15);
                gTransition.setDuration(Duration.millis(100));

            }
        } catch (NullPointerException sn) {
            System.err.println("null pointer expcpetion in tesdt for car " + name);
        }
    }

    private boolean endReached() {
        Lane n = sd[currentLaneIndex];
        if (n.isIsHorizontal()) {
            if (n.isIsRightToLeft()) {
                return !(car.getX() >= n.getEndX());
            } else if (n.getEndX() - n.getBeginingX() > 0) {
                return !(car.getX() < n.getEndX());
            }

        } else {

            if (n.isIsDownToUp()) {
                return !(car.getY() >= n.getEndY());
            } else if (n.getEndY() - n.getBeginingy() > 0) {

                return !(car.getY() <= n.getEndY());
            }
        }
        return false;
    }

    private void movement(Lane n) {
        car.setOnMouseClicked((g) -> {
            System.out.println("I'm car " + name);
            System.out.println("I'm on lane " + n.getName());
            System.out.println("X location: " + car.getX());
            System.out.println("Y Location: " + car.getY());
        });
        setDistance(n, 15);

        gTransition.setOnFinished((d) -> {

            gTransition.setDuration(Duration.millis(100));
// setting the new location of the car

            car.setX(car.getX() + car.getTranslateX());
            car.setY(car.getY() + car.getTranslateY());
            car.setTranslateX(0);
            car.setTranslateY(0);

            if (endReached()) {
                if (n.isGreen()) {
                    prepChange();
                } else {

                    pauseRequest();
                }
            } else {

                prox(sd[currentLaneIndex], 80, 60);
                gTransition.play();
            }

        }
        );

    }

    private void prepChange() {
        try {
            if (currentLaneIndex + 1 < sd.length) {

                if (changeLane()) {

                    Lane n = sd[currentLaneIndex];
                    System.out.println(n);
                    gTransition = new TranslateTransition();
                    gTransition.setNode(car);
                    movement(n);
                    car.setX(n.getBeginingX());
                    car.setY(n.getBeginingy());
                    car.setRotate(n.getAngle());
                    gTransition.setDuration(Duration.millis(100));
                    gTransition.play();
                    isPause = false;
                } else {
                    try {
                        if (indexOfNextActLane == -1) {
                            indexOfNextActLane = checkNextcLane(currentLaneIndex + 1);
                        }

                        if (indexOfNextActLane != -1) {
                            Car cc = sd[indexOfNextActLane].getLasCar();
                            if (cc != null) {

                                setDistance(sd[currentLaneIndex], 0);
                                cc.setAdvise_car_behind(this);

                            }
                        }

                    } catch (Exception d) {
                        System.err.println("Exception in prep waiting change:");
                        System.err.println("car " + name);
                    }

                }
            } else {
                gTransition = null;
                sd[currentLaneIndex].removeCar(this);
                car.setVisible(false);
                Platform.runLater(() -> {
                    mainPane.getChildren().remove(car);
                });
            }
        } catch (Exception k) {
            System.err.println("Exception in prep change: " + k.getMessage());
            System.err.println("car " + name);
        }
    }

    public void close() {
        try {
            gTransition.stop();

            mainPane.getChildren().remove(car);
        } catch (Exception m) {

        }
    }

    public void starter() {

        if (sd == null) {

            sd = roads.geFullPAth(null);
        }
        Lane n = sd[currentLaneIndex];
        if (currentLaneIndex == 0) {
            n.addCar(this);
        }
        car.setX(n.getBeginingX());
        car.setY(n.getBeginingy());
        car.setRotate(n.getAngle());
        gTransition = new TranslateTransition();
        gTransition.setDuration(Duration.millis(100));

        gTransition.setNode(car);
        movement(n);

    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isMoving() {
        return moving;
    }

    public ImageView getCarImage() {
        return car;
    }

    public void startSimulation() {
        Platform.runLater(() -> {
            gTransition.play();
        });
    }

}
