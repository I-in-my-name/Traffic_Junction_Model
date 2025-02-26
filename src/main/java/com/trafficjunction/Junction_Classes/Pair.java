package com.trafficjunction.Junction_Classes;

import javax.crypto.KEM;

class Pair<K, V> { // amount of time the taffic light will be in a given state
// the state (e.g. red =)
    K left;
    V right;
    
    public Pair(K k, V v) {
        left = k;
        right = v;
    }

    public K getLeft() {
        return left;
    }

    public void setLeft(K k) {
        this.left = k;
    }

    public V getRight() {
        return right;
    }

    public void setRight(V v) {
        this.right = v;
    }

    @Override
    public String toString() {
        return "("+ this.left.toString() + ", " + this.right.toString() + ")";
    }
}