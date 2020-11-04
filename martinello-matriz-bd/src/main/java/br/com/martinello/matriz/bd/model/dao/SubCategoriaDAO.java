/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.model.dao;

import br.com.martinello.matriz.bd.model.domain.SubCategoria;
import br.com.martinello.matriz.util.Utilitarios;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 *
 * @author luiz.almeida
 */
public class SubCategoriaDAO {

    private String selectSubCategoria, insertSubCategoria, deletSubCategoria, updateSubCategoria;
    private PreparedStatement psSelectSubCategoria, psInsertSubCategoria, psUpdateSubCategoria, psDeleteSubCategoria;
    private ResultSet rsSelectSubCategoria, rsInsertSubCategoria, rsUpdateSubCategoria, rsDeleteSubCategoria;
    private List<SubCategoria> lSubCategoria;
    private SubCategoria subCategoria;
    private int idSubCategoria = 0;
    private int resultado, min, hora;
    private String data, sHora, codPro;

    public SubCategoriaDAO() {

    }

    public List<SubCategoria> selectSubCategoria(SubCategoria subCategoria) throws ErroSistemaException {
        try {
            selectSubCategoria = "SELECT USU_T075CAS.USU_CODEMP,\n"
                    + "       USU_T075CAS.USU_CODSCA,      \n"
                    + "       USU_DESSCA,\n"
                    + "       USU_T075CAS.USU_CODCAT,\n"
                    + "       USU_DESCAT,\n"
                    + "       USU_SITSCA,\n"
                    + "       USU_OBSSCA,\n"
                    + "       USU_T075CAS.USU_USUGER,\n"
                    + "       USU_T075CAS.USU_DATGER,\n"
                    + "       USU_T075CAS.USU_HORGER,\n"
                    + "       USU_T075CAS.USU_USUALT,\n"
                    + "       USU_T075CAS.USU_DATALT,\n"
                    + "       USU_T075CAS.USU_HORALT,\n"
                    + "       USU_T075CAS.USU_OBSALT,\n"
                    + "       NOMUSU\n"
                    + "  FROM USU_T075CAS\n"
                    + " INNER JOIN R999USU\n"
                    + "    ON CODUSU = USU_T075CAS.USU_USUGER\n"
                    + "    INNER JOIN USU_T075CAT\n"
                    + "    ON USU_T075CAS.USU_CODEMP = USU_T075CAT.USU_CODEMP\n"
                    + "    AND USU_T075CAS.USU_CODCAT = USU_T075CAT.USU_CODCAT \n" + getWhere(subCategoria);
            psSelectSubCategoria = Conexao.getConnection().prepareStatement(selectSubCategoria);
            rsSelectSubCategoria = psSelectSubCategoria.executeQuery();
            lSubCategoria = new ArrayList<>();
            while (rsSelectSubCategoria.next()) {
                subCategoria = new SubCategoria();
                subCategoria.setIdSubCategoria(rsSelectSubCategoria.getInt("USU_CODSCA"));
                subCategoria.setIdCategoria(rsSelectSubCategoria.getInt("USU_CODCAT"));
                subCategoria.setCategoria(rsSelectSubCategoria.getString("USU_DESCAT"));
                subCategoria.setSubCategoria(rsSelectSubCategoria.getString("USU_DESSCA"));
                subCategoria.setSituacao(rsSelectSubCategoria.getString("USU_SITSCA"));
                subCategoria.setIdUsuario(rsSelectSubCategoria.getLong("USU_USUGER"));
                subCategoria.setNomeUsuario(rsSelectSubCategoria.getString("NOMUSU"));
                subCategoria.setObsSubCategoria(rsSelectSubCategoria.getString("USU_OBSSCA"));
                subCategoria.setDataCadastro(rsSelectSubCategoria.getDate("USU_DATGER"));
                lSubCategoria.add(subCategoria);
            }
            resultado = psSelectSubCategoria.executeUpdate();
            if (resultado == -1) {
                psSelectSubCategoria.close();
                rsSelectSubCategoria.close();
                return lSubCategoria;
            } else {
                psSelectSubCategoria.close();
                rsSelectSubCategoria.close();
                return lSubCategoria;
            }

        } catch (ErroSistemaException | SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public SubCategoria buscarSubCategoria(SubCategoria subCategoria) throws ErroSistemaException {
        try {
            selectSubCategoria = "SELECT USU_T075CAS.USU_CODEMP,\n"
                    + "       USU_T075CAS.USU_CODSCA,      \n"
                    + "       USU_DESSCA,\n"
                    + "       USU_T075CAS.USU_CODCAT,\n"
                    + "       USU_DESCAT,\n"
                    + "       USU_SITSCA,\n"
                    + "       USU_OBSSCA,\n"
                    + "       USU_T075CAS.USU_USUGER,\n"
                    + "       USU_T075CAS.USU_DATGER,\n"
                    + "       USU_T075CAS.USU_HORGER,\n"
                    + "       USU_T075CAS.USU_USUALT,\n"
                    + "       USU_T075CAS.USU_DATALT,\n"
                    + "       USU_T075CAS.USU_HORALT,\n"
                    + "       USU_T075CAS.USU_OBSALT\n"
                    + "     FROM USU_T075CAS\n"
                    + "    INNER JOIN USU_T075CAT\n"
                    + "    ON USU_T075CAS.USU_CODEMP = USU_T075CAT.USU_CODEMP\n"
                    + "    AND USU_T075CAS.USU_CODCAT = USU_T075CAT.USU_CODCAT \n"
                    + "    WHERE USU_T075CAS.USU_CODSCA = " + subCategoria.getIdSubCategoria() + "\n"
                    + "    AND USU_T075CAS.USU_CODCAT = " + subCategoria.getIdCategoria() + "\n";
            psSelectSubCategoria = Conexao.getConnection().prepareStatement(selectSubCategoria);
            rsSelectSubCategoria = psSelectSubCategoria.executeQuery();
            lSubCategoria = new ArrayList<>();
            if (rsSelectSubCategoria.next()) {
                subCategoria = new SubCategoria();
                subCategoria.setIdCategoria(rsSelectSubCategoria.getInt("USU_CODCAT"));
                subCategoria.setCategoria(rsSelectSubCategoria.getString("USU_DESCAT"));
                subCategoria.setIdSubCategoria(rsSelectSubCategoria.getInt("USU_CODSCA"));
                subCategoria.setSubCategoria(rsSelectSubCategoria.getString("USU_DESSCA"));
                subCategoria.setSituacao(rsSelectSubCategoria.getString("USU_SITSCA"));
                subCategoria.setIdUsuario(rsSelectSubCategoria.getLong("USU_USUGER"));
                subCategoria.setObsSubCategoria(rsSelectSubCategoria.getString("USU_OBSSCA"));
                subCategoria.setDataCadastro(rsSelectSubCategoria.getDate("USU_DATGER"));
            }
            resultado = psSelectSubCategoria.executeUpdate();
            if (resultado == -1) {
                psSelectSubCategoria.close();
                rsSelectSubCategoria.close();
                return subCategoria;
            } else {
                psSelectSubCategoria.close();
                rsSelectSubCategoria.close();
                return subCategoria;
            }
        } catch (ErroSistemaException | SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public int insertSubCategoria(SubCategoria subCategoria) throws ErroSistemaException {
        try {
            variaveis();
            insertSubCategoria = " INSERT INTO USU_T075CAS\n"
                    + " (USU_CODEMP,\n"
                    + "          USU_CODCAT,\n"
                    + "          USU_CODSCA,\n"
                    + "          USU_DESSCA,\n"
                    + "          USU_SITSCA,\n"
                    + "          USU_OBSSCA,\n"
                     + "          USU_CODECO,\n"
                    + "          USU_USUGER,\n"
                    + "          USU_DATGER,\n"
                    + "          USU_HORGER)\n"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            selectSubCategoria = "SELECT NVL(MAX(USU_CODSCA), 0) AS CODSCA FROM USU_T075CAS WHERE USU_CODEMP = 1 AND USU_CODCAT = " + subCategoria.getIdCategoria() + "";
            psSelectSubCategoria = Conexao.getConnection().prepareStatement(selectSubCategoria);
            rsSelectSubCategoria = psSelectSubCategoria.executeQuery();
            if (rsSelectSubCategoria.next()) {
                idSubCategoria = rsSelectSubCategoria.getInt("CODSCA");
                idSubCategoria = idSubCategoria + 1;
            }
            psInsertSubCategoria = Conexao.getConnection().prepareStatement(insertSubCategoria);
            psInsertSubCategoria.setLong(1, 1);
            psInsertSubCategoria.setLong(2, subCategoria.getIdCategoria());
            psInsertSubCategoria.setInt(3, idSubCategoria);
            psInsertSubCategoria.setString(4, subCategoria.getSubCategoria());
            psInsertSubCategoria.setString(5, subCategoria.getSituacao());
            psInsertSubCategoria.setString(6, subCategoria.getObsSubCategoria());
            psInsertSubCategoria.setInt(7, 0);
            psInsertSubCategoria.setLong(8, subCategoria.getIdUsuario());
            psInsertSubCategoria.setString(9, data);
            psInsertSubCategoria.setInt(10, Utilitarios.getHoraNumero(sHora));
            resultado = psInsertSubCategoria.executeUpdate();
            if (resultado == -1) {
                psInsertSubCategoria.close();
                psSelectSubCategoria.close();
                Conexao.getConnection().rollback();
                return 0;
            } else {
                psInsertSubCategoria.close();
                psSelectSubCategoria.close();
                Conexao.getConnection().commit();
                return idSubCategoria;
            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public int updateSubCategoria(SubCategoria subCategoria) throws ErroSistemaException {
        try {
            variaveis();
            updateSubCategoria = "UPDATE USU_T075CAS SET"
                    + "          USU_DESSCA = ?,\n"
                    + "          USU_SITSCA = ?,\n"
                    + "          USU_OBSSCA = ?,\n"
                    + "          USU_CODCAT = ?,\n"
                    + "          USU_USUALT = ?,\n"
                    + "          USU_DATALT = ?,\n"
                    + "          USU_HORALT = ?\n"
                    + "          WHERE \n"
                    + "          USU_CODSCA = ? \n"
                    + "          AND USU_CODEMP = ?";
            psUpdateSubCategoria = Conexao.getConnection().prepareStatement(updateSubCategoria);
            psUpdateSubCategoria.setString(1, subCategoria.getSubCategoria());
            psUpdateSubCategoria.setString(2, subCategoria.getSituacao());
            psUpdateSubCategoria.setString(3, subCategoria.getObsSubCategoria());
            psUpdateSubCategoria.setLong(4, subCategoria.getIdCategoria());
            psUpdateSubCategoria.setLong(5, subCategoria.getIdUsuario());
            psUpdateSubCategoria.setString(6, data);
            psUpdateSubCategoria.setInt(7, Utilitarios.getHoraNumero(sHora));
            psUpdateSubCategoria.setLong(8, subCategoria.getIdSubCategoria());
            psUpdateSubCategoria.setInt(9, 1);

            resultado = psUpdateSubCategoria.executeUpdate();
            if (resultado == -1) {
                psUpdateSubCategoria.close();
                psUpdateSubCategoria.close();
                Conexao.getConnection().rollback();
                return 0;
            } else {
                psUpdateSubCategoria.close();
                psUpdateSubCategoria.close();
                Conexao.getConnection().commit();
                return idSubCategoria;
            }
        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public String validaAltCategoria(String codigo) throws ErroSistemaException {
        try {
            selectSubCategoria = "SELECT CODPRO\n"
                    + "  FROM E075PRO\n"
                    + " WHERE EXISTS (SELECT 1 FROM E075PRO WHERE USU_CODSCA = " + codigo + ")";
            psSelectSubCategoria = Conexao.getConnection().prepareStatement(selectSubCategoria);
            rsSelectSubCategoria = psSelectSubCategoria.executeQuery();
            codPro = "";
            while (rsSelectSubCategoria.next()) {
                if (codPro.equalsIgnoreCase("")) {
                    codPro = rsSelectSubCategoria.getString("");
                } else {
                    codPro = codPro + " ," + rsSelectSubCategoria.getString("");
                }
            }
            resultado = psSelectSubCategoria.executeUpdate();
            if (resultado == -1) {
                psSelectSubCategoria.close();
                rsSelectSubCategoria.close();
                return codPro;
            } else {
                psSelectSubCategoria.close();
                rsSelectSubCategoria.close();
                return codPro;
            }
        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public List<SubCategoria> listarJcbSubCategorias(SubCategoria subCategoria) throws ErroSistemaException {
        try {
            lSubCategoria = new ArrayList<>();
            selectSubCategoria = "SELECT 0 USU_CODSCA, 0 USU_CODCAT, 'TODAS' USU_DESSCA\n"
                    + "         FROM DUAL\n"
                    + "         UNION ALL\n"
                    + "         SELECT USU_CODSCA, USU_CODCAT, USU_DESSCA FROM USU_T075CAS\n" + getWhereJcb(subCategoria) + " ORDER BY USU_CODSCA ASC";
            psSelectSubCategoria = Conexao.getConnection().prepareStatement(selectSubCategoria);
            rsSelectSubCategoria = psSelectSubCategoria.executeQuery();
            while (rsSelectSubCategoria.next()) {
                subCategoria = new SubCategoria();
                subCategoria.setIdSubCategoria(rsSelectSubCategoria.getInt("USU_CODSCA"));
                subCategoria.setIdCategoria(rsSelectSubCategoria.getInt("USU_CODCAT"));
                subCategoria.setSubCategoria(rsSelectSubCategoria.getString("USU_DESSCA"));
                lSubCategoria.add(subCategoria);
            }
            psSelectSubCategoria.close();
            rsSelectSubCategoria.close();
        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lSubCategoria;
    }

    public boolean excluirSubCategoria(SubCategoria subCategoria) throws ErroSistemaException {
        deletSubCategoria = "DELETE FROM USU_T075CAS WHERE USU_CODCAT = " + subCategoria.getIdCategoria() + " AND USU_CODSCA = " + subCategoria.getIdSubCategoria() + "";
        String deleteCar = "DELETE FROM USU_T075CAR WHERE USU_CODCAT = " + subCategoria.getIdCategoria() + " AND USU_CODSCA = " + subCategoria.getIdSubCategoria() + "";
        String deleteCarIte = "DELETE FROM USU_T075CAP WHERE USU_CODCAT = " + subCategoria.getIdCategoria() + " AND USU_CODSCA = " + subCategoria.getIdSubCategoria() + "";

        try {
            selectSubCategoria = "SELECT *FROM USU_T075CAD \n"
                    + "            WHERE USU_CODCAT = " + subCategoria.getIdCategoria() + " \n"
                    + "              AND USU_CODSCA = " + subCategoria.getIdSubCategoria() + "";
            Connection connection = Conexao.getConnection();
            connection.setAutoCommit(false);
            psSelectSubCategoria = connection.prepareStatement(selectSubCategoria);
            rsSelectSubCategoria = psSelectSubCategoria.executeQuery();
            if (rsSelectSubCategoria.next()) {
                throw new ErroSistemaException("Erro na exclusão: O registro não pode ser excluido poís já possui valores de descrição cadastrado!");
            } else {
                psDeleteSubCategoria = connection.prepareStatement(deleteCarIte);
                psDeleteSubCategoria.executeUpdate();
                psDeleteSubCategoria = connection.prepareStatement(deleteCar);
                psDeleteSubCategoria.executeUpdate();
                psDeleteSubCategoria = connection.prepareStatement(deletSubCategoria);
                resultado = psDeleteSubCategoria.executeUpdate();
                if (resultado == -1) {
                    psDeleteSubCategoria.close();
                    psSelectSubCategoria.close();
                    rsSelectSubCategoria.close();
                    connection.rollback();
                    return false;
                } else {
                    psDeleteSubCategoria.close();
                    psSelectSubCategoria.close();
                    rsSelectSubCategoria.close();
                    connection.commit();
                    return true;
                }
            }
        } catch (ErroSistemaException | SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public String getWhere(SubCategoria subCategoria) {
        String where = "";
        where += ((subCategoria.getIdCategoria() != 0) ? " AND USU_T075CAT.USU_CODCAT = " + subCategoria.getIdCategoria() + "" : "");
        where += ((subCategoria.getIdSubCategoria() != 0) ? " AND USU_T075CAS.USU_CODSCA = " + subCategoria.getIdSubCategoria() + "" : "");
        where += ((subCategoria.getCategoria() != null) ? " AND UPPER(USU_T075CAS.USU_DESCAT) LIKE '" + subCategoria.getCategoria() + "'" : "");
        where += ((subCategoria.getSubCategoria() != null) ? " AND UPPER(USU_T075CAS.USU_DESSCA) LIKE '" + subCategoria.getSubCategoria() + "'" : "");
        where += ((!subCategoria.getSituacao().trim().equalsIgnoreCase("")) ? " AND USU_T075CAS.USU_SITSCA IN (" + subCategoria.getSituacao() + ")" : "");
        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }

    public String getWhereJcb(SubCategoria subCategoria) {
        String where = "";
        where += ((subCategoria.getIdCategoria() != 0) ? " AND USU_T075CAS.USU_CODCAT = " + subCategoria.getIdCategoria() + "" : "");
        where += ((subCategoria.getIdSubCategoria() != 0) ? " AND USU_T075CAS.USU_CODSCA = " + subCategoria.getIdSubCategoria() + "" : "");
        where += ((subCategoria.getCategoria() != null) ? " AND UPPER(USU_T075CAS.USU_DESSCA) LIKE '" + subCategoria.getCategoria() + "'" : "");

        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }

    public void variaveis() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Cuiaba"));
        hora = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        sHora = hora + ":" + min;
        data = Utilitarios.converteDataString(new Date(), "dd/MM/yyyy");
    }
}
