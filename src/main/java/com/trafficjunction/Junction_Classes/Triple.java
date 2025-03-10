package com.trafficjunction.Junction_Classes;

/**
 * Class used to strore 3 values together in a 'triple'
 * <p>
 * @param <K> the type of the first element (left)
 * @param <V> the type of the second element (middle)
 * @param <X> the type of the third element (right)
 */
public class Triple<K, V, X> {
    
    // The first (left) value of the tripple
    private K left;
    
    // The second (middle) value of the tripple
    private V middle;
    
    // The third (right) value of the tripple
    private X right;
    
    /**
     * Constructs a new Triple with the given values
     * <p>
     * @param k the value for the left element
     * @param v the value for the middle element
     * @param x the value for the right element
     */
    public Triple(K k, V v, X x) {
        this.left = k;
        this.middle = v;
        this.right = x;
    }

    /**
     * Retrieves the left element of the Triple
     * <p>
     * @return the left element
     */
    public K getLeft() {
        return left;
    }

    /**
     * Retrieves the middle element of the Triple
     * <p>
     * @return the middle element
     */
    public V getMiddle() {
        return middle;
    }

    /**
     * Retrieves the right element of the Triple
     * <p>
     * @return the right element
     */
    public X getRight() {
        return right;
    }

    /**
     * Sets the left element of the Triple
     * <p>
     * @param k the new value for the left element
     */
    public void setLeft(K k) {
        this.left = k;
    }

    /**
     * Sets the middle element of the Triple
     * <p>
     * @param v the new value for the middle element
     */
    public void setMiddle(V v) {
        this.middle = v;
    }

    /**
     * Sets the right element of the Triple.
     * <p>
     * @param x the new value for the right element
     */
    public void setRight(X x) {
        this.right = x;
    }

    /**
     * Returns a string representation of the Triple.
     * <p>
     * @return a string in the format (left, middle, right)
     */
    @Override
    public String toString() {
        
        // Creates a string with brackets and commas to represtent the triple in string format
        return "("+ this.left.toString() + ", " + this.middle.toString() + ", " + this.right.toString() + ")";
    }
}