/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.view;

import br.com.martinello.componentesbasicos.MenuItem;
import br.com.martinello.bd.matriz.control.DicionarioControl;
import br.com.martinello.util.excessoes.ErroSistemaException;
import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.JTattooUtilities;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 *
 * @author Sidnei
 */
public class GerenciadorSistema extends JFrame implements ActionListener, InternalFrameListener {

    protected JMenuBar jMenuBar = new JMenuBar();
    protected JMenu jmSistema = new JMenu("Sistema");
    protected JMenu jmDicionarioDados = new JMenu("Dicionário de Dados");
    protected JMenuItem jmiListas = new JMenuItem("Listas", KeyEvent.VK_L);
    protected JMenuItem jmiTabelas = new JMenuItem("Tabelas", KeyEvent.VK_T);
    protected JMenuItem jmiMenu = new JMenuItem("Menu", KeyEvent.VK_M);
    protected JMenuItem jmiUsuario = new JMenuItem("Usuários", KeyEvent.VK_U);
    protected JMenuItem jmiPerfil = new JMenuItem("Perfis", KeyEvent.VK_P);
    protected JMenuItem jmiGerar = new JMenuItem("Gerar", KeyEvent.VK_G);
    protected JMenuItem jmiVersao = new JMenuItem("Versões", KeyEvent.VK_G);
    protected MenuItem jmiSair = new MenuItem("Sair", KeyEvent.VK_S, "jmiSair", "sairSistema");

    protected JDesktopPane jDesktopPane = new JDesktopPane();

    protected static GUIProperties guiProps = new GUIProperties();
    protected List<TelaPadrao> telasAbertas = new ArrayList<>();
    protected TelaPadrao telaTabelas = null,
            telaListas = null,
            telaConsistenciaDB = null,
            telaMenus = null,
            telaUsuarios = null,
            telaPerfis = null,
            telaVersao = null,
            telaGeracaoDicionario = null;

    protected DicionarioControl dicionarioControl;

    public GerenciadorSistema() {
        super("Gerenciador do Sistema");

        setName("TELA_PRINCIPAL");

        updateLookAndFeel(GUIProperties.PLAF_ACRYL);

        montarMenus();

        getContentPane().add(jDesktopPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1900, 1000);
    }

    public void updateLookAndFeel(String lf) {
        try {
            // If new look handles the WindowDecorationStyle not in the same manner as the old look
            // we have to reboot our application.

            LookAndFeel oldLAF = UIManager.getLookAndFeel();
            boolean oldDecorated = false;
            if (oldLAF instanceof MetalLookAndFeel) {
                oldDecorated = true;
            }
            if (oldLAF instanceof AbstractLookAndFeel) {
                oldDecorated = AbstractLookAndFeel.getTheme().isWindowDecorationOn();
            }

            // reset to default theme
            if (lf.equals(GUIProperties.PLAF_METAL)) {
                javax.swing.plaf.metal.MetalLookAndFeel.setCurrentTheme(new javax.swing.plaf.metal.DefaultMetalTheme());
            } else if (lf.equals(GUIProperties.PLAF_ACRYL)) {
                com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_AERO)) {
                com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_ALUMINIUM)) {
                com.jtattoo.plaf.aluminium.AluminiumLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_BERNSTEIN)) {
                com.jtattoo.plaf.bernstein.BernsteinLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_FAST)) {
                com.jtattoo.plaf.fast.FastLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_GRAPHITE)) {
                com.jtattoo.plaf.graphite.GraphiteLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_HIFI)) {
                com.jtattoo.plaf.hifi.HiFiLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_LUNA)) {
                com.jtattoo.plaf.luna.LunaLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_MCWIN)) {
                com.jtattoo.plaf.mcwin.McWinLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_MINT)) {
                com.jtattoo.plaf.mint.MintLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_NOIRE)) {
                com.jtattoo.plaf.noire.NoireLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_SMART)) {
                com.jtattoo.plaf.smart.SmartLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_TEXTURE)) {
                com.jtattoo.plaf.texture.TextureLookAndFeel.setTheme("Default");
            } else if (lf.equals(GUIProperties.PLAF_CUSTOM)) {
//                com.jtattoo.plaf.custom.flx.FLXLookAndFeel.setTheme("Default");
//                com.jtattoo.plaf.custom.quantycarlo.DarkRoastLookAndFeel.setTheme("Default");
//                com.jtattoo.plaf.custom.systemx.SystemXLookAndFeel.setTheme("Default");
            }
            guiProps.setTheme("Default");
            guiProps.setLookAndFeel(lf);
            UIManager.setLookAndFeel(guiProps.getLookAndFeel());

            LookAndFeel newLAF = UIManager.getLookAndFeel();
            boolean newDecorated = false;
            if (newLAF instanceof MetalLookAndFeel) {
                newDecorated = true;
            }
            if (newLAF instanceof AbstractLookAndFeel) {
                newDecorated = AbstractLookAndFeel.getTheme().isWindowDecorationOn();
            }
            if (oldDecorated != newDecorated) {
                // Reboot the application
                Rectangle savedBounds = getBounds();
                dispose();
                //app = new JTattooDemo(savedBounds);
                setBounds(savedBounds);
            } else {
                updateComponentTree();
            }

            SwingUtilities.updateComponentTreeUI(this.rootPane);
        } catch (Exception ex) {
            System.out.println("Failed loading L&F: " + guiProps.getLookAndFeel() + " Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void updateComponentTree() {
        // Update the application
        getRootPane().updateUI();
        if (JTattooUtilities.getJavaVersion() >= 1.6) {
            Window windows[] = Window.getWindows();
            for (int i = 0; i < windows.length; i++) {
                if (windows[i].isDisplayable()) {
                    SwingUtilities.updateComponentTreeUI(windows[i]);
                }
            }
        } else {
            Frame frames[] = Frame.getFrames();
            for (int i = 0; i < frames.length; i++) {
                if (frames[i].isDisplayable()) {
                    SwingUtilities.updateComponentTreeUI(frames[i]);
                }
            }
        }
    }

    protected boolean telaAberta(TelaPadrao telaPadrao) {
        return telasAbertas.contains(telaPadrao);
    }

    public void montarMenus() {
        addMenuItem(jmDicionarioDados, jmiListas);
        addMenuItem(jmDicionarioDados, jmiTabelas);
        addMenuItem(jmDicionarioDados, jmiGerar);

        jmSistema.add(jmDicionarioDados);

        addMenuItem(jmSistema, jmiMenu);
        addMenuItem(jmSistema, jmiUsuario);
        addMenuItem(jmSistema, jmiPerfil);
        addMenuItem(jmSistema, jmiVersao);
        addMenuItem(jmSistema, jmiSair);

        jMenuBar.add(jmSistema);

        setJMenuBar(jMenuBar);
    }

    public void addMenuItem(JMenu jMenu, JMenuItem jMenuItem) {
        jMenu.add(jMenuItem);
        jMenuItem.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jmiTabelas) {
            abrirTelaTabelas();
        }
        if (e.getSource() == jmiListas) {
            abrirTelaListas();
        }
        if (e.getSource() == jmiGerar) {
            try {
                gerarDicionarioDados();
            } catch (ErroSistemaException ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == jmiMenu) {
            abrirTelaMenus();
        }

        if (e.getSource() == jmiUsuario) {
            abrirTelaUsuarios();
        }

        if (e.getSource() == jmiPerfil) {
            abrirTelaPerfis();
        }

        if (e.getSource() == jmiVersao) {
            abrirTelaVersao();
        }

        if (e.getSource() == jmiSair) {
            System.exit(0);
        }

    }

    public void abrirTelaListas() {
        if (telaListas == null) {
            telaListas = new TelaListas();
            telaListas.addInternalFrameListener(this);
            telasAbertas.add(telaListas);
            jDesktopPane.add(telaListas);
            telaListas.setVisible(true);
            centralizarTela(telaListas);
        }
        jDesktopPane.moveToFront(telaListas);

    }

    public void abrirTelaTabelas() {
        if (telaTabelas == null) {
            telaTabelas = new TelaTabelas();
            telaTabelas.addInternalFrameListener(this);
            telasAbertas.add(telaTabelas);
            jDesktopPane.add(telaTabelas);
            telaTabelas.setVisible(true);
            centralizarTela(telaTabelas);
        }
        jDesktopPane.moveToFront(telaTabelas);

    }
//
//    public void abrirTelaConsistenciaDB() {
//        if (telaConsistenciaDB == null) {
//            telaConsistenciaDB = new TelaConsistenciaDB();
//            telaConsistenciaDB.addInternalFrameListener(this);
//            telasAbertas.add(telaConsistenciaDB);
//            jDesktopPane.add(telaConsistenciaDB);
//            telaConsistenciaDB.setVisible(true);
//            centralizarTela(telaConsistenciaDB);
//        }
//        jDesktopPane.moveToFront(telaConsistenciaDB);
//
//    }

    public void abrirTelaMenus() {
        if (telaMenus == null) {
            telaMenus = new TelaMenus();
            telaMenus.addInternalFrameListener(this);
            telasAbertas.add(telaMenus);
            jDesktopPane.add(telaMenus);
            telaMenus.setVisible(true);
            centralizarTela(telaMenus);
        }
        jDesktopPane.moveToFront(telaMenus);

    }

    private void abrirTelaUsuarios() {
        if (telaUsuarios == null) {
            telaUsuarios = new TelaUsuarios();
            telaUsuarios.addInternalFrameListener(this);
            telasAbertas.add(telaUsuarios);
            jDesktopPane.add(telaUsuarios);
            telaUsuarios.setVisible(true);
            centralizarTela(telaUsuarios);
        }
        jDesktopPane.moveToFront(telaUsuarios);
    }

    private void abrirTelaPerfis() {
        if (telaPerfis == null) {
            telaPerfis = new TelaPerfis();
            telaPerfis.addInternalFrameListener(this);
            telasAbertas.add(telaPerfis);
            jDesktopPane.add(telaPerfis);
            telaPerfis.setVisible(true);
            centralizarTela(telaPerfis);
        }
        jDesktopPane.moveToFront(telaPerfis);
    }

    private void abrirTelaVersao() {
        if (telaVersao == null) {
            telaVersao = new TelaVersao();
            telaVersao.addInternalFrameListener(this);
            telasAbertas.add(telaVersao);
            jDesktopPane.add(telaVersao);
            telaVersao.setVisible(true);
            centralizarTela(telaVersao);
        }
        jDesktopPane.moveToFront(telaVersao);
    }

    public void gerarDicionarioDados() throws ErroSistemaException {
        if (telaGeracaoDicionario == null) {
            telaGeracaoDicionario = new TelaGeracaoDicionario();
            telaGeracaoDicionario.addInternalFrameListener(this);
            telasAbertas.add(telaGeracaoDicionario);
            jDesktopPane.add(telaGeracaoDicionario);
            telaGeracaoDicionario.setVisible(true);
            centralizarTela(telaGeracaoDicionario);
        }
        jDesktopPane.moveToFront(telaGeracaoDicionario);
    }

    public static void setCentro(JInternalFrame com, JDesktopPane desktop, int menosX, int menosY) {
        Dimension dx = desktop.getSize();
        Dimension ds = com.getSize();
        com.setLocation(((dx.width - ds.width) / 2) - menosX, ((dx.height - ds.height) / 2) - menosY);
    }

    public void centralizarTela(JInternalFrame jInternalFrame) {
        jInternalFrame.setLocation((jDesktopPane.getToolkit().getScreenSize().width - jInternalFrame.getSize().width) / 2,
                (jDesktopPane.getToolkit().getScreenSize().height - jInternalFrame.getSize().height) / 2);
    }

    public void sairSistema() {
        System.exit(0);
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
        telasAbertas.remove((TelaPadrao) e.getInternalFrame());

        if (e.getInternalFrame() == telaListas) {
            telaListas = null;
        }

        if (e.getInternalFrame() == telaTabelas) {
            telaTabelas = null;
        }

        if (e.getInternalFrame() == telaConsistenciaDB) {
            telaConsistenciaDB = null;
        }

        if (e.getInternalFrame() == telaMenus) {
            telaMenus = null;
        }

        if (e.getInternalFrame() == telaUsuarios) {
            telaUsuarios = null;
        }

        if (e.getInternalFrame() == telaPerfis) {
            telaPerfis = null;
        }

        if (e.getInternalFrame() == telaGeracaoDicionario) {
            telaGeracaoDicionario = null;
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

}
