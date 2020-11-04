/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.componentes;

import br.com.martinello.componentesbasicos.Campo;
import br.com.martinello.componentesbasicos.Rotulo;
import br.com.martinello.bd.matriz.control.ListaControl;
import br.com.martinello.bd.matriz.model.domain.Lista;
import br.com.martinello.bd.matriz.model.domain.ListaItem;
import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.excessoes.ErroSistemaException;
import com.towel.el.annotation.Resolvable;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

/**
 *
 * @author Sidnei
 */
public class CampoLista extends JComboBox implements Campo {

    protected String dica;
    protected String descricaoRotulo;
    protected boolean obrigatorio;
    protected boolean editavel = true;
    protected Border bordaOriginal = null;
    protected Rotulo rRotulo;
    protected Lista lista;
    protected ListaControl listaControl;

    public CampoLista() {
    }

    public CampoLista(ComboBoxModel aModel) {
        super(aModel);
    }

    public CampoLista(Class tabela, String campo, String nomeLista) {
        try {
            listaControl = new ListaControl();
            lista = listaControl.getLista(nomeLista);

            for (ListaItem listaItem : lista.getItensLista()) {
                addItem(listaItem.getDescricao());
            }
        } catch (ErroSistemaException ex) {
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        }

        bordaOriginal = this.getBorder();
        Utilitarios.considerarEnterComoTab(this);

        try {
            if (tabela != null) {
                if (tabela.getDeclaredField(campo).getType() != String.class && tabela.getDeclaredField(campo).getType() != int.class) {
                    JoinColumn joinColumn = tabela.getDeclaredField(campo).getAnnotation(JoinColumn.class);
                    Resolvable r = tabela.getDeclaredField(campo).getAnnotation(Resolvable.class);

                    //obrigatorio = !joinColumn.nullable();
                    dica = r.colName();
                    setToolTipText(dica);
                } else {
                    Column coluna = tabela.getDeclaredField(campo).getAnnotation(Column.class);
                    Resolvable r = tabela.getDeclaredField(campo).getAnnotation(Resolvable.class);

                    //obrigatorio = !coluna.nullable();
                    dica = r.colName();
                    setToolTipText(dica);

                }
            }

        } catch (NoSuchFieldException | SecurityException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar configuração do campo " + campo, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void limpar() {
        setSelectedIndex(0);
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eVazio() {
        if (obrigatorio) {
            if (getSelectedIndex() >= 0) {
                setBorder(bordaOriginal);
                return false;
            } else {
                setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDica() {
        return dica;
    }

    @Override
    public String getDescricaoRotulo() {
        return descricaoRotulo;
    }

    @Override
    public void setDescricaoRotulo(String descricaoRotulo) {
        this.descricaoRotulo = descricaoRotulo;
    }

    @Override
    public void setObrigatorio(boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    @Override
    public void setComponenteRotulo(Rotulo rRotulo) {
        this.rRotulo = rRotulo;

        if (rRotulo != null) {
            SwingUtilities.invokeLater(() -> {
                if (obrigatorio) {
                    rRotulo.setText("<html><body>" + descricaoRotulo + "<FONT COLOR='red'><b>*</b></FONT>:</body></html>");
                } else {
                    rRotulo.setText("<html><body>" + descricaoRotulo + ":</body></html>");
                }
            });
        }
    }

    public String getString() {
        return lista.getItensLista().get(getSelectedIndex()).getValor();
    }

    public void setString(String valor) {
        Optional<ListaItem> oListaItem = lista.getItensLista().stream().filter(l -> l.getValor().equals(valor)).findFirst();
        if (oListaItem.isPresent()) {
            setSelectedItem(oListaItem.get().getDescricao());
        }
    }

    public int getInt() {
        return Integer.parseInt(getSelectedItem().toString());
    }

    public void setInt(int valor) {
        setSelectedItem(Integer.toString(valor));
    }

    @Override
    public boolean isObrigatorio() {
        return obrigatorio;
    }

    @Override
    public Rotulo getComponenteRotulo() {
        return rRotulo;
    }
}
