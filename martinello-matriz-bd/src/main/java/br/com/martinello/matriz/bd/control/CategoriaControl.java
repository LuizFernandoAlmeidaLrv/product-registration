/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.control;

import br.com.martinello.matriz.bd.model.dao.CategoriaDAO;
import br.com.martinello.matriz.bd.model.dao.relatorio.CategoriaRelatorioDAO;
import br.com.martinello.matriz.bd.model.domain.Categoria;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import br.com.martinello.matriz.util.filtro.Filtro;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class CategoriaControl {

    private CategoriaDAO categoriaDAO;
    private CategoriaRelatorioDAO catRelDAO;

    public CategoriaControl() {
        categoriaDAO = new CategoriaDAO();
        catRelDAO = new CategoriaRelatorioDAO();
    }

    public List<Categoria> listarCategoria(Categoria categoria) throws ErroSistemaException {
        return this.categoriaDAO.selectCategoria(categoria);
    }

    public boolean insertCategoria(Categoria categoria) throws ErroSistemaException {
        return this.categoriaDAO.insertCategoria(categoria);
    }

    public Categoria buscarCategoria(String codigo) throws ErroSistemaException {
        return this.categoriaDAO.buscarCategoria(codigo);
    }

    public boolean updateCategoria(Categoria categoria) throws ErroSistemaException {
        return this.categoriaDAO.updateCategoria(categoria);
    }

    public boolean excluir(Categoria categoria) throws ErroSistemaException {
        return this.categoriaDAO.excluirCategoria(categoria); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Categoria> listarCatalogo(List<Filtro> lFiltros) throws ErroSistemaException {
        return this.categoriaDAO.selectCatalogo(lFiltros);

    }

    public List<Categoria> listarCatalogo(Categoria categoria) throws ErroSistemaException {
        return this.categoriaDAO.listarCatalogo(categoria);
    }

    public void insertCatalogo(Categoria categoria) throws ErroSistemaException {
        categoriaDAO.insertCatalogo(categoria);
    }

    public boolean updateCatalogo(Categoria categoria) throws ErroSistemaException {
        return this.categoriaDAO.updateCatalogo(categoria);
    }

    public boolean excluirCatalogo(Categoria categoria) throws ErroSistemaException {
        return this.categoriaDAO.excluirCatalogo(categoria);
    }

    public List<Categoria> listarCatalogoItem(String codigo) throws ErroSistemaException {
        return this.categoriaDAO.listarCatalogoItem(codigo);
    }

    public void relacaoCategoria(int idUsuario) throws ErroSistemaException {
        this.catRelDAO.relacaoCategoria(idUsuario);
    }

}
