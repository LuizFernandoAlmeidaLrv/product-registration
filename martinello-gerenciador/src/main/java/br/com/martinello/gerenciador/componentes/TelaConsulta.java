/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.componentes;

import br.com.martinello.util.anotacoes.FormatoColuna;
import br.com.martinello.componentesbasicos.ListaConfiguracaoCampo;
import br.com.martinello.bd.matriz.model.dao.ConexaoOracle;
import com.sun.glass.events.KeyEvent;
import com.towel.el.annotation.Resolvable;
import com.towel.swing.table.ObjectTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Sidnei
 */
public class TelaConsulta extends javax.swing.JDialog {

    protected List<ListaConfiguracaoCampo> listaCampos = new ArrayList<>();

    protected String colunas = "";
    protected CampoConsulta campoConsulta;
    protected ObjectTableModel otmDados;
    protected String filtroFixo;

    protected Class tabela;
    protected String campo;

    /**
     * Creates new form TelaConsulta
     *
     * @param campoConsulta
     * @param lista
     * @param filtroFixo
     */
    public TelaConsulta(CampoConsulta campoConsulta, List lista, String filtroFixo) {
        super();
        setModal(true);
        this.campoConsulta = campoConsulta;
        this.filtroFixo = filtroFixo;

        initComponents();

        jxtDados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //listaCampos.add(new ListaConfiguracaoCampo("Filtro Geral", "", FormatoColuna.TEXT));
        //jcbCampoFiltro.addItem("Filtro Geral");
        try {

            tabela = campoConsulta.getConsultavel().getTabela();
            for (Field f : tabela.getDeclaredFields()) {

                if (f.getType() != List.class && !f.getName().equals("serialVersionUID")) {
                    Resolvable r = tabela.getDeclaredField(f.getName()).getAnnotation(Resolvable.class);
                    ListaConfiguracaoCampo listaConfiguracaoCampo = new ListaConfiguracaoCampo();
                    listaConfiguracaoCampo.setNome(f.getName());
                    listaConfiguracaoCampo.setDescricao(r.colName());

                    if (f.getType() == String.class) {
                        listaConfiguracaoCampo.setTipo(FormatoColuna.TEXT);
                    } else if (f.getType() == Long.class || f.getType() == long.class) {
                        listaConfiguracaoCampo.setTipo(FormatoColuna.INTEGER);
                    } else if (f.getType() == Date.class) {
                        listaConfiguracaoCampo.setTipo(FormatoColuna.DATE);
                    } else if (f.getType() == Double.class) {
                        listaConfiguracaoCampo.setTipo(FormatoColuna.REAL);
                    } else if (f.getType() == String.class) {

                    }

                    //System.out.println(f);
                    listaCampos.add(listaConfiguracaoCampo);

                    jcbCampoFiltro.addItem(r.colName());

                    colunas += colunas.trim().length() > 0 ? "," + f.getName() : f.getName();

                }

            }

        } catch (NoSuchFieldException | SecurityException ex) {
            ex.printStackTrace();
        }

        otmDados = new ObjectTableModel<>(tabela, colunas);

        jxtDados.setModel(otmDados);

        if (lista == null) {
            filtrar();
        } else {
            otmDados.setData(lista);
            jxtDados.packAll();
        }

        jxtDados.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    selecionar();
                }
            }
        });

        setLocationRelativeTo(campoConsulta);

        SwingUtilities.invokeLater(() -> {
            if (otmDados.getData().size() > 0) {
                jxtDados.setRowSelectionInterval(0, 0);
                jxtDados.grabFocus();
            }
        });

        setVisible(true);

    }

    protected void filtrar() {

        String campoFiltro = listaCampos.get(jcbCampoFiltro.getSelectedIndex()).getNome();
        String valorFiltro = csFiltro.getString();
        String filtro = "";
        EntityManager em = ConexaoOracle.getEntityManager();
        TypedQuery tqListas = null;

        if (valorFiltro.length() > 0) {

            if (jcbTipoFiltro.getSelectedItem().toString().equals("Igual")) {
                filtro = "And " + campoFiltro + " = :filtro";
            } else if (jcbTipoFiltro.getSelectedItem().toString().equals("Contendo")) {
                filtro = "And " + campoFiltro + " Like :filtro";
                valorFiltro = "%" + valorFiltro + "%";
            } else if (jcbTipoFiltro.getSelectedItem().toString().equals("Iniciando")) {
                filtro = "And " + campoFiltro + " Like :filtro";
                valorFiltro = valorFiltro + "%";
            }

            if (listaCampos.get(jcbCampoFiltro.getSelectedIndex()).getTipo() == FormatoColuna.INTEGER) {
                tqListas = em.createQuery("from " + tabela.getName() + " where 0 = 0 " + filtro + filtroFixo, tabela).setParameter("filtro", Long.parseLong(csFiltro.getString()));
            } else if (listaCampos.get(jcbCampoFiltro.getSelectedIndex()).getTipo() == FormatoColuna.DATE) {
                DateTimeFormatter formatterBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dataFiltro = LocalDate.parse(csFiltro.getString(), formatterBR);
                tqListas = em.createQuery("from " + tabela.getName() + " where 0 = 0 " + filtro + filtroFixo, tabela).setParameter("filtro", dataFiltro);
            } else if (listaCampos.get(jcbCampoFiltro.getSelectedIndex()).getTipo() == FormatoColuna.REAL) {
                tqListas = em.createQuery("from " + tabela.getName() + " where 0 = 0 " + filtro + filtroFixo, tabela).setParameter("filtro", Double.valueOf(csFiltro.getString()));
            } else if (listaCampos.get(jcbCampoFiltro.getSelectedIndex()).getTipo() == FormatoColuna.TEXT) {
                tqListas = em.createQuery("from " + tabela.getName() + " where 0 = 0 upper(" + filtro + ")" + filtroFixo, tabela).setParameter("filtro", valorFiltro.toUpperCase());
            } else if (listaCampos.get(jcbCampoFiltro.getSelectedIndex()).getTipo() == FormatoColuna.TIME) {
                //tqListas = em.createQuery("from " + campoConsulta.getTabela().getName() + " where 0 = 0 " + filtro, Lista.class).setParameter("filtro", Long.parseLong(getString()));
            } else if (listaCampos.get(jcbCampoFiltro.getSelectedIndex()).getTipo() == FormatoColuna.TIME_STAMP) {
                //tqListas = em.createQuery("from " + campoConsulta.getTabela().getName() + " where 0 = 0 " + filtro, Lista.class).setParameter("filtro", Long.parseLong(getString()));
            }
        } else {
            tqListas = em.createQuery("from " + tabela.getName(), tabela);
        }

        if (tqListas != null) {
            otmDados.setData(tqListas.getResultList());
            jxtDados.packAll();
        }

    }

    protected void selecionar() {
        if (jxtDados.getSelectedRow() >= 0) {
            campoConsulta.setValor(otmDados.getValue(jxtDados.convertRowIndexToModel(jxtDados.getSelectedRow())));
            dispose();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jcbCampoFiltro = new javax.swing.JComboBox<>();
        jcbTipoFiltro = new javax.swing.JComboBox<>();
        csFiltro = new br.com.martinello.componentesbasicos.CampoString();
        jbFiltrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jbSelecionar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jxtDados = new org.jdesktop.swingx.JXTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta");
        setMinimumSize(new java.awt.Dimension(700, 515));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro"));
        jPanel1.setPreferredSize(new java.awt.Dimension(607, 50));

        jcbTipoFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Igual", "Contendo", "Iniciando" }));

        jbFiltrar.setText("Filtrar");
        jbFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFiltrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jcbCampoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbTipoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(csFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbFiltrar)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbCampoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbTipoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbFiltrar))
                .addGap(0, 4, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setPreferredSize(new java.awt.Dimension(607, 35));

        jbSelecionar.setMnemonic('s');
        jbSelecionar.setText("Selecionar");
        jbSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSelecionarActionPerformed(evt);
            }
        });
        jPanel2.add(jbSelecionar);

        jbCancelar.setMnemonic('c');
        jbCancelar.setText("Cancelar");
        jbCancelar.setPreferredSize(new java.awt.Dimension(81, 23));
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(jbCancelar);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jxtDados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jxtDados.setColumnControlVisible(true);
        jxtDados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jxtDadosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jxtDados);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSelecionarActionPerformed
        selecionar();
    }//GEN-LAST:event_jbSelecionarActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void jbFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFiltrarActionPerformed
        filtrar();
    }//GEN-LAST:event_jbFiltrarActionPerformed

    private void jxtDadosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jxtDadosKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            selecionar();
        }
    }//GEN-LAST:event_jxtDadosKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.componentesbasicos.CampoString csFiltro;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbFiltrar;
    private javax.swing.JButton jbSelecionar;
    private javax.swing.JComboBox<String> jcbCampoFiltro;
    private javax.swing.JComboBox<String> jcbTipoFiltro;
    private org.jdesktop.swingx.JXTable jxtDados;
    // End of variables declaration//GEN-END:variables
}
