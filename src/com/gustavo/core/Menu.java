package com.gustavo.core;

import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Menu {
    public static void processarMenu(String[] args) {


        HashMap<String, Zona> mapaZonas = new HashMap<>();

        loadData(mapaZonas);

        Menu.main(mapaZonas);


    }

    private static void loadData(HashMap<String, Zona> mapaZonas) {
        try {
            File myObj = new File("Data/ZONES_DATA.csv");
            Scanner myReader = new Scanner(myObj);

            boolean pastHeader = false;


            while (myReader.hasNextLine()) {
                //Ler Args
                String data = myReader.nextLine();
                if (!pastHeader){
                    pastHeader = true;
                    continue;
                }
                String[] split = data.split("\t");
                //Limpar Args
                for (int i = 0; i < split.length; i++) {
                    split[i] = split[i].trim();
                }


                Zona novaZona = new Zona();
                novaZona.setNome(split[0]);
                novaZona.setCodGeo(split[1]);
                //novaZona.set
                novaZona.setTotalCond(Double.parseDouble(split[3]));
                novaZona.setPopulacao(((int)Double.parseDouble(split[4])));

                mapaZonas.put(novaZona.getNome(), novaZona);


                //for(int i=0; i<split.length; i++)
                //System.out.println(novaZona);
                //}

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error ocurred.");
            e.printStackTrace();
        }
    }

    private static void main(HashMap<String, Zona> mapaZonas) {
        int choice = 0;

        Scanner scanner = new Scanner(System.in);

        System.out.println("--------Menu--------");
        System.out.println("1 - Listar as Zonas");
        System.out.println("2 - Adicionar Zonas");

        choice = scanner.nextInt();

        switch (choice) {
            case 1:
                listarZonas(mapaZonas);
                break;
            default:
                ;
        }


    }

    private static void listarZonas(HashMap<String, Zona> mapaZonas) {
        System.out.println("PK\tGIS\tNAME\tPOPULATION");
        for (HashMap.Entry<String, Zona> zona :
                mapaZonas.entrySet()) {
            System.out.println(zona.getValue());
        }




    }
}

