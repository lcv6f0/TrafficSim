/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Utils.ArrayQueue;
import Utils.QueueException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 *
 * @author Luc
 */
public class Lane {

    private double beginingX;
    private double beginingy;
    private double angle;
    private double endX;
    private double endY;

    private boolean isGreen = false;
    private boolean isPause = false;
    private ArrayQueue<Car> carQ, waitingQ = new ArrayQueue<>();

    int limit;
    private Car lastCar;

    private boolean isHorizontal, vertical;
    private double a = new Random().nextDouble();

    private Circle light;
    private boolean isStartingPoint;
    private ArrayList<Lane> possiblePaths;
    private boolean end;
    private String name = "default";
    private double rate = 1;
    private long k;
    private boolean isRightToLeft = false;
    private boolean isDownToUp = false;
    private long startTime;
    private int totalCar;

    public Lane(int direction) {

        possiblePaths = new ArrayList<>();
        carQ = new ArrayQueue<>();
    }

    public Lane(String name) {
        while (a == 0) {
            a = new Random().nextDouble();
        }

        possiblePaths = new ArrayList<>();
        this.name = name;
        carQ = new ArrayQueue<>();
    }

    public Lane(String name, int beginingX, int beginingY, int endX, int endY, double angle, double rate) {
        while (a == 0) {
            a = new Random().nextDouble();
        }

        possiblePaths = new ArrayList<>();
        this.name = name;
        carQ = new ArrayQueue<>();
        addCoordonate(beginingX, beginingY, endX, endY, angle, rate);
    }

    public synchronized void advise(Car car) {
        isPause = true;
        Car temp = carQ.getNextElement(car);
        if (temp != null) {
            temp.pauseRequest();
        }
    }

    public void setDisburseRate(int mult) {
        k = (long) (a * mult * Math.PI) * 1000;

        k = 500;

    }

    public void enableNextCar() {
        new Thread() {
            public void run() {
                long k1 = 1500;
                /*
	if the queue is full > let the last car know to let me know when it started to move
	if it isnt full carry on with the usual method
	k in this method is going to be the amount of time it takes for a car to start moving whem the one in front of ut is moving
	
                 */
                while (!waitingQ.isEmpty()) {
                    if ((System.currentTimeMillis() - startTime) / k - totalCar < 1) { //if there isn't any car to let go
                        try {
                            Thread.sleep(k1);
                        } catch (InterruptedException ex) {

                        }
                    } else {
                        if (carQ.isFull()) {
                            if (lastCar != null) {
                                lastCar.setAdviseLaneOfMov(true);// let the last car know that it will have to trigger the nextc ar to show
                                break;
                            }
                        } else {

                            try {
                                Car ds = null;
                                ds = waitingQ.dequeue();
                                carQ.enqueue(ds);
                                lastCar = ds;

                                ds.startSimulation();
                                totalCar++;
                                Thread.sleep(k1);

                            } catch (QueueException | InterruptedException ex) {

                            }
                        }
                    }
                }

            }
        }.start();

    }

    public void disburse() {
        startTime = System.currentTimeMillis();
        enableNextCar();
        /*
        int counter = 0;
        if (!waitingQ.isEmpty()) {
            Car ds = null;

            boolean wasFull = false;
            while (!waitingQ.isEmpty()) {
                Car cc = carQ.getLastElement();

                boolean isYes = false;
                if (cc != null) {
                    isYes = cc.isPaused();
                }
                if (!carQ.isFull()) {
                    if (wasFull) {

                        if (!isYes && carQ.size() < limit) {
                            wasFull = false;
                            try {
                                ds = waitingQ.dequeue();
                                carQ.enqueue(ds);
                                lastCar = ds;
                                ds.getCarImage().setVisible(true);
                                ds.startSimulation();

//                        }
                                Thread.sleep(k);
//                        }); 
                            } catch (QueueException ex) {
                                Logger.getLogger(Lane.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Lane.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (Exception sd) {
                                System.out.println("Cant disburse for lane " + name + " eception: " + sd.getMessage());
//                        System.out.println("Cant disburse for lane " + name);
                            }
                        }

                    } else {

                        try {
                            ds = waitingQ.dequeue();
                            carQ.enqueue(ds);
                            lastCar = ds;
                            ds.startSimulation();
                            ds.getCarImage().setVisible(true);
//                        }

                            Thread.sleep(k);
//                        }); 
                        } catch (QueueException ex) {
                            Logger.getLogger(Lane.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Lane.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception sd) {
                            System.out.println("Cant disburse for lane " + name + " eception: " + sd.getMessage());
//                        System.out.println("Cant disburse for lane " + name);
                        }
                    }

                } else {
                    wasFull = true;
                    counter++;
                    System.out.println(counter + "car should have been disburse during the wait");
                    try {

                        Thread.sleep(k);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Lane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
         */
    }

    public Lane(ArrayList<Lane> possiblePaths) {

        this.possiblePaths = possiblePaths;
        carQ = new ArrayQueue<>();

    }

    public int getSize() {
        return carQ.size();
    }

    public synchronized Circle getLight() {
        return light;
    }

    public synchronized void setStartingPoint(boolean isStarting) {
        this.isStartingPoint = isStarting;
    }

    public synchronized void setEnding(boolean isEnd) {
        this.end = isEnd;
    }

    public synchronized boolean isIsStartingPoint() {
        return isStartingPoint;
    }

    public ArrayQueue<Car> returnQ() {
        return carQ;
    }

    public boolean isStop() {
        return isPause;
    }

    public synchronized Car getCarInFront(Car current) {

        return carQ.getPrevElement(current);
    }

    public synchronized Car getLasCar() {
        if (carQ.isEmpty()) {
            return null;
        }
        return lastCar;
    }

    public int getQCapacity() {
        return carQ.getCapacity();
    }

    public synchronized Car getnextCar(Car current) {
        return carQ.getNextElement(current);
    }

    public synchronized Lane getNextPath() {
        try {
            return possiblePaths.get(new Random().nextInt(possiblePaths.size()));
        } catch (NullPointerException | IndexOutOfBoundsException s) {
            return null;
        }
    }

    private void calculateLimit() {
        limit = 0;
        if (!name.contains("inter") && !name.contains("end")) {
            if (beginingX - endX != 0) {
                limit = (int) (Math.abs(beginingX - endX) / (31 + 20));
                isHorizontal = true;
                System.out.println("Limit of lane " + name + ": " + limit);
            } else if (beginingy - endY != 0) {

                isHorizontal = false;
                limit = (int) (Math.abs(endY - beginingy) / (31 + 20));
                System.out.println("Limit of lane " + name + ": " + limit);
            }
            carQ = new ArrayQueue<Car>(limit);
        } else {
            carQ = new ArrayQueue<Car>();
        }

    }

    public void addCoordonate(int beginingX, int beginingY, int endX, int endY, double angle,  double rate) {

        this.beginingX = beginingX;
        this.beginingy = beginingY;
        this.endX = endX;
        this.endY = endY;
        this.rate = rate;
        this.angle = angle;
        isHorizontal = (beginingX - endX != 0);
        vertical = (beginingY - endY != 0);
        calculateLimit();

    }

    public void setLight(Circle c) {
        isGreen = c.getFill().equals(Paint.valueOf("green"));
        ChangeListener ls = (ChangeListener<Paint>) (ObservableValue<? extends Paint> arg0, Paint oldPropertyValue, Paint newPropertyValue) -> {

            if (newPropertyValue.equals(Paint.valueOf("green"))) {
                isGreen = true;
                isPause = false;
                try {

                    if (!carQ.isEmpty()) {
                        carQ.front().resumeRequest(true);
                        if (!carQ.isEmpty()) {
                            carQ.front().resumeRequest(false);
                        }
                    }
                } catch (QueueException ex) {
                    Logger.getLogger(Lane.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

                isGreen = false;

            }

        };
        this.light = c;
        c.fillProperty().addListener(ls);
    }

    public synchronized boolean isEnd() {
        return end;
    }

    public void addPossiblePaths(Lane n) {
        possiblePaths.add(n);
    }

    public synchronized boolean isStartingPoint() {
        return isStartingPoint;
    }

    public synchronized void addCar(Car car) {
        if (endX - beginingX < 0) {
            isRightToLeft = true;
        }
        if (endY - beginingy < 0) {
            isDownToUp = true;
        }
        if (isStartingPoint) {
            try {
                waitingQ.enqueue(car);
                // carQ.enqueue(car);
            } catch (QueueException ex) {
                Logger.getLogger(Lane.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Car nextCar = getCarInFront(car);
            boolean requestPause = false;
            if (nextCar != null) {
                requestPause = nextCar.isPaused();
            }
//            if (isPause || requestPause) {
//                new Thread() {
//                    public void run() {
//                        car.pauseRequest();
//                    }
//
//                }.start();
//
//                try {
//
//                    carQ.enqueue(car);
//                } catch (QueueException ex) {
//                    Logger.getLogger(Lane.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            } else {
            try {
                carQ.enqueue(car);
                lastCar = car;
            } catch (QueueException ex) {
                Logger.getLogger(Lane.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception v) {
                System.err.println("Exception in Core.Lane.AddCar in lane " + name);
                System.err.println("Error message: " + v.getMessage());

            }
//            System.out.println("---------------------addind---------------------------");
//            System.out.println("car " + car.getCarName() + " added");
//            System.out.println("Lane " + name + " has " + carQ.size() + " cars");
//            System.out.println("------------------------------------------------------");
        }
    }

    public synchronized void removeCar(Car car) {
//        System.out.println("car " + car.getCarName() + " removed");

        try {
            if (!carQ.isEmpty()) {
                carQ.dequeue();
            }

        } catch (QueueException ex) {

            Logger.getLogger(Lane.class.getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println("Lane " + name + "has " + carQ.size() + " cars");

    }

    public synchronized boolean isGreen() {
        if (light == null) {
            return true;
        }
        return isGreen;
    }

    @Override
    public String toString() {
        return name + "";
    }

    public synchronized double getBeginingX() {
        return beginingX;
    }

    public synchronized double getBeginingy() {
        return beginingy;
    }

    public synchronized double getEndX() {
        return endX;
    }

    public synchronized double getEndY() {
        return endY;
    }

    public synchronized void setBeginingX(double beginingX) {
        this.beginingX = beginingX;
    }

    public synchronized void setBeginingy(double beginingy) {
        this.beginingy = beginingy;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    public synchronized int getCarLocation(Car c) {

        return carQ.search(c);
    }

    public boolean isFirst(Car c) {
        return carQ.IsFirts(c);
    }

    public synchronized int amountOfCar() {
        return carQ.size();
    }

    public synchronized double getyRate() {
        return rate;
    }

    public synchronized boolean isIsRightToLeft() {
        return isRightToLeft;
    }

    public synchronized boolean isIsDownToUp() {
        return isDownToUp;
    }

    public synchronized double getAngle() {
        return angle;
    }

    public synchronized boolean isIsPause() {
        return isPause;
    }

    public synchronized boolean isFull() {
        return carQ.isFull();

    }

    public synchronized boolean isEmpty() {
        return carQ.isEmpty();
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized boolean isIsHorizontal() {
        return isHorizontal;
    }

    public synchronized boolean isVertical() {
        return vertical;
    }

}
