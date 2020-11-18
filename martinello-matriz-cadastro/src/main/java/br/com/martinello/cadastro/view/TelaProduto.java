/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.cadastro.view;

import br.com.martinello.matriz.bd.control.ArquivoControl;
import br.com.martinello.matriz.bd.control.CaracteristicaControl;
import br.com.martinello.matriz.bd.control.CategoriaControl;
import br.com.martinello.matriz.bd.control.ParametrosControl;
import br.com.martinello.matriz.bd.control.ProdutoControl;
import br.com.martinello.matriz.bd.control.SubCategoriaControl;
import br.com.martinello.matriz.bd.integracao.control.PendenciaControlInt;
import br.com.martinello.matriz.bd.integracao.model.domain.Pendencia;
import br.com.martinello.matriz.bd.model.domain.Arquivo;
import br.com.martinello.matriz.bd.model.domain.Caracteristica;
import br.com.martinello.matriz.bd.model.domain.Categoria;
import br.com.martinello.matriz.bd.model.domain.Parametro;
import br.com.martinello.matriz.bd.model.domain.Produto;
import br.com.martinello.matriz.bd.model.domain.SubCategoria;
import br.com.martinello.matriz.bd.model.domain.Usuario;
import br.com.martinello.matriz.bd.util.AsynchronousRecursiveDirectoryStream;
import br.com.martinello.matriz.bd.util.DirUtils;
import br.com.martinello.matriz.componentesbasicos.CampoListaSimples;
import br.com.martinello.matriz.componentesbasicos.paineis.JPStatus;
import br.com.martinello.matriz.componentesbasicos.view.TelaPadrao;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import br.com.martinello.matriz.util.filtro.Filtro;
import com.towel.swing.table.ObjectTableModel;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.CellEditor;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author luiz.almeida
 */
public class TelaProduto extends TelaPadrao {

    /**
     * Creates new form TelaCategoria
     */
    private DirUtils dir;
    private AsynchronousRecursiveDirectoryStream stream;
    private JColorChooser escolherCor = new JColorChooser();
    private Color cor = null;
    private BufferedImage img;
    private JFileChooser chooser;
    private File directory;
    private String choosertitle;
    private String cat;
    private String codigo;
    private String salvarAlterar = "Salvar";
    private SubCategoria filtroSubCategoria;
    private Produto filtroProduto;
    private Produto produto;
    private SubCategoria subCategoria;
    private Categoria categoria, filtroCategoria;
    private Caracteristica caracteristica;
    private Arquivo arquivos;
    private Parametro parametro;
    private ParametrosControl parametroControl;
    private ProdutoControl produtoControl;
    private CaracteristicaControl caracteristicaControl;
    private SubCategoriaControl subCategoriaControl;
    private CategoriaControl categoriaControl;
    private ArquivoControl arquivoControl;
    private List<Caracteristica> lCaracteristicas;
    private List<SubCategoria> lSubCategoria;
    private List<Produto> lProdutos;
    private List<Arquivo> lArquivo;
    private List<Produto> lProdutosCaracteristicas;
    private List<Parametro> lParametro;
    private boolean resultado;
    private static String catSelect, dirArqOrigem = "", dirArqDestino;
    private static List<Categoria> lCategoria;
    private static Usuario usuario = TelaPrincipal.usuario;

    private final ObjectTableModel<Arquivo> otmArquivo
            = new ObjectTableModel<>(Arquivo.class, "sequencia,nome,novoNome,extencao,principal,visivel,alterado");

    private final ObjectTableModel<Produto> otmProduto
            = new ObjectTableModel<>(Produto.class, "codigo,descricao,idCategoria,categoria,idSubCategoria,subCategoria,situacao,observacao");

    private final ObjectTableModel<Produto> otmCaracteristicas
            = new ObjectTableModel<>(Produto.class, "idCaracteristica,caracteristica,idCaracteristicaItem,caracteristicaItem,idDescCaractItem,descCaracteristica,sequencia,situacao,visivel");

    private final ObjectTableModel<Produto> otmCaracteristicaProduto
            = new ObjectTableModel<>(Produto.class, "caracteristica,caracteristicaItem,descCaracteristica,sequencia,situacao,visivel");

    private final ObjectTableModel<Categoria> otmCatalogo
            = new ObjectTableModel<>(Categoria.class, "idCategoria,idCategoriaPai,categoriaPai,categoria,situacao,visivel");

    private final ObjectTableModel<Categoria> otmCatalogoItem
            = new ObjectTableModel<>(Categoria.class, "codPro,idCategoria,idCategoriaPai,categoriaPai,categoria,situacao,visivel,codEcommerce");

    public TelaProduto() {
        initComponents();
        cscFiltroProduto.getClsTipoFiltro().setSelectedIndex(1);
        carregarCategorias("Consulta");
        ppCadastro.setVisible(false);
        paProduto.setEnabledAt(1, false);
        otmCaracteristicaProduto.setColEditable(2, true);
        otmCaracteristicaProduto.setColEditable(4, true);
        otmCaracteristicaProduto.setColEditable(5, true);
        otmArquivo.setColEditable(2, true);
        otmArquivo.setColEditable(4, true);
        otmArquivo.setColEditable(5, true);
        otmCatalogoItem.setColEditable(5, true);
        //  ccpProduto.setFiltroSituacao("Consulta");
        tpConsultaProduto.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }
            if (tpConsultaProduto.getSelectedRow() >= 0) {
                setDadosCaracteristicas(otmProduto.getValue(tpConsultaProduto.getLinhaSelecionada()).getCodigo());
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

        jpsProduto = new br.com.martinello.matriz.componentesbasicos.paineis.JPStatus();
        paProduto = new br.com.martinello.matriz.componentesbasicos.paineis.PainelAba();
        ppConsultaProduto = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        paCaracteristica = new br.com.martinello.matriz.componentesbasicos.paineis.PainelAba();
        ppSubTabCaracteristicas = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        jScrollPane1 = new javax.swing.JScrollPane();
        tpCaracteristicas = new br.com.martinello.matriz.componentesbasicos.TabelaPadrao();
        ppSubCarItens = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        ppFiltroProduto = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        rIdProdutoFiltro = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        btPesquisarProduto = new br.com.martinello.matriz.componentesbasicos.Botao();
        btAlterarProduto = new br.com.martinello.matriz.componentesbasicos.Botao();
        btIncluirProduto = new br.com.martinello.matriz.componentesbasicos.Botao();
        btExcluirProduto = new br.com.martinello.matriz.componentesbasicos.Botao();
        rSubCategoriaFiltro = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        clsSubCatFiltro = new br.com.martinello.matriz.componentesbasicos.CampoListaSimples();
        rCategoriaFiltro = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        clsCategoriaFiltro = new br.com.martinello.matriz.componentesbasicos.CampoListaSimples();
        cscFiltroProduto = new br.com.martinello.matriz.componentesbasicos.consulta.CampoStringConsulta();
        rProduto = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        csCodigo = new br.com.martinello.matriz.componentesbasicos.CampoString();
        btRelatorio = new br.com.martinello.matriz.componentesbasicos.Botao();
        ppTabConsultaProduto = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        jspTabConsultaProduto = new javax.swing.JScrollPane();
        tpConsultaProduto = new br.com.martinello.matriz.componentesbasicos.TabelaPadrao();
        ppQtdProduto = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        rQtdProduto = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rQtdProdutoValor = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        ppCadastro = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        ppCadastroProduto = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        rDescProCad = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rProdutoCadastro = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        clsCatCad = new br.com.martinello.matriz.componentesbasicos.CampoListaSimples();
        rCatCad = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rSubCatCad = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        clsSubCatCad = new br.com.martinello.matriz.componentesbasicos.CampoListaSimples();
        btCancelarSubCat = new br.com.martinello.matriz.componentesbasicos.Botao();
        btSalvarSubCat = new br.com.martinello.matriz.componentesbasicos.Botao();
        ccpProdutoIncluir = new br.com.martinello.cadastro.componentes.consulta.CampoConsultaProduto();
        rStatusProduto = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        clsSituacaoProduto = new br.com.martinello.matriz.componentesbasicos.CampoListaSimples();
        rVisivelProduto = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        clsVisivelProduto = new br.com.martinello.matriz.componentesbasicos.CampoListaSimples();
        btAlterarCadPro = new br.com.martinello.matriz.componentesbasicos.Botao();
        btExcluirAnexo = new br.com.martinello.matriz.componentesbasicos.Botao();
        ppCadastroGeral = new br.com.martinello.matriz.componentesbasicos.paineis.PainelAba();
        ppDescricao = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        painelEstilo = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        rDescricaoLonga = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rNome = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        csNomeProduto = new br.com.martinello.matriz.componentesbasicos.CampoString();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaDeTexto = new javax.swing.JTextPane();
        ppCadastroCaracteristicas = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        jspInformacoes = new javax.swing.JScrollPane();
        tpCadCar = new br.com.martinello.matriz.componentesbasicos.TabelaPadrao();
        ppAnexos = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        ppFiltroAnexos = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        rSelecionar = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        btSalvarAnexo = new br.com.martinello.matriz.componentesbasicos.Botao();
        ccdDiretorio = new br.com.martinello.cadastro.componentes.consulta.campoConsultaDiretorio();
        bVizualizarAnexo = new br.com.martinello.matriz.componentesbasicos.Botao();
        btExcluirAnexo1 = new br.com.martinello.matriz.componentesbasicos.Botao();
        ppTbAnexos = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        jspTbAnexos = new javax.swing.JScrollPane();
        tpAnexos = new br.com.martinello.matriz.componentesbasicos.TabelaPadrao();
        painelPadrao1 = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        ppFiltroRelacionamento = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        ciCodCatPaiCatalogo = new br.com.martinello.matriz.componentesbasicos.CampoInteiro();
        ciCodCatCatalogo = new br.com.martinello.matriz.componentesbasicos.CampoInteiro();
        rCodCatPaiCatalogo = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rCatCatalogo = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        rCatItem = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        cscFiltroCat = new br.com.martinello.matriz.componentesbasicos.consulta.CampoStringConsulta();
        rCatPai = new br.com.martinello.matriz.componentesbasicos.Rotulo();
        cscFiltroCatPai = new br.com.martinello.matriz.componentesbasicos.consulta.CampoStringConsulta();
        botao3 = new br.com.martinello.matriz.componentesbasicos.Botao();
        ppTrafael = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        ppCatalogoCad = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        jScrollPane4 = new javax.swing.JScrollPane();
        tpCatalogoItem = new br.com.martinello.matriz.componentesbasicos.TabelaPadrao();
        ppCatalogo = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        jScrollPane3 = new javax.swing.JScrollPane();
        tpCatalogo = new br.com.martinello.matriz.componentesbasicos.TabelaPadrao();
        botao1 = new br.com.martinello.matriz.componentesbasicos.Botao();
        botao2 = new br.com.martinello.matriz.componentesbasicos.Botao();
        ppBotaoCadCar = new br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao();
        btCancelarCaracteristica = new br.com.martinello.matriz.componentesbasicos.Botao();
        btSalvarCaracteristica = new br.com.martinello.matriz.componentesbasicos.Botao();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Produto");
        setMaximumSize(new java.awt.Dimension(1600, 900));
        setPreferredSize(new java.awt.Dimension(1600, 900));
        getContentPane().add(jpsProduto, java.awt.BorderLayout.SOUTH);

        paProduto.setToolTipText("");

        ppConsultaProduto.setPreferredSize(new java.awt.Dimension(1029, 800));
        ppConsultaProduto.setLayout(new java.awt.BorderLayout());

        paCaracteristica.setPreferredSize(new java.awt.Dimension(1300, 250));

        ppSubTabCaracteristicas.setPreferredSize(new java.awt.Dimension(600, 360));
        ppSubTabCaracteristicas.setLayout(new java.awt.BorderLayout());

        tpCaracteristicas.setModel(otmCaracteristicas);
        jScrollPane1.setViewportView(tpCaracteristicas);

        ppSubTabCaracteristicas.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        paCaracteristica.addTab("Caracteristicas", ppSubTabCaracteristicas);

        ppSubCarItens.setPreferredSize(new java.awt.Dimension(600, 360));
        ppSubCarItens.setLayout(new java.awt.BorderLayout());
        paCaracteristica.addTab("Volumes", ppSubCarItens);

        ppConsultaProduto.add(paCaracteristica, java.awt.BorderLayout.SOUTH);

        ppFiltroProduto.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros da pesquisa"));
        ppFiltroProduto.setMinimumSize(new java.awt.Dimension(1029, 120));
        ppFiltroProduto.setPreferredSize(new java.awt.Dimension(1029, 140));

        rIdProdutoFiltro.setText("Código:");

        btPesquisarProduto.setText("Pesquisar");
        btPesquisarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarProdutoActionPerformed(evt);
            }
        });

        btAlterarProduto.setText("Alterar");
        btAlterarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarProdutoActionPerformed(evt);
            }
        });

        btIncluirProduto.setText("Incluir");
        btIncluirProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIncluirProdutoActionPerformed(evt);
            }
        });

        btExcluirProduto.setText("Excluir");
        btExcluirProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirProdutoActionPerformed(evt);
            }
        });

        rSubCategoriaFiltro.setText("Categoria Item:");

        rCategoriaFiltro.setText("Categoria:");

        clsCategoriaFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clsCategoriaFiltroActionPerformed(evt);
            }
        });

        cscFiltroProduto.setaTipoFiltro(null);
        cscFiltroProduto.setaValorFiltro("");

        rProduto.setText("Produto:");

        btRelatorio.setText("Imprimir");
        btRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRelatorioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppFiltroProdutoLayout = new javax.swing.GroupLayout(ppFiltroProduto);
        ppFiltroProduto.setLayout(ppFiltroProdutoLayout);
        ppFiltroProdutoLayout.setHorizontalGroup(
            ppFiltroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFiltroProdutoLayout.createSequentialGroup()
                        .addComponent(rSubCategoriaFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clsSubCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 510, Short.MAX_VALUE)
                        .addComponent(btPesquisarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ppFiltroProdutoLayout.createSequentialGroup()
                        .addGroup(ppFiltroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppFiltroProdutoLayout.createSequentialGroup()
                                .addComponent(rIdProdutoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(csCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ppFiltroProdutoLayout.createSequentialGroup()
                                .addComponent(rCategoriaFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clsCategoriaFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ppFiltroProdutoLayout.createSequentialGroup()
                        .addComponent(btRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btExcluirProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ppFiltroProdutoLayout.createSequentialGroup()
                        .addComponent(btIncluirProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAlterarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
            .addGroup(ppFiltroProdutoLayout.createSequentialGroup()
                .addComponent(rProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cscFiltroProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ppFiltroProdutoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btAlterarProduto, btExcluirProduto, btIncluirProduto, btPesquisarProduto, btRelatorio});

        ppFiltroProdutoLayout.setVerticalGroup(
            ppFiltroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroProdutoLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(ppFiltroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btPesquisarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btIncluirProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAlterarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btExcluirProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rCategoriaFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsCategoriaFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(ppFiltroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rSubCategoriaFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsSubCatFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rIdProdutoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cscFiltroProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        ppFiltroProdutoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btAlterarProduto, btExcluirProduto, btIncluirProduto, btPesquisarProduto, btRelatorio});

        ppConsultaProduto.add(ppFiltroProduto, java.awt.BorderLayout.NORTH);

        ppTabConsultaProduto.setMaximumSize(new java.awt.Dimension(1900, 400));
        ppTabConsultaProduto.setMinimumSize(new java.awt.Dimension(1029, 120));
        ppTabConsultaProduto.setPreferredSize(new java.awt.Dimension(1029, 280));
        ppTabConsultaProduto.setLayout(new java.awt.BorderLayout());

        jspTabConsultaProduto.setPreferredSize(new java.awt.Dimension(2, 280));

        tpConsultaProduto.setModel(otmProduto);
        tpConsultaProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpConsultaProdutoKeyPressed(evt);
            }
        });
        jspTabConsultaProduto.setViewportView(tpConsultaProduto);

        ppTabConsultaProduto.add(jspTabConsultaProduto, java.awt.BorderLayout.CENTER);

        ppQtdProduto.setPreferredSize(new java.awt.Dimension(1279, 25));

        rQtdProduto.setText("Quantidade:");
        rQtdProduto.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

        rQtdProdutoValor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rQtdProdutoValor.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        javax.swing.GroupLayout ppQtdProdutoLayout = new javax.swing.GroupLayout(ppQtdProduto);
        ppQtdProduto.setLayout(ppQtdProdutoLayout);
        ppQtdProdutoLayout.setHorizontalGroup(
            ppQtdProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppQtdProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rQtdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rQtdProdutoValor, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1086, Short.MAX_VALUE))
        );
        ppQtdProdutoLayout.setVerticalGroup(
            ppQtdProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppQtdProdutoLayout.createSequentialGroup()
                .addGroup(ppQtdProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rQtdProdutoValor, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rQtdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 4, Short.MAX_VALUE))
        );

        ppTabConsultaProduto.add(ppQtdProduto, java.awt.BorderLayout.PAGE_END);

        ppConsultaProduto.add(ppTabConsultaProduto, java.awt.BorderLayout.CENTER);

        paProduto.addTab("Consulta", ppConsultaProduto);

        ppCadastro.setMaximumSize(new java.awt.Dimension(1024, 500));
        ppCadastro.setLayout(new java.awt.BorderLayout());

        ppCadastroProduto.setBorder(javax.swing.BorderFactory.createTitledBorder("Produto Cadastro"));
        ppCadastroProduto.setPreferredSize(new java.awt.Dimension(150, 150));

        rDescProCad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        clsCatCad.setComponenteRotulo(rCatCad);
        clsCatCad.setDescricaoRotulo("Categoria");
        clsCatCad.setObrigatorio(true);
        clsCatCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clsCatCadActionPerformed(evt);
            }
        });

        clsSubCatCad.setComponenteRotulo(rSubCatCad);
        clsSubCatCad.setDescricaoRotulo("SubCategoria");
        clsSubCatCad.setObrigatorio(true);
        clsSubCatCad.setOpaque(false);
        clsSubCatCad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                clsSubCatCadFocusLost(evt);
            }
        });

        btCancelarSubCat.setText("Cancelar");
        btCancelarSubCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarSubCatActionPerformed(evt);
            }
        });

        btSalvarSubCat.setText("Salvar");
        btSalvarSubCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarSubCatActionPerformed(evt);
            }
        });

        ccpProdutoIncluir.setComponenteRotulo(rProdutoCadastro);
        ccpProdutoIncluir.setDescricaoRotulo("Produto");
        ccpProdutoIncluir.setFiltroSituacao("Incluir");
        ccpProdutoIncluir.setOrigem("Produto");
        ccpProdutoIncluir.setrDescricaoProduto(rDescProCad);

        clsSituacaoProduto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ativo", "Inativo", " " }));
        clsSituacaoProduto.setComponenteRotulo(rStatusProduto);
        clsSituacaoProduto.setDescricaoRotulo("Situação");
        clsSituacaoProduto.setObrigatorio(true);
        clsSituacaoProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clsSituacaoProdutoActionPerformed(evt);
            }
        });

        clsVisivelProduto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sim", "Não", "" }));
        clsVisivelProduto.setComponenteRotulo(rVisivelProduto);
        clsVisivelProduto.setDescricaoRotulo("Visível");
        clsVisivelProduto.setObrigatorio(true);

        btAlterarCadPro.setText("Alterar");
        btAlterarCadPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarCadProActionPerformed(evt);
            }
        });

        btExcluirAnexo.setText("Excluir");
        btExcluirAnexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirAnexoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppCadastroProdutoLayout = new javax.swing.GroupLayout(ppCadastroProduto);
        ppCadastroProduto.setLayout(ppCadastroProdutoLayout);
        ppCadastroProdutoLayout.setHorizontalGroup(
            ppCadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppCadastroProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppCadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppCadastroProdutoLayout.createSequentialGroup()
                        .addGroup(ppCadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppCadastroProdutoLayout.createSequentialGroup()
                                .addComponent(rSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clsSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 764, Short.MAX_VALUE))
                            .addGroup(ppCadastroProdutoLayout.createSequentialGroup()
                                .addComponent(rProdutoCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ccpProdutoIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rDescProCad, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addComponent(btCancelarSubCat, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btAlterarCadPro, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btExcluirAnexo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btSalvarSubCat, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ppCadastroProdutoLayout.createSequentialGroup()
                        .addGroup(ppCadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppCadastroProdutoLayout.createSequentialGroup()
                                .addComponent(rCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clsCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ppCadastroProdutoLayout.createSequentialGroup()
                                .addComponent(rStatusProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clsSituacaoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rVisivelProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clsVisivelProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        ppCadastroProdutoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btAlterarCadPro, btCancelarSubCat, btExcluirAnexo, btSalvarSubCat});

        ppCadastroProdutoLayout.setVerticalGroup(
            ppCadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppCadastroProdutoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppCadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppCadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clsSubCatCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(ppCadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppCadastroProdutoLayout.createSequentialGroup()
                        .addGroup(ppCadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rProdutoCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ccpProdutoIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppCadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ppCadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(clsVisivelProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(rVisivelProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ppCadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rStatusProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(clsSituacaoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(ppCadastroProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(btCancelarSubCat, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btAlterarCadPro, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btExcluirAnexo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btSalvarSubCat, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(rDescProCad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ppCadastroProdutoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btAlterarCadPro, btCancelarSubCat, btExcluirAnexo, btSalvarSubCat});

        ppCadastro.add(ppCadastroProduto, java.awt.BorderLayout.PAGE_START);

        ppDescricao.setLayout(new java.awt.BorderLayout());

        painelEstilo.setPreferredSize(new java.awt.Dimension(1274, 60));

        rDescricaoLonga.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rDescricaoLonga.setText("Descrição longa para o produto");
        rDescricaoLonga.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        rDescricaoLonga.setPreferredSize(new java.awt.Dimension(184, 21));

        csNomeProduto.setComponenteRotulo(rNome);
        csNomeProduto.setDescricaoRotulo("Nome");
        csNomeProduto.setName(""); // NOI18N
        csNomeProduto.setObrigatorio(true);

        javax.swing.GroupLayout painelEstiloLayout = new javax.swing.GroupLayout(painelEstilo);
        painelEstilo.setLayout(painelEstiloLayout);
        painelEstiloLayout.setHorizontalGroup(
            painelEstiloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEstiloLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelEstiloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rDescricaoLonga, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelEstiloLayout.createSequentialGroup()
                        .addComponent(rNome, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(csNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(530, Short.MAX_VALUE))
        );
        painelEstiloLayout.setVerticalGroup(
            painelEstiloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelEstiloLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelEstiloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(csNomeProduto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rNome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rDescricaoLonga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        ppDescricao.add(painelEstilo, java.awt.BorderLayout.PAGE_START);

        jScrollPane2.setViewportView(areaDeTexto);

        ppDescricao.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        ppCadastroGeral.addTab("Dados Gerais", ppDescricao);

        ppCadastroCaracteristicas.setLayout(new java.awt.BorderLayout());

        jspInformacoes.setAlignmentX(2.0F);

        tpCadCar.setModel(otmCaracteristicaProduto);
        tpCadCar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tpCadCarFocusLost(evt);
            }
        });
        tpCadCar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tpCadCarMouseClicked(evt);
            }
        });
        jspInformacoes.setViewportView(tpCadCar);

        ppCadastroCaracteristicas.add(jspInformacoes, java.awt.BorderLayout.CENTER);

        ppCadastroGeral.addTab("Descrição", ppCadastroCaracteristicas);

        ppAnexos.setLayout(new java.awt.BorderLayout());

        ppFiltroAnexos.setPreferredSize(new java.awt.Dimension(1024, 50));

        rSelecionar.setText("Selecionar Arquivo:");

        btSalvarAnexo.setText("Adicionar");
        btSalvarAnexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarAnexoActionPerformed(evt);
            }
        });

        bVizualizarAnexo.setText("Vizualizar");
        bVizualizarAnexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bVizualizarAnexoActionPerformed(evt);
            }
        });

        btExcluirAnexo1.setText("Excluir");
        btExcluirAnexo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirAnexo1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppFiltroAnexosLayout = new javax.swing.GroupLayout(ppFiltroAnexos);
        ppFiltroAnexos.setLayout(ppFiltroAnexosLayout);
        ppFiltroAnexosLayout.setHorizontalGroup(
            ppFiltroAnexosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroAnexosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rSelecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ccdDiretorio, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btSalvarAnexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 394, Short.MAX_VALUE)
                .addComponent(btExcluirAnexo1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bVizualizarAnexo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        ppFiltroAnexosLayout.setVerticalGroup(
            ppFiltroAnexosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroAnexosLayout.createSequentialGroup()
                .addGroup(ppFiltroAnexosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppFiltroAnexosLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(ppFiltroAnexosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroAnexosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btSalvarAnexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bVizualizarAnexo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btExcluirAnexo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(rSelecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroAnexosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ccdDiretorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ppAnexos.add(ppFiltroAnexos, java.awt.BorderLayout.PAGE_START);

        ppTbAnexos.setLayout(new java.awt.BorderLayout());

        jspTbAnexos.setPreferredSize(new java.awt.Dimension(302, 300));

        tpAnexos.setModel(otmArquivo);
        tpAnexos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tpAnexosFocusLost(evt);
            }
        });
        tpAnexos.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tpAnexosPropertyChange(evt);
            }
        });
        tpAnexos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tpAnexosKeyPressed(evt);
            }
        });
        jspTbAnexos.setViewportView(tpAnexos);

        ppTbAnexos.add(jspTbAnexos, java.awt.BorderLayout.CENTER);

        ppAnexos.add(ppTbAnexos, java.awt.BorderLayout.CENTER);

        ppCadastroGeral.addTab("Anexos", ppAnexos);

        painelPadrao1.setLayout(new java.awt.BorderLayout());

        ppFiltroRelacionamento.setPreferredSize(new java.awt.Dimension(450, 100));

        ciCodCatPaiCatalogo.setText("null");
        ciCodCatPaiCatalogo.setComponenteRotulo(rCodCatPaiCatalogo);
        ciCodCatPaiCatalogo.setDescricaoRotulo("Código Categoria Pai");
        ciCodCatPaiCatalogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ciCodCatPaiCatalogoActionPerformed(evt);
            }
        });

        ciCodCatCatalogo.setText("null");
        ciCodCatCatalogo.setComponenteRotulo(rCatCatalogo);
        ciCodCatCatalogo.setDescricaoRotulo("Código Categoria");

        rCatItem.setText("Categoria:");

        cscFiltroCat.setaTipoFiltro(null);
        cscFiltroCat.setaValorFiltro("");

        rCatPai.setText("Categoria Pai:");

        cscFiltroCatPai.setaTipoFiltro(null);
        cscFiltroCatPai.setaValorFiltro("");

        botao3.setText("Pesquisar");
        botao3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppFiltroRelacionamentoLayout = new javax.swing.GroupLayout(ppFiltroRelacionamento);
        ppFiltroRelacionamento.setLayout(ppFiltroRelacionamentoLayout);
        ppFiltroRelacionamentoLayout.setHorizontalGroup(
            ppFiltroRelacionamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppFiltroRelacionamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroRelacionamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(ppFiltroRelacionamentoLayout.createSequentialGroup()
                        .addComponent(rCatPai, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cscFiltroCatPai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(ppFiltroRelacionamentoLayout.createSequentialGroup()
                        .addComponent(rCodCatPaiCatalogo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ciCodCatPaiCatalogo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rCatCatalogo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ciCodCatCatalogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(ppFiltroRelacionamentoLayout.createSequentialGroup()
                        .addComponent(rCatItem, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cscFiltroCat, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(botao3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(607, Short.MAX_VALUE))
        );
        ppFiltroRelacionamentoLayout.setVerticalGroup(
            ppFiltroRelacionamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppFiltroRelacionamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppFiltroRelacionamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rCodCatPaiCatalogo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ciCodCatPaiCatalogo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ciCodCatCatalogo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rCatCatalogo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroRelacionamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cscFiltroCatPai, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rCatPai, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppFiltroRelacionamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cscFiltroCat, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rCatItem, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        painelPadrao1.add(ppFiltroRelacionamento, java.awt.BorderLayout.NORTH);

        ppTrafael.setLayout(new java.awt.BorderLayout());

        ppCatalogoCad.setBorder(javax.swing.BorderFactory.createTitledBorder("Categoria Produto"));
        ppCatalogoCad.setToolTipText("");
        ppCatalogoCad.setPreferredSize(new java.awt.Dimension(550, 491));
        ppCatalogoCad.setLayout(new java.awt.BorderLayout());

        tpCatalogoItem.setModel(otmCatalogoItem);
        tpCatalogoItem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tpCatalogoItemFocusLost(evt);
            }
        });
        jScrollPane4.setViewportView(tpCatalogoItem);

        ppCatalogoCad.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        ppTrafael.add(ppCatalogoCad, java.awt.BorderLayout.CENTER);

        ppCatalogo.setBorder(javax.swing.BorderFactory.createTitledBorder("Categoria Geral"));
        ppCatalogo.setPreferredSize(new java.awt.Dimension(750, 34));

        tpCatalogo.setModel(otmCatalogo);
        jScrollPane3.setViewportView(tpCatalogo);

        botao1.setText("Adicionar");
        botao1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao1ActionPerformed(evt);
            }
        });

        botao2.setText("Remover");
        botao2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ppCatalogoLayout = new javax.swing.GroupLayout(ppCatalogo);
        ppCatalogo.setLayout(ppCatalogoLayout);
        ppCatalogoLayout.setHorizontalGroup(
            ppCatalogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppCatalogoLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppCatalogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botao1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botao2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        ppCatalogoLayout.setVerticalGroup(
            ppCatalogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppCatalogoLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(botao1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botao2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
        );

        ppTrafael.add(ppCatalogo, java.awt.BorderLayout.WEST);

        painelPadrao1.add(ppTrafael, java.awt.BorderLayout.CENTER);

        ppCadastroGeral.addTab("Relacionamento", painelPadrao1);

        ppCadastro.add(ppCadastroGeral, java.awt.BorderLayout.CENTER);

        btCancelarCaracteristica.setText("Cancelar");
        btCancelarCaracteristica.setPreferredSize(new java.awt.Dimension(100, 25));
        btCancelarCaracteristica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarCaracteristicaActionPerformed(evt);
            }
        });
        ppBotaoCadCar.add(btCancelarCaracteristica);

        btSalvarCaracteristica.setText("Salvar");
        btSalvarCaracteristica.setPreferredSize(new java.awt.Dimension(100, 25));
        btSalvarCaracteristica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarCaracteristicaActionPerformed(evt);
            }
        });
        ppBotaoCadCar.add(btSalvarCaracteristica);

        ppCadastro.add(ppBotaoCadCar, java.awt.BorderLayout.SOUTH);

        paProduto.addTab("Cadastro / Alteração", ppCadastro);

        getContentPane().add(paProduto, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btPesquisarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarProdutoActionPerformed
        buscarProdutos("Pesquisar");
    }//GEN-LAST:event_btPesquisarProdutoActionPerformed

    private void btIncluirProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIncluirProdutoActionPerformed
        salvarAlterar = "Salvar";
        produto = new Produto();
        produto.setCodigo("");
        paProduto.setSelectedComponent(ppCadastro);
        paProduto.setEnabledAt(1, true);
        paProduto.setEnabledAt(0, false);
        ppCadastro.setVisible(true);
        btSalvarSubCat.setEnabled(true);
        btCancelarSubCat.setEnabled(true);
        carregarCategorias("Incluir");
        // carregarParametros();
        ccdDiretorio.setProduto(produto);
        ccdDiretorio.setDirOrigem(dirArqOrigem);
        ccpProdutoIncluir.setFiltroSituacao("Incluir");

    }//GEN-LAST:event_btIncluirProdutoActionPerformed

    private void tpConsultaProdutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpConsultaProdutoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tpConsultaProdutoKeyPressed

    private void btCancelarCaracteristicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarCaracteristicaActionPerformed
        limparCadastro();
    }//GEN-LAST:event_btCancelarCaracteristicaActionPerformed

    private void btSalvarCaracteristicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarCaracteristicaActionPerformed
        salvarProduto();
    }//GEN-LAST:event_btSalvarCaracteristicaActionPerformed

    private void btAlterarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarProdutoActionPerformed
        salvarAlterar = "Alterar";
        caracteristica = new Caracteristica();
        //   carregarParametros();
        alterarProduto();
        ccdDiretorio.setProduto(produto);
        ccdDiretorio.setDirOrigem(dirArqOrigem);
        if (resultado == true) {
            paProduto.setSelectedComponent(ppCadastro);
            ppCadastroGeral.setSelectedComponent(ppCadastroCaracteristicas);
            paProduto.setEnabledAt(1, true);
            paProduto.setEnabledAt(0, false);
            //buscarCaracteristica(caracteristica);
        } else {

        }

    }//GEN-LAST:event_btAlterarProdutoActionPerformed

    private void clsCategoriaFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsCategoriaFiltroActionPerformed
        carregarSubcategoria("Consulta");
    }//GEN-LAST:event_clsCategoriaFiltroActionPerformed

    private void clsCatCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsCatCadActionPerformed
        if (salvarAlterar.equalsIgnoreCase("Salvar")) {
            if (clsCatCad.getItemCount() == 0) {
            } else {
                carregarSubcategoria("Incluir");
            }
        }
    }//GEN-LAST:event_clsCatCadActionPerformed

    private void tpCadCarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpCadCarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tpCadCarMouseClicked

    private void btCancelarSubCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarSubCatActionPerformed
        if (salvarAlterar.equalsIgnoreCase("Alterar")) {
            clsSituacaoProduto.setEnabled(false);
            clsVisivelProduto.setEnabled(false);
        } else {
            clsCatCad.setSelectedIndex(0);
            ccpProdutoIncluir.setProduto(new Produto());
            clsVisivelProduto.setSelectedIndex(0);
            clsSituacaoProduto.setSelectedIndex(0);
        }
    }//GEN-LAST:event_btCancelarSubCatActionPerformed

    private void btSalvarSubCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarSubCatActionPerformed
        if (salvarAlterar.equalsIgnoreCase("Salvar")) {
            salvarRelacionamento("Salvar");
            if (resultado == true) {
                btSalvarSubCat.setEnabled(false);
                btAlterarCadPro.setEnabled(true);
                btCancelarSubCat.setEnabled(false);
                ppCadastroGeral.setSelectedComponent(ppCadastroCaracteristicas);
            }
        } else {
            salvarRelacionamento("Alterar");
            ppCadastroGeral.setSelectedComponent(ppCadastroCaracteristicas);
            clsSituacaoProduto.setEnabled(false);
            clsVisivelProduto.setEnabled(false);
        }


    }//GEN-LAST:event_btSalvarSubCatActionPerformed

    private void clsSubCatCadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_clsSubCatCadFocusLost

    }//GEN-LAST:event_clsSubCatCadFocusLost

    private void ccpProdutoIncluirFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ccpProdutoIncluirFocusLost

    }//GEN-LAST:event_ccpProdutoIncluirFocusLost

    private void btSalvarAnexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarAnexoActionPerformed
        addArquivo();
    }//GEN-LAST:event_btSalvarAnexoActionPerformed

    private void clsSituacaoProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clsSituacaoProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clsSituacaoProdutoActionPerformed

    private void btAlterarCadProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarCadProActionPerformed
        clsSituacaoProduto.setEnabled(true);
        clsVisivelProduto.setEnabled(true);
        if (salvarAlterar.equalsIgnoreCase("Salvar")) {
            ppCadastroProduto.setEnabled(true);
        }
    }//GEN-LAST:event_btAlterarCadProActionPerformed

    private void tpCadCarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpCadCarFocusLost
        validaDados(otmCaracteristicaProduto.getValue(tpCadCar.getLinhaSelecionada()));
    }//GEN-LAST:event_tpCadCarFocusLost

    private void tpAnexosPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tpAnexosPropertyChange

    }//GEN-LAST:event_tpAnexosPropertyChange

    private void tpAnexosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpAnexosFocusLost
        CellEditor editor = tpAnexos.getCellEditor();
        CellEditorListener editorListener = new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                otmArquivo.getValue(tpAnexos.getLinhaSelecionada()).setAlterado("S");

            }

            @Override
            public void editingCanceled(ChangeEvent e) {

            }
        };
        if (editor == null) {

        } else {
            editor.addCellEditorListener(editorListener);
        }
    }//GEN-LAST:event_tpAnexosFocusLost

    private void tpAnexosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tpAnexosKeyPressed

    }//GEN-LAST:event_tpAnexosKeyPressed

    private void btExcluirAnexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirAnexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btExcluirAnexoActionPerformed

    private void bVizualizarAnexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bVizualizarAnexoActionPerformed
       vizualizarImagem();
    }//GEN-LAST:event_bVizualizarAnexoActionPerformed

    private void btExcluirAnexo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirAnexo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btExcluirAnexo1ActionPerformed

    private void btExcluirProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirProdutoActionPerformed
        excluirProduto();
    }//GEN-LAST:event_btExcluirProdutoActionPerformed

    private void btRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRelatorioActionPerformed

        try {
            produtoControl.gerarFichaProduto(otmProduto.getValue(tpConsultaProduto.getLinhaSelecionada()).getCodigo());
        } catch (ErroSistemaException ex) {
            jpsProduto.setStatus(ex.getMessage(), JPStatus.ERRO);
        }

    }//GEN-LAST:event_btRelatorioActionPerformed

    private void ciCodCatPaiCatalogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ciCodCatPaiCatalogoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ciCodCatPaiCatalogoActionPerformed

    private void botao3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao3ActionPerformed
        pesquisarCatalogo();
    }//GEN-LAST:event_botao3ActionPerformed

    private void botao1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao1ActionPerformed
        addCatalogo();
    }//GEN-LAST:event_botao1ActionPerformed

    private void botao2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao2ActionPerformed
        removeCatalogo();
    }//GEN-LAST:event_botao2ActionPerformed

    private void tpCatalogoItemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tpCatalogoItemFocusLost
        CellEditor editor = tpCatalogoItem.getCellEditor();
        CellEditorListener editorListener = new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                otmCatalogoItem.getValue(tpCatalogoItem.getLinhaSelecionada()).setAlterado("S");
            }

            @Override
            public void editingCanceled(ChangeEvent e) {
            }
        };
        if (editor == null) {

        } else {
            editor.addCellEditorListener(editorListener);
        }
    }//GEN-LAST:event_tpCatalogoItemFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane areaDeTexto;
    private br.com.martinello.matriz.componentesbasicos.Botao bVizualizarAnexo;
    private br.com.martinello.matriz.componentesbasicos.Botao botao1;
    private br.com.martinello.matriz.componentesbasicos.Botao botao2;
    private br.com.martinello.matriz.componentesbasicos.Botao botao3;
    private br.com.martinello.matriz.componentesbasicos.Botao btAlterarCadPro;
    private br.com.martinello.matriz.componentesbasicos.Botao btAlterarProduto;
    private br.com.martinello.matriz.componentesbasicos.Botao btCancelarCaracteristica;
    private br.com.martinello.matriz.componentesbasicos.Botao btCancelarSubCat;
    private br.com.martinello.matriz.componentesbasicos.Botao btExcluirAnexo;
    private br.com.martinello.matriz.componentesbasicos.Botao btExcluirAnexo1;
    private br.com.martinello.matriz.componentesbasicos.Botao btExcluirProduto;
    private br.com.martinello.matriz.componentesbasicos.Botao btIncluirProduto;
    private br.com.martinello.matriz.componentesbasicos.Botao btPesquisarProduto;
    private br.com.martinello.matriz.componentesbasicos.Botao btRelatorio;
    private br.com.martinello.matriz.componentesbasicos.Botao btSalvarAnexo;
    private br.com.martinello.matriz.componentesbasicos.Botao btSalvarCaracteristica;
    private br.com.martinello.matriz.componentesbasicos.Botao btSalvarSubCat;
    private br.com.martinello.cadastro.componentes.consulta.campoConsultaDiretorio ccdDiretorio;
    private br.com.martinello.cadastro.componentes.consulta.CampoConsultaProduto ccpProdutoIncluir;
    private br.com.martinello.matriz.componentesbasicos.CampoInteiro ciCodCatCatalogo;
    private br.com.martinello.matriz.componentesbasicos.CampoInteiro ciCodCatPaiCatalogo;
    private br.com.martinello.matriz.componentesbasicos.CampoListaSimples clsCatCad;
    private br.com.martinello.matriz.componentesbasicos.CampoListaSimples clsCategoriaFiltro;
    private br.com.martinello.matriz.componentesbasicos.CampoListaSimples clsSituacaoProduto;
    private br.com.martinello.matriz.componentesbasicos.CampoListaSimples clsSubCatCad;
    private br.com.martinello.matriz.componentesbasicos.CampoListaSimples clsSubCatFiltro;
    private br.com.martinello.matriz.componentesbasicos.CampoListaSimples clsVisivelProduto;
    private br.com.martinello.matriz.componentesbasicos.CampoString csCodigo;
    private br.com.martinello.matriz.componentesbasicos.CampoString csNomeProduto;
    private br.com.martinello.matriz.componentesbasicos.consulta.CampoStringConsulta cscFiltroCat;
    private br.com.martinello.matriz.componentesbasicos.consulta.CampoStringConsulta cscFiltroCatPai;
    private br.com.martinello.matriz.componentesbasicos.consulta.CampoStringConsulta cscFiltroProduto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private br.com.martinello.matriz.componentesbasicos.paineis.JPStatus jpsProduto;
    private javax.swing.JScrollPane jspInformacoes;
    private javax.swing.JScrollPane jspTabConsultaProduto;
    private javax.swing.JScrollPane jspTbAnexos;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelAba paCaracteristica;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelAba paProduto;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao painelEstilo;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao painelPadrao1;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppAnexos;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppBotaoCadCar;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppCadastro;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppCadastroCaracteristicas;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelAba ppCadastroGeral;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppCadastroProduto;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppCatalogo;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppCatalogoCad;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppConsultaProduto;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppDescricao;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppFiltroAnexos;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppFiltroProduto;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppFiltroRelacionamento;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppQtdProduto;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppSubCarItens;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppSubTabCaracteristicas;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppTabConsultaProduto;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppTbAnexos;
    private br.com.martinello.matriz.componentesbasicos.paineis.PainelPadrao ppTrafael;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rCatCad;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rCatCatalogo;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rCatItem;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rCatPai;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rCategoriaFiltro;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rCodCatPaiCatalogo;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rDescProCad;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rDescricaoLonga;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rIdProdutoFiltro;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rNome;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rProduto;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rProdutoCadastro;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rQtdProduto;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rQtdProdutoValor;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rSelecionar;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rStatusProduto;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rSubCatCad;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rSubCategoriaFiltro;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rVisivelProduto;
    private br.com.martinello.matriz.componentesbasicos.TabelaPadrao tpAnexos;
    private br.com.martinello.matriz.componentesbasicos.TabelaPadrao tpCadCar;
    private br.com.martinello.matriz.componentesbasicos.TabelaPadrao tpCaracteristicas;
    private br.com.martinello.matriz.componentesbasicos.TabelaPadrao tpCatalogo;
    private br.com.martinello.matriz.componentesbasicos.TabelaPadrao tpCatalogoItem;
    private br.com.martinello.matriz.componentesbasicos.TabelaPadrao tpConsultaProduto;
    // End of variables declaration//GEN-END:variables

    private void buscarProdutos(String origem) {
        try {
            filtroProduto = new Produto();
            lProdutos = new LinkedList<>();
            produtoControl = new ProdutoControl();

            if (clsCategoriaFiltro.getSelectedItem().toString().equalsIgnoreCase("0 - TODAS")) {
            } else {
                cat = clsCategoriaFiltro.getSelectedItem().toString();
                for (Categoria categorias : lCategoria) {
                    String catSelect = categorias.getIdCategoria() + " - " + categorias.getCategoria();
                    if (catSelect.equalsIgnoreCase(clsCategoriaFiltro.getSelectedItem().toString())) {
                        filtroProduto.setIdCategoria(categorias.getIdCategoria());
                    }
                }
            }
            if (clsSubCatFiltro.getSelectedItem().toString().equalsIgnoreCase("0 - TODAS")) {
            } else {
                cat = clsSubCatFiltro.getSelectedItem().toString();
                for (SubCategoria subCategorias : lSubCategoria) {
                    String catSelect = subCategorias.getIdSubCategoria() + " - " + subCategorias.getSubCategoria();
                    if (catSelect.equalsIgnoreCase(clsSubCatFiltro.getSelectedItem().toString())) {
                        filtroProduto.setIdSubCategoria(subCategorias.getIdSubCategoria());
                    }
                }
            }

            if (csCodigo.getString() != null) {
                filtroProduto.setCodigo(csCodigo.getString());
            }
            List<Filtro> lFiltros = new LinkedList<>();
            lFiltros.add(new Filtro("idCategoria", Filtro.STRING, Filtro.INTEGER, filtroProduto.getIdCategoria() == 0 ? null : filtroProduto.getIdCategoria()));
            lFiltros.add(new Filtro("idSubCategoria", Filtro.STRING, Filtro.INTEGER, filtroProduto.getIdSubCategoria() == 0 ? null : filtroProduto.getIdSubCategoria()));
            lFiltros.add(new Filtro("codigo", Filtro.STRING, Filtro.IGUAL, csCodigo.getString().equalsIgnoreCase("") ? null : csCodigo.getString()));
            lFiltros.add(new Filtro("descricao", Filtro.STRING, Filtro.IGUAL, cscFiltroProduto.getFiltroOld() == null ? null : cscFiltroProduto.getFiltroOld().toString().toUpperCase()));

            lProdutos = produtoControl.listarProdutos(lFiltros);
            otmProduto.setData(lProdutos);
            otmProduto.fireTableDataChanged();
            tpConsultaProduto.setForeground(Color.BLACK);
            if (origem.equalsIgnoreCase("Pesquisar")) {
                jpsProduto.setStatus("Pesquisa realizada com sucesso!", JPStatus.NORMAL);
            }
            rQtdProdutoValor.setText(Integer.toString(tpConsultaProduto.getRowCount()));
            if (tpConsultaProduto.getRowCount() > 0) {
                tpConsultaProduto.packAll();
                tpConsultaProduto.addRowSelectionInterval(tpConsultaProduto.convertRowIndexToView(0), tpConsultaProduto.convertRowIndexToView(0));
                tpConsultaProduto.grabFocus();
            }
        } catch (ErroSistemaException ex) {
            jpsProduto.setStatus(ex.getMessage(), JPStatus.ERRO);
        }
        // carregarCategorias();

    }

    private void carregarCategorias(String origem) {
        categoria = new Categoria();
        subCategoriaControl = new SubCategoriaControl();
        lCategoria = new ArrayList<>();
        try {
            lCategoria = subCategoriaControl.listarJcbCategoria(categoria);
        } catch (ErroSistemaException ex) {
            jpsProduto.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }
        if (origem.equalsIgnoreCase("Incluir")) {
            clsCatCad.removeAllItems();
        } else {
            clsCategoriaFiltro.removeAllItems();
        }
        for (Categoria categorias : lCategoria) {
            if (origem.equalsIgnoreCase("Incluir")) {
                clsCatCad.addItem(categorias.getIdCategoria() + " - " + categorias.getCategoria());
            } else {
                clsCategoriaFiltro.addItem(categorias.getIdCategoria() + " - " + categorias.getCategoria());
            }
        }
    }

    private void carregarSubcategoria(String Origem) {
        subCategoria = new SubCategoria();
        subCategoriaControl = new SubCategoriaControl();
        lSubCategoria = new ArrayList<>();
        if (lSubCategoria.size() > 0) {
            lSubCategoria.clear();
        }
        try {
            if (Origem.equalsIgnoreCase("Consulta")) {
                if (clsCategoriaFiltro.getModel().getSize() > 0) {
                    if (clsCategoriaFiltro.getSelectedItem().toString().equalsIgnoreCase("0 - TODAS")) {

                    } else {
                        cat = clsCategoriaFiltro.getSelectedItem().toString();
                        for (Categoria categorias : lCategoria) {
                            String idCategoria = categorias.getIdCategoria() + " - " + categorias.getCategoria();
                            if (idCategoria.equalsIgnoreCase(clsCategoriaFiltro.getSelectedItem().toString())) {
                                subCategoria.setIdCategoria(categorias.getIdCategoria());
                            }
                        }
                    }
                }
            }
            if (Origem.equalsIgnoreCase("Incluir")) {
                if (clsCatCad.getSelectedItem().toString().equalsIgnoreCase("0 - TODAS")) {
                } else {
                    cat = clsCatCad.getSelectedItem().toString();
                    for (Categoria categorias : lCategoria) {
                        String idCategoria = categorias.getIdCategoria() + " - " + categorias.getCategoria();
                        if (idCategoria.equalsIgnoreCase(clsCatCad.getSelectedItem().toString())) {
                            subCategoria.setIdCategoria(categorias.getIdCategoria());
                        }
                    }
                }
                clsSubCatCad.removeAllItems();
            }

            lSubCategoria = subCategoriaControl.listarJcbSubCategoria(subCategoria);
        } catch (ErroSistemaException ex) {
            jpsProduto.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }
        if (Origem.equalsIgnoreCase("Consulta")) {
            clsSubCatFiltro.removeAllItems();
        }
        if (Origem.equalsIgnoreCase("Incluir")) {
            clsSubCatCad.removeAllItems();
        }

        for (SubCategoria subCategorias : lSubCategoria) {
            if (Origem.equalsIgnoreCase("Incluir")) {
                clsSubCatCad.addItem(subCategorias.getIdSubCategoria() + " - " + subCategorias.getSubCategoria());
            } else {
                clsSubCatFiltro.addItem(subCategorias.getIdSubCategoria() + " - " + subCategorias.getSubCategoria());
            }
        }
    }

    private boolean salvarProduto() {
        produtoControl = new ProdutoControl();
        resultado = false;
        for (Produto proValidacao : otmCaracteristicaProduto.getData()) {
            resultado = validaDados(proValidacao);
            if (resultado == false) {
                jpsProduto.setStatus("Erro na validação de dados, verifique os campos e corrija!", JPStatus.ERRO);
                return resultado;
            }
        }
        for (Arquivo arqValidacao : otmArquivo.getData()) {
            resultado = validaDadosArq(arqValidacao);
            if (resultado == false) {
                jpsProduto.setStatus("Erro na validação de dados da Tabela Anexos, verifique os campos e corrija!", JPStatus.ERRO);
                return resultado;
            }
        }
        for (Categoria catValidacao : otmCatalogoItem.getData()) {
            resultado = validaDadosRel(catValidacao);
            if (resultado == false) {
                jpsProduto.setStatus("Erro na validação de dados da Tabela Relacionamento, verifique os campos e corrija!", JPStatus.ERRO);
                return resultado;
            }
        }

        produto.setDescLonga(areaDeTexto.getText());
        if (clsSituacaoProduto.getSelectedItem().toString().equalsIgnoreCase("ATIVO")) {
            produto.setSituacao("A");
        } else {
            produto.setSituacao("I");
        }
        if (clsVisivelProduto.getSelectedItem().toString().equalsIgnoreCase("SIM")) {
            produto.setVisivel("S");
        } else {
            produto.setVisivel("N");
        }
        produto.setIdUsuario(usuario.getIdUsuario());
        if (salvarAlterar.equalsIgnoreCase("Salvar")) {
            try {
                // resultado = produtoControl.InserirRelacionamento(produto, otmCaracteristicaProduto.getData(), otmArquivo.getData());
                resultado = produtoControl.InserirRelacionamentoNew(produto, otmCaracteristicaProduto.getData(), otmArquivo.getData(), otmCatalogoItem.getData());
                jpsProduto.setStatus("Informações do produto salva com sucesso!", JPStatus.NORMAL, 20);

            } catch (ErroSistemaException ex) {
                resultado = false;
                jpsProduto.setStatus(ex.getMessage(), JPStatus.ERRO);
            }
        } else {
            try {
                lProdutos = new LinkedList<>();
                lProdutos = produtoControl.buscarRelacionados(produto);
                // resultado = produtoControl.UpdateProduto(lProdutos, produto, otmCaracteristicaProduto.getData(), otmArquivo.getData());
                resultado = produtoControl.UpdateProdutoNew(lProdutos, produto, otmCaracteristicaProduto.getData(), otmArquivo.getData(), otmCatalogoItem.getData());

                jpsProduto.setStatus("Informações do produto alterado com sucesso!", JPStatus.NORMAL, 20);
            } catch (ErroSistemaException ex) {
                jpsProduto.setStatus(ex.getMessage(), JPStatus.ERRO);
            }
        }
        if (resultado == true) {
            buscarProdutos("Cadastro");
            paProduto.setSelectedComponent(ppConsultaProduto);
            paProduto.setEnabledAt(1, false);
            paProduto.setEnabledAt(0, true);
            limparCadastro();
        }

        if (resultado) {
            PendenciaControlInt pendenciaControlInt = new PendenciaControlInt();
            Pendencia pendencia = new Pendencia();
            pendencia.setChave(produto.getCodigo());
            pendencia.setDataGeracao(new Date());
            pendencia.setHoraGeracao(0l);
            pendencia.setOperacao("A");
            pendencia.setPrioridade(0l);
            pendencia.setProcesso(Pendencia.ALTERACAO_PRODUTO);
            pendencia.setSituacao("P");
            pendencia.setTipo("A");
            pendencia.setUsuarioGeracao(usuario.getIdUsuario());

            try {
                pendenciaControlInt.inserirPendencia(pendencia);
            } catch (ErroSistemaException ex) {
                ex.printStackTrace();
                jpsProduto.setStatus(ex.getMessage(), JPStatus.ERRO);
            }
        }
//        carregarCategorias("Consulta");
        //  limparCadastro();
        return resultado;
    }

    private boolean alterarProduto() {
        produto = new Produto();
        resultado = false;
        produtoControl = new ProdutoControl();
        arquivoControl = new ArquivoControl();
        int linha = tpConsultaProduto.getSelectedRow();
        codigo = "0";
        if (linha < 0) {
            jpsProduto.setStatus("Selecione um produto na tabela, antes de clicar em alterar!", JPStatus.ALERTA);
            return resultado;
        } else {
            try {
                produto = otmProduto.getValue(tpConsultaProduto.getLinhaSelecionada());
                Produto produtoDesc = new Produto();
                produtoDesc = produtoControl.descricaoProduto(produto.getCodigo());

                if (produtoDesc.getIdCategoria() > 0) {
                    if (produtoDesc.getVisivel().equalsIgnoreCase("N")) {
                        clsVisivelProduto.setSelectedIndex(1);
                    } else {
                        clsVisivelProduto.setSelectedIndex(0);
                    }
                    if (produtoDesc.getSituacao().equalsIgnoreCase("N")) {
                        clsSituacaoProduto.setSelectedIndex(1);
                    } else {
                        clsSituacaoProduto.setSelectedIndex(0);
                    }
                    areaDeTexto.setText(produtoDesc.getDescLonga());
                }
                clsCatCad.removeAllItems();
                clsSubCatCad.removeAllItems();
                clsCatCad.addItem(produto.getIdCategoria() + "-" + produto.getCategoria());
                clsSubCatCad.addItem(produto.getIdSubCategoria() + "-" + produto.getSubCategoria());
                ccpProdutoIncluir.setProduto(produto);
                ppCadastroProduto.setEnabled(false);
                clsCatCad.setEnabled(false);
                clsSubCatCad.setEnabled(false);
                clsSituacaoProduto.setEnabled(false);
                clsVisivelProduto.setEnabled(false);
                ccpProdutoIncluir.setEnabled(false);
                btSalvarSubCat.setEnabled(false);
                btCancelarSubCat.setEnabled(false);
                salvarAlterar = "Alterar";
                resultado = true;
                otmArquivo.setData(arquivoControl.listarArquivos(produto.getCodigo()));
                otmCaracteristicaProduto.setData(otmCaracteristicas.getData());
                otmCaracteristicaProduto.fireTableDataChanged();
                tpCadCar.setForeground(Color.BLACK);
                if (tpCadCar.getRowCount() > 0) {
                    tpCadCar.packAll();
                    tpCadCar.addRowSelectionInterval(tpCadCar.convertRowIndexToView(0), tpCadCar.convertRowIndexToView(0));
                    tpCadCar.grabFocus();
                }
                categoriaControl = new CategoriaControl();
                otmCatalogoItem.setData(categoriaControl.listarCatalogoItem(produto.getCodigo()));
            } catch (ErroSistemaException ex) {
                jpsProduto.setStatus(ex.getMessage(), JPStatus.ERRO, ex);
            }
        }
        return resultado;
    }

    private boolean salvarCaracteristicasProduto(Produto produto) {
        subCategoriaControl = new SubCategoriaControl();
        if (salvarAlterar.equalsIgnoreCase("Salvar")) {
            if (clsCatCad.getSelectedItem().toString().equalsIgnoreCase("0 - Todas") || clsSubCatCad.getSelectedItem().toString().equalsIgnoreCase("0 - Todas")) {
                jpsProduto.setStatus("Erro é necessário informar uma Categoria!", JPStatus.ALERTA, 20);
                return resultado;
            }
            if (rDescProCad.getText().isEmpty()) {
                jpsProduto.setStatus("Erro é necessário informar um Produto!", JPStatus.ALERTA, 20);
                return resultado;
            }
        }
        try {
            caracteristica.setIdSubCategoria(subCategoriaControl.insertSubCategoria(subCategoria));
        } catch (ErroSistemaException ex) {
            jpsProduto.setStatus(ex.getMessage(), JPStatus.ERRO, ex);
            return resultado;
        }
        if (resultado = true) {
            jpsProduto.setStatus("Registro Cadastrado com Sucesso!", JPStatus.NORMAL, 20);
        }
        return resultado;
    }

    private void setDadosCaracteristicas(String codigo) {
        try {
            lProdutosCaracteristicas = new ArrayList<>();
            lProdutosCaracteristicas = produtoControl.listarCaracteristicaProduto(codigo);
            otmCaracteristicas.setData(lProdutosCaracteristicas);
            otmCaracteristicas.fireTableDataChanged();
            tpCaracteristicas.setForeground(Color.BLACK);
            if (tpCaracteristicas.getRowCount() > 0) {
                tpCaracteristicas.packAll();
                tpCaracteristicas.addRowSelectionInterval(tpCaracteristicas.convertRowIndexToView(0), tpCaracteristicas.convertRowIndexToView(0));
            }
        } catch (ErroSistemaException ex) {
            jpsProduto.setStatus(ex.getMessage(), JPStatus.ERRO);
        }
    }

    private boolean salvarTabelaItem(String salvarAlterar) {
        return false;

    }

    private void salvarCaracteristicas() {
        try {
            caracteristicaControl = new CaracteristicaControl();
            caracteristicaControl.insertCaracteristicas(lCaracteristicas);
        } catch (ErroSistemaException ex) {
            jpsProduto.setStatus(ex.getMessage(), JPStatus.ERRO, 20);
        }
        jpsProduto.setStatus("Item Salvo com Sucesso!.", JPStatus.NORMAL);

    }

    private boolean salvarRelacionamento(String origem) {
        resultado = false;
        produtoControl = new ProdutoControl();
        produto = new Produto();
        if (rDescProCad.getText().isEmpty()) {
            jpsProduto.setStatus("Erro é necessário informar um produto!", JPStatus.ALERTA);
            return resultado;
        }
        if (clsSituacaoProduto.getSelectedItem().toString().equalsIgnoreCase(" ")) {
            jpsProduto.setStatus("Erro é necessário informar o Status do produto!", JPStatus.ALERTA);
            return resultado;
        }
        if (clsVisivelProduto.getSelectedItem().toString().equalsIgnoreCase(" ")) {
            jpsProduto.setStatus("Erro é necessário informar se o produto é Visível Sim ou Não!", JPStatus.ALERTA);
            return resultado;
        }
        if (origem.equalsIgnoreCase("Salvar")) {
            if (clsCatCad.getSelectedItem().toString().equalsIgnoreCase("0 - Todas")) {
                jpsProduto.setStatus("Erro é necessário informar uma Categoria!", JPStatus.ALERTA, 20);
                return resultado;
            } else {
                cat = clsCatCad.getSelectedItem().toString();
                for (Categoria categorias : lCategoria) {
                    String idCategoria = categorias.getIdCategoria() + " - " + categorias.getCategoria();
                    if (idCategoria.equalsIgnoreCase(clsCatCad.getSelectedItem().toString())) {
                        produto.setIdCategoria(categorias.getIdCategoria());
                    }
                }
            }
            if (clsSubCatCad.getSelectedItem().toString().equalsIgnoreCase("0 - Todas")) {
                jpsProduto.setStatus("Erro é necessário informar uma Categoria!", JPStatus.ALERTA, 20);
                return resultado;
            } else {
                cat = clsCatCad.getSelectedItem().toString();
                for (SubCategoria subCategorias : lSubCategoria) {
                    String idCategoria = subCategorias.getIdSubCategoria() + " - " + subCategorias.getSubCategoria();
                    if (idCategoria.equalsIgnoreCase(clsSubCatCad.getSelectedItem().toString())) {
                        produto.setIdSubCategoria(subCategorias.getIdSubCategoria());
                    }
                }
            }
            ppCadastroProduto.setEnabled(false);
            clsCatCad.setEnabled(false);
            clsSubCatCad.setEnabled(false);
            clsSituacaoProduto.setEnabled(false);
            clsVisivelProduto.setEnabled(false);
            ccpProdutoIncluir.setEnabled(false);
            btSalvarSubCat.setEnabled(false);
            btCancelarSubCat.setEnabled(false);
            produto.setCodigo(ccpProdutoIncluir.getProduto().getCodigo());
            produto.setIdUsuario(TelaPrincipal.usuario.getIdUsuario());
            carregarCaracteristicas(produto);
        }

        return resultado = true;
    }

    private void carregarCaracteristicas(Produto produto) {
        try {
            lProdutos = new LinkedList<>();
            produtoControl = new ProdutoControl();
            if (salvarAlterar.equalsIgnoreCase("Salvar")) {
                lProdutos = produtoControl.listarCaracteristicas(produto);
            } else {
                lProdutos = produtoControl.listarCaracteristicaProduto(produto.getCodigo());
            }
            otmCaracteristicaProduto.setData(lProdutos);
            otmCaracteristicaProduto.fireTableDataChanged();
            tpCadCar.setForeground(Color.BLACK);
            if (tpCadCar.getRowCount() > 0) {
                tpCadCar.packAll();
                tpCadCar.addRowSelectionInterval(tpCadCar.convertRowIndexToView(0), tpCadCar.convertRowIndexToView(0));
                tpCadCar.grabFocus();
                //  tpCadCar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                tpCadCar.getColumnModel().getColumn(2).setResizable(true);
                tpCadCar.getColumnModel().getColumn(2).setPreferredWidth(500);
            }
        } catch (ErroSistemaException ex) {
            jpsProduto.setStatus("Erro ao Carregar as Caracteristicas do produto!.", JPStatus.ERRO);
        }
    }

    public File buscarArquivo(Produto produto, String dirOrigem) {
        //Pega o arquivo no PC
        File arquivo = new File(dirArqOrigem);
        JFileChooser file = new JFileChooser();
        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (dirOrigem.equalsIgnoreCase("")) {
            carregarParametros();
            file.setCurrentDirectory(new File(dirArqOrigem));
            file.setCurrentDirectory(new File(dirArqOrigem + "\\" + produto.getCategoria()));
            file.setCurrentDirectory(new File(dirArqOrigem + "\\" + produto.getCategoria() + "\\" + produto.getSubCategoria()));
            file.setCurrentDirectory(new File(dirArqOrigem + "\\" + produto.getCategoria() + "\\" + produto.getSubCategoria() + "\\" + produto.getCodigo()));
        } else {
            file.setCurrentDirectory(new File(dirOrigem));
        }
        System.out.println("file" + file.getCurrentDirectory().toString());
        int i = file.showSaveDialog(null);
        //Pega o arquivo no PC
        //Verifica se o arquivo foi selecionado ou se a operação foi cancelada
        //Se cancelada, seta o campo como nulo
        if (i == 1) {
            ccdDiretorio.setDiretorio("");
            //Caso contrário pega o arquivo e seta o campo txtLocal com o caminho do arquivo
        } else {
            arquivo = file.getSelectedFile();
        }
        return arquivo;
    }

    private void addArquivo() {
        if (ccdDiretorio.getDiretorio().equalsIgnoreCase("")) {
            jpsProduto.setStatus("Erro é necessário selecionar um arquivo!.", JPStatus.ERRO);
        } else {
            String arq = ccdDiretorio.getDiretorio();
            String nome = ccdDiretorio.getArquivo().getName();
            String ext[] = arq.split("\\.");
            String arq2[] = arq.split(nome);
            int a = ext.length;
            int d = arq2.length;
            arquivos = new Arquivo();
            arquivos.setNome(nome);
            arquivos.setSequencia(tpAnexos.getRowCount() + 1);
            arquivos.setVisivel("S");
            arquivos.setDiretorio(arq2[d - 1]);
            arquivos.setExtencao(ext[a - 1]);
            arquivos.setProduto(produto.getCodigo());
            arquivos.setPrincipal("N");
            arquivos.setSituacao("A");
            arquivos.setAlterado("S");
            arquivos.setNovoNome(produto.getCodigo() + "-" + arquivos.getSequencia());
            otmArquivo.add(arquivos);
            otmArquivo.fireTableDataChanged();
            tpAnexos.setForeground(Color.BLACK);
            dirArqOrigem = arquivos.getDiretorio();
            ccdDiretorio.setDirOrigem(dirArqOrigem);
            ccdDiretorio.setDiretorio("");
            jpsProduto.setStatus("Anexo adicionado com sucesso.", JPStatus.NORMAL);
            if (tpAnexos.getRowCount() > 0) {
                tpAnexos.packAll();
                tpAnexos.addRowSelectionInterval(tpAnexos.convertRowIndexToView(0), tpAnexos.convertRowIndexToView(0));
                tpAnexos.grabFocus();
            }
        }

    }

    /*Editor de Texto*/
    private void adicionaFontesNaCombo(CampoListaSimples combo) {
        String[] fontes = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();
        for (String fonte : fontes) {
            combo.addItem(fonte);
        }
    }

    private void adicionaTamanhosNaCombo(CampoListaSimples combo) {
        for (int i = 8; i <= 72; i += 2) {
            combo.addItem(i);
        }
    }

    public void escolherCor() {
        escolherCor = new JColorChooser();
        cor = escolherCor.showDialog(null, "Selecione", Color.BLACK);

    }

    private void limparCadastro() {
        clsSituacaoProduto.setSelectedIndex(0);
        clsVisivelProduto.setSelectedIndex(0);
        areaDeTexto.setText("");
        ccpProdutoIncluir.setProduto(new Produto());
        buscarProdutos("Pesquisar");

        ppCadastroProduto.setEnabled(false);
        //  clsCatCad.setEnabled(true);
        clsSubCatCad.setEnabled(true);
        clsSituacaoProduto.setEnabled(true);
        clsVisivelProduto.setEnabled(true);
        ccpProdutoIncluir.setEnabled(true);
        otmCaracteristicaProduto.setData(new LinkedList<>());
        otmArquivo.setData(new LinkedList<>());
        otmCatalogo.setData(new LinkedList<>());
        otmCatalogoItem.setData(new LinkedList<>());
        cor = null;
        paProduto.setSelectedComponent(ppConsultaProduto);
        paProduto.setEnabledAt(1, false);
        paProduto.setEnabledAt(0, true);
        dirArqOrigem = "";
    }

    private boolean validaDados(Produto value) {
        if (value.getSituacao().equalsIgnoreCase("A") || value.getSituacao().equalsIgnoreCase("I")) {
        } else {
            JOptionPane.showMessageDialog(null, "Erro na linha (" + value.getCaracteristica() + "/" + value.getCaracteristicaItem() + ") Campo Situação só permite valor (A) para Ativo e (I) para Inativo!");
            return false;
        }
        if (value.getVisivel().equalsIgnoreCase("S") || value.getVisivel().equalsIgnoreCase("N")) {
        } else {
            JOptionPane.showMessageDialog(null, "Erro na linha (" + value.getCaracteristica() + "/" + value.getCaracteristicaItem() + ") Campo Visível só permite valor (S) para Sim e (N) para Não!");
            return false;
        }
        return true;
    }

    private void carregarParametros() {
        try {
            parametroControl = new ParametrosControl();
            parametro = new Parametro();
            parametro.setParametro("");
            parametro.setDescricao("");
            parametro.setValor("");
            lParametro = new LinkedList<>();
            lParametro = parametroControl.buscarParametros(parametro);
            for (Parametro parametro : lParametro) {
                if (parametro.getParametro().equalsIgnoreCase("DIRETORIO ORIGEM FOTOS")) {
                    dirArqOrigem = parametro.getValor();
                }
                if (parametro.getParametro().equalsIgnoreCase("DIRETORIO DESTINO FOTOS")) {
                    dirArqDestino = parametro.getValor();
                }
            }
        } catch (ErroSistemaException ex) {
            jpsProduto.setStatus(ex.getMessage(), JPStatus.ERRO);

        }

    }

    private boolean validaDadosArq(Arquivo value) {

        if (value.getVisivel().equalsIgnoreCase("S") || value.getVisivel().equalsIgnoreCase("N")) {
        } else {
            JOptionPane.showMessageDialog(null, "Erro na linha (" + value.getNovoNome() + ") Campo Visível só permite valor (S) para Sim e (N) para Não!");
            return false;
        }
        if (value.getPrincipal().equalsIgnoreCase("S") || value.getPrincipal().equalsIgnoreCase("N")) {
        } else {
            JOptionPane.showMessageDialog(null, "Erro na linha (" + value.getNovoNome() + ") Campo Principal só permite valor (S) para Sim e (N) para Não!");
            return false;
        }
        return true;
    }

    private boolean validaDadosRel(Categoria value) {

        if (value.getVisivel().equalsIgnoreCase("S") || value.getVisivel().equalsIgnoreCase("N")) {
        } else {
            JOptionPane.showMessageDialog(null, "Erro na Categoria (" + value.getCategoria() + ") Campo Visível só permite valor (S) para Sim e (N) para Não!");
            return false;
        }
        if (value.getSituacao().equalsIgnoreCase("A") || value.getSituacao().equalsIgnoreCase("I")) {
        } else {
            JOptionPane.showMessageDialog(null, "Erro na Categoria (" + value.getCategoria() + ") Campo Ativo só permite valor (A) para Ativo e (I) para Inativo!");
            return false;
        }
        return true;
    }

    private void excluirProduto() {

    }

    private void pesquisarCatalogo() {
        try {
            filtroCategoria = new Categoria();
            lCategoria = new ArrayList<>();
            categoriaControl = new CategoriaControl();
            if (ciCodCatCatalogo.getInteger() > 0) {
                filtroCategoria.setIdCategoria(ciCodCatCatalogo.getInteger());
            }
            if (ciCodCatPaiCatalogo.getInteger() > 0) {
                filtroCategoria.setIdCategoriaPai(ciCodCatPaiCatalogo.getInteger());
            }
            if (cscFiltroCat.getFiltroOld() != null) {
                filtroCategoria.setCategoria(cscFiltroCat.getFiltroOld().toString().toUpperCase());
            }
            if (cscFiltroCatPai.getFiltroOld() != null) {
                filtroCategoria.setCategoriaPai(cscFiltroCatPai.getFiltroOld().toString().toUpperCase());
            }
            filtroCategoria.setSituacao("");
            lCategoria = categoriaControl.listarCatalogo(filtroCategoria);
            otmCatalogo.setData(lCategoria);
            otmCatalogo.fireTableDataChanged();
            tpCatalogo.setForeground(Color.BLACK);

            jpsProduto.setStatus("Pesquisa realizada com sucesso.", JPStatus.NORMAL);

            if (tpCatalogo.getRowCount() > 0) {
                tpCatalogo.packAll();
                tpCatalogo.addRowSelectionInterval(tpCatalogo.convertRowIndexToView(0), tpCatalogo.convertRowIndexToView(0));
                tpCatalogo.grabFocus();
            }
        } catch (ErroSistemaException ex) {
            jpsProduto.setStatus(ex.getMessage(), JPStatus.ERRO);
        }
    }

    private void addCatalogo() {
        categoria = otmCatalogo.getValue(tpCatalogo.getLinhaSelecionada());
        categoria.setCodPro("");
        categoria.setAlterado("S");
        otmCatalogoItem.add(categoria);
    }

    private void removeCatalogo() {
        if (otmCatalogoItem.getValue(tpCatalogoItem.getLinhaSelecionada()).getCodPro().equals("")) {
            int excluir = JOptionPane.showConfirmDialog(rootPane, "Deseja realmente Remover o Item?", "Confirma", JOptionPane.YES_NO_OPTION);
            if (excluir == 0) {
                otmCatalogoItem.remove(otmCatalogoItem.getValue(tpCatalogoItem.getLinhaSelecionada()));
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Registro já cadastrado não pôde ser removido!\n" + " Caso queira você pode inativar a categoria.");
        }
    }

    private void vizualizarImagem() {
        try {
            carregarParametros();
            arquivos = otmArquivo.getValue(tpAnexos.getLinhaSelecionada());
            File file = new File(dirArqDestino + arquivos.getProduto() + "//" + arquivos.getNovoNome() + "." + arquivos.getExtencao());
            Desktop.getDesktop().open(file);            
        } catch (IOException ex) {
            jpsProduto.setStatus(ex.getMessage(), JPStatus.ERRO);
        }       
    }

}
