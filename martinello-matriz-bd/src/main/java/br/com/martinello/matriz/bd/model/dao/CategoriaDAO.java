/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.model.dao;

import br.com.martinello.matriz.bd.model.domain.Categoria;
import br.com.martinello.matriz.util.Utilitarios;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import br.com.martinello.matriz.util.filtro.Filtro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

/**
 *
 * @author luiz.almeida
 */
public class CategoriaDAO {

    private String selectCategoria, insertCategoria, deletCategoria, updateCategoria;
    private PreparedStatement psSelectCategoria, psInsertCategoria, psUpdateCategoria, psDeleteCategoria;
    private ResultSet rsSelectCategoria, rsInsertCategoria, rsUpdateCategoria, rsDeleteCategoria;
    private List<Categoria> lCategoria;
    private Categoria categoria;
    private long idCategoria = 0;
    private int resultado, min, hora;
    private String data, sHora;

    public CategoriaDAO() {

    }

    public List<Categoria> selectCategoria(Categoria categoria) throws ErroSistemaException {
        try {
            selectCategoria = "  SELECT USU_CODEMP,\n"
                    + "          USU_CODCAT,\n"
                    + "          USU_DESCAT,\n"
                    + "          USU_SITCAT,\n"
                    + "          USU_OBSCAT,\n"
                    + "          USU_USUGER,\n"
                    + "          USU_DATGER,\n"
                    + "          USU_HORGER,\n"
                    + "          USU_USUALT,\n"
                    + "          USU_DATALT,\n"
                    + "          USU_HORALT,\n"
                    + "          USU_OBSALT,\n"
                    + "          NOMUSU\n"
                    + "     FROM USU_T075CAT\n"
                    + "    INNER JOIN R999USU\n"
                    + "       ON CODUSU = USU_USUGER \n" + getWhere(categoria);
            System.out.println("SelectCat:" + selectCategoria);
            psSelectCategoria = Conexao.getConnection().prepareStatement(selectCategoria);
            rsSelectCategoria = psSelectCategoria.executeQuery();
            lCategoria = new ArrayList<>();
            while (rsSelectCategoria.next()) {
                categoria = new Categoria();
                categoria.setIdCategoria(rsSelectCategoria.getInt("USU_CODCAT"));
                categoria.setCategoria(rsSelectCategoria.getString("USU_DESCAT"));
                categoria.setSituacao(rsSelectCategoria.getString("USU_SITCAT"));
                categoria.setIdUsuario(rsSelectCategoria.getLong("USU_USUGER"));
                categoria.setNomeUsuario(rsSelectCategoria.getString("NOMUSU"));
                categoria.setObsCategoria(rsSelectCategoria.getString("USU_OBSCAT"));
                categoria.setDataCadastro(rsSelectCategoria.getDate("USU_DATGER"));
                lCategoria.add(categoria);
            }
            resultado = psSelectCategoria.executeUpdate();
            if (resultado == -1) {
                psSelectCategoria.close();
                rsSelectCategoria.close();
                return lCategoria;
            } else {
                psSelectCategoria.close();
                rsSelectCategoria.close();
                return lCategoria;
            }

        } catch (ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public List<Categoria> listarJcbCategorias(Categoria categoria) throws ErroSistemaException {

        try {
            lCategoria = new ArrayList<>();
            selectCategoria = "SELECT 0 USU_CODCAT, 'TODAS' USU_DESCAT\n"
                    + "  FROM DUAL\n"
                    + "UNION ALL\n"
                    + "SELECT USU_CODCAT, USU_DESCAT FROM USU_T075CAT ORDER BY USU_CODCAT ASC";
            psSelectCategoria = Conexao.getConnection().prepareStatement(selectCategoria);
            rsSelectCategoria = psSelectCategoria.executeQuery();
            while (rsSelectCategoria.next()) {
                categoria = new Categoria();
                categoria.setIdCategoria(rsSelectCategoria.getInt("USU_CODCAT"));
                categoria.setCategoria(rsSelectCategoria.getString("USU_DESCAT"));
                lCategoria.add(categoria);
            }
            psSelectCategoria.close();
            rsSelectCategoria.close();
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

        return lCategoria;
    }

    public Categoria buscarCategoria(String codigo) throws ErroSistemaException {
        try {
            selectCategoria = "  SELECT USU_CODEMP,\n"
                    + "          USU_CODCAT,\n"
                    + "          USU_DESCAT,\n"
                    + "          USU_SITCAT,\n"
                    + "          USU_OBSCAT,\n"
                    + "          USU_USUGER,\n"
                    + "          USU_DATGER,\n"
                    + "          USU_HORGER,\n"
                    + "          USU_USUALT,\n"
                    + "          USU_DATALT,\n"
                    + "          USU_HORALT,\n"
                    + "          USU_OBSALT\n"
                    + "     FROM USU_T075CAT\n"
                    + "     WHERE USU_CODCAT = " + codigo + "";
            psSelectCategoria = Conexao.getConnection().prepareStatement(selectCategoria);
            rsSelectCategoria = psSelectCategoria.executeQuery();
            lCategoria = new ArrayList<>();
            if (rsSelectCategoria.next()) {
                categoria = new Categoria();
                categoria.setIdCategoria(rsSelectCategoria.getInt("USU_CODCAT"));
                categoria.setCategoria(rsSelectCategoria.getString("USU_DESCAT"));
                categoria.setSituacao(rsSelectCategoria.getString("USU_SITCAT"));
                categoria.setIdUsuario(rsSelectCategoria.getLong("USU_USUGER"));
                categoria.setObsCategoria(rsSelectCategoria.getString("USU_OBSCAT"));
                categoria.setDataCadastro(rsSelectCategoria.getDate("USU_DATGER"));
            }
            resultado = psSelectCategoria.executeUpdate();
            if (resultado == -1) {
                psSelectCategoria.close();
                rsSelectCategoria.close();
                return categoria;
            } else {
                psSelectCategoria.close();
                rsSelectCategoria.close();
                return categoria;
            }

        } catch (ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public boolean insertCategoria(Categoria categoria) throws ErroSistemaException {
        try {
            insertCategoria = " INSERT INTO USU_T075CAT\n"
                    + " (USU_CODEMP,\n"
                    + "          USU_CODCAT,\n"
                    + "          USU_DESCAT,\n"
                    + "          USU_SITCAT,\n"
                    + "          USU_OBSCAT,\n"
                    + "          USU_CODECO,\n"
                    + "          USU_USUGER,\n"
                    + "          USU_DATGER,\n"
                    + "          USU_HORGER)\n"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

            selectCategoria = "SELECT NVL(MAX(USU_CODCAT), 1) AS CODCAT FROM USU_T075CAT WHERE USU_CODEMP = 1";
            psSelectCategoria = Conexao.getConnection().prepareStatement(selectCategoria);
            rsSelectCategoria = psSelectCategoria.executeQuery();
            if (rsSelectCategoria.next()) {
                idCategoria = rsSelectCategoria.getLong("CODCAT");
                idCategoria = idCategoria + 1;
            }
            psInsertCategoria = Conexao.getConnection().prepareStatement(insertCategoria);
            psInsertCategoria.setLong(1, 1);
            psInsertCategoria.setLong(2, idCategoria);
            psInsertCategoria.setString(3, categoria.getCategoria());
            psInsertCategoria.setString(4, categoria.getSituacao());
            psInsertCategoria.setString(5, categoria.getObsCategoria());
            psInsertCategoria.setInt(6, 0);
            psInsertCategoria.setLong(7, categoria.getIdUsuario());
            data = Utilitarios.converteDataString(new Date(), "dd/MM/yyyy");
            psInsertCategoria.setString(8, data);
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Cuiaba"));
            hora = calendar.get(Calendar.HOUR_OF_DAY);
            min = calendar.get(Calendar.MINUTE);
            sHora = hora + ":" + min;
            psInsertCategoria.setInt(9, Utilitarios.getHoraNumero(sHora));
            System.out.println("" + psInsertCategoria.toString());

            resultado = psInsertCategoria.executeUpdate();
            if (resultado == -1) {
                psInsertCategoria.close();
                psSelectCategoria.close();
                Conexao.getConnection().rollback();
                return false;
            } else {
                psInsertCategoria.close();
                psSelectCategoria.close();
                Conexao.getConnection().commit();
                return true;
            }

        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());

        }

    }

    public boolean updateCategoria(Categoria categoria) throws ErroSistemaException {
        try {
            variaveis();
            updateCategoria = "UPDATE USU_T075CAT SET"
                    + "          USU_DESCAT = ?,\n"
                    + "          USU_SITCAT = ?,\n"
                    + "          USU_OBSCAT = ?,\n"
                    + "          USU_USUALT = ?,\n"
                    + "          USU_DATALT = ?,\n"
                    + "          USU_HORALT = ?\n"
                    + "          WHERE \n"
                    + "          USU_CODCAT = ? \n"
                    + "          AND USU_CODEMP = ?";
            psUpdateCategoria = Conexao.getConnection().prepareStatement(updateCategoria);
            psUpdateCategoria.setString(1, categoria.getCategoria());
            psUpdateCategoria.setString(2, categoria.getSituacao());
            psUpdateCategoria.setString(3, categoria.getObsCategoria());
            psUpdateCategoria.setLong(4, categoria.getIdUsuario());
            psUpdateCategoria.setString(5, data);
            psUpdateCategoria.setInt(6, Utilitarios.getHoraNumero(sHora));
            psUpdateCategoria.setLong(7, categoria.getIdCategoria());
            psUpdateCategoria.setInt(8, 1);
            resultado = psUpdateCategoria.executeUpdate();
            if (resultado == -1) {
                psUpdateCategoria.close();
                psUpdateCategoria.close();
                Conexao.getConnection().rollback();
                return false;
            } else {
                psUpdateCategoria.close();
                psUpdateCategoria.close();
                Conexao.getConnection().commit();
                return true;
            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public String getWhere(Categoria categoria) {
        String where = "";
        where += ((categoria.getIdCategoria() != 0) ? " AND USU_T075CAT.USU_CODCAT = " + categoria.getIdCategoria() + "" : "");
        where += ((categoria.getCategoria() != null) ? " AND UPPER(USU_T075CAT.USU_DESCAT) LIKE '" + categoria.getCategoria() + "'" : "");
        where += ((!categoria.getSituacao().trim().equalsIgnoreCase("")) ? " AND USU_T075CAT.USU_SITCAT IN (" + categoria.getSituacao() + ")" : "");
        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }

    public String getWhereCat(Categoria categoria) {
        String where = "";
        where += ((categoria.getIdCategoria() != 0) ? " AND USU_T075ECD.USU_CODCAT = " + categoria.getIdCategoria() + "" : "");
        where += ((categoria.getCategoria() != null) ? " AND UPPER(USU_T075ECD.USU_DESCAT) LIKE '" + categoria.getCategoria() + "'" : "");
        where += ((categoria.getIdCategoriaPai() != 0) ? " AND USU_T075ECD.USU_CATPAI = " + categoria.getIdCategoriaPai() + "" : "");
        where += ((categoria.getCategoriaPai() != null) ? " AND UPPER(CATPAI.USU_DESCAT) LIKE '" + categoria.getCategoriaPai() + "'" : "");
        where += ((!categoria.getSituacao().trim().equalsIgnoreCase("")) ? " AND USU_T075ECD.USU_SITCAT IN (" + categoria.getSituacao() + ")" : "");
        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }

    public boolean excluirCategoria(Categoria categoria) throws ErroSistemaException {
        try {
            deletCategoria = "DELETE FROM USU_T075CAT WHERE USU_CODCAT = " + categoria.getIdCategoria() + "";
            Connection connection = Conexao.getConnection();
            connection.setAutoCommit(false);
            psDeleteCategoria = connection.prepareStatement(deletCategoria);
            resultado = psDeleteCategoria.executeUpdate();

            if (resultado == -1) {
                psUpdateCategoria.close();
                psUpdateCategoria.close();
                Conexao.getConnection().rollback();
                return false;
            } else {
                psUpdateCategoria.close();
                psUpdateCategoria.close();
                Conexao.getConnection().commit();
                return true;
            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public List<Categoria> selectCatalogo(List<Filtro> lFiltros) throws ErroSistemaException {
        try {
            selectCategoria = "SELECT USU_CODCAT, USU_CATPAI, USU_DESCAT, USU_SITCAT FROM USU_T075ECD" + getWhereFiltro(lFiltros);
            lCategoria = new LinkedList<>();
            System.out.println("Catalogo: " + selectCategoria);
            psSelectCategoria = Conexao.getConnection().prepareStatement(selectCategoria);
            rsSelectCategoria = psSelectCategoria.executeQuery();
            while (rsSelectCategoria.next()) {
                categoria = new Categoria();
                categoria.setIdCategoria(rsSelectCategoria.getInt("USU_CODCAT"));
                categoria.setIdCategoriaPai(rsSelectCategoria.getInt("USU_CATPAI"));
                categoria.setCategoria(rsSelectCategoria.getString("USU_DESCAT"));
                categoria.setSituacao(rsSelectCategoria.getString("USU_SITCAT"));
                lCategoria.add(categoria);
            }
            return lCategoria;
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    private String getWhereFiltro(List<Filtro> lFiltros) {
        String where = "";
        for (Filtro filtro : lFiltros) {
            if (filtro.getCampo().equalsIgnoreCase("descricao")) {
                where += ((filtro.getValor() != null) ? " AND UPPER(USU_T075ECD.USU_DESCAT) LIKE UPPER('" + filtro.getValor() + "')" : "");
            }
            if (filtro.getCampo().equalsIgnoreCase("codigo")) {
                where += ((filtro.getValor() != null) ? " AND USU_T075ECD.USU_CODCAT = '" + filtro.getValor() + "'" : "");
            }
        }
        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }

    public List<Categoria> listarCatalogo(Categoria categoria) throws ErroSistemaException {
        try {
            selectCategoria = "SELECT USU_T075ECD.USU_CODCAT, \n"
                    + "               USU_T075ECD.USU_CATPAI,\n"
                    + "               CATPAI.USU_DESCAT DESPAI,\n"
                    + "               USU_T075ECD.USU_DESCAT,\n"
                    + "               USU_T075ECD.USU_SITCAT,\n"
                    + "               USU_T075ECD.USU_VISSIT,\n"
                    + "               USU_T075ECD.USU_OBSALT,\n"
                    + "               USU_T075ECD.USU_DATGER\n"
                    + "          FROM USU_T075ECD\n"
                    + "          LEFT JOIN USU_T075ECD CATPAI\n"
                    + "               ON CATPAI.USU_CODCAT = USU_T075ECD.USU_CATPAI" + getWhereCat(categoria);
            lCategoria = new LinkedList<>();
            System.out.println("Catalogo: " + selectCategoria);
            psSelectCategoria = Conexao.getConnection().prepareStatement(selectCategoria);
            rsSelectCategoria = psSelectCategoria.executeQuery();
            while (rsSelectCategoria.next()) {
                categoria = new Categoria();
                categoria.setIdCategoria(rsSelectCategoria.getInt("USU_CODCAT"));
                categoria.setIdCategoriaPai(rsSelectCategoria.getInt("USU_CATPAI"));
                categoria.setCategoria(rsSelectCategoria.getString("USU_DESCAT"));
                categoria.setCategoriaPai(rsSelectCategoria.getString("DESPAI"));
                categoria.setSituacao(rsSelectCategoria.getString("USU_SITCAT"));
                categoria.setVisivel(rsSelectCategoria.getString("USU_VISSIT"));
                categoria.setObsCategoria(rsSelectCategoria.getString("USU_OBSALT"));
                categoria.setDataCadastro(rsSelectCategoria.getDate("USU_DATGER"));
                lCategoria.add(categoria);
            }
            return lCategoria;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public void insertCatalogo(Categoria categoria) throws ErroSistemaException {
        Connection connection = Conexao.getConnection();
        try {
            variaveis();
            insertCategoria = " INSERT INTO USU_T075ECD\n"
                    + "          (USU_CODCAT,\n"
                    + "          USU_CATPAI,\n"
                    + "          USU_DESCAT,\n"
                    + "          USU_SITCAT,\n"
                    + "          USU_VISSIT,\n"
                    + "          USU_CODECO,\n"
                    + "          USU_USUGER,\n"
                    + "          USU_DATGER,\n"
                    + "          USU_HORGER)\n"
                    + "   VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

            selectCategoria = "SELECT NVL(MAX(USU_CODCAT), 0) AS CODCAT FROM USU_T075ECD";
            psSelectCategoria = connection.prepareStatement(selectCategoria);
            rsSelectCategoria = psSelectCategoria.executeQuery();
            System.out.println("insertCatalogo " + insertCategoria);
            if (rsSelectCategoria.next()) {
                idCategoria = rsSelectCategoria.getLong("CODCAT");
                idCategoria = idCategoria + 1;
            }
            psInsertCategoria = connection.prepareStatement(insertCategoria);
            psInsertCategoria.setLong(1, idCategoria);
            if (categoria.getIdCategoriaPai() == 0) {
                psInsertCategoria.setString(2, null);
            } else {
                psInsertCategoria.setString(2, Integer.toString(categoria.getIdCategoriaPai()));
            }
            psInsertCategoria.setString(3, categoria.getCategoria());
            psInsertCategoria.setString(4, categoria.getSituacao());
            psInsertCategoria.setString(5, categoria.getVisivel());
            psInsertCategoria.setInt(6, 0);
            psInsertCategoria.setLong(7, categoria.getIdUsuario());
            psInsertCategoria.setString(8, data);
            psInsertCategoria.setInt(9, Utilitarios.getHoraNumero(sHora));
            resultado = psInsertCategoria.executeUpdate();
//            if (resultado == -1) {
//                psInsertCategoria.close();
//                psSelectCategoria.close();
//                connection.rollback();
//                return false;
//            } else {
//                psInsertCategoria.close();
//                psSelectCategoria.close();
//                connection.commit();
//                return true;
//            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
            try {
                if (resultado == -1) {
                    psInsertCategoria.close();
                    psSelectCategoria.close();
                    connection.rollback();
                    //return false;
                } else {
                    psInsertCategoria.close();
                    psSelectCategoria.close();
                    connection.commit();
                    //return true;
                }
            } catch (SQLException ex) {
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
        }
    }

    public boolean updateCatalogo(Categoria categoria) throws ErroSistemaException {

        Connection connection = Conexao.getConnection();
        try {
            variaveis();
            updateCategoria = "UPDATE USU_T075ECD SET"
                    + "          USU_DESCAT = ?,\n"
                    + "          USU_SITCAT = ?,\n"
                    + "          USU_CATPAI = ?,\n"
                    + "          USU_VISSIT = ?,\n"
                    + "          USU_USUALT = ?,\n"
                    + "          USU_DATALT = ?,\n"
                    + "          USU_HORALT = ?,\n"
                    + "         USU_OBSALT = ?\n"
                    + "          WHERE \n"
                    + "          USU_CODCAT = ? \n";

            connection.setAutoCommit(false);
            psUpdateCategoria = connection.prepareStatement(updateCategoria);
            psUpdateCategoria.setString(1, categoria.getCategoria());
            psUpdateCategoria.setString(2, categoria.getSituacao());
            if (categoria.getIdCategoriaPai() == 0) {
                psUpdateCategoria.setString(3, null);
            } else {
                psUpdateCategoria.setString(3, Integer.toString(categoria.getIdCategoriaPai()));
            }
            psUpdateCategoria.setString(4, categoria.getVisivel());
            psUpdateCategoria.setLong(5, categoria.getIdUsuario());
            psUpdateCategoria.setString(6, data);
            psUpdateCategoria.setInt(7, Utilitarios.getHoraNumero(sHora));
            psUpdateCategoria.setString(8, categoria.getObsCategoria());
            psUpdateCategoria.setLong(9, categoria.getIdCategoria());
            resultado = psUpdateCategoria.executeUpdate();
//            if (resultado == -1) {
//                psUpdateCategoria.close();
//                connection.rollback();
//                return false;
//            } else {
//                psUpdateCategoria.close();
//                connection.commit();
//                return true;
//            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
            try {
                if (resultado == -1) {
                    psUpdateCategoria.close();
                    connection.rollback();
                    return false;
                } else {
                    psUpdateCategoria.close();
                    connection.commit();
                    return true;
                }
            } catch (SQLException ex) {
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
        }
    }

    public boolean excluirCatalogo(Categoria categoria) throws ErroSistemaException {

        Connection connection = Conexao.getConnection();
        try {
            deletCategoria = "DELETE FROM USU_T075ECD "
                    + "             WHERE ((USU_CODCAT = " + categoria.getIdCategoria() + ") "
                    + "                OR USU_CATPAI = " + categoria.getIdCategoria() + ") "
                    + "               AND USU_CODECO = 0 ";

            connection.setAutoCommit(false);
            psDeleteCategoria = connection.prepareStatement(deletCategoria);
            resultado = psDeleteCategoria.executeUpdate();

//            if (resultado == -1) {
//                psDeleteCategoria.close();
//                connection.rollback();
//                return false;
//            } else {
//                psDeleteCategoria.close();
//                connection.commit();
//                return true;
//            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());

        } finally {
            try {
                if (resultado == -1) {
                    psDeleteCategoria.close();
                    connection.rollback();
                    return false;
                } else {
                    psDeleteCategoria.close();
                    connection.commit();
                    return true;
                }
            } catch (SQLException ex) {
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
        }

    }

    public void variaveis() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Cuiaba"));
        hora = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        sHora = hora + ":" + min;
        data = Utilitarios.converteDataString(new Date(), "dd/MM/yyyy");
    }

    public List<Categoria> listarCatalogoItem(String codigo) throws ErroSistemaException {
        selectCategoria = "SELECT USU_T075ECI.USU_CODEMP,\n"
                + "       USU_T075ECI.USU_CODPRO,\n"
                + "       USU_T075ECI.USU_CODCAT,\n"
                + "       USU_T075ECD.USU_CATPAI,\n"
                + "       CATPAI.USU_DESCAT DESPAI,\n"
                + "       USU_T075ECD.USU_DESCAT,\n"
                + "       USU_T075ECI.USU_SITREL,\n"
                + "       USU_T075ECI.USU_VISSIT,\n"
                + "       USU_T075ECI.USU_USUGER,\n"
                + "       USU_T075ECI.USU_DATGER,\n"
                + "       USU_T075ECI.USU_HORGER,\n"
                + "       USU_T075ECI.USU_CODECO\n"
                + "  FROM USU_T075ECI\n"
                + " INNER JOIN USU_T075ECD\n"
                + "    ON USU_T075ECI.USU_CODCAT = USU_T075ECD.USU_CODCAT\n"
                + "  LEFT JOIN USU_T075ECD CATPAI\n"
                + "    ON CATPAI.USU_CODCAT = USU_T075ECD.USU_CATPAI\n"
                + " WHERE USU_T075ECI.USU_CODEMP = 1\n"
                + "   AND USU_T075ECI.USU_CODPRO = '" + codigo + "'";
        try {
            lCategoria = new LinkedList<>();
            System.out.println("Catalogo: " + selectCategoria);
            psSelectCategoria = Conexao.getConnection().prepareStatement(selectCategoria);
            rsSelectCategoria = psSelectCategoria.executeQuery();
            while (rsSelectCategoria.next()) {
                categoria = new Categoria();
                categoria.setCodPro(rsSelectCategoria.getString("USU_CODPRO"));
                categoria.setIdCategoria(rsSelectCategoria.getInt("USU_CODCAT"));
                categoria.setIdCategoriaPai(rsSelectCategoria.getInt("USU_CATPAI"));
                categoria.setCategoria(rsSelectCategoria.getString("USU_DESCAT"));
                categoria.setCategoriaPai(rsSelectCategoria.getString("DESPAI"));
                categoria.setSituacao(rsSelectCategoria.getString("USU_SITREL"));
                categoria.setVisivel(rsSelectCategoria.getString("USU_VISSIT"));
                categoria.setCodEcommerce(rsSelectCategoria.getInt("USU_CODECO"));
                categoria.setDataCadastro(rsSelectCategoria.getDate("USU_DATGER"));
                lCategoria.add(categoria);
            }
            return lCategoria;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

}
