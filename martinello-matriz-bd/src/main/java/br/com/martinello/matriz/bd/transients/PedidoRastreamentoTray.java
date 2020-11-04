/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.transients;

import java.util.Date;

/**
 *
 * @author rafael.rosar
 */
public class PedidoRastreamentoTray {

    private Long situacaoPedidoId;
    private Long centroDistribuicaoId;
    private String rastreamento;
    private Date dataEvento;
    private String numeroNotaFiscal;
    private String chaveAcessoNFE;
    private String urlNFE;
    private String serieNFE;
    private Long cfop;
    private String urlRastreamento;

    public Long getSituacaoPedidoId() {
        return situacaoPedidoId;
    }

    public void setSituacaoPedidoId(Long situacaoPedidoId) {
        this.situacaoPedidoId = situacaoPedidoId;
    }

    public Long getCentroDistribuicaoId() {
        return centroDistribuicaoId;
    }

    public void setCentroDistribuicaoId(Long centroDistribuicaoId) {
        this.centroDistribuicaoId = centroDistribuicaoId;
    }

    public String getRastreamento() {
        return rastreamento;
    }

    public void setRastreamento(String rastreamento) {
        this.rastreamento = rastreamento;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public void setNumeroNotaFiscal(String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public String getChaveAcessoNFE() {
        return chaveAcessoNFE;
    }

    public void setChaveAcessoNFE(String chaveAcessoNFE) {
        this.chaveAcessoNFE = chaveAcessoNFE;
    }

    public String getUrlNFE() {
        return urlNFE;
    }

    public void setUrlNFE(String urlNFE) {
        this.urlNFE = urlNFE;
    }

    public String getSerieNFE() {
        return serieNFE;
    }

    public void setSerieNFE(String serieNFE) {
        this.serieNFE = serieNFE;
    }

    public Long getCfop() {
        return cfop;
    }

    public void setCfop(Long cfop) {
        this.cfop = cfop;
    }

    public String getUrlRastreamento() {
        return urlRastreamento;
    }

    public void setUrlRastreamento(String urlRastreamento) {
        this.urlRastreamento = urlRastreamento;
    }

}
