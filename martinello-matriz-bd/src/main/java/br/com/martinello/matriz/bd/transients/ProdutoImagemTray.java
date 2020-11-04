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
public class ProdutoImagemTray {

    private String base64;
    private String formato;
    private Boolean exibirMiniatura;
    private Boolean estampa;
    private Long ordem;
    private Long produtoImagemId;
    private String visivelSite;
    private String situacaoRegistro;

    private long empresaErp;
    private String produtoErp;
    private String derivacaoErp;
    private long sequenciaErp;
    private String descricaoErp;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Boolean getExibirMiniatura() {
        return exibirMiniatura;
    }

    public void setExibirMiniatura(Boolean exibirMiniatura) {
        this.exibirMiniatura = exibirMiniatura;
    }

    public Boolean getEstampa() {
        return estampa;
    }

    public void setEstampa(Boolean estampa) {
        this.estampa = estampa;
    }

    public Long getOrdem() {
        return ordem;
    }

    public void setOrdem(Long ordem) {
        this.ordem = ordem;
    }

    public Long getProdutoImagemId() {
        return produtoImagemId;
    }

    public void setProdutoImagemId(Long produtoImagemId) {
        this.produtoImagemId = produtoImagemId;
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

    public String getDerivacaoErp() {
        return derivacaoErp;
    }

    public void setDerivacaoErp(String derivacaoErp) {
        this.derivacaoErp = derivacaoErp;
    }

    public long getSequenciaErp() {
        return sequenciaErp;
    }

    public void setSequenciaErp(long sequenciaErp) {
        this.sequenciaErp = sequenciaErp;
    }

    public String getDescricaoErp() {
        return descricaoErp;
    }

    public void setDescricaoErp(String descricaoErp) {
        this.descricaoErp = descricaoErp;
    }

    public String getVisivelSite() {
        return visivelSite;
    }

    public void setVisivelSite(String visivelSite) {
        this.visivelSite = visivelSite;
    }

    public String getSituacaoRegistro() {
        return situacaoRegistro;
    }

    public void setSituacaoRegistro(String situacaoRegistro) {
        this.situacaoRegistro = situacaoRegistro;
    }

}
