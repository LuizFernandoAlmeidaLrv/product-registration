/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.integracao.control;

import br.com.martinello.matriz.bd.integracao.model.dao.PendenciaDAOInt;
import br.com.martinello.matriz.bd.integracao.model.domain.Pendencia;
import br.com.martinello.matriz.bd.transients.FiltroInt;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class PendenciaControlInt {

    private PendenciaDAOInt pendenciaDAOInt;

    public PendenciaControlInt() {
        pendenciaDAOInt = new PendenciaDAOInt();
    }

    public void inserirPendencia(Pendencia pendencia) throws ErroSistemaException {
        pendenciaDAOInt.inserirPendencia(pendencia);
    }
    
    public void atualizarSituacao(Pendencia pendencia) throws ErroSistemaException {
        pendenciaDAOInt.atualizarSituacaoPendencia(pendencia);
    }

    public List<Pendencia> buscarPendencia(List<FiltroInt> filtroInt) throws ErroSistemaException {
        return this.pendenciaDAOInt.buscarPendenciaIntegrarEcommerce(filtroInt);
    }
}
