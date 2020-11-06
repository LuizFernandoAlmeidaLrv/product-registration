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
public class PedidoItemFormulaTray {

    private String chaveAjuste;
    private Double valor;
    private String nome;
    private String expressao;
    private String expressaoInterpretada;
    private String endPoint;

    public String getChaveAjuste() {
        return chaveAjuste;
    }

    public void setChaveAjuste(String chaveAjuste) {
        this.chaveAjuste = chaveAjuste;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getExpressao() {
        return expressao;
    }

    public void setExpressao(String expressao) {
        this.expressao = expressao;
    }

    public String getExpressaoInterpretada() {
        return expressaoInterpretada;
    }

    public void setExpressaoInterpretada(String expressaoInterpretada) {
        this.expressaoInterpretada = expressaoInterpretada;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

}
