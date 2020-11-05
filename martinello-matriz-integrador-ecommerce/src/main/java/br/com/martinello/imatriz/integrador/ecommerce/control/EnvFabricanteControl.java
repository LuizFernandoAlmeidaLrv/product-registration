/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.imatriz.integrador.ecommerce.control;

import br.com.martinello.matriz.bd.integracao.control.FabricanteControlInt;
import br.com.martinello.matriz.bd.transients.ChavePendencia;
import br.com.martinello.matriz.bd.transients.FabricanteTray;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import br.com.martinello.matriz.util.integracao.ConexaoWebService;
import br.com.martinello.matriz.util.integracao.Retorno;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;

/**
 *
 * @author rafael
 */
public class EnvFabricanteControl {

    public EnvFabricanteControl() {
    }

    public void enviarFabricante(ChavePendencia chavePendencia) throws ErroSistemaException {
        Gson gson = new GsonBuilder().create();

        FabricanteControlInt fabricanteControl = new FabricanteControlInt();

        FabricanteTray fabricanteTray = fabricanteControl.buscarProdutoIntegrarEcommerce(chavePendencia);

        fabricanteTray.setUrlCarrossel("");
        fabricanteTray.setUrlLink("");
        fabricanteTray.setUrlLogoTipo("");

        ConexaoWebService conexaoWebService = new ConexaoWebService();
        Retorno retorno = new Retorno();

        /* Já possui ID do E-commerce */
        if (fabricanteTray.getFabricanteId() != null && fabricanteTray.getFabricanteId() > 0) {
            retorno = conexaoWebService.executarServico("https://api.fbits.net/fabricantes/" + fabricanteTray.getFabricanteId(), "", gson.toJson(fabricanteTray), "PUT", "");

            if (!retorno.getStatus().equals(ConexaoWebService.OK)) {
                throw new ErroSistemaException(retorno.getRetorno());
            }
        } else {
            /*Necessario verificar se existe antes de tentar nova inclusão. */
            retorno = conexaoWebService.executarServico("https://api.fbits.net/fabricantes/", fabricanteTray.getNome(), "", "GET", "");
            if (retorno.getStatus().equalsIgnoreCase(ConexaoWebService.OK) && !retorno.getRetorno().equalsIgnoreCase("null")) {

                FabricanteTray fabricanteTrayRecebido = gson.fromJson(retorno.getRetorno(), FabricanteTray.class);
                fabricanteTray.setFabricanteId(fabricanteTrayRecebido.getFabricanteId());
                fabricanteControl.atualizarCodigoEcommerce(fabricanteTray);

                retorno = conexaoWebService.executarServico("https://api.fbits.net/fabricantes/" + fabricanteTray.getFabricanteId(), "", gson.toJson(fabricanteTray), "PUT", "");

                if (!retorno.getStatus().equals(ConexaoWebService.OK)) {
                    throw new ErroSistemaException(retorno.getRetorno());
                }
            } else {
                /* Realmente nÃo existe, logo inserir. */
                retorno = conexaoWebService.executarServico("https://api.fbits.net/fabricantes/", "", gson.toJson(fabricanteTray), "POST", "");

                /* Atualiza */
                if (retorno.getStatus().equals(ConexaoWebService.OK)) {
                    fabricanteTray.setFabricanteId(Long.parseLong(retorno.getRetorno()));
                    fabricanteControl.atualizarCodigoEcommerce(fabricanteTray);
                } else {
                    throw new ErroSistemaException(retorno.getRetorno());
                }
            }
        }

    }

}
