package com.gustavo.core;


public class Zona {

    private static int curId = 0;

    private int id;
    private int populacao;
    private double totalCond;
    private String nome;
    private String codGeo;
    private Medidor medidorSaida;
    private Medidor medidorZona;

    public Zona() {
        id = curId++;
    }

    public int getPopulacao() {
        return populacao;
    }

    public void setPopulacao(int populacao) {
        this.populacao = populacao;
    }

    public double getTotalCond() {
        return totalCond;
    }

    public void setTotalCond(double totalCond) {
        this.totalCond = totalCond;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodGeo() {
        return codGeo;
    }

    public void setCodGeo(String codGeo) {
        this.codGeo = codGeo;
    }

    public Medidor getMedidorSaida() {
        return medidorSaida;
    }

    public void setMedidorSaida(Medidor medidorSaida) {
        this.medidorSaida = medidorSaida;
    }

    public Medidor getMedidorZona() {
        return medidorZona;
    }

    public void setMedidorZona(Medidor medidorZona) {
        this.medidorZona = medidorZona;
    }

    @Override
    public String toString() {
        return getId() + "\t" + getCodGeo() + "\t" + getNome() + "\t" + getPopulacao();
    }

    private int getId() {
        return id;
    }
}
