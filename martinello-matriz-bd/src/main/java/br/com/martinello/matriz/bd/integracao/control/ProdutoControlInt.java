/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.integracao.control;

import br.com.martinello.matriz.bd.integracao.model.dao.ProdutoDAOInt;
import br.com.martinello.matriz.bd.model.dao.relatorio.ProdutoRelatorioDAO;
import br.com.martinello.matriz.bd.transients.CatalogoErp;
import br.com.martinello.matriz.bd.transients.ChavePendencia;
import br.com.martinello.matriz.bd.transients.ProdutoImagemTray;
import br.com.martinello.matriz.bd.transients.ProdutoInformacaoTray;
import br.com.martinello.matriz.bd.transients.ProdutoTray;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ProdutoControlInt {

    private ProdutoDAOInt produtoDAO;
    private ProdutoRelatorioDAO produtoRel;

    public ProdutoControlInt() {
        produtoDAO = new ProdutoDAOInt();
        produtoRel = new ProdutoRelatorioDAO();
    }

    public List<ProdutoTray> buscarProdutoIntegrarEcommerce() throws ErroSistemaException {
        return this.produtoDAO.buscarProdutoIntegrarEcommerce();
    }

    public ProdutoTray buscarProdutoIntegrarEcommerce(ChavePendencia chavePendencia) throws ErroSistemaException {
        return this.produtoDAO.buscarProdutoIntegrarEcommerce(chavePendencia);
    }

    public ProdutoImagemTray buscarProdutoImagemIntegrarEcommerce(ChavePendencia chavePendencia) throws ErroSistemaException {
        return this.produtoDAO.buscarProdutoImagensIntegrarEcommerce(chavePendencia);
    }

    public ProdutoInformacaoTray buscarProdutoInformacaoIntegrarEcommerce(ChavePendencia chavePendencia) throws ErroSistemaException {
        return this.produtoDAO.buscarProdutoInformacoesIntegrarEcommerce(chavePendencia);
    }

    public CatalogoErp buscarProdutoCatalogoEcommerce(ChavePendencia chavePendencia) throws ErroSistemaException {
        return this.produtoDAO.buscarProdutoCategoriaRelacionada(chavePendencia);
    }

    public void atualizarCodigoEcommerce(ProdutoTray produtoTray) throws ErroSistemaException {
        produtoDAO.atualizarCodigoEcommerce(produtoTray);
    }

    public void atualizarCatalogoCodigoEcommerce(CatalogoErp catalogoErp) throws ErroSistemaException {
        produtoDAO.atualizarCatalogoCodigoEcommerce(catalogoErp);
    }

    public void atualizarImagemCodigoEcommerce(ProdutoImagemTray produtoImagemTray) throws ErroSistemaException {
        produtoDAO.atualizarImagemCodigoEcommerce(produtoImagemTray);
    }

    public void atualizarInformacoesCodigoEcommerce(ChavePendencia chavePendencia, ProdutoInformacaoTray produtoInformacaoTray) throws ErroSistemaException {
        produtoDAO.atualizarInformacoesCodigoEcommerce(chavePendencia, produtoInformacaoTray);
    }
}
