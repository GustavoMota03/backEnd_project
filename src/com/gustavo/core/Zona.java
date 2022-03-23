package com.gustavo.core;


public class Zona {

    public static String[] HEADERS = new String[]{"NAME", "ID", "ZONE METER", "LENGTH", "POPULATION\n"};


    double populacao;
    double totalCond;
    String nome;
    String codGeo;
    //Medidor medidorSaida;
    Medidor medidorZona;
    String tmpZona;


    public double getPopulacao() {
        return populacao;
    }

    public void setPopulacao(double populacao) {
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
            return getNome() + "\t" + getCodGeo() + "\t"+ (this.getMedidorZona()== null ? "null" : this.getMedidorZona().getCodMedidor()) +"\t" + getTotalCond() + "\t" + getPopulacao();
        }

    public String serializeZone() {
        String[] fields = new String[]{
                getNome(),
                getCodGeo(),
                tmpZona,
                String.valueOf(getTotalCond()),
                String.valueOf(getPopulacao()),
        };

        return String.join("\t", fields);
    }

}
