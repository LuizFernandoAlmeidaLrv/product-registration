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
    private String tipoRastreamentoPedido;
    private Date data;
    private Date dataPagamento;
    private Date dataUltimaAtualizacao;
    private Double valorFrete;
    private Double valorTotalPedido;
    private Double valorTotal;
    private Double valorJuros;
    private Double valorDesconto;
    private Double valorDebitoCC;
    private String marketPlacePedidoId;
    private String marketPlacePedidoSiteId;
    private String canalOrigem;
    private Long usuarioId;
    private Long enderecoId;
    private Boolean isMobile;
    private Long eventoId;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoItemTray> itens;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoFreteTray> fretes;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoAssinaturaTray> assinatura;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoPagamentoTray> pagamento;
    private PedidoEnderecoTray pedidoEndereco;
    private Long canalId;
    private Long transacaoId;
    private String cupomDesconto;
    private Long retiradaLojaId;
    private boolean isPedidoEvento;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoObservacaoTray> observacao;
    private Double valorCreditoFidelidade;
    private boolean valido;
    private Double valorSubTotalSemDescontos;

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

    public String getTipoRastreamentoPedido() {
        return tipoRastreamentoPedido;
    }

    public void setTipoRastreamentoPedido(String tipoRastreamentoPedido) {
        this.tipoRastreamentoPedido = tipoRastreamentoPedido;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Date getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    public Double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(Double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Double getValorTotalPedido() {
        return valorTotalPedido;
    }

    public void setValorTotalPedido(Double valorTotalPedido) {
        this.valorTotalPedido = valorTotalPedido;
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

    public Double getValorDebitoCC() {
        return valorDebitoCC;
    }

    public void setValorDebitoCC(Double valorDebitoCC) {
        this.valorDebitoCC = valorDebitoCC;
    }

    public String getMarketPlacePedidoId() {
        return marketPlacePedidoId;
    }

    public void setMarketPlacePedidoId(String marketPlacePedidoId) {
        this.marketPlacePedidoId = marketPlacePedidoId;
    }

    public String getMarketPlacePedidoSiteId() {
        return marketPlacePedidoSiteId;
    }

    public void setMarketPlacePedidoSiteId(String marketPlacePedidoSiteId) {
        this.marketPlacePedidoSiteId = marketPlacePedidoSiteId;
    }

    public String getCanalOrigem() {
        return canalOrigem;
    }

    public void setCanalOrigem(String canalOrigem) {
        this.canalOrigem = canalOrigem;
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

    public List<PedidoItemTray> getItens() {
        return itens;
    }

    public void setItens(List<PedidoItemTray> itens) {
        this.itens = itens;
    }

    public List<PedidoFreteTray> getFretes() {
        return fretes;
    }

    public void setFretes(List<PedidoFreteTray> fretes) {
        this.fretes = fretes;
    }

    public List<PedidoAssinaturaTray> getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(List<PedidoAssinaturaTray> assinatura) {
        this.assinatura = assinatura;
    }

    public List<PedidoPagamentoTray> getPagamento() {
        return pagamento;
    }

    public void setPagamento(List<PedidoPagamentoTray> pagamento) {
        this.pagamento = pagamento;
    }

    public PedidoEnderecoTray getPedidoEndereco() {
        return pedidoEndereco;
    }

    public void setPedidoEndereco(PedidoEnderecoTray pedidoEndereco) {
        this.pedidoEndereco = pedidoEndereco;
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

    public String getCupomDesconto() {
        return cupomDesconto;
    }

    public void setCupomDesconto(String cupomDesconto) {
        this.cupomDesconto = cupomDesconto;
    }

    public Long getRetiradaLojaId() {
        return retiradaLojaId;
    }

    public void setRetiradaLojaId(Long retiradaLojaId) {
        this.retiradaLojaId = retiradaLojaId;
    }

    public boolean isIsPedidoEvento() {
        return isPedidoEvento;
    }

    public void setIsPedidoEvento(boolean isPedidoEvento) {
        this.isPedidoEvento = isPedidoEvento;
    }

    public List<PedidoObservacaoTray> getObservacao() {
        return observacao;
    }

    public void setObservacao(List<PedidoObservacaoTray> observacao) {
        this.observacao = observacao;
    }

    public Double getValorCreditoFidelidade() {
        return valorCreditoFidelidade;
    }

    public void setValorCreditoFidelidade(Double valorCreditoFidelidade) {
        this.valorCreditoFidelidade = valorCreditoFidelidade;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public Double getValorSubTotalSemDescontos() {
        return valorSubTotalSemDescontos;
    }

    public void setValorSubTotalSemDescontos(Double valorSubTotalSemDescontos) {
        this.valorSubTotalSemDescontos = valorSubTotalSemDescontos;
    }

}
