/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.componentesbasicos.paineis;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.JXBusyLabel;

/**
 *
 * @author Sidnei
 */
public class TelaProcessamento extends JDialog implements WindowListener {

    protected JXBusyLabel jxblStatus = new JXBusyLabel();

    protected Thread thread;

    public TelaProcessamento(String texto) {
        super();
        setModal(true);
        jxblStatus.setText(texto);
        jxblStatus.setDelay(100);
        jxblStatus.setBusy(true);
        getContentPane().add(jxblStatus);
        setSize(200, 100);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(this);
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (thread != null) {
            if (JOptionPane.showConfirmDialog(rootPane, "Confirma cancelamento do processamento?", "Cancela", JOptionPane.YES_NO_OPTION) == 0) {
                thread.stop();
                this.dispose();
            }
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
