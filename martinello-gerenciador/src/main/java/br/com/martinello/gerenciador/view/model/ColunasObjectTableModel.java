/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.view.model;

import br.com.martinello.bd.matriz.model.domain.Coluna;
import com.towel.swing.table.ObjectTableModel;

/**
 *
 * @author Sidnei
 */
public class ColunasObjectTableModel extends ObjectTableModel<Coluna> {

    public ColunasObjectTableModel() {
        super(Coluna.class, "coluna,tituloCurto,tituloLongo,descricao,tipo,tamanho,precisao,mascara,lista,obrigatorio,valorPadrao");
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        String nomeColuna = getColumnResolver(columnIndex).getFieldName();
        if (getValue(rowIndex).getTipo() != null && getValue(rowIndex).getTipo().equals("Serial")) {

            if (nomeColuna.equals("tamanho")
                    || nomeColuna.equals("precisao")
                    || nomeColuna.equals("mascara")
                    || nomeColuna.equals("lista")
                    || nomeColuna.equals("obrigatorio")
                    || nomeColuna.equals("valorPadrao")) {
                return false;
            }
            return true;
        }
//        if (getColumnResolver(columnIndex).getFieldName().equals("tipo")) {
//            return true;
//        }
        return true;
    }

}
