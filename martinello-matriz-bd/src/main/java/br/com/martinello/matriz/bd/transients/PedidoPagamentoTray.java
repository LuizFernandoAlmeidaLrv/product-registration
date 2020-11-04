/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.transients;

import java.util.List;

/**
 *
 * @author rafael.rosar
 */
public class PedidoPagamentoTray {

    private Long formaPagamentoId;
    private Long numeroParcelas;
    private Double valorParcela;
    private List<PedidoPagamentoInformacaoAdicionalTray> informacaoAdicional;

    public Long getFormaPagamentoId() {
        return formaPagamentoId;
    }

    public void setFormaPagamentoId(Long formaPagamentoId) {
        this.formaPagamentoId = formaPagamentoId;
    }

    public Long getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Long numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public Double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(Double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public List<PedidoPagamentoInformacaoAdicionalTray> getInformacaoAdicional() {
        return informacaoAdicional;
    }

    public void setInformacaoAdicional(List<PedidoPagamentoInformacaoAdicionalTray> informacaoAdicional) {
        this.informacaoAdicional = informacaoAdicional;
    }

}
