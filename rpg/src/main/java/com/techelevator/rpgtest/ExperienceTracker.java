package com.techelevator.rpgtest;

public class ExperienceTracker {

    private final int DEFAULT_STARTING_EXPERIENCE = 0;
    private final int DEFAULT_NEXT_LEVEL = 10;

    private int health;
    private int healthMax;
    private int strength;
    private int range;
    private int magic;
    private int experience = DEFAULT_STARTING_EXPERIENCE;
    private double nextLevel = DEFAULT_NEXT_LEVEL;

    public ExperienceTracker(int health, int healthMax, int strength, int range, int magic) {
        this.health = health;
        this.healthMax = healthMax;
        this.strength = strength;
        this.range = range;
        this.magic = magic;
    }

    public void healthUp(int increase) {
        health = health + increase;
    }

    public void healthDown(int decrease) {
        health = health - decrease;
    }

    public void maxHealthUp(int increase) {
        healthMax = healthMax + increase;
    }

    public void rangeUp(int increase) {
        range = range + increase;
    }

    public void rangeDown(int decrease) {
        range = range - decrease;
    }

    public void magicUp(int increase) {
        magic = magic + increase;
    }

    public void magicDown(int decrease) {
        magic = magic - decrease;
    }

    public void strengthUp(int increase) {
        strength = strength + increase;
    }

    public void strengthDown(int decrease) {
        strength = strength - decrease;
    }

    public void experienceUp(int increase) {
        experience = experience + increase;
    }

    public void levelUp() {
        nextLevel = nextLevel * 1.5;
    }

    public int getHealthMax() {
        return healthMax;
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public int getRange() {
        return range;
    }

    public int getMagic() {
        return magic;
    }

    public int getExperience() {
        return experience;
    }
}
