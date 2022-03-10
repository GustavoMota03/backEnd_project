package com.gustavo.core;

public class Medidor {
    private String nomeMedidor;
    private String codMedidor;
    private String codUni;
    private int tipoMedidor;
    private Zona zona;

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

    public int getTipoMedidor() {
        return tipoMedidor;
    }

    public void setTipoMedidor(int tipoMedidor) {
        this.tipoMedidor = tipoMedidor;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }
}
