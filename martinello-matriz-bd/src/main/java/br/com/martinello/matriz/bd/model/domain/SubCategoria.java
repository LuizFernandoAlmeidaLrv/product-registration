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
public class SubCategoria {

    @Resolvable(colName = "IdSubCategoria")
    private int idSubCategoria;
    
    @Resolvable(colName = "IdCategoria")
    private int idCategoria;

    @Resolvable(colName = "Situação")
    private String situacao;

    @Resolvable(colName = "Categoria")
    private String categoria;

    @Resolvable(colName = "SubCategoria")
    private String subCategoria;

    @Resolvable(colName = "Observação")
    private String obsSubCategoria;

    @Resolvable(colName = "Usuário")
    private long idUsuario;

    @Resolvable(colName = "Nome Usuário")
    private String nomeUsuario;

    @Resolvable(colName = "Data Geração")
    private Date dataCadastro;

    public int getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(int idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
    }

    public String getObsSubCategoria() {
        return obsSubCategoria;
    }

    public void setObsSubCategoria(String obsSubCategoria) {
        this.obsSubCategoria = obsSubCategoria;
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

}