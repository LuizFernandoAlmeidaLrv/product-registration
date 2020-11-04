/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.control;

import br.com.martinello.matriz.bd.model.dao.RelacionamentoDAO;
import br.com.martinello.matriz.bd.model.domain.Produto;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import br.com.martinello.matriz.util.filtro.Filtro;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class RelacionamentoControl {

    RelacionamentoDAO relacionamentoDAO;

    public RelacionamentoControl() {
        relacionamentoDAO = new RelacionamentoDAO();
    }

    public List<Produto> Buscar(Produto produto) throws ErroSistemaException {
        return this.relacionamentoDAO.SelectRelacionados(produto);
    }

    public List<Produto> listarProduto(List<Filtro> lFiltros) throws ErroSistemaException {
        return this.relacionamentoDAO.listarProduto(lFiltros);
    }

    public boolean agrupar(Produto produto) throws ErroSistemaException {
         return this.relacionamentoDAO.agrupar(produto);
    }

}
