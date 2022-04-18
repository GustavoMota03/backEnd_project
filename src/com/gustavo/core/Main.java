package com.gustavo.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.function.DoubleBinaryOperator;

public class Main {


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolher qual programa abrir -\n1 - Meters_Zones com Base de dados\n2 - Meters_Zones sem Base de dados");
        int prog = scanner.nextInt();

        switch (prog){
            case 1:
                new MenuDB().processarMenuDB(scanner);
                break;
            case 2:
                new  Menu().processarMenu();
                break;
            case 0:
                System.out.println("\nDebug Encerrado!\n");
                return;

        }


    }
}
