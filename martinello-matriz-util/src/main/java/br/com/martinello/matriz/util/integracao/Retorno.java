/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.util.integracao;

/**
 *
 * @author rafael.rosar
 */
public class Retorno {

    public Integer Codigo;
    public String status;
    public String retorno;

    public Retorno() {
    }

    public Retorno(Integer Codigo, String status, String retorno) {
        this.Codigo = Codigo;
        this.status = status;
        this.retorno = retorno;
    }

    public Integer getCodigo() {
        return Codigo;
    }

    public void setCodigo(Integer Codigo) {
        this.Codigo = Codigo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRetorno() {
        return retorno;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

}
