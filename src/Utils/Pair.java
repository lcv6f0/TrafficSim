/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author Luc
 * @param <E>
 * @param <E1>
 */
public class Pair<E, E1> {

    private final E key;
    private final E1 value;

    public Pair(E key, E1 value) {
        this.key = key;
        this.value = value;
    }

    public E getKey() {
        return this.key;
    }

    public E1 getValue() {
        return this.value;
    }

    public String toString() {
        return key + "," + value+",";
    }
}
