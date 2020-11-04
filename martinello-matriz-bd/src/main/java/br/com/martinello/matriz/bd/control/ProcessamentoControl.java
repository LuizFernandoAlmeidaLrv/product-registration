/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.control;

import br.com.martinello.matriz.bd.model.dao.ProcessamentoDAO;
import br.com.martinello.matriz.bd.model.domain.Usuario;

/**
 *
 * @author luiz.almeida
 */
public class ProcessamentoControl {
    
     public ProcessamentoDAO processamentoDAO;

    
    public ProcessamentoControl(){
        processamentoDAO = new ProcessamentoDAO();
    }    

    public void encerrarProcessos(Usuario usuario) {
       this.processamentoDAO.encerrarProcessos(usuario);
    }
    
}
