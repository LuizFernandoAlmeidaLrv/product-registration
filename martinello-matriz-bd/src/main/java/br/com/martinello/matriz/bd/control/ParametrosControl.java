/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.control;

import br.com.martinello.matriz.bd.model.dao.ParametrosDAO;
import br.com.martinello.matriz.bd.model.domain.Parametro;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ParametrosControl {
    public ParametrosDAO parametroDAO = new ParametrosDAO();
    
    public ParametrosControl(){
     parametroDAO = new ParametrosDAO();        
    }

    public List<Parametro> buscarParametros(Parametro parametro) throws ErroSistemaException {
      return this.parametroDAO.ListParametros(parametro);
    }
    
    public Parametro buscarParametro(Parametro parametro) throws ErroSistemaException {
      return this.parametroDAO.parametro(parametro);
    }

    public void updateParametro(Parametro parametro) throws ErroSistemaException {
        parametroDAO.updateParametro(parametro);
    }

    public void insertParametro(Parametro parametro) throws ErroSistemaException {
        parametroDAO.insertParametro(parametro);
    }
    
}
