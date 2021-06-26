package com.techelevator.rpgtest;

public class Barbarian extends Character{

    ExperienceTracker barbarianTracker = new ExperienceTracker(5,5,20,5,5);

    public Barbarian (String heroName, String heroType) {
        super(heroName, heroType);
    }

}
