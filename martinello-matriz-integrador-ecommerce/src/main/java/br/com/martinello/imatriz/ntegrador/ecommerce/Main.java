/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.imatriz.ntegrador.ecommerce;

import br.com.martinello.imatriz.ntegrador.ecommerce.control.EnvProdutoControl;
import br.com.martinello.matriz.util.integracao.ConexaoWebService;
import br.com.martinello.matriz.util.integracao.Retorno;
import com.google.gson.Gson;

/**
 *
 * @author rafael
 */
public class Main {

    public static void main(String[] args) {
        teste();
    }

    public static void teste() {
        try {
            Gson gson = new Gson();
            ConexaoWebService conexaoWebService;

            Retorno retorno = new Retorno();

            EnvProdutoControl envProdutoControl = new EnvProdutoControl();

            Processamento processamento = new Processamento();
            processamento.processar();

            //envProdutoControl.enviarProduto();
            // EnvCategoriaControl envCategoriaControl = new EnvCategoriaControl();
            // envCategoriaControl.enviarCategoria();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
