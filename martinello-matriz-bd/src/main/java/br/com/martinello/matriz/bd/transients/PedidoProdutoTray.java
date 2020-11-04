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
public class PedidoProdutoTray {

    private Long produtoVarianteId;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoProdutoQuantidadeTray> quantidade;
    private Double precoVenda;
    private Boolean isBrinde;

    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoProdutoAjusteTray> ajustes;

    public Long getProdutoVarianteId() {
        return produtoVarianteId;
    }

    public void setProdutoVarianteId(Long produtoVarianteId) {
        this.produtoVarianteId = produtoVarianteId;
    }

    public List<PedidoProdutoQuantidadeTray> getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(List<PedidoProdutoQuantidadeTray> quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Boolean getIsBrinde() {
        return isBrinde;
    }

    public void setIsBrinde(Boolean isBrinde) {
        this.isBrinde = isBrinde;
    }

    public List<PedidoProdutoAjusteTray> getAjustes() {
        return ajustes;
    }

    public void setAjustes(List<PedidoProdutoAjusteTray> ajustes) {
        this.ajustes = ajustes;
    }

}
