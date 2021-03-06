/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.cadastro.view;

import br.com.martinello.matriz.bd.control.RelacionamentoControl;
import br.com.martinello.matriz.bd.model.domain.Produto;
import br.com.martinello.matriz.componentesbasicos.paineis.JPStatus;
import br.com.martinello.matriz.componentesbasicos.view.TelaPadrao;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import com.towel.swing.table.ObjectTableModel;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class TelaRelacionaProduto extends TelaPadrao {

    /**
     * Creates new form TelaCategoria
     */
    private boolean resultado;
    private String salvarAlterar = "Salvar";
    private RelacionamentoControl relacionamentoControl;
    private Produto produto;
    private List<Produto> lProduto;

    private final ObjectTableModel<Produto> otmRelacinamento
            = new ObjectTableModel<>(Produto.class, "codigo,descricao,codigoAgr,idCategoria,categoria,idSubCategoria,subCategoria");

    public TelaRelacionaProduto() {
        initComponents();
        ppCadAgrPro.setVisible(false);
        paAgrupamento.setEnabledAt(1, false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpsAgrupamento = new br.com.martinello.matriz.componentesbasicos.paineis.JPStatus();
        paAgrupamento = new br.com.martinello.matriz.componentesbasicos.paineis.PainelAba();
        ppConsultaAgrPro = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        ppTabConsultaCaract = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        jspTabConsulta = new javax.swing.JScrollPane();
        tpConsulta = new br.com.martinello.matriz.componentesbasicos.TabelaPadrao();
        ppFiltroConsAgrPro = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        rCodAgrPro = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        btFiltroPesquisar = new br.com.martinello.matriz.componentesbasicos.Botao();
        btFiltroAlterar = new br.com.martinello.matriz.componentesbasicos.Botao();
        btFiltroIncluir = new br.com.martinello.matriz.componentesbasicos.Botao();
        btFiltroExcluir = new br.com.martinello.matriz.componentesbasicos.Botao();
        rCodProduto = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        csFiltroCodAgrPro = new br.com.martinello.matriz.componentesbasicos.CampoString();
        csFiltroCodPro = new br.com.martinello.matriz.componentesbasicos.CampoString();
        rProduto = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        cscFiltroProduto = new br.com.martinello.matriz.componentesbasicos.consulta.CampoStringConsulta();
        painelPadrao1 = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        rQtdReg = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rQtdRegValor = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        ppCadAgrPro = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        ppCadastroCaracteristica = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        rCodAgr = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        btCancelarCarCad = new br.com.martinello.matriz.componentesbasicos.Botao();
        btSalvarCarCad = new br.com.martinello.matriz.componentesbasicos.Botao();
        csCodigoAgr = new br.com.martinello.matriz.componentesbasicos.CampoString();
        rProdutoCad = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        ccpProduto = new br.com.martinello.cadastro.componentes.consulta.CampoConsultaProduto();
        rDescricaoPro = new br.com.martinello.matriz.componentesbasicos.Rotulo();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Agrupamento de Produto");
        setMaximumSize(new java.awt.Dimension(1600, 900));
        setPreferredSize(new java.awt.Dimension(1600, 900));
        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(jpsAgrupamento, java.awt.BorderLayout.SOUTH);

        paAgrupamento.setToolTipText("");

        ppConsultaAgrPro.setLayout(new java.awt.BorderLayout());

        ppTabConsultaCaract.setMinimumSize(new java.awt.Dimension(1029, 120));
        ppTabConsultaCaract.setPreferredSize(new java.awt.Dimension(1029, 420));
        ppTabConsultaCaract.setLayout(new java.awt.BorderLayout());

        tpConsulta.setModel(otmRelacinamento);
        tpConsulta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpConsultaKeyPressed(evt);
            }
        });
        jspTabConsulta.setViewportView(tpConsulta);

        ppTabConsultaCaract.add(jspTabConsulta, java.awt.BorderLayout.CENTER);

        ppFiltroConsAgrPro.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros da pesquisa"));
        ppFiltroConsAgrPro.setMinimumSize(new java.awt.Dimension(1029, 180));
        ppFiltroConsAgrPro.setPreferredSize(new java.awt.Dimension(1029, 110));

        rCodAgrPro.setText("C??d. Agrupamento:");

        btFiltroPesquisar.setText("Pesquisar");
        btFiltroPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFiltroPesquisarActionPerformed(evt);
            }
        });

        btFiltroAlterar.setText("Alterar");
        btFiltroAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFiltroAlterarActionPerformed(evt);
            }
        });

        btFiltroIncluir.setText("Incluir");
        btFiltroIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFiltroIncluirActionPerformed(evt);
            }
        });

        btFiltroExcluir.setText("Excluir");

        rCodProduto.setText("C??digo:");

        csFiltroCodAgrPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                csFiltroCodAgrProActionPerformed(evt);
            }
        });

        csFiltroCodPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                csFiltroCodProActionPerformed(evt);
            }
        });

        rProduto.setText("Produto:");

        javax.swing.GroupLayout ppFiltroConsAgrProLayout = new javax.swing.GroupLayout(ppFiltroConsAgrPro);
        ppFiltroConsAgrPro.setLayout(ppFiltroConsAgrProLayout);
        ppFiltroConsAgrProLayout.setHorizontalGroup(
            ppFiltroConsAgrProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroConsAgrProLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroConsAgrProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ppFiltroConsAgrProLayout.createSequentialGroup()
                        .addComponent(rCodAgrPro, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(csFiltroCodAgrPro, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(ppFiltroConsAgrProLayout.createSequentialGroup()
                        .addGroup(ppFiltroConsAgrProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(rCodProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroConsAgrProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppFiltroConsAgrProLayout.createSequentialGroup()
                                .addComponent(csFiltroCodPro, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btFiltroPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btFiltroAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btFiltroIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ppFiltroConsAgrProLayout.createSequentialGroup()
                                .addComponent(cscFiltroProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 617, Short.MAX_VALUE)
                                .addComponent(btFiltroExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18))
        );
        ppFiltroConsAgrProLayout.setVerticalGroup(
            ppFiltroConsAgrProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroConsAgrProLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroConsAgrProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ppFiltroConsAgrProLayout.createSequentialGroup()
                        .addGroup(ppFiltroConsAgrProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rCodAgrPro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(csFiltroCodAgrPro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rCodProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ppFiltroConsAgrProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(ppFiltroConsAgrProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btFiltroAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btFiltroIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btFiltroPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(csFiltroCodPro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroConsAgrProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btFiltroExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cscFiltroProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        ppTabConsultaCaract.add(ppFiltroConsAgrPro, java.awt.BorderLayout.PAGE_START);

        painelPadrao1.setPreferredSize(new java.awt.Dimension(1279, 30));

        rQtdReg.setText("Quantidade:");

        rQtdRegValor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout painelPadrao1Layout = new javax.swing.GroupLayout(painelPadrao1);
        painelPadrao1.setLayout(painelPadrao1Layout);
        painelPadrao1Layout.setHorizontalGroup(
            painelPadrao1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPadrao1Layout.createSequentialGroup()
                .addComponent(rQtdReg, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rQtdRegValor, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1363, Short.MAX_VALUE))
        );
        painelPadrao1Layout.setVerticalGroup(
            painelPadrao1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPadrao1Layout.createSequentialGroup()
                .addGroup(painelPadrao1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rQtdReg, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rQtdRegValor, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        ppTabConsultaCaract.add(painelPadrao1, java.awt.BorderLayout.PAGE_END);

        ppConsultaAgrPro.add(ppTabConsultaCaract, java.awt.BorderLayout.CENTER);

        paAgrupamento.addTab("Consulta", ppConsultaAgrPro);

        ppCadAgrPro.setMaximumSize(new java.awt.Dimension(1024, 500));
        ppCadAgrPro.setLayout(new java.awt.BorderLayout());

        ppCadastroCaracteristica.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros da pesquisa"));
        ppCadastroCaracteristica.setMinimumSize(new java.awt.Dimension(1029, 170));
        ppCadastroCaracteristica.setPreferredSize(new java.awt.Dimension(1300, 170));

        btCancelarCarCad.setText("Cancelar");
        btCancelarCarCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarCarCadActionPerformed(evt);
            }
        });

        btSalvarCarCad.setText("Salvar");
        btSalvarCarCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarCarCadActionPerformed(evt);
            }
        });

        csCodigoAgr.setComponenteRotulo(rCodAgr);
        csCodigoAgr.setDescricaoRotulo("C??d. Agrupamento");
        csCodigoAgr.setObrigatorio(true);

        ccpProduto.setComponenteRotulo(rProdutoCad);
        ccpProduto.setDescricaoRotulo("Produto");
        ccpProduto.setFiltroSituacao("Incluir");
        ccpProduto.setOrigem("relacionamento");
        ccpProduto.setrDescricaoProduto(rDescricaoPro);

        rDescricaoPro.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout ppCadastroCaracteristicaLayout = new javax.swing.GroupLayout(ppCadastroCaracteristica);
        ppCadastroCaracteristica.setLayout(ppCadastroCaracteristicaLayout);
        ppCadastroCaracteristicaLayout.setHorizontalGroup(
            ppCadastroCaracteristicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppCadastroCaracteristicaLayout.createSequentialGroup()
                .addContainerGap(856, Short.MAX_VALUE)
                .addComponent(btCancelarCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btSalvarCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(491, 491, 491))
            .addGroup(ppCadastroCaracteristicaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppCadastroCaracteristicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rProdutoCad, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                    .addComponent(rCodAgr, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppCadastroCaracteristicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppCadastroCaracteristicaLayout.createSequentialGroup()
                        .addComponent(ccpProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rDescricaoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(csCodigoAgr, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(889, Short.MAX_VALUE))
        );
        ppCadastroCaracteristicaLayout.setVerticalGroup(
            ppCadastroCaracteristicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppCadastroCaracteristicaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppCadastroCaracteristicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rProdutoCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ccpProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rDescricaoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(ppCadastroCaracteristicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rCodAgr, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csCodigoAgr, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 708, Short.MAX_VALUE)
                .addGroup(ppCadastroCaracteristicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCancelarCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSalvarCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        ppCadAgrPro.add(ppCadastroCaracteristica, java.awt.BorderLayout.CENTER);

        paAgrupamento.addTab("Cadastro / Altera????o", ppCadAgrPro);

        getContentPane().add(paAgrupamento, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btFiltroPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFiltroPesquisarActionPerformed
        buscarRelacionados("Consulta");
    }//GEN-LAST:event_btFiltroPesquisarActionPerformed

    private void btFiltroIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFiltroIncluirActionPerformed
        salvarAlterar = "Salvar";
        paAgrupamento.setSelectedComponent(ppCadAgrPro);
        ppCadAgrPro.setVisible(true);
        paAgrupamento.setEnabledAt(0, false);

    }//GEN-LAST:event_btFiltroIncluirActionPerformed

    private void tpConsultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpConsultaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tpConsultaKeyPressed

    private void btCancelarCarCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarCarCadActionPerformed
        limparCadastrar();
    }//GEN-LAST:event_btCancelarCarCadActionPerformed

    private void btSalvarCarCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarCarCadActionPerformed
        salvarAgrPro();

    }//GEN-LAST:event_btSalvarCarCadActionPerformed

    private void btFiltroAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFiltroAlterarActionPerformed
        salvarAlterar = "Alterar";
        //   alterarSubCategoria();
        if (resultado = true) {
            paAgrupamento.setSelectedComponent(ppCadAgrPro);
            ppCadAgrPro.setVisible(true);
        } else {

        }

    }//GEN-LAST:event_btFiltroAlterarActionPerformed

    private void csFiltroCodAgrProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csFiltroCodAgrProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_csFiltroCodAgrProActionPerformed

    private void csFiltroCodProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csFiltroCodProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_csFiltroCodProActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.matriz.componentesbasicos.Botao btCancelarCarCad;
    private br.com.martinello.matriz.componentesbasicos.Botao btFiltroAlterar;
    private br.com.martinello.matriz.componentesbasicos.Botao btFiltroExcluir;
    private br.com.martinello.matriz.componentesbasicos.Botao btFiltroIncluir;
    private br.com.martinello.matriz.componentesbasicos.Botao btFiltroPesquisar;
    private br.com.martinello.matriz.componentesbasicos.Botao btSalvarCarCad;
    private br.com.martinello.cadastro.componentes.consulta.CampoConsultaProduto ccpProduto;
    private br.com.martinello.matriz.componentesbasicos.CampoString csCodigoAgr;
    private br.com.martinello.matriz.componentesbasicos.CampoString csFiltroCodAgrPro;
    private br.com.martinello.matriz.componentesbasicos.CampoString csFiltroCodPro;
    private br.com.martinello.matriz.componentesbasicos.consulta.CampoStringConsulta cscFiltroProduto;
    private br.com.martinello.matriz.componentesbasicos.paineis.JPStatus jpsAgrupamento;
    private javax.swing.JScrollPane jspTabConsulta;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelAba paAgrupamento;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao painelPadrao1;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppCadAgrPro;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppCadastroCaracteristica;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppConsultaAgrPro;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppFiltroConsAgrPro;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppTabConsultaCaract;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rCodAgr;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rCodAgrPro;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rCodProduto;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rDescricaoPro;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rProduto;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rProdutoCad;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rQtdReg;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rQtdRegValor;
    private br.com.martinello.matriz.componentesbasicos.TabelaPadrao tpConsulta;
    // End of variables declaration//GEN-END:variables

    private void buscarRelacionados(String origem) {
        try {
            relacionamentoControl = new RelacionamentoControl();
            produto = new Produto();
            lProduto = new LinkedList<>();
            produto.setCodigo(csFiltroCodPro.getString());
            produto.setCodigoAgr(csFiltroCodAgrPro.getString());
            if (cscFiltroProduto.getFiltroOld() != null) {
                produto.setDescricao(cscFiltroProduto.getFiltroOld().toString().toUpperCase());
            }
            lProduto = relacionamentoControl.Buscar(produto);
            otmRelacinamento.setData(lProduto);
            otmRelacinamento.fireTableDataChanged();
            if (origem.equalsIgnoreCase("Inclusao")) {
            } else {
                jpsAgrupamento.setStatus("Pesquisa realizada com sucesso.", JPStatus.NORMAL);
            }
            if (tpConsulta.getRowCount() > 0) {
                tpConsulta.packAll();
                tpConsulta.addRowSelectionInterval(tpConsulta.convertRowIndexToView(0), tpConsulta.convertRowIndexToView(0));
                tpConsulta.grabFocus();
                rQtdRegValor.setText(Integer.toString(lProduto.size()));
            }
        } catch (ErroSistemaException ex) {
            jpsAgrupamento.setStatus(ex.getMessage(), JPStatus.ERRO);
        }
    }

    private boolean salvarAgrPro() {

        try {
            resultado = false;
            produto = new Produto();
            produto = ccpProduto.getProduto();
            if (produto == null) {
                jpsAgrupamento.setStatus("Nenhum produto selecionado! Por favor selecione um produto.", JPStatus.ERRO);
                return resultado;
            }
            if (csCodigoAgr.getString().length() < 5) {
                jpsAgrupamento.setStatus("Nenhum produto selecionado! Por favor selecione um produto.", JPStatus.ERRO);
                return resultado;
            }
            produto.setIdUsuario(TelaPrincipal.usuario.getIdUsuario());
            produto.setCodigoAgr(csCodigoAgr.getString());
            relacionamentoControl = new RelacionamentoControl();
            resultado = relacionamentoControl.agrupar(produto);
            limparCadastrar();
            jpsAgrupamento.setStatus("Produto c??digo:" + produto.getCodigo() + " agrupado com sucesso!", JPStatus.NORMAL);
            return resultado;
        } catch (ErroSistemaException ex) {
            jpsAgrupamento.setStatus(ex.getMessage(), JPStatus.ERRO);
            return resultado;
        }
    }

    private void limparCadastrar() {
        paAgrupamento.setSelectedComponent(ppConsultaAgrPro);
        paAgrupamento.setEnabledAt(1, false);
        paAgrupamento.setEnabledAt(0, true);
        buscarRelacionados("Inclusao");
    }
}
