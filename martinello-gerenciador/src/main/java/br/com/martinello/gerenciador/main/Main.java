/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.main;

import br.com.martinello.bd.matriz.model.dao.ConexaoOracle;
import br.com.martinello.gerenciador.view.GerenciadorSistema;
import javax.persistence.EntityManager;
import javax.swing.UIManager;

/**
 *
 * @author Sidnei
 */
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //EntityManager emDerby = ConexaoDerby.getEntityManager();
        EntityManager emOracle = ConexaoOracle.getEntityManager();
//            RelacionamentoColuna relacionamentoColuna = em.find(RelacionamentoColuna.class, 604l);

//            em.remove(relacionamentoColuna);
//            try {
//                TabelaControl tabelaControl = new TabelaControl();
//                //tabelaControl.gerarTabelasTeste();
//            } catch (ErroSistemaException ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            }
        java.awt.EventQueue.invokeLater(() -> {
            new GerenciadorSistema().setVisible(true);
        });

    }
}
