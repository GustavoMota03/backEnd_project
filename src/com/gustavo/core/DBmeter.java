package com.gustavo.core;

import java.sql.*;
import java.util.Scanner;

import static com.gustavo.core.DBzone.printSQLException;

public class DBmeter {

    public void insertMeter(Scanner scanner, ConnectionDB conn) throws SQLException {

        System.out.println(conn.INSERT_METER_SQL);
        // Step 1: Establishing a Connection
        conn.getConnectX().setAutoCommit(false);

        System.out.println("Insira Codigo do Medidor:");
        String codMedidor = scanner.nextLine();

        System.out.println("Insira o nome do Medidor:");
        String nomeMedidor = scanner.nextLine();

        System.out.println("Insira o a Zona do Medidor:");
        int fk_zona = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Insira o Suply do Medidor:");
        String suply_by = scanner.nextLine();

        System.out.println("Insira o codigo das Unidades:");
        String codUni = scanner.nextLine();

        System.out.println("Insira o Tipo de Medidor:");
        int tipoMedidor = scanner.nextInt();


        try (PreparedStatement preparedStatement = conn.getConnectX().prepareStatement(conn.INSERT_METER_SQL)) {
            // Step 2:Create a statement using connection object

            preparedStatement.setString(1, codMedidor);
            preparedStatement.setString(2, nomeMedidor);
            preparedStatement.setInt(3, fk_zona);
            preparedStatement.setString(4, suply_by);
            preparedStatement.setString(5, codUni);
            preparedStatement.setInt(6, tipoMedidor);

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.execute();
            conn.getConnectX().commit();
        } catch (SQLException ex) {
            conn.getConnectX().rollback();
            // print SQL exception information
            printSQLException(ex);
        }


    }


    public void showMeter(ConnectionDB conn) throws SQLException {
        System.out.println(conn.SHOW_METER_SQL);


        System.out.println("Lista de Medidores:");

        System.out.println("\n Id\t     Codigo Medidor \t Nome Medidor\t Zona do Medidor\t Supply By \t Codigo das Unidades\t Tipo Medidor");


        int i = 0;
        try (PreparedStatement preparedStatement = conn.getConnectX().prepareStatement(conn.SHOW_METER_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                System.out.print(" " + resultSet.getString(1) + "    \t|");
                System.out.print(" " + resultSet.getString(2) + "        \t|");
                System.out.print(" " + resultSet.getString(3) + "    \t|");
                System.out.print(" " + resultSet.getString(4) + "               \t|");
                System.out.print(" " + resultSet.getString(5) + "          \t|");
                System.out.print(" " + resultSet.getString(6) + "\t\t\t\t| ");
                System.out.println(" " + resultSet.getString(7) + "      \t ");
                i++;
            }


        } catch (SQLException e) {

            printSQLException(e);
        }
    }


    public void deleteMeter(Scanner scanner, ConnectionDB conn) throws SQLException {

        System.out.println(conn.DELETE_METER_SQL);
        System.out.println("Insira a Primary key do Medidor que deseja dar delete: ");
        Integer id = scanner.nextInt();


        try (PreparedStatement preparedStatement = conn.getConnectX().prepareStatement(conn.DELETE_METER_SQL)) {
            conn.getConnectX().setAutoCommit(false);

            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            conn.getConnectX().commit();
            System.out.println("Medidor com a Primary Key número " + id + ", eliminado com sucesso!");
        } catch (SQLException ex) {
            conn.getConnectX().rollback();
            printSQLException(ex);
        }



        return;
    }

    public void updateMeter(Scanner scanner, ConnectionDB conn) throws SQLException {
        System.out.println(conn.UPDATE_METER_SQL);
        System.out.println("Insira a Primary Key do Medidor que deseja alterar: ");
        Integer id = scanner.nextInt();

        conn.getConnectX().setAutoCommit(false);
        int campo = 0;
        do {
            System.out.println("--------Menu de campos--------");
            System.out.println("1 - Nome do Medidor");
            System.out.println("2 - Zona do Medidor");
            System.out.println("3 - Supply do Medidor");
            System.out.println("4 - Codigo de Unidade do Medidor");
            System.out.println("5 - Tipo de Medidor");
            campo = scanner.nextInt();
            scanner.nextLine();

        } while (campo < 1 && campo > 5);

        String field = null;
        Object value = null;

        try {
            switch (campo) {
                case 1:
                    field = "nomemedidor";
                    System.out.println("Insira o novo nome do Medidor: ");

                    break;
                case 2:
                    field = "fk_zona";
                    System.out.println("Insira a nova Zona do Medidor: ");

                    break;
                case 3:
                    field = "supply_by";
                    System.out.println("Insira o novo Supply do Medidor: ");

                    break;
                case 4:
                    field = "coduni";
                    System.out.println("Insira o novo Codigo de Unidades do Medidor: ");
                    break;
                case 5:
                    field = "tipomedidor";
                    System.out.println("Insira o novo Tipo de Medidor: ");
                    break;
            }
            PreparedStatement preparedStatement = conn.getConnectX().prepareStatement(String.format(ConnectionDB.UPDATE_METER_SQL, field));
            if (campo == 2 || campo == 5) {
                value = scanner.nextInt();
            } else {
                value = scanner.nextLine();
            }

            preparedStatement.setObject(1, value);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            conn.getConnectX().commit();

        } catch (SQLException ex) {
            conn.getConnectX().rollback();
            printSQLException(ex);
        }
    }


}
