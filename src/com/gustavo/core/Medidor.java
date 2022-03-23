package com.gustavo.core;

import java.util.HashMap;

public class Medidor {

    public static String[] HEADERS = new String[]{"ID", "NAME", "ZONE", "Supplied by", "Units", "Type\n"};

    public static int TSV_COLUMN_ZONE = 2;

    String nomeMedidor;
    String codMedidor;
    String codUni;
    String suppliedBy;
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
        return codMedidor ;
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

    public String getSuppliedBy() { return suppliedBy; }

    public void setSuppliedBy(String suppliedBy) { this.suppliedBy = suppliedBy; }

    @Override
    public String toString() {
        return getCodMedidor() + "\t" + getNomeMedidor() + "\t" + zona.getCodGeo() + "\t" + getSuppliedBy() + "\t" + getCodUni() +"\t" + getTipoMedidor();
    }


    public static Medidor parse(String input, HashMap<String, Zona> mapaZonas) throws Exception {

        Medidor novoMedidor = new Medidor();

        String[] split = input.split("\t");
        //Limpar Args
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].trim();
            if (split[3] == (null)){
                return null;
            }
            if(split[2].equals("System")){
                return null;
            }
        }


        Zona zona = mapaZonas.get(split[TSV_COLUMN_ZONE]);
        if (zona != null) {
            novoMedidor.setZona(zona);
            if (zona.tmpZona.equals(split[0])) {
                zona.setMedidorZona(novoMedidor);
            }
        }

        if (novoMedidor.zona == null){
            //throw new Exception("Zona "+ split[TSV_COLUMN_ZONE] +" não encontrada!");
        }
        novoMedidor.setCodMedidor(split[0]);
        novoMedidor.setNomeMedidor(split[1]);
        novoMedidor.setZona(zona);
        novoMedidor.setSuppliedBy(split[3]);
        novoMedidor.setCodUni(split[4]);
        novoMedidor.tipo = TipoMedidor.parse(split[5]);


        return novoMedidor;
    }

    public String serializeMeter() {
        String[] fields = new String[]{
                getCodMedidor(),
                getNomeMedidor(),
                String.valueOf(getZona().getCodGeo()),
                getSuppliedBy(),
                getCodUni(),
                String.valueOf(getTipoMedidor()),
        };

        return String.join("\t", fields);
    }
}
