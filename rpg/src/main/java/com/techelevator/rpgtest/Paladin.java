package com.techelevator.rpgtest;

public class Paladin extends Character{

    ExperienceTracker paladinTracker = new ExperienceTracker(10,10,15,5,5);

    public Paladin (String heroName, String heroType) {
        super(heroName, heroType);
    }

}
