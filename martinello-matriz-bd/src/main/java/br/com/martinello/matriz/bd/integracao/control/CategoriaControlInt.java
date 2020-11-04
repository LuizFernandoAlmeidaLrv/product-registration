/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.integracao.control;

import br.com.martinello.matriz.bd.integracao.model.dao.CategoriaDAOInt;
import br.com.martinello.matriz.bd.transients.CategoriaTray;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class CategoriaControlInt {

    private CategoriaDAOInt categoriaDAO;

    public CategoriaControlInt() {
        categoriaDAO = new CategoriaDAOInt();
    }

    public List<CategoriaTray> buscarProdutoIntegrarEcommerce() throws ErroSistemaException {
        return this.categoriaDAO.buscarCategoriaIntegrarEcommerce();
    }
    public CategoriaTray buscarProdutoIntegrarEcommerce(Long codCat) throws ErroSistemaException {
        return this.categoriaDAO.buscarCategoriaIntegrarEcommerce(codCat);
    }

    public void atualizarCodigoEcommerce(CategoriaTray categoriaTray) throws ErroSistemaException {
        categoriaDAO.atualizarCodigoEcommerce(categoriaTray);
    }

}
