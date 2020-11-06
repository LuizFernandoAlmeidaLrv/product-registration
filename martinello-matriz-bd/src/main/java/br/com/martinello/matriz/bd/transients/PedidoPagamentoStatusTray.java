/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.transients;

import java.util.Date;

/**
 *
 * @author rafael
 */
public class PedidoPagamentoStatusTray {

    private String numeroAutorizacao;
    private String numeroComprovanteVenda;
    private Date dataAtualizacao;
    private Date dataUltimoStatus;
    private String adquirente;
    private String tid;

    public String getNumeroAutorizacao() {
        return numeroAutorizacao;
    }

    public void setNumeroAutorizacao(String numeroAutorizacao) {
        this.numeroAutorizacao = numeroAutorizacao;
    }

    public String getNumeroComprovanteVenda() {
        return numeroComprovanteVenda;
    }

    public void setNumeroComprovanteVenda(String numeroComprovanteVenda) {
        this.numeroComprovanteVenda = numeroComprovanteVenda;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Date getDataUltimoStatus() {
        return dataUltimoStatus;
    }

    public void setDataUltimoStatus(Date dataUltimoStatus) {
        this.dataUltimoStatus = dataUltimoStatus;
    }

    public String getAdquirente() {
        return adquirente;
    }

    public void setAdquirente(String adquirente) {
        this.adquirente = adquirente;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

}
