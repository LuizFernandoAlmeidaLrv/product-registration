/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.model.domain;

import com.towel.el.annotation.Resolvable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class Caracteristica {

    @Resolvable(colName = "IdSubCategoria")
    private int idSubCategoria;
    
    
    @Resolvable(colName = "IdCategoria")
    private int idCategoria;
    
     @Resolvable(colName = "Sequência")
    private int seqCaracteristica;

    @Resolvable(colName = "IdCaracterística")
    private int idCaracteristica;

    @Resolvable(colName = "Situação")
    private String situacao;
    
     @Resolvable(colName = "Visível Site")
    private String visivel;

    @Resolvable(colName = "Característica")
    private String caracteristica;

    @Resolvable(colName = "SubCategoria")
    private String subCategoria;

    @Resolvable(colName = "Observação")
    private String obsCaracteristica;
    
    @Resolvable(colName = "Único")
    private String unico;

    @Resolvable(colName = "Id_Usu. Geração")
    private long idUsuario;

    @Resolvable(colName = "Usu. Geração")
    private String nomeUsuario;

    @Resolvable(colName = "Data Geração")
    private Date dataCadastro;

    public int getIdSubCategoria() {
        return idSubCategoria;
          }
    private String alterado;
    
    private List<CaracteristicaItem> caracteristicaItens = new LinkedList<>();

    public void setIdSubCategoria(int idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public int getIdCaracteristica() {
        return idCaracteristica;
    }

    public void setIdCaracteristica(int idCaracteristica) {
        this.idCaracteristica = idCaracteristica;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
    }

    public String getObsCaracteristica() {
        return obsCaracteristica;
    }

    public void setObsCaracteristica(String obsCaracteristica) {
        this.obsCaracteristica = obsCaracteristica;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public int getSeqCategoria() {
        return seqCaracteristica;
    }

    public void setSeqCategoria(int seqCategoria) {
        this.seqCaracteristica = seqCategoria;
    }

    public String getVisivel() {
        return visivel;
    }

    public void setVisivel(String visivel) {
        this.visivel = visivel;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public List<CaracteristicaItem> getCaracteristicaItens() {
        return caracteristicaItens;
    }

    public void setCaracteristicaItens(List<CaracteristicaItem> caracteristicaItens) {
        this.caracteristicaItens = caracteristicaItens;
    }

    public String getAlterado() {
        return alterado;
    }

    public void setAlterado(String alterado) {
        this.alterado = alterado;
    }

    public String getUnico() {
        return unico;
    }

    public void setUnico(String unico) {
        this.unico = unico;
    }

}
