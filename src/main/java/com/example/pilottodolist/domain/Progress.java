package com.example.pilottodolist.domain;

import lombok.Getter;

@Getter
public enum Progress {
    ACTIVE,COMPLETED;

    public static Progress of(String progress) {

        if(progress == null) return null;
        switch(progress) {
            case "ACTIVE":
                return Progress.ACTIVE;
            case "COMPLETED" :
                return Progress.COMPLETED;
            default :
                return null;
        }
    }
}
