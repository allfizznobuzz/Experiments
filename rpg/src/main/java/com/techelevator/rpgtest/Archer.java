package com.techelevator.rpgtest;

public class Archer extends Character{

    ExperienceTracker archerTracker = new ExperienceTracker(10,10,5,15,5);

    public Archer (String heroName, String heroType) {
        super(heroName, heroType);
    }
}
