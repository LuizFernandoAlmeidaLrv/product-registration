/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.cadastro.view;

import br.com.martinello.matriz.bd.control.ConsultarUsuarioControl;
import br.com.martinello.matriz.bd.control.UsuarioControl;
import br.com.martinello.matriz.bd.model.domain.Usuario;
import br.com.martinello.matriz.bd.model.domain.UsuarioLogin;
import br.com.martinello.matriz.componentesbasicos.paineis.JPStatus;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import org.xmlpull.v1.XmlPullParserException;

/**
 *
 * @author Sidnei
 */
public class TelaLogin extends javax.swing.JFrame {

    /**
     * Creates new form TelaLogin
     */
    protected UsuarioControl usuarioControl;
    protected int retorno;

    public TelaLogin() {
        usuarioControl = new UsuarioControl();

        initComponents();

        setLocationRelativeTo(null);

        try {
            String nomecomputador = InetAddress.getLocalHost().getHostName();
            if (nomecomputador.equalsIgnoreCase("TI07")) {
                csUsuario.setText("rafael.rosar");
                csSenha.setText("123456");
            } else if (nomecomputador.equalsIgnoreCase("TI09")) {
                csUsuario.setText("luiz.almeida");
                csSenha.setText("M@rtinell0");
            }
        } catch (UnknownHostException e) {
            System.out.println(e);
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

        jlUsuario = new javax.swing.JLabel();
        jlSenha = new javax.swing.JLabel();
        csUsuario = new br.com.martinello.matriz.componentesbasicos.CampoString();
        csSenha = new br.com.martinello.matriz.componentesbasicos.CampoSenha();
        jlEntrar = new br.com.martinello.matriz.componentesbasicos.Botao();
        jbSair = new br.com.martinello.matriz.componentesbasicos.Botao();
        jpsStatusLogin = new br.com.martinello.matriz.componentesbasicos.paineis.JPStatus();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jlUsuario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlUsuario.setText("Usuário:");

        jlSenha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlSenha.setText("Senha:");

        jlEntrar.setMnemonic('e');
        jlEntrar.setText("Entrar");
        jlEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jlEntrarActionPerformed(evt);
            }
        });

        jbSair.setMnemonic('s');
        jbSair.setText("Sair");
        jbSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpsStatusLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbSair, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(csUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(csSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbSair, jlEntrar});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jlSenha, jlUsuario});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {csSenha, csUsuario});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(csSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbSair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpsStatusLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 24, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jbSair, jlEntrar});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jlSenha, jlUsuario});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {csSenha, csUsuario});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jlEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jlEntrarActionPerformed
        try {
            ConsultarUsuarioControl consultar = new ConsultarUsuarioControl();
            if (consultar.ConsultarUsuarioControl(csUsuario.getString(), csSenha.getString())) {
                Usuario usuarioLogin = usuarioControl.login(csUsuario.getString());
                UsuarioLogin login = new UsuarioLogin();
                login.setUsuario(usuarioLogin);
                login.setDataLogin(new Date());
                login.setSistemaOperacional(System.getProperty("os.name"));
                login.setNomeUsuarioSO(System.getProperty("user.name"));
                login.setNomeEstacao(InetAddress.getLocalHost().getHostName());
                usuarioLogin.getLogins().add(login);
                usuarioControl.setUsuario(usuarioLogin);
                dispose();
                usuarioControl.salvarAtualizarLogin();
                new TelaPrincipal(usuarioControl.getUsuario());

            } else {
                jpsStatusLogin.setStatus("Usuário ou senha inválidos.", JPStatus.ERRO);
            }
        } catch (IOException | XmlPullParserException ex) {
            jpsStatusLogin.setStatus(ex.getMessage(), JPStatus.ERRO);
        } catch (ErroSistemaException ex) {
            jpsStatusLogin.setStatus(ex.getMessage(), JPStatus.ERRO);
        } catch (SQLException ex) {
           jpsStatusLogin.setStatus(ex.getMessage(), JPStatus.ERRO);
        }
    }//GEN-LAST:event_jlEntrarActionPerformed

    private void jbSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSairActionPerformed
        int sair = JOptionPane.showConfirmDialog(rootPane, "Deseja realmente sair?", "Confirma", JOptionPane.YES_NO_OPTION);
        if (sair == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_jbSairActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.matriz.componentesbasicos.CampoSenha csSenha;
    private br.com.martinello.matriz.componentesbasicos.CampoString csUsuario;
    private br.com.martinello.matriz.componentesbasicos.Botao jbSair;
    private br.com.martinello.matriz.componentesbasicos.Botao jlEntrar;
    private javax.swing.JLabel jlSenha;
    private javax.swing.JLabel jlUsuario;
    private br.com.martinello.matriz.componentesbasicos.paineis.JPStatus jpsStatusLogin;
    // End of variables declaration//GEN-END:variables
}