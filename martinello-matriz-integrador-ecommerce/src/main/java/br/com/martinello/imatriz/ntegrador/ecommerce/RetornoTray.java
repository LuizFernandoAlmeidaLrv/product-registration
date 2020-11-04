/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.imatriz.ntegrador.ecommerce;

/**
 *
 * @author rafael
 */
public class RetornoTray {

    private boolean resultadoOperacao;
    private Long codigo;
    private String mensagem;

    public boolean isResultadoOperacao() {
        return resultadoOperacao;
    }

    public void setResultadoOperacao(boolean resultadoOperacao) {
        this.resultadoOperacao = resultadoOperacao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
