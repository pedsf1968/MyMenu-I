package com.ocr.anthony;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.APPEND;

public class Order {
    Scanner sc = new Scanner(System.in);
    String orderSummary = "";
    /**
     * Display all available menus in the restaurant.
     */
    public void displayAvailableMenu() {
        System.out.println("Choix menu");
        System.out.println("1 - poulet");
        System.out.println("2 - boeuf");
        System.out.println("3 - végétarien");
        System.out.println("Que souhaitez-vous comme menu ?");
    }

    /**
     * Display all available sides depending on the all sides enable or not.
     * All sides = vegatable, fries and rice
     * No all sides = rice or not
     * @param allSideEnable enable display for all side or not
     */
    public void displayAvailableSide(boolean allSideEnable){
        System.out.println("Choix accompagnement");
        if( allSideEnable){
            System.out.println("1 - légumes frais");
            System.out.println("2 - frites");
            System.out.println("3 - riz");
        } else {
            System.out.println("1 - riz");
            System.out.println("2 - pas de riz");
        }
        System.out.println("Que souhaitez-vous comme accompagnement ?");
    }

    /**
     * Display all available drink in the restaurant
     */
    public void displayAvailableDrink() {
        System.out.println("Choix boisson");
        System.out.println("1 - eau plate");
        System.out.println("2 - eau gazeuse");
        System.out.println("3 - soda");
        System.out.println("Que souhaitez-vous comme boisson ?");
    }

    /**
     * Display a selected menu.
     * @param nbMenu The selected menu.
     */
    public void displaySelectedMenu(int nbMenu) {
        switch ( nbMenu){
            case 1:
                System.out.println("Vous avez choisi comme menu : poulet");
                break;
            case 2:
                System.out.println("Vous avez choisi comme menu : boeuf");
                break;
            case 3:
                System.out.println("Vous avez choisi comme menu : végétarien");
                break;
            default:
                System.out.println("Vous n'avez pas choisi de menu parmi les choix proposés");
                break;
        }
    }

    /**
     * Display a selected side depending on all sides enable or not.
     * All sides = vegetable, fries and rice
     * Not all sides = rice or not
     * @param nbSide the selected side
     * @param allSidesEnable enable display for all sides or not.
     */
    public void displaySelectedSide(int nbSide, boolean allSidesEnable){
        if (allSidesEnable){
            switch (nbSide){
                case 1:
                    System.out.println("Vous avez choisi comme accompagnement : légumes frais");
                    break;
                case 2:
                    System.out.println("Vous avez choisi comme accompagnement : frites");
                    break;
                case 3:
                    System.out.println("Vous avez choisi comme accompagnement : riz");
                    break;
                default:
                    System.out.println("Vous n'avez pas choisi d'accompagnement parmi les choix proposés");
                    break;
            }
        } else{
            switch (nbSide){
                case 1:
                    System.out.println("Vous avez choisi comme accompagnement : riz");
                    break;
                case 2:
                    System.out.println("Vous avez choisi comme accompagnement : pas de riz");
                    break;
                default:
                    System.out.println("Vous n'avez pas choisi d'accompagnement parmi les choix proposés");
                    break;
            }
        }

    }

    /**
     * Display a selected drink.
     * @param nbDrink the selected drink.
     */
    public void displaySelectedDrink(int nbDrink) {
        switch (nbDrink){
            case 1:
                System.out.println("Vous avez choisi comme boisson : eau plate");
                break;
            case 2:
                System.out.println("Vous avez choisi comme boisson : eau gazeuse");
                break;
            case 3:
                System.out.println("Vous avez choisi comme boisson : soda");
                break;
            default:
                System.out.println("Vous n'avez pas choisi de boisson parmi les choix proposés");
                break;
        }
    }
    /**
     * * Run asking process for order.
     * return a string contain all the order
     */
    public String runMenu(){
        int nbMenu = askMenu();
        int nbSide = -1;
        int nbDrink = -1;

        switch (nbMenu) {
            case 1:
                nbSide = askSide(true);
                nbDrink = askDrink();
                break;
            case 2:
                nbSide = askSide(true);
                break;
            case 3:
                nbSide = askSide(false);
                nbDrink = askDrink();
                break;
        }
        return nbMenu + "," + nbSide + "," + nbDrink + "%n"; // le % permet de passer à la ligne sur mac, windows ou unxi.
    }

    /**
     * Run asking process for several menus
     */
    public void runMenus() {
        boolean responseIsGood;
        int menuQuantity = 0;
        orderSummary = "Résumé de votre commande :%n";
        Path ortherPath = Paths.get("order.csv");

        System.out.println("Combien souhaitez-vous commander de menu ?");
        do {
            try {
                menuQuantity = sc.nextInt();
                responseIsGood = true;
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("Vous devez saisir un nombre, correspondant au nombre de menus souhaités");
                responseIsGood = false;
            }
        } while(!responseIsGood);
        for (int i=0; i<menuQuantity; i++){
            orderSummary += "Menu " + (i+1) + ":%n";
            String orderLine = this.runMenu();
            try {
                Files.write(ortherPath, String.format(orderLine).getBytes(),APPEND);
            } catch (IOException e) {
                System.out.println("Une erreur est survenue. Merci de réessayer plus tard.");
                return; // pour sortir de la fonction sans exécuter le code suivant
            }
        }
        System.out.println("");
        System.out.println(String.format(orderSummary));
    }

    /**
     * Display a question about a category in the standard input, get response and display it
     * @param category the category of the question
     * @param responses available responses
     * return category number selected
     */
    public int askSomething(String category, String[] responses){
        System.out.println("Choix " + category);
        for (int i=1; i<=responses.length;i++)
            System.out.println(i + " - " + responses[i-1]);

        System.out.println("Que souhaitez vous comme " + category + " ?");
        int nbResponse = 0;
        boolean responseIsGood;

        do {
            try {
                nbResponse = sc.nextInt();
                responseIsGood = (nbResponse >= 1 && nbResponse <= responses.length);
            } catch (InputMismatchException e) {
                sc.next();
                responseIsGood = false;
            }

            if (responseIsGood) {
                String choice = "Vous avez choisi comme " + category + " : " + responses[nbResponse - 1];
                System.out.println(choice);
                orderSummary += choice + "%n";
            } else {
                boolean isVowel = "aeiouy".contains(Character.toString(category.charAt(0)));
                if ( isVowel)
                    System.out.println("Vous n'avez pas choisi d'" + category + " parmi les choix proposés");
                else
                    System.out.println("Vous n'avez pas choisi de " + category + " parmi les choix proposés");

            }
        } while (!responseIsGood);

        return nbResponse;
    }

    /**
     * Display a question about menus in the standard input, get the response and display it
     *  return menu selected
     */
    public int askMenu() {
        String[] menus = {"poulet","boeuf","végétarien"};

        return askSomething("menu", menus);
    }

    /**
     * Display a question about sides in the standard input, get the response and display it
     * @param allSidesEnable all sides or not
     * Return the number of side
     */
    public int askSide(boolean allSidesEnable) {

        if(allSidesEnable) {
            String[] sides = {"légumes frais","frites","riz"};
            return askSomething("accompagnement", sides);
        } else {
            String[] sides = {"riz","pas de riz"};
            return askSomething("accompagnement", sides);
        }
    }

    /**
     * Display a question about drinks in the standard input, get the response and display it
     * Return the number of drink
     */
    public int askDrink() {
        String[] drinks = {"eau plate","eau gazeuse","soda"};
        return askSomething("boisson",drinks);
    }
}