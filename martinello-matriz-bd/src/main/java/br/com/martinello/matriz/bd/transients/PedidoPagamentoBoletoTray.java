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
public class PedidoPagamentoBoletoTray {

    private String urlBoleto;
    private String codigoDeBarras;

    public String getUrlBoleto() {
        return urlBoleto;
    }

    public void setUrlBoleto(String urlBoleto) {
        this.urlBoleto = urlBoleto;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

}
