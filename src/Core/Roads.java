/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luc
 */
public class Roads implements Cloneable {
//private static Random ran = new Random(14);

    private static Random ran = new Random();

    private ArrayList<Lane> list;

    public Roads() {
        list = new ArrayList<>();
    }

    public void addRoad(Lane r) {
        list.add(r);
    }

    public void addRoad(Collection<Lane> r) {
        list.addAll(r);
    }

    public synchronized Lane getRandomStartingLane() {
        //not completed
        Lane path = null;
        Lane r;

        while (true) {

            r = list.get(ran.nextInt(list.size() - 2));
            if (r.isStartingPoint() && r != null) {
                return r;
            }
        }

    }

    public synchronized ArrayList<Lane> getRoadList() {
        return list;
    }

    public void done() {
        list.forEach((d) -> {
            if (d.isStartingPoint()) {
                new Thread() {
                    public void run() {
                        d.disburse();
                    }
                }.start();

            }
        });
    }

    public synchronized Lane[] geFullPAth() {
        Lane[] bn = null;
        ArrayList<Lane> s1 = new ArrayList<>();

        try {

            Lane tempLane = getRandomStartingLane();

            s1.add(tempLane);
            int counter = 0;

            while (!tempLane.isEnd() || tempLane == null) {
                tempLane = tempLane.getNextPath();
                if (tempLane != null) {
                    s1.add(tempLane);
                }

            }
            bn = new Lane[s1.size()];
            for (int i = 0; i < s1.size(); i++) {
                bn[i] = s1.get(i);
            }
        } catch (Exception d) {
            System.err.println("Exception while trying to calculate path. Message: " + d.getMessage());
            System.err.println("Content of array: " + Arrays.toString(bn));
        }
        return bn;

    }

    /**
     * Get the full path starting from a lane
     *
     * @param initLane
     * @return
     */
    public synchronized Lane[] geFullPAth(Lane initLane) {
        initLane = list.get(0);
        Lane[] bn = null;
        ArrayList<Lane> s1 = new ArrayList<>();
        try {

            Lane tempLane = initLane;
            s1.add(tempLane);
            int counter = 0;

            while (!tempLane.isEnd() || tempLane == null) {
                tempLane = tempLane.getNextPath();
                if (tempLane != null) {
                    s1.add(tempLane);
                }

            }
            bn = new Lane[s1.size()];
            for (int i = 0; i < s1.size(); i++) {
                bn[i] = s1.get(i);
            }
        } catch (Exception d) {
            System.err.println("Exception while trying to calculate path. Message: " + d.getMessage());
            System.err.println("Content of array: " + Arrays.toString(bn));

        }
        return bn;

    }

    @Override
    public Object clone() {

        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Roads.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
