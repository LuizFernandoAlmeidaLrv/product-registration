/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.view;

import br.com.martinello.componentesbasicos.Campo;
import br.com.martinello.componentesbasicos.ConstantesGlobais;
import br.com.martinello.gerenciador.componentes.CampoConsultaEvento;
import br.com.martinello.gerenciador.componentes.CampoConsultaListener;
import br.com.martinello.gerenciador.componentes.ConsultavelLista;
import br.com.martinello.gerenciador.componentes.ConsultavelTabela;
import br.com.martinello.bd.matriz.control.TabelaControl;
import br.com.martinello.bd.matriz.model.domain.Chave;
import br.com.martinello.bd.matriz.model.domain.Coluna;
import br.com.martinello.bd.matriz.model.domain.Indice;
import br.com.martinello.bd.matriz.model.domain.IndiceColuna;
import br.com.martinello.bd.matriz.model.domain.Lista;
import br.com.martinello.bd.matriz.model.domain.Relacionamento;
import br.com.martinello.bd.matriz.model.domain.RelacionamentoColuna;
import br.com.martinello.bd.matriz.model.domain.Restricao;
import br.com.martinello.bd.matriz.model.domain.RestricaoColuna;
import br.com.martinello.bd.matriz.model.domain.Tabela;
import br.com.martinello.gerenciador.view.model.ColunasObjectTableModel;
import br.com.martinello.util.ValidacaoException;
import br.com.martinello.util.excessoes.ErroSistemaException;
import com.towel.swing.table.ObjectTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import br.com.martinello.componentesbasicos.TabelaPadrao;
import br.com.martinello.componentesbasicos.paineis.JPStatus;
import br.com.martinello.impressora.Laser;
import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.filtro.Filtro;
import java.util.LinkedList;
import javax.swing.SwingUtilities;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Sidnei
 *
 */
public class TelaTabelas extends TelaPadrao implements CampoConsultaListener {

    protected ObjectTableModel<Tabela> otmConsultaTabela = new ObjectTableModel(Tabela.class, "idTabela,schema,tabela,descricao");
    protected ObjectTableModel<Coluna> otmConsultaColuna = new ObjectTableModel(Coluna.class, "idColuna,coluna,tituloCurto,tituloLongo,descricao,tipo,tamanho,precisao,mascara,lista,obrigatorio,valorPadrao");
    protected ObjectTableModel<Indice> otmConsultaIndices = new ObjectTableModel(Indice.class, "indice,descricao");
    protected ObjectTableModel<Relacionamento> otmConsultaRelacionamentos = new ObjectTableModel(Relacionamento.class, "nome,tabelaRelacionada");
    protected ObjectTableModel<Chave> otmConsultaChave = new ObjectTableModel(Chave.class, "coluna,ordem");
    protected ObjectTableModel<Restricao> otmConsultaRestricoes = new ObjectTableModel(Restricao.class, "restricao,descricao,indice");

    protected ColunasObjectTableModel cotmCadastroColunas = new ColunasObjectTableModel();
    protected ObjectTableModel<Coluna> otmCadastroColunas = new ObjectTableModel(Coluna.class, "coluna,tituloCurto,tituloLongo,descricao,tipo,tamanho,precisao,mascara,lista,obrigatorio,valorPadrao");
    protected ObjectTableModel<Indice> otmCadastroIndices = new ObjectTableModel(Indice.class, "indice,descricao");
    protected ObjectTableModel<IndiceColuna> otmCadastroIndiceColunas = new ObjectTableModel(IndiceColuna.class, "coluna,ordem");
    protected ObjectTableModel<Relacionamento> otmCadastroRelacionamentos = new ObjectTableModel(Relacionamento.class, "nome,tabelaRelacionada");
    protected ObjectTableModel<RelacionamentoColuna> otmCadastroRelacionamentoColunas = new ObjectTableModel(RelacionamentoColuna.class, "colunaOrigem,colunaRelacionada");
    protected ObjectTableModel<Chave> otmCadastroChave = new ObjectTableModel(Chave.class, "coluna,ordem");
    protected ObjectTableModel<Restricao> otmCadastroRestricoes = new ObjectTableModel(Restricao.class, "restricao,descricao,indice");
    protected ObjectTableModel<RestricaoColuna> otmCadastroRestricaoColunas = new ObjectTableModel(RestricaoColuna.class, "coluna,ordem");

    //protected TableRowSorter<ObjectTableModel> trsCadastroColunas = new TableRowSorter<ObjectTableModel>(cotmCadastroColunas);
    //protected TableRowSorter<ObjectTableModel> trsCadastroIndices = new TableRowSorter<ObjectTableModel>(otmCadastroIndices);
    //protected TableRowSorter<ObjectTableModel> trsCadastroIndiceColunas = new TableRowSorter<ObjectTableModel>(otmCadastroIndiceColunas);
    private DefaultComboBoxModel dcbmColunaChave, dcbmColunaIndice, dcbmColunaRelacionamentoOrigem, dcbmColunaRelacionamentoRelacionada;

    private TabelaControl tabelaControl;

    private List<Campo> listaCampoTabela = new ArrayList<>();
    private List<Campo> listaCampoTabelaColunas = new ArrayList<>();
    private List<Campo> listaCampoTabelaChave = new ArrayList<>();
    private List<Campo> listaCampoIndice = new ArrayList<>();
    private List<Campo> listaCampoIndiceColunas = new ArrayList<>();
    private List<Campo> listaCampoRelacionamento = new ArrayList<>();
    private List<Campo> listaCampoRelacionamentoColunas = new ArrayList<>();
    private List<Campo> listaCampoRestricao = new ArrayList<>();
    private List<Campo> listaCampoRestricaoColunas = new ArrayList<>();

    protected String filtroTabelaRelacionada = "", filtroColunaOrigem = "", filtroColunaDestino = "";

    protected String textoColunaOriginal;

    private Laser laser = new Laser();

    /**
     * Creates new form TelaTabelas
     */
    public TelaTabelas() {
        /**
         * Carregando o control
         */
        try {
            tabelaControl = new TabelaControl();
            otmConsultaTabela.setData(tabelaControl.getlConsultaTabela());

            dcbmColunaChave = new DefaultComboBoxModel(tabelaControl.getTabela().getColunas().toArray());
            dcbmColunaIndice = new DefaultComboBoxModel(tabelaControl.getTabela().getColunas().toArray());
            dcbmColunaRelacionamentoOrigem = new DefaultComboBoxModel(tabelaControl.getTabela().getColunas().toArray());
            dcbmColunaRelacionamentoRelacionada = new DefaultComboBoxModel(tabelaControl.getTabela().getColunas().toArray());
        } catch (ErroSistemaException ex) {
            JOptionPane.showMessageDialog(rootPane, "Erro ao carregar classe DAO.", ex.getLocalizedMessage(), JOptionPane.ERROR_MESSAGE);
        }

        //ocbmColunaIndice.setFormatter(new ColunaFormatter());
        initComponents();

        /**
         * Adicionando os campos nas listas para validação
         */
        criarListasValidacao();

        /**
         * Desativando a ordenação da Tabela
         */
        tpCadastroIndiceColunas.setSortable(false);

        /**
         * Setando o modo de seleção
         */
        tpCadastroIndiceColunas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tpCadastroIndices.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tpCadastroColunas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tpCadastroRestricoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jtpTabela.setEnabledAt(1, false);

        csRelacionamentoTabelaRelacionada.setJlDescricao(jlDescricaoTabelaRelacionada);

        //ocbmColunaIndice.setFormatter(new ColunaFormatter());
        //jcbColunaIndice.setModel(ocbmColunaIndice);
        tpConsultaTabelas.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }
            //otmConsultaColuna.setData(otmConsultaTabela.getValue(jxtConsultaTabelas.getSelectedRow()).getColunas());
        });

        tpCadastroIndices.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }

            if (tpCadastroIndices.getSelectedRow() >= 0) {
                otmCadastroIndices.getValue(tpCadastroIndices.convertRowIndexToModel(tpCadastroIndices.getSelectedRow())).getIndiceColunas().sort((p1, p2) -> Integer.compare(p1.getOrdem(), p2.getOrdem()));
                otmCadastroIndiceColunas.setData(otmCadastroIndices.getValue(tpCadastroIndices.convertRowIndexToModel(tpCadastroIndices.getSelectedRow())).getIndiceColunas());

                tpCadastroIndiceColunas.packAll();
            } else {
                otmCadastroIndiceColunas.clear();
            }
        });

        tpCadastroRelacionamentos.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }

            if (tpCadastroRelacionamentos.getSelectedRow() >= 0) {
                //otmCadastroIndices.getValue(jxtCadastroIndices.convertRowIndexToModel(jxtCadastroIndices.getSelectedRow())).getIndiceColunas().sort((p1, p2) -> Integer.compare(p1.getOrdem(), p2.getOrdem()));
                Relacionamento relacionamentoSelecionado = otmCadastroRelacionamentos.getValue(tpCadastroRelacionamentos.convertRowIndexToModel(tpCadastroRelacionamentos.getSelectedRow()));
                otmCadastroRelacionamentoColunas.setData(relacionamentoSelecionado.getRelacionamentosColunas());

                tpCadastroRelacionamentoColunas.packAll();

                //ocbmColunaRelacionamentoRelacionada.setData(relacionamentoSelecionado.getTabelaRelacionada().getColunas());
                clRelacionamentoColunaRelacionada.setModel(new DefaultComboBoxModel(relacionamentoSelecionado.getTabelaRelacionada().getColunas().toArray()));

                filtroColunaOrigem = "tabela.idTabela = " + relacionamentoSelecionado.getTabelaOrigem().getIdTabela();
                filtroColunaDestino = "tabela.idTabela = " + relacionamentoSelecionado.getTabelaRelacionada().getIdTabela();

            } else {
                otmCadastroRelacionamentoColunas.clear();
            }
        });

        tpCadastroRestricoes.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }

            if (tpCadastroRestricoes.getSelectedRow() >= 0) {
                otmCadastroRestricoes.getValue(tpCadastroRestricoes.convertRowIndexToModel(tpCadastroRestricoes.getSelectedRow())).getRestricaoColunas().sort((p1, p2) -> Integer.compare(p1.getOrdem(), p2.getOrdem()));
                otmCadastroRestricaoColunas.setData(otmCadastroRestricoes.getValue(tpCadastroRestricoes.convertRowIndexToModel(tpCadastroRestricoes.getSelectedRow())).getRestricaoColunas());

                tpCadastroRestricaoColunas.packAll();
            } else {
                otmCadastroRestricaoColunas.clear();
            }
        });

        csRelacionamentoTabelaRelacionada.addCampoConsultaListener(this);

//        csColunaColuna.setDocument(new PlainDocument() {
//            @Override
//            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
//                //super.insertString(offs, str.toUpperCase(), a);
//                System.out.println("offs: " + offs + " str: " + str);
//
//                textoColunaOriginal = textoColunaOriginal + str;
//                
//                str = Normalizer.normalize(str, Normalizer.Form.NFD);
//                str = str.replaceAll("[^\\p{ASCII}]", "");
//                
//                super.insertString(offs, str, a);
//            }
//            
//           
//        });
        csColunaColuna.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (csColunaColuna.getString().length() > 0) {
                    if (csColunaTituloCurto.getString().length() == 0) {
                        csColunaTituloCurto.setString(csColunaColuna.getString());
                        csColunaTituloLongo.setString(csColunaColuna.getString());
                        csColunaDescricao.setString(csColunaColuna.getString());
                    }
                    String textoNovo = Normalizer.normalize(csColunaColuna.getString(), Normalizer.Form.NFD);
                    textoNovo = textoNovo.replaceAll("[^\\p{ASCII}]", "").replaceAll(" ", "_").replace(".", "");
                    csColunaColuna.setString(textoNovo);

                }
            }
        });

        setarRotulos();

        Utilitarios.considerarEnterComoTab(jcbcsCadastroColunaObrigatorio);

        tpConsultaTabelas.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }

            if (tpConsultaTabelas.getLinhaSelecionada() >= 0) {
                Tabela tabelaSelecionada = otmConsultaTabela.getValue(tpConsultaTabelas.getLinhaSelecionada());

                otmConsultaColuna.setData(tabelaSelecionada.getColunas());
                otmConsultaChave.setData(tabelaSelecionada.getChaves());
                otmConsultaIndices.setData(tabelaSelecionada.getIndices());
                otmConsultaRelacionamentos.setData(tabelaSelecionada.getRelacionamentos());
                otmConsultaRestricoes.setData(tabelaSelecionada.getRestricoes());

                tpConsultaColunas.packAll();
                tpConsultaChave.packAll();
                tpConsultaIndices.packAll();
                tpConsultaRelacionamentos.packAll();
                tpConsultaRestricoes.packAll();
            } else {
                otmConsultaColuna.clear();
                otmConsultaChave.clear();
                otmConsultaIndices.clear();
                otmConsultaRelacionamentos.clear();
                otmConsultaRestricoes.clear();
            }
        });
    }

    public void setarRotulos() {
        clTabelaSchema.setComponenteRotulo(jlTabelaSchema);
        csTabelaNome.setComponenteRotulo(jlTabelaNome);
        csTabelaDescricao.setComponenteRotulo(jlTabelaDescricao);
        //clTabelaChave.setJlRotulo(jlTabelaChave);

        clChaveColuna.setComponenteRotulo(jlChaveColuna);

        csColunaColuna.setComponenteRotulo(jlColunaColuna);
        csColunaDescricao.setComponenteRotulo(jlColunaDescricao);
        csColunaLista.setComponenteRotulo(jlColunaLista);
        csColunaMascara.setComponenteRotulo(jlColunaMascara);
        csColunaPrecisao.setComponenteRotulo(jlColunaPrecisao);
        csColunaTituloCurto.setComponenteRotulo(jlColunaTituloCurto);
        csColunaTituloLongo.setComponenteRotulo(jlColunaTituloLongo);
        csColunaTamanho.setComponenteRotulo(jlColunaTamanho);
        csColunaValorPadrao.setComponenteRotulo(jlColunaValorPadrao);
        clColunaTipo.setComponenteRotulo(jlColunaTipo);
        csColunaLista.setComponenteRotulo(jlColunaLista);

        csIndiceNome.setComponenteRotulo(jlIndiceNome);
        csIndiceDescricao.setComponenteRotulo(jlIndiceDescricao);
        clIndiceColuna.setComponenteRotulo(jlIndiceColuna);
        clIndiceColunaTipoOrdem.setComponenteRotulo(jlIndiceTipoOrdem);

        csRelacionamentoNome.setComponenteRotulo(jlRelacionamentoNome);
        csRelacionamentoTabelaRelacionada.setComponenteRotulo(jlRelacionamentoTabelaRelacionada);
        clRelacionamentoColunaOrigem.setComponenteRotulo(jlRelacionamentoColunaOrigem);
        clRelacionamentoColunaRelacionada.setComponenteRotulo(jlRelacionamentoColunaRelacionada);

        //csColunaColuna.setJlRotulo(jlColunaColuna);
    }

    public void criarListasValidacao() {
        listaCampoTabela.add(clTabelaSchema);
        listaCampoTabela.add(csTabelaNome);
        listaCampoTabela.add(csTabelaDescricao);
        //listaCampoTabela.add(clTabelaChave);
        listaCampoIndice.add(csIndiceNome);
        listaCampoIndice.add(csIndiceDescricao);
        listaCampoIndiceColunas.add(clIndiceColuna);

        listaCampoTabelaColunas.add(csColunaColuna);
        listaCampoTabelaColunas.add(csColunaTituloCurto);
        listaCampoTabelaColunas.add(csColunaTituloLongo);
        listaCampoTabelaColunas.add(csColunaDescricao);
        listaCampoTabelaColunas.add(clColunaTipo);
        listaCampoTabelaColunas.add(csColunaTamanho);
        listaCampoTabelaColunas.add(csColunaMascara);
        listaCampoTabelaColunas.add(csColunaLista);
        listaCampoTabelaColunas.add(csColunaValorPadrao);

        listaCampoTabelaChave.add(clChaveColuna);

        listaCampoRelacionamento.add(csRelacionamentoNome);
        listaCampoRelacionamento.add(csRelacionamentoTabelaRelacionada);

        listaCampoRelacionamentoColunas.add(clRelacionamentoColunaOrigem);
        listaCampoRelacionamentoColunas.add(clRelacionamentoColunaRelacionada);

        listaCampoRestricao.add(csRestricaoNome);
        listaCampoRestricao.add(csRestricaoDescricao);
        listaCampoRestricao.add(clRestricaoIndice);

        listaCampoRestricaoColunas.add(clRestricaoColuna);
    }

    public TabelaControl getTabelaControl() {
        return tabelaControl;
    }

    public void setTabelaControl(TabelaControl tabelaControl) {
        this.tabelaControl = tabelaControl;
    }

    /**
     * Controle de Abas
     *
     * @param habilitada
     */
    @Override
    public void habilitaCadastro(boolean habilitada) {
        jtpTabela.setEnabledAt(0, !habilitada);
        jtpTabela.setEnabledAt(1, habilitada);
        jtpTabela.setSelectedIndex(habilitada ? 1 : 0);

        jtpCadastro.setSelectedIndex(0);
    }

    /**
     * Controle de componentes
     *
     * @param habilita
     */
    @Override
    public void habilitaComponentesCadastro(boolean habilita) {
        habilitaPainel(habilita, jpCadastro);

        tpCadastroColunas.setEnabled(habilita);
        tpCadastroIndices.setEnabled(habilita);
        //jxtCadastroRelacionamentos.setEnabled(habilita);
    }

    public void setarDadosTabela() {
//        tabelaControl.getTabelaDigitada().setSchema(csSchema.getString());
//        tabelaControl.getTabelaDigitada().setTabela(csTabela.getString());
//        tabelaControl.getTabelaDigitada().setDescricao(csDescricao.getString());
//        tabelaControl.getTabelaDigitada().setChave(csChave.getString());

        tabelaControl.getTabela().setSchema(clTabelaSchema.getSelectedItem().toString());
        tabelaControl.getTabela().setTabela(csTabelaNome.getString());
        tabelaControl.getTabela().setDescricao(csTabelaDescricao.getString());
    }

    public void carregarDadosTabela() {
        clTabelaSchema.setSelectedItem(tabelaControl.getTabela().getSchema());
        csTabelaNome.setString(tabelaControl.getTabela().getTabela());
        csTabelaDescricao.setString(tabelaControl.getTabela().getDescricao());

        cotmCadastroColunas.setData(tabelaControl.getTabela().getColunas());
        otmCadastroChave.setData(tabelaControl.getTabela().getChaves());
        otmCadastroColunas.setData(tabelaControl.getTabela().getColunas());
        otmCadastroIndices.setData(tabelaControl.getTabela().getIndices());
        otmCadastroRelacionamentos.setData(tabelaControl.getTabela().getRelacionamentos());
        otmCadastroRestricoes.setData(tabelaControl.getTabela().getRestricoes());

        SwingUtilities.invokeLater(() -> {
            tpCadastroColunas.packAll();
            tpCadastroIndices.packAll();
            tpCadastroRelacionamentos.packAll();
            tpConsultaChave.packAll();
            tpCadastroRestricoes.packAll();
        });

        atualizarCombosBox();
    }

    public void limparCampos() {
        limparListaCampos(listaCampoTabela);
        limparListaCampos(listaCampoTabelaColunas);
        limparListaCampos(listaCampoTabelaChave);
        limparListaCampos(listaCampoIndice);
        limparListaCampos(listaCampoIndiceColunas);
        limparListaCampos(listaCampoRelacionamento);
        limparListaCampos(listaCampoRelacionamentoColunas);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtpTabela = new javax.swing.JTabbedPane();
        jpConsulta = new javax.swing.JPanel();
        jpFiltros = new javax.swing.JPanel();
        jlFiltroTabela = new br.com.martinello.componentesbasicos.Rotulo();
        jlFiltroDescricao = new br.com.martinello.componentesbasicos.Rotulo();
        bIncluir = new br.com.martinello.componentesbasicos.Botao();
        bAlterar = new br.com.martinello.componentesbasicos.Botao();
        bPesquisar = new br.com.martinello.componentesbasicos.Botao();
        bExcluir = new br.com.martinello.componentesbasicos.Botao();
        cscFiltroTabela = new br.com.martinello.componentesbasicos.consulta.CampoStringConsulta();
        cscFiltroDescricao = new br.com.martinello.componentesbasicos.consulta.CampoStringConsulta();
        bImprimir = new br.com.martinello.componentesbasicos.Botao();
        jSplitPane = new javax.swing.JSplitPane();
        jspConsultaTabelas = new javax.swing.JScrollPane();
        tpConsultaTabelas = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jtpConsulta = new javax.swing.JTabbedPane();
        jspConsultaColunas = new javax.swing.JScrollPane();
        tpConsultaColunas = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jspConsultaChave = new javax.swing.JScrollPane();
        tpConsultaChave = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jspIndices = new javax.swing.JScrollPane();
        tpConsultaIndices = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jspRelacionamentos = new javax.swing.JScrollPane();
        tpConsultaRelacionamentos = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jScrollPane3 = new javax.swing.JScrollPane();
        tpConsultaRelacioamentosPor = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jspConsultaRestricoes = new javax.swing.JScrollPane();
        tpConsultaRestricoes = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jpCadastro = new javax.swing.JPanel();
        jpCadastroTabela = new javax.swing.JPanel();
        jlTabelaSchema = new br.com.martinello.componentesbasicos.Rotulo();
        jlTabelaNome = new br.com.martinello.componentesbasicos.Rotulo();
        jlTabelaDescricao = new br.com.martinello.componentesbasicos.Rotulo();
        jbSalvar = new br.com.martinello.componentesbasicos.Botao();
        jbCancelar = new br.com.martinello.componentesbasicos.Botao();
        csTabelaNome = new br.com.martinello.componentesbasicos.CampoString(Tabela.class,"tabela");
        csTabelaDescricao = new br.com.martinello.componentesbasicos.CampoString(Tabela.class,"descricao");
        clTabelaSchema = new br.com.martinello.componentesbasicos.CampoListaSimples(Tabela.class, "schema");
        jtpCadastro = new javax.swing.JTabbedPane();
        jspCadastroColunas = new javax.swing.JSplitPane();
        jpTabelaCadastroColunas = new javax.swing.JPanel();
        jspTabelaCadastroColunas = new javax.swing.JScrollPane();
        tpCadastroColunas = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jbAdicionarColuna = new br.com.martinello.componentesbasicos.Botao();
        jbAlterarColuna = new br.com.martinello.componentesbasicos.Botao();
        jbRemoverColuna = new br.com.martinello.componentesbasicos.Botao();
        jbCancelarColuna = new br.com.martinello.componentesbasicos.Botao();
        jpCadastroColunas = new javax.swing.JPanel();
        jlColunaTituloLongo = new br.com.martinello.componentesbasicos.Rotulo();
        jlColunaDescricao = new br.com.martinello.componentesbasicos.Rotulo();
        csColunaDescricao = new br.com.martinello.componentesbasicos.CampoString(Coluna.class,"descricao","Descrição");
        jlColunaTipo = new br.com.martinello.componentesbasicos.Rotulo();
        jlColunaTamanho = new br.com.martinello.componentesbasicos.Rotulo();
        csColunaTamanho = new br.com.martinello.componentesbasicos.CampoString(Coluna.class,"tamanho","Tamanho");
        jlColunaPrecisao = new br.com.martinello.componentesbasicos.Rotulo();
        csColunaPrecisao = new br.com.martinello.componentesbasicos.CampoString(Coluna.class,"precisao","Precisão");
        jlColunaMascara = new br.com.martinello.componentesbasicos.Rotulo();
        csColunaMascara = new br.com.martinello.componentesbasicos.CampoString(Coluna.class,"mascara","Máscara");
        jlColunaLista = new br.com.martinello.componentesbasicos.Rotulo();
        jlColunaObrigatorio = new br.com.martinello.componentesbasicos.Rotulo();
        jlColunaValorPadrao = new br.com.martinello.componentesbasicos.Rotulo();
        csColunaValorPadrao = new br.com.martinello.componentesbasicos.CampoString(Coluna.class,"valorPadrao","Valor Padrão");
        jcbcsCadastroColunaObrigatorio = new javax.swing.JCheckBox();
        jlDescricaoLista = new br.com.martinello.componentesbasicos.Rotulo();
        csColunaTituloLongo = new br.com.martinello.componentesbasicos.CampoString(Coluna.class,"tituloLongo","Título Longo");
        csColunaTituloCurto = new br.com.martinello.componentesbasicos.CampoString(Coluna.class,"tituloCurto","Título Curto");
        jlColunaTituloCurto = new br.com.martinello.componentesbasicos.Rotulo();
        csColunaColuna = new br.com.martinello.componentesbasicos.CampoString(Coluna.class,"coluna","Coluna");
        jlColunaColuna = new br.com.martinello.componentesbasicos.Rotulo();
        clColunaTipo = new br.com.martinello.componentesbasicos.CampoListaSimples(Coluna.class, "tipo");
        csColunaLista = new br.com.martinello.gerenciador.componentes.CampoConsulta(new ConsultavelLista(Lista.class, "idLista"), Coluna.class, "lista", "Lista", jlDescricaoLista,"");
        jPanel1 = new javax.swing.JPanel();
        jlChaveColuna = new br.com.martinello.componentesbasicos.Rotulo();
        clChaveColuna = new br.com.martinello.componentesbasicos.CampoListaSimples(IndiceColuna.class,"coluna");
        jScrollPane1 = new javax.swing.JScrollPane();
        tpCadastroChave = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jbAdicionarChaveColuna = new br.com.martinello.componentesbasicos.Botao();
        jbRemoverChaveColuna = new br.com.martinello.componentesbasicos.Botao();
        jbSubirChaveColuna = new br.com.martinello.componentesbasicos.Botao();
        jbBaixarChaveColuna = new br.com.martinello.componentesbasicos.Botao();
        jspCadastroIndices = new javax.swing.JSplitPane();
        jpCadastroIndices = new javax.swing.JPanel();
        jlIndiceNome = new br.com.martinello.componentesbasicos.Rotulo();
        jlIndiceDescricao = new br.com.martinello.componentesbasicos.Rotulo();
        csIndiceNome = new br.com.martinello.componentesbasicos.CampoString(Indice.class,"indice","Índice");
        csIndiceDescricao = new br.com.martinello.componentesbasicos.CampoString(Indice.class,"descricao","Descrição");
        jspTabelaCadastroIndices = new javax.swing.JScrollPane();
        tpCadastroIndices = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jbAdicionarIndice = new br.com.martinello.componentesbasicos.Botao();
        jbAlterarIndice = new br.com.martinello.componentesbasicos.Botao();
        jbRemoverIndice = new br.com.martinello.componentesbasicos.Botao();
        jpCadastroIndiceColunas = new javax.swing.JPanel();
        jspCadastroIndiceColunas = new javax.swing.JScrollPane();
        tpCadastroIndiceColunas = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jbAdicionarIndiceColuna = new br.com.martinello.componentesbasicos.Botao();
        jbRemoverIndiceColuna = new br.com.martinello.componentesbasicos.Botao();
        jbSubirIndiceColuna = new br.com.martinello.componentesbasicos.Botao();
        jbBaixarIndiceColuna = new br.com.martinello.componentesbasicos.Botao();
        jlIndiceColuna = new br.com.martinello.componentesbasicos.Rotulo();
        clIndiceColuna = new br.com.martinello.componentesbasicos.CampoListaSimples(IndiceColuna.class,"coluna");
        jlIndiceTipoOrdem = new br.com.martinello.componentesbasicos.Rotulo();
        clIndiceColunaTipoOrdem = new br.com.martinello.componentesbasicos.CampoListaSimples(IndiceColuna.class, "tipoOrdem");
        jspCadastroRelacionamentos = new javax.swing.JSplitPane();
        jpCadastroRelacionamentos = new javax.swing.JPanel();
        jlRelacionamentoNome = new br.com.martinello.componentesbasicos.Rotulo();
        jlRelacionamentoTabelaRelacionada = new br.com.martinello.componentesbasicos.Rotulo();
        csRelacionamentoNome = new br.com.martinello.componentesbasicos.CampoString(Relacionamento.class,"nome","Nome");
        jspTabelaCadastroRelacionamentos = new javax.swing.JScrollPane();
        tpCadastroRelacionamentos = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jbAdicionarRelacionamento = new br.com.martinello.componentesbasicos.Botao();
        jbAlterarRelacionamento = new br.com.martinello.componentesbasicos.Botao();
        jbRemoverRelacionamento = new br.com.martinello.componentesbasicos.Botao();
        jlDescricaoTabelaRelacionada = new br.com.martinello.componentesbasicos.Rotulo();
        csRelacionamentoTabelaRelacionada = new br.com.martinello.gerenciador.componentes.CampoConsulta(new ConsultavelTabela(Tabela.class, "idTabela"), Relacionamento.class, "tabelaRelacionada","Tabela Relacionada", jlDescricaoTabelaRelacionada,filtroTabelaRelacionada);
        jpCadastroRelacionamentosColunas = new javax.swing.JPanel();
        jlRelacionamentoColunaOrigem = new br.com.martinello.componentesbasicos.Rotulo();
        jspCadastroRelacionamentoColunas = new javax.swing.JScrollPane();
        tpCadastroRelacionamentoColunas = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jbAdicionarRelacionamentoColuna = new br.com.martinello.componentesbasicos.Botao();
        jbRemoverRelacionamentoColuna = new br.com.martinello.componentesbasicos.Botao();
        jlRelacionamentoColunaRelacionada = new br.com.martinello.componentesbasicos.Rotulo();
        clRelacionamentoColunaOrigem = new br.com.martinello.componentesbasicos.CampoListaSimples(RelacionamentoColuna.class,"colunaOrigem");
        clRelacionamentoColunaRelacionada = new br.com.martinello.componentesbasicos.CampoListaSimples(RelacionamentoColuna.class,"colunaRelacionada");
        jspCadastroRestricoes = new javax.swing.JSplitPane();
        ppCadastroRestricoes = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jlRestricaoDescricao = new br.com.martinello.componentesbasicos.Rotulo();
        csRestricaoNome = new br.com.martinello.componentesbasicos.CampoString(Indice.class,"indice","Índice");
        csRestricaoDescricao = new br.com.martinello.componentesbasicos.CampoString(Indice.class,"descricao","Descrição");
        jspTabelaCadastroRestricoes = new javax.swing.JScrollPane();
        tpCadastroRestricoes = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jbAdicionarRestricao = new br.com.martinello.componentesbasicos.Botao();
        jbAlterarRestricao = new br.com.martinello.componentesbasicos.Botao();
        jbRemoverRestricao = new br.com.martinello.componentesbasicos.Botao();
        jlRestricaoNome = new br.com.martinello.componentesbasicos.Rotulo();
        jlRestricaoIndice = new br.com.martinello.componentesbasicos.Rotulo();
        clRestricaoIndice = new br.com.martinello.componentesbasicos.CampoListaSimples(RelacionamentoColuna.class,"colunaOrigem");
        ppCadastroRestricoesColunas = new br.com.martinello.componentesbasicos.paineis.PainelPadrao();
        jbAdicionarRestricaoColuna = new br.com.martinello.componentesbasicos.Botao();
        jbRemoverRestricaoColuna = new br.com.martinello.componentesbasicos.Botao();
        jbSubirRestricaoColuna = new br.com.martinello.componentesbasicos.Botao();
        jbBaixarRestricaoColuna = new br.com.martinello.componentesbasicos.Botao();
        jlIndiceColuna1 = new br.com.martinello.componentesbasicos.Rotulo();
        clRestricaoColuna = new br.com.martinello.componentesbasicos.CampoListaSimples(IndiceColuna.class,"coluna");
        jspCadastroRestricaoColunas = new javax.swing.JScrollPane();
        tpCadastroRestricaoColunas = new br.com.martinello.componentesbasicos.TabelaPadrao();
        jpsStatusBar = new br.com.martinello.componentesbasicos.paineis.JPStatus();

        setClosable(true);
        setIconifiable(true);
        setTitle("Gerenciamento de Tabelas");

        jpFiltros.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros da Pesquisa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, ConstantesGlobais.FONTE_11_NORMAL));

        jlFiltroTabela.setText("Tabela:");

        jlFiltroDescricao.setText("Descrição:");

        bIncluir.setMnemonic('i');
        bIncluir.setText("Incluir");
        bIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bIncluirActionPerformed(evt);
            }
        });

        bAlterar.setMnemonic('a');
        bAlterar.setText("Alterar");
        bAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAlterarActionPerformed(evt);
            }
        });

        bPesquisar.setMnemonic('p');
        bPesquisar.setText("Pesquisar");
        bPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPesquisarActionPerformed(evt);
            }
        });

        bExcluir.setMnemonic('e');
        bExcluir.setText("Excluir");
        bExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExcluirActionPerformed(evt);
            }
        });

        bImprimir.setMnemonic('e');
        bImprimir.setText("Imprimir");
        bImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpFiltrosLayout = new javax.swing.GroupLayout(jpFiltros);
        jpFiltros.setLayout(jpFiltrosLayout);
        jpFiltrosLayout.setHorizontalGroup(
            jpFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpFiltrosLayout.createSequentialGroup()
                .addGroup(jpFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpFiltrosLayout.createSequentialGroup()
                        .addGroup(jpFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlFiltroDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlFiltroTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cscFiltroDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jpFiltrosLayout.createSequentialGroup()
                                .addComponent(cscFiltroTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 568, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpFiltrosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jpFiltrosLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jlFiltroDescricao, jlFiltroTabela});

        jpFiltrosLayout.setVerticalGroup(
            jpFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpFiltrosLayout.createSequentialGroup()
                .addGroup(jpFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlFiltroTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cscFiltroTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlFiltroDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cscFiltroDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jpFiltrosLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cscFiltroDescricao, cscFiltroTabela, jlFiltroDescricao, jlFiltroTabela});

        jSplitPane.setDividerLocation(250);
        jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jspConsultaTabelas.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jspConsultaTabelas.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jspConsultaTabelas.setPreferredSize(new java.awt.Dimension(319, 200));

        tpConsultaTabelas.setModel(otmConsultaTabela);
        tpConsultaTabelas.setEditable(false);
        tpConsultaTabelas.setPreferredScrollableViewportSize(new java.awt.Dimension(300, 200));
        tpConsultaTabelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tpConsultaTabelasMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tpConsultaTabelasMouseReleased(evt);
            }
        });
        jspConsultaTabelas.setViewportView(tpConsultaTabelas);

        jSplitPane.setTopComponent(jspConsultaTabelas);

        tpConsultaColunas.setModel(otmConsultaColuna);
        tpConsultaColunas.setEditable(false);
        jspConsultaColunas.setViewportView(tpConsultaColunas);

        jtpConsulta.addTab("Colunas", jspConsultaColunas);

        tpConsultaChave.setModel(otmConsultaChave);
        jspConsultaChave.setViewportView(tpConsultaChave);

        jtpConsulta.addTab("Chave", jspConsultaChave);

        tpConsultaIndices.setModel(otmConsultaIndices);
        tpConsultaIndices.setEditable(false);
        jspIndices.setViewportView(tpConsultaIndices);

        jtpConsulta.addTab("Indices", jspIndices);

        tpConsultaRelacionamentos.setModel(otmConsultaRelacionamentos);
        tpConsultaRelacionamentos.setEditable(false);
        jspRelacionamentos.setViewportView(tpConsultaRelacionamentos);

        jtpConsulta.addTab("Relacionamentos", jspRelacionamentos);

        tpConsultaRelacioamentosPor.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tpConsultaRelacioamentosPor);

        jtpConsulta.addTab("Relacionada por", jScrollPane3);

        tpConsultaRestricoes.setModel(otmConsultaRestricoes);
        jspConsultaRestricoes.setViewportView(tpConsultaRestricoes);

        jtpConsulta.addTab("Restrições", jspConsultaRestricoes);

        jSplitPane.setRightComponent(jtpConsulta);

        javax.swing.GroupLayout jpConsultaLayout = new javax.swing.GroupLayout(jpConsulta);
        jpConsulta.setLayout(jpConsultaLayout);
        jpConsultaLayout.setHorizontalGroup(
            jpConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpFiltros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane)
        );
        jpConsultaLayout.setVerticalGroup(
            jpConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpConsultaLayout.createSequentialGroup()
                .addComponent(jpFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE))
        );

        jtpTabela.addTab("Consulta", jpConsulta);

        jbSalvar.setMnemonic('s');
        jbSalvar.setText("Salvar");
        jbSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalvarActionPerformed(evt);
            }
        });

        jbCancelar.setMnemonic('c');
        jbCancelar.setText("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        csTabelaNome.setComponenteRotulo(jlTabelaNome);
        csTabelaNome.setDescricaoRotulo("Tabela");
        csTabelaNome.setObrigatorio(true);

        csTabelaDescricao.setComponenteRotulo(jlTabelaDescricao);
        csTabelaDescricao.setDescricaoRotulo("Descrição");
        csTabelaDescricao.setObrigatorio(true);

        clTabelaSchema.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "public", "sistema" }));
        clTabelaSchema.setComponenteRotulo(jlTabelaSchema);
        clTabelaSchema.setDescricaoRotulo("Schema");
        clTabelaSchema.setObrigatorio(true);

        javax.swing.GroupLayout jpCadastroTabelaLayout = new javax.swing.GroupLayout(jpCadastroTabela);
        jpCadastroTabela.setLayout(jpCadastroTabelaLayout);
        jpCadastroTabelaLayout.setHorizontalGroup(
            jpCadastroTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroTabelaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastroTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jlTabelaNome, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(jlTabelaSchema, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(jlTabelaDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCadastroTabelaLayout.createSequentialGroup()
                        .addGroup(jpCadastroTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpCadastroTabelaLayout.createSequentialGroup()
                                .addComponent(jbSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(clTabelaSchema, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jpCadastroTabelaLayout.createSequentialGroup()
                        .addGroup(jpCadastroTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(csTabelaDescricao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
                            .addComponent(csTabelaNome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jpCadastroTabelaLayout.setVerticalGroup(
            jpCadastroTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroTabelaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastroTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlTabelaSchema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clTabelaSchema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlTabelaNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csTabelaNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(csTabelaDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlTabelaDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jspCadastroColunas.setDividerLocation(601);
        jspCadastroColunas.setDividerSize(4);

        tpCadastroColunas.setModel(otmCadastroColunas);
        tpCadastroColunas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tpCadastroColunasMouseClicked(evt);
            }
        });
        jspTabelaCadastroColunas.setViewportView(tpCadastroColunas);

        jbAdicionarColuna.setMnemonic('a');
        jbAdicionarColuna.setText("Adicionar");
        jbAdicionarColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarColunaActionPerformed(evt);
            }
        });

        jbAlterarColuna.setMnemonic('l');
        jbAlterarColuna.setText("Alterar");
        jbAlterarColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAlterarColunaActionPerformed(evt);
            }
        });

        jbRemoverColuna.setMnemonic('r');
        jbRemoverColuna.setText("Remover");
        jbRemoverColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverColunaActionPerformed(evt);
            }
        });

        jbCancelarColuna.setMnemonic('n');
        jbCancelarColuna.setText("Cancelar");
        jbCancelarColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarColunaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpTabelaCadastroColunasLayout = new javax.swing.GroupLayout(jpTabelaCadastroColunas);
        jpTabelaCadastroColunas.setLayout(jpTabelaCadastroColunasLayout);
        jpTabelaCadastroColunasLayout.setHorizontalGroup(
            jpTabelaCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTabelaCadastroColunasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTabelaCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpTabelaCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jbAdicionarColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbAlterarColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbRemoverColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jbCancelarColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jspTabelaCadastroColunas, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE))
        );
        jpTabelaCadastroColunasLayout.setVerticalGroup(
            jpTabelaCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTabelaCadastroColunasLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jbAdicionarColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbAlterarColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbRemoverColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbCancelarColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jspTabelaCadastroColunas, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
        );

        jspCadastroColunas.setRightComponent(jpTabelaCadastroColunas);

        jlColunaTituloLongo.setText("Título Longo:");

        jlColunaDescricao.setText("Descrição:");

        csColunaDescricao.setObrigatorio(true);

        jlColunaTipo.setText("Tipo:");

        jlColunaTamanho.setText("Tamanho:");

        jlColunaPrecisao.setText("Precisão:");

        jlColunaMascara.setText("Máscara:");

        jlColunaLista.setText("Lista:");

        jlColunaObrigatorio.setText("Obrigatório:");

        jlColunaValorPadrao.setText("Valor Padrão:");

        csColunaValorPadrao.setNextFocusableComponent(jbAdicionarColuna);

        jlDescricaoLista.setText(" ");

        csColunaTituloLongo.setObrigatorio(true);

        csColunaTituloCurto.setObrigatorio(true);

        jlColunaTituloCurto.setText("Título Curto:");

        csColunaColuna.setObrigatorio(true);

        jlColunaColuna.setText("Coluna:");

        clColunaTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Text", "Integer", "Serial", "Date", "Numeric", "Time", "TimeStamp", "Bytea" }));
        clColunaTipo.setDescricaoRotulo("Tipo");
        clColunaTipo.setObrigatorio(true);

        javax.swing.GroupLayout jpCadastroColunasLayout = new javax.swing.GroupLayout(jpCadastroColunas);
        jpCadastroColunas.setLayout(jpCadastroColunasLayout);
        jpCadastroColunasLayout.setHorizontalGroup(
            jpCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroColunasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jlColunaValorPadrao, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlColunaObrigatorio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlColunaLista, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlColunaMascara, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlColunaPrecisao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlColunaTamanho, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlColunaTipo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlColunaDescricao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlColunaTituloLongo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlColunaTituloCurto, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlColunaColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(csColunaTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpCadastroColunasLayout.createSequentialGroup()
                        .addComponent(csColunaLista, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlDescricaoLista, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jcbcsCadastroColunaObrigatorio)
                    .addComponent(csColunaValorPadrao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(csColunaPrecisao, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csColunaDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(csColunaTituloLongo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(csColunaTituloCurto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(csColunaMascara, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clColunaTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csColunaColuna, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jpCadastroColunasLayout.setVerticalGroup(
            jpCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroColunasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(csColunaColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlColunaColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(csColunaTituloCurto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlColunaTituloCurto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlColunaTituloLongo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csColunaTituloLongo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlColunaDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csColunaDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlColunaTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clColunaTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlColunaTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csColunaTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlColunaPrecisao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csColunaPrecisao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlColunaMascara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csColunaMascara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlColunaLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlDescricaoLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csColunaLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbcsCadastroColunaObrigatorio)
                    .addComponent(jlColunaObrigatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlColunaValorPadrao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csColunaValorPadrao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(212, Short.MAX_VALUE))
        );

        jspCadastroColunas.setLeftComponent(jpCadastroColunas);

        jtpCadastro.addTab("Colunas", jspCadastroColunas);

        jlChaveColuna.setText("Coluna:");

        clChaveColuna.setModel(dcbmColunaChave);
        clChaveColuna.setDescricaoRotulo("Coluna");
        clChaveColuna.setNextFocusableComponent(jbAdicionarChaveColuna);
        clChaveColuna.setObrigatorio(true);

        tpCadastroChave.setModel(otmCadastroChave);
        jScrollPane1.setViewportView(tpCadastroChave);

        jbAdicionarChaveColuna.setMnemonic('a');
        jbAdicionarChaveColuna.setText("Adicionar");
        jbAdicionarChaveColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarChaveColunaActionPerformed(evt);
            }
        });

        jbRemoverChaveColuna.setMnemonic('r');
        jbRemoverChaveColuna.setText("Remover");
        jbRemoverChaveColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverChaveColunaActionPerformed(evt);
            }
        });

        jbSubirChaveColuna.setMnemonic('u');
        jbSubirChaveColuna.setText("Subir");
        jbSubirChaveColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSubirChaveColunaActionPerformed(evt);
            }
        });

        jbBaixarChaveColuna.setMnemonic('b');
        jbBaixarChaveColuna.setText("Baixar");
        jbBaixarChaveColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBaixarChaveColunaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlChaveColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(clChaveColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbAdicionarChaveColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbRemoverChaveColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbSubirChaveColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbBaixarChaveColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(694, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlChaveColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clChaveColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbAdicionarChaveColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRemoverChaveColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbSubirChaveColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbBaixarChaveColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(82, Short.MAX_VALUE))
        );

        jtpCadastro.addTab("Chave", jPanel1);

        jspCadastroIndices.setDividerLocation(540);
        jspCadastroIndices.setDividerSize(4);

        jlIndiceNome.setText("Índice:");

        jlIndiceDescricao.setText("Descrição:");

        csIndiceNome.setObrigatorio(true);

        csIndiceDescricao.setNextFocusableComponent(jbAdicionarIndice);
        csIndiceDescricao.setObrigatorio(true);

        tpCadastroIndices.setModel(otmCadastroIndices);
        tpCadastroIndices.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tpCadastroIndicesMouseClicked(evt);
            }
        });
        jspTabelaCadastroIndices.setViewportView(tpCadastroIndices);

        jbAdicionarIndice.setMnemonic('a');
        jbAdicionarIndice.setText("Adicionar");
        jbAdicionarIndice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarIndiceActionPerformed(evt);
            }
        });

        jbAlterarIndice.setMnemonic('l');
        jbAlterarIndice.setText("Alterar");
        jbAlterarIndice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAlterarIndiceActionPerformed(evt);
            }
        });

        jbRemoverIndice.setMnemonic('r');
        jbRemoverIndice.setText("Remover");
        jbRemoverIndice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverIndiceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpCadastroIndicesLayout = new javax.swing.GroupLayout(jpCadastroIndices);
        jpCadastroIndices.setLayout(jpCadastroIndicesLayout);
        jpCadastroIndicesLayout.setHorizontalGroup(
            jpCadastroIndicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroIndicesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastroIndicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jspTabelaCadastroIndices, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                    .addGroup(jpCadastroIndicesLayout.createSequentialGroup()
                        .addGroup(jpCadastroIndicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jlIndiceDescricao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                            .addComponent(jlIndiceNome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpCadastroIndicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(csIndiceDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                            .addComponent(csIndiceNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroIndicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbAdicionarIndice, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAlterarIndice, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbRemoverIndice, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpCadastroIndicesLayout.setVerticalGroup(
            jpCadastroIndicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroIndicesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastroIndicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlIndiceNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csIndiceNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroIndicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlIndiceDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csIndiceDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroIndicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCadastroIndicesLayout.createSequentialGroup()
                        .addComponent(jbAdicionarIndice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAlterarIndice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRemoverIndice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jspTabelaCadastroIndices, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)))
        );

        jspCadastroIndices.setLeftComponent(jpCadastroIndices);

        tpCadastroIndiceColunas.setModel(otmCadastroIndiceColunas);
        jspCadastroIndiceColunas.setViewportView(tpCadastroIndiceColunas);

        jbAdicionarIndiceColuna.setMnemonic('d');
        jbAdicionarIndiceColuna.setText("Adicionar");
        jbAdicionarIndiceColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarIndiceColunaActionPerformed(evt);
            }
        });

        jbRemoverIndiceColuna.setMnemonic('e');
        jbRemoverIndiceColuna.setText("Remover");
        jbRemoverIndiceColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverIndiceColunaActionPerformed(evt);
            }
        });

        jbSubirIndiceColuna.setMnemonic('u');
        jbSubirIndiceColuna.setText("Subir");
        jbSubirIndiceColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSubirIndiceColunaActionPerformed(evt);
            }
        });

        jbBaixarIndiceColuna.setMnemonic('b');
        jbBaixarIndiceColuna.setText("Baixar");
        jbBaixarIndiceColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBaixarIndiceColunaActionPerformed(evt);
            }
        });

        jlIndiceColuna.setText("Campo:");

        clIndiceColuna.setModel(dcbmColunaIndice);
        clIndiceColuna.setDescricaoRotulo("Campo");
        clIndiceColuna.setObrigatorio(true);

        jlIndiceTipoOrdem.setText("Ordem:");

        clIndiceColunaTipoOrdem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ASC", "DESC" }));
        clIndiceColunaTipoOrdem.setToolTipText("Índice btree\nUtilizado para indexar colunas que geralmente serão consultadas por intervalo. Por exemplo o campo salário, onde geralmente serão buscado as tuplas por um intervalo de valores desse campo.\n\nÍndice hash\nUtilizado para indexar colunas que serão consultadas por um valor exato. Por exemplo o campo RG que apesar de poder repetir, geralmente serão consultado as tuplas usando a condição de igualdade para esse campo.");
        clIndiceColunaTipoOrdem.setDescricaoRotulo("Ordem");
        clIndiceColunaTipoOrdem.setNextFocusableComponent(jbAdicionarIndiceColuna);
        clIndiceColunaTipoOrdem.setObrigatorio(true);

        javax.swing.GroupLayout jpCadastroIndiceColunasLayout = new javax.swing.GroupLayout(jpCadastroIndiceColunas);
        jpCadastroIndiceColunas.setLayout(jpCadastroIndiceColunasLayout);
        jpCadastroIndiceColunasLayout.setHorizontalGroup(
            jpCadastroIndiceColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroIndiceColunasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastroIndiceColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspCadastroIndiceColunas, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpCadastroIndiceColunasLayout.createSequentialGroup()
                        .addGroup(jpCadastroIndiceColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlIndiceTipoOrdem, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlIndiceColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpCadastroIndiceColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(clIndiceColuna, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(clIndiceColunaTipoOrdem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroIndiceColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbAdicionarIndiceColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbRemoverIndiceColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbSubirIndiceColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBaixarIndiceColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jpCadastroIndiceColunasLayout.setVerticalGroup(
            jpCadastroIndiceColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroIndiceColunasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastroIndiceColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlIndiceColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clIndiceColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroIndiceColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(clIndiceColunaTipoOrdem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlIndiceTipoOrdem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroIndiceColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCadastroIndiceColunasLayout.createSequentialGroup()
                        .addComponent(jbAdicionarIndiceColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRemoverIndiceColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbSubirIndiceColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbBaixarIndiceColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jspCadastroIndiceColunas, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE))
                .addContainerGap())
        );

        jspCadastroIndices.setRightComponent(jpCadastroIndiceColunas);

        jtpCadastro.addTab("Indices", jspCadastroIndices);

        jspCadastroRelacionamentos.setDividerLocation(625);
        jspCadastroRelacionamentos.setDividerSize(4);

        jlRelacionamentoNome.setText("Nome:");

        jlRelacionamentoTabelaRelacionada.setText("Tabela Relacionada:");

        csRelacionamentoNome.setObrigatorio(true);

        tpCadastroRelacionamentos.setModel(otmCadastroRelacionamentos);
        tpCadastroRelacionamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tpCadastroRelacionamentosMouseClicked(evt);
            }
        });
        jspTabelaCadastroRelacionamentos.setViewportView(tpCadastroRelacionamentos);

        jbAdicionarRelacionamento.setMnemonic('a');
        jbAdicionarRelacionamento.setText("Adicionar");
        jbAdicionarRelacionamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarRelacionamentoActionPerformed(evt);
            }
        });

        jbAlterarRelacionamento.setMnemonic('l');
        jbAlterarRelacionamento.setText("Alterar");
        jbAlterarRelacionamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAlterarRelacionamentoActionPerformed(evt);
            }
        });

        jbRemoverRelacionamento.setMnemonic('r');
        jbRemoverRelacionamento.setText("Remover");
        jbRemoverRelacionamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverRelacionamentoActionPerformed(evt);
            }
        });

        jlDescricaoTabelaRelacionada.setText(" ");

        csRelacionamentoTabelaRelacionada.setNextFocusableComponent(jbAdicionarRelacionamento);
        csRelacionamentoTabelaRelacionada.setObrigatorio(true);

        javax.swing.GroupLayout jpCadastroRelacionamentosLayout = new javax.swing.GroupLayout(jpCadastroRelacionamentos);
        jpCadastroRelacionamentos.setLayout(jpCadastroRelacionamentosLayout);
        jpCadastroRelacionamentosLayout.setHorizontalGroup(
            jpCadastroRelacionamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroRelacionamentosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastroRelacionamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCadastroRelacionamentosLayout.createSequentialGroup()
                        .addGroup(jpCadastroRelacionamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlRelacionamentoTabelaRelacionada, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(jlRelacionamentoNome, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpCadastroRelacionamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpCadastroRelacionamentosLayout.createSequentialGroup()
                                .addComponent(csRelacionamentoTabelaRelacionada, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlDescricaoTabelaRelacionada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(csRelacionamentoNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jspTabelaCadastroRelacionamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroRelacionamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbAdicionarRelacionamento, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAlterarRelacionamento, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbRemoverRelacionamento, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );
        jpCadastroRelacionamentosLayout.setVerticalGroup(
            jpCadastroRelacionamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroRelacionamentosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastroRelacionamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlRelacionamentoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csRelacionamentoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroRelacionamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlRelacionamentoTabelaRelacionada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlDescricaoTabelaRelacionada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csRelacionamentoTabelaRelacionada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroRelacionamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCadastroRelacionamentosLayout.createSequentialGroup()
                        .addComponent(jbAdicionarRelacionamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAlterarRelacionamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRemoverRelacionamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 352, Short.MAX_VALUE))
                    .addComponent(jspTabelaCadastroRelacionamentos, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
                .addContainerGap())
        );

        jspCadastroRelacionamentos.setLeftComponent(jpCadastroRelacionamentos);

        jlRelacionamentoColunaOrigem.setText("Col. Origem:");

        tpCadastroRelacionamentoColunas.setModel(otmCadastroRelacionamentoColunas);
        jspCadastroRelacionamentoColunas.setViewportView(tpCadastroRelacionamentoColunas);

        jbAdicionarRelacionamentoColuna.setMnemonic('d');
        jbAdicionarRelacionamentoColuna.setText("Adicionar");
        jbAdicionarRelacionamentoColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarRelacionamentoColunaActionPerformed(evt);
            }
        });

        jbRemoverRelacionamentoColuna.setMnemonic('e');
        jbRemoverRelacionamentoColuna.setText("Remover");
        jbRemoverRelacionamentoColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverRelacionamentoColunaActionPerformed(evt);
            }
        });

        jlRelacionamentoColunaRelacionada.setText("Col. Rela.:");

        clRelacionamentoColunaOrigem.setModel(dcbmColunaRelacionamentoOrigem);
        clRelacionamentoColunaOrigem.setDescricaoRotulo("Col. Origem");
        clRelacionamentoColunaOrigem.setObrigatorio(true);

        clRelacionamentoColunaRelacionada.setModel(dcbmColunaRelacionamentoRelacionada);
        clRelacionamentoColunaRelacionada.setDescricaoRotulo("Col. Rela.");
        clRelacionamentoColunaRelacionada.setNextFocusableComponent(jbAdicionarRelacionamentoColuna);
        clRelacionamentoColunaRelacionada.setObrigatorio(true);

        javax.swing.GroupLayout jpCadastroRelacionamentosColunasLayout = new javax.swing.GroupLayout(jpCadastroRelacionamentosColunas);
        jpCadastroRelacionamentosColunas.setLayout(jpCadastroRelacionamentosColunasLayout);
        jpCadastroRelacionamentosColunasLayout.setHorizontalGroup(
            jpCadastroRelacionamentosColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroRelacionamentosColunasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastroRelacionamentosColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCadastroRelacionamentosColunasLayout.createSequentialGroup()
                        .addGroup(jpCadastroRelacionamentosColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jlRelacionamentoColunaRelacionada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlRelacionamentoColunaOrigem, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpCadastroRelacionamentosColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(clRelacionamentoColunaOrigem, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(clRelacionamentoColunaRelacionada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jpCadastroRelacionamentosColunasLayout.createSequentialGroup()
                        .addComponent(jspCadastroRelacionamentoColunas, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpCadastroRelacionamentosColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbAdicionarRelacionamentoColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbRemoverRelacionamentoColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpCadastroRelacionamentosColunasLayout.setVerticalGroup(
            jpCadastroRelacionamentosColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroRelacionamentosColunasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadastroRelacionamentosColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlRelacionamentoColunaOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clRelacionamentoColunaOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroRelacionamentosColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlRelacionamentoColunaRelacionada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clRelacionamentoColunaRelacionada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadastroRelacionamentosColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCadastroRelacionamentosColunasLayout.createSequentialGroup()
                        .addComponent(jbAdicionarRelacionamentoColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRemoverRelacionamentoColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 383, Short.MAX_VALUE))
                    .addComponent(jspCadastroRelacionamentoColunas, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE))
                .addContainerGap())
        );

        jspCadastroRelacionamentos.setRightComponent(jpCadastroRelacionamentosColunas);

        jtpCadastro.addTab("Relacionamentos", jspCadastroRelacionamentos);

        jspCadastroRestricoes.setDividerLocation(540);

        csRestricaoNome.setComponenteRotulo(jlRestricaoNome);
        csRestricaoNome.setDescricaoRotulo("Restrição");
        csRestricaoNome.setObrigatorio(true);

        csRestricaoDescricao.setComponenteRotulo(jlRestricaoDescricao);
        csRestricaoDescricao.setDescricaoRotulo("Descrição");
        csRestricaoDescricao.setObrigatorio(true);

        tpCadastroRestricoes.setModel(otmCadastroRestricoes);
        tpCadastroRestricoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tpCadastroRestricoesMouseClicked(evt);
            }
        });
        jspTabelaCadastroRestricoes.setViewportView(tpCadastroRestricoes);

        jbAdicionarRestricao.setMnemonic('a');
        jbAdicionarRestricao.setText("Adicionar");
        jbAdicionarRestricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarRestricaoActionPerformed(evt);
            }
        });

        jbAlterarRestricao.setMnemonic('l');
        jbAlterarRestricao.setText("Alterar");
        jbAlterarRestricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAlterarRestricaoActionPerformed(evt);
            }
        });

        jbRemoverRestricao.setMnemonic('r');
        jbRemoverRestricao.setText("Remover");
        jbRemoverRestricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverRestricaoActionPerformed(evt);
            }
        });

        clRestricaoIndice.setModel(dcbmColunaRelacionamentoOrigem);
        clRestricaoIndice.setComponenteRotulo(jlRestricaoIndice);
        clRestricaoIndice.setDescricaoRotulo("Índice");
        clRestricaoIndice.setNextFocusableComponent(jbAdicionarRestricao);

        javax.swing.GroupLayout ppCadastroRestricoesLayout = new javax.swing.GroupLayout(ppCadastroRestricoes);
        ppCadastroRestricoes.setLayout(ppCadastroRestricoesLayout);
        ppCadastroRestricoesLayout.setHorizontalGroup(
            ppCadastroRestricoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppCadastroRestricoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppCadastroRestricoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(ppCadastroRestricoesLayout.createSequentialGroup()
                        .addComponent(jlRestricaoIndice, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clRestricaoIndice, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE))
                    .addComponent(jspTabelaCadastroRestricoes, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ppCadastroRestricoesLayout.createSequentialGroup()
                        .addGroup(ppCadastroRestricoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jlRestricaoDescricao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                            .addComponent(jlRestricaoNome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppCadastroRestricoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(csRestricaoDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                            .addComponent(csRestricaoNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppCadastroRestricoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbAdicionarRestricao, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAlterarRestricao, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbRemoverRestricao, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ppCadastroRestricoesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jlRestricaoDescricao, jlRestricaoIndice, jlRestricaoNome});

        ppCadastroRestricoesLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {clRestricaoIndice, csRestricaoDescricao, csRestricaoNome});

        ppCadastroRestricoesLayout.setVerticalGroup(
            ppCadastroRestricoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppCadastroRestricoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppCadastroRestricoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlRestricaoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csRestricaoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppCadastroRestricoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlRestricaoDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csRestricaoDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppCadastroRestricoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlRestricaoIndice, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clRestricaoIndice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(ppCadastroRestricoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppCadastroRestricoesLayout.createSequentialGroup()
                        .addComponent(jbAdicionarRestricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAlterarRestricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRemoverRestricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jspTabelaCadastroRestricoes, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)))
        );

        ppCadastroRestricoesLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jlRestricaoDescricao, jlRestricaoIndice, jlRestricaoNome});

        ppCadastroRestricoesLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {clRestricaoIndice, csRestricaoDescricao, csRestricaoNome});

        jspCadastroRestricoes.setLeftComponent(ppCadastroRestricoes);

        jbAdicionarRestricaoColuna.setMnemonic('d');
        jbAdicionarRestricaoColuna.setText("Adicionar");
        jbAdicionarRestricaoColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarRestricaoColunaActionPerformed(evt);
            }
        });

        jbRemoverRestricaoColuna.setMnemonic('e');
        jbRemoverRestricaoColuna.setText("Remover");
        jbRemoverRestricaoColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverRestricaoColunaActionPerformed(evt);
            }
        });

        jbSubirRestricaoColuna.setMnemonic('u');
        jbSubirRestricaoColuna.setText("Subir");
        jbSubirRestricaoColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSubirRestricaoColunaActionPerformed(evt);
            }
        });

        jbBaixarRestricaoColuna.setMnemonic('b');
        jbBaixarRestricaoColuna.setText("Baixar");
        jbBaixarRestricaoColuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBaixarRestricaoColunaActionPerformed(evt);
            }
        });

        jlIndiceColuna1.setText("Campo:");

        clRestricaoColuna.setModel(dcbmColunaIndice);
        clRestricaoColuna.setDescricaoRotulo("Campo");
        clRestricaoColuna.setNextFocusableComponent(jbAdicionarRestricaoColuna);
        clRestricaoColuna.setObrigatorio(true);

        tpCadastroRestricaoColunas.setModel(otmCadastroRestricaoColunas);
        jspCadastroRestricaoColunas.setViewportView(tpCadastroRestricaoColunas);

        javax.swing.GroupLayout ppCadastroRestricoesColunasLayout = new javax.swing.GroupLayout(ppCadastroRestricoesColunas);
        ppCadastroRestricoesColunas.setLayout(ppCadastroRestricoesColunasLayout);
        ppCadastroRestricoesColunasLayout.setHorizontalGroup(
            ppCadastroRestricoesColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppCadastroRestricoesColunasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppCadastroRestricoesColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppCadastroRestricoesColunasLayout.createSequentialGroup()
                        .addComponent(jspCadastroRestricaoColunas, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ppCadastroRestricoesColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbAdicionarRestricaoColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbRemoverRestricaoColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbSubirRestricaoColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbBaixarRestricaoColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ppCadastroRestricoesColunasLayout.createSequentialGroup()
                        .addComponent(jlIndiceColuna1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clRestricaoColuna, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        ppCadastroRestricoesColunasLayout.setVerticalGroup(
            ppCadastroRestricoesColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppCadastroRestricoesColunasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ppCadastroRestricoesColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlIndiceColuna1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clRestricaoColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ppCadastroRestricoesColunasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspCadastroRestricaoColunas, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addGroup(ppCadastroRestricoesColunasLayout.createSequentialGroup()
                        .addComponent(jbAdicionarRestricaoColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRemoverRestricaoColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbSubirRestricaoColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbBaixarRestricaoColuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jspCadastroRestricoes.setRightComponent(ppCadastroRestricoesColunas);

        jtpCadastro.addTab("Restrições", jspCadastroRestricoes);

        javax.swing.GroupLayout jpCadastroLayout = new javax.swing.GroupLayout(jpCadastro);
        jpCadastro.setLayout(jpCadastroLayout);
        jpCadastroLayout.setHorizontalGroup(
            jpCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpCadastroTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jtpCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 1285, Short.MAX_VALUE)
        );
        jpCadastroLayout.setVerticalGroup(
            jpCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadastroLayout.createSequentialGroup()
                .addComponent(jpCadastroTabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtpCadastro))
        );

        jtpCadastro.getAccessibleContext().setAccessibleName("Colunas");

        jtpTabela.addTab("Cadastro/Alteração", jpCadastro);

        getContentPane().add(jtpTabela, java.awt.BorderLayout.CENTER);
        getContentPane().add(jpsStatusBar, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPesquisarActionPerformed
        List<Filtro> lFiltros = new LinkedList<>();
        lFiltros.add(cscFiltroTabela.getFiltro("tabela"));
        lFiltros.add(cscFiltroDescricao.getFiltro("descricao"));

        try {
            tabelaControl.pesquisar(lFiltros);

            otmConsultaTabela.fireTableDataChanged();
        } catch (ErroSistemaException ex) {
            jpsStatusBar.setStatus("Erro ao pesquisar tabela." + ex.getLocalizedMessage(), JPStatus.ALERTA, ex);
        }
    }//GEN-LAST:event_bPesquisarActionPerformed

    private void bIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bIncluirActionPerformed
        tabelaControl.novo();
        statusTela = INCLUINDO;
        habilitaComponentesCadastro(true);
        habilitaCadastro(true);
        habilitaBotoesColunas(true);

        otmCadastroColunas.setData(tabelaControl.getTabela().getColunas());
        otmCadastroIndices.setData(tabelaControl.getTabela().getIndices());
        otmCadastroRelacionamentos.setData(tabelaControl.getTabela().getRelacionamentos());
        otmCadastroChave.setData(tabelaControl.getTabela().getChaves());
        otmCadastroRestricoes.setData(tabelaControl.getTabela().getRestricoes());
    }//GEN-LAST:event_bIncluirActionPerformed

    private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed
        try {
            if (statusTela == INCLUINDO || statusTela == ALTERANDO) {
                if (!validarListaCampos(listaCampoTabela)) {
                    jpsStatusBar.setStatus("Campos obrigatórios não preenchidos.", JPStatus.ALERTA);
                    return;
                }

                if (statusTela == INCLUINDO) {
                    tabelaControl.getTabela().setTabelaOriginal(tabelaControl.getTabela().getTabela());
                }

                setarDadosTabela();

                tabelaControl.salvar();

                otmConsultaTabela.fireTableDataChanged();

                SwingUtilities.invokeLater(() -> {
                    int posicaoLinha = tabelaControl.getlConsultaTabela().indexOf(tabelaControl.getTabela());
                    tpConsultaTabelas.addRowSelectionInterval(tpConsultaTabelas.convertRowIndexToView(posicaoLinha), tpConsultaTabelas.convertRowIndexToView(posicaoLinha));
                    tpConsultaTabelas.scrollRectToVisible(tpConsultaTabelas.getCellRect(tpConsultaTabelas.getSelectedRow(), 0, true));

                    otmConsultaColuna.setData(tabelaControl.getTabela().getColunas());
                    otmConsultaChave.setData(tabelaControl.getTabela().getChaves());
                    otmConsultaIndices.setData(tabelaControl.getTabela().getIndices());
                    otmConsultaRelacionamentos.setData(tabelaControl.getTabela().getRelacionamentos());
                    otmConsultaRestricoes.setData(tabelaControl.getTabela().getRestricoes());

                    tpConsultaColunas.packAll();
                    tpConsultaChave.packAll();
                    tpConsultaIndices.packAll();
                    tpConsultaRelacionamentos.packAll();
                    tpConsultaRestricoes.packAll();
                });

            } else {
                tabelaControl.excluir();

                otmConsultaTabela.fireTableDataChanged();
            }

            jpsStatusBar.setStatus("Processamento efetuado com sucesso.", JPStatus.NORMAL);
            habilitaCadastro(false);

            limparCampos();

            statusTela = INDEFINIDO;

        } catch (ErroSistemaException ex) {
            //ex.printStackTrace();
            //JOptionPane.showMessageDialog(rootPane, "Erro ao salvar tabela.", ex.getLocalizedMessage(), JOptionPane.ERROR_MESSAGE);
            jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
        }


    }//GEN-LAST:event_jbSalvarActionPerformed


    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        habilitaCadastro(false);

        limparCampos();

        statusTela = INDEFINIDO;
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void bAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAlterarActionPerformed
        alterarTabela();
    }//GEN-LAST:event_bAlterarActionPerformed

    private void bExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExcluirActionPerformed
        if (tpConsultaTabelas.getSelectedRow() >= 0) {
            try {
                tabelaControl.setTabela(tabelaControl.find(otmConsultaTabela.getValue(tpConsultaTabelas.getLinhaSelecionada())));

                carregarDadosTabela();

                habilitaComponentesCadastro(false);

                jbSalvar.setEnabled(true);
                jbCancelar.setEnabled(true);

                habilitaCadastro(true);

                statusTela = EXCLUINDO;
            } catch (ErroSistemaException ex) {
                jpsStatusBar.setStatus("Erro ao selecionar tabela.", JPStatus.ERRO);
            }
        } else {
            jpsStatusBar.setStatus("É necessário selecionar uma tabela.", JPStatus.ALERTA);
        }
    }//GEN-LAST:event_bExcluirActionPerformed

    private void jbAdicionarIndiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarIndiceActionPerformed
        try {
            if (validarListaCampos(listaCampoIndice)) {
                Indice indiceNovo = new Indice(tabelaControl.getTabela(), "IDX_" + csTabelaNome.getString().toUpperCase() + "_" + csIndiceNome.getString().toUpperCase(), csIndiceDescricao.getString());
                tabelaControl.adicionarIndice(indiceNovo);
                otmCadastroIndices.fireTableDataChanged();

                limparListaCampos(listaCampoIndice);

                int linhaIndiceNovo = tabelaControl.getTabela().getIndices().indexOf(indiceNovo);

                tpCadastroIndices.setRowSelectionInterval(tpCadastroIndices.convertRowIndexToView(linhaIndiceNovo), tpCadastroIndices.convertRowIndexToView(linhaIndiceNovo));

                clIndiceColuna.grabFocus();
            } else {
                jpsStatusBar.setStatus("Existem campos obrigatórios não preenchidos.", JPStatus.ALERTA);
            }
        } catch (ValidacaoException ex) {
            jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
        }
    }//GEN-LAST:event_jbAdicionarIndiceActionPerformed

    private void jbAlterarIndiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarIndiceActionPerformed
        /*
        if (jxtCadastroIndices.getSelectedRow() >= 0) {
            try {
                if (validarListaCampos(listaCampoIndice)) {
                    Indice indiceAlterar = otmCadastroIndices.getValue(jxtCadastroIndices.convertRowIndexToModel(jxtCadastroIndices.getSelectedRow()));
                    Indice indiceAlterado = new Indice(indiceAlterar.getTabela(), csIndiceNome.getString(), clIndiceTipo.getSelectedItem().toString(), csIndiceDescricao.getString());

                    tabelaControl.alterarIndice(indiceAlterar, indiceAlterado);

                    otmCadastroIndices.fireTableDataChanged();
                    limparListaCampos(listaCampoIndice);

                    int linhaIndice = otmCadastroIndices.indexOf(indiceAlterar);
                    jxtCadastroIndices.setRowSelectionInterval(jxtCadastroIndices.convertRowIndexToView(linhaIndice), jxtCadastroIndices.convertRowIndexToView(linhaIndice));
                } else {
                    jpsStatusBar.setStatus("Existem campos obrigatórios não preenchidos.", jpsStatusBar.ALERTA);
                }
            } catch (ValidacaoException ex) {
                jpsStatusBar.setStatus(ex.getLocalizedMessage(), jpsStatusBar.ALERTA);
            }
        }*/
    }//GEN-LAST:event_jbAlterarIndiceActionPerformed

    private void jbRemoverIndiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverIndiceActionPerformed
        if (tpCadastroIndices.getSelectedRow() >= 0) {
            int ret = JOptionPane.showConfirmDialog(rootPane, "Confirma a exclusão do registro?", "Remover", JOptionPane.YES_NO_OPTION);
            if (ret == 0) {
                try {
                    tabelaControl.removerIndice(otmCadastroIndices.getValue(tpCadastroIndices.convertRowIndexToModel(tpCadastroIndices.getSelectedRow())));
                    otmCadastroIndices.fireTableDataChanged();
                } catch (ValidacaoException ex) {
                    jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
                }
            }
        }
    }//GEN-LAST:event_jbRemoverIndiceActionPerformed

    private void tpCadastroIndicesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpCadastroIndicesMouseClicked
        if (evt.getClickCount() == 2) {
            if (tpCadastroIndices.getSelectedRow() >= 0) {
                Indice indice = otmCadastroIndices.getValue(tpCadastroIndices.getSelectedRow());
                csIndiceNome.setString(indice.getIndice());
                csIndiceDescricao.setString(indice.getDescricao());
            }
        }
    }//GEN-LAST:event_tpCadastroIndicesMouseClicked

    private void jbAdicionarIndiceColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarIndiceColunaActionPerformed
        if (tpCadastroIndices.getSelectedRow() >= 0) {
            try {
                if (validarListaCampos(listaCampoIndiceColunas)) {
                    Indice indice = otmCadastroIndices.getValue(tpCadastroIndices.convertRowIndexToModel(tpCadastroIndices.getSelectedRow()));
                    //IndiceColuna indiceColuna = new IndiceColuna(indice, (Coluna) csCadastroIndiceColuna.getValor(), 0);
                    //IndiceColuna indiceColuna = new IndiceColuna(indice, ocbmColunaIndice.getSelectedObject(), clIndiceColunaTipoOrdem.getSelectedItem().toString(), 0);
                    IndiceColuna indiceColuna = new IndiceColuna(indice, (Coluna) clIndiceColuna.getSelectedItem(), clIndiceColunaTipoOrdem.getSelectedItem().toString(), 0);

                    tabelaControl.adicionarIndiceColuna(indice, indiceColuna);

                    otmCadastroIndiceColunas.fireTableDataChanged();

                    limparListaCampos(listaCampoIndiceColunas);

                    clIndiceColuna.grabFocus();
                } else {
                    jpsStatusBar.setStatus("Existem campos obrigatórios não preenchidos.", JPStatus.ALERTA);
                }
            } catch (ValidacaoException ex) {
                jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
            }
        }
    }//GEN-LAST:event_jbAdicionarIndiceColunaActionPerformed

    private void jbRemoverIndiceColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverIndiceColunaActionPerformed
        if (tpCadastroIndiceColunas.getSelectedRow() >= 0) {
            int ret = JOptionPane.showConfirmDialog(rootPane, "Confirma a exclusão do registro?", "Remover", JOptionPane.YES_NO_OPTION);
            if (ret == 0) {
                try {
                    Indice indice = otmCadastroIndices.getValue(tpCadastroIndices.convertRowIndexToModel(tpCadastroIndices.getSelectedRow()));
                    IndiceColuna indiceColunaRemover = otmCadastroIndiceColunas.getValue(tpCadastroIndiceColunas.convertRowIndexToModel(tpCadastroIndiceColunas.getSelectedRow()));

                    tabelaControl.removerIndiceColuna(indice, indiceColunaRemover);
                    otmCadastroIndiceColunas.fireTableDataChanged();
                } catch (ValidacaoException ex) {
                    jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
                }
            }
        }
    }//GEN-LAST:event_jbRemoverIndiceColunaActionPerformed

    private void jbSubirIndiceColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSubirIndiceColunaActionPerformed
        if (tpCadastroIndiceColunas.getSelectedRow() >= 0) {
            try {
                Indice indice = otmCadastroIndices.getValue(tpCadastroIndices.convertRowIndexToModel(tpCadastroIndices.getSelectedRow()));
                IndiceColuna indiceColunaMover = otmCadastroIndiceColunas.getValue(tpCadastroIndiceColunas.convertRowIndexToModel(tpCadastroIndiceColunas.getSelectedRow()));

                tabelaControl.movimentaIndiceColuna(TabelaControl.SUBIR, indice, indiceColunaMover);

                otmCadastroIndiceColunas.fireTableDataChanged();

                int linha = otmCadastroIndiceColunas.indexOf(indiceColunaMover);
                tpCadastroIndiceColunas.setRowSelectionInterval(tpCadastroIndiceColunas.convertRowIndexToModel(linha), tpCadastroIndiceColunas.convertRowIndexToModel(linha));

            } catch (ValidacaoException ex) {
                jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
            }
        }
    }//GEN-LAST:event_jbSubirIndiceColunaActionPerformed

    private void jbBaixarIndiceColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBaixarIndiceColunaActionPerformed
        if (tpCadastroIndiceColunas.getSelectedRow() >= 0) {
            try {
                Indice indice = otmCadastroIndices.getValue(tpCadastroIndices.convertRowIndexToModel(tpCadastroIndices.getSelectedRow()));
                IndiceColuna indiceColunaMover = otmCadastroIndiceColunas.getValue(tpCadastroIndiceColunas.convertRowIndexToModel(tpCadastroIndiceColunas.getSelectedRow()));

                tabelaControl.movimentaIndiceColuna(TabelaControl.BAIXAR, indice, indiceColunaMover);

                otmCadastroIndiceColunas.fireTableDataChanged();

                int linha = otmCadastroIndiceColunas.indexOf(indiceColunaMover);
                tpCadastroIndiceColunas.setRowSelectionInterval(tpCadastroIndiceColunas.convertRowIndexToModel(linha), tpCadastroIndiceColunas.convertRowIndexToModel(linha));

            } catch (ValidacaoException ex) {
                jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
            }
        }
    }//GEN-LAST:event_jbBaixarIndiceColunaActionPerformed

    private void jbAdicionarColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarColunaActionPerformed
        try {
            if (validarListaCampos(listaCampoTabelaColunas)) {
                Coluna colunaNova = new Coluna(tabelaControl.getTabela());
                colunaNova.setColuna(csColunaColuna.getString());
                colunaNova.setTituloCurto(csColunaTituloCurto.getString());
                colunaNova.setTituloLongo(csColunaTituloLongo.getString());
                colunaNova.setDescricao(csColunaDescricao.getString());
                colunaNova.setTipo(clColunaTipo.getSelectedItem().toString());
                colunaNova.setTamanho(csColunaTamanho.getInt());
                colunaNova.setPrecisao(csColunaPrecisao.getInt());
                colunaNova.setMascara(csColunaMascara.getString());
                colunaNova.setLista((Lista) csColunaLista.getValor());
                colunaNova.setObrigatorio(jcbcsCadastroColunaObrigatorio.isSelected());
                colunaNova.setValorPadrao(csColunaValorPadrao.getString());
                colunaNova.setColunaOriginal(csColunaColuna.getString());

                tabelaControl.adicionarColuna(colunaNova);

                otmCadastroColunas.fireTableDataChanged();

                limparListaCampos(listaCampoTabelaColunas);

                atualizarCombosBox();

                //int linhaColunaNova = tabelaControl.getTabelaDigitada().getColunas().indexOf(colunaNova);
                //jxtCadastroColunasNew.setRowSelectionInterval(jxtCadastroColunasNew.convertRowIndexToView(linhaColunaNova), jxtCadastroColunasNew.convertRowIndexToView(linhaColunaNova));
                csColunaColuna.grabFocus();
            } else {
                jpsStatusBar.setStatus("Existem campos obrigatórios não preenchidos.", JPStatus.ALERTA);
            }

        } catch (ValidacaoException ex) {
            jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
        }
    }//GEN-LAST:event_jbAdicionarColunaActionPerformed

    private void jbAlterarColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarColunaActionPerformed
        if (tpCadastroColunas.getSelectedRow() >= 0) {
            try {
                if (validarListaCampos(listaCampoTabelaColunas)) {
                    Coluna colunaAlterar = otmCadastroColunas.getValue(tpCadastroColunas.convertRowIndexToModel(tpCadastroColunas.getSelectedRow()));
                    Coluna colunaAlterada = new Coluna(colunaAlterar.getTabela());
                    colunaAlterada.setIdColuna(colunaAlterar.getIdColuna());
                    colunaAlterada.setColuna(csColunaColuna.getString());
                    colunaAlterada.setTituloCurto(csColunaTituloCurto.getString());
                    colunaAlterada.setTituloLongo(csColunaTituloLongo.getString());
                    colunaAlterada.setDescricao(csColunaDescricao.getString());
                    colunaAlterada.setTipo(clColunaTipo.getSelectedItem().toString());
                    colunaAlterada.setTamanho(csColunaTamanho.getInt());
                    colunaAlterada.setPrecisao(csColunaPrecisao.getInt());
                    colunaAlterada.setMascara(csColunaMascara.getString());
                    colunaAlterada.setLista((Lista) csColunaLista.getValor());
                    colunaAlterada.setObrigatorio(jcbcsCadastroColunaObrigatorio.isSelected());
                    colunaAlterada.setValorPadrao(csColunaValorPadrao.getString());

                    if (colunaAlterar.getIdColuna() == 0) {
                        colunaAlterada.setColunaOriginal(csColunaColuna.getString());
                    }

                    tabelaControl.alterarColuna(colunaAlterar, colunaAlterada);

                    otmCadastroColunas.fireTableDataChanged();
                    limparListaCampos(listaCampoTabelaColunas);

                    int linhaIndice = otmCadastroColunas.indexOf(colunaAlterar);

                    atualizarCombosBox();

                    tpCadastroColunas.setRowSelectionInterval(tpCadastroColunas.convertRowIndexToView(linhaIndice), tpCadastroColunas.convertRowIndexToView(linhaIndice));
                } else {
                    jpsStatusBar.setStatus("Existem campos obrigatórios não preenchidos.", JPStatus.ALERTA);
                }

                habilitaBotoesColunas(true);
            } catch (ValidacaoException ex) {
                jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
            }
        }
    }//GEN-LAST:event_jbAlterarColunaActionPerformed

    private void jbRemoverColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverColunaActionPerformed
        if (tpCadastroColunas.getSelectedRow() >= 0) {
            int ret = JOptionPane.showConfirmDialog(rootPane, "Confirma a exclusão do registro?", "Remover", JOptionPane.YES_NO_OPTION);
            if (ret == 0) {
                try {
                    tabelaControl.removerColuna(otmCadastroColunas.getValue(tpCadastroColunas.convertRowIndexToModel(tpCadastroColunas.getSelectedRow())));
                    otmCadastroColunas.fireTableDataChanged();

                    atualizarCombosBox();
                } catch (ValidacaoException | ErroSistemaException ex) {
                    jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
                }
            }
        }
    }//GEN-LAST:event_jbRemoverColunaActionPerformed

    private void tpCadastroColunasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpCadastroColunasMouseClicked
        if (evt.getClickCount() == 2) {
            if (tpCadastroColunas.getSelectedRow() >= 0) {
                Coluna colunaSelecionada = otmCadastroColunas.getValue(tpCadastroColunas.convertRowIndexToModel(tpCadastroColunas.getSelectedRow()));

                csColunaColuna.setString(colunaSelecionada.getColuna());
                csColunaTituloCurto.setString(colunaSelecionada.getTituloCurto());
                csColunaTituloLongo.setString(colunaSelecionada.getTituloLongo());
                csColunaDescricao.setString(colunaSelecionada.getDescricao());
                clColunaTipo.setSelectedItem(colunaSelecionada.getTipo());
                csColunaTamanho.setInt(colunaSelecionada.getTamanho());
                csColunaPrecisao.setInt(colunaSelecionada.getPrecisao());
                csColunaMascara.setString(colunaSelecionada.getMascara());
                csColunaLista.setValor(colunaSelecionada.getLista());
                jcbcsCadastroColunaObrigatorio.setSelected(colunaSelecionada.isObrigatorio());
                csColunaValorPadrao.setString(colunaSelecionada.getValorPadrao());

                habilitaBotoesColunas(false);

            }
        }
    }//GEN-LAST:event_tpCadastroColunasMouseClicked

    private void tpCadastroRelacionamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpCadastroRelacionamentosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tpCadastroRelacionamentosMouseClicked

    private void jbAdicionarRelacionamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarRelacionamentoActionPerformed
        if (validarListaCampos(listaCampoRelacionamento)) {
            try {
                Relacionamento relacionamentoNovo = new Relacionamento("FK_" + csTabelaNome.getString().toUpperCase() + "_" + csRelacionamentoNome.getString().toUpperCase(), tabelaControl.getTabela(), (Tabela) csRelacionamentoTabelaRelacionada.getValor());
                tabelaControl.adicionarRelacionamento(relacionamentoNovo);
                otmCadastroRelacionamentos.fireTableDataChanged();

                limparListaCampos(listaCampoRelacionamento);

                int linhaColunaNova = tabelaControl.getTabela().getRelacionamentos().indexOf(relacionamentoNovo);

                tpCadastroRelacionamentos.setRowSelectionInterval(tpCadastroRelacionamentos.convertRowIndexToView(linhaColunaNova), tpCadastroRelacionamentos.convertRowIndexToView(linhaColunaNova));
                clRelacionamentoColunaOrigem.grabFocus();
            } catch (ValidacaoException ex) {
                jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
            }
        }
    }//GEN-LAST:event_jbAdicionarRelacionamentoActionPerformed

    private void jbAlterarRelacionamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarRelacionamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbAlterarRelacionamentoActionPerformed

    private void jbRemoverRelacionamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverRelacionamentoActionPerformed
        if (tpCadastroRelacionamentos.getSelectedRow() >= 0) {
            int ret = JOptionPane.showConfirmDialog(rootPane, "Confirma a exclusão do registro?", "Remover", JOptionPane.YES_NO_OPTION);
            if (ret == 0) {
                try {
                    tabelaControl.excluirRelacionamento(otmCadastroRelacionamentos.getValue(tpCadastroRelacionamentos.convertRowIndexToModel(tpCadastroRelacionamentos.getSelectedRow())));
                    otmCadastroRelacionamentos.fireTableDataChanged();
                } catch (ValidacaoException ex) {
                    jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
                }
            }
        }
    }//GEN-LAST:event_jbRemoverRelacionamentoActionPerformed

    private void jbAdicionarRelacionamentoColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarRelacionamentoColunaActionPerformed
        if (tpCadastroRelacionamentos.getSelectedRow() >= 0) {

            try {
                if (validarListaCampos(listaCampoRelacionamentoColunas)) {

                    Relacionamento relacionamento = otmCadastroRelacionamentos.getValue(tpCadastroRelacionamentos.convertRowIndexToModel(tpCadastroRelacionamentos.getSelectedRow()));
//                    Coluna colunaOrigem = ocbmColunaRelacionamentoOrigem.getSelectedObject();
//                    Coluna colunaRelacionada = ocbmColunaRelacionamentoRelacionada.getSelectedObject();
                    Coluna colunaOrigem = (Coluna) clRelacionamentoColunaOrigem.getSelectedItem();
                    Coluna colunaRelacionada = (Coluna) clRelacionamentoColunaRelacionada.getSelectedItem();

                    if (colunaOrigem.getTipo().toUpperCase().equals(colunaRelacionada.getTipo().toUpperCase().replace("SERIAL", "INTEGER"))) {
                        RelacionamentoColuna relacionamentoColuna = new RelacionamentoColuna(relacionamento, colunaOrigem, colunaRelacionada);
                        tabelaControl.adicionarRelacionamentoColuna(relacionamento, relacionamentoColuna);

                        otmCadastroRelacionamentoColunas.fireTableDataChanged();

                        limparListaCampos(listaCampoRelacionamentoColunas);

                        clRelacionamentoColunaOrigem.grabFocus();
                    } else {
                        jpsStatusBar.setStatus("Colunas com tipos incompatíveis.", JPStatus.ALERTA);
                    }

                } else {
                    jpsStatusBar.setStatus("Existem campos obrigatórios não preenchidos.", JPStatus.ALERTA);
                }

            } catch (ValidacaoException ex) {
                jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
            }

        } else {
            jpsStatusBar.setStatus("É necessário selecionar um relacionamento.", JPStatus.ALERTA);
        }
    }//GEN-LAST:event_jbAdicionarRelacionamentoColunaActionPerformed

    private void jbRemoverRelacionamentoColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverRelacionamentoColunaActionPerformed
        if (tpCadastroRelacionamentoColunas.getSelectedRow() >= 0) {
            int ret = JOptionPane.showConfirmDialog(rootPane, "Confirma a exclusão do registro?", "Remover", JOptionPane.YES_NO_OPTION);
            if (ret == 0) {
                try {
                    Relacionamento relacionamento = otmCadastroRelacionamentos.getValue(tpCadastroRelacionamentos.convertRowIndexToModel(tpCadastroRelacionamentos.getSelectedRow()));
                    RelacionamentoColuna relacionamentoColunaRemover = otmCadastroRelacionamentoColunas.getValue(tpCadastroRelacionamentoColunas.convertRowIndexToModel(tpCadastroRelacionamentoColunas.getSelectedRow()));

                    tabelaControl.removerRelacionamentoColuna(relacionamento, relacionamentoColunaRemover);

                    otmCadastroRelacionamentoColunas.fireTableDataChanged();
                } catch (ValidacaoException ex) {
                    jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
                }
            }
        }
    }//GEN-LAST:event_jbRemoverRelacionamentoColunaActionPerformed

    private void jbAdicionarChaveColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarChaveColunaActionPerformed
        try {
            if (validarListaCampos(listaCampoTabelaChave)) {
                //Chave chaveNova = new Chave(tabelaControl.getTabelaDigitada(), ocbmColunaChave.getSelectedObject(), 0);
                Chave chaveNova = new Chave(tabelaControl.getTabela(), (Coluna) clChaveColuna.getSelectedItem(), 0);

                tabelaControl.adicionarChaveColuna(chaveNova);

                otmCadastroChave.fireTableDataChanged();

                limparListaCampos(listaCampoTabelaChave);

                clChaveColuna.grabFocus();
            } else {
                jpsStatusBar.setStatus("Existem campos obrigatórios não preenchidos.", jpsStatusBar.ALERTA);
            }
        } catch (ValidacaoException ex) {
            jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
        }
    }//GEN-LAST:event_jbAdicionarChaveColunaActionPerformed

    private void jbRemoverChaveColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverChaveColunaActionPerformed
        if (tpCadastroChave.getSelectedRow() >= 0) {
            int ret = JOptionPane.showConfirmDialog(rootPane, "Confirma a exclusão do registro?", "Remover", JOptionPane.YES_NO_OPTION);
            if (ret == 0) {
                try {
                    Chave chaveRemover = otmCadastroChave.getValue(tpCadastroChave.convertRowIndexToModel(tpCadastroChave.getSelectedRow()));

                    tabelaControl.removerChaveColuna(chaveRemover);
                    otmCadastroChave.fireTableDataChanged();
                } catch (ValidacaoException ex) {
                    jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
                }
            }
        }
    }//GEN-LAST:event_jbRemoverChaveColunaActionPerformed

    private void jbSubirChaveColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSubirChaveColunaActionPerformed
        if (tpCadastroChave.getSelectedRow() >= 0) {
            try {
                Chave chaveMover = otmCadastroChave.getValue(tpCadastroChave.convertRowIndexToModel(tpCadastroChave.getSelectedRow()));

                tabelaControl.movimentaChaveColuna(TabelaControl.SUBIR, chaveMover);

                otmCadastroChave.fireTableDataChanged();

                int linha = otmCadastroChave.indexOf(chaveMover);
                tpCadastroChave.setRowSelectionInterval(tpCadastroChave.convertRowIndexToModel(linha), tpCadastroChave.convertRowIndexToModel(linha));

            } catch (ValidacaoException ex) {
                jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
            }
        }
    }//GEN-LAST:event_jbSubirChaveColunaActionPerformed

    private void jbBaixarChaveColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBaixarChaveColunaActionPerformed
        if (tpCadastroChave.getSelectedRow() >= 0) {
            try {
                Chave chaveMover = otmCadastroChave.getValue(tpCadastroChave.convertRowIndexToModel(tpCadastroChave.getSelectedRow()));

                tabelaControl.movimentaChaveColuna(TabelaControl.BAIXAR, chaveMover);

                otmCadastroChave.fireTableDataChanged();

                int linha = otmCadastroChave.indexOf(chaveMover);
                tpCadastroChave.setRowSelectionInterval(tpCadastroChave.convertRowIndexToModel(linha), tpCadastroChave.convertRowIndexToModel(linha));

            } catch (ValidacaoException ex) {
                jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
            }
        }
    }//GEN-LAST:event_jbBaixarChaveColunaActionPerformed

    private void jbCancelarColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarColunaActionPerformed
        limparListaCampos(listaCampoTabelaColunas);

        habilitaBotoesColunas(true);
    }//GEN-LAST:event_jbCancelarColunaActionPerformed

    private void tpConsultaTabelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpConsultaTabelasMouseClicked
        if (evt.getClickCount() == 2) {
            alterarTabela();
        }
    }//GEN-LAST:event_tpConsultaTabelasMouseClicked

    private void tpConsultaTabelasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpConsultaTabelasMouseReleased
        JPopupMenu jpmMenu = new JPopupMenu();
        JMenuItem jmiGerarClasseVO = new JMenuItem("Gerar Classe VO");
        JMenuItem jmiGerarColunasTabela = new JMenuItem("Gerar Tabelas Colunas");
        JMenuItem jmiGerarSetters = new JMenuItem("Gerar Setters");
        JMenuItem jmiGerarGetters = new JMenuItem("Gerar Getters");
        JMenuItem jmiGerarPreparedStatement = new JMenuItem("Gerar PreparedStatement");

        jpmMenu.add(jmiGerarClasseVO);
        jpmMenu.add(jmiGerarColunasTabela);
        jpmMenu.add(jmiGerarSetters);
        jpmMenu.add(jmiGerarGetters);
        jpmMenu.add(jmiGerarPreparedStatement);

        jmiGerarClasseVO.addActionListener((ActionEvent ae) -> {
            selecionarTabela();
            tabelaControl.montaClasseDomain(tabelaControl.getTabela());
        });

        jmiGerarColunasTabela.addActionListener((ActionEvent ae) -> {
            selecionarTabela();
            tabelaControl.montarColunasTabela(tabelaControl.getTabela());
        });

        jmiGerarSetters.addActionListener((ActionEvent ae) -> {
            selecionarTabela();
            tabelaControl.montarSettersColunasTabela(tabelaControl.getTabela());
        });

        jmiGerarGetters.addActionListener((ActionEvent ae) -> {
            selecionarTabela();
            tabelaControl.montarGettersColunasTabela(tabelaControl.getTabela());
        });

        jmiGerarPreparedStatement.addActionListener((ActionEvent ae) -> {
            selecionarTabela();
            tabelaControl.montarPreparedStatement(tabelaControl.getTabela());
        });

        if (evt.isPopupTrigger()) {
            TabelaPadrao jxtTabela = (TabelaPadrao) evt.getSource();
            int row = jxtTabela.rowAtPoint(evt.getPoint());
            int column = jxtTabela.columnAtPoint(evt.getPoint());

            if (!jxtTabela.isRowSelected(row)) {
                jxtTabela.changeSelection(row, column, false, false);
            }

            jpmMenu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tpConsultaTabelasMouseReleased

    private void tpCadastroRestricoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tpCadastroRestricoesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tpCadastroRestricoesMouseClicked

    private void jbAdicionarRestricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarRestricaoActionPerformed
        try {
            if (validarListaCampos(listaCampoRestricao)) {
                Restricao restricaoNova = new Restricao(tabelaControl.getTabela(), "UC_" + csTabelaNome.getString().toUpperCase() + "_" + csRestricaoNome.getString().toUpperCase(), csRestricaoDescricao.getString());
                tabelaControl.adicionarRestricao(restricaoNova);
                otmCadastroRestricoes.fireTableDataChanged();

                limparListaCampos(listaCampoRestricao);

                int linhaRestricaoNova = tabelaControl.getTabela().getRestricoes().indexOf(restricaoNova);

                tpCadastroRestricoes.setRowSelectionInterval(tpCadastroRestricoes.convertRowIndexToView(linhaRestricaoNova), tpCadastroRestricoes.convertRowIndexToView(linhaRestricaoNova));

                clRestricaoColuna.grabFocus();
            } else {
                jpsStatusBar.setStatus("Existem campos obrigatórios não preenchidos.", JPStatus.ALERTA);
            }
        } catch (ValidacaoException ex) {
            jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
        }
    }//GEN-LAST:event_jbAdicionarRestricaoActionPerformed

    private void jbAlterarRestricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarRestricaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbAlterarRestricaoActionPerformed

    private void jbRemoverRestricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverRestricaoActionPerformed
        if (tpCadastroRestricoes.getSelectedRow() >= 0) {
            int ret = JOptionPane.showConfirmDialog(rootPane, "Confirma a exclusão do registro?", "Remover", JOptionPane.YES_NO_OPTION);
            if (ret == 0) {
                try {
                    tabelaControl.removerRestricao(otmCadastroRestricoes.getValue(tpCadastroRestricoes.convertRowIndexToModel(tpCadastroRestricoes.getSelectedRow())));
                    otmCadastroRestricoes.fireTableDataChanged();
                } catch (ValidacaoException ex) {
                    jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
                }
            }
        }
    }//GEN-LAST:event_jbRemoverRestricaoActionPerformed

    private void jbAdicionarRestricaoColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarRestricaoColunaActionPerformed
        if (tpCadastroRestricoes.getSelectedRow() >= 0) {
            try {
                if (validarListaCampos(listaCampoRestricaoColunas)) {
                    Restricao restricao = otmCadastroRestricoes.getValue(tpCadastroRestricoes.convertRowIndexToModel(tpCadastroRestricoes.getSelectedRow()));
                    //IndiceColuna indiceColuna = new IndiceColuna(indice, (Coluna) csCadastroIndiceColuna.getValor(), 0);
                    //IndiceColuna indiceColuna = new IndiceColuna(indice, ocbmColunaIndice.getSelectedObject(), clIndiceColunaTipoOrdem.getSelectedItem().toString(), 0);
                    RestricaoColuna restricaoColuna = new RestricaoColuna(restricao, (Coluna) clRestricaoColuna.getSelectedItem(), 0);

                    tabelaControl.adicionarRestricaoColuna(restricao, restricaoColuna);

                    otmCadastroRestricaoColunas.fireTableDataChanged();

                    limparListaCampos(listaCampoRestricaoColunas);

                    clRestricaoColuna.grabFocus();
                } else {
                    jpsStatusBar.setStatus("Existem campos obrigatórios não preenchidos.", JPStatus.ALERTA);
                }
            } catch (ValidacaoException ex) {
                jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
            }
        }
    }//GEN-LAST:event_jbAdicionarRestricaoColunaActionPerformed

    private void jbRemoverRestricaoColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverRestricaoColunaActionPerformed
        if (tpCadastroRestricaoColunas.getSelectedRow() >= 0) {
            int ret = JOptionPane.showConfirmDialog(rootPane, "Confirma a exclusão do registro?", "Remover", JOptionPane.YES_NO_OPTION);
            if (ret == 0) {
                try {
                    Restricao restricao = otmCadastroRestricoes.getValue(tpCadastroRestricoes.convertRowIndexToModel(tpCadastroRestricoes.getSelectedRow()));
                    RestricaoColuna restricaoColunaRemover = otmCadastroRestricaoColunas.getValue(tpCadastroRestricaoColunas.convertRowIndexToModel(tpCadastroRestricaoColunas.getSelectedRow()));

                    tabelaControl.removerRestricaoColuna(restricao, restricaoColunaRemover);
                    otmCadastroRestricaoColunas.fireTableDataChanged();
                } catch (ValidacaoException ex) {
                    jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
                }
            }
        }
    }//GEN-LAST:event_jbRemoverRestricaoColunaActionPerformed

    private void jbSubirRestricaoColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSubirRestricaoColunaActionPerformed
        if (tpCadastroRestricaoColunas.getSelectedRow() >= 0) {
            try {
                Restricao restricao = otmCadastroRestricoes.getValue(tpCadastroRestricoes.convertRowIndexToModel(tpCadastroRestricoes.getSelectedRow()));
                RestricaoColuna restricaoColunaMover = otmCadastroRestricaoColunas.getValue(tpCadastroRestricaoColunas.convertRowIndexToModel(tpCadastroRestricaoColunas.getSelectedRow()));

                tabelaControl.movimentaRestricaoColuna(TabelaControl.SUBIR, restricao, restricaoColunaMover);

                otmCadastroRestricaoColunas.fireTableDataChanged();

                int linha = otmCadastroRestricaoColunas.indexOf(restricaoColunaMover);
                tpCadastroRestricaoColunas.setRowSelectionInterval(tpCadastroRestricaoColunas.convertRowIndexToModel(linha), tpCadastroRestricaoColunas.convertRowIndexToModel(linha));

            } catch (ValidacaoException ex) {
                jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
            }
        }
    }//GEN-LAST:event_jbSubirRestricaoColunaActionPerformed

    private void jbBaixarRestricaoColunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBaixarRestricaoColunaActionPerformed
        if (tpCadastroRestricaoColunas.getSelectedRow() >= 0) {
            try {
                Restricao restricao = otmCadastroRestricoes.getValue(tpCadastroRestricoes.convertRowIndexToModel(tpCadastroRestricoes.getSelectedRow()));
                RestricaoColuna restricaoColunaMover = otmCadastroRestricaoColunas.getValue(tpCadastroRestricaoColunas.convertRowIndexToModel(tpCadastroRestricaoColunas.getSelectedRow()));

                tabelaControl.movimentaRestricaoColuna(TabelaControl.BAIXAR, restricao, restricaoColunaMover);

                otmCadastroRestricaoColunas.fireTableDataChanged();

                int linha = otmCadastroRestricaoColunas.indexOf(restricaoColunaMover);
                tpCadastroRestricaoColunas.setRowSelectionInterval(tpCadastroRestricaoColunas.convertRowIndexToModel(linha), tpCadastroRestricaoColunas.convertRowIndexToModel(linha));

            } catch (ValidacaoException ex) {
                jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ALERTA);
            }
        }
    }//GEN-LAST:event_jbBaixarRestricaoColunaActionPerformed

    private void bImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImprimirActionPerformed

        try {
            laser.gerarRelatorioDesktop(otmConsultaTabela.getList(tpConsultaTabelas.getLinhasSelecionadas()), null, "G:\\NetBeansProjects\\Martinello\\Frente Loja\\martinello-relatorios\\Martinello\\gerenciador\\Tabela.jasper", null, 1, false);
        } catch (JRException | ErroSistemaException ex) {
            jpsStatusBar.setStatus(ex.getLocalizedMessage(), JPStatus.ERRO, ex);
        }
    }//GEN-LAST:event_bImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.componentesbasicos.Botao bAlterar;
    private br.com.martinello.componentesbasicos.Botao bExcluir;
    private br.com.martinello.componentesbasicos.Botao bImprimir;
    private br.com.martinello.componentesbasicos.Botao bIncluir;
    private br.com.martinello.componentesbasicos.Botao bPesquisar;
    private br.com.martinello.componentesbasicos.CampoListaSimples clChaveColuna;
    private br.com.martinello.componentesbasicos.CampoListaSimples clColunaTipo;
    private br.com.martinello.componentesbasicos.CampoListaSimples clIndiceColuna;
    private br.com.martinello.componentesbasicos.CampoListaSimples clIndiceColunaTipoOrdem;
    private br.com.martinello.componentesbasicos.CampoListaSimples clRelacionamentoColunaOrigem;
    private br.com.martinello.componentesbasicos.CampoListaSimples clRelacionamentoColunaRelacionada;
    private br.com.martinello.componentesbasicos.CampoListaSimples clRestricaoColuna;
    private br.com.martinello.componentesbasicos.CampoListaSimples clRestricaoIndice;
    private br.com.martinello.componentesbasicos.CampoListaSimples clTabelaSchema;
    private br.com.martinello.componentesbasicos.CampoString csColunaColuna;
    private br.com.martinello.componentesbasicos.CampoString csColunaDescricao;
    private br.com.martinello.gerenciador.componentes.CampoConsulta csColunaLista;
    private br.com.martinello.componentesbasicos.CampoString csColunaMascara;
    private br.com.martinello.componentesbasicos.CampoString csColunaPrecisao;
    private br.com.martinello.componentesbasicos.CampoString csColunaTamanho;
    private br.com.martinello.componentesbasicos.CampoString csColunaTituloCurto;
    private br.com.martinello.componentesbasicos.CampoString csColunaTituloLongo;
    private br.com.martinello.componentesbasicos.CampoString csColunaValorPadrao;
    private br.com.martinello.componentesbasicos.CampoString csIndiceDescricao;
    private br.com.martinello.componentesbasicos.CampoString csIndiceNome;
    private br.com.martinello.componentesbasicos.CampoString csRelacionamentoNome;
    private br.com.martinello.gerenciador.componentes.CampoConsulta csRelacionamentoTabelaRelacionada;
    private br.com.martinello.componentesbasicos.CampoString csRestricaoDescricao;
    private br.com.martinello.componentesbasicos.CampoString csRestricaoNome;
    private br.com.martinello.componentesbasicos.CampoString csTabelaDescricao;
    private br.com.martinello.componentesbasicos.CampoString csTabelaNome;
    private br.com.martinello.componentesbasicos.consulta.CampoStringConsulta cscFiltroDescricao;
    private br.com.martinello.componentesbasicos.consulta.CampoStringConsulta cscFiltroTabela;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane;
    private br.com.martinello.componentesbasicos.Botao jbAdicionarChaveColuna;
    private br.com.martinello.componentesbasicos.Botao jbAdicionarColuna;
    private br.com.martinello.componentesbasicos.Botao jbAdicionarIndice;
    private br.com.martinello.componentesbasicos.Botao jbAdicionarIndiceColuna;
    private br.com.martinello.componentesbasicos.Botao jbAdicionarRelacionamento;
    private br.com.martinello.componentesbasicos.Botao jbAdicionarRelacionamentoColuna;
    private br.com.martinello.componentesbasicos.Botao jbAdicionarRestricao;
    private br.com.martinello.componentesbasicos.Botao jbAdicionarRestricaoColuna;
    private br.com.martinello.componentesbasicos.Botao jbAlterarColuna;
    private br.com.martinello.componentesbasicos.Botao jbAlterarIndice;
    private br.com.martinello.componentesbasicos.Botao jbAlterarRelacionamento;
    private br.com.martinello.componentesbasicos.Botao jbAlterarRestricao;
    private br.com.martinello.componentesbasicos.Botao jbBaixarChaveColuna;
    private br.com.martinello.componentesbasicos.Botao jbBaixarIndiceColuna;
    private br.com.martinello.componentesbasicos.Botao jbBaixarRestricaoColuna;
    private br.com.martinello.componentesbasicos.Botao jbCancelar;
    private br.com.martinello.componentesbasicos.Botao jbCancelarColuna;
    private br.com.martinello.componentesbasicos.Botao jbRemoverChaveColuna;
    private br.com.martinello.componentesbasicos.Botao jbRemoverColuna;
    private br.com.martinello.componentesbasicos.Botao jbRemoverIndice;
    private br.com.martinello.componentesbasicos.Botao jbRemoverIndiceColuna;
    private br.com.martinello.componentesbasicos.Botao jbRemoverRelacionamento;
    private br.com.martinello.componentesbasicos.Botao jbRemoverRelacionamentoColuna;
    private br.com.martinello.componentesbasicos.Botao jbRemoverRestricao;
    private br.com.martinello.componentesbasicos.Botao jbRemoverRestricaoColuna;
    private br.com.martinello.componentesbasicos.Botao jbSalvar;
    private br.com.martinello.componentesbasicos.Botao jbSubirChaveColuna;
    private br.com.martinello.componentesbasicos.Botao jbSubirIndiceColuna;
    private br.com.martinello.componentesbasicos.Botao jbSubirRestricaoColuna;
    private javax.swing.JCheckBox jcbcsCadastroColunaObrigatorio;
    private br.com.martinello.componentesbasicos.Rotulo jlChaveColuna;
    private br.com.martinello.componentesbasicos.Rotulo jlColunaColuna;
    private br.com.martinello.componentesbasicos.Rotulo jlColunaDescricao;
    private br.com.martinello.componentesbasicos.Rotulo jlColunaLista;
    private br.com.martinello.componentesbasicos.Rotulo jlColunaMascara;
    private br.com.martinello.componentesbasicos.Rotulo jlColunaObrigatorio;
    private br.com.martinello.componentesbasicos.Rotulo jlColunaPrecisao;
    private br.com.martinello.componentesbasicos.Rotulo jlColunaTamanho;
    private br.com.martinello.componentesbasicos.Rotulo jlColunaTipo;
    private br.com.martinello.componentesbasicos.Rotulo jlColunaTituloCurto;
    private br.com.martinello.componentesbasicos.Rotulo jlColunaTituloLongo;
    private br.com.martinello.componentesbasicos.Rotulo jlColunaValorPadrao;
    private br.com.martinello.componentesbasicos.Rotulo jlDescricaoLista;
    private br.com.martinello.componentesbasicos.Rotulo jlDescricaoTabelaRelacionada;
    private br.com.martinello.componentesbasicos.Rotulo jlFiltroDescricao;
    private br.com.martinello.componentesbasicos.Rotulo jlFiltroTabela;
    private br.com.martinello.componentesbasicos.Rotulo jlIndiceColuna;
    private br.com.martinello.componentesbasicos.Rotulo jlIndiceColuna1;
    private br.com.martinello.componentesbasicos.Rotulo jlIndiceDescricao;
    private br.com.martinello.componentesbasicos.Rotulo jlIndiceNome;
    private br.com.martinello.componentesbasicos.Rotulo jlIndiceTipoOrdem;
    private br.com.martinello.componentesbasicos.Rotulo jlRelacionamentoColunaOrigem;
    private br.com.martinello.componentesbasicos.Rotulo jlRelacionamentoColunaRelacionada;
    private br.com.martinello.componentesbasicos.Rotulo jlRelacionamentoNome;
    private br.com.martinello.componentesbasicos.Rotulo jlRelacionamentoTabelaRelacionada;
    private br.com.martinello.componentesbasicos.Rotulo jlRestricaoDescricao;
    private br.com.martinello.componentesbasicos.Rotulo jlRestricaoIndice;
    private br.com.martinello.componentesbasicos.Rotulo jlRestricaoNome;
    private br.com.martinello.componentesbasicos.Rotulo jlTabelaDescricao;
    private br.com.martinello.componentesbasicos.Rotulo jlTabelaNome;
    private br.com.martinello.componentesbasicos.Rotulo jlTabelaSchema;
    private javax.swing.JPanel jpCadastro;
    private javax.swing.JPanel jpCadastroColunas;
    private javax.swing.JPanel jpCadastroIndiceColunas;
    private javax.swing.JPanel jpCadastroIndices;
    private javax.swing.JPanel jpCadastroRelacionamentos;
    private javax.swing.JPanel jpCadastroRelacionamentosColunas;
    private javax.swing.JPanel jpCadastroTabela;
    private javax.swing.JPanel jpConsulta;
    private javax.swing.JPanel jpFiltros;
    private javax.swing.JPanel jpTabelaCadastroColunas;
    private br.com.martinello.componentesbasicos.paineis.JPStatus jpsStatusBar;
    private javax.swing.JSplitPane jspCadastroColunas;
    private javax.swing.JScrollPane jspCadastroIndiceColunas;
    private javax.swing.JSplitPane jspCadastroIndices;
    private javax.swing.JScrollPane jspCadastroRelacionamentoColunas;
    private javax.swing.JSplitPane jspCadastroRelacionamentos;
    private javax.swing.JScrollPane jspCadastroRestricaoColunas;
    private javax.swing.JSplitPane jspCadastroRestricoes;
    private javax.swing.JScrollPane jspConsultaChave;
    private javax.swing.JScrollPane jspConsultaColunas;
    private javax.swing.JScrollPane jspConsultaRestricoes;
    private javax.swing.JScrollPane jspConsultaTabelas;
    private javax.swing.JScrollPane jspIndices;
    private javax.swing.JScrollPane jspRelacionamentos;
    private javax.swing.JScrollPane jspTabelaCadastroColunas;
    private javax.swing.JScrollPane jspTabelaCadastroIndices;
    private javax.swing.JScrollPane jspTabelaCadastroRelacionamentos;
    private javax.swing.JScrollPane jspTabelaCadastroRestricoes;
    private javax.swing.JTabbedPane jtpCadastro;
    private javax.swing.JTabbedPane jtpConsulta;
    private javax.swing.JTabbedPane jtpTabela;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppCadastroRestricoes;
    private br.com.martinello.componentesbasicos.paineis.PainelPadrao ppCadastroRestricoesColunas;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpCadastroChave;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpCadastroColunas;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpCadastroIndiceColunas;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpCadastroIndices;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpCadastroRelacionamentoColunas;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpCadastroRelacionamentos;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpCadastroRestricaoColunas;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpCadastroRestricoes;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpConsultaChave;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpConsultaColunas;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpConsultaIndices;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpConsultaRelacioamentosPor;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpConsultaRelacionamentos;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpConsultaRestricoes;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpConsultaTabelas;
    // End of variables declaration//GEN-END:variables

    @Override
    public void valorSelecionado(CampoConsultaEvento evt) {
//        if (evt.getSource() == csRelacionamentoTabelaRelacionada) {
//            Tabela tabelaRelacionada = (Tabela) csRelacionamentoTabelaRelacionada.getValor();
//            if (tabelaRelacionada != null) {
//                ocbmColunaRelacionamentoRelacionada.setData(tabelaRelacionada.getColunas());
//            }
//        }
    }

    public void atualizarCombosBox() {
//        ocbmColunaIndice.clear();
//        ocbmColunaChave.clear();
//        ocbmColunaRelacionamentoOrigem.clear();

        clChaveColuna.setModel(new DefaultComboBoxModel(tabelaControl.getTabela().getColunas().toArray()));
        clIndiceColuna.setModel(new DefaultComboBoxModel(tabelaControl.getTabela().getColunas().toArray()));
        clRestricaoColuna.setModel(new DefaultComboBoxModel(tabelaControl.getTabela().getColunas().toArray()));
        clRelacionamentoColunaOrigem.setModel(new DefaultComboBoxModel(tabelaControl.getTabela().getColunas().toArray()));

        //ocbmColunaIndice.setData(tabelaControl.getTabelaDigitada().getColunas());
        //ocbmColunaChave.setData(tabelaControl.getTabelaDigitada().getColunas());
        //ocbmColunaRelacionamentoOrigem.setData(tabelaControl.getTabelaDigitada().getColunas());
    }

    @Override
    public void habilitaBotoesColunas(boolean b) {
        jbAdicionarColuna.setEnabled(b);
        jbAlterarColuna.setEnabled(!b);
        jbRemoverColuna.setEnabled(b);
        jbCancelarColuna.setEnabled(!b);
    }

    private void alterarTabela() {
        if (tpConsultaTabelas.getSelectedRow() >= 0) {
            try {
                tabelaControl.setTabela(tabelaControl.find(otmConsultaTabela.getValue(tpConsultaTabelas.getLinhaSelecionada())));

                carregarDadosTabela();

                habilitaComponentesCadastro(true);
                habilitaCadastro(true);
                habilitaBotoesColunas(true);

                statusTela = ALTERANDO;
            } catch (ErroSistemaException ex) {
                jpsStatusBar.setStatus("Erro ao selecionar a tabela.", JPStatus.ERRO);
            }
        } else {
            jpsStatusBar.setStatus("É necessário selecionar uma tabela.", JPStatus.ALERTA);
        }
    }

    public void selecionarTabela() {
        if (tpConsultaTabelas.getLinhaSelecionada() >= 0) {
            try {
                tabelaControl.setTabela(tabelaControl.find(otmConsultaTabela.getValue(tpConsultaTabelas.getLinhaSelecionada())));
            } catch (ErroSistemaException ex) {
                jpsStatusBar.setStatus("Não foi possível selecionar a tabela.", JPStatus.ERRO, ex);
            }
        }
    }
}
