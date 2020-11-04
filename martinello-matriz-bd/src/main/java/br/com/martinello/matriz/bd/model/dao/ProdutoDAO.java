/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.model.dao;

import br.com.martinello.matriz.bd.model.domain.Arquivo;
import br.com.martinello.matriz.bd.model.domain.Categoria;
import br.com.martinello.matriz.bd.model.domain.Parametro;
import br.com.martinello.matriz.bd.model.domain.Produto;
import br.com.martinello.matriz.bd.transients.ProdutoImagemTray;
import br.com.martinello.matriz.bd.transients.ProdutoTray;
import br.com.martinello.matriz.util.Utilitarios;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import br.com.martinello.matriz.util.filtro.Filtro;
import com.google.common.io.ByteSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import jcifs.smb.SmbFile;

/**
 *
 * @author luiz.almeida
 */
public class ProdutoDAO {

    private PreparedStatement psSelectProduto, psUpdateProduto, psInserirProduto, psSelectId, psDesPro, psSelect, psArquivos, psCatalogo;
    private ResultSet rsSelectProduto, rsUpdateProduto, rsInserirProduto, rsSelectId, rsDesPro, rsSelect;
    private List<Produto> lProduto;
    private List<Parametro> lParametro;
    private Produto produto;
    private Parametro parametro;
    private ParametrosDAO parametroDAO;
    private Connection connection;
    private int resultado, min, hora, idCarCad;
    private String data, sHora, codPro, urlOrigem, urlDestino;
    private boolean retorno;
    private String selectProduto, updateProduto, inserirProduto, selectId;
    private Path origem;
    private Path destino;

    public ProdutoDAO() {

    }

    public List<Produto> pesquisar(List<Filtro> lFiltros) throws ErroSistemaException {
        lProduto = new LinkedList<>();
        selectProduto = "SELECT CODPRO, DESPRO, USU_CODCAT, USU_CODSCA FROM E075PRO" + getWhere(lFiltros) + " AND (TRIM(USU_CODCAT) IS NULL OR USU_CODCAT = 0) ";
        try {
            psSelectProduto = Conexao.getConnection().prepareStatement(selectProduto);
            rsSelectProduto = psSelectProduto.executeQuery();
            while (rsSelectProduto.next()) {
                produto = new Produto();
                produto.setIdCategoria(rsSelectProduto.getInt("USU_CODCAT"));
                produto.setIdSubCategoria(rsSelectProduto.getInt("USU_CODSCA"));
                produto.setCodigo(rsSelectProduto.getString("CODPRO"));
                produto.setDescricao(rsSelectProduto.getString("DESPRO"));
                lProduto.add(produto);
            }
            psSelectProduto.close();
            rsSelectProduto.close();
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lProduto;
    }

    public List<Produto> listarProdutos(List<Filtro> lFiltros) throws ErroSistemaException {
        try {
            lProduto = new LinkedList<>();
            selectProduto = "SELECT E075PRO.CODPRO,\n"
                    + "       E075PRO.DESPRO,\n"
                    + "       E075PRO.USU_CODCAT,\n"
                    + "       E075PRO.USU_CODSCA,\n"
                    + "       USU_T075CAT.USU_DESCAT,\n"
                    + "       USU_T075CAS.USU_DESSCA\n"
                    + "  FROM E075PRO\n"
                    + " INNER JOIN USU_T075CAT\n"
                    + "    ON E075PRO.USU_CODCAT = USU_T075CAT.USU_CODCAT\n"
                    + "   AND E075PRO.CODEMP = USU_T075CAT.USU_CODEMP\n"
                    + " INNER JOIN USU_T075CAS\n"
                    + "    ON USU_T075CAS.USU_CODEMP = E075PRO.CODEMP\n"
                    + "   AND USU_T075CAS.USU_CODCAT = E075PRO.USU_CODCAT\n"
                    + "   AND USU_T075CAS.USU_CODSCA = E075PRO.USU_CODSCA\n" + getWhere(lFiltros);
            psSelectProduto = Conexao.getConnection().prepareStatement(selectProduto);
            System.out.println("" + selectProduto);

            rsSelectProduto = psSelectProduto.executeQuery();
            while (rsSelectProduto.next()) {
                produto = new Produto();
                produto.setIdCategoria(rsSelectProduto.getInt("USU_CODCAT"));
                produto.setIdSubCategoria(rsSelectProduto.getInt("USU_CODSCA"));
                produto.setCategoria(rsSelectProduto.getString("USU_DESCAT"));
                produto.setSubCategoria(rsSelectProduto.getString("USU_DESSCA"));
                produto.setCodigo(rsSelectProduto.getString("CODPRO"));
                produto.setDescricao(rsSelectProduto.getString("DESPRO"));

                lProduto.add(produto);
            }
            psSelectProduto.close();
            rsSelectProduto.close();
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {

        }
        return lProduto;
    }

    public List<Produto> listarCaracteristicaProduto(String codigo) throws ErroSistemaException {
        try {
            lProduto = new LinkedList<>();
            selectProduto = "SELECT USU_T075CAD.USU_CODEMP,\n"
                    + "       USU_T075CAP.USU_CODCAI,\n"
                    + "       USU_T075CAP.USU_DESCAI,\n"
                    + "       USU_T075CAP.USU_REPITE,\n"
                    + "       USU_T075CAD.USU_CODCAT,\n"
                    + "       USU_T075CAD.USU_CODCAT,\n"
                    + "       USU_T075CAT.USU_DESCAT,\n"
                    + "       USU_T075CAD.USU_CODSCA,\n"
                    + "       USU_T075CAS.USU_DESSCA,\n"
                    + "       USU_T075CAD.USU_CODCAR,\n"
                    + "       USU_T075CAR.USU_DESCAR,\n"
                    + "       R999USU.NOMUSU,\n"
                    + "       USU_T075CAD.USU_DATGER,\n"
                    + "       USU_T075CAD.USU_HORGER,\n"
                    + "       USU_T075CAD.USU_USUALT,\n"
                    + "       USU_T075CAD.USU_DATALT,\n"
                    + "       USU_T075CAD.USU_HORALT,\n"
                    + "       USU_T075CAD.USU_OBSALT,\n"
                    + "       USU_T075CAD.USU_CODCAD,\n"
                    + "       USU_T075CAD.USU_DESCAD,\n"
                    + "       USU_T075CAD.USU_SITCAD,\n"
                    + "       USU_T075CAD.USU_CODPRO,\n"
                    + "       E075PRO.DESPRO,\n"
                    + "       USU_T075CAD.USU_VISSIT\n"
                    + "  FROM USU_T075CAD\n"
                    + " INNER JOIN R999USU\n"
                    + "    ON R999USU.CODUSU = USU_T075CAD.USU_USUGER\n"
                    + " INNER JOIN USU_T075CAR\n"
                    + "    ON USU_T075CAD.USU_CODCAR = USU_T075CAR.USU_CODCAR\n"
                    + "   AND USU_T075CAD.USU_CODCAT = USU_T075CAR.USU_CODCAT\n"
                    + "   AND USU_T075CAD.USU_CODSCA = USU_T075CAR.USU_CODSCA\n"
                    + " INNER JOIN USU_T075CAT\n"
                    + "    ON USU_T075CAD.USU_CODCAT = USU_T075CAT.USU_CODCAT\n"
                    + " INNER JOIN USU_T075CAS\n"
                    + "    ON USU_T075CAD.USU_CODCAR = USU_T075CAR.USU_CODCAR\n"
                    + "   AND USU_T075CAD.USU_CODCAT = USU_T075CAS.USU_CODCAT\n"
                    + "   AND USU_T075CAD.USU_CODSCA = USU_T075CAS.USU_CODSCA\n"
                    + " INNER JOIN E075PRO\n"
                    + "    ON E075PRO.USU_CODCAT = USU_T075CAD.USU_CODCAT\n"
                    + "   AND E075PRO.USU_CODSCA = USU_T075CAD.USU_CODSCA\n"
                    + "   AND E075PRO.CODPRO = USU_T075CAD.USU_CODPRO \n  "
                    + " INNER JOIN USU_T075CAP\n"
                    + "    ON USU_T075CAP.USU_CODCAT = USU_T075CAD.USU_CODCAT\n"
                    + "   AND USU_T075CAP.USU_CODSCA = USU_T075CAD.USU_CODSCA\n"
                    + "   AND USU_T075CAP.USU_CODCAR = USU_T075CAD.USU_CODCAR\n"
                    + "   AND USU_T075CAP.USU_CODCAI = USU_T075CAD.USU_CODCAI\n"
                    + " WHERE E075PRO.CODPRO = '" + codigo + "'" + " ORDER BY USU_SEQCAR, USU_SEQCAI";
            psSelectProduto = Conexao.getConnection().prepareStatement(selectProduto);
            rsSelectProduto = psSelectProduto.executeQuery();
            while (rsSelectProduto.next()) {
                produto = new Produto();
                produto.setIdCategoria(rsSelectProduto.getInt("USU_CODCAT"));
                produto.setIdSubCategoria(rsSelectProduto.getInt("USU_CODSCA"));
                produto.setIdCaracteristica(rsSelectProduto.getInt("USU_CODCAR"));
                produto.setIdCaracteristicaItem(rsSelectProduto.getInt("USU_CODCAI"));
                produto.setIdDescCaractItem(rsSelectProduto.getInt("USU_CODCAD"));
                produto.setCategoria(rsSelectProduto.getString("USU_DESCAT"));
                produto.setSubCategoria(rsSelectProduto.getString("USU_DESSCA"));
                produto.setCaracteristica(rsSelectProduto.getString("USU_DESCAR"));
                produto.setCaracteristicaItem(rsSelectProduto.getString("USU_DESCAI"));
                produto.setDescCaracteristica(rsSelectProduto.getString("USU_DESCAD"));
                produto.setSituacao(rsSelectProduto.getString("USU_SITCAD"));
                produto.setVisivel(rsSelectProduto.getString("USU_VISSIT"));
                produto.setCodigo(rsSelectProduto.getString("USU_CODPRO"));
                produto.setReplicavel(rsSelectProduto.getString("USU_REPITE"));
                produto.setDescricao(rsSelectProduto.getString("DESPRO"));
                lProduto.add(produto);
            }

            psSelectProduto.close();
            rsSelectProduto.close();
        } catch (SQLException | ErroSistemaException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lProduto;
    }

    public List<Produto> listarCaracteristicas(Produto produto) throws ErroSistemaException {
        selectProduto = "SELECT USU_T075CAP.USU_CODEMP,\n"
                + "       USU_T075CAP.USU_CODCAI,\n"
                + "       USU_T075CAP.USU_DESCAI,\n"
                + "       USU_T075CAP.USU_CODCAT,\n"
                + "       USU_T075CAT.USU_DESCAT,\n"
                + "       USU_T075CAP.USU_CODSCA,\n"
                + "       USU_T075CAS.USU_DESSCA,\n"
                + "       USU_T075CAP.USU_CODCAR,\n"
                + "       USU_T075CAR.USU_DESCAR,\n"
                + "       USU_T075CAP.USU_SEQCAI,\n"
                + "       USU_T075CAP.USU_SITCAI,\n"
                + "       USU_T075CAP.USU_OBSCAI,\n"
                + "       USU_T075CAP.USU_VISSIT,\n"
                + "       USU_T075CAP.USU_USUGER,\n"
                + "       USU_T075CAP.USU_DATGER,\n"
                + "       USU_T075CAP.USU_HORGER,\n"
                + "       USU_T075CAP.USU_USUALT,\n"
                + "       USU_T075CAP.USU_DATALT,\n"
                + "       USU_T075CAP.USU_HORALT,\n"
                + "       USU_T075CAP.USU_OBSALT\n"
                + "  FROM USU_T075CAP\n"
                + " INNER JOIN USU_T075CAR\n"
                + "    ON USU_T075CAP.USU_CODCAR = USU_T075CAR.USU_CODCAR\n"
                + "   AND USU_T075CAP.USU_CODSCA = USU_T075CAR.USU_CODSCA\n"
                + "   AND USU_T075CAP.USU_CODCAT = USU_T075CAR.USU_CODCAT\n"
                + " INNER JOIN USU_T075CAT\n"
                + "    ON USU_T075CAP.USU_CODCAT = USU_T075CAT.USU_CODCAT\n"
                + " INNER JOIN USU_T075CAS\n"
                + "    ON USU_T075CAP.USU_CODSCA = USU_T075CAS.USU_CODSCA\n"
                + "   AND USU_T075CAP.USU_CODCAT = USU_T075CAS.USU_CODCAT\n"
                + " WHERE USU_T075CAP.USU_CODCAT = " + produto.getIdCategoria() + "\n"
                + "   AND USU_T075CAP.USU_CODSCA = " + produto.getIdSubCategoria() + " ORDER BY USU_SEQCAR, USU_SEQCAI";
        try {
            lProduto = new LinkedList<>();
            psSelectProduto = Conexao.getConnection().prepareStatement(selectProduto);
            rsSelectProduto = psSelectProduto.executeQuery();
            while (rsSelectProduto.next()) {
                produto = new Produto();
                produto.setIdCategoria(rsSelectProduto.getInt("USU_CODCAT"));
                produto.setIdSubCategoria(rsSelectProduto.getInt("USU_CODSCA"));
                produto.setIdCaracteristica(rsSelectProduto.getInt("USU_CODCAR"));
                produto.setIdCaracteristicaItem(rsSelectProduto.getInt("USU_CODCAI"));
                produto.setCategoria(rsSelectProduto.getString("USU_DESCAT"));
                produto.setSubCategoria(rsSelectProduto.getString("USU_DESSCA"));
                produto.setCaracteristica(rsSelectProduto.getString("USU_DESCAR"));
                produto.setCaracteristicaItem(rsSelectProduto.getString("USU_DESCAI"));
                produto.setVisivel("S");
                produto.setSituacao("A");
                produto.setCodigo(produto.getCodigo());
                produto.setDescricao(produto.getDescricao());
                lProduto.add(produto);
            }
            psSelectProduto.close();
            rsSelectProduto.close();
        } catch (SQLException | ErroSistemaException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lProduto;
    }

    public Produto buscarProduto(String codigo) throws ErroSistemaException {
        try {
            selectProduto = "SELECT E075PRO.CODPRO,\n"
                    + "       E075PRO.DESPRO,\n"
                    + "       E075PRO.USU_CODCAT,\n"
                    + "       E075PRO.USU_CODSCA,\n"
                    + "       USU_T075CAT.USU_DESCAT,\n"
                    + "       USU_T075CAS.USU_DESSCA\n"
                    + "  FROM E075PRO\n"
                    + " INNER JOIN USU_T075CAT\n"
                    + "    ON E075PRO.USU_CODCAT = USU_T075CAT.USU_CODCAT\n"
                    + "   AND E075PRO.CODEMP = USU_T075CAT.USU_CODEMP\n"
                    + " INNER JOIN USU_T075CAS\n"
                    + "    ON USU_T075CAS.USU_CODEMP = E075PRO.CODEMP\n"
                    + "   AND USU_T075CAS.USU_CODCAT = E075PRO.USU_CODCAT\n"
                    + "   AND USU_T075CAS.USU_CODSCA = E075PRO.USU_CODSCA\n"
                    + " WHERE E075PRO.CODPRO = '" + codigo + "'";
            psSelectProduto = Conexao.getConnection().prepareStatement(selectProduto);
            rsSelectProduto = psSelectProduto.executeQuery();
            while (rsSelectProduto.next()) {
                produto = new Produto();
                produto.setIdCategoria(rsSelectProduto.getInt("USU_CODCAT"));
                produto.setIdSubCategoria(rsSelectProduto.getInt("USU_CODSCA"));
                produto.setCategoria(rsSelectProduto.getString("USU_DESCAT"));
                produto.setSubCategoria(rsSelectProduto.getString("USU_DESSCA"));
                produto.setCodigo(rsSelectProduto.getString("CODPRO"));
                produto.setDescricao(rsSelectProduto.getString("DESPRO"));
            }
            psSelectProduto.close();
            rsSelectProduto.close();
        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return produto;
    }

    public boolean inserirRelacionamento(Produto produto, List<Produto> lProduto, List<Arquivo> lArquivo) throws ErroSistemaException {
        retorno = false;
        selectProduto = "SELECT CODPRO, DESPRO, USU_CODCAT, USU_CODSCA FROM E075PRO WHERE CODPRO = '" + produto.getCodigo() + "' AND (TRIM(USU_CODCAT) IS NOT NULL AND USU_CODCAT > 0)";

        updateProduto = "UPDATE E075PRO SET "
                + "             USU_CODCAT = ?,\n"
                + "             USU_CODSCA = ?\n"
                + "       WHERE CODEMP = ?\n"
                + "         AND CODPRO = ?";
        connection = Conexao.getConnection();
        try {
            connection.setAutoCommit(false);
            psSelectProduto = connection.prepareStatement(selectProduto);
            rsSelectProduto = psSelectProduto.executeQuery();
            while (rsSelectProduto.next()) {
                throw new ErroSistemaException("Produto já relacionado Verifique!");
            }
            psSelectProduto.close();
            rsSelectProduto.close();
            psUpdateProduto = connection.prepareStatement(updateProduto);
            psUpdateProduto.setInt(1, produto.getIdCategoria());
            psUpdateProduto.setInt(2, produto.getIdSubCategoria());
            psUpdateProduto.setInt(3, 1);
            psUpdateProduto.setString(4, produto.getCodigo());
            psUpdateProduto.executeUpdate();
            psUpdateProduto.close();
            retorno = InserirProduto(produto, lProduto, "CadPro", connection, lArquivo);
        } catch (SQLException | ErroSistemaException ex) {
            retorno = false;
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
            try {
                if (retorno == false) {
                    connection.rollback();
                } else {
                    connection.commit();
                }
            } catch (SQLException ex) {
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }

        }
        return retorno;
    }

    public boolean InserirProduto(Produto produto, List<Produto> lProduto, String origem, Connection connection, List<Arquivo> lArquivo) throws ErroSistemaException {
        try {
            variaveis();
            inserirProduto = "INSERT INTO USU_T075CAD\n"
                    + "  (USU_CODEMP,\n"
                    + "   USU_CODCAT,\n"
                    + "   USU_CODSCA,\n"
                    + "   USU_CODCAR,\n"
                    + "   USU_CODCAI,\n"
                    + "   USU_CODCAD,\n"
                    + "   USU_CODPRO,\n"
                    + "   USU_DESCAD,\n"
                    + "   USU_SEQCAD,\n"
                    + "   USU_SITCAD,\n"
                    + "   USU_VISSIT,\n"
                    + "   USU_USUGER,\n"
                    + "   USU_DATGER,\n"
                    + "   USU_HORGER)\n"
                    + "   VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            selectId = "SELECT NVL(MAX(USU_T075CAD.USU_CODCAD), 0) USU_CODCAD \n"
                    + "   FROM USU_T075CAD WHERE USU_CODEMP = 1 \n "
                    + "    AND USU_T075CAD.USU_CODCAT = " + produto.getIdCategoria() + "\n"
                    + "    AND USU_T075CAD.USU_CODSCA = " + produto.getIdSubCategoria() + "\n"
                    + "    AND USU_CODPRO = '" + produto.getCodigo() + "'";
            psSelectId = connection.prepareStatement(selectId);
            rsSelectId = psSelectId.executeQuery();
            if (rsSelectId.next()) {
                idCarCad = rsSelectId.getInt("USU_CODCAD");
            }
            psSelectId.close();
            rsSelectId.close();
            for (Produto Pro : lProduto) {
                idCarCad++;
                psInserirProduto = connection.prepareStatement(inserirProduto);
                psInserirProduto.setInt(1, 1);
                psInserirProduto.setInt(2, produto.getIdCategoria());
                psInserirProduto.setInt(3, produto.getIdSubCategoria());
                psInserirProduto.setInt(4, Pro.getIdCaracteristica());
                psInserirProduto.setInt(5, Pro.getIdCaracteristicaItem());
                psInserirProduto.setInt(6, idCarCad);
                psInserirProduto.setString(7, produto.getCodigo());
                if (origem.equalsIgnoreCase("CadAgrPro") && Pro.getReplicavel().equalsIgnoreCase("N")) {
                    psInserirProduto.setString(8, "");
                } else {
                    psInserirProduto.setString(8, Pro.getDescCaracteristica());
                }

                psInserirProduto.setInt(9, idCarCad);
                psInserirProduto.setString(10, Pro.getSituacao().toUpperCase());
                psInserirProduto.setString(11, Pro.getVisivel().toUpperCase());
                psInserirProduto.setLong(12, produto.getIdUsuario());
                psInserirProduto.setString(13, data);
                psInserirProduto.setInt(14, Utilitarios.getHoraNumero(sHora));
                psInserirProduto.executeUpdate();
                retorno = true;
            }
            psInserirProduto.close();
            if (origem.equalsIgnoreCase("CadPro") | origem.equalsIgnoreCase("CadAgrPro")) {
                retorno = insertDescricao(produto, connection);
            }
            if (origem.equalsIgnoreCase("CadPro") && lArquivo.size() > 0) {
                retorno = insertAnexo(produto, lArquivo, connection);
            }

        } catch (ErroSistemaException | SQLException ex) {
            retorno = false;
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return retorno;
    }

    public boolean UpdateProduto(List<Produto> lAgrPro, Produto produto, List<Produto> lProduto, List<Arquivo> lArquivo) throws ErroSistemaException {
        retorno = false;
        try {
            updateProduto = "UPDATE USU_T075CAD SET \n"
                    + "             USU_DESCAD = ?,\n"
                    + "             USU_SITCAD = ?,\n"
                    + "             USU_SEQCAD = ?,\n"
                    + "             USU_VISSIT = ?,\n"
                    + "             USU_USUALT = ?,\n"
                    + "             USU_DATALT = ?,\n"
                    + "             USU_HORALT = ?\n"
                    + "       WHERE USU_CODEMP = ?"
                    + "         AND USU_CODCAT = ?"
                    + "         AND USU_CODSCA = ?"
                    + "         AND USU_CODCAR = ?"
                    + "         AND USU_CODCAI = ?"
                    + "         AND USU_CODPRO = ?"
                    + "         AND USU_CODCAD = ? ";
            connection = Conexao.getConnection();
            connection.setAutoCommit(false);
            variaveis();
            for (Produto AgrPro : lAgrPro) {
                for (Produto produtoUp : lProduto) {
                    if ((AgrPro.getCodigo().equalsIgnoreCase(produto.getCodigo())
                            || (!AgrPro.getCodigo().equalsIgnoreCase(produto.getCodigo()) && (produtoUp.getReplicavel().equalsIgnoreCase("S"))))) {
                        psUpdateProduto = connection.prepareStatement(updateProduto);

                        psUpdateProduto.setString(1, produtoUp.getDescCaracteristica());
                        psUpdateProduto.setString(2, produtoUp.getSituacao().toUpperCase());
                        psUpdateProduto.setInt(3, produtoUp.getSequencia());
                        psUpdateProduto.setString(4, produtoUp.getVisivel().toUpperCase());
                        psUpdateProduto.setLong(5, produto.getIdUsuario());
                        psUpdateProduto.setString(6, data);
                        psUpdateProduto.setInt(7, Utilitarios.getHoraNumero(sHora));
                        psUpdateProduto.setInt(8, 1);
                        psUpdateProduto.setInt(9, produto.getIdCategoria());
                        psUpdateProduto.setInt(10, produto.getIdSubCategoria());
                        psUpdateProduto.setInt(11, produtoUp.getIdCaracteristica());
                        psUpdateProduto.setInt(12, produtoUp.getIdCaracteristicaItem());
                        psUpdateProduto.setString(13, produtoUp.getCodigo());
                        psUpdateProduto.setInt(14, produtoUp.getIdDescCaractItem());
                        psUpdateProduto.execute();
                    } else {
                    }
                }
                retorno = true;
                if (produto.getCodigo().equalsIgnoreCase(AgrPro.getCodigo())) {
                    retorno = insertDescricao(produto, connection);
                }
                if (AgrPro.getCodigo().equalsIgnoreCase(produto.getCodigo()) && lArquivo.size() > 0) {
                    retorno = insertAnexo(produto, lArquivo, connection);
                    if (retorno == false) {
                        throw new ErroSistemaException("Erro ao Salvar Anexo.");
                    }
                }
            }

        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
            if (retorno == false) {
                try {
                    psUpdateProduto.close();
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new ErroSistemaException(ex.getMessage(), ex.getCause());
                }
            } else {
                try {
                    psUpdateProduto.close();
                    connection.commit();
                } catch (SQLException ex) {
                    throw new ErroSistemaException(ex.getMessage(), ex.getCause());
                }
            }
        }
        return retorno;

    }

    private boolean insertDescricao(Produto produto, Connection connection) throws ErroSistemaException {
        try {
            String insertDesc = "INSERT INTO USU_T075DPR "
                    + "            (USU_CODEMP,"
                    + "             USU_CODPRO,"
                    + "             USU_CODCAT,"
                    + "             USU_CODSCA,"
                    + "             USU_DESPRO,"
                    + "             USU_SITDPR,"
                    + "             USU_VISSIT,"
                    + "             USU_OBSDPR,"
                    + "             USU_CODECO,"
                    + "             USU_USUGER,"
                    + "             USU_DATGER,"
                    + "             USU_HORGER)"
                    + "             VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String updateDesc = "UPDATE USU_T075DPR SET\n"
                    + "               USU_DESPRO = ?,\n"
                    + "               USU_SITDPR = ?,\n"
                    + "               USU_VISSIT = ?,\n"
                    + "               USU_OBSDPR = ?,\n"
                    + "               USU_USUALT = ?,\n"
                    + "               USU_DATALT = ?,\n"
                    + "               USU_HORALT = ?\n"
                    + "         WHERE USU_CODEMP = ?\n"
                    + "           AND USU_CODPRO = ?";

            selectProduto = "SELECT *FROM USU_T075DPR WHERE USU_CODPRO = " + produto.getCodigo() + " AND USU_CODEMP = 1";
            variaveis();
            psSelectProduto = connection.prepareStatement(selectProduto);
            rsSelectProduto = psSelectProduto.executeQuery();
            if (rsSelectProduto.next()) {
                psDesPro = connection.prepareStatement(updateDesc);
                psDesPro.setString(1, produto.getDescLonga());
                psDesPro.setString(2, produto.getSituacao().toUpperCase());
                psDesPro.setString(3, produto.getVisivel().toUpperCase());
                psDesPro.setString(4, "Alterado pelo Usuário");
                psDesPro.setLong(5, produto.getIdUsuario());
                psDesPro.setString(6, data);
                psDesPro.setInt(7, Utilitarios.getHoraNumero(sHora));
                psDesPro.setInt(8, 1);
                psDesPro.setString(9, produto.getCodigo());
                psDesPro.executeUpdate();
            } else {
                psDesPro = connection.prepareStatement(insertDesc);
                psDesPro.setInt(1, 1);
                psDesPro.setString(2, produto.getCodigo());
                psDesPro.setInt(3, produto.getIdCategoria());
                psDesPro.setInt(4, produto.getIdSubCategoria());
                psDesPro.setString(5, produto.getDescLonga());
                psDesPro.setString(6, produto.getSituacao().toUpperCase());
                psDesPro.setString(7, produto.getVisivel().toUpperCase());
                psDesPro.setString(8, "Inserido pelo Usuário");
                psDesPro.setInt(9, 0);
                psDesPro.setLong(10, produto.getIdUsuario());
                psDesPro.setString(11, data);
                psDesPro.setInt(12, Utilitarios.getHoraNumero(sHora));
                psDesPro.executeUpdate();
            }
            psSelectProduto.close();
            rsSelectProduto.close();
            retorno = true;
            psDesPro.close();
        } catch (SQLException ex) {
            retorno = false;
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return retorno;
    }

    public Produto descricao(String codigo) throws ErroSistemaException {
        try {
            produto = new Produto();
            selectProduto = "SELECT USU_CODCAT, USU_CODSCA, USU_SITDPR, USU_VISSIT, USU_DESPRO FROM USU_T075DPR WHERE USU_CODPRO = " + codigo + "";
            psDesPro = Conexao.getConnection().prepareStatement(selectProduto);
            rsDesPro = psDesPro.executeQuery();
            if (rsDesPro.next()) {
                produto.setDescLonga(rsDesPro.getString("USU_DESPRO"));
                produto.setIdCategoria(rsDesPro.getInt("USU_CODCAT"));
                produto.setIdSubCategoria(rsDesPro.getInt("USU_CODSCA"));
                produto.setCodigo(codigo);
                produto.setSituacao(rsDesPro.getString("USU_SITDPR"));
                produto.setVisivel(rsDesPro.getString("USU_VISSIT"));
            }
            psDesPro.close();
            rsDesPro.close();
            return produto;
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public List<Produto> listarRelacionados(Produto produto) throws ErroSistemaException {
        try {
            lProduto = new LinkedList<>();
            selectProduto = "SELECT CODPRO\n"
                    + "  FROM E075PRO\n"
                    + " WHERE USU_AGRSIT =\n"
                    + "       (SELECT USU_AGRSIT FROM E075PRO WHERE CODPRO = '" + produto.getCodigo() + "' AND TRIM(USU_AGRSIT) IS NOT NULL)\n"
                    + "    OR (CODPRO = '" + produto.getCodigo() + "')\n"
                    + "   AND CODEMP = 1";
            psSelectProduto = Conexao.getConnection().prepareStatement(selectProduto);
            rsSelectProduto = psSelectProduto.executeQuery();
            while (rsSelectProduto.next()) {
                produto = new Produto();
                produto.setCodigo(rsSelectProduto.getString("CODPRO"));
                lProduto.add(produto);
            }

        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lProduto;
    }

    private boolean insertAnexo(Produto produto, List<Arquivo> lArquivo, Connection connection) throws ErroSistemaException {
        String selectArquivo = "SELECT USU_CODEMP, \n"
                + "             USU_CODPRO, \n"
                + "             USU_CODDER, \n"
                + "             USU_SEQARQ, \n"
                + "             USU_DESARQ, \n"
                + "             USU_ARQPRI, \n"
                + "             USU_VISSIT, \n"
                + "             USU_DESANT, \n"
                + "             USU_EXTARQ  \n"
                + "             FROM USU_T075ARQ \n "
                + "             WHERE USU_CODPRO = ?\n "
                + "             AND USU_SEQARQ = ?";
        String insertArquivo = "INSERT INTO USU_T075ARQ \n"
                + "             (USU_CODEMP, \n"
                + "             USU_CODPRO, \n"
                + "             USU_CODDER, \n"
                + "             USU_SEQARQ, \n"
                + "             USU_DESARQ, \n"
                + "             USU_ARQPRI, \n"
                + "             USU_VISSIT, \n"
                + "             USU_DESANT, \n"
                + "             USU_EXTARQ, \n"
                + "             USU_ORDARQ, \n"
                + "             USU_USUGER, \n"
                + "             USU_DATGER, \n"
                + "             USU_HORGER)\n"
                + "             VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String updateArquivo = "UPDATE USU_T075ARQ SET\n"
                + "             USU_DESARQ = ?, \n"
                + "             USU_ARQPRI = ?, \n"
                + "             USU_VISSIT = ?, \n"
                + "             USU_DESANT = ?, \n"
                + "             USU_EXTARQ = ?, \n"
                + "             USU_USUALT = ?, \n"
                + "             USU_DATALT = ?, \n"
                + "             USU_HORALT = ? \n"
                + "            WHERE USU_CODPRO = ? \n"
                + "              AND USU_CODEMP = ? \n"
                + "              AND USU_SEQARQ = ?";

        for (Arquivo arquivo : lArquivo) {
            try (PreparedStatement psSelectArq = Conexao.getConnection().prepareStatement(selectArquivo)) {

                psSelectArq.setString(1, produto.getCodigo());
                psSelectArq.setInt(2, arquivo.getSequencia());
                try (ResultSet rsSelectArq = psSelectArq.executeQuery();) {
                    if (rsSelectArq.next()) {
                        if (arquivo.getAlterado().equalsIgnoreCase("S")) {
                            psArquivos = connection.prepareStatement(updateArquivo);
                            psArquivos.setString(1, arquivo.getNovoNome());
                            psArquivos.setString(2, arquivo.getPrincipal());
                            psArquivos.setString(3, arquivo.getVisivel());
                            psArquivos.setString(4, arquivo.getNome());
                            psArquivos.setString(5, arquivo.getExtencao());
                            psArquivos.setLong(6, produto.getIdUsuario());
                            psArquivos.setString(7, data);
                            psArquivos.setInt(8, Utilitarios.getHoraNumero(sHora));
                            psArquivos.setString(9, arquivo.getProduto());
                            psArquivos.setInt(10, 1);
                            psArquivos.setInt(11, arquivo.getSequencia());
                            psArquivos.execute();
                            psArquivos.close();
                        }

                    } else {
                        psArquivos = connection.prepareStatement(insertArquivo);
                        psArquivos.setInt(1, 1);
                        psArquivos.setString(2, arquivo.getProduto());
                        psArquivos.setInt(3, 1);
                        psArquivos.setInt(4, arquivo.getSequencia());
                        psArquivos.setString(5, arquivo.getNovoNome());
                        psArquivos.setString(6, arquivo.getPrincipal());
                        psArquivos.setString(7, arquivo.getVisivel());
                        psArquivos.setString(8, arquivo.getNome());
                        psArquivos.setString(9, arquivo.getExtencao());
                        psArquivos.setInt(10, arquivo.getSequencia());
                        psArquivos.setLong(11, produto.getIdUsuario());
                        psArquivos.setString(12, data);
                        psArquivos.setInt(13, Utilitarios.getHoraNumero(sHora));
                        psArquivos.execute();
                        psArquivos.close();

                        parametroDAO = new ParametrosDAO();
                        parametro = new Parametro();
                        parametro.setParametro("");
                        parametro.setValor("");
                        parametro.setDescricao("");
                        lParametro = new LinkedList<>();
                        lParametro = parametroDAO.ListParametros(parametro);
                        for (Parametro parametro : lParametro) {
                            if (parametro.getParametro().equalsIgnoreCase("DIRETORIO ORIGEM FOTOS")) {
                                if (arquivo.getDiretorio().equals("")) {
                                    arquivo.setDiretorio(parametro.getValor());
                                    urlOrigem = arquivo.getDiretorio() + produto.getCategoria() + "\\" + produto.getSubCategoria() + "\\" + produto.getCodigo() + "\\" + arquivo.getNome();
                                } else {
                                    urlOrigem = arquivo.getDiretorio() + arquivo.getNome();
                                }
                            }
                            if (parametro.getParametro().equalsIgnoreCase("DIRETORIO DESTINO FOTOS")) {
                                arquivo.setDiretorioDestino(parametro.getValor());
                            }
                        }
                        urlDestino = arquivo.getDiretorioDestino() + produto.getCodigo();
                        Files.createDirectories(Paths.get(urlDestino));
                        Files.copy(Paths.get(urlOrigem), Paths.get(arquivo.getDiretorioDestino() + produto.getCodigo() + "\\" + arquivo.getNovoNome() + "." + arquivo.getExtencao()));
                    }
                }
            } catch (FileAlreadyExistsException ex) {
                //Nada Faz
            } catch (SQLException ex) {
                try {
                    retorno = false;
                    connection.rollback();
                    connection.close();
                    throw new ErroSistemaException(ex.getMessage(), ex.getCause());
                } catch (SQLException ex1) {
                    retorno = false;
                    throw new ErroSistemaException(ex.getMessage(), ex.getCause());
                }
            } catch (IOException ex) {
                retorno = false;
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }

        }
        return retorno;
    }

    public List<Produto> listarAgrPro(List<Filtro> lFiltros) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void variaveis() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Cuiaba"));
        hora = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        sHora = hora + ":" + min;
        data = Utilitarios.converteDataString(new Date(), "dd/MM/yyyy");
    }

    private String getWhere(List<Filtro> lFiltros) {
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

    public boolean UpdateProdutoNew(List<Produto> lAgrPro, Produto produto, List<Produto> lProduto, List<Arquivo> lArquivo, List<Categoria> lCatalogo) throws ErroSistemaException {
        retorno = false;
        try {
            updateProduto = "UPDATE USU_T075CAD SET \n"
                    + "             USU_DESCAD = ?,\n"
                    + "             USU_SITCAD = ?,\n"
                    + "             USU_SEQCAD = ?,\n"
                    + "             USU_VISSIT = ?,\n"
                    + "             USU_USUALT = ?,\n"
                    + "             USU_DATALT = ?,\n"
                    + "             USU_HORALT = ?\n"
                    + "       WHERE USU_CODEMP = ?"
                    + "         AND USU_CODCAT = ?"
                    + "         AND USU_CODSCA = ?"
                    + "         AND USU_CODCAR = ?"
                    + "         AND USU_CODCAI = ?"
                    + "         AND USU_CODPRO = ?"
                    + "         AND USU_CODCAD = ? ";
            connection = Conexao.getConnection();
            connection.setAutoCommit(false);
            variaveis();
            for (Produto AgrPro : lAgrPro) {
                for (Produto produtoUp : lProduto) {
                    if ((AgrPro.getCodigo().equalsIgnoreCase(produto.getCodigo())
                            || (!AgrPro.getCodigo().equalsIgnoreCase(produto.getCodigo()) && (produtoUp.getReplicavel().equalsIgnoreCase("S"))))) {
                        psUpdateProduto = connection.prepareStatement(updateProduto);

                        psUpdateProduto.setString(1, produtoUp.getDescCaracteristica());
                        psUpdateProduto.setString(2, produtoUp.getSituacao().toUpperCase());
                        psUpdateProduto.setInt(3, produtoUp.getSequencia());
                        psUpdateProduto.setString(4, produtoUp.getVisivel().toUpperCase());
                        psUpdateProduto.setLong(5, produto.getIdUsuario());
                        psUpdateProduto.setString(6, data);
                        psUpdateProduto.setInt(7, Utilitarios.getHoraNumero(sHora));
                        psUpdateProduto.setInt(8, 1);
                        psUpdateProduto.setInt(9, produto.getIdCategoria());
                        psUpdateProduto.setInt(10, produto.getIdSubCategoria());
                        psUpdateProduto.setInt(11, produtoUp.getIdCaracteristica());
                        psUpdateProduto.setInt(12, produtoUp.getIdCaracteristicaItem());
                        psUpdateProduto.setString(13, produtoUp.getCodigo());
                        psUpdateProduto.setInt(14, produtoUp.getIdDescCaractItem());
                        psUpdateProduto.execute();
                    } else {
                    }
                }
                retorno = true;
                if (produto.getCodigo().equalsIgnoreCase(AgrPro.getCodigo())) {
                    retorno = insertDescricao(produto, connection);
                }
                if (AgrPro.getCodigo().equalsIgnoreCase(produto.getCodigo()) && lArquivo.size() > 0) {
                    retorno = insertAnexo(produto, lArquivo, connection);
                    if (retorno == false) {
                        throw new ErroSistemaException("Erro ao Salvar Anexo.");
                    }
                }
                if (AgrPro.getCodigo().equalsIgnoreCase(produto.getCodigo()) && lCatalogo.size() > 0) {
                    insertCatalogo(produto, lCatalogo, connection);
                }
            }

        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
            if (retorno == false) {
                try {
                    psUpdateProduto.close();
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new ErroSistemaException(ex.getMessage(), ex.getCause());
                }
            } else {
                try {
                    psUpdateProduto.close();
                    connection.commit();
                } catch (SQLException ex) {
                    throw new ErroSistemaException(ex.getMessage(), ex.getCause());
                }
            }
        }
        return retorno;
    }

    public boolean inserirRelacionamentoNew(Produto produto, List<Produto> lProduto, List<Arquivo> lArquivo, List<Categoria> lCatalogo) throws ErroSistemaException {

        selectProduto = "SELECT CODPRO, DESPRO, USU_CODCAT, USU_CODSCA FROM E075PRO WHERE CODPRO = '" + produto.getCodigo() + "' AND (TRIM(USU_CODCAT) IS NOT NULL AND USU_CODCAT > 0)";

        updateProduto = "UPDATE E075PRO SET "
                + "             USU_CODCAT = ?,\n"
                + "             USU_CODSCA = ?\n"
                + "       WHERE CODEMP = ?\n"
                + "         AND CODPRO = ?";
        connection = Conexao.getConnection();
        try {
            connection.setAutoCommit(false);
            psSelectProduto = connection.prepareStatement(selectProduto);
            rsSelectProduto = psSelectProduto.executeQuery();
            while (rsSelectProduto.next()) {
                throw new ErroSistemaException("Produto já relacionado Verifique!");
            }
            psSelectProduto.close();
            rsSelectProduto.close();
            psUpdateProduto = connection.prepareStatement(updateProduto);
            psUpdateProduto.setInt(1, produto.getIdCategoria());
            psUpdateProduto.setInt(2, produto.getIdSubCategoria());
            psUpdateProduto.setInt(3, 1);
            psUpdateProduto.setString(4, produto.getCodigo());
            psUpdateProduto.executeUpdate();
            retorno = InserirProdutoNew(produto, lProduto, "CadPro", connection, lArquivo, lCatalogo);
        } catch (SQLException ex) {
            retorno = false;
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
            try {
                if (retorno == false) {
                    psUpdateProduto.close();
                    connection.rollback();
                } else {
                    psUpdateProduto.close();
                    connection.commit();
                }
            } catch (SQLException ex) {
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }

        }
        return retorno;
    }

    public boolean InserirProdutoNew(Produto produto, List<Produto> lProduto, String origem, Connection connection, List<Arquivo> lArquivo, List<Categoria> lCatalogo) throws ErroSistemaException {
        try {
            variaveis();
            inserirProduto = "INSERT INTO USU_T075CAD\n"
                    + "  (USU_CODEMP,\n"
                    + "   USU_CODCAT,\n"
                    + "   USU_CODSCA,\n"
                    + "   USU_CODCAR,\n"
                    + "   USU_CODCAI,\n"
                    + "   USU_CODCAD,\n"
                    + "   USU_CODPRO,\n"
                    + "   USU_DESCAD,\n"
                    + "   USU_SEQCAD,\n"
                    + "   USU_SITCAD,\n"
                    + "   USU_VISSIT,\n"
                    + "   USU_USUGER,\n"
                    + "   USU_DATGER,\n"
                    + "   USU_HORGER)\n"
                    + "   VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            selectId = "SELECT NVL(MAX(USU_T075CAD.USU_CODCAD), 0) USU_CODCAD \n"
                    + "   FROM USU_T075CAD WHERE USU_CODEMP = 1 \n "
                    + "    AND USU_T075CAD.USU_CODCAT = " + produto.getIdCategoria() + "\n"
                    + "    AND USU_T075CAD.USU_CODSCA = " + produto.getIdSubCategoria() + "\n"
                    + "    AND USU_CODPRO = '" + produto.getCodigo() + "'";
            psSelectId = connection.prepareStatement(selectId);
            rsSelectId = psSelectId.executeQuery();
            if (rsSelectId.next()) {
                idCarCad = rsSelectId.getInt("USU_CODCAD");
            }
            psSelectId.close();
            rsSelectId.close();
            for (Produto Pro : lProduto) {
                idCarCad++;
                psInserirProduto = connection.prepareStatement(inserirProduto);
                psInserirProduto.setInt(1, 1);
                psInserirProduto.setInt(2, produto.getIdCategoria());
                psInserirProduto.setInt(3, produto.getIdSubCategoria());
                psInserirProduto.setInt(4, Pro.getIdCaracteristica());
                psInserirProduto.setInt(5, Pro.getIdCaracteristicaItem());
                psInserirProduto.setInt(6, idCarCad);
                psInserirProduto.setString(7, produto.getCodigo());
                if (origem.equalsIgnoreCase("CadAgrPro") && Pro.getReplicavel().equalsIgnoreCase("N")) {
                    psInserirProduto.setString(8, "");
                } else {
                    psInserirProduto.setString(8, Pro.getDescCaracteristica());
                }

                psInserirProduto.setInt(9, idCarCad);
                psInserirProduto.setString(10, Pro.getSituacao().toUpperCase());
                psInserirProduto.setString(11, Pro.getVisivel().toUpperCase());
                psInserirProduto.setLong(12, produto.getIdUsuario());
                psInserirProduto.setString(13, data);
                psInserirProduto.setInt(14, Utilitarios.getHoraNumero(sHora));
                psInserirProduto.executeUpdate();
                retorno = true;
            }
            psInserirProduto.close();
            if (origem.equalsIgnoreCase("CadPro") | origem.equalsIgnoreCase("CadAgrPro")) {
                retorno = insertDescricao(produto, connection);
            }
            if (origem.equalsIgnoreCase("CadPro") && lArquivo.size() > 0) {
                retorno = insertAnexo(produto, lArquivo, connection);
            }
            if (lCatalogo.size() > 0) {
                insertCatalogo(produto, lCatalogo, connection);
            }

        } catch (ErroSistemaException | SQLException ex) {
            retorno = false;
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return retorno;
    }

    private void insertCatalogo(Produto produto, List<Categoria> lCatalogo, Connection connection) throws ErroSistemaException {
        String insertCatalogo = "INSERT INTO USU_T075ECI\n"
                + "  (USU_CODEMP,\n"
                + "   USU_CODPRO,\n"
                + "   USU_CODCAT,\n"
                + "   USU_SITREL,\n"
                + "   USU_VISSIT,\n"
                + "   USU_USUGER,\n"
                + "   USU_DATGER,\n"
                + "   USU_HORGER,\n"
                + "   USU_CODECO)\n"
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String updateCatalogo = "UPDATE USU_T075ECI SET \n"
                + "                     USU_SITREL = ?,\n"
                + "                     USU_VISSIT = ?,\n"
                + "                     USU_USUALT = ?,\n"
                + "                     USU_DATALT = ?,\n"
                + "                     USU_HORALT = ?,\n"
                + "                     USU_CODECO = ?\n"
                + "               WHERE USU_CODEMP = ?\n"
                + "                 AND USU_CODPRO = ?\n"
                + "                 AND USU_CODCAT = ?";

        for (Categoria catalogo : lCatalogo) {
            try {
                if (catalogo.getCodPro().equals("")) {
                    psCatalogo = connection.prepareStatement(insertCatalogo);
                    psCatalogo.setInt(1, 1);
                    psCatalogo.setString(2, produto.getCodigo());
                    psCatalogo.setInt(3, catalogo.getIdCategoria());
                    psCatalogo.setString(4, catalogo.getSituacao());
                    psCatalogo.setString(5, catalogo.getVisivel());
                    psCatalogo.setLong(6, produto.getIdUsuario());
                    psCatalogo.setString(7, data);
                    psCatalogo.setInt(8, Utilitarios.getHoraNumero(sHora));
                    psCatalogo.setInt(9, catalogo.getCodEcommerce());
                    psCatalogo.executeUpdate();
                } else {
                    if (catalogo.getAlterado() != null && catalogo.getAlterado().equals("S")) {
                        psCatalogo = connection.prepareStatement(updateCatalogo);
                        psCatalogo.setString(1, catalogo.getSituacao());
                        psCatalogo.setString(2, catalogo.getVisivel());
                        psCatalogo.setLong(3, produto.getIdUsuario());
                        psCatalogo.setString(4, data);
                        psCatalogo.setInt(5, Utilitarios.getHoraNumero(sHora));
                        psCatalogo.setInt(6, catalogo.getCodEcommerce());
                        psCatalogo.setInt(7, 1);
                        psCatalogo.setString(8, produto.getCodigo());
                        psCatalogo.setInt(9, catalogo.getIdCategoria());
                        psCatalogo.executeUpdate();
                    }
                }
                retorno = true;
            } catch (SQLException ex) {
                retorno = false;
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
        }

    }

    public List<Produto> buscarPendecias() throws ErroSistemaException {
        String select = "SELECT CODPRO,\n"
                + "       DESPRO,\n"
                + "       DATGER,\n"
                + "       NOMUSU,\n"
                + "       (SELECT SUM(QTDEST)\n"
                + "          FROM E210EST\n"
                + "         WHERE E210EST.CODPRO = E075PRO.CODPRO\n"
                + "           AND CODDEP LIKE 'E%') ESTOQUE\n"
                + "  FROM E075PRO\n"
                + " INNER JOIN R999USU\n"
                + "    ON R999USU.CODUSU = E075PRO.USUGER\n"
                + " WHERE DATGER > '01/01/2020'\n"
                + "   AND USU_CODCAT = 0\n"
                + "   AND CODORI IN\n"
                + "       ('001', '002', '003', '004', '005', '006', '007', '008', '009')\n"
                + "   AND SUBSTR(DESPRO, 0, 1) <> '_'\n"
                + "   AND USU_STAPRO IN ('L')";
        lProduto = new LinkedList<>();
        try (PreparedStatement psSelectP = Conexao.getConnection().prepareStatement(select)) {
            ResultSet rsSelectP = psSelectP.executeQuery();
            while (rsSelectP.next()) {
                produto = new Produto();
                produto.setCodigo(rsSelectP.getString("CODPRO"));
                produto.setDescricao(rsSelectP.getString("DESPRO"));
                produto.setDataCadastro(rsSelectP.getDate("DATGER"));
                produto.setNomeUsuario(rsSelectP.getString("NOMUSU"));
                produto.setEstoque(rsSelectP.getInt("ESTOQUE"));
                lProduto.add(produto);
            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lProduto;
    }


}
