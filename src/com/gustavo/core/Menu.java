package com.gustavo.core;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Menu {
    static String tmpZona;
    public static void processarMenu(String[] args) {


        HashMap<String, Zona> mapaZonas = new HashMap<>();
        HashMap<String, Medidor> mapaMedidor = new HashMap<>();

        loadData(mapaZonas, mapaMedidor);

        Menu.main(mapaZonas, mapaMedidor);


    }

    private static void loadData(HashMap<String, Zona> mapaZonas,HashMap<String, Medidor> mapaMedidor) {
        //Zones | Zonas


        try {
            File myObj = new File("Data/ZONES_DATA.tsv");
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
                tmpZona = split[2];
                novaZona.setTotalCond(Double.parseDouble(split[3]));
                novaZona.setPopulacao(((int)Double.parseDouble(split[4])));

                mapaZonas.put(novaZona.getCodGeo(), novaZona);


                //for(int i=0; i<split.length; i++)
                //System.out.println(novaZona);
                //}

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error ocurred.");
            e.printStackTrace();
        }

        //Meters | Medidores

        try {
            File myObj = new File("Data/METERS_DATA.tsv");
            Scanner myReader = new Scanner(myObj);

            boolean pastHeader = false;

            while (myReader.hasNextLine()) {
                //Ler Args
                if (!pastHeader){
                    pastHeader = true;
                    continue;
                }


                String input = myReader.nextLine();
                try {
                    Medidor novoMedidor = Medidor.parse(input, mapaZonas);


                    mapaMedidor.put(novoMedidor.getNomeMedidor(), novoMedidor);
                }catch (Exception e){
                    //e.printStackTrace();
                }
                //for(int i=0; i<split.length; i++)
                //System.out.println(novaZona);
                //}

            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("An error ocurred.");
            e.printStackTrace();
        }
    }

    private static void main(HashMap<String, Zona> mapaZonas, HashMap<String, Medidor> mapaMedidor) {
        int choice = 1;

        Scanner scanner = new Scanner(System.in);
        loadData(mapaZonas, mapaMedidor);

        while(choice != 0) {
            System.out.println("--------Menu--------");
            System.out.println("1 - Listar as Zonas");
            System.out.println("2 - Listar Medidores");
            System.out.println("3 - Adicionar Zonas");
            System.out.println("0 - Fechar");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    listZones(mapaZonas);
                    break;
                case 2:
                    listMedidor(mapaMedidor);
                    break;
                case 3:
                    addZones(mapaZonas, scanner);
                    break;

                case 0:
                    System.out.println("\nDebug Encerrado!\n");
                    return;
                default:
                    System.out.println("Escolha uma das opções disponiveis!");;
            }
        }


    }

    //Zones | Zonas

    private static void listZones(HashMap<String, Zona> mapaZonas) {
        System.out.println("PK\tGIS\tNAME\tPOPULATION");
        for (HashMap.Entry<String, Zona> zona :
                mapaZonas.entrySet()) {
            System.out.println(zona.getValue());
        }
    }

    private static void listMedidor(HashMap<String, Medidor> mapaMedidor) {
        System.out.println("PK\tGIS\tNAME\tPOPULATION");
        for (HashMap.Entry<String, Medidor> medidor :
                mapaMedidor.entrySet()) {
            System.out.println(medidor.getValue());
        }
    }

    public static void addZones(HashMap<String, Zona> mapaZonas, Scanner scanner){
        try {
            FileWriter myWriter = new FileWriter("Data/ZONES_DATA.csv", true);
            Zona novaZona = new Zona();

            System.out.print("Code: ");
            String arg = scanner.next();
            novaZona.setCodGeo(arg);

            System.out.print("\nName: ");
            arg = scanner.next();
            novaZona.setNome(arg);

            System.out.print("\nPopulation: ");
            arg = scanner.next();
            novaZona.setPopulacao(Integer.parseInt(arg));

            System.out.print("\nPipes Lenght: ");
            arg = scanner.next();
            novaZona.setTotalCond(Double.parseDouble(arg));


            myWriter.write("\n");

            myWriter.write(serializeZone(novaZona));
            myWriter.flush();

            System.out.println(serializeZone(novaZona));

            mapaZonas.put(novaZona.getNome(), novaZona);

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    private static String serializeZone(Zona zona){
        String[] fields = new String[]{
                zona.getNome(),
                zona.getCodGeo(),
                "null",
                String.valueOf(zona.getTotalCond()),
                String.valueOf(zona.getPopulacao()),
        };

        return String.join("\t",fields);
    }


    //Meters | Medidores

    private static void listMeters(HashMap<String, Zona> mapaZonas) {
        System.out.println("ID\tNAME\tZONE\tPOPULATION");
        for (HashMap.Entry<String, Zona> zona :
                mapaZonas.entrySet()) {
            System.out.println(zona.getValue());
        }
    }


}



