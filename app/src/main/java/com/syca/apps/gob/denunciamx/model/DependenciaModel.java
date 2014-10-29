package com.syca.apps.gob.denunciamx.model;

/**
 * Created by JARP on 10/29/14.
 */
public class DependenciaModel {


    public DependenciaModel(int idDependencia, String dependencia)
    {
        this.idDependencia=idDependencia;
        this.dependencia=dependencia;
    }

    int idDependencia;
    String dependencia;

    public int getIdDependencia() {
        return idDependencia;
    }

    public void setIdDependencia(int idDependencia) {
        this.idDependencia = idDependencia;
    }
}
