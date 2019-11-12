/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Utils.PairList;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Christian
 * Not Used 
 */
public class Road {

//declare the fields
    private ArrayList<Lane> lanes;
    private int maxSpeed, minSpeed;
    private String name;
    private boolean isMiddle;
    private boolean isStartingPoint;
    private Lane right, left, middle;
    private PairList<Road, Integer> nextRoad;

    public boolean isIsMiddle() {
        return isMiddle;
    }

    public boolean isIsStartingPoint() {
        return isStartingPoint;
    }

    public boolean isIsEnd() {
        return nextRoad.isEmpty();
    }
    private boolean isEnd;
    private int timeCompletion;

    public String getName() {
        return name;
    }

    public Road() {
        lanes = new ArrayList<>();
        minSpeed = 25;
        maxSpeed = 35;
    }

    /**
     * This constructoRequired information are the name of the street.rwhere it
     * starts, where it ends and the min and max speed min and max speed
     *
     * @param name
     *
     *
     * @param minSpeed
     * @param maxSpeed
     * @param isStartingPoint
     */
    public Road(String name, int minSpeed, int maxSpeed, boolean isStartingPoint) {
        this.name = name;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        lanes = new ArrayList<>();
        this.isStartingPoint = isStartingPoint;
        nextRoad = new PairList<>();

    }

    public Road(String name, int minSpeed, int maxSpeed, boolean isStartingPoint, ArrayList<Lane> lanes, Road nextRoad) {
        this.lanes = lanes;
        this.name = name;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        lanes = new ArrayList<>();
        this.isStartingPoint = isStartingPoint;

    }

    public void addLane(Lane l) {
        lanes.add(l);

    }

    public void addPossibleRoad(Road r, int i) {

        nextRoad.add(r, i);
        if (!isStartingPoint && nextRoad.isEmpty()) {
            isEnd = true;
        }
    }

    public void addLanes(Collection<Lane> l) {
        lanes.addAll(l);
    }

    public double getMinSpeed() {
// Return the min speed of the road
        return 0.0;
    }

    public double getTimeToEnd() {
        return timeCompletion;
    }

    public double getMaxSpeed() {
//Return the max speed of the road
        return 0.0;
    }

    public String getStreetname() {
        //return the street name
        return "";
    }

    public ArrayList<Lane> getPaths() {
        return lanes;
    }

    public boolean isStartingPoint() {
        return isStartingPoint;
    }

    public Road getNextRoad(Lane l) {

        for (int i = 0; i < nextRoad.size(); i++) {
            if (nextRoad.get(i).getValue() == l.getDirrection() || nextRoad.get(i).getValue() == Lane.STRAIGHT) {
                return nextRoad.get(i).getKey();
            }
        }

        return null;
    }

    public Lane getNextPath(int laneId) {
        for (int i = 0; i < lanes.size(); i++) {
            if (laneId == lanes.get(i).getDirrection()) {
                return lanes.get(i);
            }
        }
        return null;
    }

    public int getAmountOfnextRoas() {
        return nextRoad.size();
    }
    public String toString(){
        return name;
    }
}
