package com.gustavo.core;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuDB {


    public void processarMenuDB(Scanner scanner) {
        int choice = 1;
        try (ConnectionDB conn = new ConnectionDB()) {
            while (choice != 0) {
                System.out.println("--------Menu--------");
                System.out.println("1 - Adicionar Zonas");
                System.out.println("2 - Listar as Zonas");
                System.out.println("3 - Eliminar Zona");
                System.out.println("4 - Editar Zona");
                System.out.println("5 - Adicionar Medidores");
                System.out.println("6 - Listar os Medidores");
                System.out.println("7 - Eliminar Medidor");
                System.out.println("8 - Editar Medidor");

                /*
                System.out.println("5 - Verificar Zona");
                System.out.println("6 - Verificar Medidor");



                */
                System.out.println("0 - Fechar");

                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        new DBzone().insertZone(scanner,conn);
                        break;
                    case 2:
                        new DBzone().showZone(conn);
                        break;
                    case 3:
                        new DBzone().deleteZone(scanner,conn);
                        break;
                    case 4:
                        new DBzone().updateZone(scanner,conn);
                        break;
                    case 5:
                        new DBmeter().insertMeter(scanner,conn);
                        break;
                    case 6:
                        new DBmeter().showMeter(conn);
                        break;
                    case 7:
                        new DBmeter().deleteMeter(scanner,conn);
                        break;
                    case 8:
                        new DBmeter().updateMeter(scanner,conn);
                        break;

                    case 0:
                        System.out.println("\nDebug Encerrado!\n");
                        return;
                    default:
                        System.out.println("Escolha uma das opções disponiveis!");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
