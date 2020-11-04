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
public class TabelaPrecoProdutoTray {

    private Long tabelaPrecoProdutoVarianteId;
    private Long tabelaPrecoId;
    private Long produtoVarianteId;
    private String sku;
    private Double precoDe;
    private Double precoPor;

    public Long getTabelaPrecoProdutoVarianteId() {
        return tabelaPrecoProdutoVarianteId;
    }

    public void setTabelaPrecoProdutoVarianteId(Long tabelaPrecoProdutoVarianteId) {
        this.tabelaPrecoProdutoVarianteId = tabelaPrecoProdutoVarianteId;
    }

    public Long getTabelaPrecoId() {
        return tabelaPrecoId;
    }

    public void setTabelaPrecoId(Long tabelaPrecoId) {
        this.tabelaPrecoId = tabelaPrecoId;
    }

    public Long getProdutoVarianteId() {
        return produtoVarianteId;
    }

    public void setProdutoVarianteId(Long produtoVarianteId) {
        this.produtoVarianteId = produtoVarianteId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getPrecoDe() {
        return precoDe;
    }

    public void setPrecoDe(Double precoDe) {
        this.precoDe = precoDe;
    }

    public Double getPrecoPor() {
        return precoPor;
    }

    public void setPrecoPor(Double precoPor) {
        this.precoPor = precoPor;
    }

}
