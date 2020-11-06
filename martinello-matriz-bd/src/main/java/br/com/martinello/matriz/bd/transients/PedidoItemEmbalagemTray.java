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
public class PedidoItemEmbalagemTray {

    private Long tipoEmbalagemId;
    private String nomeTipoEmbalagem;
    private String mensagem;
    private Double valor;

    public Long getTipoEmbalagemId() {
        return tipoEmbalagemId;
    }

    public void setTipoEmbalagemId(Long tipoEmbalagemId) {
        this.tipoEmbalagemId = tipoEmbalagemId;
    }

    public String getNomeTipoEmbalagem() {
        return nomeTipoEmbalagem;
    }

    public void setNomeTipoEmbalagem(String nomeTipoEmbalagem) {
        this.nomeTipoEmbalagem = nomeTipoEmbalagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

}
