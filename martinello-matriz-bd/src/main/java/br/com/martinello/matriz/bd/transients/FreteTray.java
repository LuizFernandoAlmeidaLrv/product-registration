/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.transients;

/**
 *
 * @author rafael.rosar
 */
public class FreteTray {

    private Long freteId;
    private String nome;
    private Boolean ativo;
    private Long volumeMaximo;
    private Long pesoCubado;
    private Long entregaAgendadaConfiguracaoId;
    private String linkRastreamento;
    private Boolean ehAssinatura;
    private Long larguraMaxima;
    private Long alturaMaxima;
    private Long comprimentoMaximo;
    private Long limiteMaximoDimensoes;
    private Long limitePesoCubado;
    private Long tempoMinimoDespacho;
    private Long centroDistribuicaoId;
    private Long alorMinimoProdutos;

    public Long getFreteId() {
        return freteId;
    }

    public void setFreteId(Long freteId) {
        this.freteId = freteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getVolumeMaximo() {
        return volumeMaximo;
    }

    public void setVolumeMaximo(Long volumeMaximo) {
        this.volumeMaximo = volumeMaximo;
    }

    public Long getPesoCubado() {
        return pesoCubado;
    }

    public void setPesoCubado(Long pesoCubado) {
        this.pesoCubado = pesoCubado;
    }

    public Long getEntregaAgendadaConfiguracaoId() {
        return entregaAgendadaConfiguracaoId;
    }

    public void setEntregaAgendadaConfiguracaoId(Long entregaAgendadaConfiguracaoId) {
        this.entregaAgendadaConfiguracaoId = entregaAgendadaConfiguracaoId;
    }

    public String getLinkRastreamento() {
        return linkRastreamento;
    }

    public void setLinkRastreamento(String linkRastreamento) {
        this.linkRastreamento = linkRastreamento;
    }

    public Boolean getEhAssinatura() {
        return ehAssinatura;
    }

    public void setEhAssinatura(Boolean ehAssinatura) {
        this.ehAssinatura = ehAssinatura;
    }

    public Long getLarguraMaxima() {
        return larguraMaxima;
    }

    public void setLarguraMaxima(Long larguraMaxima) {
        this.larguraMaxima = larguraMaxima;
    }

    public Long getAlturaMaxima() {
        return alturaMaxima;
    }

    public void setAlturaMaxima(Long alturaMaxima) {
        this.alturaMaxima = alturaMaxima;
    }

    public Long getComprimentoMaximo() {
        return comprimentoMaximo;
    }

    public void setComprimentoMaximo(Long comprimentoMaximo) {
        this.comprimentoMaximo = comprimentoMaximo;
    }

    public Long getLimiteMaximoDimensoes() {
        return limiteMaximoDimensoes;
    }

    public void setLimiteMaximoDimensoes(Long limiteMaximoDimensoes) {
        this.limiteMaximoDimensoes = limiteMaximoDimensoes;
    }

    public Long getLimitePesoCubado() {
        return limitePesoCubado;
    }

    public void setLimitePesoCubado(Long limitePesoCubado) {
        this.limitePesoCubado = limitePesoCubado;
    }

    public Long getTempoMinimoDespacho() {
        return tempoMinimoDespacho;
    }

    public void setTempoMinimoDespacho(Long tempoMinimoDespacho) {
        this.tempoMinimoDespacho = tempoMinimoDespacho;
    }

    public Long getCentroDistribuicaoId() {
        return centroDistribuicaoId;
    }

    public void setCentroDistribuicaoId(Long centroDistribuicaoId) {
        this.centroDistribuicaoId = centroDistribuicaoId;
    }

    public Long getAlorMinimoProdutos() {
        return alorMinimoProdutos;
    }

    public void setAlorMinimoProdutos(Long alorMinimoProdutos) {
        this.alorMinimoProdutos = alorMinimoProdutos;
    }

}
