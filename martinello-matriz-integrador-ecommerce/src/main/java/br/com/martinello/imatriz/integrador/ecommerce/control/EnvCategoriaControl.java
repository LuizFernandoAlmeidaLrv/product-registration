/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.imatriz.integrador.ecommerce.control;

import br.com.martinello.matriz.bd.integracao.control.CategoriaControlInt;
import br.com.martinello.matriz.bd.transients.CategoriaTray;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import br.com.martinello.matriz.util.integracao.ConexaoWebService;
import br.com.martinello.matriz.util.integracao.Retorno;
import com.google.gson.Gson;
import java.util.List;

/**
 *
 * @author rafael
 */
public class EnvCategoriaControl {

    public EnvCategoriaControl() {
    }

    public void enviarCategoria() throws ErroSistemaException {
        Gson gson = new Gson();
        CategoriaControlInt categoriaControlInt = new CategoriaControlInt();

        List<CategoriaTray> lCategoriaTray = categoriaControlInt.buscarProdutoIntegrarEcommerce();
        for (CategoriaTray categoriaTray : lCategoriaTray) {

            /* Necessario realizar uma nova busca para pegar o registro atualizado. */
            CategoriaTray categoriaTrayAtual = new CategoriaTray();
            categoriaTrayAtual = categoriaControlInt.buscarProdutoIntegrarEcommerce(Long.parseLong(categoriaTray.getCategoriaERPId()));

            if (categoriaTrayAtual.getCategoriaERPPaiId() != null && categoriaTrayAtual.getCategoriaERPPaiId().length() > 0) {
                if (categoriaTrayAtual.getCategoriaPaiId() == null || (categoriaTrayAtual.getCategoriaPaiId() != null && categoriaTrayAtual.getCategoriaPaiId() == 0)) {
                    throw new ErroSistemaException("Categoria pai não integrada. ");
                }
            }

            ConexaoWebService conexaoWebService = new ConexaoWebService();
            Retorno retorno = new Retorno();

            /* Já possui ID do E-commerce */
            if (categoriaTrayAtual.getId() != null && categoriaTrayAtual.getId() > 0) {
                retorno = conexaoWebService.executarServico("https://api.fbits.net/categorias/" + categoriaTrayAtual.getId(), "", gson.toJson(categoriaTrayAtual), "PUT", "");

                if (!retorno.getStatus().equals(ConexaoWebService.OK)) {
                    throw new ErroSistemaException(retorno.getRetorno());
                }
            } else {
                /*Necessario verificar se existe antes de tentar nova inclusÃo. */
                retorno = conexaoWebService.executarServico("https://api.fbits.net/categorias/erp/", categoriaTrayAtual.getCategoriaERPId(), "", "GET", "");
                if (retorno.getStatus().equalsIgnoreCase(ConexaoWebService.OK) && !retorno.getRetorno().equalsIgnoreCase("null")) {

                    CategoriaTray categoriaTrayConsulta = gson.fromJson(retorno.getRetorno(), CategoriaTray.class);

                    categoriaTrayAtual.setId(categoriaTrayConsulta.getId());
                    categoriaControlInt.atualizarCodigoEcommerce(categoriaTrayAtual);

                    retorno = conexaoWebService.executarServico("https://api.fbits.net/categorias/" + categoriaTrayAtual.getId(), "", gson.toJson(categoriaTrayAtual), "PUT", "");

                    if (!retorno.getStatus().equals(ConexaoWebService.OK)) {
                        throw new ErroSistemaException(retorno.getRetorno());
                    }
                } else {
                    /* Insere um novo */
                    retorno = conexaoWebService.executarServico("https://api.fbits.net/categorias/", "", gson.toJson(categoriaTrayAtual), "POST", "");

                    /* Atualiza */
                    if (retorno.getStatus().equals(ConexaoWebService.OK)) {
                        categoriaTrayAtual.setId(Long.parseLong(retorno.getRetorno()));
                        categoriaControlInt.atualizarCodigoEcommerce(categoriaTrayAtual);
                    } else {
                        throw new ErroSistemaException(retorno.getRetorno());
                    }
                }

            }

        }

    }

}
