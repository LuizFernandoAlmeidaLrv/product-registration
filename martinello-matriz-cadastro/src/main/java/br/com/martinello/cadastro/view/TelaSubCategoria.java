/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.cadastro.view;

import br.com.martinello.matriz.bd.control.CaracteristicaControl;
import br.com.martinello.matriz.bd.control.CaracteristicaItemControl;
import br.com.martinello.matriz.bd.control.CategoriaControl;
import br.com.martinello.matriz.bd.control.SubCategoriaControl;
import br.com.martinello.matriz.bd.model.domain.Caracteristica;
import br.com.martinello.matriz.bd.model.domain.CaracteristicaItem;
import br.com.martinello.matriz.bd.model.domain.Categoria;
import br.com.martinello.matriz.bd.model.domain.SubCategoria;
import br.com.martinello.matriz.bd.model.domain.Usuario;
import br.com.martinello.matriz.componentesbasicos.paineis.JPStatus;
import br.com.martinello.matriz.componentesbasicos.view.TelaPadrao;
import br.com.martinello.matriz.util.FixedLengthDocument;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import com.towel.swing.table.ObjectTableModel;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author luiz.almeida
 */
public class TelaSubCategoria extends TelaPadrao {

    /**
     * Creates new form TelaCategoria
     */
    private String cat;
    private String codigo;
    private String salvarAlterar = "Salvar";
    private SubCategoria filtroSubCategoria;
    private SubCategoria subCategoria;
    private Categoria categoria;
    private Caracteristica caracteristica;
    private CaracteristicaItem caracteristicaItem, caracteristicaItemAdd;
    private CaracteristicaControl caracteristicaControl;
    private CaracteristicaItemControl caracteristicaItemControl;
    private CategoriaControl categoriaControl;
    private SubCategoriaControl subCategoriaControl;
    private List<Caracteristica> lCaracteristicas;
    private List<CaracteristicaItem> lCaracteristicasItens;
    private List<SubCategoria> lSubCategoria;
    private boolean resultado;
    private static String catSelect;
    private Caracteristica caracteristicaAdd;
    private static List<Categoria> lCategoria;
    private static Usuario usuario;
    private ImageIcon iconDescer, iconSubir;
    private boolean row_Is_Selected = false, row_Item_Is_Selected = false;
    private int index;
    private ObjectTableModel model, modelItem;

    private final ObjectTableModel<SubCategoria> otmSubCategoria = new ObjectTableModel<>(SubCategoria.class, "idSubCategoria,categoria,subCategoria,situacao,obsSubCategoria,idUsuario,nomeUsuario,dataCadastro");

    private final ObjectTableModel<Caracteristica> otmCaracteristica
            = new ObjectTableModel<>(Caracteristica.class, "idSubCategoria,idCaracteristica,subCategoria,caracteristica,seqCaracteristica,situacao,visivel,obsCaracteristica,dataCadastro");

    private final ObjectTableModel<CaracteristicaItem> otmCaracteristicaItem
            = new ObjectTableModel<>(CaracteristicaItem.class, "subCategoria,caracteristica,idCaracteristicaItem,caracteristicaItem,seqCatIte,situacao,visivel,replicavel,observacao,dataCadastro");

    private final ObjectTableModel<Caracteristica> otmCaracteristicaAdd
            = new ObjectTableModel<>(Caracteristica.class, "idSubCategoria,idCaracteristica,subCategoria,caracteristica,seqCaracteristica,unico,situacao,visivel,obsCaracteristica");

    private final ObjectTableModel<CaracteristicaItem> otmCaracteristicaItemAdd
            = new ObjectTableModel<>(CaracteristicaItem.class, "idCaracteristicaItem,subCategoria,caracteristica,caracteristicaItem,seqCatIte,situacao,visivel,replicavel,observacao");

    public TelaSubCategoria() {
        initComponents();
        carregarCategorias();
        ppCadastroSubCat.setVisible(false);
        paSubCategoria.setEnabledAt(1, false);
        paSubCategoria.setEnabledAt(2, false);
        usuario = TelaPrincipal.usuario;

        iconSubir = new ImageIcon(System.getProperty("user.dir") + "/SetaSubir.png");
        iconDescer = new ImageIcon(System.getProperty("user.dir") + "/SetaDescer.png");
        bSubirCaracteristica.setIcon(iconSubir);
        bDescerCaracteristica.setIcon(iconDescer);
        bDescerCaracteristicaItem.setIcon(iconDescer);
        bSubirCaracteristicaItem.setIcon(iconSubir);

        tpConsultaSubCat.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }
            if (tpConsultaSubCat.getSelectedRow() >= 0) {
                setDadosCaracteristicas(otmSubCategoria.getValue(tpConsultaSubCat.getLinhaSelecionada()));
            }
        });
        tpCadCar.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }
            if (tpCadCar.getSelectedRow() >= 0) {
                //setDadosCaracteristicasItens(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()));
                setDadosCaracteristicasItens();
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgUnico = new javax.swing.ButtonGroup();
        jpsSubCategoria = new br.com.martinello.matriz.componentesbasicos.paineis.JPStatus();
        paSubCategoria = new br.com.martinello.matriz.componentesbasicos.paineis.PainelAba();
        ppConsultaSubCat = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        paCaracteristica = new br.com.martinello.matriz.componentesbasicos.paineis.PainelAba();
        ppSubTabCaracteristicas = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        jScrollPane1 = new javax.swing.JScrollPane();
        tpSubTabCaracteristicas = new br.com.martinello.matriz.componentesbasicos.TabelaPadrao();
        rMargeConsultaCar = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        ppSubCarItens = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        jScrollPane2 = new javax.swing.JScrollPane();
        tpSubCarItens = new br.com.martinello.matriz.componentesbasicos.TabelaPadrao();
        ppFiltroConsultaSubCat = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        rIdSubCatFiltro = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rSubCatFiltro = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        ciIdSubCatFiltro = new br.com.martinello.matriz.componentesbasicos.CampoInteiro();
        cscSubCatFiltro = new br.com.martinello.matriz.componentesbasicos.consulta.CampoStringConsulta();
        rStatusSubCatFiltro = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        jrbAtivoSubCatFiltro = new javax.swing.JRadioButton();
        jrbInativoSubCatFiltro = new javax.swing.JRadioButton();
        btPesquisarSubCat = new br.com.martinello.matriz.componentesbasicos.Botao();
        btAlterarSubCat = new br.com.martinello.matriz.componentesbasicos.Botao();
        btIncluirSubCat = new br.com.martinello.matriz.componentesbasicos.Botao();
        btExcluirSubCat = new br.com.martinello.matriz.componentesbasicos.Botao();
        rCatFiltro = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        clsCatFiltro = new br.com.martinello.matriz.componentesbasicos.CampoListaSimples();
        btRelatorio = new br.com.martinello.matriz.componentesbasicos.Botao();
        ppTabConsultaSubCat = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        jspTabConsultaSubCat = new javax.swing.JScrollPane();
        tpConsultaSubCat = new br.com.martinello.matriz.componentesbasicos.TabelaPadrao();
        rMargemConsultaSub = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        ppCadastroSubCat = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        ppCadastroSubCat1 = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        rIdSubCatCadastro = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rSubCatCadastro = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        ciIdSubCatCad = new br.com.martinello.matriz.componentesbasicos.CampoInteiro();
        rStatusSubCatCad = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        btCancelarSubCatCad = new br.com.martinello.matriz.componentesbasicos.Botao();
        btSalvarSubCatCad = new br.com.martinello.matriz.componentesbasicos.Botao();
        rObsSubCatCad = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        jspDescSubCatCad = new javax.swing.JScrollPane();
        jtaObservacao = new javax.swing.JTextArea();
        csSubCatCad = new br.com.martinello.matriz.componentesbasicos.CampoString();
        clsSituacaoSubCatCad = new br.com.martinello.matriz.componentesbasicos.CampoListaSimples();
        clsCatCad = new br.com.martinello.matriz.componentesbasicos.CampoListaSimples();
        rCatCad = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        ppCadCaracteristica = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        painelPadrao3 = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        btSalvarCarCad2 = new br.com.martinello.matriz.componentesbasicos.Botao();
        btCancelarCarCad2 = new br.com.martinello.matriz.componentesbasicos.Botao();
        ppPrincCaractItem = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        ppCadastroCaracteristicaItem = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        rObsCarCadItem = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rCarItemCadastroItem = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        csCarItemCadItem = new br.com.martinello.matriz.componentesbasicos.CampoString();
        clsVisivelCarCadItem = new br.com.martinello.matriz.componentesbasicos.CampoListaSimples();
        rVisivelCadItem = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rStatusCarCadItem = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        clsSituacaoCarCadItem = new br.com.martinello.matriz.componentesbasicos.CampoListaSimples();
        csObsCarItemCad = new br.com.martinello.matriz.componentesbasicos.CampoString();
        clsReplicavelCarCadItem = new br.com.martinello.matriz.componentesbasicos.CampoListaSimples();
        rReplicavelCadItem = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        ppTabCadCatItem = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        jScrollPane3 = new javax.swing.JScrollPane();
        tpCadCarItem = new br.com.martinello.matriz.componentesbasicos.TabelaPadrao();
        ppAddCarItem = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        btAddCaracteristicaItem = new br.com.martinello.matriz.componentesbasicos.Botao();
        btCancelarCaracteristicaItem = new br.com.martinello.matriz.componentesbasicos.Botao();
        btAlterarCaracteristicaItem = new br.com.martinello.matriz.componentesbasicos.Botao();
        bDescerCaracteristicaItem = new br.com.martinello.matriz.componentesbasicos.Botao();
        bSubirCaracteristicaItem = new br.com.martinello.matriz.componentesbasicos.Botao();
        rMargemItem = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        ppPrincCaract = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        ppCadastroCaracteristica = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        rCarCadastro = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rStatusCarCad = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rObsCarCad = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        csCarCad = new br.com.martinello.matriz.componentesbasicos.CampoString();
        clsSituacaoCarCad = new br.com.martinello.matriz.componentesbasicos.CampoListaSimples();
        rCatCarCad = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rSubCategoriaCad = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rVisivelCad = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        clsVisivelCarCad = new br.com.martinello.matriz.componentesbasicos.CampoListaSimples();
        csObsCarCad = new br.com.martinello.matriz.componentesbasicos.CampoString();
        csSubCatCarCad = new br.com.martinello.matriz.componentesbasicos.CampoString();
        csCatCad = new br.com.martinello.matriz.componentesbasicos.CampoString();
        jrbUnicoSim = new javax.swing.JRadioButton();
        rotulo1 = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        jrbUnicoNao = new javax.swing.JRadioButton();
        painelPadrao1 = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        jScrollPane4 = new javax.swing.JScrollPane();
        tpCadCar = new br.com.martinello.matriz.componentesbasicos.TabelaPadrao();
        painelPadrao5 = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        btCancelarCaracteristica = new br.com.martinello.matriz.componentesbasicos.Botao();
        btAlterarCaracteristica = new br.com.martinello.matriz.componentesbasicos.Botao();
        btAddCaracteristica = new br.com.martinello.matriz.componentesbasicos.Botao();
        bDescerCaracteristica = new br.com.martinello.matriz.componentesbasicos.Botao();
        bSubirCaracteristica = new br.com.martinello.matriz.componentesbasicos.Botao();
        rMargemCararcterística = new br.com.martinello.matriz.componentesbasicos.Rotulo();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cadastro Itens Categoria");
        setMaximumSize(new java.awt.Dimension(1600, 900));
        setPreferredSize(new java.awt.Dimension(1600, 900));
        getContentPane().add(jpsSubCategoria, java.awt.BorderLayout.SOUTH);

        paSubCategoria.setToolTipText("");

        ppConsultaSubCat.setLayout(new java.awt.BorderLayout());

        paCaracteristica.setPreferredSize(new java.awt.Dimension(605, 330));

        ppSubTabCaracteristicas.setPreferredSize(new java.awt.Dimension(600, 320));
        ppSubTabCaracteristicas.setLayout(new java.awt.BorderLayout());

        tpSubTabCaracteristicas.setModel(otmCaracteristica);
        tpSubTabCaracteristicas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tpSubTabCaracteristicasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tpSubTabCaracteristicas);

        ppSubTabCaracteristicas.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        rMargeConsultaCar.setPreferredSize(new java.awt.Dimension(5, 2));
        ppSubTabCaracteristicas.add(rMargeConsultaCar, java.awt.BorderLayout.WEST);

        paCaracteristica.addTab("Caracteristicas", ppSubTabCaracteristicas);

        ppSubCarItens.setPreferredSize(new java.awt.Dimension(600, 360));
        ppSubCarItens.setLayout(new java.awt.BorderLayout());

        tpSubCarItens.setModel(otmCaracteristicaItem);
        jScrollPane2.setViewportView(tpSubCarItens);

        ppSubCarItens.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        paCaracteristica.addTab("Caracteristicas Itens", ppSubCarItens);

        ppConsultaSubCat.add(paCaracteristica, java.awt.BorderLayout.SOUTH);

        ppFiltroConsultaSubCat.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros da pesquisa"));
        ppFiltroConsultaSubCat.setMinimumSize(new java.awt.Dimension(1029, 140));
        ppFiltroConsultaSubCat.setPreferredSize(new java.awt.Dimension(1029, 140));

        rIdSubCatFiltro.setText("Código:");

        rSubCatFiltro.setText("SubCategoria:");

        ciIdSubCatFiltro.setText("0");

        rStatusSubCatFiltro.setText("Status:");

        jrbAtivoSubCatFiltro.setText("Ativo");

        jrbInativoSubCatFiltro.setText("Inativo");

        btPesquisarSubCat.setText("Pesquisar");
        btPesquisarSubCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarSubCatActionPerformed(evt);
            }
        });

        btAlterarSubCat.setText("Alterar");
        btAlterarSubCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarSubCatActionPerformed(evt);
            }
        });

        btIncluirSubCat.setText("Incluir");
        btIncluirSubCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIncluirSubCatActionPerformed(evt);
            }
        });

        btExcluirSubCat.setText("Excluir");
        btExcluirSubCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirSubCatActionPerformed(evt);
            }
        });

        rCatFiltro.setText("Categoria:");

        clsCatFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clsCatFiltroActionPerformed(evt);
            }
        });

        btRelatorio.setText("Imprimir");
        btRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRelatorioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppFiltroConsultaSubCatLayout = new javax.swing.GroupLayout(ppFiltroConsultaSubCat);
        ppFiltroConsultaSubCat.setLayout(ppFiltroConsultaSubCatLayout);
        ppFiltroConsultaSubCatLayout.setHorizontalGroup(
            ppFiltroConsultaSubCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroConsultaSubCatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroConsultaSubCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFiltroConsultaSubCatLayout.createSequentialGroup()
                        .addComponent(rIdSubCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ciIdSubCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(ppFiltroConsultaSubCatLayout.createSequentialGroup()
                        .addGroup(ppFiltroConsultaSubCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppFiltroConsultaSubCatLayout.createSequentialGroup()
                                .addComponent(rCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(ppFiltroConsultaSubCatLayout.createSequentialGroup()
                                .addGroup(ppFiltroConsultaSubCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(clsCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(ppFiltroConsultaSubCatLayout.createSequentialGroup()
                                        .addGroup(ppFiltroConsultaSubCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(rStatusSubCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(rSubCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(ppFiltroConsultaSubCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cscSubCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(ppFiltroConsultaSubCatLayout.createSequentialGroup()
                                                .addComponent(jrbAtivoSubCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jrbInativoSubCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 820, Short.MAX_VALUE)
                                .addComponent(btPesquisarSubCat, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ppFiltroConsultaSubCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroConsultaSubCatLayout.createSequentialGroup()
                                        .addComponent(btAlterarSubCat, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btIncluirSubCat, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroConsultaSubCatLayout.createSequentialGroup()
                                        .addComponent(btRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btExcluirSubCat, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())))
        );
        ppFiltroConsultaSubCatLayout.setVerticalGroup(
            ppFiltroConsultaSubCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroConsultaSubCatLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(ppFiltroConsultaSubCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rIdSubCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ciIdSubCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroConsultaSubCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroConsultaSubCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFiltroConsultaSubCatLayout.createSequentialGroup()
                        .addGroup(ppFiltroConsultaSubCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rSubCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cscSubCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroConsultaSubCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rStatusSubCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jrbAtivoSubCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jrbInativoSubCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ppFiltroConsultaSubCatLayout.createSequentialGroup()
                        .addGroup(ppFiltroConsultaSubCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btAlterarSubCat, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btIncluirSubCat, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btPesquisarSubCat, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppFiltroConsultaSubCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btExcluirSubCat, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(2, Short.MAX_VALUE))
        );

        ppConsultaSubCat.add(ppFiltroConsultaSubCat, java.awt.BorderLayout.NORTH);

        ppTabConsultaSubCat.setMinimumSize(new java.awt.Dimension(1029, 120));
        ppTabConsultaSubCat.setPreferredSize(new java.awt.Dimension(1029, 450));
        ppTabConsultaSubCat.setLayout(new java.awt.BorderLayout());

        tpConsultaSubCat.setModel(otmSubCategoria);
        tpConsultaSubCat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpConsultaSubCatKeyPressed(evt);
            }
        });
        jspTabConsultaSubCat.setViewportView(tpConsultaSubCat);

        ppTabConsultaSubCat.add(jspTabConsultaSubCat, java.awt.BorderLayout.CENTER);

        rMargemConsultaSub.setPreferredSize(new java.awt.Dimension(5, 15));
        ppTabConsultaSubCat.add(rMargemConsultaSub, java.awt.BorderLayout.LINE_START);

        ppConsultaSubCat.add(ppTabConsultaSubCat, java.awt.BorderLayout.CENTER);

        paSubCategoria.addTab("Consulta", ppConsultaSubCat);

        ppCadastroSubCat.setMaximumSize(new java.awt.Dimension(1024, 500));
        ppCadastroSubCat.setLayout(new java.awt.BorderLayout());

        ppCadastroSubCat1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros da pesquisa"));
        ppCadastroSubCat1.setMinimumSize(new java.awt.Dimension(1029, 170));
        ppCadastroSubCat1.setPreferredSize(new java.awt.Dimension(1029, 170));

        ciIdSubCatCad.setEditable(false);
        ciIdSubCatCad.setText("0");
        ciIdSubCatCad.setComponenteRotulo(rIdSubCatCadastro);
        ciIdSubCatCad.setDescricaoRotulo("Código");
        ciIdSubCatCad.setObrigatorio(true);

        btCancelarSubCatCad.setText("Cancelar");
        btCancelarSubCatCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarSubCatCadActionPerformed(evt);
            }
        });

        btSalvarSubCatCad.setText("Salvar");
        btSalvarSubCatCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarSubCatCadActionPerformed(evt);
            }
        });

        rObsSubCatCad.setText("Observação:");

        jtaObservacao.setColumns(20);
        jtaObservacao.setDocument(new FixedLengthDocument(125
        ));
        jtaObservacao.setRows(5);
        jspDescSubCatCad.setViewportView(jtaObservacao);

        csSubCatCad.setComponenteRotulo(rSubCatCadastro);
        csSubCatCad.setDescricaoRotulo("Descrição");
        csSubCatCad.setObrigatorio(true);

        clsSituacaoSubCatCad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ativo", "Inativo", "" }));
        clsSituacaoSubCatCad.setComponenteRotulo(rStatusSubCatCad);
        clsSituacaoSubCatCad.setDescricaoRotulo("Situação");
        clsSituacaoSubCatCad.setObrigatorio(true);

        clsCatCad.setComponenteRotulo(rCatCad);
        clsCatCad.setDescricaoRotulo("Categoria");
        clsCatCad.setObrigatorio(true);

        javax.swing.GroupLayout ppCadastroSubCat1Layout = new javax.swing.GroupLayout(ppCadastroSubCat1);
        ppCadastroSubCat1.setLayout(ppCadastroSubCat1Layout);
        ppCadastroSubCat1Layout.setHorizontalGroup(
            ppCadastroSubCat1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppCadastroSubCat1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppCadastroSubCat1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppCadastroSubCat1Layout.createSequentialGroup()
                        .addComponent(rIdSubCatCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ciIdSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(ppCadastroSubCat1Layout.createSequentialGroup()
                        .addGroup(ppCadastroSubCat1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppCadastroSubCat1Layout.createSequentialGroup()
                                .addGroup(ppCadastroSubCat1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(rStatusSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rSubCatCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rObsSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ppCadastroSubCat1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(csSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jspDescSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(clsSituacaoSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(ppCadastroSubCat1Layout.createSequentialGroup()
                                .addComponent(rCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clsCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(1048, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppCadastroSubCat1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btCancelarSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btSalvarSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(547, 547, 547))
        );
        ppCadastroSubCat1Layout.setVerticalGroup(
            ppCadastroSubCat1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppCadastroSubCat1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppCadastroSubCat1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rIdSubCatCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ciIdSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppCadastroSubCat1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(ppCadastroSubCat1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rSubCatCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppCadastroSubCat1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rStatusSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsSituacaoSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppCadastroSubCat1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspDescSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rObsSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 567, Short.MAX_VALUE)
                .addGroup(ppCadastroSubCat1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCancelarSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSalvarSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        ppCadastroSubCat.add(ppCadastroSubCat1, java.awt.BorderLayout.CENTER);

        paSubCategoria.addTab("Cadastro / Alteração", ppCadastroSubCat);

        ppCadCaracteristica.setMaximumSize(new java.awt.Dimension(1024, 500));
        ppCadCaracteristica.setLayout(new java.awt.BorderLayout());

        painelPadrao3.setMinimumSize(new java.awt.Dimension(100, 40));
        painelPadrao3.setPreferredSize(new java.awt.Dimension(1300, 40));

        btSalvarCarCad2.setText("Salvar");
        btSalvarCarCad2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarCarCad2ActionPerformed(evt);
            }
        });

        btCancelarCarCad2.setText("Cancelar");
        btCancelarCarCad2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarCarCad2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelPadrao3Layout = new javax.swing.GroupLayout(painelPadrao3);
        painelPadrao3.setLayout(painelPadrao3Layout);
        painelPadrao3Layout.setHorizontalGroup(
            painelPadrao3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPadrao3Layout.createSequentialGroup()
                .addGap(493, 493, 493)
                .addComponent(btSalvarCarCad2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCancelarCarCad2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(870, Short.MAX_VALUE))
        );
        painelPadrao3Layout.setVerticalGroup(
            painelPadrao3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPadrao3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelPadrao3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCancelarCarCad2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSalvarCarCad2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        ppCadCaracteristica.add(painelPadrao3, java.awt.BorderLayout.SOUTH);

        ppPrincCaractItem.setPreferredSize(new java.awt.Dimension(1300, 300));
        ppPrincCaractItem.setLayout(new java.awt.BorderLayout());

        ppCadastroCaracteristicaItem.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Caracteristícas Itens", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP));
        ppCadastroCaracteristicaItem.setMinimumSize(new java.awt.Dimension(1029, 150));
        ppCadastroCaracteristicaItem.setPreferredSize(new java.awt.Dimension(1029, 110));

        csCarItemCadItem.setComponenteRotulo(rCarItemCadastroItem);
        csCarItemCadItem.setDescricaoRotulo("Descrição");
        csCarItemCadItem.setObrigatorio(true);
        csCarItemCadItem.setQtdMaxCaracteres(100);

        clsVisivelCarCadItem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sim", "Não", "" }));
        clsVisivelCarCadItem.setComponenteRotulo(rVisivelCadItem);
        clsVisivelCarCadItem.setDescricaoRotulo("Visível");
        clsVisivelCarCadItem.setObrigatorio(true);
        clsVisivelCarCadItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clsVisivelCarCadItemActionPerformed(evt);
            }
        });

        clsSituacaoCarCadItem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ativo", "Inativo", " " }));
        clsSituacaoCarCadItem.setComponenteRotulo(rStatusCarCadItem);
        clsSituacaoCarCadItem.setDescricaoRotulo("Situação");
        clsSituacaoCarCadItem.setObrigatorio(true);

        csObsCarItemCad.setComponenteRotulo(rObsCarCadItem);
        csObsCarItemCad.setDescricaoRotulo("Observação");
        csObsCarItemCad.setQtdMaxCaracteres(100);

        clsReplicavelCarCadItem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sim", "Não", "" }));
        clsReplicavelCarCadItem.setComponenteRotulo(rReplicavelCadItem);
        clsReplicavelCarCadItem.setDescricaoRotulo("Replicável");
        clsReplicavelCarCadItem.setObrigatorio(true);
        clsReplicavelCarCadItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clsReplicavelCarCadItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppCadastroCaracteristicaItemLayout = new javax.swing.GroupLayout(ppCadastroCaracteristicaItem);
        ppCadastroCaracteristicaItem.setLayout(ppCadastroCaracteristicaItemLayout);
        ppCadastroCaracteristicaItemLayout.setHorizontalGroup(
            ppCadastroCaracteristicaItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppCadastroCaracteristicaItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppCadastroCaracteristicaItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppCadastroCaracteristicaItemLayout.createSequentialGroup()
                        .addComponent(rStatusCarCadItem, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clsSituacaoCarCadItem, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rVisivelCadItem, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clsVisivelCarCadItem, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rReplicavelCadItem, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clsReplicavelCarCadItem, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(ppCadastroCaracteristicaItemLayout.createSequentialGroup()
                        .addGroup(ppCadastroCaracteristicaItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(ppCadastroCaracteristicaItemLayout.createSequentialGroup()
                                .addComponent(rObsCarCadItem, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(csObsCarItemCad, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE))
                            .addGroup(ppCadastroCaracteristicaItemLayout.createSequentialGroup()
                                .addComponent(rCarItemCadastroItem, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(csCarItemCadItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(673, 778, Short.MAX_VALUE))))
        );
        ppCadastroCaracteristicaItemLayout.setVerticalGroup(
            ppCadastroCaracteristicaItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppCadastroCaracteristicaItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppCadastroCaracteristicaItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rCarItemCadastroItem, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csCarItemCadItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppCadastroCaracteristicaItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rObsCarCadItem, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csObsCarItemCad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppCadastroCaracteristicaItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppCadastroCaracteristicaItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rVisivelCadItem, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(clsVisivelCarCadItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ppCadastroCaracteristicaItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rStatusCarCadItem, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(clsSituacaoCarCadItem, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ppCadastroCaracteristicaItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rReplicavelCadItem, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(clsReplicavelCarCadItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        ppPrincCaractItem.add(ppCadastroCaracteristicaItem, java.awt.BorderLayout.NORTH);

        ppTabCadCatItem.setPreferredSize(new java.awt.Dimension(1300, 160));
        ppTabCadCatItem.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setAlignmentX(2.0F);
        jScrollPane3.setPreferredSize(new java.awt.Dimension(2, 150));

        tpCadCarItem.setModel(otmCaracteristicaItemAdd);
        tpCadCarItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tpCadCarItemMouseClicked(evt);
            }
        });
        tpCadCarItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpCadCarItemKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tpCadCarItem);

        ppTabCadCatItem.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        ppAddCarItem.setPreferredSize(new java.awt.Dimension(120, 150));

        btAddCaracteristicaItem.setText("Adicionar");
        btAddCaracteristicaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddCaracteristicaItemActionPerformed(evt);
            }
        });

        btCancelarCaracteristicaItem.setText("Cancelar");
        btCancelarCaracteristicaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarCaracteristicaItemActionPerformed(evt);
            }
        });

        btAlterarCaracteristicaItem.setText("Alterar");
        btAlterarCaracteristicaItem.setEnabled(false);
        btAlterarCaracteristicaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarCaracteristicaItemActionPerformed(evt);
            }
        });

        bDescerCaracteristicaItem.setBorder(null);
        bDescerCaracteristicaItem.setToolTipText("");
        bDescerCaracteristicaItem.setContentAreaFilled(false);
        bDescerCaracteristicaItem.setRequestFocusEnabled(false);
        bDescerCaracteristicaItem.setRolloverEnabled(false);
        bDescerCaracteristicaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDescerCaracteristicaItemActionPerformed(evt);
            }
        });

        bSubirCaracteristicaItem.setBorder(null);
        bSubirCaracteristicaItem.setToolTipText("");
        bSubirCaracteristicaItem.setContentAreaFilled(false);
        bSubirCaracteristicaItem.setRequestFocusEnabled(false);
        bSubirCaracteristicaItem.setRolloverEnabled(false);
        bSubirCaracteristicaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSubirCaracteristicaItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppAddCarItemLayout = new javax.swing.GroupLayout(ppAddCarItem);
        ppAddCarItem.setLayout(ppAddCarItemLayout);
        ppAddCarItemLayout.setHorizontalGroup(
            ppAddCarItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppAddCarItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppAddCarItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppAddCarItemLayout.createSequentialGroup()
                        .addGroup(ppAddCarItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btAddCaracteristicaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btCancelarCaracteristicaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btAlterarCaracteristicaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(bDescerCaracteristicaItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bSubirCaracteristicaItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        ppAddCarItemLayout.setVerticalGroup(
            ppAddCarItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppAddCarItemLayout.createSequentialGroup()
                .addComponent(btAddCaracteristicaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btAlterarCaracteristicaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCancelarCaracteristicaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bSubirCaracteristicaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bDescerCaracteristicaItem, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(233, Short.MAX_VALUE))
        );

        ppTabCadCatItem.add(ppAddCarItem, java.awt.BorderLayout.EAST);

        rMargemItem.setPreferredSize(new java.awt.Dimension(10, 10));
        ppTabCadCatItem.add(rMargemItem, java.awt.BorderLayout.LINE_START);

        ppPrincCaractItem.add(ppTabCadCatItem, java.awt.BorderLayout.CENTER);

        ppCadCaracteristica.add(ppPrincCaractItem, java.awt.BorderLayout.CENTER);

        ppPrincCaract.setPreferredSize(new java.awt.Dimension(1334, 300));
        ppPrincCaract.setLayout(new java.awt.BorderLayout());

        ppCadastroCaracteristica.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastro Caracteristícas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP));
        ppCadastroCaracteristica.setMinimumSize(new java.awt.Dimension(1029, 170));
        ppCadastroCaracteristica.setPreferredSize(new java.awt.Dimension(1334, 140));

        rObsCarCad.setText("Observação:");

        csCarCad.setComponenteRotulo(rCarCadastro);
        csCarCad.setDescricaoRotulo("Descrição");
        csCarCad.setObrigatorio(true);
        csCarCad.setQtdMaxCaracteres(100);

        clsSituacaoCarCad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ativo", "Inativo", " " }));
        clsSituacaoCarCad.setComponenteRotulo(rStatusCarCad);
        clsSituacaoCarCad.setDescricaoRotulo("Situação");
        clsSituacaoCarCad.setObrigatorio(true);
        clsSituacaoCarCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clsSituacaoCarCadActionPerformed(evt);
            }
        });

        clsVisivelCarCad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sim", "Não", "" }));
        clsVisivelCarCad.setComponenteRotulo(rVisivelCad);
        clsVisivelCarCad.setDescricaoRotulo("Visível");
        clsVisivelCarCad.setObrigatorio(true);

        csObsCarCad.setQtdMaxCaracteres(100);

        csSubCatCarCad.setComponenteRotulo(rSubCategoriaCad);
        csSubCatCarCad.setDescricaoRotulo("SubCategoria");
        csSubCatCarCad.setEnabled(false);
        csSubCatCarCad.setObrigatorio(true);

        csCatCad.setComponenteRotulo(rCatCarCad);
        csCatCad.setDescricaoRotulo("Categoria");
        csCatCad.setEnabled(false);
        csCatCad.setObrigatorio(true);

        bgUnico.add(jrbUnicoSim);
        jrbUnicoSim.setText("Sim");
        jrbUnicoSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbUnicoSimActionPerformed(evt);
            }
        });

        rotulo1.setText("Único:");

        bgUnico.add(jrbUnicoNao);
        jrbUnicoNao.setSelected(true);
        jrbUnicoNao.setText("Não");
        jrbUnicoNao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbUnicoNaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppCadastroCaracteristicaLayout = new javax.swing.GroupLayout(ppCadastroCaracteristica);
        ppCadastroCaracteristica.setLayout(ppCadastroCaracteristicaLayout);
        ppCadastroCaracteristicaLayout.setHorizontalGroup(
            ppCadastroCaracteristicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppCadastroCaracteristicaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppCadastroCaracteristicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(ppCadastroCaracteristicaLayout.createSequentialGroup()
                        .addGroup(ppCadastroCaracteristicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rCatCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rCarCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppCadastroCaracteristicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppCadastroCaracteristicaLayout.createSequentialGroup()
                                .addComponent(csCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(rSubCategoriaCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(csSubCatCarCad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(csCarCad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(ppCadastroCaracteristicaLayout.createSequentialGroup()
                        .addComponent(rStatusCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clsSituacaoCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rVisivelCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clsVisivelCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(rotulo1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jrbUnicoSim, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jrbUnicoNao, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ppCadastroCaracteristicaLayout.createSequentialGroup()
                        .addComponent(rObsCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(csObsCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(546, 778, Short.MAX_VALUE))
        );
        ppCadastroCaracteristicaLayout.setVerticalGroup(
            ppCadastroCaracteristicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppCadastroCaracteristicaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppCadastroCaracteristicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rCatCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSubCategoriaCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csSubCatCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppCadastroCaracteristicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rCarCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppCadastroCaracteristicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rObsCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csObsCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppCadastroCaracteristicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clsVisivelCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsSituacaoCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rStatusCarCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rVisivelCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jrbUnicoSim, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rotulo1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jrbUnicoNao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        ppPrincCaract.add(ppCadastroCaracteristica, java.awt.BorderLayout.NORTH);

        painelPadrao1.setPreferredSize(new java.awt.Dimension(1334, 150));
        painelPadrao1.setLayout(new java.awt.BorderLayout());

        jScrollPane4.setAlignmentX(2.0F);
        jScrollPane4.setPreferredSize(new java.awt.Dimension(2, 200));

        tpCadCar.setModel(otmCaracteristicaAdd);
        tpCadCar.setPreferredScrollableViewportSize(new java.awt.Dimension(0, 250));
        tpCadCar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tpCadCarMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tpCadCar);

        painelPadrao1.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        painelPadrao5.setPreferredSize(new java.awt.Dimension(120, 150));

        btCancelarCaracteristica.setText("Cancelar");
        btCancelarCaracteristica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarCaracteristicaActionPerformed(evt);
            }
        });

        btAlterarCaracteristica.setText("Alterar");
        btAlterarCaracteristica.setEnabled(false);
        btAlterarCaracteristica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarCaracteristicaActionPerformed(evt);
            }
        });

        btAddCaracteristica.setText("Adicionar");
        btAddCaracteristica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddCaracteristicaActionPerformed(evt);
            }
        });

        bDescerCaracteristica.setBorder(null);
        bDescerCaracteristica.setToolTipText("");
        bDescerCaracteristica.setContentAreaFilled(false);
        bDescerCaracteristica.setRequestFocusEnabled(false);
        bDescerCaracteristica.setRolloverEnabled(false);
        bDescerCaracteristica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDescerCaracteristicaActionPerformed(evt);
            }
        });

        bSubirCaracteristica.setBorder(null);
        bSubirCaracteristica.setToolTipText("");
        bSubirCaracteristica.setContentAreaFilled(false);
        bSubirCaracteristica.setRequestFocusEnabled(false);
        bSubirCaracteristica.setRolloverEnabled(false);
        bSubirCaracteristica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSubirCaracteristicaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelPadrao5Layout = new javax.swing.GroupLayout(painelPadrao5);
        painelPadrao5.setLayout(painelPadrao5Layout);
        painelPadrao5Layout.setHorizontalGroup(
            painelPadrao5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPadrao5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPadrao5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelPadrao5Layout.createSequentialGroup()
                        .addGroup(painelPadrao5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btCancelarCaracteristica, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(btAlterarCaracteristica, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(btAddCaracteristica, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(bDescerCaracteristica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(bSubirCaracteristica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        painelPadrao5Layout.setVerticalGroup(
            painelPadrao5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPadrao5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btAddCaracteristica, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btAlterarCaracteristica, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCancelarCaracteristica, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bSubirCaracteristica, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bDescerCaracteristica, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        painelPadrao1.add(painelPadrao5, java.awt.BorderLayout.EAST);

        rMargemCararcterística.setPreferredSize(new java.awt.Dimension(10, 10));
        painelPadrao1.add(rMargemCararcterística, java.awt.BorderLayout.LINE_START);

        ppPrincCaract.add(painelPadrao1, java.awt.BorderLayout.CENTER);

        ppCadCaracteristica.add(ppPrincCaract, java.awt.BorderLayout.NORTH);

        paSubCategoria.addTab("Caracteristicas", ppCadCaracteristica);

        getContentPane().add(paSubCategoria, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btPesquisarSubCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarSubCatActionPerformed
        buscarSubCategoria();
    }//GEN-LAST:event_btPesquisarSubCatActionPerformed

    private void btIncluirSubCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIncluirSubCatActionPerformed
        salvarAlterar = "Salvar";
        paSubCategoria.setSelectedComponent(ppCadastroSubCat);
        paSubCategoria.setEnabledAt(1, true);
        paSubCategoria.setEnabledAt(0, false);
        ppCadastroSubCat.setVisible(true);
        paSubCategoria.setEnabledAt(2, true);
        ciIdSubCatCad.setInteger(0);
        ciIdSubCatCad.setEditable(false);
    }//GEN-LAST:event_btIncluirSubCatActionPerformed

    private void tpConsultaSubCatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpConsultaSubCatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tpConsultaSubCatKeyPressed

    private void btCancelarSubCatCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarSubCatCadActionPerformed
        limparCadastrar("CancelSubCat");
    }//GEN-LAST:event_btCancelarSubCatCadActionPerformed

    private void btSalvarSubCatCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarSubCatCadActionPerformed
        salvarSubCategoria();
        if (resultado == true) {
            buscarCaracteristica(caracteristica);
            paSubCategoria.setSelectedComponent(ppCadCaracteristica);
            paSubCategoria.setEnabledAt(1, false);
            paSubCategoria.setEnabledAt(2, true);
        }

    }//GEN-LAST:event_btSalvarSubCatCadActionPerformed

    private void btAlterarSubCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarSubCatActionPerformed
        salvarAlterar = "Alterar";
        caracteristica = new Caracteristica();

        alterarSubCategoria();
        if (resultado = true) {
            paSubCategoria.setSelectedComponent(ppCadastroSubCat);
            paSubCategoria.setEnabledAt(1, true);
            paSubCategoria.setEnabledAt(0, false);
            ppCadastroSubCat.setVisible(true);
            paSubCategoria.setEnabledAt(2, true);
            buscarCaracteristica(caracteristica);
        } else {

        }

    }//GEN-LAST:event_btAlterarSubCatActionPerformed

    private void clsCatFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsCatFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clsCatFiltroActionPerformed

    private void clsSituacaoCarCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsSituacaoCarCadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clsSituacaoCarCadActionPerformed

    private void clsVisivelCarCadItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsVisivelCarCadItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clsVisivelCarCadItemActionPerformed

    private void btAddCaracteristicaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddCaracteristicaItemActionPerformed
        salvarTabelaItem("Salvar");
        btAlterarCaracteristicaItem.setEnabled(false);

    }//GEN-LAST:event_btAddCaracteristicaItemActionPerformed

    private void btCancelarCaracteristicaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarCaracteristicaItemActionPerformed
        limparAddItem("CadItem");
    }//GEN-LAST:event_btCancelarCaracteristicaItemActionPerformed

    private void btAlterarCaracteristicaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarCaracteristicaItemActionPerformed

        salvarTabelaItem("Alterar");
        tpCadCar.setEnabled(true);
        tpCadCarItem.setEnabled(true);
        btAddCaracteristicaItem.setEnabled(true);
        btAlterarCaracteristicaItem.setEnabled(false);
    }//GEN-LAST:event_btAlterarCaracteristicaItemActionPerformed

    private void btAddCaracteristicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddCaracteristicaActionPerformed
        salvarTabelaCaracteristica("Salvar");
        btAlterarCaracteristica.setEnabled(false);
    }//GEN-LAST:event_btAddCaracteristicaActionPerformed

    private void btCancelarCaracteristicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarCaracteristicaActionPerformed
        limparAddItem("CadCar");
    }//GEN-LAST:event_btCancelarCaracteristicaActionPerformed

    private void btAlterarCaracteristicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarCaracteristicaActionPerformed
        salvarTabelaCaracteristica("Alterar");
        btAddCaracteristica.setEnabled(true);
        btAlterarCaracteristica.setEnabled(false);
    }//GEN-LAST:event_btAlterarCaracteristicaActionPerformed

    private void btSalvarCarCad2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarCarCad2ActionPerformed
        salvarCaracteristicas();
        limparAddCar();
    }//GEN-LAST:event_btSalvarCarCad2ActionPerformed

    private void btCancelarCarCad2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarCarCad2ActionPerformed
        limparAddCar();
    }//GEN-LAST:event_btCancelarCarCad2ActionPerformed

    private void tpCadCarItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpCadCarItemKeyPressed

    }//GEN-LAST:event_tpCadCarItemKeyPressed

    private void tpCadCarItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpCadCarItemMouseClicked
        tpCadCarItem(evt);
    }//GEN-LAST:event_tpCadCarItemMouseClicked

    private void tpSubTabCaracteristicasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpSubTabCaracteristicasMouseClicked
        tpSubCatCar(evt);
    }//GEN-LAST:event_tpSubTabCaracteristicasMouseClicked

    private void tpCadCarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpCadCarMouseClicked
        tpCadCar(evt);
    }//GEN-LAST:event_tpCadCarMouseClicked

    private void jrbUnicoNaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbUnicoNaoActionPerformed
        jrbUnicoSim.setSelected(false);
    }//GEN-LAST:event_jrbUnicoNaoActionPerformed

    private void jrbUnicoSimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbUnicoSimActionPerformed
        jrbUnicoNao.setSelected(false);
    }//GEN-LAST:event_jrbUnicoSimActionPerformed

    private void btExcluirSubCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirSubCatActionPerformed
        excluirSubCategoria();
    }//GEN-LAST:event_btExcluirSubCatActionPerformed

    private void clsReplicavelCarCadItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsReplicavelCarCadItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clsReplicavelCarCadItemActionPerformed

    private void bSubirCaracteristicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSubirCaracteristicaActionPerformed
        if (row_Is_Selected == false) {
            model = (ObjectTableModel) tpCadCar.getModel();
            row_Is_Selected = true;
        }
        index = tpCadCar.getSelectedRow();
        if (index > 0) {
            caracteristicaAdd = otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada());
            Caracteristica caracteristicaS = otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada() - 1);
            caracteristicaS.setAlterado("S");
            caracteristicaS.setIdUsuario(usuario.getIdUsuario());
            caracteristicaS.setSeqCategoria(index + 1);
            caracteristicaAdd.setAlterado("S");
            caracteristicaAdd.setIdUsuario(usuario.getIdUsuario());
            caracteristicaAdd.setSeqCategoria(index);
            lCaracteristicas.set(index, caracteristicaS);
            lCaracteristicas.set(index - 1, caracteristicaAdd);
            otmCaracteristicaAdd.setData(lCaracteristicas);
            otmCaracteristicaAdd.fireTableDataChanged();
            if (tpCadCar.getRowCount() > 0) {
                tpCadCar.packAll();
                tpCadCar.grabFocus();
            }
            tpCadCar.setRowSelectionInterval(index - 1, index - 1);

        }
    }//GEN-LAST:event_bSubirCaracteristicaActionPerformed

    private void bDescerCaracteristicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDescerCaracteristicaActionPerformed
        if (row_Is_Selected == false) {
            model = (ObjectTableModel) tpCadCar.getModel();
            row_Is_Selected = true;
        }
        index = tpCadCar.getSelectedRow();
        if (index < model.getRowCount() - 1) {
            caracteristicaAdd = otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada());
            Caracteristica caracteristicaB = otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada() + 1);
            caracteristicaB.setAlterado("S");
            caracteristicaB.setIdUsuario(usuario.getIdUsuario());
            caracteristicaB.setSeqCategoria(index + 1);
            caracteristicaAdd.setAlterado("S");
            caracteristicaAdd.setIdUsuario(usuario.getIdUsuario());
            caracteristicaAdd.setSeqCategoria(index + 2);
            lCaracteristicas.set(index, caracteristicaB);
            lCaracteristicas.set(index + 1, caracteristicaAdd);
            otmCaracteristicaAdd.setData(lCaracteristicas);
            otmCaracteristicaAdd.fireTableDataChanged();
            if (tpCadCar.getRowCount() > 0) {
                tpCadCar.packAll();
                tpCadCar.grabFocus();
            }
            tpCadCar.setRowSelectionInterval(index + 1, index + 1);

        }
    }//GEN-LAST:event_bDescerCaracteristicaActionPerformed

    private void bSubirCaracteristicaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSubirCaracteristicaItemActionPerformed
        int indexCar = lCaracteristicas.indexOf(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()));
        index = tpCadCarItem.getSelectedRow();
        if (index > 0) {
            caracteristicaItemAdd = otmCaracteristicaItemAdd.getValue(tpCadCarItem.getLinhaSelecionada());
            CaracteristicaItem caracteristicaItemS = otmCaracteristicaItemAdd.getValue(tpCadCarItem.getLinhaSelecionada() - 1);
            caracteristicaItemS.setAlterado("S");
            caracteristicaItemS.setIdUsuario(usuario.getIdUsuario());
            caracteristicaItemS.setSeqCatIte(index + 1);
            caracteristicaItemAdd.setAlterado("S");
            caracteristicaItemAdd.setIdUsuario(usuario.getIdUsuario());
            caracteristicaItemAdd.setSeqCatIte(index);
            lCaracteristicas.get(indexCar).getCaracteristicaItens().set(index, caracteristicaItemS);
            lCaracteristicas.get(indexCar).getCaracteristicaItens().set(index - 1, caracteristicaItemAdd);
            otmCaracteristicaAdd.setData(lCaracteristicas);
            otmCaracteristicaAdd.fireTableDataChanged();
            if (tpCadCar.getRowCount() > 0) {
                tpCadCar.packAll();
                tpCadCar.grabFocus();
            }
            tpCadCar.setRowSelectionInterval(indexCar, indexCar);
            tpCadCarItem.setRowSelectionInterval(index - 1, index - 1);

        }
    }//GEN-LAST:event_bSubirCaracteristicaItemActionPerformed

    private void bDescerCaracteristicaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDescerCaracteristicaItemActionPerformed
        modelItem = (ObjectTableModel) tpCadCarItem.getModel();
        int indexCar = lCaracteristicas.indexOf(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()));
        index = tpCadCarItem.getSelectedRow();
        if (index < modelItem.getRowCount() - 1) {
            caracteristicaItemAdd = otmCaracteristicaItemAdd.getValue(tpCadCarItem.getLinhaSelecionada());
            CaracteristicaItem caracteristicaItemB = otmCaracteristicaItemAdd.getValue(tpCadCarItem.getLinhaSelecionada() + 1);
            caracteristicaItemB.setAlterado("S");
            caracteristicaItemB.setIdUsuario(usuario.getIdUsuario());
            caracteristicaItemB.setSeqCatIte(index + 1);
            caracteristicaItemAdd.setAlterado("S");
            caracteristicaItemAdd.setIdUsuario(usuario.getIdUsuario());
            caracteristicaItemAdd.setSeqCatIte(index + 2);
            lCaracteristicas.get(indexCar).getCaracteristicaItens().set(index, caracteristicaItemB);
            lCaracteristicas.get(indexCar).getCaracteristicaItens().set(index + 1, caracteristicaItemAdd);
            otmCaracteristicaAdd.setData(lCaracteristicas);
            otmCaracteristicaAdd.fireTableDataChanged();
            if (tpCadCar.getRowCount() > 0) {
                tpCadCar.packAll();
                tpCadCar.grabFocus();
            }
            tpCadCar.setRowSelectionInterval(indexCar, indexCar);
            tpCadCarItem.setRowSelectionInterval(index + 1, index + 1);
        }
    }//GEN-LAST:event_bDescerCaracteristicaItemActionPerformed

    private void btRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRelatorioActionPerformed

        try {
            subCategoriaControl = new SubCategoriaControl();
            subCategoriaControl.gerarFichaSubCategoria(otmSubCategoria.getValue(tpConsultaSubCat.getLinhaSelecionada()).getIdCategoria(), 
                    otmSubCategoria.getValue(tpConsultaSubCat.getLinhaSelecionada()).getIdSubCategoria(), "S", "A");
        } catch (ErroSistemaException ex) {
            jpsSubCategoria.setStatus(ex.getMessage(), JPStatus.ERRO);
        }

    }//GEN-LAST:event_btRelatorioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.matriz.componentesbasicos.Botao bDescerCaracteristica;
    private br.com.martinello.matriz.componentesbasicos.Botao bDescerCaracteristicaItem;
    private br.com.martinello.matriz.componentesbasicos.Botao bSubirCaracteristica;
    private br.com.martinello.matriz.componentesbasicos.Botao bSubirCaracteristicaItem;
    private javax.swing.ButtonGroup bgUnico;
    private br.com.martinello.matriz.componentesbasicos.Botao btAddCaracteristica;
    private br.com.martinello.matriz.componentesbasicos.Botao btAddCaracteristicaItem;
    private br.com.martinello.matriz.componentesbasicos.Botao btAlterarCaracteristica;
    private br.com.martinello.matriz.componentesbasicos.Botao btAlterarCaracteristicaItem;
    private br.com.martinello.matriz.componentesbasicos.Botao btAlterarSubCat;
    private br.com.martinello.matriz.componentesbasicos.Botao btCancelarCarCad2;
    private br.com.martinello.matriz.componentesbasicos.Botao btCancelarCaracteristica;
    private br.com.martinello.matriz.componentesbasicos.Botao btCancelarCaracteristicaItem;
    private br.com.martinello.matriz.componentesbasicos.Botao btCancelarSubCatCad;
    private br.com.martinello.matriz.componentesbasicos.Botao btExcluirSubCat;
    private br.com.martinello.matriz.componentesbasicos.Botao btIncluirSubCat;
    private br.com.martinello.matriz.componentesbasicos.Botao btPesquisarSubCat;
    private br.com.martinello.matriz.componentesbasicos.Botao btRelatorio;
    private br.com.martinello.matriz.componentesbasicos.Botao btSalvarCarCad2;
    private br.com.martinello.matriz.componentesbasicos.Botao btSalvarSubCatCad;
    private br.com.martinello.matriz.componentesbasicos.CampoInteiro ciIdSubCatCad;
    private br.com.martinello.matriz.componentesbasicos.CampoInteiro ciIdSubCatFiltro;
    private br.com.martinello.matriz.componentesbasicos.CampoListaSimples clsCatCad;
    private br.com.martinello.matriz.componentesbasicos.CampoListaSimples clsCatFiltro;
    private br.com.martinello.matriz.componentesbasicos.CampoListaSimples clsReplicavelCarCadItem;
    private br.com.martinello.matriz.componentesbasicos.CampoListaSimples clsSituacaoCarCad;
    private br.com.martinello.matriz.componentesbasicos.CampoListaSimples clsSituacaoCarCadItem;
    private br.com.martinello.matriz.componentesbasicos.CampoListaSimples clsSituacaoSubCatCad;
    private br.com.martinello.matriz.componentesbasicos.CampoListaSimples clsVisivelCarCad;
    private br.com.martinello.matriz.componentesbasicos.CampoListaSimples clsVisivelCarCadItem;
    private br.com.martinello.matriz.componentesbasicos.CampoString csCarCad;
    private br.com.martinello.matriz.componentesbasicos.CampoString csCarItemCadItem;
    private br.com.martinello.matriz.componentesbasicos.CampoString csCatCad;
    private br.com.martinello.matriz.componentesbasicos.CampoString csObsCarCad;
    private br.com.martinello.matriz.componentesbasicos.CampoString csObsCarItemCad;
    private br.com.martinello.matriz.componentesbasicos.CampoString csSubCatCad;
    private br.com.martinello.matriz.componentesbasicos.CampoString csSubCatCarCad;
    private br.com.martinello.matriz.componentesbasicos.consulta.CampoStringConsulta cscSubCatFiltro;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private br.com.martinello.matriz.componentesbasicos.paineis.JPStatus jpsSubCategoria;
    private javax.swing.JRadioButton jrbAtivoSubCatFiltro;
    private javax.swing.JRadioButton jrbInativoSubCatFiltro;
    private javax.swing.JRadioButton jrbUnicoNao;
    private javax.swing.JRadioButton jrbUnicoSim;
    private javax.swing.JScrollPane jspDescSubCatCad;
    private javax.swing.JScrollPane jspTabConsultaSubCat;
    private javax.swing.JTextArea jtaObservacao;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelAba paCaracteristica;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelAba paSubCategoria;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao painelPadrao1;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao painelPadrao3;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao painelPadrao5;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppAddCarItem;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppCadCaracteristica;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppCadastroCaracteristica;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppCadastroCaracteristicaItem;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppCadastroSubCat;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppCadastroSubCat1;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppConsultaSubCat;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppFiltroConsultaSubCat;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppPrincCaract;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppPrincCaractItem;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppSubCarItens;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppSubTabCaracteristicas;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppTabCadCatItem;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppTabConsultaSubCat;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rCarCadastro;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rCarItemCadastroItem;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rCatCad;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rCatCarCad;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rCatFiltro;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rIdSubCatCadastro;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rIdSubCatFiltro;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rMargeConsultaCar;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rMargemCararcterística;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rMargemConsultaSub;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rMargemItem;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rObsCarCad;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rObsCarCadItem;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rObsSubCatCad;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rReplicavelCadItem;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rStatusCarCad;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rStatusCarCadItem;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rStatusSubCatCad;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rStatusSubCatFiltro;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rSubCatCadastro;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rSubCatFiltro;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rSubCategoriaCad;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rVisivelCad;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rVisivelCadItem;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rotulo1;
    private br.com.martinello.matriz.componentesbasicos.TabelaPadrao tpCadCar;
    private br.com.martinello.matriz.componentesbasicos.TabelaPadrao tpCadCarItem;
    private br.com.martinello.matriz.componentesbasicos.TabelaPadrao tpConsultaSubCat;
    private br.com.martinello.matriz.componentesbasicos.TabelaPadrao tpSubCarItens;
    private br.com.martinello.matriz.componentesbasicos.TabelaPadrao tpSubTabCaracteristicas;
    // End of variables declaration//GEN-END:variables

    private void buscarSubCategoria() {
        try {
            filtroSubCategoria = new SubCategoria();
            lSubCategoria = new ArrayList<>();
            subCategoriaControl = new SubCategoriaControl();
            if (clsCatFiltro.getSelectedItem().toString().equalsIgnoreCase("0 - TODAS")) {

            } else {
                cat = clsCatFiltro.getSelectedItem().toString();
                for (Categoria categorias : lCategoria) {
                    String catSelect = categorias.getIdCategoria() + " - " + categorias.getCategoria();
                    if (catSelect.equalsIgnoreCase(clsCatFiltro.getSelectedItem().toString())) {
                        filtroSubCategoria.setIdCategoria(categorias.getIdCategoria());
                    }
                }
            }
            if (ciIdSubCatFiltro.getInteger() > 0) {
                filtroSubCategoria.setIdCategoria(ciIdSubCatFiltro.getInteger());
            }
            if (cscSubCatFiltro.getFiltroOld() != null) {
                filtroSubCategoria.setSubCategoria(cscSubCatFiltro.getFiltroOld().toString().toUpperCase());
            }
            String sitCat = "";
            if (jrbAtivoSubCatFiltro.isSelected()) {
                sitCat = "'" + "A" + "'";
            }
            if (jrbInativoSubCatFiltro.isSelected()) {
                if (sitCat.isEmpty()) {
                    sitCat = "'" + "I" + "'";
                } else {
                    sitCat = sitCat + ",'I'";
                }
            }
            System.out.println("sit:" + sitCat);
            filtroSubCategoria.setSituacao(sitCat);
            lSubCategoria = subCategoriaControl.listarSubCategoria(filtroSubCategoria);
            otmSubCategoria.setData(lSubCategoria);
            otmSubCategoria.fireTableDataChanged();

            tpConsultaSubCat.setForeground(Color.BLACK);

            jpsSubCategoria.setStatus("Pesquisa realizada com sucesso.", JPStatus.NORMAL);

            if (tpConsultaSubCat.getRowCount() > 0) {
                tpConsultaSubCat.packAll();
                tpConsultaSubCat.addRowSelectionInterval(tpConsultaSubCat.convertRowIndexToView(0), tpConsultaSubCat.convertRowIndexToView(0));
                tpConsultaSubCat.grabFocus();
            }
        } catch (ErroSistemaException ex) {
            jpsSubCategoria.setStatus(ex.getMessage(), JPStatus.ERRO);
        }
        carregarCategorias();
    }

    private void buscarCaracteristica(Caracteristica caracteristica) {
        try {
            caracteristicaControl = new CaracteristicaControl();
            lCaracteristicas = new ArrayList<>();
            caracteristica.setSituacao("");
            lCaracteristicas = caracteristicaControl.listarCaracteristicas(caracteristica);
            otmCaracteristicaAdd.setData(lCaracteristicas);
            otmCaracteristicaAdd.fireTableDataChanged();
            tpCadCar.setForeground(Color.BLACK);
            jpsSubCategoria.setStatus("Pesquisa realizada com sucesso.", JPStatus.NORMAL);
            if (tpCadCar.getRowCount() > 0) {
                tpCadCar.packAll();
                tpCadCar.addRowSelectionInterval(tpCadCar.convertRowIndexToView(0), tpCadCar.convertRowIndexToView(0));
                tpCadCar.grabFocus();
            }
        } catch (ErroSistemaException ex) {
            jpsSubCategoria.setStatus(ex.getMessage(), JPStatus.ERRO);
        }
    }

    private void carregarCategorias() {
        categoria = new Categoria();
        subCategoriaControl = new SubCategoriaControl();
        lCategoria = new ArrayList<>();
        try {
            lCategoria = subCategoriaControl.listarJcbCategoria(categoria);
        } catch (ErroSistemaException ex) {
            jpsSubCategoria.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }
        clsCatCad.removeAllItems();
        clsCatFiltro.removeAllItems();
        for (Categoria categorias : lCategoria) {
            clsCatFiltro.addItem(categorias.getIdCategoria() + " - " + categorias.getCategoria());
            clsCatCad.addItem(categorias.getIdCategoria() + " - " + categorias.getCategoria());
        }
    }

    private void tpCadCar(MouseEvent evt) {
        if (evt.getClickCount() == 2 && tpCadCar.getSelectedColumn() != 1) {
            caracteristica = new Caracteristica();
            caracteristica = otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada());
            csCarCad.setString(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()).getCaracteristica());
            csSubCatCad.setString(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()).getSubCategoria());
            csCarCad.setString(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()).getCaracteristica());
            csObsCarCad.setString(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()).getObsCaracteristica());
            if (caracteristica.getUnico().equals("S")) {
                jrbUnicoNao.setSelected(false);
                jrbUnicoSim.setSelected(true);
            } else {
                jrbUnicoNao.setSelected(true);
                jrbUnicoSim.setSelected(false);
            }
            tpCadCar.setEnabled(false);
            tpCadCarItem.setEnabled(false);
            btAlterarCaracteristica.setEnabled(true);
            btAddCaracteristica.setEnabled(false);
            salvarAlterar = "Alterar";
        }
    }

    private void tpCadCarItem(MouseEvent evt) {
        if (evt.getClickCount() == 2 && tpCadCarItem.getSelectedColumn() != 1) {
            tpCadCar.setEnabled(false);
            tpCadCarItem.setEnabled(false);
            caracteristicaItemAdd = new CaracteristicaItem();
            caracteristicaItemAdd = otmCaracteristicaItemAdd.getValue(tpCadCarItem.getLinhaSelecionada());
            csCarItemCadItem.setString(otmCaracteristicaItemAdd.getValue(tpCadCarItem.getLinhaSelecionada()).getCaracteristicaItem());
            csObsCarItemCad.setString(otmCaracteristicaItemAdd.getValue(tpCadCarItem.getLinhaSelecionada()).getObservacao());
            btAlterarCaracteristicaItem.setEnabled(true);
            btAddCaracteristicaItem.setEnabled(false);
            salvarAlterar = "Alterar";
        }
    }

    private void tpSubCatCar(MouseEvent evt) {
        if (evt.getClickCount() == 2 && tpCadCarItem.getSelectedColumn() != 1) {
            try {
                caracteristicaItem = new CaracteristicaItem();
                lCaracteristicasItens = new LinkedList<>();
                caracteristicaItemControl = new CaracteristicaItemControl();
                caracteristicaItem.setIdCategoria(otmSubCategoria.getValue(tpConsultaSubCat.getLinhaSelecionada()).getIdCategoria());
                caracteristicaItem.setIdSubCategoria(otmSubCategoria.getValue(tpConsultaSubCat.getLinhaSelecionada()).getIdSubCategoria());
                caracteristicaItem.setIdCaracteristica(otmCaracteristica.getValue(tpSubTabCaracteristicas.getLinhaSelecionada()).getIdCaracteristica());
                lCaracteristicasItens = caracteristicaItemControl.listarCarItem(caracteristicaItem);
                otmCaracteristicaItem.setData(lCaracteristicasItens);
                otmCaracteristicaItem.fireTableDataChanged();
                tpSubCarItens.setForeground(Color.BLACK);
                if (tpSubCarItens.getRowCount() > 0) {
                    tpSubCarItens.packAll();
                    tpSubCarItens.addRowSelectionInterval(tpSubCarItens.convertRowIndexToView(0), tpSubCarItens.convertRowIndexToView(0));
                    tpSubCarItens.grabFocus();
                }
                paCaracteristica.setSelectedComponent(ppSubCarItens);
                jpsSubCategoria.setStatus("Pesquisa Realizada com Sucesso!.", JPStatus.NORMAL);
            } catch (ErroSistemaException ex) {
                jpsSubCategoria.setStatus(ex.getMessage(), JPStatus.ERRO, 20);
            }
        }
    }

    private boolean alterarSubCategoria() {
        subCategoria = new SubCategoria();
        resultado = false;
        subCategoriaControl = new SubCategoriaControl();
        int linha = tpConsultaSubCat.getSelectedRow();
        codigo = "0";
        if (linha < 0) {
            jpsSubCategoria.setStatus("Selecione uma Categoria na Tabela, antes de clicar em alterar", JPStatus.ALERTA);
            return resultado;
        } else {
            subCategoria = otmSubCategoria.getValue(tpConsultaSubCat.getLinhaSelecionada());
            paSubCategoria.setSelectedComponent(ppCadastroSubCat);
            try {
                subCategoria = subCategoriaControl.buscarSubCategoria(subCategoria);
            } catch (ErroSistemaException ex) {
                ex.printStackTrace();
                jpsSubCategoria.setStatus("Erro ao Carregar a SubCategoria", JPStatus.ERRO, ex);
                return resultado;
            }
            caracteristica.setIdCategoria(subCategoria.getIdCategoria());
            caracteristica.setIdSubCategoria(subCategoria.getIdSubCategoria());
            caracteristica.setSubCategoria(subCategoria.getSubCategoria());
            catSelect = subCategoria.getIdCategoria() + " - " + subCategoria.getCategoria();
            clsCatCad.setSelectedItem(catSelect);
            ciIdSubCatCad.setInteger(subCategoria.getIdSubCategoria());
            csSubCatCarCad.setString(subCategoria.getSubCategoria());
            csCatCad.setString(subCategoria.getCategoria());
            ciIdSubCatCad.setEditable(false);
            csSubCatCad.setText(subCategoria.getSubCategoria());
            jtaObservacao.setText(subCategoria.getObsSubCategoria());
            if (subCategoria.getSituacao().equalsIgnoreCase("A")) {
                clsSituacaoSubCatCad.setSelectedIndex(0);
            } else {
                clsSituacaoSubCatCad.setSelectedIndex(1);
            }
            salvarAlterar = "Alterar";

            resultado = true;
        }
        return resultado;
    }

    private boolean salvarSubCategoria() {
        resultado = false;
        caracteristica = new Caracteristica();
        if (clsSituacaoSubCatCad.getSelectedItem().toString().equalsIgnoreCase(" ")) {
            jpsSubCategoria.setStatus("Erro é necessário informar o Status da Categoria!", JPStatus.ALERTA);
            return resultado;
        }
        if (csSubCatCad.getString().equalsIgnoreCase("")) {
            jpsSubCategoria.setStatus("Erro é necessário informar a descrição da SubCategoria!", JPStatus.ALERTA);
            return resultado;
        }
        subCategoria = new SubCategoria();
        subCategoriaControl = new SubCategoriaControl();
        if (clsCatCad.getSelectedItem().toString().equalsIgnoreCase("0 - TODAS")) {
            jpsSubCategoria.setStatus("Erro é necessário informar a Categoria!", JPStatus.ALERTA);
            return resultado;
        } else {
            cat = clsCatCad.getSelectedItem().toString();
            for (Categoria categorias : lCategoria) {
                String idCategoria = categorias.getIdCategoria() + " - " + categorias.getCategoria();
                if (idCategoria.equalsIgnoreCase(clsCatCad.getSelectedItem().toString())) {
                    subCategoria.setIdCategoria(categorias.getIdCategoria());
                    caracteristica.setIdCategoria(categorias.getIdCategoria());
                }
            }
        }
        subCategoria.setIdUsuario(usuario.getIdUsuario());// Lembrar de pegar idUsuário
        subCategoria.setIdSubCategoria(ciIdSubCatCad.getInteger());
        subCategoria.setSubCategoria(csSubCatCad.getString());
        subCategoria.setObsSubCategoria(jtaObservacao.getText());
        csSubCatCarCad.setString(cat);
        csCatCad.setString(csSubCatCad.getString());
        if (clsSituacaoSubCatCad.getSelectedItem().toString().equalsIgnoreCase("ATIVO")) {
            subCategoria.setSituacao("A");
        } else {
            subCategoria.setSituacao("I");
        }
        if (salvarAlterar.equalsIgnoreCase("Alterar")) {
            try {
                if (clsCatCad.getSelectedItem().toString().equalsIgnoreCase("0 - Todas")) {
                    jpsSubCategoria.setStatus("Erro é necessário informar uma Categoria!", JPStatus.ALERTA, 20);
                    return resultado;
                } else {
                    if (!clsCatCad.getSelectedItem().toString().equalsIgnoreCase(catSelect)) {
                        int option = JOptionPane.showConfirmDialog(rootPane, "Deseja  relamente alterar a categoria a qual a subCategoria pertence?", "Confirma", JOptionPane.YES_NO_OPTION);
                        if (option == 0) {
                            String codPro = subCategoriaControl.validaAltCategoria(codigo);
                            if (!codPro.equalsIgnoreCase("")) {
                                jpsSubCategoria.setStatus("Erro ao altera SubCategoria! O registro possui produto(s) relacionado(s). Produto(s):" + codPro, JPStatus.ALERTA, 20);
                                return resultado;
                            }
                        } else {
                            jpsSubCategoria.setStatus("Processo Cancelado!", JPStatus.ALERTA, 20);
                            return resultado;
                        }
                    }
                }
                caracteristica.setIdSubCategoria(subCategoriaControl.updateSubCategoria(subCategoria));
            } catch (ErroSistemaException ex) {
                jpsSubCategoria.setStatus(ex.getMessage(), JPStatus.ERRO);
            }
        } else {
            try {
                caracteristica.setIdSubCategoria(subCategoriaControl.insertSubCategoria(subCategoria));
            } catch (ErroSistemaException ex) {
                jpsSubCategoria.setStatus(ex.getMessage(), JPStatus.ERRO, ex);
                return resultado;
            }
        }
        if (resultado = true) {
            jpsSubCategoria.setStatus("Registro Cadastrado com Sucesso!", JPStatus.NORMAL, 20);
            limparCadastrar("Salvar");
        }
        return resultado;
    }

    private void setDadosCaracteristicas(SubCategoria value) {
        try {
            caracteristica = new Caracteristica();
            lCaracteristicas = new ArrayList<>();
            caracteristicaControl = new CaracteristicaControl();
            caracteristica.setIdCategoria(value.getIdCategoria());
            caracteristica.setIdSubCategoria(value.getIdSubCategoria());
            caracteristica.setSituacao("");

            lCaracteristicas = caracteristicaControl.listarCaracteristica(caracteristica);
            otmCaracteristica.setData(lCaracteristicas);
            otmCaracteristica.fireTableDataChanged();
            tpSubTabCaracteristicas.setForeground(Color.BLACK);
            if (tpSubTabCaracteristicas.getRowCount() > 0) {
                tpSubTabCaracteristicas.packAll();             
                tpConsultaSubCat.grabFocus();
                tpSubTabCaracteristicas.addRowSelectionInterval(tpSubTabCaracteristicas.convertRowIndexToView(0), tpSubTabCaracteristicas.convertRowIndexToView(0));

            }
            //jpsSubCategoria.setStatus("Pesquisa realizada com sucesso.", JPStatus.NORMAL);
        } catch (ErroSistemaException ex) {
            jpsSubCategoria.setStatus(ex.getMessage(), JPStatus.ERRO);
        }
    }

    private void setDadosCaracteristicasItens() {

        caracteristicaItemAdd = new CaracteristicaItem();
        caracteristicaItemAdd.setIdCategoria(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()).getIdCategoria());
        caracteristicaItemAdd.setIdSubCategoria(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()).getIdSubCategoria());
        caracteristicaItemAdd.setIdCaracteristica(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()).getIdCaracteristica());
        caracteristicaItemAdd.setSubCategoria(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()).getSubCategoria());
        caracteristicaItemAdd.setCaracteristica(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()).getCaracteristica());
        otmCaracteristicaItemAdd.setData(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()).getCaracteristicaItens());
        otmCaracteristicaItemAdd.fireTableDataChanged();
        tpCadCarItem.setForeground(Color.BLACK);
        jpsSubCategoria.setStatus("Pesquisa realizada com sucesso.", JPStatus.NORMAL);
        if (tpCadCarItem.getRowCount() > 0) {
            tpCadCarItem.packAll();
            tpCadCarItem.addRowSelectionInterval(tpCadCarItem.convertRowIndexToView(0), tpCadCarItem.convertRowIndexToView(0));
            tpCadCarItem.grabFocus();
        }
    }

    private boolean salvarTabelaCaracteristica(String salvarAlterar) {
        resultado = false;
        caracteristicaAdd = new Caracteristica();
        //caracteristicaAdd = caracteristica;
        if (salvarAlterar.equalsIgnoreCase("Salvar")) {
            caracteristicaAdd.setIdCategoria(caracteristica.getIdCategoria());
            caracteristicaAdd.setIdSubCategoria(caracteristica.getIdSubCategoria());
            caracteristicaAdd.setSubCategoria(csSubCatCarCad.getString());
            caracteristicaAdd.setCaracteristica(csCarCad.getString());
            caracteristicaAdd.setObsCaracteristica(csObsCarCad.getString());
            caracteristicaAdd.setSeqCategoria(tpCadCar.getRowCount() + 1);
            caracteristicaAdd.setAlterado("N");
            if (clsSituacaoCarCad.getSelectedItem().toString().equalsIgnoreCase("ATIVO")) {
                caracteristicaAdd.setSituacao("A");
            } else {
                caracteristicaAdd.setSituacao("I");
            }
            if (clsVisivelCarCad.getSelectedItem().toString().equalsIgnoreCase("SIM")) {
                caracteristicaAdd.setVisivel("S");
            } else {
                caracteristicaAdd.setVisivel("N");
            }
            if ((jrbUnicoSim.isSelected() && jrbUnicoNao.isSelected())
                    || (!jrbUnicoSim.isSelected() && !jrbUnicoNao.isSelected())) {
                jpsSubCategoria.setStatus("Item adicionado com sucesso!.", JPStatus.NORMAL);
                return resultado;
            } else {
                if (jrbUnicoSim.isSelected()) {
                    caracteristicaAdd.setUnico("S");
                } else if (jrbUnicoNao.isSelected()) {
                    caracteristicaAdd.setUnico("N");
                }
            }
            if (caracteristicaAdd.getUnico().equalsIgnoreCase("S")) {
                caracteristicaItemAdd = new CaracteristicaItem();
                caracteristicaItemAdd.setAlterado("N");
                caracteristicaItemAdd.setCaracteristica(caracteristicaAdd.getCaracteristica());
                caracteristicaItemAdd.setCaracteristicaItem("Geral");
                caracteristicaItemAdd.setIdCategoria(caracteristicaAdd.getIdCategoria());
                caracteristicaItemAdd.setIdSubCategoria(caracteristicaAdd.getIdSubCategoria());
                caracteristicaItemAdd.setSubCategoria(csSubCatCarCad.getString());
                caracteristicaItemAdd.setVisivel("S");
                caracteristicaItemAdd.setSituacao("A");
                caracteristicaItemAdd.setReplicavel("S");
                caracteristicaItemAdd.setIdUsuario(usuario.getIdUsuario());
                caracteristicaItemAdd.setSeqCatIte(1);
                lCaracteristicasItens = new LinkedList<>();
                lCaracteristicasItens.add(caracteristicaItemAdd);
                caracteristicaAdd.setCaracteristicaItens(lCaracteristicasItens);
            }
            caracteristicaAdd.setIdUsuario(usuario.getIdUsuario());
            lCaracteristicas.add(caracteristicaAdd);

        } else {
            /* Tela  */
            caracteristicaAdd.setIdCategoria(caracteristica.getIdCategoria());
            caracteristicaAdd.setIdSubCategoria(caracteristica.getIdSubCategoria());
            caracteristicaAdd.setIdCaracteristica(caracteristica.getIdCaracteristica());
            caracteristicaAdd.setSubCategoria(csSubCatCad.getString());
            caracteristicaAdd.setCaracteristica(csCarCad.getString());
            caracteristicaAdd.setCaracteristica(csCarCad.getString());
            caracteristicaAdd.setSeqCategoria(caracteristica.getSeqCategoria());
            caracteristicaAdd.setObsCaracteristica(csObsCarCad.getString());
            caracteristicaAdd.setIdUsuario(usuario.getIdUsuario());
            caracteristicaAdd.setAlterado("S");
            if (jrbUnicoSim.isSelected()) {
                caracteristicaAdd.setUnico("S");
            } else if (jrbUnicoNao.isSelected()) {
                caracteristicaAdd.setUnico("N");
            }
            caracteristicaAdd.setCaracteristicaItens(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()).getCaracteristicaItens());
            if (clsSituacaoCarCad.getSelectedItem().toString().equalsIgnoreCase("ATIVO")) {
                caracteristicaAdd.setSituacao("A");
            } else {
                caracteristicaAdd.setSituacao("I");
            }
            if (clsVisivelCarCad.getSelectedItem().toString().equalsIgnoreCase("SIM")) {
                caracteristicaAdd.setVisivel("S");
            } else {
                caracteristicaAdd.setVisivel("N");
            }
            int indexCaracteristica = 0;
            indexCaracteristica = lCaracteristicas.indexOf(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()));
            lCaracteristicas.set(indexCaracteristica, caracteristicaAdd);
        }
        otmCaracteristicaAdd.setData(lCaracteristicas);
        otmCaracteristicaAdd.fireTableDataChanged();
        if (tpCadCar.getRowCount() > 0) {
            tpCadCar.packAll();
            tpCadCar.addRowSelectionInterval(otmCaracteristicaAdd.getRowCount() - 1, otmCaracteristicaAdd.getRowCount() - 1);
            tpCadCar.grabFocus();
        }
        jpsSubCategoria.setStatus("Item adicionado com sucesso!.", JPStatus.NORMAL);
        limparAddItem("CadCar");
        return resultado = true;
    }

    private boolean salvarTabelaItem(String salvarAlterar) {
        resultado = false;
        int indexCaracteristica = 0, indexCaracteristicaItem = 0;
        if (salvarAlterar.equalsIgnoreCase("Salvar")) {
            if (otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()).getUnico().equalsIgnoreCase("N")) {
                caracteristicaItemAdd.setCaracteristicaItem(csCarItemCadItem.getString());
                caracteristicaItemAdd.setObservacao(csObsCarItemCad.getString());
                caracteristicaItemAdd.setSeqCatIte(tpCadCarItem.getRowCount() + 1);
                caracteristicaItemAdd.setAlterado("N");
                if (clsSituacaoCarCadItem.getSelectedItem().toString().equalsIgnoreCase("ATIVO")) {
                    caracteristicaItemAdd.setSituacao("A");
                } else {
                    caracteristicaItemAdd.setSituacao("I");
                }
                if (clsVisivelCarCadItem.getSelectedItem().toString().equalsIgnoreCase("SIM")) {
                    caracteristicaItemAdd.setVisivel("S");
                } else {
                    caracteristicaItemAdd.setVisivel("N");
                }
                if (clsReplicavelCarCadItem.getSelectedItem().toString().equalsIgnoreCase("SIM")) {
                    caracteristicaItemAdd.setReplicavel("S");
                } else {
                    caracteristicaItemAdd.setReplicavel("N");
                }
                caracteristicaItemAdd.setIdUsuario(usuario.getIdUsuario());
                indexCaracteristica = lCaracteristicas.indexOf(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()));
                lCaracteristicas.set(lCaracteristicas.indexOf(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada())), otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada())).getCaracteristicaItens().add(caracteristicaItemAdd);
            } else {
                jpsSubCategoria.setStatus("Não é possivel adicionar novo item para caracteristicas do tipo único!.", JPStatus.NORMAL);
            }
        } else {
            /**
             * Tela
             */

            caracteristicaItemAdd.setCaracteristicaItem(csCarItemCadItem.getString());
            caracteristicaItemAdd.setObservacao(csObsCarItemCad.getString());
            caracteristicaItemAdd.setAlterado("S");
            if (clsSituacaoCarCadItem.getSelectedItem().toString().equalsIgnoreCase("ATIVO")) {
                caracteristicaItemAdd.setSituacao("A");
            } else {
                caracteristicaItemAdd.setSituacao("I");
            }
            if (clsVisivelCarCadItem.getSelectedItem().toString().equalsIgnoreCase("SIM")) {
                caracteristicaItemAdd.setVisivel("S");
            } else {
                caracteristicaItemAdd.setVisivel("N");
            }
            if (clsReplicavelCarCadItem.getSelectedItem().toString().equalsIgnoreCase("SIM")) {
                caracteristicaItemAdd.setReplicavel("S");
            } else {
                caracteristicaItemAdd.setReplicavel("N");
            }

            indexCaracteristica = lCaracteristicas.indexOf(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()));
            indexCaracteristicaItem = lCaracteristicas.get(indexCaracteristica).getCaracteristicaItens().indexOf(caracteristicaItemAdd);
            lCaracteristicas.get(indexCaracteristica).getCaracteristicaItens().set(indexCaracteristicaItem, caracteristicaItemAdd);
            //lCaracteristicas.set(), otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada())).getCaracteristicaItens().set(lCaracteristicas.get(lCaracteristicas.indexOf(otmCaracteristicaAdd.getValue(tpCadCar.getLinhaSelecionada()))).getCaracteristicaItens().indexOf(caracteristicaItemAdd), caracteristicaItemAdd);
        }
        otmCaracteristicaAdd.setData(lCaracteristicas);
        otmCaracteristicaAdd.fireTableDataChanged();
        if (tpCadCar.getRowCount() > 0) {
            tpCadCar.packAll();
            tpCadCar.addRowSelectionInterval(indexCaracteristica, indexCaracteristica);
            tpCadCar.grabFocus();
        }
        jpsSubCategoria.setStatus("Item adicionado com sucesso!.", JPStatus.NORMAL);
        limparAddItem("CadItem");
        return resultado;
    }

    private void salvarCaracteristicas() {
        try {
            caracteristicaControl = new CaracteristicaControl();
            if (salvarAlterar.equalsIgnoreCase("Salvar")) {
                caracteristicaControl.insertCaracteristicas(lCaracteristicas);
            }
            if (salvarAlterar.equalsIgnoreCase("Alterar")) {
                caracteristicaControl.updateCaracteristica(lCaracteristicas);
            }
        } catch (ErroSistemaException ex) {
            jpsSubCategoria.setStatus(ex.getMessage(), JPStatus.ERRO, 20);
        }
        jpsSubCategoria.setStatus("Item Salvo com Sucesso!.", JPStatus.NORMAL);

    }

    public void limparAddItem(String cancelar) {
        if (cancelar.equalsIgnoreCase("CadItem")) {
            csObsCarItemCad.setString(null);
            csCarItemCadItem.setString(null);
            clsSituacaoCarCadItem.setSelectedIndex(0);
            clsVisivelCarCadItem.setSelectedIndex(0);
        } else if (cancelar.equalsIgnoreCase("CadCar")) {
            csCarCad.setString(null);
            csObsCarCad.setString(null);
            clsSituacaoCarCad.setSelectedIndex(0);
            clsVisivelCarCad.setSelectedIndex(0);
        }
        jrbUnicoSim.setSelected(false);
        jrbUnicoNao.setSelected(true);
        tpCadCar.setEnabled(true);
        tpCadCarItem.setEnabled(true);
    }

    private void limparAddCar() {
        clsCatCad.setSelectedIndex(0);
        ciIdSubCatCad.setInteger(0);
        ciIdSubCatCad.setEditable(false);
        csSubCatCad.setText(null);
        jtaObservacao.setText(null);
        csCarItemCadItem.setText(null);
        csCarCad.setText(null);
        csObsCarCad.setText(null);
        csObsCarItemCad.setText(null);
        salvarAlterar = "Salvar";
        paSubCategoria.setEnabledAt(2, false);
        paSubCategoria.setSelectedComponent(ppConsultaSubCat);
        paSubCategoria.setEnabledAt(0, true);
        buscarSubCategoria();
    }

    private void limparCadastrar(String origem) {
        clsCatCad.setSelectedIndex(0);
        ciIdSubCatCad.setInteger(0);
        ciIdSubCatCad.setEditable(false);
        csSubCatCad.setText(null);
        jtaObservacao.setText(null);
        clsSituacaoSubCatCad.setSelectedIndex(2);
        salvarAlterar = "Salvar";
        if (origem.equalsIgnoreCase("CancelSubCat")) {
            paSubCategoria.setSelectedComponent(ppConsultaSubCat);
            paSubCategoria.setEnabledAt(0, true);
            paSubCategoria.setEnabledAt(2, false);
            paSubCategoria.setEnabledAt(1, false);
        }
    }

    public void setaUnicoSim() {
        jrbUnicoSim.setSelected(true);
    }

    public void setaUnicoNao() {
        jrbUnicoNao.setSelected(true);
    }
    //    private boolean cadastrarCategoria() {
//        try {
//            categoria = new Categoria();
//            if (cscSubCatFiltro.getString().isEmpty()) {
//                JOptionPane.showMessageDialog(null, "É necessário preencher o campo descrição da categoria.");
//            } else {
//                categoria.setCategoria(cscSubCatFiltro.getString().toUpperCase());
//            }
//            int option = JOptionPane.showConfirmDialog(rootPane, "Deseja realmente cadastrar nova categoria?", "Confirma", JOptionPane.YES_NO_OPTION);
//            if (option == 1) {
//                return resultado = false;
//            }
//
//            categoriaControl = new CategoriaControl();
//            resultado = categoriaControl.insertCategoria(categoria);
//        } catch (ErroSistemaException ex) {
//            jpsSubCategoria.setStatus(ex.getMessage(), JPStatus.ERRO);
//        }
//        if (resultado = true) {
//            ciIdSubCatFiltro.setValorPadrao(0);
//            cscSubCatFiltro.setaValorFiltro(null);
//        }
//        return resultado;
//    }

    private void excluirSubCategoria() {
        try {
            int linha = tpConsultaSubCat.getSelectedRow();
            if (linha < 0) {
                jpsSubCategoria.setStatus("Selecione uma SubCategoria na Tabela, antes de clicar em Excluir", JPStatus.ALERTA);
            } else {
                int sair = JOptionPane.showConfirmDialog(rootPane, "Deseja realmente excluir o registro?", "Confirma", JOptionPane.YES_NO_OPTION);
                if (sair == 0) {
                    resultado = false;
                    subCategoriaControl = new SubCategoriaControl();
                    subCategoria = new SubCategoria();

                    subCategoria = otmSubCategoria.getValue(tpConsultaSubCat.getLinhaSelecionada());
                    resultado = subCategoriaControl.excluir(subCategoria);
                    if (resultado == true) {
                        jpsSubCategoria.setStatus("Exclusão realizada com sucesso!", JPStatus.NORMAL, 20);
                    }
                }
            }
        } catch (ErroSistemaException ex) {
            jpsSubCategoria.setStatus(ex.getMessage(), JPStatus.ERRO, ex);
        }
    }

}
