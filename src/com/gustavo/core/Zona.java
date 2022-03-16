package com.gustavo.core;


public class Zona {

    public static String[] HEADERS = new String[]{"NAME", "ID", "ZONE", "METER", "LENGTH", "POPULATION\n"};


    int populacao;
    double totalCond;
    String nome;
    String codGeo;
    //Medidor medidorSaida;
    Medidor medidorZona;
    String tmpZona;


    public String getTmpZona() {
        return tmpZona;
    }

    public void setTmpZona(String tmpZona) {
        this.tmpZona = tmpZona;
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

    /*public Medidor getMedidorSaida() {
        return medidorSaida;
    }

    public void setMedidorSaida(Medidor medidorSaida) {
        this.medidorSaida = medidorSaida;
    }*/

    public Medidor getMedidorZona() {
        return medidorZona;
    }

    public void setMedidorZona(Medidor medidorZona) {
        this.medidorZona = medidorZona;
    }



    @Override
    public String toString() {
        return getNome() + "\t" + getCodGeo() + "\t"+ tmpZona +"\t" + getTotalCond() + "\t" + getPopulacao();
    }

}
