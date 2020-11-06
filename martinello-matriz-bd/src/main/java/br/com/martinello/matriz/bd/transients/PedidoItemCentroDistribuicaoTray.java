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
public class PedidoItemCentroDistribuicaoTray {

    private Long centroDistribuicaoId;
    private Double quantidade;
    private Long situacaoProdutoId;
    private Double valorFreteEmpresa;
    private Double valorFreteCliente;

    public Long getCentroDistribuicaoId() {
        return centroDistribuicaoId;
    }

    public void setCentroDistribuicaoId(Long centroDistribuicaoId) {
        this.centroDistribuicaoId = centroDistribuicaoId;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Long getSituacaoProdutoId() {
        return situacaoProdutoId;
    }

    public void setSituacaoProdutoId(Long situacaoProdutoId) {
        this.situacaoProdutoId = situacaoProdutoId;
    }

    public Double getValorFreteEmpresa() {
        return valorFreteEmpresa;
    }

    public void setValorFreteEmpresa(Double valorFreteEmpresa) {
        this.valorFreteEmpresa = valorFreteEmpresa;
    }

    public Double getValorFreteCliente() {
        return valorFreteCliente;
    }

    public void setValorFreteCliente(Double valorFreteCliente) {
        this.valorFreteCliente = valorFreteCliente;
    }

}
