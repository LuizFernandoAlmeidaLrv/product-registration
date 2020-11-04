/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.view.model;

import br.com.martinello.bd.matriz.model.domain.Coluna;
import com.towel.bean.Formatter;

/**
 *
 * @author Sidnei
 */
public class ColunaFormatter implements Formatter {

    @Override
    public Object format(Object o) {
        Coluna c = (Coluna) o;
        if (c == null)//No combo box o primeiro item sempre é null, para poder ficar em branco
        {
            return "";
        }
        return o.toString();
    }

    @Override
    public Object parse(Object o) {
        return null;
    }

    @Override
    public String getName() {
        return "coluna";
    }

}
