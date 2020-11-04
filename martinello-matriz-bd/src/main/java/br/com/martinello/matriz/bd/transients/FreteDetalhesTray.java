/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.transients;

import java.util.List;

/**
 *
 * @author rafael.rosar
 */
public class FreteDetalhesTray {

    private Long freteId;
    private Long cepInicial;
    private Long cepFinal;

    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<FreteDetalheVariacaoTray> variacoesFreteDetalhe;

    public Long getFreteId() {
        return freteId;
    }

    public void setFreteId(Long freteId) {
        this.freteId = freteId;
    }

    public Long getCepInicial() {
        return cepInicial;
    }

    public void setCepInicial(Long cepInicial) {
        this.cepInicial = cepInicial;
    }

    public Long getCepFinal() {
        return cepFinal;
    }

    public void setCepFinal(Long cepFinal) {
        this.cepFinal = cepFinal;
    }

    public List<FreteDetalheVariacaoTray> getVariacoesFreteDetalhe() {
        return variacoesFreteDetalhe;
    }

    public void setVariacoesFreteDetalhe(List<FreteDetalheVariacaoTray> variacoesFreteDetalhe) {
        this.variacoesFreteDetalhe = variacoesFreteDetalhe;
    }

}
