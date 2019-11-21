/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

/**
 * Might not need this
 *
 * @author Luc
 */
public class Statistics {

    //Create a list of possible paths, FOrmats TBD
    private long total = 0;

    private int amount_Of_car;

    public synchronized void addEntry(long time) {
        total += time;
    }

    public void setAmount_Of_car(int amount_Of_car) {
        this.amount_Of_car = amount_Of_car;
    }

    public void exit() {

        System.out.println(total / amount_Of_car);
    }

}
