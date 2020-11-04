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
public class ParceiroTray {

    private Long parceiroId;
    private String nome;
    private Long tabelaPrecoId;
    private Long portfolioId;
    private String tipoEscopo;
    private Boolean ativo;
    private Boolean isMarketPlace;

    public Long getParceiroId() {
        return parceiroId;
    }

    public void setParceiroId(Long parceiroId) {
        this.parceiroId = parceiroId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getTabelaPrecoId() {
        return tabelaPrecoId;
    }

    public void setTabelaPrecoId(Long tabelaPrecoId) {
        this.tabelaPrecoId = tabelaPrecoId;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getTipoEscopo() {
        return tipoEscopo;
    }

    public void setTipoEscopo(String tipoEscopo) {
        this.tipoEscopo = tipoEscopo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getIsMarketPlace() {
        return isMarketPlace;
    }

    public void setIsMarketPlace(Boolean isMarketPlace) {
        this.isMarketPlace = isMarketPlace;
    }

}
