package com.gustavo.core;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLOutput;
import java.util.*;


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
                String input = myReader.nextLine();

                if (!pastHeader){
                    pastHeader = true;
                    continue;
                }


                //input = myReader.nextLine();
                try {
                    Medidor novoMedidor = Medidor.parse(input, mapaZonas);

                    if(novoMedidor != null){ mapaMedidor.put(novoMedidor.getCodMedidor(), novoMedidor);}

                }catch (Exception e){
                    e.printStackTrace();
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
            System.out.println("8 - Eliminar Medidor");
            System.out.println("9 - Editar Zona");
            System.out.println("10 - Editar Medidor");
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
                case 8:
                    delMeters(mapaZonas,mapaMedidor);
                    break;
                case 9:
                    updateZone(mapaZonas,mapaMedidor);
                    break;
                case 10:
                    updateMeter(mapaZonas,mapaMedidor);
                    break;

                case 0:

                    System.out.println("\nDebug Encerrado!\n");
                    saveMeters(mapaMedidor);
                    saveZonas(mapaZonas);
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
        System.out.println("ID\tNAME\tZONE\tSupplied by\tUNITS\tTYPE");
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

            System.out.print("\nSupplied by: ");
            arg = scanner.nextLine();
            novoMedidor.setSuppliedBy(arg);

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
            FileWriter myWriter = new FileWriter("Data/ZONES_DATA.csv", true);
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
                medidor.getSuppliedBy(),
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

        System.out.println("Medidor não encontrado.");

    }

    private static void delZones(HashMap<String, Zona> mapaZonas,HashMap<String, Medidor> mapaMedidor) {
        Scanner myScanner = new Scanner(System.in);
        //System.out.println("Initial Mappings are: " + mapaZonas);

        System.out.println("Digite o ID da ZONA que deseja eliminar: ");
        String arg = myScanner.nextLine();
        Set<String> IDs = new HashSet<>(mapaMedidor.keySet());

        for(String x : IDs){

            boolean blnExists = mapaMedidor.get(x).getZona().getCodGeo().equals(arg);
            if (blnExists) {
                //System.out.println(arg + " exists in HashMap ? : " + blnExists);
                mapaMedidor.remove(x);
                saveMeters(mapaMedidor);
            }
        }


        mapaZonas.remove(arg);

        saveZonas(mapaZonas);
    }

    private static void saveZonas(HashMap<String, Zona> mapaZonas) {
        File zonas = null;

        try {
            zonas = File.createTempFile("Zonas", ".csv");

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(zonas))) {
                bw.write(String.join("\t", Zona.HEADERS));
                for (Map.Entry<String, Zona> entry : mapaZonas.entrySet()) {
                    // put key and value separated by a colon
                    bw.write(entry.getValue().toString());
                   // System.out.println(entry.getKey() + "\n" + entry.getValue());
                    // new line
                    bw.newLine();

                }
                File dst = new File("Data/ZONES_DATA.csv");
                dst.delete();
                zonas.renameTo(dst);
            } catch (IOException e) {
                try {
                    if (zonas != null && zonas.exists())
                        zonas.delete();
                } catch (Exception ee) {

                }
            }

            //loadData(mapaZonas,mapaMedidor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void delMeters(HashMap<String, Zona> mapaZonas,HashMap<String, Medidor> mapaMedidor){
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Initial Mappings are: " + mapaMedidor);

        System.out.println("Digite o ID do MEDIDOR que deseja eliminar: ");
        String arg = myScanner.nextLine();

        if(!mapaMedidor.containsKey(arg)){
            System.out.println("Medidor não encontrado.");
        }
        else {

            mapaMedidor.remove(arg);

            saveMeters(mapaMedidor);
        }
    }

    private static void saveMeters(HashMap<String, Medidor> mapaMedidor) {
        File medidores = null;

        try {
            medidores = File.createTempFile("Medidores", ".tsv");

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(medidores))) {
                bw.write(String.join("\t", Medidor.HEADERS));
                for (Map.Entry<String, Medidor> entry : mapaMedidor.entrySet()) {
                    // put key and value separated by a colon
                    bw.write(entry.getValue().toString());
                   // System.out.println(entry.getKey() + "\t" + entry.getValue());
                    // new line
                    bw.newLine();
                }
            } catch (IOException e) {
                try {
                    if (medidores != null && medidores.exists())
                        medidores.delete();
                } catch (Exception ee) {

                }
            } finally {
                File dst = new File("Data/METERS_DATA.tsv");
                //dst.delete();
                //medidores.renameTo(dst);
                Files.move(medidores.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
                //System.out.println(medidores.getAbsolutePath());
            }

            //loadData(mapaZonas,mapaMedidor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateZone(HashMap<String, Zona> mapaZonas,HashMap<String, Medidor> mapaMedidor){

        Scanner myScanner = new Scanner(System.in);

        boolean same = true;


        do {
            System.out.println("\nIntroduza o ID da zona que deseja alterar: ");
            String arg = myScanner.nextLine();

            try {
                if (mapaZonas.containsKey(arg)) {
                    System.out.println("Campos: \n1 - Nome\n2 - Zone Meter\n3 - Comprimento\n4 - População");
                    int argCamp = myScanner.nextInt();

                    System.out.println("Introduza a alteração: ");
                    String argNovoCamp = myScanner.next();

                    Zona z = mapaZonas.get(arg);
                    switch (argCamp) {
                        case 1:
                            System.out.println("Campo com nome " + z.getNome() + " alterado para " + argNovoCamp);
                            z.setNome(argNovoCamp);
                            break;

                        case 2:
                            System.out.println("Campo com nome " + z.tmpZona + " alterado para " + argNovoCamp);
                            z.tmpZona = argNovoCamp;
                            break;

                        case 3:
                            System.out.println("Campo com nome " + z.getTotalCond() + " alterado para " + argNovoCamp);
                            z.setTotalCond(Double.parseDouble(argNovoCamp));
                            break;

                        case 4:
                            System.out.println("Campo com nome " + z.getPopulacao() + " alterado para " + argNovoCamp);
                            z.setPopulacao(Integer.parseInt(argNovoCamp));
                            break;
                    }
                    saveZonas(mapaZonas);
                    same = false;
                } else {
                    System.out.println("ID de zona inexistente.\nIntroduza Novamente:");
                    //continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }while (same);

    }

    private static void updateMeter(HashMap<String, Zona> mapaZonas,HashMap<String, Medidor> mapaMedidor){

        Scanner myScanner = new Scanner(System.in);

        boolean same = true;


        do {
            System.out.println("\nIntroduza o ID do Medidor que deseja alterar: ");
            String arg = myScanner.nextLine();

            try {
                if (mapaMedidor.containsKey(arg)) {
                    System.out.println("Campos: \n1 - Nome\n2 - Zona\n3 - Supplied by\n4 - Unidades \n5 - Tipo de Medidor");
                    int argCamp = myScanner.nextInt();
                    String argNovoCamp = null;

                    if(argCamp != 5 ) {
                        System.out.println("Introduza a alteração: ");
                        argNovoCamp = myScanner.next();
                    }

                    Medidor m = mapaMedidor.get(arg);
                    Zona z = mapaZonas.get(arg);
                    switch (argCamp) {
                        case 1:
                            System.out.println("Campo com nome " + m.getNomeMedidor() + " alterado para " + argNovoCamp);
                            m.setNomeMedidor(argNovoCamp);
                            break;

                        case 2:
                                if (mapaZonas.containsKey(argNovoCamp)) {
                                    System.out.println("Campo com nome " + m.getZona() + " alterado para " + argNovoCamp);
                                    m.setZona(mapaZonas.get(argNovoCamp));
                                } else {
                                    System.out.println("Zona Inválida, introduza novamente!\n");
                                    continue;
                                }
                            break;

                        case 3:
                                System.out.println("Campo com nome " + m.getSuppliedBy() + " alterado para " + argNovoCamp);
                                m.setSuppliedBy(argNovoCamp);
                            break;

                        case 4:
                            System.out.println("Campo com nome " + m.getCodUni() + " alterado para " + argNovoCamp);
                            m.setCodUni(argNovoCamp);
                            break;

                        case 5:
                            System.out.println("\n Available types: \n1 - Flow\n2 - Pressure\n3 - Level\n4 - Rainfall\n5 - Temperature");
                            System.out.print("\nType: ");

                            int typeArg = myScanner.nextInt();
                            m.tipo = Medidor.TipoMedidor.values()[typeArg - 1];
                            break;
                    }
                    
                    System.out.println("Alteração feita com sucesso!\n");
                    saveMeters(mapaMedidor);
                    same = false;
                } else {
                    System.out.println("ID de zona inexistente.\nIntroduza Novamente:");
                    //continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }while (same);

    }

}



