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
public class PedidoCentroDistribuicaoTray {

    private Double volume;
    private Double volumeCobrado;
    private Long prazoEnvio;
    private Long freteContratoId;
    private String freteContrato;
    private Double valorFreteEmpresa;
    private Double valorFreteCliente;
    private Double peso;
    private Double pesoCobrado;
    private String prazoEnvioTexto;
    private Long centroDistribuicaoId;

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getVolumeCobrado() {
        return volumeCobrado;
    }

    public void setVolumeCobrado(Double volumeCobrado) {
        this.volumeCobrado = volumeCobrado;
    }

    public Long getPrazoEnvio() {
        return prazoEnvio;
    }

    public void setPrazoEnvio(Long prazoEnvio) {
        this.prazoEnvio = prazoEnvio;
    }

    public Long getFreteContratoId() {
        return freteContratoId;
    }

    public void setFreteContratoId(Long freteContratoId) {
        this.freteContratoId = freteContratoId;
    }

    public String getFreteContrato() {
        return freteContrato;
    }

    public void setFreteContrato(String freteContrato) {
        this.freteContrato = freteContrato;
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

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getPesoCobrado() {
        return pesoCobrado;
    }

    public void setPesoCobrado(Double pesoCobrado) {
        this.pesoCobrado = pesoCobrado;
    }

    public String getPrazoEnvioTexto() {
        return prazoEnvioTexto;
    }

    public void setPrazoEnvioTexto(String prazoEnvioTexto) {
        this.prazoEnvioTexto = prazoEnvioTexto;
    }

    public Long getCentroDistribuicaoId() {
        return centroDistribuicaoId;
    }

    public void setCentroDistribuicaoId(Long centroDistribuicaoId) {
        this.centroDistribuicaoId = centroDistribuicaoId;
    }

}
