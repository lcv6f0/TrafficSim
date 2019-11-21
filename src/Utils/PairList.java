/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.ArrayList;

/**
 *
 * @author List
 */
public class PairList<E, E1> {

    private ArrayList<Pair> pairList;

    public PairList() {

        pairList = new ArrayList<>();
    }

    public void add(E key, E1 value) {

        pairList.add(new Pair(key, value));
    }

    public void clear() {
        pairList.clear();
    }

    public int size() {
        return pairList.size();
    }

    public boolean isEmpty() {
        return pairList.isEmpty();
    }

    public Pair<E, E1> get(int i) {
        return pairList.get(i);
    }

    public E1 get(E key) {

        for (Pair pair : pairList) {
            if (pair.getKey().equals(key)) {
                return (E1) pair.getValue();
            }
        }
        return null;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (int i1 = 0; i1 < pairList.size(); i1++) {
            sb.append(pairList.get(i1).toString());
        }
        return sb.toString();
    }
}
