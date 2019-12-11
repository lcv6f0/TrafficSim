/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Core.Roads;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luc
 */
public class ArrayQueue<E> implements Cloneable, Queue<E> {

    public static final int DEFAULT_CAPACITY = 2000000;
    private int f, numElem;
    private int capacity;
    private E s[];

    public ArrayQueue() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayQueue(int capacity) {
        this.capacity = capacity;
        f = 0;
        numElem = 0;
        s = (E[]) new Object[capacity];
    }

    // Returns the number of elements in the queue
    public int size() {
        return numElem;
    }

    // Returns true if queue is empty.
    @Override
    public boolean isEmpty() {
        return numElem == 0;
    }

    // Returns true if queue is full.
    @Override
    public synchronized boolean isFull() {
        return numElem == s.length;
    }

    //Returns the front item from the queue.
    //But does not remove it.
    @Override
    public synchronized E front() throws QueueException {
        if (isEmpty()) {
            throw new QueueException("Queue is emptty");
        }
        return s[f];
    }

    public synchronized int getCapacity() {
        return s.length;
    }

    // Inserts item at the rear of the queue.
    @Override
    public synchronized void enqueue(E item) throws QueueException {
        if (isFull()) {
            throw new QueueException("Queue is full, remove a few things first");
        }
        int freeIndex = (f + numElem) % s.length;
        s[freeIndex] = item;
        numElem++;
    }

    // Inserts item at the rear of the queue.
    @Override
    public synchronized E dequeue() throws QueueException {
        if (isEmpty()) {
            throw new QueueException("Queue is empty, add something first");
        }
        E item = s[f];
        s[f] = null;
        f = (f + 1) % s.length;
        numElem--;
        return item;
    }

    //To use to display the content of the queue.
    @Override
    public String toString() {
        int ff = f, r = (f + numElem) % s.length;
        String str = "<";
        if (size() > 0) {
            str += s[ff];
        }
        ff = (ff + 1) % s.length;
        while (ff != r) {
            str += ", " + s[ff];
            ff = (ff + 1) % s.length;
        }
        return str + ">";
    }

    /**
     * Return -1 if the item provided is not found
     *
     * @param item
     * @return
     */
    public synchronized int search(E item) {

        int ff = f, r = (f + numElem) % s.length;
        int counter = 1;
        if (size() > 0) {
            if (s[ff] == item) {
                return counter;
            }
        }
        ff = (ff + 1) % s.length;
        counter++;
        while (ff != r) {
            if (s[ff] == item) {
                return counter;
            }
            counter++;
            ff = (ff + 1) % s.length;
        }
// need to finish redign  this
/*
        int counter = 0;
        for (int i = f; i < s.length + f; i++) {
            if (s[i % s.length] == item) {
                return counter + 1;
            }
            if (s[i % s.length] != null) {
                counter++;
            }
        }
         */
        return -1;
    }

    public synchronized E getPrevElement(E item) {
        E previous = null;
        int ff = f, r = (f + numElem) % s.length;

        if (size() > 0) {
            previous = s[ff];
        }
        ff = (ff + 1) % s.length;
        while (ff != r) {
            if (s[ff] == item) {
                return previous;
            }
            previous = s[ff];
            ff = (ff + 1) % s.length;
        }
// need to finish redign  this
/*
        for (int i = f; i < s.length + f; i++) {

            if (s[f] != null && s[f] == item) {
                return null;
            } else if (s[f] != null && s[i % s.length] == item && i % s.length > 0) {
                return s[(i - 1) % s.length];
            } else if (s[f] != null && s[i % s.length] == item && i % s.length == 0) {
                return s[numElem - 1];
            }
    }
         */
        return null;
    }

    public synchronized boolean IsFirts(E item) {
        try {
            return front().equals(item);
        } catch (QueueException ex) {
            Logger.getLogger(ArrayQueue.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public synchronized E getNextElement(E item) {

        int ff = f, r = (f + numElem) % s.length;

        if (size() > 0) {
            if (s[ff] == item) {
                return s[(ff + 1) % s.length];
            }
        }
        ff = (ff + 1) % s.length;
        while (ff != r) {
            if (s[ff] == item) {
                return s[(ff + 1) % s.length];
            }
            ff = (ff + 1) % s.length;
        }
        return null;
// need to finish redign  this
/*
        int index = search(item);

        if (s[(index + f + s.length - numElem) % s.length] == s[f]) {
            return null;
        } else if (s[f] != item) {
            return (s[(index + f + s.length - numElem) % s.length]);
        } else {
            return (s[(index + f + s.length - numElem) % s.length]);
        }
         */
    }

    public synchronized E getLastElement() {
        if (numElem > 0) {
            return s[(f + numElem - 1) % (s.length)];
        }
        return null;
    }

    @Override
    public Object clone() {

        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Roads.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}

/**
 *
 * @author L
 */
interface Queue<E> {

    // Returns true if queue is empty.
    public boolean isEmpty();

    //Returns the front item from the queue.
    //But does not remove it.
    public E front() throws QueueException;

    // Inserts item at the rear of the queue.
    public void enqueue(E item) throws QueueException;

    // Removes an item from the rear the queue and returns it.
    public E dequeue() throws QueueException;

    // Returns true if the queue is full.
    public boolean isFull();
}
