/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.ArrayList;

/**
 *
 * @author Christian
 */
public class PairList<E, E1> {

    private ArrayList<Pair> pairList;
    private int a = -1, b = -1, c = -1, d = -1, e = -1, f = -1, g = -1, h = -1, i = -1, j = -1, k = -1, l = -1, m = -1, n = -1, o = -1, p = -1, q = -1, r = -1, s = -1, t = -1, u = -1, v = -1, w = -1, x = -1, y = -1, z = -1;

    public PairList() {

        pairList = new ArrayList<>();
    }

    public void add(E key, E1 value) {

        pairList.add(new Pair(key, value));
    }
    public void clear (){
        pairList.clear();
    }
    public int size (){
        return pairList.size();
    }
public boolean isEmpty(){
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
    public String toString(){
        
        StringBuilder sb = new 
            StringBuilder();
        for (int i1 = 0; i1 < pairList.size(); i1++) {
            sb.append(pairList.get(i1).toString());
        }
        return sb.toString();
    }
}
