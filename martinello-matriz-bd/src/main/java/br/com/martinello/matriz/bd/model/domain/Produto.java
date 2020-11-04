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
public class Produto {

    @Resolvable(colName = "Código")
    private String codigo;

    @Resolvable(colName = "Código Agr")
    private String codigoAgr;

    @Resolvable(colName = "Produto")
    private String descricao;

    @Resolvable(colName = "Cód. Categoria")
    private int idCategoria;

    @Resolvable(colName = "Cód. Categoria Item")
    private int idSubCategoria;

    @Resolvable(colName = "Cód. Caracteristica")
    private int idCaracteristica;

    @Resolvable(colName = "Cód. Caracteristica Item")
    private int idCaracteristicaItem;

    @Resolvable(colName = "Cód. Descrição")
    private int idDescCaractItem;

    @Resolvable(colName = "Caracteristica")
    private String caracteristica;

    @Resolvable(colName = "Caracteristica Item")
    private String caracteristicaItem;

    @Resolvable(colName = "Descrição")
    private String descCaracteristica;

    @Resolvable(colName = "Categoria")
    private String categoria;

    @Resolvable(colName = "Categoria Item")
    private String subCategoria;

    @Resolvable(colName = "Observação")
    private String observacao;

    @Resolvable(colName = "Situação")
    private String situacao;

    @Resolvable(colName = "Visível")
    private String visivel;

    @Resolvable(colName = "Marca")
    private String marca;
    
     @Resolvable(colName = "Replicavel")
    private String replicavel;

    @Resolvable(colName = "Bloqueado")
    private String bloqueado;

    @Resolvable(colName = "Sequência")
    private int sequencia;
    
     @Resolvable(colName = "Estoque")
    private int estoque;

    private long idUsuario;

    @Resolvable(colName = "Usuário")
    private String nomeUsuario;

    @Resolvable(colName = "Data Geração")
    private Date dataCadastro;

    @Resolvable(colName = "Descrição")
    private String descLonga;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
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

    public int getIdCaracteristica() {
        return idCaracteristica;
    }

    public void setIdCaracteristica(int idCaracteristica) {
        this.idCaracteristica = idCaracteristica;
    }

    public int getIdCaracteristicaItem() {
        return idCaracteristicaItem;
    }

    public void setIdCaracteristicaItem(int idCaracteristicaItem) {
        this.idCaracteristicaItem = idCaracteristicaItem;
    }

    public int getIdDescCaractItem() {
        return idDescCaractItem;
    }

    public void setIdDescCaractItem(int idDescCaractItem) {
        this.idDescCaractItem = idDescCaractItem;
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

    public String getDescCaracteristica() {
        return descCaracteristica;
    }

    public void setDescCaracteristica(String descCaracteristica) {
        this.descCaracteristica = descCaracteristica;
    }

    public String getVisivel() {
        return visivel;
    }

    public void setVisivel(String visivel) {
        this.visivel = visivel;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(String bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public String getDescLonga() {
        return descLonga;
    }

    public void setDescLonga(String descLonga) {
        this.descLonga = descLonga;
    }

    public String getCodigoAgr() {
        return codigoAgr;
    }

    public void setCodigoAgr(String codigoAgr) {
        this.codigoAgr = codigoAgr;
    }

    public String getReplicavel() {
        return replicavel;
    }

    public void setReplicavel(String replicavel) {
        this.replicavel = replicavel;
    }

    /**
     * @return the estoque
     */
    public int getEstoque() {
        return estoque;
    }

    /**
     * @param estoque the estoque to set
     */
    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}
