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
public class PedidoProdutoQuantidadeTray {

    private Double quantidadeTotal;
    private List<PedidoProdutoQuantidadeCentroDistribuicaoTray> quantidadePorCentroDeDistribuicao;

    public Double getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(Double quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public List<PedidoProdutoQuantidadeCentroDistribuicaoTray> getQuantidadePorCentroDeDistribuicao() {
        return quantidadePorCentroDeDistribuicao;
    }

    public void setQuantidadePorCentroDeDistribuicao(List<PedidoProdutoQuantidadeCentroDistribuicaoTray> quantidadePorCentroDeDistribuicao) {
        this.quantidadePorCentroDeDistribuicao = quantidadePorCentroDeDistribuicao;
    }

}
