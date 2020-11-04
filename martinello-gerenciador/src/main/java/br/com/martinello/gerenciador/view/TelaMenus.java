/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.view;

import br.com.martinello.componentesbasicos.Campo;
import br.com.martinello.componentesbasicos.ConstantesGlobais;
import br.com.martinello.bd.matriz.control.MenuControl;
import br.com.martinello.bd.matriz.model.domain.Menu;
import br.com.martinello.gerenciador.view.model.MenuMutableTreeNode;
import br.com.martinello.gerenciador.view.model.MenuTreeModel;
import br.com.martinello.util.ValidacaoException;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Sidnei
 */
public class TelaMenus extends TelaPadrao {

    /**
     * Creates new form TelaMenus
     */
    protected MenuTreeModel dtmModelo;
    protected MenuMutableTreeNode dmtnRoot;
    protected MenuControl menuControl;
    protected Menu menu;
    protected List<Campo> lCampos;
    
    public TelaMenus() {
        initComponents();
        
        lCampos = new LinkedList<>();
        
        carregarParametrosCampos();
        
        menuControl = new MenuControl();
        menu = new Menu();
        
        dmtnRoot = new MenuMutableTreeNode("Menus");
        dtmModelo = new MenuTreeModel(dmtnRoot);
        jtMenus.setModel(dtmModelo);
        
        jtMenus.setEditable(false);
        jtMenus.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jtMenus.setShowsRootHandles(true);

        //        for (int i = 0; i < 40; i++) {
//            Menu menu = new Menu(new Long(i), "jmCadastro", "Cadastro", "Menu de Cadastro", "Menu", "", "", null);
//            MenuTreeModel menuTreeModel = new MenuTreeModel(menu);
//            dmtnRoot.add(menuTreeModel);
//
//        }
        carregarMenusDB();
        expandAll(jtMenus);
        habilitaPainel(false, jpDadosMenu);
        habilitaBotoes(true);
        
        jtMenus.setSelectionPath(new TreePath(dmtnRoot.getPath()));
        
        jtMenus.addTreeSelectionListener((TreeSelectionEvent e) -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) jtMenus.getLastSelectedPathComponent();

            /* if nothing is selected */
            if (node == null) {
                return;
            }
            
            MenuMutableTreeNode dmtnSelecionado = (MenuMutableTreeNode) jtMenus.getLastSelectedPathComponent();
            
            if (dmtnSelecionado != null && dmtnSelecionado != dmtnRoot) {
                menu = dmtnSelecionado.getMenu();
                setCampos();
            }
        });
    }
    
    public void carregarParametrosCampos() {
//        csMenu.setComponenteRotulo(rMenu);
//        csRotulo.setComponenteRotulo(rRotulo);
//        csDescricao.setComponenteRotulo(rDescricao);
//        clTipo.setComponenteRotulo(rTipo);
//        csTela.setComponenteRotulo(rTela);
//        csNomeSGL.setComponenteRotulo(rNomeSGL);

        lCampos.add(csMenu);
        lCampos.add(csRotulo);
        lCampos.add(csDescricao);
        lCampos.add(clTipo);
        lCampos.add(csTela);
        lCampos.add(csNomeSGL);
    }
    
    public void carregarMenusDB() {
        try {
            dmtnRoot.removeAllChildren();

            //dtmModelo.addAllMenus(menuControl.getAll().sort((p1, p2) -> Integer.compare(p1.getOrdem(), p2.getOrdem())), dmtnRoot);
            dtmModelo.addAllMenus(menuControl.getAllPais().stream().sorted((p1, p2) -> Integer.compare(p1.getOrdem(), p2.getOrdem())).collect(Collectors.toList()), dmtnRoot);
            dtmModelo.reload();

//            if (dmtnRoot.getChildCount() > 0) {
//                dmtnRoot.sort();
//            }
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
        }
    }
    
    public void habilitaBotoes(boolean habilita) {
        jbNovoMenu.setEnabled(habilita);
        jbAlterarMenu.setEnabled(habilita);
        jbRemoverMenu.setEnabled(habilita);
        jbSalvarMenu.setEnabled(!habilita);
        jbCancelarMenu.setEnabled(!habilita);
    }
    
    public void setCampos() {
        csMenu.setString(menu.getMenu());
        csRotulo.setString(menu.getRotulo());
        csDescricao.setString(menu.getDescricao());
        clTipo.setSelectedItem(menu.getTipo());
        csPacote.setString(menu.getPacote());
        csTela.setString(menu.getTela());
        csNomeSGL.setString(menu.getNomeSGL());
        clTipoExecucao.setString(menu.getTipoExecucao());
    }
    
    public void getCampos() {
        menu.setMenu(csMenu.getString());
        menu.setRotulo(csRotulo.getString());
        menu.setDescricao(csDescricao.getString());
        menu.setTipo(clTipo.getSelectedItem().toString());
        menu.setTela(csTela.getString());
        menu.setPacote(csPacote.getString());
        menu.setNomeSGL(csNomeSGL.getString());
        menu.setTipoExecucao(clTipoExecucao.getString());
    }
    
    public void expandAll(JTree tree) {
        TreeNode root = (TreeNode) tree.getModel().getRoot();
        expandAll(tree, new TreePath(root));
    }
    
    private void expandAll(JTree tree, TreePath parent) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path);
            }
        }
        tree.expandPath(parent);
        // tree.collapsePath(parent);
    }
    
    public void alterarMenu() {
        MenuMutableTreeNode dmtnSelecionado = (MenuMutableTreeNode) jtMenus.getLastSelectedPathComponent();
        
        if (dmtnSelecionado == null) {
            jpsStatus.setStatus("Obrigatório a seleção de um menu para alteração.", jpsStatus.ALERTA);
        } else if (dmtnSelecionado == dmtnRoot) {
            jpsStatus.setStatus("Não é permitido alterar o menu principal.", jpsStatus.ALERTA);
        } else {
            statusTela = ALTERANDO;
            menu = dmtnSelecionado.getMenu();
            setCampos();
            
            habilitaPainel(true, jpDadosMenu);
            habilitaPainel(false, jpMenus);
            habilitaBotoes(false);
            jtMenus.setEnabled(false);
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

        jpMenus = new javax.swing.JPanel();
        jspMenus = new javax.swing.JScrollPane();
        jtMenus = new javax.swing.JTree();
        jbNovoMenu = new br.com.martinello.componentesbasicos.Botao();
        jbAlterarMenu = new br.com.martinello.componentesbasicos.Botao();
        jbRemoverMenu = new br.com.martinello.componentesbasicos.Botao();
        jbSubirMenu = new br.com.martinello.componentesbasicos.Botao();
        jbDescerMenu = new br.com.martinello.componentesbasicos.Botao();
        jpDadosMenu = new javax.swing.JPanel();
        rMenu = new br.com.martinello.componentesbasicos.Rotulo();
        rRotulo = new br.com.martinello.componentesbasicos.Rotulo();
        rTipo = new br.com.martinello.componentesbasicos.Rotulo();
        rDescricao = new br.com.martinello.componentesbasicos.Rotulo();
        rTela = new br.com.martinello.componentesbasicos.Rotulo();
        rNomeSGL = new br.com.martinello.componentesbasicos.Rotulo();
        csMenu = new br.com.martinello.componentesbasicos.CampoString(Menu.class, "menu");
        csRotulo = new br.com.martinello.componentesbasicos.CampoString(Menu.class, "rotulo");
        csDescricao = new br.com.martinello.componentesbasicos.CampoString(Menu.class, "descricao");
        csTela = new br.com.martinello.componentesbasicos.CampoString(Menu.class, "tela");
        csNomeSGL = new br.com.martinello.componentesbasicos.CampoString(Menu.class, "nomeSGL");
        clTipo = new br.com.martinello.componentesbasicos.CampoListaSimples(Menu.class, "tipo");
        jbSalvarMenu = new br.com.martinello.componentesbasicos.Botao();
        jbCancelarMenu = new br.com.martinello.componentesbasicos.Botao();
        rPacote = new br.com.martinello.componentesbasicos.Rotulo();
        csPacote = new br.com.martinello.componentesbasicos.CampoString(Menu.class, "tela");
        rTipoExecucao = new br.com.martinello.componentesbasicos.Rotulo();
        clTipoExecucao = new br.com.martinello.componentesbasicos.CampoListaSimples(Menu.class, "tipo");
        jpsStatus = new br.com.martinello.componentesbasicos.paineis.JPStatus();

        setClosable(true);
        setIconifiable(true);
        setTitle("Gerenciamento de Menus");

        jpMenus.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Menus", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, ConstantesGlobais.FONTE_BORDA_JPANEL));

        jtMenus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtMenusMouseClicked(evt);
            }
        });
        jspMenus.setViewportView(jtMenus);

        jbNovoMenu.setMnemonic('n');
        jbNovoMenu.setText("Novo");
        jbNovoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNovoMenuActionPerformed(evt);
            }
        });

        jbAlterarMenu.setMnemonic('a');
        jbAlterarMenu.setText("Alterar");
        jbAlterarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAlterarMenuActionPerformed(evt);
            }
        });

        jbRemoverMenu.setMnemonic('r');
        jbRemoverMenu.setText("Remover");
        jbRemoverMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverMenuActionPerformed(evt);
            }
        });

        jbSubirMenu.setMnemonic('u');
        jbSubirMenu.setText("Subir");
        jbSubirMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSubirMenuActionPerformed(evt);
            }
        });

        jbDescerMenu.setMnemonic('d');
        jbDescerMenu.setText("Descer");
        jbDescerMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDescerMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpMenusLayout = new javax.swing.GroupLayout(jpMenus);
        jpMenus.setLayout(jpMenusLayout);
        jpMenusLayout.setHorizontalGroup(
            jpMenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMenusLayout.createSequentialGroup()
                .addComponent(jspMenus, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpMenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbNovoMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAlterarMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbRemoverMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbSubirMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbDescerMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jpMenusLayout.setVerticalGroup(
            jpMenusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMenus, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
            .addGroup(jpMenusLayout.createSequentialGroup()
                .addComponent(jbNovoMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbAlterarMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbRemoverMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbSubirMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbDescerMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jpDadosMenu.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, ConstantesGlobais.FONTE_BORDA_JPANEL));

        csMenu.setComponenteRotulo(rMenu);
        csMenu.setDescricaoRotulo("Menu");
        csMenu.setObrigatorio(true);
        csMenu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                csMenuFocusLost(evt);
            }
        });

        csRotulo.setComponenteRotulo(rRotulo);
        csRotulo.setDescricaoRotulo("Rótulo");
        csRotulo.setObrigatorio(true);

        csDescricao.setComponenteRotulo(rDescricao);
        csDescricao.setDescricaoRotulo("Descrição");
        csDescricao.setObrigatorio(true);

        csTela.setComponenteRotulo(rTela);
        csTela.setDescricaoRotulo("Tela");

        csNomeSGL.setComponenteRotulo(rNomeSGL);
        csNomeSGL.setDescricaoRotulo("Nome SGL");

        clTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Menu", "ItemMenu" }));
        clTipo.setComponenteRotulo(rTipo);
        clTipo.setDescricaoRotulo("Tipo");
        clTipo.setObrigatorio(true);
        clTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                clTipoItemStateChanged(evt);
            }
        });

        jbSalvarMenu.setMnemonic('s');
        jbSalvarMenu.setText("Salvar");
        jbSalvarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalvarMenuActionPerformed(evt);
            }
        });

        jbCancelarMenu.setMnemonic('c');
        jbCancelarMenu.setText("Cancelar");
        jbCancelarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarMenuActionPerformed(evt);
            }
        });

        csPacote.setComponenteRotulo(rPacote);
        csPacote.setDescricaoRotulo("Pacote");

        clTipoExecucao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NovaInstancia", "Método" }));
        clTipoExecucao.setComponenteRotulo(rTipoExecucao);
        clTipoExecucao.setDescricaoRotulo("Tipo Exec.");
        clTipoExecucao.setObrigatorio(true);
        clTipoExecucao.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                clTipoExecucaoItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jpDadosMenuLayout = new javax.swing.GroupLayout(jpDadosMenu);
        jpDadosMenu.setLayout(jpDadosMenuLayout);
        jpDadosMenuLayout.setHorizontalGroup(
            jpDadosMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDadosMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDadosMenuLayout.createSequentialGroup()
                        .addComponent(rTela, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(csTela, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))
                    .addGroup(jpDadosMenuLayout.createSequentialGroup()
                        .addComponent(rNomeSGL, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDadosMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(csNomeSGL, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .addGroup(jpDadosMenuLayout.createSequentialGroup()
                                .addComponent(jbSalvarMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbCancelarMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jpDadosMenuLayout.createSequentialGroup()
                        .addComponent(rDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(csDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))
                    .addGroup(jpDadosMenuLayout.createSequentialGroup()
                        .addComponent(rRotulo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(csRotulo, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))
                    .addGroup(jpDadosMenuLayout.createSequentialGroup()
                        .addComponent(rMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(csMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jpDadosMenuLayout.createSequentialGroup()
                        .addComponent(rPacote, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(csPacote, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))
                    .addGroup(jpDadosMenuLayout.createSequentialGroup()
                        .addGroup(jpDadosMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpDadosMenuLayout.createSequentialGroup()
                                .addComponent(rTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpDadosMenuLayout.createSequentialGroup()
                                .addComponent(rTipoExecucao, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clTipoExecucao, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jpDadosMenuLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {rDescricao, rMenu, rNomeSGL, rRotulo, rTela, rTipo});

        jpDadosMenuLayout.setVerticalGroup(
            jpDadosMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDadosMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(csMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(csRotulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rRotulo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(csDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rTipoExecucao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clTipoExecucao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rPacote, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csPacote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rTela, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csTela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rNomeSGL, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csNomeSGL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbSalvarMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbCancelarMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(273, Short.MAX_VALUE))
        );

        jpDadosMenuLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {rDescricao, rMenu, rNomeSGL, rRotulo, rTela, rTipo});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpMenus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpDadosMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jpsStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpMenus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpDadosMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpsStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbNovoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNovoMenuActionPerformed
        
        MenuMutableTreeNode dmtnSelecionado = (MenuMutableTreeNode) jtMenus.getLastSelectedPathComponent();
        
        if (dmtnSelecionado == null) {
            jpsStatus.setStatus("Obrigatório a seleção do menu pai.", jpsStatus.ALERTA);
        } else if (dmtnSelecionado != dmtnRoot && dmtnSelecionado.getMenu().getTipo().equals("ItemMenu")) {
            jpsStatus.setStatus("Não é permitido a inserção de um item em outro item menu.", jpsStatus.ALERTA);
        } else {
            statusTela = INCLUINDO;
            
            menu = new Menu();
            menu.setMenu("jm");
            menu.setTipo("Menu");
            
            jtMenus.setEnabled(false);
            jpsStatus.limparStatus();
            
            if (jtMenus.getLastSelectedPathComponent() != null && dmtnSelecionado != dmtnRoot) {
                MenuMutableTreeNode selectedNode = (MenuMutableTreeNode) jtMenus.getLastSelectedPathComponent();
                
                menu.setMenuPai(selectedNode.getMenu());
                setCampos();
                
                habilitaPainel(true, jpDadosMenu);
                habilitaPainel(false, jpMenus);
                habilitaBotoes(false);
                
            } else {
                setCampos();
                
                habilitaPainel(true, jpDadosMenu);
                habilitaPainel(false, jpMenus);
                habilitaBotoes(false);
                
            }
        }
        

    }//GEN-LAST:event_jbNovoMenuActionPerformed

    private void jbCancelarMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarMenuActionPerformed
        habilitaBotoes(true);
        habilitaPainel(false, jpDadosMenu);
        habilitaPainel(true, jpMenus);
        jtMenus.setEnabled(true);
        
        statusTela = INDEFINIDO;
    }//GEN-LAST:event_jbCancelarMenuActionPerformed
    

    private void jbSalvarMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarMenuActionPerformed
        try {
            
            if (statusTela == INCLUINDO) {
                
                if (validarListaCampos(lCampos)) {
                    getCampos();
                    
                    MenuMutableTreeNode dmtnSelecionado = (MenuMutableTreeNode) jtMenus.getLastSelectedPathComponent();
                    if (jtMenus.getLastSelectedPathComponent() != null && dmtnSelecionado != dmtnRoot) {
                        Menu menuPai = menuControl.find(dmtnSelecionado.getMenu());
                        Comparator<Menu> comp = (p1, p2) -> Integer.compare(p1.getOrdem(), p2.getOrdem());
                        int ultimoRegistro = menuPai.getMenusFilhos().size() > 0 ? menuPai.getMenusFilhos().stream().max(comp).get().getOrdem() + 1 : 1;
                        menu.setOrdem(ultimoRegistro);
                        
                    } else {
                        try {
                            List<Menu> lMenus = menuControl.getAllPais();
                            Comparator<Menu> comp = (p1, p2) -> Integer.compare(p1.getOrdem(), p2.getOrdem());
                            int ultimoRegistro = lMenus.size() > 0 ? lMenus.stream().max(comp).get().getOrdem() + 1 : 1;
                            menu.setOrdem(ultimoRegistro);
                        } catch (ErroSistemaException ex) {
                            Logger.getLogger(TelaMenus.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    menuControl.salvar(menu);
                    
                    MenuMutableTreeNode novoMenu = new MenuMutableTreeNode(menu);
                    MenuMutableTreeNode selectedNode = (MenuMutableTreeNode) jtMenus.getLastSelectedPathComponent();
                    selectedNode.add(novoMenu);
                    
                    dtmModelo.reload(selectedNode);
                    habilitaBotoes(true);
                    habilitaPainel(false, jpDadosMenu);
                    habilitaPainel(true, jpMenus);
                    jtMenus.setEnabled(true);
                    
                    expandAll(jtMenus);
                    
                    jtMenus.setSelectionPath(new TreePath(selectedNode.getPath()));
                    statusTela = INDEFINIDO;
                }
            }
            
            if (statusTela == ALTERANDO) {
                if (validarListaCampos(lCampos)) {
                    getCampos();
                    menuControl.salvar(menu);
                    
                    MenuMutableTreeNode selectedNode = (MenuMutableTreeNode) jtMenus.getLastSelectedPathComponent();
                    selectedNode.setMenu(menu);
                    dtmModelo.reload(selectedNode);
                    
                    habilitaBotoes(true);
                    habilitaPainel(false, jpDadosMenu);
                    habilitaPainel(true, jpMenus);
                    jtMenus.setEnabled(true);
                    
                    jtMenus.setSelectionPath(new TreePath(selectedNode.getPath()));
                    statusTela = INDEFINIDO;
                }
            }
            
            if (statusTela == EXCLUINDO) {
                getCampos();
                //menuControl.remover(menu);

                if (menu.getMenuPai() == null) {
                    menuControl.remover(menu);
                } else {
                    Menu menuPai = menuControl.find(menu.getMenuPai());
                    menuPai.getMenusFilhos().remove(menu);
                    //menu.getMenuPai().getMenusFilhos().remove(menu);
                    menuControl.salvar(menuPai);
                    //menuControl.remover(menu);
                }
                
                MenuMutableTreeNode selectedNode = (MenuMutableTreeNode) jtMenus.getLastSelectedPathComponent();
                dtmModelo.removeNodeFromParent(selectedNode);
                
                jtMenus.setSelectionPath(new TreePath(dmtnRoot.getPath()));

                //dtmModelo.reload(selectedNode);
                habilitaBotoes(true);
                habilitaPainel(false, jpDadosMenu);
                habilitaPainel(true, jpMenus);
                jtMenus.setEnabled(true);
                statusTela = INDEFINIDO;
            }
            
        } catch (ErroSistemaException ex) {
            jpsStatus.setStatus("Erro ao salvar menu.", jpsStatus.ERRO, ex);
        }
    }//GEN-LAST:event_jbSalvarMenuActionPerformed

    private void jbAlterarMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarMenuActionPerformed
        alterarMenu();
    }//GEN-LAST:event_jbAlterarMenuActionPerformed

    private void jbRemoverMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverMenuActionPerformed
        MenuMutableTreeNode dmtnSelecionado = (MenuMutableTreeNode) jtMenus.getLastSelectedPathComponent();
        
        if (dmtnSelecionado == null) {
            jpsStatus.setStatus("Obrigatório a seleção de um menu para remoção.", jpsStatus.ALERTA);
        } else if (dmtnSelecionado == dmtnRoot) {
            jpsStatus.setStatus("Não é permitido remover o menu principal.", jpsStatus.ALERTA);
        } else {
            statusTela = EXCLUINDO;
            menu = dmtnSelecionado.getMenu();
            setCampos();

            //habilitaPainel(true, jpDadosMenu);
            habilitaPainel(false, jpMenus);
            habilitaBotoes(false);
            jtMenus.setEnabled(false);
        }
    }//GEN-LAST:event_jbRemoverMenuActionPerformed

    private void jbSubirMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSubirMenuActionPerformed
        MenuMutableTreeNode dmtnSelecionado = (MenuMutableTreeNode) jtMenus.getLastSelectedPathComponent();
        List<Menu> lMenusMovimentar;
        if (dmtnSelecionado == dmtnRoot) {
            jpsStatus.setStatus("Não é permitido alterar a ordem do menu principal.", jpsStatus.ALERTA);
        } else {
            Menu menuMovimentar = dmtnSelecionado.getMenu();
            if (menuMovimentar.getMenuPai() == null) {
                try {
                    lMenusMovimentar = menuControl.getAllPais();
                    menuControl.movimentaIndiceColuna(MenuControl.SUBIR, lMenusMovimentar, dmtnSelecionado.getMenu());
                    
                    carregarMenusDB();
                    
                    jtMenus.setSelectionPath(new TreePath(dmtnRoot.searchNode(menuMovimentar).getPath()));
                } catch (ErroSistemaException | ValidacaoException ex) {
                    jpsStatus.setStatus("Não foi possível realizar a operação.", jpsStatus.ERRO, ex);
                }
                
            } else {
                try {
                    lMenusMovimentar = menuMovimentar.getMenuPai().getMenusFilhos();
                    menuControl.movimentaIndiceColuna(MenuControl.SUBIR, lMenusMovimentar, dmtnSelecionado.getMenu());
                    
                    carregarMenusDB();
                    
                    jtMenus.setSelectionPath(new TreePath(dmtnRoot.searchNode(menuMovimentar).getPath()));
                } catch (ErroSistemaException | ValidacaoException ex) {
                    jpsStatus.setStatus("Não foi possível realizar a operação.", jpsStatus.ERRO, ex);
                }
            }
        }

    }//GEN-LAST:event_jbSubirMenuActionPerformed

    private void jbDescerMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDescerMenuActionPerformed
        MenuMutableTreeNode dmtnSelecionado = (MenuMutableTreeNode) jtMenus.getLastSelectedPathComponent();
        List<Menu> lMenusMovimentar;
        if (dmtnSelecionado == dmtnRoot) {
            jpsStatus.setStatus("Não é permitido alterar a ordem do menu principal.", jpsStatus.ALERTA);
        } else {
            Menu menuMovimentar = dmtnSelecionado.getMenu();
            if (menuMovimentar.getMenuPai() == null) {
                try {
                    lMenusMovimentar = menuControl.getAllPais();
                    menuControl.movimentaIndiceColuna(MenuControl.BAIXAR, lMenusMovimentar, dmtnSelecionado.getMenu());
                    
                    carregarMenusDB();
                    
                    jtMenus.setSelectionPath(new TreePath(dmtnRoot.searchNode(menuMovimentar).getPath()));
                } catch (ErroSistemaException | ValidacaoException ex) {
                    jpsStatus.setStatus("Não foi possível realizar a operação.", jpsStatus.ERRO, ex);
                }
                
            } else {
                try {
                    lMenusMovimentar = menuMovimentar.getMenuPai().getMenusFilhos();
                    menuControl.movimentaIndiceColuna(MenuControl.BAIXAR, lMenusMovimentar, dmtnSelecionado.getMenu());
                    
                    carregarMenusDB();
                    
                    jtMenus.setSelectionPath(new TreePath(dmtnRoot.searchNode(menuMovimentar).getPath()));
                } catch (ErroSistemaException | ValidacaoException ex) {
                    jpsStatus.setStatus("Não foi possível realizar a operação.", jpsStatus.ERRO, ex);
                }
            }
        }
        

    }//GEN-LAST:event_jbDescerMenuActionPerformed
    
    public DefaultMutableTreeNode searchNode(Object nodeStr, DefaultMutableTreeNode top) {
        DefaultMutableTreeNode node;
        Enumeration<?> e = top.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            node = (DefaultMutableTreeNode) e.nextElement();
            if (nodeStr.equals(node.getUserObject())) {
                return node;
            }
        }
        return null;
    }
    
    public DefaultMutableTreeNode searchNode(Menu menu, DefaultMutableTreeNode top) {
        DefaultMutableTreeNode node;
        Enumeration<?> e = top.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            node = (DefaultMutableTreeNode) e.nextElement();
            if (menu.equals(((MenuMutableTreeNode) node).getMenu())) {
                return node;
            }
        }
        return null;
    }

    private void csMenuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_csMenuFocusLost
        if (csMenu.getString().length() > 0) {
            if (csRotulo.getString().length() == 0) {
                csRotulo.setString(csMenu.getString().replace("jmi", "").replace("jm", ""));
                csDescricao.setString(csMenu.getString().replace("jmi", "").replace("jm", ""));
            }
//            String textoNovo = Normalizer.normalize(csColunaColuna.getString(), Normalizer.Form.NFD);
//            textoNovo = textoNovo.replaceAll("[^\\p{ASCII}]", "").replaceAll(" ", "_").replace(".", "");
//            csColunaColuna.setString(textoNovo);

        }
    }//GEN-LAST:event_csMenuFocusLost

    private void clTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_clTipoItemStateChanged
        if (csMenu.getString().toUpperCase().equals("JM") || csMenu.getString().toUpperCase().equals("JMI")) {
            if (clTipo.getSelectedItem().toString().equals("Menu")) {
                csMenu.setString("jm");
            } else {
                csMenu.setString("jmi");
            }
        }
    }//GEN-LAST:event_clTipoItemStateChanged

    private void jtMenusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtMenusMouseClicked
        if (evt.getClickCount() == 2) {
            alterarMenu();
        }
    }//GEN-LAST:event_jtMenusMouseClicked

    private void clTipoExecucaoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_clTipoExecucaoItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_clTipoExecucaoItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.componentesbasicos.CampoListaSimples clTipo;
    private br.com.martinello.componentesbasicos.CampoListaSimples clTipoExecucao;
    private br.com.martinello.componentesbasicos.CampoString csDescricao;
    private br.com.martinello.componentesbasicos.CampoString csMenu;
    private br.com.martinello.componentesbasicos.CampoString csNomeSGL;
    private br.com.martinello.componentesbasicos.CampoString csPacote;
    private br.com.martinello.componentesbasicos.CampoString csRotulo;
    private br.com.martinello.componentesbasicos.CampoString csTela;
    private br.com.martinello.componentesbasicos.Botao jbAlterarMenu;
    private br.com.martinello.componentesbasicos.Botao jbCancelarMenu;
    private br.com.martinello.componentesbasicos.Botao jbDescerMenu;
    private br.com.martinello.componentesbasicos.Botao jbNovoMenu;
    private br.com.martinello.componentesbasicos.Botao jbRemoverMenu;
    private br.com.martinello.componentesbasicos.Botao jbSalvarMenu;
    private br.com.martinello.componentesbasicos.Botao jbSubirMenu;
    private javax.swing.JPanel jpDadosMenu;
    private javax.swing.JPanel jpMenus;
    private br.com.martinello.componentesbasicos.paineis.JPStatus jpsStatus;
    private javax.swing.JScrollPane jspMenus;
    private javax.swing.JTree jtMenus;
    private br.com.martinello.componentesbasicos.Rotulo rDescricao;
    private br.com.martinello.componentesbasicos.Rotulo rMenu;
    private br.com.martinello.componentesbasicos.Rotulo rNomeSGL;
    private br.com.martinello.componentesbasicos.Rotulo rPacote;
    private br.com.martinello.componentesbasicos.Rotulo rRotulo;
    private br.com.martinello.componentesbasicos.Rotulo rTela;
    private br.com.martinello.componentesbasicos.Rotulo rTipo;
    private br.com.martinello.componentesbasicos.Rotulo rTipoExecucao;
    // End of variables declaration//GEN-END:variables
}
