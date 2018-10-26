package com.ocr.anthony;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class OrderReader {
    public static void main(String[] args) {
        OrderReader orderReader = new OrderReader();
        orderReader.read();

    }

    public void read(){
        Path orderPath = Paths.get("order.csv");
        List<String> lines = null; // no value by default

        try {
            lines = Files.readAllLines(orderPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (lines.size() < 2){
            System.out.println("Il n'y a pas de commande dans le fichier!");
            return;
        }

        String[] menus = {"poulet","boeuf","végétarien"};
        String[] side = {" avec des légumes frais", " avec des frites"," avec du riz"};
        String[] sideVegetarian = {" avec du riz", " sans riz"};
        String[] drink = {" et avec de l'eau plate", " et avec de l'eau gazeuse", " et avec du soda" };

        // the first line is the index names
        for (int i=1; i<lines.size();i++){
            String[] split = lines.get(i).split(",");
            int nbMenu = Integer.valueOf(split[0]);
            int nbSide = Integer.valueOf(split[1]);
            int nbDrink = Integer.valueOf(split[2]);

            String orderLine = menus[nbMenu-1];
            if( nbMenu == 3)
                orderLine += sideVegetarian[nbSide-1];
            else
                orderLine += side[nbSide-1];
            if ( nbDrink == -1)
                orderLine += " et sans boisson";
            else
                orderLine += drink[nbDrink-1];
            System.out.println(orderLine);
        }
    }
}
