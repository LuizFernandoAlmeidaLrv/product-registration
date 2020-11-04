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
public class Categoria {

    public int getCodEcommerce() {
        return codEcommerce;
    }

    public void setCodEcommerce(int codEcommerce) {
        this.codEcommerce = codEcommerce;
    }

    public String getCodPro() {
        return codPro;
    }

    public void setCodPro(String codPro) {
        this.codPro = codPro;
    }

    @Resolvable(colName = "Id")
    private int idCategoria;
    
    @Resolvable(colName = "Id_Categoria Pai")
    private int idCategoriaPai;
    
    @Resolvable(colName = "Codigo E-commerce")
    private int codEcommerce;
    
    @Resolvable(colName = "Produto")
    private String codPro;

    @Resolvable(colName = "Situação")
    private String situacao;
    
     @Resolvable(colName = "Visível")
    private String visivel;

    @Resolvable(colName = "Categoria")
    private String categoria;
    
     @Resolvable(colName = "Categoria Pai")
    private String categoriaPai;

    @Resolvable(colName = "Observação")
    private String obsCategoria;

    @Resolvable(colName = "Id_Usu. Geração")
    private long idUsuario;

    @Resolvable(colName = "Usu. Geração")
    private String nomeUsuario;

    @Resolvable(colName = "Data Geração")
    private Date dataCadastro;
    
    @Resolvable(colName = "Alterado")
    private String alterado;

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

    public void setCategoria(String Categoria) {
        this.categoria = Categoria;
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

    public String getObsCategoria() {
        return obsCategoria;
    }

    public void setObsCategoria(String obsCategoria) {
        this.obsCategoria = obsCategoria;
    }

    public String getCategoriaPai() {
        return categoriaPai;
    }

    public void setCategoriaPai(String categoriaPai) {
        this.categoriaPai = categoriaPai;
    }

    public int getIdCategoriaPai() {
        return idCategoriaPai;
    }

    public void setIdCategoriaPai(int idCategoriaPai) {
        this.idCategoriaPai = idCategoriaPai;
    }

    public String getVisivel() {
        return visivel;
    }

    public void setVisivel(String visivel) {
        this.visivel = visivel;
    }

    public String getAlterado() {
        return alterado;
    }

    public void setAlterado(String alterado) {
        this.alterado = alterado;
    }

}
