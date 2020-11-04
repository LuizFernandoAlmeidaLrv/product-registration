/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.integracao.model.domain;

import java.util.Date;

/**
 *
 * @author rafael
 */
public class Pendencia {

    private Long id;
    private String chave;
    private String processo;
    private String operacao;
    private String situacao;
    private String tipo;
    private Long prioridade;
    private Long usuarioGeracao;
    private Date dataGeracao;
    private Long horaGeracao;
    private Long usuarioAlteracao;
    private Date dataAlteracao;
    private Long horaAlteracao;
    private Date dataProcessamento;
    private String observacao;
    public final static String ALTERACAO_PRODUTO = "ALTERACAO_PRODUTO";
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Long prioridade) {
        this.prioridade = prioridade;
    }

    public Long getUsuarioGeracao() {
        return usuarioGeracao;
    }

    public void setUsuarioGeracao(Long usuarioGeracao) {
        this.usuarioGeracao = usuarioGeracao;
    }

    public Date getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(Date dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public Long getHoraGeracao() {
        return horaGeracao;
    }

    public void setHoraGeracao(Long horaGeracao) {
        this.horaGeracao = horaGeracao;
    }

    public Long getUsuarioAlteracao() {
        return usuarioAlteracao;
    }

    public void setUsuarioAlteracao(Long usuarioAlteracao) {
        this.usuarioAlteracao = usuarioAlteracao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Long getHoraAlteracao() {
        return horaAlteracao;
    }

    public void setHoraAlteracao(Long horaAlteracao) {
        this.horaAlteracao = horaAlteracao;
    }

    public Date getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(Date dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
