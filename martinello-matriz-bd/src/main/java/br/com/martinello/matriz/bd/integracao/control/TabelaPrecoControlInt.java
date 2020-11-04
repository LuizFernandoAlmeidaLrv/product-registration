/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.integracao.control;

import br.com.martinello.matriz.bd.integracao.model.dao.FabricanteDAOInt;
import br.com.martinello.matriz.bd.transients.ChavePendencia;
import br.com.martinello.matriz.bd.transients.FabricanteTray;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class TabelaPrecoControlInt {

    private FabricanteDAOInt fabricanteDao;

    public TabelaPrecoControlInt() {
        fabricanteDao = new FabricanteDAOInt();
    }

    public FabricanteTray buscarProdutoIntegrarEcommerce(ChavePendencia chavePendencia) throws ErroSistemaException {
        return this.fabricanteDao.buscarFabricanteIntegrarEcommerce(chavePendencia);
    }

    public void atualizarCodigoEcommerce(FabricanteTray fabricanteTray) throws ErroSistemaException {
        fabricanteDao.atualizarCodigoEcommerce(fabricanteTray);
    }

}
