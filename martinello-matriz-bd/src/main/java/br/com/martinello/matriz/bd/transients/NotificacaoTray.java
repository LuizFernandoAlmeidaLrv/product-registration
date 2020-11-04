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
public class NotificacaoTray {

    private String urlImagem;
    private String texto;
    private String urlClique;
    private Long tipoNotificacaoId;
    private String tempo;
    private Long notificacaoId;
    private Long notificacaoUsuarioId;
    private String valor;

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getUrlClique() {
        return urlClique;
    }

    public void setUrlClique(String urlClique) {
        this.urlClique = urlClique;
    }

    public Long getTipoNotificacaoId() {
        return tipoNotificacaoId;
    }

    public void setTipoNotificacaoId(Long tipoNotificacaoId) {
        this.tipoNotificacaoId = tipoNotificacaoId;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public Long getNotificacaoId() {
        return notificacaoId;
    }

    public void setNotificacaoId(Long notificacaoId) {
        this.notificacaoId = notificacaoId;
    }

    public Long getNotificacaoUsuarioId() {
        return notificacaoUsuarioId;
    }

    public void setNotificacaoUsuarioId(Long notificacaoUsuarioId) {
        this.notificacaoUsuarioId = notificacaoUsuarioId;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}
