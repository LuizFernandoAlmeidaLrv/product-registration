/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.componentesbasicos.paineis;

/**
 *
 * @author sidnei.vieira
 */
public class PainelRelatorio extends javax.swing.JPanel {

    /**
     * Creates new form PainelRelatorio
     */
    public PainelRelatorio() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpsStatusBar = new br.com.martinello.matriz.componentesbasicos.paineis.JPStatus();
        pRelatorio = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        pBotoes = new br.com.martinello.matriz.componentesbasicos.paineis.Painel();
        bConfirmar = new br.com.martinello.matriz.componentesbasicos.Botao();
        bCancelar = new br.com.martinello.matriz.componentesbasicos.Botao();
        pFiltros = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        pImpressora = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        clsImpressoraResumo = new br.com.martinello.matriz.componentesbasicos.CampoListaSimples();

        setLayout(new java.awt.BorderLayout());
        add(jpsStatusBar, java.awt.BorderLayout.PAGE_END);

        pRelatorio.setLayout(new java.awt.BorderLayout());

        pBotoes.setPreferredSize(new java.awt.Dimension(979, 35));

        bConfirmar.setMnemonic('s');
        bConfirmar.setText("Confirmar");
        bConfirmar.setPreferredSize(new java.awt.Dimension(95, 23));
        bConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bConfirmarActionPerformed(evt);
            }
        });
        pBotoes.add(bConfirmar);

        bCancelar.setMnemonic('c');
        bCancelar.setText("Cancelar");
        bCancelar.setPreferredSize(new java.awt.Dimension(95, 23));
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });
        pBotoes.add(bCancelar);

        pRelatorio.add(pBotoes, java.awt.BorderLayout.SOUTH);

        pFiltros.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));

        javax.swing.GroupLayout pFiltrosLayout = new javax.swing.GroupLayout(pFiltros);
        pFiltros.setLayout(pFiltrosLayout);
        pFiltrosLayout.setHorizontalGroup(
            pFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 473, Short.MAX_VALUE)
        );
        pFiltrosLayout.setVerticalGroup(
            pFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 101, Short.MAX_VALUE)
        );

        pRelatorio.add(pFiltros, java.awt.BorderLayout.CENTER);

        pImpressora.setBorder(javax.swing.BorderFactory.createTitledBorder("Impressora"));
        pImpressora.setPreferredSize(new java.awt.Dimension(699, 45));

        clsImpressoraResumo.setDescricaoRotulo("Aval. End.");

        javax.swing.GroupLayout pImpressoraLayout = new javax.swing.GroupLayout(pImpressora);
        pImpressora.setLayout(pImpressoraLayout);
        pImpressoraLayout.setHorizontalGroup(
            pImpressoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(clsImpressoraResumo, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
        );
        pImpressoraLayout.setVerticalGroup(
            pImpressoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pImpressoraLayout.createSequentialGroup()
                .addComponent(clsImpressoraResumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        pRelatorio.add(pImpressora, java.awt.BorderLayout.NORTH);

        add(pRelatorio, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void bConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bConfirmarActionPerformed
        try {

        } catch (Exception ex) {
            jpsStatusBar.setStatus("Erro ao realizar processamento." + ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }
    }//GEN-LAST:event_bConfirmarActionPerformed

    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed

    }//GEN-LAST:event_bCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.matriz.componentesbasicos.Botao bCancelar;
    private br.com.martinello.matriz.componentesbasicos.Botao bConfirmar;
    private br.com.martinello.matriz.componentesbasicos.CampoListaSimples clsImpressoraResumo;
    private br.com.martinello.matriz.componentesbasicos.paineis.JPStatus jpsStatusBar;
    private br.com.martinello.matriz.componentesbasicos.paineis.Painel pBotoes;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao pFiltros;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao pImpressora;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao pRelatorio;
    // End of variables declaration//GEN-END:variables
}
