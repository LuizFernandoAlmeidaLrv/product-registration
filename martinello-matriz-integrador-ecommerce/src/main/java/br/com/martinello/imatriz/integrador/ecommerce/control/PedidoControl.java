/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.imatriz.integrador.ecommerce.control;

import br.com.martinello.matriz.bd.transients.PedidoTray;
import br.com.martinello.matriz.util.integracao.ConexaoWebService;
import br.com.martinello.matriz.util.integracao.Retorno;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

/**
 *
 * @author rafael
 */
public class PedidoControl {

    public PedidoControl() {
    }

    public void baixarPedido() {
        ConexaoWebService conexaoWebService = new ConexaoWebService();
        Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

        Type pedidoType = new TypeToken<List<PedidoTray>>() {
        }.getType();

        //https://api.fbits.net/pedidos?dataInicial=2020-11-05%2000%3A00%3A00&dataFinal=2020-11-06%2000%3A00%3A00&enumTipoFiltroData=DataPedido
        Retorno retorno = conexaoWebService.executarServico("https://api.fbits.net/pedidos?dataInicial=2020-11-05%2000%3A00%3A00&dataFinal=2020-11-06%2000%3A00%3A00&enumTipoFiltroData=DataPedido", "", "", ConexaoWebService.GET, "");

        if (retorno.getStatus().equalsIgnoreCase("OK")) {

            try {
                List<PedidoTray> lPedidoTray = gson.fromJson(retorno.getRetorno(), pedidoType);
                int i = 0;

            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

    }

}
