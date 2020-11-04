/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.util.ordem;

import java.util.Objects;

/**
 *
 * @author sidnei.vieira
 */
public class Ordem {

    public static String DATE = "Date";
    public static String TIMESTAMP = "Timestamp";
    public static String STRING = "String";
    public static String LONG = "Long";
    public static String DOUBLE = "Double";
    public static String INTEGER = "Integer";
    public static String ARRAY = "Array";
    public static String CAIXA = "Caixa";
    public static String LOJA = "Loja";
    public static String USUARIO = "Usuario";
    public static String CLIENTE = "Cliente";
    public static String CLIENTE_CR_MOVIMENTO = "ClienteCRMovimento";

    public static String ASC = "Ascendente";
    public static String DESC = "Descendente";

    private String campo;
    private String formato;
    private String tipo;
    private Integer sequencia;

    public Ordem(String campo, String formato, String tipo, Integer sequencia) {
        this.campo = campo;
        this.formato = formato;
        this.tipo = tipo;
        this.sequencia = sequencia;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.campo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ordem other = (Ordem) obj;
        if (!Objects.equals(this.campo, other.campo)) {
            return false;
        }
        return true;
    }

}
