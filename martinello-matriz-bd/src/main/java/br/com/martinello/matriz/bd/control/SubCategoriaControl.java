/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.control;

import br.com.martinello.matriz.bd.model.dao.CategoriaDAO;
import br.com.martinello.matriz.bd.model.dao.SubCategoriaDAO;
import br.com.martinello.matriz.bd.model.dao.relatorio.SubCategoriaRelatorioDAO;
import br.com.martinello.matriz.bd.model.domain.Categoria;
import br.com.martinello.matriz.bd.model.domain.SubCategoria;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class SubCategoriaControl {

    private SubCategoriaDAO subCategoriaDAO;
    private CategoriaDAO categoriaDAO;
    private SubCategoriaRelatorioDAO subCatRelDAO;

    public SubCategoriaControl() {
        subCategoriaDAO = new SubCategoriaDAO();
        categoriaDAO = new CategoriaDAO();
        subCatRelDAO = new SubCategoriaRelatorioDAO();
    }

    public List<SubCategoria> listarSubCategoria(SubCategoria subCategoria) throws ErroSistemaException {
        return this.subCategoriaDAO.selectSubCategoria(subCategoria);
    }

    public int insertSubCategoria(SubCategoria subCategoria) throws ErroSistemaException {
        return this.subCategoriaDAO.insertSubCategoria(subCategoria);
    }

    public SubCategoria buscarSubCategoria(SubCategoria subCategoria) throws ErroSistemaException {
        return this.subCategoriaDAO.buscarSubCategoria(subCategoria);
    }

    public int updateSubCategoria(SubCategoria subCategoria) throws ErroSistemaException {
        return this.subCategoriaDAO.updateSubCategoria(subCategoria);
    }

    public List<Categoria> listarJcbCategoria(Categoria categoria) throws ErroSistemaException {
        return this.categoriaDAO.listarJcbCategorias(categoria);
    }

    public String validaAltCategoria(String codigo) throws ErroSistemaException {
       return this.subCategoriaDAO.validaAltCategoria(codigo);
    }

    public List<SubCategoria> listarJcbSubCategoria(SubCategoria subCategoria) throws ErroSistemaException {
        return this.subCategoriaDAO.listarJcbSubCategorias(subCategoria);
    }

    public boolean excluir(SubCategoria subCategoria) throws ErroSistemaException {
       return this.subCategoriaDAO.excluirSubCategoria(subCategoria);
    }

    public void relacaoSubCategoria(int idUsuario) throws ErroSistemaException {
        this.subCatRelDAO.relacaoSubCategoria(idUsuario);
    }

    public boolean gerarFichaSubCategoria(int idCategoria, int idSubCategoria, String visSit, String sit) throws ErroSistemaException {
        return this.subCatRelDAO.gerarFichaSubCategoria(idCategoria, idSubCategoria, visSit, sit);
    }
}
