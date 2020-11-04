/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.util.integracao;

/**
 *
 * @author rafael.rosar
 */
public class JsonCustomizado {

    private String identificacao;
    private String conteudo;

    public JsonCustomizado() {
    }

    public JsonCustomizado(String identificacao, String conteudo) {
        this.identificacao = identificacao;
        this.conteudo = conteudo;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

}
