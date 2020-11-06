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
public class PedidoAssinaturaTray {

    private Long assinaturaId;
    private Long grupoAssinaturaId;
    private String tipoPeriodo;
    private Long tempoPeriodo;
    private Double percentualDesconto;

    public Long getAssinaturaId() {
        return assinaturaId;
    }

    public void setAssinaturaId(Long assinaturaId) {
        this.assinaturaId = assinaturaId;
    }

    public Long getGrupoAssinaturaId() {
        return grupoAssinaturaId;
    }

    public void setGrupoAssinaturaId(Long grupoAssinaturaId) {
        this.grupoAssinaturaId = grupoAssinaturaId;
    }

    public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    public void setTipoPeriodo(String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    public Long getTempoPeriodo() {
        return tempoPeriodo;
    }

    public void setTempoPeriodo(Long tempoPeriodo) {
        this.tempoPeriodo = tempoPeriodo;
    }

    public Double getPercentualDesconto() {
        return percentualDesconto;
    }

    public void setPercentualDesconto(Double percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }
}
