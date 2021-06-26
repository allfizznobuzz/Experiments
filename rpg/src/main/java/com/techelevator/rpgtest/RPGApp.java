package com.techelevator.rpgtest;

import java.util.Scanner;

public class RPGApp {

    private Character character;
    private CharacterInventory characterInventory;
    private Weapon weapon;
    private Menu menu;

    Scanner scanner = new Scanner(System.in);

    public RPGApp() {
        characterInventory = new CharacterInventory();
        weapon = new Weapon();
        menu = new Menu();
    }

    public static void main(String[] args) {
        RPGApp rpgApp = new RPGApp();
        rpgApp.run();
    }

    public void run() {

        boolean didNotHitTwo = false;
        menu.startOfGame();

        while(true) {

            if(didNotHitTwo) {
                break;
            }

            String userInput = scanner.nextLine();
            String heroName = "";

            if (userInput.equals("1")) {

                didNotHitTwo = true;
                menu.heroName();
                heroName = scanner.nextLine();

                menu.characterSelection();
                userInput = scanner.nextLine();

                if(userInput.equals("1")) {
                    Mage mage = new Mage(heroName,"Mage");
                    menu.printNewHero(heroName, "Mage");
                }
                else if(userInput.equals("2")) {
                    Barbarian barbarian = new Barbarian(heroName,"Barbarian");
                    menu.printNewHero(heroName, "Barbarian");
                }
                else if(userInput.equals("3")) {
                    Paladin paladin = new Paladin(heroName,"Paladin");
                    menu.printNewHero(heroName, "Paladin");
                }
                else if(userInput.equals("4")) {
                    Archer archer = new Archer(heroName,"Archer");
                    menu.printNewHero(heroName, "Archer");
                }
                else menu.menuError();

            } else if (userInput.equals("2")) {
                break;
            }
            else menu.menuError();
        }

        if(didNotHitTwo) {
            System.out.println("Good job not hitting 2!!!");
        }
    }

}
