/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.imatriz.ntegrador.ecommerce;

import br.com.martinello.imatriz.ntegrador.ecommerce.control.EnvCategoriaControl;
import br.com.martinello.imatriz.ntegrador.ecommerce.control.EnvFabricanteControl;
import br.com.martinello.imatriz.ntegrador.ecommerce.control.EnvProdutoControl;
import br.com.martinello.matriz.bd.integracao.control.PendenciaControlInt;
import br.com.martinello.matriz.bd.integracao.model.domain.Pendencia;
import br.com.martinello.matriz.bd.transients.ChavePendencia;
import br.com.martinello.matriz.bd.transients.FiltroInt;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import com.google.gson.Gson;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafael
 */
public class Processamento {

    public Processamento() {
    }

    public void processar() {
        String json = "";
        Gson gson = new Gson();
        PendenciaControlInt pendenciaControlInt = new PendenciaControlInt();

        List<FiltroInt> lFiltro = new LinkedList<>();
        lFiltro.add(new FiltroInt("USU_T000PEN", "USU_SITPEN", FiltroInt.STRING, FiltroInt.ARRAY, "P,E"));

        try {
            List<Pendencia> lPendencia = pendenciaControlInt.buscarPendencia(lFiltro);

            for (Pendencia pendencia : lPendencia) {

                /* Não permitir executar se tiver pendências anteriores com erro ou pendente. */
 /* Lock na pendência. */
                ChavePendencia chavePendencia = gson.fromJson(pendencia.getChave(), ChavePendencia.class);

                EnvCategoriaControl envCategoriaControl = new EnvCategoriaControl();
                envCategoriaControl.enviarCategoria();

                try {

                    if (pendencia.getProcesso().equals("PRODUTO")) {
                        EnvProdutoControl envProdutoControl = new EnvProdutoControl();
                        envProdutoControl.enviarProduto(chavePendencia);
                    } else if (pendencia.getProcesso().equals("CATALOGO")) {
                        EnvProdutoControl envProdutoControl = new EnvProdutoControl();
                        envProdutoControl.enviarRelacionamento(chavePendencia);
                    } else if (pendencia.getProcesso().equals("MARCA")) {
                        EnvFabricanteControl envFabricanteControl = new EnvFabricanteControl();
                        envFabricanteControl.enviarFabricante(chavePendencia);
                    } else if (pendencia.getProcesso().equals("CARACTERISTICA")) {
                        EnvProdutoControl envProdutoControl = new EnvProdutoControl();
                        envProdutoControl.enviarInformacoes(chavePendencia);
                    } else if (pendencia.getProcesso().equals("IMAGEM")) {
                        EnvProdutoControl envProdutoControl = new EnvProdutoControl();
                        envProdutoControl.enviarImagens(chavePendencia);
                    }

                    TimeUnit.MILLISECONDS.sleep(1250);
                    pendencia.setSituacao("S");
                    pendencia.setObservacao("OK");
                } catch (InterruptedException | ErroSistemaException ex) {
                    ex.printStackTrace();
                    pendencia.setSituacao("E");
                    pendencia.setObservacao(ex.getMessage());
                }

                System.out.println("Pendencia:  " + pendencia.getId() + " Chave: "
                        + pendencia.getChave() + " Situação:  "
                        + pendencia.getSituacao() + " Erro: " + pendencia.getObservacao());
                pendenciaControlInt.atualizarSituacao(pendencia);
                /* Atualizar a pendência e liberar o lock. */
            }
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
            Logger.getLogger(Processamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
