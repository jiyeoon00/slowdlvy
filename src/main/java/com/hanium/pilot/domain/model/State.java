package com.hanium.pilot.domain.model;

public enum State {
    ACTIVE,DELETED,COMPLETED;


    public static boolean contains(String state) {
        for (State s : State.values()) {
            if (s.name().equals(state)) {
                return true;
            }
        }
        return false;
    }
}
