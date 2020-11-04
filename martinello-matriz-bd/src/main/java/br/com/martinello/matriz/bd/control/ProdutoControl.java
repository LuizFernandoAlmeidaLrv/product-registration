/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.control;

import br.com.martinello.matriz.bd.model.dao.ProdutoDAO;
import br.com.martinello.matriz.bd.model.dao.relatorio.ProdutoRelatorioDAO;
import br.com.martinello.matriz.bd.model.domain.Arquivo;
import br.com.martinello.matriz.bd.model.domain.Categoria;
import br.com.martinello.matriz.bd.model.domain.Produto;
import br.com.martinello.matriz.bd.transients.ProdutoImagemTray;
import br.com.martinello.matriz.bd.transients.ProdutoTray;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import br.com.martinello.matriz.util.filtro.Filtro;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ProdutoControl {

    private ProdutoDAO produtoDAO;
    private ProdutoRelatorioDAO produtoRel;

    public ProdutoControl() {
        produtoDAO = new ProdutoDAO();
        produtoRel = new ProdutoRelatorioDAO();
    }

    public List<Produto> pesquisar(List<Filtro> lFiltros) throws ErroSistemaException {
        return this.produtoDAO.pesquisar(lFiltros);
    }

    public List<Produto> listarProdutos(List<Filtro> lFiltros) throws ErroSistemaException {
        return this.produtoDAO.listarProdutos(lFiltros);
    }

    public List<Produto> listarCaracteristicaProduto(String codigo) throws ErroSistemaException {
        return this.produtoDAO.listarCaracteristicaProduto(codigo);
    }

    public Produto buscarProduto(String codigo) throws ErroSistemaException {
        return this.produtoDAO.buscarProduto(codigo);
    }

    public boolean InserirRelacionamento(Produto produto, List<Produto> lProduto, List<Arquivo> lArquivo) throws ErroSistemaException {
        return this.produtoDAO.inserirRelacionamento(produto, lProduto, lArquivo);
    }

    public List<Produto> listarCaracteristicas(Produto produto) throws ErroSistemaException {
        return this.produtoDAO.listarCaracteristicas(produto);
    }

    public boolean UpdateProduto(List<Produto> lAgrPro, Produto produto, List<Produto> lProduto, List<Arquivo> lArquivo) throws ErroSistemaException {
        return this.produtoDAO.UpdateProduto(lAgrPro, produto, lProduto, lArquivo);
    }

    public Produto descricaoProduto(String codigo) throws ErroSistemaException {
        return this.produtoDAO.descricao(codigo);
    }

    public List<Produto> buscarRelacionados(Produto produto) throws ErroSistemaException {
        return this.produtoDAO.listarRelacionados(produto);
    }

    public List<Produto> listarProdutosAgr(List<Filtro> lFiltros) {
        return this.produtoDAO.listarAgrPro(lFiltros);
    }

    public boolean InserirRelacionamentoNew(Produto produto, List<Produto> lProduto, List<Arquivo> lArquivo, List<Categoria> lCatalogo) throws ErroSistemaException {
        return this.produtoDAO.inserirRelacionamentoNew(produto, lProduto, lArquivo, lCatalogo);
    }

    public boolean UpdateProdutoNew(List<Produto> lAgrPro, Produto produto, List<Produto> lProduto, List<Arquivo> lArquivo, List<Categoria> lCatalogo) throws ErroSistemaException {
        return this.produtoDAO.UpdateProdutoNew(lAgrPro, produto, lProduto, lArquivo, lCatalogo);
    }

    public boolean gerarFichaProduto(String codigo) throws ErroSistemaException {
        return this.produtoRel.gerarFichaProduto(codigo);
    }

    public void relacaoProdutosCadastrado(int idUsuario) throws ErroSistemaException {
        this.produtoRel.relacaoProdutosCadastrado(idUsuario);
    }

    public void relacaoProdutosNaoCadastrado(int idUsuario) throws ErroSistemaException {
        this.produtoRel.relacaoProdutosNaoCadastrado(idUsuario);
    }

    public List<Produto> buscarPendecias() throws ErroSistemaException {
        return this.produtoDAO.buscarPendecias();
    }
}
