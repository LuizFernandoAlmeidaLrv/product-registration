/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.model.domain;

import com.towel.el.annotation.Resolvable;

/**
 *
 * @author luiz.almeida
 */
public class Arquivo {

    @Resolvable(colName = "Nome Original")
    private String nome;

    @Resolvable(colName = "Novo Nome")
    private String novoNome;

    @Resolvable(colName = "Sequência")
    private int sequencia;

    @Resolvable(colName = "Principal")
    private String principal;

    @Resolvable(colName = "Situação")
    private String situacao;

    @Resolvable(colName = "Visivel")
    private String visivel;

    @Resolvable(colName = "Produto")
    private String produto;

    @Resolvable(colName = "Derivação")
    private String derProduto;

    @Resolvable(colName = "Diretório")
    private String diretorio;
    
    @Resolvable(colName = "Diretório")
    private String diretorioDestino;

    @Resolvable(colName = "Extenção")
    private String extencao;

    @Resolvable(colName = "Alterado")
    private String alterado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNovoNome() {
        return novoNome;
    }

    public void setNovoNome(String novoNome) {
        this.novoNome = novoNome;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
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

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getDerProduto() {
        return derProduto;
    }

    public void setDerProduto(String derProduto) {
        this.derProduto = derProduto;
    }

    public String getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }

    public String getExtencao() {
        return extencao;
    }

    public void setExtencao(String extencao) {
        this.extencao = extencao;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getAlterado() {
        return alterado;
    }

    public void setAlterado(String alterado) {
        this.alterado = alterado;
    }

    public String getDiretorioDestino() {
        return diretorioDestino;
    }

    public void setDiretorioDestino(String diretorioDestino) {
        this.diretorioDestino = diretorioDestino;
    }
}
