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
public class FreteDetalheVariacaoTray {

    private Long pesoInicial;
    private Long pesoFinal;
    private Double valorFrete;
    private Long prazoEntrega;
    private Double valorPreco;
    private Double valorPeso;

    public Long getPesoInicial() {
        return pesoInicial;
    }

    public void setPesoInicial(Long pesoInicial) {
        this.pesoInicial = pesoInicial;
    }

    public Long getPesoFinal() {
        return pesoFinal;
    }

    public void setPesoFinal(Long pesoFinal) {
        this.pesoFinal = pesoFinal;
    }

    public Double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(Double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Long getPrazoEntrega() {
        return prazoEntrega;
    }

    public void setPrazoEntrega(Long prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }

    public Double getValorPreco() {
        return valorPreco;
    }

    public void setValorPreco(Double valorPreco) {
        this.valorPreco = valorPreco;
    }

    public Double getValorPeso() {
        return valorPeso;
    }

    public void setValorPeso(Double valorPeso) {
        this.valorPeso = valorPeso;
    }

}
