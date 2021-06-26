package com.techelevator.rpgtest;

public class Mage extends Character{

    ExperienceTracker mageTracker = new ExperienceTracker(10,10,5,5,15);

    public Mage (String heroName, String heroType) {
        super(heroName, heroType);
    }

}
