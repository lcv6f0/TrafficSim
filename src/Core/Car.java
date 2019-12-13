/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

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
 * @author Luc
 */
public class Car {

    private final Roads roads;
//    private SequentialTransition gTransition;
    private TranslateTransition gTransition;

    private int currentLaneIndex;

    private final ImageView car;
    private boolean isPause;
    private long initTimer;
    private String name;

    private AnchorPane mainPane;

    private Lane[] sd;

    private boolean completed = false;
    private boolean moving;
    private int indexOfNextActLane = -1;
    private boolean wasFUll = false;
    private Car advise_car_behind;
    private boolean adviseLaneOfMov;
    // var for 

    public Car(Roads roads, ImageView car) {
        this.roads = roads;
        this.car = car;
    }

    public String getCarName() {
        return name;
    }

    public void setCarName(String name) {
        this.name = name;
    }

    public void setAdviseLaneOfMov(boolean adviseLaneOfMov) {
        this.adviseLaneOfMov = adviseLaneOfMov;
    }

    public void pauseRequest() {

        Car current = this;
        double nextlo;
        new Thread() {
            public void run() {
                sd[currentLaneIndex].advise(current);
            }
        }.start();

//        gTransition.pause();
        isPause = true;
    }

    public boolean isPaused() {
        return isPause;
    }

    public void setMainPane(AnchorPane main) {
        this.mainPane = main;
    }

    public void advisedOfMov() {

        setDistance(sd[currentLaneIndex], 0);
        gTransition.play();
    }

    public void setAdvise_car_behind(Car advise_car_behind) {
        this.advise_car_behind = advise_car_behind;
    }

    private Car getNextAvCar() {
        Car c = null;
        for (int i = currentLaneIndex + 1; i < sd.length; i++) {
            c = sd[i].getLasCar();
            if (c != null) {
                return c;
            }
        }
        return c;
    }

    public void resumeRequest(boolean isfirst) {
        if (isPause) {
            if (isfirst) {
                isPause = false;

                prepChange();
            } else {
                Car carBehind = sd[currentLaneIndex].getnextCar(this);

                isPause = false;
                 setDistance(sd[currentLaneIndex], 1);
               gTransition.setDuration(Duration.millis(100));
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

    private void adviseLaneofMovement() {

        sd[currentLaneIndex].enableNextCar();

        adviseLaneOfMov = false;
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

    private void changeLane() {
        sd[currentLaneIndex].removeCar(this);
        currentLaneIndex++;
        sd[currentLaneIndex].addCar(this);
        indexOfNextActLane = -1;
        wasFUll = false;
    }

    /**
     * move from one path to another
     *
     * @param old
     * @param newLane
     */
    private boolean canBeChanged() {
        boolean isFull = false;

        if (indexOfNextActLane < 0) {
            indexOfNextActLane = checkNextcLane(currentLaneIndex + 1);
        }

        if (indexOfNextActLane > 0) {
            isFull = sd[indexOfNextActLane].isFull();

            if (!isFull) {
                int size = 0;
                for (int i = currentLaneIndex + 1; i < indexOfNextActLane + 1; i++) {
                    size += sd[i].getSize();

                }
                isFull = (size >= sd[indexOfNextActLane].getQCapacity());
            }

        }
        if (!isFull) {
            if (wasFUll) {
                Car cc = null;
                if (indexOfNextActLane > 0) {
                    cc = sd[indexOfNextActLane].getLasCar();
                }

                if (cc != null) {
                    if (cc.isPaused()) {
                        setDistance(sd[currentLaneIndex], 0);
                        cc.setAdvise_car_behind(this);
                        return false;

                    }
                }
            }
        } else {
            wasFUll = true;
            Car cc = null;
            if (indexOfNextActLane > 0) {
                cc = sd[indexOfNextActLane].getLasCar();
            }

            if (cc != null) {
//                    if (cc.isPaused()) {
                setDistance(sd[currentLaneIndex], 0);
                cc.setAdvise_car_behind(this);
            }
            return false;

        }

        return true;

    }

    @Override
    public String toString() {
        return name;
    }

    public Lane getCurrentLane() {
        return sd[currentLaneIndex];
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
            } else if (n.getyRate() != 0.0) {
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

    private void proximity(Lane n, int lowthreshold, int tooLowthreshold) {
        double myLocation = 0;
        double carInFrontLocation = 0;
        try {

            Car carInFront = n.getCarInFront(this);
            if (carInFront == null) {
                carInFront = getNextAvCar();
            }
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
//            if (carInFront != null && !n.isFirst(this) && Math.abs(carInFrontLocation - myLocation) < lowthreshold) {
            if (carInFront != null) {
                if ((carInFront.getCurrentLane().isIsHorizontal() == n.isIsHorizontal())) {

                    if (Math.abs(carInFrontLocation - myLocation) < lowthreshold) {
                        if (Math.abs(carInFrontLocation - myLocation) < tooLowthreshold) {

//pause when they are too close
                            isPause = true;
                            setDistance(n, 0);
                            

                        } else {
                            isPause = false;
                            setDistance(n, 8);
                            gTransition.setDuration(Duration.millis(600));
                            if (advise_car_behind != null) {
                                advise_car_behind.advisedOfMov();
                                advise_car_behind = null;
                            } 
                           
                        }

                    } else {
                        isPause = false;
                        setDistance(n, 15);
                        gTransition.setDuration(Duration.millis(100));

                        if (advise_car_behind != null) {
                            advise_car_behind.advisedOfMov();
                            advise_car_behind = null;
                        }
                        if (adviseLaneOfMov) {
                            adviseLaneofMovement();
                        }

                    }
                }
            }else{
                isPause = false;
                        setDistance(n, 15);
                        gTransition.setDuration(Duration.millis(100));

                        if (advise_car_behind != null) {
                            advise_car_behind.advisedOfMov();
                            advise_car_behind = null;
                        }
                        if (adviseLaneOfMov) {
                            adviseLaneofMovement();
                        }
            }
        } catch (NullPointerException sn) {
            System.err.println("null pointer expcpetion in tesdt for car " + name);
            sn.printStackTrace();
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
     
        car.setOnContextMenuRequested((d) -> {
            Platform.runLater(() -> {
                car.toFront();
                car.setVisible(true);
            });
        });
        setDistance(n, 15);

        gTransition.setOnFinished((d) -> {
            if (gTransition != null) {
                gTransition.setDuration(Duration.millis(100));
// setting the new location of the car
                Platform.runLater(() -> {
                    car.setX(car.getX() + car.getTranslateX());
                    car.setY(car.getY() + car.getTranslateY());
                    car.setTranslateX(0);
                    car.setTranslateY(0);
                    car.setVisible(true);
                });

                if (endReached()) {
                    if (n.isGreen()) {
                        prepChange();
                    } else {

                        pauseRequest();
                    }
                } else {

                    proximity(sd[currentLaneIndex], 90, 70);

                    gTransition.play();
                }

            }
        }
        );

    }

    private void prepChange() {
        try {
            if (!sd[currentLaneIndex].isEnd()) {
                
                if (canBeChanged()) {
                    if (adviseLaneOfMov) {
                            adviseLaneofMovement();
                        }
                    changeLane();
                    Lane n = sd[currentLaneIndex];
                  
                    gTransition = new TranslateTransition();
                    gTransition.setNode(car);
                    movement(n);
                    Platform.runLater(() -> {
                        car.setX(n.getBeginingX());
                        car.setY(n.getBeginingy());
                        car.setRotate(n.getAngle());
                    });

                    gTransition.setDuration(Duration.millis(100));
                    gTransition.play();
                    isPause = false;
                }else {
                    isPause=true;
                }
            } else {

                sd[currentLaneIndex].removeCar(this);

                Platform.runLater(() -> {
                    car.setVisible(false);
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
            Platform.runLater(() -> {
                mainPane.getChildren().remove(car);
            });

        } catch (Exception m) {

        }
    }

    public void starter() {

        if (sd == null) {

            sd = roads.geFullPAth();
        }
        Lane n = sd[currentLaneIndex];
        if (currentLaneIndex == 0) {
            n.addCar(this);
        }
        Platform.runLater(() -> {
            car.setX(n.getBeginingX());
            car.setY(n.getBeginingy());
            car.setRotate(n.getAngle());
            gTransition = new TranslateTransition();
            gTransition.setDuration(Duration.millis(100));

            gTransition.setNode(car);
            movement(n);
        });

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
            car.setVisible(true);
            gTransition.play();
        });
    }

}
