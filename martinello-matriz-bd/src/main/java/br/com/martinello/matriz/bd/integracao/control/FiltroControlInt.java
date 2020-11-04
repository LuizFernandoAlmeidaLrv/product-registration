/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.integracao.control;

import br.com.martinello.matriz.bd.transients.FiltroInt;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author rafael
 */
public class FiltroControlInt {

    public FiltroControlInt() {
    }

    public String getMontaFiltro(List<FiltroInt> lFiltros) {
        /**
         * Formato: String, Long, Integer Tipo: IGUAL, MAIORIGUAL, etc....
         */

        String filtroWhere = "";

        if (lFiltros.size() > 0) {
            filtroWhere = " WHERE 0 = 0";
        }

        for (FiltroInt filtro : lFiltros) {

            String operadorIgualdade = "";

            if (filtro.getTipo().equals(FiltroInt.IGUAL)) {
                operadorIgualdade = " = ";
            } else if (filtro.getTipo().equals(FiltroInt.MAIOR)) {
                operadorIgualdade = " > ";
            } else if (filtro.getTipo().equals(FiltroInt.MAIOROUIGUAL)) {
                operadorIgualdade = " >= ";
            } else if (filtro.getTipo().equals(FiltroInt.MENOR)) {
                operadorIgualdade = " < ";
            } else if (filtro.getTipo().equals(FiltroInt.MENOROUIGUAL)) {
                operadorIgualdade = " >= ";
            } else if (filtro.getTipo().equals(FiltroInt.DIFERENTE)) {
                operadorIgualdade = " <> ";
            }

            if (filtro.getTipo().equals(FiltroInt.ARRAY)) {

                String[] filtroIn = filtro.getValor().toString().split(",");
                List<String> lFiltroIn = new LinkedList<>();

                String valor = "";

                for (String filt : filtroIn) {
                    if (!filt.trim().equals("")) {
                        if (filtro.getFormato().equals(FiltroInt.STRING)) {
                            if (valor.length() == 0) {
                                valor += "'" + filt + "'";
                            } else {
                                valor += ",'" + filt + "'";
                            }
                        } else if (filtro.getTipo().equals(FiltroInt.LONG)) {
                            if (valor.length() == 0) {
                                valor += filt;
                            } else {
                                valor += "," + filt;
                            }
                        } else if (filtro.getTipo().equals(FiltroInt.DATE)) {
                            if (valor.length() == 0) {
                                valor += filt;
                            } else {
                                valor += ",'" + filt + "'";
                            }
                        }
                    }
                }

                if (valor.length() > 0) {
                    filtroWhere += "\n AND " + filtro.getTabela() + "." + filtro.getCampo() + " IN (" + valor + ")";
                }

            } else {
                if (filtro.getFormato().equals(FiltroInt.STRING)) {
                    filtroWhere += "\n AND " + filtro.getTabela() + "." + filtro.getCampo() + operadorIgualdade + "'" + filtro.getValor() + "'";
                } else if (filtro.getTipo().equals(FiltroInt.LONG)) {
                    filtroWhere += "\n AND " + filtro.getTabela() + "." + filtro.getCampo() + operadorIgualdade + filtro.getValor() + "";
                } else if (filtro.getTipo().equals(FiltroInt.DATE)) {
                    filtroWhere += "\n AND " + filtro.getTabela() + "." + filtro.getCampo() + operadorIgualdade + "'" + filtro.getValor() + "'";
                }
            }
        }
        return filtroWhere;
    }
}
