/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.transients;

import com.google.gson.annotations.Expose;

/**
 *
 * @author rafael.rosar
 */
public class FabricanteTray {

    private Long fabricanteId;
    private String nome;
    private String urlLogoTipo;
    private String urlLink;
    private String urlCarrossel;
    private String codigoErp;

    public Long getFabricanteId() {
        return fabricanteId;
    }

    public void setFabricanteId(Long fabricanteId) {
        this.fabricanteId = fabricanteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrlLogoTipo() {
        return urlLogoTipo;
    }

    public void setUrlLogoTipo(String urlLogoTipo) {
        this.urlLogoTipo = urlLogoTipo;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public String getUrlCarrossel() {
        return urlCarrossel;
    }

    public void setUrlCarrossel(String urlCarrossel) {
        this.urlCarrossel = urlCarrossel;
    }

    public String getCodigoErp() {
        return codigoErp;
    }

    public void setCodigoErp(String codigoErp) {
        this.codigoErp = codigoErp;
    }

}
