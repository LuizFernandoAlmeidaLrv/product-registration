/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.componentes;

import br.com.martinello.componentesbasicos.CampoString;
import br.com.martinello.componentesbasicos.Rotulo;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Sidnei
 * @param <E>
 * @param <I>
 */
public class CampoConsulta extends CampoString implements ActionListener, FocusListener {

    protected JButton jbConsultar = new JButton("...");
    protected JLabel jlDescricao;
    protected Class tabela;
    protected String campo;
    //protected Object<E> listaNew, listaOld;
    protected String valorNew = "", valorOld = "", filtroFixo = "";

    protected Consultavel consultavel;

    private Collection<CampoConsultaListener> campoConsultaListeners = new ArrayList<CampoConsultaListener>();

    public CampoConsulta() {
        this(null, null, null, null, true, null, null);
    }

    public CampoConsulta(Consultavel consultavel, Class tabela, String campo, String rotulo, Rotulo jlDescricao, String filtroFixo) {
        this(consultavel, tabela, campo, rotulo, false, null, jlDescricao, filtroFixo);
    }

    public CampoConsulta(Consultavel consultavel, Class tabela, String campo, String rotulo, boolean obrigatorio, Rotulo jlDescricao, String filtroFixo) {
        this(consultavel, tabela, campo, rotulo, false, null, jlDescricao, filtroFixo);
    }

    public CampoConsulta(Consultavel consultavel, Class tabela, String campo, String rotulo, boolean obrigatorio, Rotulo rotuloCampo, Rotulo jlDescricao, String filtroFixo) {
        super(tabela, campo, rotulo, obrigatorio, rotuloCampo);

        this.consultavel = consultavel;
        this.tabela = tabela;
        this.campo = campo;
        this.jlDescricao = jlDescricao;
        this.filtroFixo = filtroFixo;
        this.obrigatorio = obrigatorio;

        //setBorder(null);
        //editorComponent.setForeground(Color.red);
        setLayout(new BorderLayout());
        jbConsultar.setMargin(new Insets(0, 0, 0, 0));

        add(jbConsultar, BorderLayout.EAST);

        //addFocusListener(this);
        jbConsultar.addActionListener(this);

        jbConsultar.setFocusable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TelaConsulta telaConsulta = new TelaConsulta(this, null, filtroFixo);
    }

    public Consultavel getConsultavel() {
        return consultavel;
    }

    public void setValor(Object valor) {
        /*
        listaOld = listaNew;
        valorOld = valorNew;

        if (lista != null) {
            listaNew = lista;
            valorNew = "" + listaNew.getIdLista();

            setString("" + listaNew.getIdLista());

            if (jlDescricao != null) {
                jlDescricao.setText(listaNew.getDescricao());
            }
        } else {
            if (jlDescricao != null) {
                jlDescricao.setText("");
            }
        }*/

        if (valor != null) {
            valorOld = valorNew;

            consultavel.setValor((Serializable) valor);
            setString(consultavel.getChave().toString());

            valorNew = consultavel.getChave().toString();

            if (jlDescricao != null) {
                jlDescricao.setText(consultavel.getDescricao());
            }

            disparaValorSelecionado();
        }
    }

    public Object getValor() {
        return consultavel.getValor();
    }

    public void setJlDescricao(JLabel jlDescricao) {
        this.jlDescricao = jlDescricao;
    }

    @Override
    public void focusGained(FocusEvent e) {
        selectAll();
    }

    @Override
    public void focusLost(FocusEvent e) {

        /*
        if (getString().length() > 0 && !valorNew.equals(getString())) {
            EntityManager em = ConexaoDerby.getEntityManager();
            List<Lista> listas;

            TypedQuery<Lista> tqListas;

            if (getString().matches("[0-9]+")) {
                tqListas = em.createQuery("from Lista where idLista = :filtroId or lista like :filtro or descricao like :filtro", Lista.class).setParameter("filtroId", Long.parseLong(getString())).setParameter("filtro", "%" + getString() + "%");
                listas = tqListas.getResultList();
            } else {
                tqListas = em.createQuery("from Lista where lista like :filtro or descricao like :filtro", Lista.class).setParameter("filtro", "%" + getString() + "%");
                listas = tqListas.getResultList();
            }

            if (listas.size() == 1) {
                setValor(listas.get(0));
            } else {
                setValor(null);
                TelaConsulta telaConsulta = new TelaConsulta(this, tqListas);
            }
        }
         */
        if (getString().length() > 0 && !valorNew.equals(getString())) {
            List listas = consultavel.pesquisar(getString());

            if (listas.size() == 1) {
                setValor((Serializable) listas.get(0));

            } else {
                consultavel.setValor(null);
                TelaConsulta telaConsulta = new TelaConsulta(this, listas, filtroFixo);
            }
        }
//        else {
//            consultavel.setValor(null);
//        }
    }

    @Override
    public boolean eValido() {
        return consultavel.eValido();
    }

    @Override
    public boolean eVazio() {
        if (obrigatorio) {
            if ((!consultavel.eVazio() && getString().length() > 0)) {
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
    public void limpar() {
        super.limpar();
        if (jlDescricao != null) {
            jlDescricao.setText("");
        }

        consultavel.setValor((Serializable) null);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        jbConsultar.setEnabled(enabled);
    }

    public synchronized void addCampoConsultaListener(CampoConsultaListener l) {
        if (!campoConsultaListeners.contains(l)) {
            campoConsultaListeners.add(l);
        }
    }

    public synchronized void removeCampoConsultaListener(CampoConsultaListener l) {
        campoConsultaListeners.remove(l);
    }

    private void disparaValorSelecionado() {
        Collection<CampoConsultaListener> tl;

        synchronized (this) {
            tl = (Collection) (((ArrayList) campoConsultaListeners).clone());
        }

        CampoConsultaEvento evento = new CampoConsultaEvento(this);

        for (CampoConsultaListener t : tl) {
            t.valorSelecionado(evento);
        }
    }
}
