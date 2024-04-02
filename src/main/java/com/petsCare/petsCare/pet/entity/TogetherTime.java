package com.petsCare.petsCare.pet.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDate;

@Embeddable
@Getter
public class TogetherTime {

    private LocalDate bornTime;
    private LocalDate leaveTime;

    protected TogetherTime() {
    }

    public TogetherTime(LocalDate bornTime) {
        this.bornTime = bornTime;
    }

    public void leave(LocalDate leaveTime) {
        this.leaveTime = leaveTime;
    }
}
