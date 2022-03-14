package com.gustavo.core;

import java.util.HashMap;

public class Medidor {
    private static int TSV_COLUMN_ZONE = 2;

    String nomeMedidor;
    String codMedidor;
    String codUni;
    Zona zona;
    TipoMedidor tipo;
    int id;

    private int getId() {
        return id;
    }

    public enum TipoMedidor{
        Flow("Flow"),
        Pressure("Pressure"),
        Level("Level"),
        Rainfall("Rainfall"),
        Temperature("Temperature"),

        ;

        String name;

        TipoMedidor(String text) {
            name = text;
        }

        static TipoMedidor parse(String input){
            for (TipoMedidor tipo :TipoMedidor.values()){
                if (tipo.name.equals(input)){
                    return tipo;
                }
            }
            return null;
        }
    }


    public String getNomeMedidor() {
        return nomeMedidor;
    }

    public void setNomeMedidor(String nomeMedidor) {
        this.nomeMedidor = nomeMedidor;
    }

    public String getCodMedidor() {
        return codMedidor;
    }

    public void setCodMedidor(String codMedidor) {
        this.codMedidor = codMedidor;
    }

    public String getCodUni() {
        return codUni;
    }

    public void setCodUni(String codUni) {
        this.codUni = codUni;
    }

    public TipoMedidor getTipoMedidor() {
        return tipo;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    @Override
    public String toString() {
        return getCodMedidor() + "\t" + getNomeMedidor() + "\t" + getZona() + "\t"+ getCodUni() +"\t" + getTipoMedidor() + "\n";
    }

    HashMap<String, Zona> mapaZonas = new HashMap<>();
    HashMap<String, Medidor> mapaMedidor = new HashMap<>();



    public static Medidor parse(String input, HashMap<String, Zona> mapaZonas) throws Exception {

        Medidor novoMedidor = new Medidor();

        String[] split = input.split("\t");
        //Limpar Args
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].trim();
        }


        novoMedidor.zona = mapaZonas.get(split[TSV_COLUMN_ZONE]);
        if (novoMedidor.zona == null){
            throw new Exception("Zona "+ split[TSV_COLUMN_ZONE] +" não encontrada!");
        }
        novoMedidor.setCodMedidor(split[0]);
        novoMedidor.setNomeMedidor(split[1]);
        //novoMedidor.setZona(split[2]);
        novoMedidor.setCodUni(split[3]);
        novoMedidor.tipo = TipoMedidor.parse(split[4]);


        return novoMedidor;
    }
}
