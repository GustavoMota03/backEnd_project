package com.gustavo.core;

import javax.print.DocFlavor;
import java.sql.*;
import java.util.Scanner;

public class DBzone {


    public void insertZone(Scanner scanner, ConnectionDB conn) throws SQLException {

        System.out.println(conn.INSERT_ZONE_SQL);

        System.out.println("Insira Codigo Geográfico:");
        String codGeo = scanner.nextLine();

        System.out.println("Insira o nome:");
        String nome = scanner.nextLine();

        System.out.println("Insira o comprimento dos canos");
        Double totalCond = scanner.nextDouble();

        System.out.println("Insira a população");
        Double pop = scanner.nextDouble();

        try (PreparedStatement preparedStatement = conn.getConnectX().prepareStatement(conn.INSERT_ZONE_SQL)) {

            preparedStatement.setString(1, codGeo);
            preparedStatement.setString(2, nome);
            preparedStatement.setDouble(3, totalCond);
            preparedStatement.setDouble(4, pop);

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


    public void showZone(ConnectionDB conn) throws SQLException {
        System.out.println(conn.SHOW_ZONE_SQL);


            System.out.println("Lista de Zonas:");

            System.out.println("\n Id\t     Codigo geografico\t Nome\t Medidor da Zona\t Comprimento Condutas\t População");

            int i = 0;
        try (PreparedStatement preparedStatement = conn.getConnectX().prepareStatement(conn.SHOW_ZONE_SQL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                System.out.print(" " + resultSet.getString(1) + "    \t|");
                System.out.print(" " + resultSet.getString(2) + "        \t|");

                System.out.print(" " + resultSet.getString(3) + "    \t|");
                System.out.print(" " + resultSet.getString(4) + "    \t|");
                System.out.print(" " + resultSet.getString(5) + "        \t|");
                System.out.println(" " + resultSet.getString(6) + "    \t ");
                i++;

            }
        }catch (SQLException ex){
            conn.getConnectX().rollback();
            // print SQL exception information
            printSQLException(ex);
        }

    }

    public void deleteZone(Scanner scanner, ConnectionDB conn) throws SQLException {
        System.out.println(conn.DELETE_ZONE_SQL);
        System.out.println("Insira a Primary key da zona que deseja dar delete: ");
        int id = scanner.nextInt();



        try(PreparedStatement preparedStatement = conn.getConnectX().prepareStatement(conn.DELETE_ZONE_SQL)) {
            conn.getConnectX().setAutoCommit(false);

            preparedStatement.setInt(1, id);
            boolean execute = preparedStatement.execute();
            conn.getConnectX().commit();



            System.out.println("Zona com a Primary Key numero " + id + ", eliminada com sucesso!");
        } catch (SQLException ex) {
            conn.getConnectX().rollback();
            System.err.println(ex.getMessage());
        }

        /*String Verify = "SELECT CASE WHEN EXISTS (SELECT fk_zona FROM Meter WHERE fk_zona = ?) THEN 'true' ELSE 'false' END";
        PreparedStatement preparedStatement = conn.getConnectX().prepareStatement(Verify);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        if (preparedStatement.execute()){
            System.out.println("MERDA");
        }*/


        return;
    }

    public void updateZone(Scanner scanner, ConnectionDB conn) throws SQLException {
        System.out.println(conn.UPDATE_ZONE_SQL);
        System.out.println("Insira a Primary Key da Zona que deseja alterar: ");
        Integer id = scanner.nextInt();

        conn.getConnectX().setAutoCommit(false);
        int campo = 0;
        do {
            System.out.println("--------Menu de campos--------");
            System.out.println("1 - Nome da Zona");
            System.out.println("2 - Medidor da Zona");
            System.out.println("3 - Comprimento das Condutas da Zona");
            System.out.println("4 - População da Zona");
            campo = scanner.nextInt();
            scanner.nextLine();

        } while (campo < 1 && campo > 4);

        String field = null;
        Object value = null;

        try {
            switch (campo) {
                case 1:
                    field = "nome";
                    System.out.println("Insira o novo nome da Zona: ");

                    break;
                case 2:
                    field = "fk_medidorzona";
                    System.out.println("Insira o novo Medidor da Zona: ");

                    break;
                case 3:
                    field = "totalcond";
                    System.out.println("Insira o novo comprimento das condutas da Zona: ");

                    break;
                case 4:
                    field = "populacao";
                    System.out.println("Insira o novo numero de população da Zona: ");
                    break;
            }
            PreparedStatement preparedStatement = conn.getConnectX().prepareStatement(String.format(ConnectionDB.UPDATE_ZONE_SQL, field));
            if (campo == 1) {
                value = scanner.nextLine();
            } else if (campo == 2) {
                value = scanner.nextInt();
            } else {
                value = scanner.nextDouble();
            }

            preparedStatement.setObject(1, value);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            conn.getConnectX().commit();

        } catch (SQLException ex){
            conn.getConnectX().rollback();
            // print SQL exception information
            printSQLException(ex);
        }
    }


    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}
