/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.cadastro.view;

import br.com.martinello.cadastro.BarraStatus;
import br.com.martinello.matriz.bd.control.CategoriaControl;
import br.com.martinello.matriz.bd.control.ParametrosControl;
import br.com.martinello.matriz.bd.control.ProcessamentoControl;
import br.com.martinello.matriz.bd.control.ProdutoControl;
import br.com.martinello.matriz.bd.control.SubCategoriaControl;
import br.com.martinello.matriz.bd.control.UsuarioControl;
import br.com.martinello.matriz.bd.model.domain.Parametro;
import br.com.martinello.matriz.bd.model.domain.Produto;
import br.com.martinello.matriz.bd.model.domain.Usuario;
import br.com.martinello.matriz.componentesbasicos.paineis.PainelDesktop;
import br.com.martinello.matriz.componentesbasicos.paineis.TelaProcessamento;
import br.com.martinello.matriz.componentesbasicos.view.TelaPadrao;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 *
 * @author luiz.almeida
 */
public class TelaPrincipal extends JFrame implements InternalFrameListener {

    protected static BarraStatus barraStatus;
    public static Usuario usuario;
    public static Date dataExtrator;
    public static Parametro parametros;
    public static List<Parametro> Lparametros = new ArrayList<>();
    public static String email;
    public static String userEmail;
    public static String senhaEmail;
    public static String emailPcopia;
    public static String assEmail;
    public static String diretorio;
    private ParametrosControl parametroControl = new ParametrosControl();
    private ProcessamentoControl processamentoControl;
    private ProdutoControl produtoControl;
    private CategoriaControl categoriaControl;
    private SubCategoriaControl subCategoriaControl;
    protected UsuarioControl usuarioControl;
    private TelaPadrao telaCategoria;
    private TelaPadrao telaSubCategoria;
    private TelaPadrao telaCaracteristica;
    private TelaPadrao telaCaracteristicaItens;
    private TelaPadrao telaParametros;
    private TelaPadrao telaPendencia;
    private TelaPadrao telaProduto;
    private TelaPadrao telaAgrupamento;
    private TelaPadrao telaCatalogo;
    protected BufferedImage img;
    protected PainelDesktop jDesktopPane;// = new JDesktopPane();
    private boolean atualizaLogin;
    private ExecutorService executorAtualizaLogin = Executors.newFixedThreadPool(1);
    protected static String usuarioLogado;
    private static String debug;
    private static int pendentes;
    private String permicao;
    private List<Produto> lProduto;

    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal(Usuario usuarioModel) {
        atualizaLogin = true;
        TelaPrincipal.usuario = usuarioModel;
        TelaPrincipal.dataExtrator = new Date();
        TelaPrincipal.debug = "S";
        usuarioControl = new UsuarioControl();
        barraStatus = new BarraStatus(usuario, "ELETROMÓVEIS MARTINELLO", "Conectado", dataExtrator, pendentes);

        //carregarParametros();
        initComponents();
        MouseListener[] mouseListeners = barraStatus.getMouseListeners();
        validaMenu();

        this.add(barraStatus, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        setSize(1900, 1024);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                try {
                    excluirLoginUsuario();
                } catch (ErroSistemaException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }

        });
        /* telaCategoria = new TelaCategoria();
        Dimension desktopSize = getSize();
        Dimension jInternalFrameSize = telaCategoria.getSize();
        telaCategoria.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);
        telaCategoria.addInternalFrameListener(this);

        jdpPainelPrincipal.add(telaCategoria);

        telaCategoria.setVisible(true);*/

        new Thread() {
            @Override
            public void run() {
                Atualizalogin();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                carregaPendencia();
            }
        }.start();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdpPainelPrincipal = new javax.swing.JDesktopPane();
        jmbMenuPrincipal = new javax.swing.JMenuBar();
        jmCadastro = new javax.swing.JMenu();
        jmCategoria = new javax.swing.JMenu();
        jmiCategoria = new javax.swing.JMenuItem();
        jmiSubCategoria = new javax.swing.JMenuItem();
        JmiCatalogo = new javax.swing.JMenuItem();
        jMRelCat = new javax.swing.JMenu();
        jmiRelCategoria = new javax.swing.JMenuItem();
        jMiRelSubCategoria = new javax.swing.JMenuItem();
        jmProduto = new javax.swing.JMenu();
        JmiDadosGerais = new javax.swing.JMenuItem();
        jmiAgrupar = new javax.swing.JMenuItem();
        JmRelatorio = new javax.swing.JMenu();
        JmiRelProdCad = new javax.swing.JMenuItem();
        JmiRelProdNaoCad = new javax.swing.JMenuItem();
        jmiParametros = new javax.swing.JMenuItem();
        jmSistema = new javax.swing.JMenu();
        jmiAjuda = new javax.swing.JMenuItem();
        jmiSair = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("S.C.P.M - Sistema de Cadastro de Produto Martinello");
        setPreferredSize(new java.awt.Dimension(1366, 800));

        jdpPainelPrincipal.setPreferredSize(new java.awt.Dimension(1570, 860));

        javax.swing.GroupLayout jdpPainelPrincipalLayout = new javax.swing.GroupLayout(jdpPainelPrincipal);
        jdpPainelPrincipal.setLayout(jdpPainelPrincipalLayout);
        jdpPainelPrincipalLayout.setHorizontalGroup(
            jdpPainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 839, Short.MAX_VALUE)
        );
        jdpPainelPrincipalLayout.setVerticalGroup(
            jdpPainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 538, Short.MAX_VALUE)
        );

        try {
            img = ImageIO.read(new File("papel-de-parede4.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jdpPainelPrincipal = new PainelDesktop(img);
        this.add(jdpPainelPrincipal, BorderLayout.CENTER);
        this.add(barraStatus, BorderLayout.SOUTH);
        //this.add(jpsTelaPricipal, BorderLayout.SOUTH);
        setVisible(true);
        getContentPane().add(jdpPainelPrincipal, java.awt.BorderLayout.CENTER);

        jmbMenuPrincipal.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jmCadastro.setText("Cadastro");

        jmCategoria.setText("Categorias");

        jmiCategoria.setText("Categoria");
        jmiCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiCategoriaActionPerformed(evt);
            }
        });
        jmCategoria.add(jmiCategoria);

        jmiSubCategoria.setText("SubCategoria");
        jmiSubCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSubCategoriaActionPerformed(evt);
            }
        });
        jmCategoria.add(jmiSubCategoria);

        JmiCatalogo.setText("Categoria Site");
        JmiCatalogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmiCatalogoActionPerformed(evt);
            }
        });
        jmCategoria.add(JmiCatalogo);

        jMRelCat.setText("Relatórios");

        jmiRelCategoria.setText("Categorias");
        jmiRelCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiRelCategoriaActionPerformed(evt);
            }
        });
        jMRelCat.add(jmiRelCategoria);

        jMiRelSubCategoria.setText("SubCategoria");
        jMiRelSubCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMiRelSubCategoriaActionPerformed(evt);
            }
        });
        jMRelCat.add(jMiRelSubCategoria);

        jmCategoria.add(jMRelCat);

        jmCadastro.add(jmCategoria);

        jmProduto.setText("Produto");

        JmiDadosGerais.setText("Ficha do Produto");
        JmiDadosGerais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmiDadosGeraisActionPerformed(evt);
            }
        });
        jmProduto.add(JmiDadosGerais);

        jmiAgrupar.setText("Agrupamento");
        jmiAgrupar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAgruparActionPerformed(evt);
            }
        });
        jmProduto.add(jmiAgrupar);

        JmRelatorio.setText("Relatórios");

        JmiRelProdCad.setText("Relação Produtos Cadastrado");
        JmiRelProdCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmiRelProdCadActionPerformed(evt);
            }
        });
        JmRelatorio.add(JmiRelProdCad);

        JmiRelProdNaoCad.setText("Relação Produtos Não Cadastrado");
        JmiRelProdNaoCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JmiRelProdNaoCadActionPerformed(evt);
            }
        });
        JmRelatorio.add(JmiRelProdNaoCad);

        jmProduto.add(JmRelatorio);

        jmCadastro.add(jmProduto);

        jmiParametros.setText("Parametros");
        jmiParametros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiParametrosActionPerformed(evt);
            }
        });
        jmCadastro.add(jmiParametros);

        jmbMenuPrincipal.add(jmCadastro);

        jmSistema.setText("Sistema");

        jmiAjuda.setText("Ajuda");
        jmiAjuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAjudaActionPerformed(evt);
            }
        });
        jmSistema.add(jmiAjuda);

        jmiSair.setText("Sair");
        jmiSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSairActionPerformed(evt);
            }
        });
        jmSistema.add(jmiSair);

        jmbMenuPrincipal.add(jmSistema);

        jMenu1.setText("e-commerce");

        jMenuItem1.setText("Pendência");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jmbMenuPrincipal.add(jMenu1);

        setJMenuBar(jmbMenuPrincipal);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSairActionPerformed
        try {
            excluirLoginUsuario();
            System.exit(0);
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }


    }//GEN-LAST:event_jmiSairActionPerformed

    private void jmiAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAjudaActionPerformed
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI("https://eletromoveismartinello.net.br/glpi/front/knowbaseitem.form.php?id=3"));
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jmiAjudaActionPerformed

    private void jmiParametrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiParametrosActionPerformed

        if (telaParametros == null) {
            telaParametros = new TelaParametros();
            Dimension desktopSize = getSize();
            Dimension jInternalFrameSize = telaParametros.getSize();
            telaParametros.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    ((desktopSize.height - 70) - jInternalFrameSize.height) / 2);
            telaParametros.addInternalFrameListener(this);
            //    telasAbertas.add(telaFilial);
            jdpPainelPrincipal.add(telaParametros);
            telaParametros.setVisible(true);

            // centralizarTela(telaParametros);
        }
        jdpPainelPrincipal.moveToFront(telaParametros);        // TODO add your handling code here:

    }//GEN-LAST:event_jmiParametrosActionPerformed

    private void jmiCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiCategoriaActionPerformed
        if (telaCategoria == null) {
            telaCategoria = new TelaCategoria();
            telaCategoria.addInternalFrameListener(this);
            //    telasAbertas.add(telaFilial);

            jdpPainelPrincipal.add(telaCategoria);
            telaCategoria.setVisible(true);

            // centralizarTela(telaExtrator);
        }
        //private void centralizaForm(JInternalFrame frame) {
        Dimension desktopSize = getSize();
        Dimension jInternalFrameSize = telaCategoria.getSize();
        telaCategoria.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                ((desktopSize.height - 70) - jInternalFrameSize.height) / 2);
        //}
        jdpPainelPrincipal.moveToFront(telaCategoria);
        // centralizarTela(telaExtrator);
//telaExtrator.
    }//GEN-LAST:event_jmiCategoriaActionPerformed

    private void jmiSubCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSubCategoriaActionPerformed
        if (telaSubCategoria == null) {
            telaSubCategoria = new TelaSubCategoria();
            telaSubCategoria.addInternalFrameListener(this);
            //    telasAbertas.add(telaFilial);

            jdpPainelPrincipal.add(telaSubCategoria);
            telaSubCategoria.setVisible(true);

            // centralizarTela(telaExtrator);
        }
        //private void centralizaForm(JInternalFrame frame) {
        Dimension desktopSize = getSize();
        Dimension jInternalFrameSize = telaSubCategoria.getSize();
        telaSubCategoria.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                ((desktopSize.height - 70) - jInternalFrameSize.height) / 2);
        //}
        jdpPainelPrincipal.moveToFront(telaSubCategoria);
    }//GEN-LAST:event_jmiSubCategoriaActionPerformed

    private void JmiDadosGeraisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmiDadosGeraisActionPerformed
        if (telaProduto == null) {
            telaProduto = new TelaProduto();
            telaProduto.addInternalFrameListener(this);
            //    telasAbertas.add(telaFilial);

            jdpPainelPrincipal.add(telaProduto);
            telaProduto.setVisible(true);

            // centralizarTela(telaProduto);
        }

        Dimension desktopSize = this.getSize();
        Dimension jInternalFrameSize = telaProduto.getSize();
        telaProduto.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                ((desktopSize.height - 70) - jInternalFrameSize.height) / 2);

        jdpPainelPrincipal.moveToFront(telaProduto);
    }//GEN-LAST:event_JmiDadosGeraisActionPerformed

    private void jmiAgruparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAgruparActionPerformed
        if (telaAgrupamento == null) {
            telaAgrupamento = new TelaRelacionaProduto();
            telaAgrupamento.addInternalFrameListener(this);
            jdpPainelPrincipal.add(telaAgrupamento);
            telaAgrupamento.setVisible(true);
        }
        Dimension desktopSize = getSize();
        Dimension jInternalFrameSize = telaAgrupamento.getSize();
        telaAgrupamento.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                ((desktopSize.height - 70) - jInternalFrameSize.height) / 2);

        jdpPainelPrincipal.moveToFront(telaAgrupamento);
    }//GEN-LAST:event_jmiAgruparActionPerformed

    private void JmiCatalogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmiCatalogoActionPerformed
        if (telaCatalogo == null) {
            telaCatalogo = new TelaCategoriaSite();
            telaCatalogo.addInternalFrameListener(this);
            jdpPainelPrincipal.add(telaCatalogo);
            telaCatalogo.setVisible(true);
        }
        Dimension desktopSize = getSize();
        Dimension jInternalFrameSize = telaCatalogo.getSize();
        telaCatalogo.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                ((desktopSize.height - 70) - jInternalFrameSize.height) / 2);

        jdpPainelPrincipal.moveToFront(telaCatalogo);
    }//GEN-LAST:event_JmiCatalogoActionPerformed

    private void JmiRelProdCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmiRelProdCadActionPerformed
        relacaoProdutosCadastrado();
    }//GEN-LAST:event_JmiRelProdCadActionPerformed

    private void JmiRelProdNaoCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JmiRelProdNaoCadActionPerformed
        relacaoProdutosNaoCadastrado();
    }//GEN-LAST:event_JmiRelProdNaoCadActionPerformed

    private void jmiRelCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiRelCategoriaActionPerformed
        relacaoCategorias();
    }//GEN-LAST:event_jmiRelCategoriaActionPerformed

    private void jMiRelSubCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMiRelSubCategoriaActionPerformed
        relacaoSubCategoria();
    }//GEN-LAST:event_jMiRelSubCategoriaActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if (telaPendencia == null) {
            telaPendencia = new TelaParametros();
            Dimension desktopSize = getSize();
            Dimension jInternalFrameSize = telaParametros.getSize();
            telaParametros.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    ((desktopSize.height - 70) - jInternalFrameSize.height) / 2);
            telaParametros.addInternalFrameListener(this);
            //    telasAbertas.add(telaFilial);
            jdpPainelPrincipal.add(telaParametros);
            telaParametros.setVisible(true);

            // centralizarTela(telaParametros);
        }
        jdpPainelPrincipal.moveToFront(telaParametros);        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
//public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
////        try {
////            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
////                if ("Nimbus".equals(info.getName())) {
////                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
////                    break;
////                }
////            }
////        } catch (ClassNotFoundException ex) {
////            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        } catch (InstantiationException ex) {
////            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        } catch (IllegalAccessException ex) {
////            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
////            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        }
//        //</editor-fold>
//
//        try {
//            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
//        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
//            System.out.println(e);
//        }
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new TelaPrincipal().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu JmRelatorio;
    private javax.swing.JMenuItem JmiCatalogo;
    private javax.swing.JMenuItem JmiDadosGerais;
    private javax.swing.JMenuItem JmiRelProdCad;
    private javax.swing.JMenuItem JmiRelProdNaoCad;
    private javax.swing.JMenu jMRelCat;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMiRelSubCategoria;
    private javax.swing.JDesktopPane jdpPainelPrincipal;
    private javax.swing.JMenu jmCadastro;
    private javax.swing.JMenu jmCategoria;
    private javax.swing.JMenu jmProduto;
    private javax.swing.JMenu jmSistema;
    private javax.swing.JMenuBar jmbMenuPrincipal;
    private javax.swing.JMenuItem jmiAgrupar;
    private javax.swing.JMenuItem jmiAjuda;
    private javax.swing.JMenuItem jmiCategoria;
    private javax.swing.JMenuItem jmiParametros;
    private javax.swing.JMenuItem jmiRelCategoria;
    private javax.swing.JMenuItem jmiSair;
    private javax.swing.JMenuItem jmiSubCategoria;
    // End of variables declaration//GEN-END:variables
//    public void setposicao(){
//        Dimension d = .get getSize();
//    }

//    public static UsuarioModel getUsuario() {
//        return UsuarioController.find(usuario);
//    }
    public static void setUsuario(Usuario usuario) {
        TelaPrincipal.usuario = usuario;
    }

    public void centralizarTela(JInternalFrame jInternalFrame) {
        jInternalFrame.setLocation((jdpPainelPrincipal.getToolkit().getScreenSize().width - jInternalFrame.getSize().width) / 2,
                (jdpPainelPrincipal.getToolkit().getScreenSize().height - jInternalFrame.getSize().height) / 2);
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {

        if (e.getInternalFrame() == telaCategoria) {
            telaCategoria = null;
        }
        if (e.getInternalFrame() == telaSubCategoria) {
            telaSubCategoria = null;
        }
        if (e.getInternalFrame() == telaParametros) {
            telaParametros = null;
        }
        if (e.getInternalFrame() == telaCaracteristica) {
            telaCaracteristica = null;
        }
        if (e.getInternalFrame() == telaCaracteristicaItens) {
            telaCaracteristicaItens = null;
        }
        if (e.getInternalFrame() == telaProduto) {
            telaProduto = null;
        }
        if (e.getInternalFrame() == telaAgrupamento) {
            telaAgrupamento = null;
        }
        if (e.getInternalFrame() == telaCatalogo) {
            telaCatalogo = null;
        }

    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {
    }

    public void excluirLoginUsuario() throws ErroSistemaException {
        usuarioControl.setUsuario(usuario);
        usuario.getLogins().clear();
        try {
            ProcessamentoControl processamento = new ProcessamentoControl();
            processamento.encerrarProcessos(usuario);
            usuarioControl.AtualizarLogin();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public void Atualizalogin() {

        while (atualizaLogin == true) {
            try {
                usuarioControl.setUsuario(usuario);
                usuarioControl.AtualizaLogin();
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException ex) {
                barraStatus.setStatus(ex.getMessage());

            } catch (ErroSistemaException ex) {
                barraStatus.setStatus(ex.getMessage());
            }
        }
    }

    private void relacaoProdutosCadastrado() {

        final TelaProcessamento telaProcessamentoteste = new TelaProcessamento("Gerando Excel...");
        new Thread() {
            @Override
            public void run() {
                try {
                    produtoControl = new ProdutoControl();
                    produtoControl.relacaoProdutosCadastrado((int) usuario.getIdUsuario());

                    SwingUtilities.invokeLater(
                            () -> {
                                telaProcessamentoteste.dispose();
                            }
                    );
                } catch (ErroSistemaException ex) {
                    barraStatus.setStatus(ex.getMessage());
                }
            }
        }.start();

        telaProcessamentoteste.setVisible(
                true);
        telaProcessamentoteste.requestFocusInWindow();
    }

    private void relacaoProdutosNaoCadastrado() {

        final TelaProcessamento telaProcessamentoteste = new TelaProcessamento("Gerando Excel...");
        new Thread() {
            @Override
            public void run() {
                try {
                    produtoControl = new ProdutoControl();
                    produtoControl.relacaoProdutosNaoCadastrado((int) usuario.getIdUsuario());

                    SwingUtilities.invokeLater(
                            () -> {
                                telaProcessamentoteste.dispose();
                            }
                    );
                } catch (ErroSistemaException ex) {
                    barraStatus.setStatus(ex.getMessage());
                }
            }
        }.start();

        telaProcessamentoteste.setVisible(
                true);
        telaProcessamentoteste.requestFocusInWindow();
    }

    private void validaMenu() {
        int[] usuPermite = {117, 212, 384};//Usuarios com permissão menu de Relatório
        permicao = "N";
        for (int CodUsu : usuPermite) {
            if (usuario.getIdUsuario() == CodUsu) {
                permicao = "S";
                break;
            }
        }
        if (permicao.equalsIgnoreCase("S")) {
            JmRelatorio.setEnabled(true);
            JmRelatorio.setVisible(true);
            jMRelCat.setEnabled(true);
            jMRelCat.setVisible(true);
        } else {
            JmRelatorio.setEnabled(false);
            JmRelatorio.setVisible(false);
            jMRelCat.setEnabled(false);
            jMRelCat.setVisible(false);
        }
    }

    private void carregaPendencia() {
        while (atualizaLogin == true) {
            try {
                produtoControl = new ProdutoControl();
                lProduto = new LinkedList<>();
                System.out.println("Passei aqui");
                lProduto = produtoControl.buscarPendecias();
                pendentes = lProduto.size();
                barraStatus.setPendencia(pendentes);
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException ex) {
                barraStatus.setStatus(ex.getMessage());

            } catch (ErroSistemaException ex) {
                barraStatus.setStatus(ex.getMessage());
            }
        }
    }

    private void relacaoCategorias() {
        final TelaProcessamento telaProcessamentoteste = new TelaProcessamento("Gerando Excel...");
        new Thread() {
            @Override
            public void run() {
                try {
                    categoriaControl = new CategoriaControl();
                    categoriaControl.relacaoCategoria((int) usuario.getIdUsuario());
                    SwingUtilities.invokeLater(
                            () -> {
                                telaProcessamentoteste.dispose();
                            }
                    );
                } catch (ErroSistemaException ex) {
                    barraStatus.setStatus(ex.getMessage());
                }
            }
        }.start();

        telaProcessamentoteste.setVisible(
                true);
        telaProcessamentoteste.requestFocusInWindow();
    }

    private void relacaoSubCategoria() {
        final TelaProcessamento telaProcessamentoteste = new TelaProcessamento("Gerando Excel...");
        new Thread() {
            @Override
            public void run() {
                try {
                    subCategoriaControl = new SubCategoriaControl();
                    subCategoriaControl.relacaoSubCategoria((int) usuario.getIdUsuario());

                    SwingUtilities.invokeLater(
                            () -> {
                                telaProcessamentoteste.dispose();
                            }
                    );
                } catch (ErroSistemaException ex) {
                    barraStatus.setStatus(ex.getMessage());
                }
            }
        }.start();

        telaProcessamentoteste.setVisible(
                true);
        telaProcessamentoteste.requestFocusInWindow();
    }

}
