package com.syca.apps.gob.denunciamx.model.DenunciaRest;

import java.util.ArrayList;

/**
 * Created by JARP on 10/30/14.
 */
public class Denuncia {
    String id;
    String idDenunciaSPF;
    String token;
    boolean isAnonima;
    String mail;
    String titulo;
    int idDependencia;
    int idEstadoDenuncia;
    String fechaRegistro;
    Evidencia evidencia;
    public Evidencia getEvidencia() {
        return evidencia;
    }
    public void setEvidencia(Evidencia evidencia) {
        this.evidencia = evidencia;
    }
    Ubicacion ubicacion;
    public Denuncia(){}
    public Denuncia( String id, String idDenunciaSPF,String token,
                     boolean isAnonima, String mail, String titulo,
                     int idDependencia, int idEstadoDenuncia,String fechaRegistro,
                     Evidencia evidencia,
                     Ubicacion ubicacion){
        super();
        this.id=id;
        this.idDenunciaSPF = idDenunciaSPF;
        this.token=token;
        this.isAnonima=isAnonima;
        this.mail=mail;
        this.titulo=titulo;
        this.idDependencia=idDependencia;
        this.idEstadoDenuncia=idEstadoDenuncia;
        this.fechaRegistro=fechaRegistro;
        this.evidencia=evidencia;
        this.ubicacion=ubicacion;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIdDenunciaSPF() {
        return idDenunciaSPF;
    }
    public void setIdDenunciaSPF(String idDenunciaSPF) {
        this.idDenunciaSPF = idDenunciaSPF;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public boolean isAnonima() {
        return isAnonima;
    }
    public void setAnonima(boolean isAnonima) {
        this.isAnonima = isAnonima;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public int getIdDependencia() {
        return idDependencia;
    }
    public void setIdDependencia(int idDependencia) {
        this.idDependencia = idDependencia;
    }
    public int getIdEstadoDenuncia() {
        return idEstadoDenuncia;
    }
    public void setIdEstadoDenuncia(int idEstadoDenuncia) {
        this.idEstadoDenuncia = idEstadoDenuncia;
    }
    public String getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public Ubicacion getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public static class DenunciaBuilder
    {
        String id;
        String idDenunciaSPF;
        String token;
        boolean isAnonima;
        String mail;
        String titulo;
        int idDependencia;
        int idEstadoDenuncia;
        String fechaRegistro;
        Ubicacion ubicacion;
        Evidencia evidencia;
        public DenunciaBuilder(String id, String idDenunciaSPF,String token,
                               boolean isAnonima, String mail, String titulo,
                               int idDependencia, int idEstadoDenuncia,String fechaRegistro)
        {
            this.id=id;
            this.idDenunciaSPF = idDenunciaSPF;
            this.token=token;
            this.isAnonima=isAnonima;
            this.mail=mail;
            this.titulo=titulo;
            this.idDependencia=idDependencia;
            this.idEstadoDenuncia=idEstadoDenuncia;
            this.fechaRegistro=fechaRegistro;
        }

        public DenunciaBuilder addUbicacion(Ubicacion ubicacion)
        {
            this.ubicacion=ubicacion;
            return this;
        }

        public DenunciaBuilder addFoto(Foto foto)
        {
            if(null==evidencia) evidencia = new Evidencia();
            if(null==evidencia.fotos) evidencia.fotos = new ArrayList<Foto>();
            evidencia.fotos.add(foto);
            return this;
        }

        public DenunciaBuilder addVideo(VideoEvidencia video)
        {
            if(null==evidencia) evidencia = new Evidencia();
            if(null==evidencia.fotos) evidencia.videos = new ArrayList<VideoEvidencia>();
            evidencia.videos.add(video);
            return this;
        }
        public DenunciaBuilder addAudio(Audio audio)
        {
            if(null==evidencia) evidencia = new Evidencia();
            if(null==evidencia.fotos) evidencia.audios = new ArrayList<Audio>();
            evidencia.audios.add(audio);
            return this;
        }

        public Denuncia buildDenuncia()
        {
            return new Denuncia(id, idDenunciaSPF,token,
                                isAnonima, mail, titulo,
                                idDependencia, idEstadoDenuncia,fechaRegistro,
                                evidencia, ubicacion);
        }


    }
}