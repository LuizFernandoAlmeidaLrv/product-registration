/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.model.domain;

import com.towel.el.annotation.Resolvable;
import java.util.Date;

/**
 *
 * @author luiz.almeida
 */
public class CaracteristicaItem {

    public String getReplicavel() {
        return replicavel;
    }

    public void setReplicavel(String replicavel) {
        this.replicavel = replicavel;
    }

    @Resolvable(colName = " Código")
    private int idCaracteristicaItem;

    @Resolvable(colName = "IdCaracterística")
    private int idCaracteristica;

    @Resolvable(colName = "IdCategoria")
    private int idCategoria;

    @Resolvable(colName = "IdSubCategoria")
    private int idSubCategoria;

    @Resolvable(colName = "Sequência")
    private int seqCatIte;

    @Resolvable(colName = "Situação")
    private String situacao;

    @Resolvable(colName = "Visível Site")
    private String visivel;
    
    @Resolvable(colName = "Replicável")
    private String replicavel;

    @Resolvable(colName = "Característica")
    private String caracteristica;

    @Resolvable(colName = "Item Característica")
    private String caracteristicaItem;

    @Resolvable(colName = "SubCategoria")
    private String subCategoria;

    @Resolvable(colName = "Observação")
    private String observacao;

    @Resolvable(colName = "Ususário")
    private long idUsuario;

    @Resolvable(colName = "Nome Usuário")
    private String nomeUsuario;

    @Resolvable(colName = "Data Geração")
    private Date dataCadastro;
    
    private String alterado;

    public int getIdCaracteristicaItem() {
        return idCaracteristicaItem;
    }

    public void setIdCaracteristicaItem(int idCaracteristicaItem) {
        this.idCaracteristicaItem = idCaracteristicaItem;
    }

    public int getIdCaracteristica() {
        return idCaracteristica;
    }

    public void setIdCaracteristica(int idCaracteristica) {
        this.idCaracteristica = idCaracteristica;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(int idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public int getSeqCatIte() {
        return seqCatIte;
    }

    public void setSeqCatIte(int seqCatIte) {
        this.seqCatIte = seqCatIte;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getVisivel() {
        return visivel;
    }

    public void setVisivel(String visivel) {
        this.visivel = visivel;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public String getCaracteristicaItem() {
        return caracteristicaItem;
    }

    public void setCaracteristicaItem(String caracteristicaItem) {
        this.caracteristicaItem = caracteristicaItem;
    }

    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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

    public String getAlterado() {
        return alterado;
    }

    public void setAlterado(String alterado) {
        this.alterado = alterado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.idCaracteristicaItem;
        hash = 53 * hash + this.idCaracteristica;
        hash = 53 * hash + this.idCategoria;
        hash = 53 * hash + this.idSubCategoria;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CaracteristicaItem other = (CaracteristicaItem) obj;
        if (this.idCaracteristicaItem != other.idCaracteristicaItem) {
            return false;
        }
        if (this.idCaracteristica != other.idCaracteristica) {
            return false;
        }
        if (this.idCategoria != other.idCategoria) {
            return false;
        }
        if (this.idSubCategoria != other.idSubCategoria) {
            return false;
        }
        return true;
    }
    
    

}
