/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.transients;

import java.util.Date;

/**
 *
 * @author rafael.rosar
 */
public class PedidoFreteTray {

    private Long centroDistribuicaoId;
    private Long freteContratoId;
    private Long peso;
    private Long pesoCobrado;
    private Long volume;
    private Long volumeCobrado;
    private Long prazoEnvio;
    private Double valorFreteEmpresa;
    private Double valorFreteCliente;
    private Date dataEntrega;

    public Long getCentroDistribuicaoId() {
        return centroDistribuicaoId;
    }

    public void setCentroDistribuicaoId(Long centroDistribuicaoId) {
        this.centroDistribuicaoId = centroDistribuicaoId;
    }

    public Long getFreteContratoId() {
        return freteContratoId;
    }

    public void setFreteContratoId(Long freteContratoId) {
        this.freteContratoId = freteContratoId;
    }

    public Long getPeso() {
        return peso;
    }

    public void setPeso(Long peso) {
        this.peso = peso;
    }

    public Long getPesoCobrado() {
        return pesoCobrado;
    }

    public void setPesoCobrado(Long pesoCobrado) {
        this.pesoCobrado = pesoCobrado;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Long getVolumeCobrado() {
        return volumeCobrado;
    }

    public void setVolumeCobrado(Long volumeCobrado) {
        this.volumeCobrado = volumeCobrado;
    }

    public Long getPrazoEnvio() {
        return prazoEnvio;
    }

    public void setPrazoEnvio(Long prazoEnvio) {
        this.prazoEnvio = prazoEnvio;
    }

    public Double getValorFreteEmpresa() {
        return valorFreteEmpresa;
    }

    public void setValorFreteEmpresa(Double valorFreteEmpresa) {
        this.valorFreteEmpresa = valorFreteEmpresa;
    }

    public Double getValorFreteCliente() {
        return valorFreteCliente;
    }

    public void setValorFreteCliente(Double valorFreteCliente) {
        this.valorFreteCliente = valorFreteCliente;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
}
