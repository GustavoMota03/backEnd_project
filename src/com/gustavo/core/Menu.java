package com.gustavo.core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Menu {
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
                novaZona.tmpZona = split[2];
                novaZona.setTotalCond(Double.parseDouble((split[3])));
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
            System.out.println("4 - Adicionar Medidores");
            System.out.println("5 - Verificar Zona");
            System.out.println("6 - Verificar Medidor");
            System.out.println("7 - Eliminar Zona");
            System.out.println("0 - Fechar");

            choice = scanner.nextInt();
            scanner.nextLine();

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
                case 4:
                    addMeters(mapaMedidor,mapaZonas, scanner);
                    break;
                case 5:
                    viewZones(mapaZonas);
                    break;
                case 6:
                    viewMeters(mapaMedidor);
                    break;
                case 7:
                    delZones(mapaZonas,mapaMedidor);
                    break;

                case 0:

                    System.out.println("\nDebug Encerrado!\n");
                    return;
                default:
                    System.out.println("Escolha uma das opções disponiveis!");;
            }
        }


    }

    //Listar

    private static void listZones(HashMap<String, Zona> mapaZonas) {
        System.out.println("NAME\tID\tZONE METER\tLENGTH\tPOPULATION");
        for (HashMap.Entry<String, Zona> zona : mapaZonas.entrySet()) {
            System.out.println(zona.getValue());
        }
    }

    private static void listMedidor(HashMap<String, Medidor> mapaMedidor) {
        System.out.println("ID\tNAME\tZONE\tUNITS\tTYPE");
        for (HashMap.Entry<String, Medidor> medidor :
                mapaMedidor.entrySet()) {
            System.out.println(medidor.getValue());
        }
    }

    //Adicionar
    public static void addMeters(HashMap<String, Medidor> mapaMedidor, HashMap<String, Zona> mapaZonas,Scanner scanner){
        try {
            FileWriter myWriter = new FileWriter("Data/METERS_DATA.tsv", true);
            Medidor novoMedidor = new Medidor();


            System.out.print("ID: ");
            String arg = scanner.nextLine();
            novoMedidor.setCodMedidor(arg);

            System.out.print("\nName: ");
            arg = scanner.nextLine();
            novoMedidor.setNomeMedidor(arg);

            System.out.print("\nZone: ");
            arg = scanner.nextLine();
            Zona zona = mapaZonas.get(arg);
            novoMedidor.setZona(zona);

            if (novoMedidor.zona == null){
                System.out.println("Zona inexistente");
                return;
            }

            System.out.println("\n Available types: \n1 - Flow\n2 - Pressure\n3 - Level\n4 - Rainfall\n5 - Temperature");
            System.out.print("\nType: ");
            //arg = scanner.next();

            int typeArg = scanner.nextInt();
            novoMedidor.tipo = Medidor.TipoMedidor.values()[typeArg - 1];


            myWriter.write("\n");

            myWriter.write(serializeMeter(novoMedidor));
            myWriter.flush();

            System.out.println(serializeMeter(novoMedidor));

            mapaMedidor.put(novoMedidor.getCodMedidor(), novoMedidor);

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }public static void addZones(HashMap<String, Zona> mapaZonas, Scanner scanner){
        try {
            FileWriter myWriter = new FileWriter("Data/ZONES_DATA.tsv", true);
            Zona novaZona = new Zona();

            System.out.print("Code: ");
            String arg = scanner.nextLine();
            novaZona.setCodGeo(arg);

            System.out.print("\nName: ");
            arg = scanner.nextLine();
            novaZona.setNome(arg);

            System.out.print("\nPopulation: ");
            arg = scanner.nextLine();
            novaZona.setPopulacao(Integer.parseInt(arg));

            System.out.print("\nPipes Lenght: ");
            arg = scanner.nextLine();
            novaZona.setTotalCond(Double.parseDouble(arg));


            myWriter.write("\n");

            myWriter.write(serializeZone(novaZona));
            myWriter.flush();

            System.out.println(serializeZone(novaZona));

            mapaZonas.put(novaZona.getCodGeo(), novaZona);

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
                zona.tmpZona,
                String.valueOf(zona.getTotalCond()),
                String.valueOf(zona.getPopulacao()),
        };

        return String.join("\t",fields);
    }
    private static String serializeMeter(Medidor medidor){
        String[] fields = new String[]{
                medidor.getCodMedidor(),
                medidor.getNomeMedidor(),
                String.valueOf(medidor.getZona().getCodGeo()),
                null,
                medidor.getCodUni(),
                String.valueOf(medidor.getTipoMedidor()),
        };

        return String.join("\t",fields);
    }


    //

    private static void viewZones(HashMap<String, Zona> mapaZonas) {
        Scanner myScanner = new Scanner(System.in);

        System.out.println("Qual é a zona que deseja ver: ");
        String tmpGeo = myScanner.next();

        for(HashMap.Entry<String, Zona> pair : mapaZonas.entrySet()){
           Zona zona = pair.getValue();

           if(tmpGeo.equals(zona.getCodGeo())){
                System.out.println(zona);
                return;
            }
        }

        System.out.println("Zona não encontrada.");

    }

    private static void viewMeters(HashMap<String, Medidor> mapaMedidor) {
        Scanner myScanner = new Scanner(System.in);

        System.out.println("Qual é o medidor que deseja ver: ");
        String tmpCod = myScanner.next();

        for(HashMap.Entry<String, Medidor> pair : mapaMedidor.entrySet()){
            Medidor medidor = pair.getValue();

            if(tmpCod.equals(medidor.getCodMedidor())){
                System.out.println(medidor);
                return;
            }
        }

        System.out.println("Medidor não encontrada.");

    }


    public static void tempFile(){
        try {

            // Create an temporary file in a specified directory.
            Path temp = Files.createTempFile( "Zonas", ".tsv");

            System.out.println("Temp file : " + temp);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void delZones(HashMap<String, Zona> mapaZonas,HashMap<String, Medidor> mapaMedidor){
        Scanner myScanner = new Scanner(System.in);
        //System.out.println("Initial Mappings are: " + mapaZonas);

        System.out.println("Digite o NOME da zona que deseja eliminar: ");
        String arg = myScanner.nextLine();

        mapaZonas.remove(arg);

         File zonas =null;

        try {
            zonas = File.createTempFile("Zonas", ".tsv");

            try(BufferedWriter bw = new BufferedWriter(new FileWriter(zonas))){
                bw.write(String.join("\t",Zona.HEADERS));
              for (Map.Entry<String, Zona> entry : mapaZonas.entrySet()) {
                // put key and value separated by a colon
                bw.write(entry.getValue().toString());
                  System.out.println(entry.getKey() + "\n" + entry.getValue());
                  // new line
                  bw.newLine();

            }
            File dst = new File("Data/ZONES_DATA.tsv");
            dst.delete();
            zonas.renameTo(dst);
        }catch (IOException e){
            try {
                if (zonas != null && zonas.exists())
                    zonas.delete();
            }catch (Exception ee){

            }
        }

        //loadData(mapaZonas,mapaMedidor);
    } catch (IOException e) {
            e.printStackTrace();
        }


    }
}



