/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.control;

import br.com.martinello.matriz.bd.model.dao.ArquivoDAO;
import br.com.martinello.matriz.bd.model.domain.Arquivo;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ArquivoControl {
     public ArquivoDAO arquivoDAO = new ArquivoDAO();
    
    public ArquivoControl(){
     arquivoDAO = new ArquivoDAO();        
    }

    public List<Arquivo> listarArquivos(String codigo) throws ErroSistemaException {
       return this.arquivoDAO.listarArquivos(codigo);
    }
}
