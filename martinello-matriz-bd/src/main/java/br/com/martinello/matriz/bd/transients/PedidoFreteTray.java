/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.transients;

import java.util.Date;
import java.util.List;

/**
 *
 * @author rafael.rosar
 */
public class PedidoFreteTray {

    private Long freteContratoId;
    private String freteContrato;
    private String referenciaConector;
    private Long peso;
    private Long pesoCobrado;
    private Long volume;
    private Long volumeCobrado;
    private Long prazoEnvio;
    private String prazoEnvioTexto;
    private Double valorFreteEmpresa;
    private Double valorFreteCliente;
    private Long retiradaLojaId;
    private List<PedidoCentroDistribuicaoTray> centrosDistribuicao;
    private String servico;
    private String retiradaAgendada;
    private String agendamento;

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

    public String getReferenciaConector() {
        return referenciaConector;
    }

    public void setReferenciaConector(String referenciaConector) {
        this.referenciaConector = referenciaConector;
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

    public String getPrazoEnvioTexto() {
        return prazoEnvioTexto;
    }

    public void setPrazoEnvioTexto(String prazoEnvioTexto) {
        this.prazoEnvioTexto = prazoEnvioTexto;
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

    public Long getRetiradaLojaId() {
        return retiradaLojaId;
    }

    public void setRetiradaLojaId(Long retiradaLojaId) {
        this.retiradaLojaId = retiradaLojaId;
    }

    public List<PedidoCentroDistribuicaoTray> getCentrosDistribuicao() {
        return centrosDistribuicao;
    }

    public void setCentrosDistribuicao(List<PedidoCentroDistribuicaoTray> centrosDistribuicao) {
        this.centrosDistribuicao = centrosDistribuicao;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getRetiradaAgendada() {
        return retiradaAgendada;
    }

    public void setRetiradaAgendada(String retiradaAgendada) {
        this.retiradaAgendada = retiradaAgendada;
    }

    public String getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(String agendamento) {
        this.agendamento = agendamento;
    }

}
