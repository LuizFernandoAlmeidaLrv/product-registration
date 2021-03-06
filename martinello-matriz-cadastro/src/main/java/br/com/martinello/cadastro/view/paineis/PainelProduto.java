/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.cadastro.view.paineis;

import br.com.martinello.matriz.bd.control.ProdutoControl;
import br.com.martinello.matriz.bd.control.RelacionamentoControl;
import br.com.martinello.matriz.bd.model.domain.Produto;
import br.com.martinello.matriz.componentesbasicos.CampoString;
import br.com.martinello.matriz.componentesbasicos.ConstantesGlobais;
import br.com.martinello.matriz.componentesbasicos.TabelaPadrao;
import br.com.martinello.matriz.componentesbasicos.consulta.CampoStringConsulta;
import br.com.martinello.matriz.componentesbasicos.paineis.JPStatus;
import br.com.martinello.matriz.componentesbasicos.paineis.Painel;
import br.com.martinello.matriz.componentesbasicos.paineis.TelaProcessamento;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import br.com.martinello.matriz.util.filtro.Filtro;
import com.towel.swing.table.ObjectTableModel;
import java.util.LinkedList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author Sidnei
 */
public class PainelProduto extends Painel {

    protected ProdutoControl produtoControl;
    protected JPStatus jpsStatus;
    protected ObjectTableModel<Produto> otmProdutos = new ObjectTableModel(Produto.class, "codigo,descricao,marca,bloqueado");
    protected boolean permiteAlterarSituacao = true, permiteAlterarTipoEstoque = true;
    private String origem;

    /**
     * Creates new form PainelProduto
     */
    public PainelProduto() {

    }

    public PainelProduto(List<Produto> lProdutosConsulta) {
        initComponents();
        tpProdutos.setModel(otmProdutos);
        tpProdutos.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }
            if (tpProdutos.getSelectedRow() >= 0) {
            }
        });

        setFont(ConstantesGlobais.FONTE_11_NORMAL);
        //csFiltroMarca.setJlDescricao(rDescricaoMarca);
        SwingUtilities.invokeLater(() -> {
            csFiltroDescricao.grabFocus();
        });

        if (lProdutosConsulta != null) {
            otmProdutos.setData(lProdutosConsulta);
            otmProdutos.fireTableDataChanged();
            tpProdutos.packAll();
        }
        //produtoControl.setLoja(TelaPrincipal.getLoja());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgBloqueado = new javax.swing.ButtonGroup();
        bgTipoEstoque = new javax.swing.ButtonGroup();
        jpConsultaProdutos = new javax.swing.JPanel();
        jpFiltros = new javax.swing.JPanel();
        rFiltroCodigo = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rFiltroDescricao = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rFiltroBloqueado = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        jrbIncluir = new javax.swing.JRadioButton();
        jrbConsulta = new javax.swing.JRadioButton();
        csFiltroCodigo = new br.com.martinello.matriz.componentesbasicos.CampoString();
        bPesquisar = new br.com.martinello.matriz.componentesbasicos.Botao();
        csFiltroDescricao = new br.com.martinello.matriz.componentesbasicos.consulta.CampoStringConsulta();
        jpDadosProdutos = new javax.swing.JPanel();
        csDescricao = new br.com.martinello.matriz.componentesbasicos.CampoString();
        rDescricao = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        jtpProdutos = new javax.swing.JTabbedPane();
        pDadosProdutos = new br.com.martinello.matriz.componentesbasicos.paineis.Painel();
        jspProdutos = new javax.swing.JScrollPane();
        tpProdutos = new br.com.martinello.matriz.componentesbasicos.TabelaPadrao();

        setLayout(new java.awt.BorderLayout());

        jpConsultaProdutos.setLayout(new java.awt.BorderLayout());

        jpFiltros.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros da Pesquisa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, ConstantesGlobais.FONTE_BORDA_JPANEL));
        jpFiltros.setFont(ConstantesGlobais.FONTE_11_NORMAL);
        jpFiltros.setPreferredSize(new java.awt.Dimension(1015, 114));

        rFiltroCodigo.setText("C??digo:");

        rFiltroDescricao.setText("Descri????o:");

        rFiltroBloqueado.setText("Origem:");

        bgBloqueado.add(jrbIncluir);
        jrbIncluir.setFont(ConstantesGlobais.FONTE_11_NORMAL);
        jrbIncluir.setText("Incluir");
        jrbIncluir.setActionCommand("jrbSim");

        bgBloqueado.add(jrbConsulta);
        jrbConsulta.setFont(ConstantesGlobais.FONTE_11_NORMAL);
        jrbConsulta.setSelected(true);
        jrbConsulta.setText("Consulta");
        jrbConsulta.setActionCommand("jrbN??o");

        bPesquisar.setMnemonic('P');
        bPesquisar.setText("Pesquisar");
        bPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpFiltrosLayout = new javax.swing.GroupLayout(jpFiltros);
        jpFiltros.setLayout(jpFiltrosLayout);
        jpFiltrosLayout.setHorizontalGroup(
            jpFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpFiltrosLayout.createSequentialGroup()
                .addGroup(jpFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rFiltroCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rFiltroBloqueado, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(csFiltroDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpFiltrosLayout.createSequentialGroup()
                        .addComponent(csFiltroCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jpFiltrosLayout.createSequentialGroup()
                        .addComponent(jrbConsulta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jrbIncluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 668, Short.MAX_VALUE)
                        .addComponent(bPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpFiltrosLayout.setVerticalGroup(
            jpFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpFiltrosLayout.createSequentialGroup()
                .addGroup(jpFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(rFiltroCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csFiltroCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(rFiltroDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csFiltroDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(rFiltroBloqueado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jrbConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jrbIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        jpConsultaProdutos.add(jpFiltros, java.awt.BorderLayout.NORTH);

        jpDadosProdutos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, ConstantesGlobais.FONTE_BORDA_JPANEL));
        jpDadosProdutos.setFont(ConstantesGlobais.FONTE_11_NORMAL);
        jpDadosProdutos.setPreferredSize(new java.awt.Dimension(1015, 417));

        csDescricao.setEditable(false);

        rDescricao.setText("Descri????o:");

        tpProdutos.setModel(otmProdutos);
        tpProdutos.getTableHeader().setReorderingAllowed(false);
        jspProdutos.setViewportView(tpProdutos);

        javax.swing.GroupLayout pDadosProdutosLayout = new javax.swing.GroupLayout(pDadosProdutos);
        pDadosProdutos.setLayout(pDadosProdutosLayout);
        pDadosProdutosLayout.setHorizontalGroup(
            pDadosProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDadosProdutosLayout.createSequentialGroup()
                .addComponent(jspProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 268, Short.MAX_VALUE))
        );
        pDadosProdutosLayout.setVerticalGroup(
            pDadosProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDadosProdutosLayout.createSequentialGroup()
                .addComponent(jspProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 45, Short.MAX_VALUE))
        );

        jtpProdutos.addTab("Dados Consulta", pDadosProdutos);

        javax.swing.GroupLayout jpDadosProdutosLayout = new javax.swing.GroupLayout(jpDadosProdutos);
        jpDadosProdutos.setLayout(jpDadosProdutosLayout);
        jpDadosProdutosLayout.setHorizontalGroup(
            jpDadosProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosProdutosLayout.createSequentialGroup()
                .addComponent(rDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(csDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jtpProdutos)
        );
        jpDadosProdutosLayout.setVerticalGroup(
            jpDadosProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosProdutosLayout.createSequentialGroup()
                .addGroup(jpDadosProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(rDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtpProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jpConsultaProdutos.add(jpDadosProdutos, java.awt.BorderLayout.CENTER);

        add(jpConsultaProdutos, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void bPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPesquisarActionPerformed
        if (csFiltroCodigo.getString().equals("") && csFiltroDescricao.getString().equals("")) {
            jpsStatus.setStatus("Obrigat??rio a informa????o de pelo menos um filtro para pesquisa.", JPStatus.ERRO);
            return;
        }
        List<Filtro> lFiltros = new LinkedList<>();
        lFiltros.add(new Filtro("codigo", Filtro.STRING, Filtro.IGUAL, csFiltroCodigo.getString().length() == 0 ? null : csFiltroCodigo.getString()));
        lFiltros.add(csFiltroDescricao.getFiltro("descricao"));
        final TelaProcessamento telaProcessamento = new TelaProcessamento("Realizando consulta...");

        new Thread() {
            @Override
            public void run() {
                try {
                    RelacionamentoControl relacionamentoControl = new RelacionamentoControl();
                    produtoControl = new ProdutoControl();
                    if (origem.equalsIgnoreCase("relacionamento")) {
                        if (jrbIncluir.isSelected()) {
                            otmProdutos.setData(relacionamentoControl.listarProduto(lFiltros));
                        } else if (jrbConsulta.isSelected()) {
                          
                        }
                    } else {
                        if (jrbIncluir.isSelected()) {
                            otmProdutos.setData(produtoControl.pesquisar(lFiltros));
                        } else if (jrbConsulta.isSelected()) {
                            otmProdutos.setData(produtoControl.listarProdutos(lFiltros));
                        }
                    }
                    if (tpProdutos.getRowCount() > 0) {
                        SwingUtilities.invokeLater(() -> {
                            tpProdutos.packAll();
                            tpProdutos.addRowSelectionInterval(tpProdutos.convertRowIndexToView(0), tpProdutos.convertRowIndexToView(0));
                            tpProdutos.grabFocus();
                        });
                    }
                    jpsStatus.setStatus("Pesquisa realizada com sucesso.", JPStatus.NORMAL);
                } catch (ErroSistemaException ex) {
                    jpsStatus.setStatus("Erro ao consultar produtos", JPStatus.ERRO, ex);
                } finally {
                    SwingUtilities.invokeLater(() -> {
                        telaProcessamento.dispose();
                    });
                }
            }
        }.start();

        telaProcessamento.setVisible(true);
        telaProcessamento.requestFocusInWindow();
    }//GEN-LAST:event_bPesquisarActionPerformed

    private void clTipoNegociacaoCPMItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_clTipoNegociacaoCPMItemStateChanged

    }//GEN-LAST:event_clTipoNegociacaoCPMItemStateChanged

    private void clNivelNegociacaoCPMFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clNivelNegociacaoCPMFocusLost

    }//GEN-LAST:event_clNivelNegociacaoCPMFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.matriz.componentesbasicos.Botao bPesquisar;
    private javax.swing.ButtonGroup bgBloqueado;
    private javax.swing.ButtonGroup bgTipoEstoque;
    private br.com.martinello.matriz.componentesbasicos.CampoString csDescricao;
    private br.com.martinello.matriz.componentesbasicos.CampoString csFiltroCodigo;
    private br.com.martinello.matriz.componentesbasicos.consulta.CampoStringConsulta csFiltroDescricao;
    private javax.swing.JPanel jpConsultaProdutos;
    private javax.swing.JPanel jpDadosProdutos;
    private javax.swing.JPanel jpFiltros;
    private javax.swing.JRadioButton jrbConsulta;
    private javax.swing.JRadioButton jrbIncluir;
    private javax.swing.JScrollPane jspProdutos;
    private javax.swing.JTabbedPane jtpProdutos;
    private br.com.martinello.matriz.componentesbasicos.paineis.Painel pDadosProdutos;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rDescricao;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rFiltroBloqueado;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rFiltroCodigo;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rFiltroDescricao;
    private br.com.martinello.matriz.componentesbasicos.TabelaPadrao tpProdutos;
    // End of variables declaration//GEN-END:variables

    public JPStatus getJpsStatus() {
        return jpsStatus;
    }

    public void setJpsStatus(JPStatus jpsStatus) {
        this.jpsStatus = jpsStatus;
    }

    public Produto getProdutoSelecionado() {
        return tpProdutos.getLinhaSelecionada() >= 0 ? otmProdutos.getValue(tpProdutos.getLinhaSelecionada()) : null;
    }

    public CampoString getCsFiltroCodigo() {
        return csFiltroCodigo;
    }

    public void setCsFiltroCodigo(CampoString csFiltroCodigo) {
        this.csFiltroCodigo = csFiltroCodigo;
    }

    public CampoStringConsulta getCsFiltroDescricao() {
        return csFiltroDescricao;
    }

    public void setCsFiltroDescricao(CampoStringConsulta csFiltroDescricao) {
        this.csFiltroDescricao = csFiltroDescricao;
    }

    public TabelaPadrao getTpProdutos() {
        return tpProdutos;
    }

    public void setTpProdutos(TabelaPadrao tpProdutos) {
        this.tpProdutos = tpProdutos;
    }

    public void setaConsuta() {
        jrbConsulta.setSelected(true);
    }

    public void setaIncluir() {
        jrbIncluir.setSelected(true);
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public void setaFocoTabelaConsulta() {
        java.awt.EventQueue.invokeLater(() -> {
            tpProdutos.grabFocus();
            if (tpProdutos.getRowCount() > 0) {
                tpProdutos.addRowSelectionInterval(0, 0);
            }
        });
    }

    public boolean isPermiteAlterarSituacao() {
        return permiteAlterarSituacao;
    }

    public void setPermiteAlterarSituacao(boolean permiteAlterarSituacao) {
        this.permiteAlterarSituacao = permiteAlterarSituacao;

        jrbConsulta.setEnabled(permiteAlterarSituacao);
        jrbIncluir.setEnabled(permiteAlterarSituacao);

    }

    public boolean isPermiteAlterarTipoEstoque() {
        return permiteAlterarTipoEstoque;
    }

}
