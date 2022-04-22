package com.gustavo.core;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

public class ConnectionDB implements AutoCloseable {

    public Connection getConnectX() {
        return connectX;
    }

    private Connection connectX = null;

    public ConnectionDB() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        connectX = conn;
    }



    public static String url = "jdbc:postgresql://localhost:5432/baseform";
    public static String user = "baseform";
    public static String password = "";



    // Zone SQL
    public static final String INSERT_ZONE_SQL = "INSERT INTO Zone" +
            "(codGeo,nome,totalCond,populacao) VALUES " +
            " (?, ?, ?, ?);";

    //public static final String SHOW_ZONE_SQL = "SELECT * FROM Zone";

    public static final String SHOW_ZONE_SQL = "SELECT z.id, z.codgeo, z.nome, (SELECT m.nomemedidor FROM Meter AS m WHERE m.id = z.fk_medidorzona), z.totalcond, z.populacao FROM Zone AS z";

    public static final String DELETE_ZONE_SQL = "DELETE FROM Zone WHERE id = ? RETURNING *";

    public static final String UPDATE_ZONE_SQL = "UPDATE Zone SET %s = ? WHERE id = ?";

    public static final String SET_MEDIDOR_ZONA_NULL = "UPDATE Zone SET fk_medidorzona = null WHERE fk_medidorzona = ?";



    //Meter SQL

    public static final String INSERT_METER_SQL = "INSERT INTO Meter" +
            "(codmedidor,nomemedidor,fk_zona,supply_by, coduni, tipomedidor) VALUES " +
            " (?, ?, ?, ?, ?, ?);";

    //public static final String SHOW_METER_SQL = "SELECT * FROM Meter" ;

    public static final String SHOW_METER_SQL = "SELECT m.id, m.codmedidor, m.nomemedidor, (SELECT z.nome FROM Zone AS z WHERE z.id = m.fk_zona), (SELECT z.nome FROM Zone AS z WHERE z.id = CAST(m.supply_by AS INT)), m.coduni, m.tipomedidor FROM Meter AS m" ;

    public static final String DELETE_METER_SQL = "DELETE FROM Meter WHERE id = ?";

    public static final String DELETE_METERS_ZONE = "DELETE FROM Meter WHERE fk_zona = ?";

    public static final String UPDATE_METER_SQL = "UPDATE Meter SET %s = ? WHERE id = ?";

    public static final String CLEAR_ZONE_METER_SQL = "UPDATE Zone SET fk_medidorzona = null WHERE fk_medidorzona = ?";




    @Override
    public void close() throws Exception {
        if (connectX != null){
            connectX.close();
        }
    }

}
