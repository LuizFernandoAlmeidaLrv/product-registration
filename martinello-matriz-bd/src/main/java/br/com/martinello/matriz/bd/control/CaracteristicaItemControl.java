/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.control;

import br.com.martinello.matriz.bd.model.dao.CaracteristicaDAO;
import br.com.martinello.matriz.bd.model.dao.CaracteristicaItemDAO;
import br.com.martinello.matriz.bd.model.dao.SubCategoriaDAO;
import br.com.martinello.matriz.bd.model.domain.Caracteristica;
import br.com.martinello.matriz.bd.model.domain.CaracteristicaItem;
import br.com.martinello.matriz.bd.model.domain.Produto;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class CaracteristicaItemControl {

    private SubCategoriaDAO subCategoriaDAO;
    private CaracteristicaDAO caracteristicaDAO;
    private CaracteristicaItemDAO caracteristicaItemDAO;

    public CaracteristicaItemControl() {
        subCategoriaDAO = new SubCategoriaDAO();
        caracteristicaDAO = new CaracteristicaDAO();
        caracteristicaItemDAO = new CaracteristicaItemDAO();
    }

    public List<CaracteristicaItem> listarCarItem(CaracteristicaItem caracteristicaItem) throws ErroSistemaException {
        return this.caracteristicaItemDAO.selectCarItem(caracteristicaItem);
    }

    public boolean insertCarItem(CaracteristicaItem caracteristicaItem) throws ErroSistemaException {
        return this.caracteristicaItemDAO.insertCarItem(caracteristicaItem);
    }

    public CaracteristicaItem buscarCarItem(String codigo) throws ErroSistemaException {
        return this.caracteristicaItemDAO.buscarCarItem(codigo);
    }

    public boolean updateCarItem(CaracteristicaItem caracteristicaItem) throws ErroSistemaException {
        return this.caracteristicaItemDAO.updateCarItem(caracteristicaItem);
    }

    /* public List<Categoria> listarCategoria(Categoria categoria) throws ErroSistemaException {
        return this.subCategoriaDAO.(categoria);
    }*/
    public String validaAltCategoria(String codigo) throws ErroSistemaException {
        return this.subCategoriaDAO.validaAltCategoria(codigo);
    }

    public List<Caracteristica> listarJcbCarItens(Caracteristica caracteristica) throws ErroSistemaException {
       return this.caracteristicaItemDAO.listarJcbCarItens(caracteristica);
    }

    public List<CaracteristicaItem> listarCaracteristicaProduto(Produto produto) {
       return this.caracteristicaItemDAO.listarCaracteristicaProduto(produto);
    }

   

}
