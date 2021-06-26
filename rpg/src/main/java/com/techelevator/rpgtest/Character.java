package com.techelevator.rpgtest;

public abstract class Character {

    private String heroName;
    private String heroType;

    public Character(String heroName, String heroType) {
        this.heroName = heroName;
        this.heroType = heroType;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroType() {
        return heroType;
    }

    public void setHeroType(String heroType) {
        this.heroType = heroType;
    }
}
