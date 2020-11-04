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
public class ProdutoInformacaoTray {

    private Long informacaoId;
    private String titulo;
    private String texto;
    private String tipoInformacao;

    public Long getInformacaoId() {
        return informacaoId;
    }

    public void setInformacaoId(Long informacaoId) {
        this.informacaoId = informacaoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTipoInformacao() {
        return tipoInformacao;
    }

    public void setTipoInformacao(String tipoInformacao) {
        this.tipoInformacao = tipoInformacao;
    }

}
