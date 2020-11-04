/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.model.dao;

import br.com.martinello.matriz.bd.model.domain.Arquivo;
import br.com.martinello.matriz.bd.model.domain.Produto;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import br.com.martinello.matriz.util.filtro.Filtro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class RelacionamentoDAO {

    private PreparedStatement psSelect, psUpdate, psInsert, psDelete;
    private ResultSet rsDelete, rsUpdate, rsInserir, rsSelect;
    private List<Produto> lProduto;
    private List<Arquivo> lArquivo;
    private Produto produto;
    private ProdutoDAO produtoDAO;
    private Connection connection;
    private int resultado, min, hora, idCarCad;
    private String data, sHora, codPro;
    private boolean retorno;
    private String select, update, insert, delete;

    public List<Produto> SelectRelacionados(Produto produto) throws ErroSistemaException {
        lProduto = new LinkedList<>();
        select = "SELECT E075PRO.CODPRO,\n"
                + "       E075PRO.DESPRO,\n"
                + "       E075PRO.USU_AGRSIT,\n"
                + "       E075PRO.USU_CODCAT,\n"
                + "       E075PRO.USU_CODSCA,\n"
                + "       USU_T075CAT.USU_DESCAT,\n"
                + "       USU_T075CAS.USU_DESSCA\n"
                + "  FROM E075PRO\n"
                + "  LEFT JOIN USU_T075CAT\n"
                + "    ON E075PRO.USU_CODCAT = USU_T075CAT.USU_CODCAT\n"
                + "   AND E075PRO.CODEMP = USU_T075CAT.USU_CODEMP\n"
                + "  LEFT JOIN USU_T075CAS\n"
                + "    ON USU_T075CAS.USU_CODEMP = E075PRO.CODEMP\n"
                + "   AND USU_T075CAS.USU_CODCAT = E075PRO.USU_CODCAT\n"
                + "   AND USU_T075CAS.USU_CODSCA = E075PRO.USU_CODSCA\n" + getWhere(produto) + "AND TRIM(USU_AGRSIT) IS NOT NULL";
        try {
            System.out.println("" + select);
            psSelect = Conexao.getConnection().prepareStatement(select);
            rsSelect = psSelect.executeQuery();
            while (rsSelect.next()) {
                produto = new Produto();
                produto.setIdCategoria(rsSelect.getInt("USU_CODCAT"));
                produto.setIdSubCategoria(rsSelect.getInt("USU_CODSCA"));
                produto.setCategoria(rsSelect.getString("USU_DESCAT"));
                produto.setSubCategoria(rsSelect.getString("USU_DESSCA"));
                produto.setCodigo(rsSelect.getString("CODPRO"));
                produto.setDescricao(rsSelect.getString("DESPRO"));
                produto.setCodigoAgr(rsSelect.getString("USU_AGRSIT"));
                lProduto.add(produto);
            }
            psSelect.close();
            rsSelect.close();
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lProduto;
    }

    public String getWhere(Produto produto) {
        String where = "WHERE  0 = 0";
        where += ((!produto.getCodigo().trim().equalsIgnoreCase("")) ? " AND E075PRO.CODPRO = '" + produto.getCodigo() + "'" : "");
        where += ((!produto.getCodigoAgr().trim().equalsIgnoreCase("")) ? " AND USU_AGRSIT = '" + produto.getCodigoAgr() + "'" : "");
        where += ((produto.getDescricao() != null) ? " AND UPPER(E075PRO.DESPRO) LIKE '" + produto.getDescricao().toUpperCase() + "'" : "");
        return where;

    }

    public String getWhereFiltro(List<Filtro> lFiltros) {
        String where = "";
        for (Filtro filtro : lFiltros) {
            if (filtro.getCampo().equalsIgnoreCase("descricao")) {
                where += ((filtro.getValor() != null) ? " AND UPPER(E075PRO.DESPRO) LIKE UPPER('" + filtro.getValor() + "')" : "");
            }
            if (filtro.getCampo().equalsIgnoreCase("codigo")) {
                where += ((filtro.getValor() != null) ? " AND E075PRO.CODPRO = '" + filtro.getValor() + "'" : "");
            }
            if (filtro.getCampo().equalsIgnoreCase("idCategoria")) {
                where += ((filtro.getValor() != null) ? " AND E075PRO.USU_CODCAT = '" + filtro.getValor() + "'" : "");
            }
            if (filtro.getCampo().equalsIgnoreCase("idSubCategoria")) {
                where += ((filtro.getValor() != null) ? " AND E075PRO.USU_CODSCA = '" + filtro.getValor() + "'" : "");
            }
        }
        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;

    }

    public List<Produto> listarProduto(List<Filtro> lFiltros) throws ErroSistemaException {
        lProduto = new LinkedList<>();
        select = "SELECT CODPRO, DESPRO, USU_CODCAT, USU_CODSCA, USU_AGRSIT FROM E075PRO" + getWhereFiltro(lFiltros) + " AND TRIM(USU_AGRSIT) IS NULL ";
        try {
            psSelect = Conexao.getConnection().prepareStatement(select);
            rsSelect = psSelect.executeQuery();
            while (rsSelect.next()) {
                produto = new Produto();
                produto.setIdCategoria(rsSelect.getInt("USU_CODCAT"));
                produto.setIdSubCategoria(rsSelect.getInt("USU_CODSCA"));
                produto.setCodigoAgr(rsSelect.getString("USU_AGRSIT"));
                produto.setCodigo(rsSelect.getString("CODPRO"));
                produto.setDescricao(rsSelect.getString("DESPRO"));
                lProduto.add(produto);
            }
            psSelect.close();
            rsSelect.close();
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lProduto;
    }

    public boolean agrupar(Produto produto) throws ErroSistemaException {
        try {
            retorno = false;
            select = "SELECT E075PRO.CODPRO,\n"
                    + "       E075PRO.DESPRO,\n"
                    + "       E075PRO.USU_AGRSIT,\n"
                    + "       E075PRO.USU_CODCAT,\n"
                    + "       E075PRO.USU_CODSCA,\n"
                    + "       USU_T075CAT.USU_DESCAT,\n"
                    + "       USU_T075CAS.USU_DESSCA\n"
                    + "  FROM E075PRO\n"
                    + "  LEFT JOIN USU_T075CAT\n"
                    + "    ON E075PRO.USU_CODCAT = USU_T075CAT.USU_CODCAT\n"
                    + "   AND E075PRO.CODEMP = USU_T075CAT.USU_CODEMP\n"
                    + "  LEFT JOIN USU_T075CAS\n"
                    + "    ON USU_T075CAS.USU_CODEMP = E075PRO.CODEMP\n"
                    + "   AND USU_T075CAS.USU_CODCAT = E075PRO.USU_CODCAT\n"
                    + "   AND USU_T075CAS.USU_CODSCA = E075PRO.USU_CODSCA\n"
                    + "   WHERE E075PRO.CODPRO = '" + produto.getCodigoAgr() + "'\n";

            update = "UPDATE E075PRO SET \n"
                    + " USU_AGRSIT = '" + produto.getCodigoAgr() + "',\n"
                    + " USU_CODCAT = ?, \n"
                    + " USU_CODSCA = ? \n"
                    + " WHERE CODPRO = '" + produto.getCodigo() + "' AND CODEMP = 1";

            connection = Conexao.getConnection();
            connection.setAutoCommit(false);
            psSelect = connection.prepareStatement(select);
            rsSelect = psSelect.executeQuery();
            if (rsSelect.next()) {
                psUpdate = connection.prepareStatement(update);
                psUpdate.setInt(1, rsSelect.getInt("USU_CODCAT"));
                psUpdate.setInt(2, rsSelect.getInt("USU_CODSCA"));
                psUpdate.execute();
                produto.setIdCategoria(rsSelect.getInt("USU_CODCAT"));
                produto.setIdSubCategoria(rsSelect.getInt("USU_CODSCA"));
                produto.setDescLonga("");
                produto.setSituacao("I");
                produto.setVisivel("N");
                if (produto.getCodigo().equals(produto.getCodigoAgr())) {

                } else {
                    produtoDAO = new ProdutoDAO();
                    lProduto = new LinkedList<>();
                    lArquivo = new LinkedList<>();
                    lProduto = produtoDAO.listarCaracteristicaProduto(produto.getCodigoAgr());
                    if (lProduto.size() > 0) {
                        produtoDAO.InserirProduto(produto, lProduto, "CadAgrPro", connection, lArquivo);
                        retorno = true;
                    }

                }
                retorno = true;
                psUpdate.close();
              
            }
            psSelect.close();
            rsSelect.close();
        } catch (ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
            if (retorno == false) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new ErroSistemaException(ex.getMessage(), ex.getCause());
                }
            } else {
                try {
                    connection.commit();
                } catch (SQLException ex) {
                    throw new ErroSistemaException(ex.getMessage(), ex.getCause());
                }
            }
        }
        return retorno;

    }

}
