/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.transients;

import java.util.Date;
import java.util.List;

/**
 *
 * @author rafael.rosar
 */
public class PedidoTray {

    private Long pedidoId;
    private String carrinhoId;
    private Long situacaoPedidoId;
    private Date data;
    private Double valorTotal;
    private Double valorJuros;
    private Double valorDesconto;
    private Long usuarioId;
    private Long enderecoId;
    private Boolean isMobile;
    private Long eventoId;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoProdutoTray> produtos;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoFreteTray> fretes;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoPagamentoTray> pagamento;
    private Long canalId;
    private Long transacaoId;
    private String observacao;
    private Boolean valido;
    private String cupomDesconto;

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getCarrinhoId() {
        return carrinhoId;
    }

    public void setCarrinhoId(String carrinhoId) {
        this.carrinhoId = carrinhoId;
    }

    public Long getSituacaoPedidoId() {
        return situacaoPedidoId;
    }

    public void setSituacaoPedidoId(Long situacaoPedidoId) {
        this.situacaoPedidoId = situacaoPedidoId;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getValorJuros() {
        return valorJuros;
    }

    public void setValorJuros(Double valorJuros) {
        this.valorJuros = valorJuros;
    }

    public Double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(Double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }

    public Boolean getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(Boolean isMobile) {
        this.isMobile = isMobile;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public List<PedidoProdutoTray> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<PedidoProdutoTray> produtos) {
        this.produtos = produtos;
    }

    public List<PedidoFreteTray> getFretes() {
        return fretes;
    }

    public void setFretes(List<PedidoFreteTray> fretes) {
        this.fretes = fretes;
    }

    public List<PedidoPagamentoTray> getPagamento() {
        return pagamento;
    }

    public void setPagamento(List<PedidoPagamentoTray> pagamento) {
        this.pagamento = pagamento;
    }

    public Long getCanalId() {
        return canalId;
    }

    public void setCanalId(Long canalId) {
        this.canalId = canalId;
    }

    public Long getTransacaoId() {
        return transacaoId;
    }

    public void setTransacaoId(Long transacaoId) {
        this.transacaoId = transacaoId;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean getValido() {
        return valido;
    }

    public void setValido(Boolean valido) {
        this.valido = valido;
    }

    public String getCupomDesconto() {
        return cupomDesconto;
    }

    public void setCupomDesconto(String cupomDesconto) {
        this.cupomDesconto = cupomDesconto;
    }

}
