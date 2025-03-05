package com.trafficjunction.Junction_Classes;

class Triple<K, V, X> { // amount of time the taffic light will be in a given state
// the state (e.g. red =)
    K left;
    V middle;
    X right;
    
    public Triple(K k, V v, X x) {
        left = k;
        middle = v;
        right = x;
    }

    public K getLeft() {
        return left;
    }
    public V getMiddle() {
        return middle;
    }
    public X getRight() {
        return right;
    }

    public void setLeft(K k) {
        this.left = k;
    }
    public void setMiddle(V v) {
        this.middle = v;
    }
    public void setRight(X x) {
        this.right = x;
    }

    @Override
    public String toString() {
        return "("+ this.left.toString() + ", " + this.middle.toString() + ", " + this.right.toString() + ")";
    }
}