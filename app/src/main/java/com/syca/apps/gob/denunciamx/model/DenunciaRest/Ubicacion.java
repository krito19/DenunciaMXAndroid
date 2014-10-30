package com.syca.apps.gob.denunciamx.model.DenunciaRest;

/**
 * Created by JARP on 10/30/14.
 */
public class Ubicacion
{
    public Ubicacion(){}
    public Ubicacion(float coordLat,float coordLong)
    {
        this.coordLat=coordLat;
        this.coordLong=coordLong;
    }
    float coordLat;
    float coordLong;
    public float getCoordLat() {
        return coordLat;
    }
    public void setCoordLat(float coordLat) {
        this.coordLat = coordLat;
    }
    public float getCoordLong() {
        return coordLong;
    }
    public void setCoordLong(float coordLong) {
        this.coordLong = coordLong;
    }
}