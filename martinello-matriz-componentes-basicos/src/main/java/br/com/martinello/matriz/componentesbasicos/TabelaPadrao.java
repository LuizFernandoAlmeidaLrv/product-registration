/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.componentesbasicos;

import br.com.martinello.matriz.componentesbasicos.cellRenders.DateCellRender;
import br.com.martinello.matriz.componentesbasicos.cellRenders.DecimalCellRender;
import br.com.martinello.matriz.componentesbasicos.cellRenders.LocalDateCellRender;
import br.com.martinello.matriz.componentesbasicos.cellRenders.StringCellRender;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

/**
 *
 * @author Sidnei
 */
public class TabelaPadrao extends JXTable {

    public TabelaPadrao() {
        setColumnControlVisible(true);

        Locale lReais = new Locale("pt", "BR");
        //NumberFormat _format = NumberFormat.getCurrencyInstance(lReais);
        NumberFormat _format = NumberFormat.getCurrencyInstance();
        DecimalCellRender _renderer = new DecimalCellRender(_format);
        setDefaultRenderer(Double.class, _renderer);

        setDefaultRenderer(java.util.Date.class, new DateCellRender());
        setDefaultRenderer(LocalDate.class, new LocalDateCellRender());
        setDefaultRenderer(String.class, new StringCellRender());

        setHighlighters(HighlighterFactory.createAlternateStriping());

        setFont(ConstantesGlobais.FONTE_10_NORMAL);
        getTableHeader().setFont(ConstantesGlobais.FONTE_10_BOLD);

        setHorizontalScrollEnabled(true);
        getTableHeader().setReorderingAllowed(false);
    }

    public TabelaPadrao(TableModel dm) {
        super(dm);
        setColumnControlVisible(true);

        Locale lReais = new Locale("pt", "BR");
        //NumberFormat _format = NumberFormat.getCurrencyInstance(lReais);
        NumberFormat _format = NumberFormat.getCurrencyInstance();
        DecimalCellRender _renderer = new DecimalCellRender(_format);
        setDefaultRenderer(Double.class, _renderer);

        setDefaultRenderer(Date.class, new DateCellRender());
        setDefaultRenderer(LocalDate.class, new LocalDateCellRender());
        setDefaultRenderer(String.class, new StringCellRender());

        setHighlighters(HighlighterFactory.createAlternateStriping());

        setFont(ConstantesGlobais.FONTE_10_NORMAL);
        getTableHeader().setFont(ConstantesGlobais.FONTE_10_BOLD);

        setHorizontalScrollEnabled(true);
        getTableHeader().setReorderingAllowed(false);
    }

    public TabelaPadrao(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
        setColumnControlVisible(true);
    }

    public TabelaPadrao(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
        super(dm, cm, sm);
        setColumnControlVisible(true);
    }

    public TabelaPadrao(int numRows, int numColumns) {
        super(numRows, numColumns);
        setColumnControlVisible(true);
    }

    public TabelaPadrao(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
        setColumnControlVisible(true);
    }

    public int[] getLinhasSelecionadas() {
        //Seleciona os ??ndices da tabela que est??o selecionados
        int linhasSelecionadas[] = getSelectedRows();
        for (int i = 0; i < linhasSelecionadas.length; i++) {
            linhasSelecionadas[i] = convertRowIndexToModel(linhasSelecionadas[i]);
        }
        // a sele????o ser?? de acordo com o modelo da JTableModel - ent??o
        // de acordo com os ??ndices selecionados ap??s execu????o do RowSorter
        return linhasSelecionadas;
    }

    /**
     * <p>
     * Converte o ??ndice selecionado da tabela para o ??ndice do modelo da
     * tabela.</p>
     * <p>
     * ?? uma vers??o sobrecarregada do m??todo que realiza a convers??o somente
     * para um ??tem</p>
     *
     * @return Retorna um inteiro com o ??ndice selecionado
     */
    public int getLinhaSelecionada() {
        int indices[] = getLinhasSelecionadas();
        if (indices != null && indices.length > 0) {
            return indices[0];
        } else {
            return -1;
        }
    }
}
