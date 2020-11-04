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
public class RetornoPadronizado {

    public String codigo;
    public String message;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RetornoPadronizado(String codigo, String message) {
        this.codigo = codigo;
        this.message = message;
    }

}
