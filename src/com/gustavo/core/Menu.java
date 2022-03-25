package com.gustavo.core;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;


public class Menu {
    public static final String DATA_METERS_DATA_TSV = "Data/METERS_DATA.tsv";
    public static final String DATA_ZONES_DATA_CSV = "Data/ZONES_DATA.csv";
    HashMap<String, Zona> mapaZonas = new HashMap<>();
    HashMap<String, Medidor> mapaMedidor = new HashMap<>();

    public void processarMenu() {


        loadData();
        main();
    }

    public void loadData() {
        //Zones | Zonas


        File myObj;
        myObj = new File(DATA_ZONES_DATA_CSV);

        boolean pastHeader = false;

        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
                //Ler Args
                String data = myReader.nextLine();
                if (!pastHeader) {
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
                novaZona.setPopulacao(((int) Double.parseDouble(split[4])));

                mapaZonas.put(novaZona.getCodGeo(), novaZona);


                //for(int i=0; i<split.length; i++)
                //System.out.println(novaZona);
                //}

            }
            //myReader.close();

            //Meters | Medidores

            myObj = new File(DATA_METERS_DATA_TSV);
            Scanner myreader = new Scanner(myObj);

            pastHeader = false;

            while (myreader.hasNextLine()) {
                //Ler Args
                String input = myreader.nextLine();

                if (!pastHeader) {
                    pastHeader = true;
                    continue;
                }


                //input = myReader.nextLine();
                try {
                    Medidor novoMedidor = Medidor.parse(input, mapaZonas);

                    if (novoMedidor != null) {
                        mapaMedidor.put(novoMedidor.getCodMedidor(), novoMedidor);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //for(int i=0; i<split.length; i++)
                //System.out.println(novaZona);
                //}

            }
            myreader.close();
        } catch (Exception e) {
            System.out.println("An error ocurred.");
            e.printStackTrace();
        }


    }

    private void main() {
        int choice = 1;

        try (Scanner scanner = new Scanner(System.in)) {
            loadData();

            while (choice != 0) {
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
                        listZones();
                        break;
                    case 2:
                        listMedidor();
                        break;
                    case 3:
                        addZones(scanner);
                        break;
                    case 4:
                        addMeters(scanner);
                        break;
                    case 5:
                        viewZones();
                        break;
                    case 6:
                        viewMeters();
                        break;
                    case 7:
                        delZones();
                        break;
                    case 8:
                        delMeters();
                        break;
                    case 9:
                        updateZone();
                        break;
                    case 10:
                        updateMeter();
                        break;
                    case 11:
                        for (int i = 0; i <= mapaMedidor.size(); i++) {
                            if (mapaMedidor.containsValue("S15")) {
                                System.out.println(mapaMedidor);
                            }
                        }
                        break;

                    case 0:

                        System.out.println("\nDebug Encerrado!\n");
                        saveMeters();
                        saveZonas();
                        return;
                    default:
                        System.out.println("Escolha uma das opções disponiveis!");

                }
            }
        }


    }

    //Listar

    private void listZones() {
        System.out.println("NAME\tID\tZONE METER\tLENGTH\tPOPULATION");
        for (HashMap.Entry<String, Zona> zona : mapaZonas.entrySet()) {
            System.out.println(zona.getValue());
        }
    }

    private void listMedidor() {
        System.out.println("ID\tNAME\tZONE\tSupplied by\tUNITS\tTYPE");
        for (HashMap.Entry<String, Medidor> medidor :
                mapaMedidor.entrySet()) {
            System.out.println(medidor.getValue());
        }
    }

    //Adicionar
    public void addMeters(Scanner scanner) {
        Medidor novoMedidor = new Medidor();

        boolean existe = false;

        while(existe == false) {
            System.out.print("ID: ");
            String arg = scanner.nextLine();
            Medidor medExi = mapaMedidor.get(arg);

            if (medExi == null) {
                novoMedidor.setCodMedidor(arg);
                existe = true;
            } else {
                System.out.println("Medidor com este ID já existe");
                continue;
            }

            System.out.print("\nName: ");
            arg = scanner.nextLine();
            novoMedidor.setNomeMedidor(arg);

            System.out.print("\nZone: ");
            arg = scanner.nextLine();
            Zona zona = mapaZonas.get(arg);
            novoMedidor.setZona(zona);


            if (novoMedidor.zona == null) {
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
        }

        mapaMedidor.put(novoMedidor.getCodMedidor(), novoMedidor);

        /*if(novoMedidor.getZona().getCodGeo().equals(zona.getCodGeo())){
            if (zona.getMedidorZona() == null){
                zona.setMedidorZona(novoMedidor);
            }
        }*/


        saveMeters();


    }

    public void addZones(Scanner scanner) {
        //FileWriter myWriter = new FileWriter(DATA_ZONES_DATA_CSV, true);
        Zona novaZona = new Zona();
        boolean existe = false;
        boolean skipZone = false;

        while (existe == false) {
            skipZone = false;

            System.out.print("ID: ");
            String arg = scanner.nextLine();
            Zona zonaExi = mapaZonas.get(arg);

            if (zonaExi == null) {
                novaZona.setCodGeo(arg);
            } else {
                System.out.println("Zona com este ID já existe");

                continue;
            }

            System.out.print("\nName: ");
            arg = scanner.nextLine();

            for (String verfN : mapaZonas.keySet()) {
                Zona verN = mapaZonas.get(verfN);
                String nome = verN.getNome();
                if (!nome.equals(arg)) {
                    novaZona.setNome(arg);
                    existe = true;
                } else {
                    System.out.println("Zona com este NOME já existe");
                    skipZone = true;
                    break;
                }
            }

            if (skipZone)
                continue;

            System.out.print("\nPopulation: ");
            arg = scanner.nextLine();
            novaZona.setPopulacao(Double.parseDouble(arg));

            System.out.print("\nPipes Lenght: ");
            arg = scanner.nextLine();
            novaZona.setTotalCond(Double.parseDouble(arg));
        }

        mapaZonas.put(novaZona.getCodGeo(), novaZona);

        saveZonas();

    }

    //

    public void viewZones() {
        Scanner myScanner = new Scanner(System.in);

        System.out.println("Qual é a zona que deseja ver: ");
        String tmpGeo = myScanner.next();

        for (HashMap.Entry<String, Zona> pair : mapaZonas.entrySet()) {
            Zona zona = pair.getValue();

            if (tmpGeo.equals(zona.getCodGeo())) {
                System.out.println(zona);
                return;
            }
        }

        System.out.println("Zona não encontrada.");

    }

    private void viewMeters() {
        Scanner myScanner = new Scanner(System.in);

        System.out.println("Qual é o medidor que deseja ver: ");
        String tmpCod = myScanner.next();

        for (HashMap.Entry<String, Medidor> pair : mapaMedidor.entrySet()) {
            Medidor medidor = pair.getValue();

            if (tmpCod.equals(medidor.getCodMedidor())) {
                System.out.println(medidor);
                return;
            }
        }

        System.out.println("Medidor não encontrado.");

    }

    private void delZones() {
        Scanner myScanner = new Scanner(System.in);
        //System.out.println("Initial Mappings are: " + mapaZonas);

        System.out.println("Digite o ID da ZONA que deseja eliminar: ");
        String arg = myScanner.nextLine();
        Set<String> IDs = new HashSet<>(mapaMedidor.keySet());

        for (String x : IDs) {

            boolean blnExists = mapaMedidor.get(x).getZona().getCodGeo().equals(arg);
            if (blnExists) {
                //System.out.println(arg + " exists in HashMap ? : " + blnExists);
                mapaMedidor.remove(x);
                saveMeters();
            }
        }


        mapaZonas.remove(arg);

        saveZonas();
    }

    private void saveZonas() {
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
                File dst = new File(DATA_ZONES_DATA_CSV);
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

    private void delMeters() {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Initial Mappings are: " + mapaMedidor);

        System.out.println("Digite o ID do MEDIDOR que deseja eliminar: ");
        String arg = myScanner.nextLine();

        if (!mapaMedidor.containsKey(arg)) {
            System.err.println("Medidor não encontrado.");
        } else {

            Medidor medidor = mapaMedidor.get(arg);
            if (medidor.getZona().getMedidorZona().equals(medidor)) {
                medidor.getZona().setMedidorZona(null);
            }

            mapaMedidor.remove(arg);


            saveMeters();
            saveZonas();
        }
    }

    private void saveMeters() {
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
                File dst = new File(DATA_METERS_DATA_TSV);
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

    private void updateZone() {

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

                    Medidor m = mapaMedidor.get(argNovoCamp);
                    Zona z = mapaZonas.get(arg);

                    switch (argCamp) {
                        case 1:
                            System.out.println("Campo com nome " + z.getNome() + " alterado para " + argNovoCamp);
                            z.setNome(argNovoCamp);
                            break;

                        case 2:

                            if (z.getCodGeo().equals(m.getZona().getCodGeo())) {
                                if (m.getCodMedidor().equals(argNovoCamp)) {
                                    z.setMedidorZona(m);
                                    m.setZona(z);
                                }
                            }

                            System.out.println("Campo com nome " + z.tmpZona + " alterado para " + argNovoCamp);
                            z.tmpZona = argNovoCamp;
                            break;

                        case 3:
                            System.out.println("Campo com nome " + z.getTotalCond() + " alterado para " + argNovoCamp);
                            z.setTotalCond(Double.parseDouble(argNovoCamp));
                            break;

                        case 4:
                            System.out.println("Campo com nome " + z.getPopulacao() + " alterado para " + argNovoCamp);
                            z.setPopulacao(Double.parseDouble(argNovoCamp));
                            break;
                    }
                    saveZonas();
                    same = false;
                } else {
                    System.out.println("ID de zona inexistente.\nIntroduza Novamente:");
                    //continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (same);

    }

    private void updateMeter() {

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

                    if (argCamp != 5) {
                        System.out.println("Introduza a alteração: ");
                        argNovoCamp = myScanner.next();
                    }

                    Medidor m = mapaMedidor.get(arg);

                    switch (argCamp) {
                        case 1:
                            System.out.println("Campo com nome " + m.getNomeMedidor() + " alterado para " + argNovoCamp);
                            m.setNomeMedidor(argNovoCamp);
                            break;

                        case 2:
                            if (mapaZonas.containsKey(argNovoCamp)) {
                                System.out.println("Campo com nome " + m.getZona() + " alterado para " + argNovoCamp);

                                Zona zona = mapaZonas.get(argNovoCamp);
                                Zona oldZ = m.getZona();
                                m.setZona(zona);
                                if (m.equals(oldZ.getMedidorZona())) {
                                    oldZ.setMedidorZona(null);
                                }

                                if (m.getZona().getMedidorZona() == null) {
                                    m.getZona().setMedidorZona(m);
                                }
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
                    saveMeters();
                    saveZonas();
                    same = false;


                } else {
                    System.out.println("ID de zona inexistente.\nIntroduza Novamente:");
                    //continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (same);

    }

}



