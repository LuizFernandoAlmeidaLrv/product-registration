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
public class CategoriaTray {

    private Long id;
    private String nome;
    private Long categoriaPaiId;
    private String categoriaERPId;
    private String categoriaERPPaiId;
    private Boolean ativo;
    private Boolean isReseller;
    private String exibirMatrizAtributos;
    private Long quantidadeMaximaCompraUnidade;
    private Double valorMinimoCompra;
    private Boolean exibeMenu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCategoriaPaiId() {
        return categoriaPaiId;
    }

    public void setCategoriaPaiId(Long categoriaPaiId) {
        this.categoriaPaiId = categoriaPaiId;
    }

    public String getCategoriaERPId() {
        return categoriaERPId;
    }

    public void setCategoriaERPId(String categoriaERPId) {
        this.categoriaERPId = categoriaERPId;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getIsReseller() {
        return isReseller;
    }

    public void setIsReseller(Boolean isReseller) {
        this.isReseller = isReseller;
    }

    public String getExibirMatrizAtributos() {
        return exibirMatrizAtributos;
    }

    public void setExibirMatrizAtributos(String exibirMatrizAtributos) {
        this.exibirMatrizAtributos = exibirMatrizAtributos;
    }

    public Long getQuantidadeMaximaCompraUnidade() {
        return quantidadeMaximaCompraUnidade;
    }

    public void setQuantidadeMaximaCompraUnidade(Long quantidadeMaximaCompraUnidade) {
        this.quantidadeMaximaCompraUnidade = quantidadeMaximaCompraUnidade;
    }

    public Double getValorMinimoCompra() {
        return valorMinimoCompra;
    }

    public void setValorMinimoCompra(Double valorMinimoCompra) {
        this.valorMinimoCompra = valorMinimoCompra;
    }

    public Boolean getExibeMenu() {
        return exibeMenu;
    }

    public void setExibeMenu(Boolean exibeMenu) {
        this.exibeMenu = exibeMenu;
    }

    public String getCategoriaERPPaiId() {
        return categoriaERPPaiId;
    }

    public void setCategoriaERPPaiId(String categoriaERPPaiId) {
        this.categoriaERPPaiId = categoriaERPPaiId;
    }

}
