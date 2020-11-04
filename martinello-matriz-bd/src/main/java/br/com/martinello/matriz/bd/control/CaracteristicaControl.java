/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.control;

import br.com.martinello.matriz.bd.model.dao.CaracteristicaDAO;
import br.com.martinello.matriz.bd.model.dao.SubCategoriaDAO;
import br.com.martinello.matriz.bd.model.domain.Caracteristica;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class CaracteristicaControl {

    private SubCategoriaDAO subCategoriaDAO;
    private CaracteristicaDAO caracteristicaDAO;

    public CaracteristicaControl() {
        subCategoriaDAO = new SubCategoriaDAO();
        caracteristicaDAO = new CaracteristicaDAO();
    }

    public List<Caracteristica> listarCaracteristica(Caracteristica carcacteristica) throws ErroSistemaException {
        return this.caracteristicaDAO.selectCaracteristica(carcacteristica);
    }

    public List<Caracteristica> listarCaracteristicas(Caracteristica carcacteristica) throws ErroSistemaException {
        return this.caracteristicaDAO.selectCaracteristicas(carcacteristica);
    }

    /*Insert caracteristica  e caracteristica Item*/
    public void insertCaracteristicas(List<Caracteristica> lcaracteristica) throws ErroSistemaException {
        this.caracteristicaDAO.insertCaracteristicas(lcaracteristica);
    }

    public Caracteristica buscarCaracteristica(String codigo) throws ErroSistemaException {
        return this.caracteristicaDAO.buscarCaracteristica(codigo);
    }

    public void updateCaracteristica(List<Caracteristica> lcaracteristica) throws ErroSistemaException {
        this.caracteristicaDAO.updateCaracteristica(lcaracteristica);
    }

    public String validaAltCategoria(String codigo) throws ErroSistemaException {
        return this.subCategoriaDAO.validaAltCategoria(codigo);
    }
}
