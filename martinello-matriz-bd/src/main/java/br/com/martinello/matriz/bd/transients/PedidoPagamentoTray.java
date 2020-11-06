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
    private Double valorDesconto;
    private Double valorJuros;
    private Double valorTotal;
    private List<PedidoPagamentoBoletoTray> boleto;
    private List<PedidoPagamentoCartaoCreditoTray> cartaoCredito;
    private List<PedidoPagamentoStatusTray> pagamentoStatus;
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

    public Double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(Double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Double getValorJuros() {
        return valorJuros;
    }

    public void setValorJuros(Double valorJuros) {
        this.valorJuros = valorJuros;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<PedidoPagamentoBoletoTray> getBoleto() {
        return boleto;
    }

    public void setBoleto(List<PedidoPagamentoBoletoTray> boleto) {
        this.boleto = boleto;
    }

    public List<PedidoPagamentoCartaoCreditoTray> getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(List<PedidoPagamentoCartaoCreditoTray> cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public List<PedidoPagamentoStatusTray> getPagamentoStatus() {
        return pagamentoStatus;
    }

    public void setPagamentoStatus(List<PedidoPagamentoStatusTray> pagamentoStatus) {
        this.pagamentoStatus = pagamentoStatus;
    }

    public List<PedidoPagamentoInformacaoAdicionalTray> getInformacaoAdicional() {
        return informacaoAdicional;
    }

    public void setInformacaoAdicional(List<PedidoPagamentoInformacaoAdicionalTray> informacaoAdicional) {
        this.informacaoAdicional = informacaoAdicional;
    }

}
