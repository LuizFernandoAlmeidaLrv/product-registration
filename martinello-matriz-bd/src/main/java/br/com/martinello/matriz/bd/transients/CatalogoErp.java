/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.transients;

/**
 *
 * @author rafael
 */
public class CatalogoErp {

    private String situacaoRelacionamento;
    private String visivelSite;
    private Long codigoEcommerce;
    private Long codigoCatalogoEcomerce;
    private String observacao;

    private String categoriaPrincipal;
    private long empresaErp;
    private String produtoErp;
    private long catalogoErp;

    public String getSituacaoRelacionamento() {
        return situacaoRelacionamento;
    }

    public void setSituacaoRelacionamento(String situacaoRelacionamento) {
        this.situacaoRelacionamento = situacaoRelacionamento;
    }

    public String getVisivelSite() {
        return visivelSite;
    }

    public void setVisivelSite(String visivelSite) {
        this.visivelSite = visivelSite;
    }

    public Long getCodigoEcommerce() {
        return codigoEcommerce;
    }

    public void setCodigoEcommerce(Long codigoEcommerce) {
        this.codigoEcommerce = codigoEcommerce;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getCategoriaPrincipal() {
        return categoriaPrincipal;
    }

    public void setCategoriaPrincipal(String categoriaPrincipal) {
        this.categoriaPrincipal = categoriaPrincipal;
    }

    public long getEmpresaErp() {
        return empresaErp;
    }

    public void setEmpresaErp(long empresaErp) {
        this.empresaErp = empresaErp;
    }

    public String getProdutoErp() {
        return produtoErp;
    }

    public void setProdutoErp(String produtoErp) {
        this.produtoErp = produtoErp;
    }

    public long getCatalogoErp() {
        return catalogoErp;
    }

    public void setCatalogoErp(long catalogoErp) {
        this.catalogoErp = catalogoErp;
    }

    public Long getCodigoCatalogoEcomerce() {
        return codigoCatalogoEcomerce;
    }

    public void setCodigoCatalogoEcomerce(Long codigoCatalogoEcomerce) {
        this.codigoCatalogoEcomerce = codigoCatalogoEcomerce;
    }

}
