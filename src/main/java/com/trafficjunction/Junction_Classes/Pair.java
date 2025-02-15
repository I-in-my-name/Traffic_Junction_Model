package com.trafficjunction.Junction_Classes;

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

    public V getRight() {
        return right;
    }
}