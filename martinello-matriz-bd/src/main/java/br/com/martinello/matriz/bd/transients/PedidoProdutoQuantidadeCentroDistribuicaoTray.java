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
public class PedidoProdutoQuantidadeCentroDistribuicaoTray {

    private Long centroDistribuicaoId;
    private Double quantidade;

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

}
