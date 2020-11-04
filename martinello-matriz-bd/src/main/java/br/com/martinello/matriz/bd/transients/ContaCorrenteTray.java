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
public class ContaCorrenteTray {

    private Double valor;
    private String tipoLancamento;
    private String observacao;
    private Boolean visivelParaCliente;

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(String tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean getVisivelParaCliente() {
        return visivelParaCliente;
    }

    public void setVisivelParaCliente(Boolean visivelParaCliente) {
        this.visivelParaCliente = visivelParaCliente;
    }

}
