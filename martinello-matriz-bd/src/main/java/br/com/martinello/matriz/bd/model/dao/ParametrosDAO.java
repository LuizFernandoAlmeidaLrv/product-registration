/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.model.dao;

import br.com.martinello.matriz.bd.model.domain.Parametro;
import br.com.martinello.matriz.util.Utilitarios;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
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
public class ParametrosDAO {

    private String sqlSelectParametros, sqlInsertParametros, sqlUpdateParametros;
    private PreparedStatement psSelectParametros, psInsertParametros, psUpdateParametros;
    private ResultSet rsSelectParametros, rsInsertParametros, rsUpdateParametros;
    private List<Parametro> LParametros = new ArrayList<>();
    private Parametro parametro;
    private int resultado, min, hora, idCarCad;
    private String data, sHora, codPro;

    public ParametrosDAO() {

        sqlInsertParametros = "INSERT INTO USU_T000PGJ\n"
                + "                   (USU_CODEMP, \n"
                + "                   USU_CODFLO, \n"
                + "                   USU_CHAPAR, \n"
                + "                   USU_VLRPAR, \n"
                + "                   USU_OBSPAR, \n"
                + "                   USU_USUGER, \n"
                + "                   USU_DATGER, \n"
                + "                   USU_HORGER) \n"
                + "             VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        sqlUpdateParametros = "UPDATE USU_T000PGJ SET \n"
                + "         USU_VLRPAR = ?, \n"
                + "         USU_OBSPAR = ?, \n"
                + "         USU_USUALT = ?, \n"
                + "         USU_DATALT = ?, \n"
                + "         USU_HORALT = ? \n"
                + "   WHERE USU_CODEMP = ? \n"
                + "     AND USU_CODFLO = ? \n"
                + "     AND USU_CHAPAR = ? \n";
    }

    public List<Parametro> ListParametros(Parametro parametro) throws ErroSistemaException {
        try {
            LParametros = new ArrayList<>();
            sqlSelectParametros = "SELECT USU_CODFLO, \n"
                    + "                   USU_CHAPAR, \n"
                    + "                   USU_VLRPAR, \n"
                    + "                   USU_OBSPAR, \n"
                    + "                   USU_USUGER, \n"
                    + "                   USU_DATGER, \n"
                    + "                   USU_HORGER \n"
                    + "              FROM USU_T000PGJ " + getWhere(parametro);
            psSelectParametros = Conexao.getConnection().prepareStatement(sqlSelectParametros);
            rsSelectParametros = psSelectParametros.executeQuery();
            while (rsSelectParametros.next()) {
                parametro = new Parametro();
                parametro.setParametro(rsSelectParametros.getString("USU_CHAPAR"));
                parametro.setValor(rsSelectParametros.getString("USU_VLRPAR"));
                parametro.setUltimaAtualizacao(rsSelectParametros.getString("USU_DATGER"));
                parametro.setUsuarioAlteracao(rsSelectParametros.getLong("USU_USUGER"));
                parametro.setDescricao(rsSelectParametros.getString("USU_OBSPAR"));
                LParametros.add(parametro);

            }
            return LParametros;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public void insertParametro(Parametro parametro) throws ErroSistemaException {

        try {
            variaveis();
            psInsertParametros = Conexao.getConnection().prepareStatement(sqlInsertParametros);
            psInsertParametros.setInt(1, 1);
            psInsertParametros.setString(2, "0000");
            psInsertParametros.setString(3, parametro.getParametro());
            psInsertParametros.setString(4, parametro.getValor());
            psInsertParametros.setString(5, parametro.getDescricao());
            psInsertParametros.setLong(6, parametro.getUsuarioAlteracao());
            psInsertParametros.setString(7, data);
            psInsertParametros.setInt(8, Utilitarios.getHoraNumero(sHora));
            psInsertParametros.execute();
            psInsertParametros.close();
            Conexao.getConnection().commit();
        } catch (SQLException ex) {
            try {
                Conexao.getConnection().rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            try {
                Conexao.getConnection().rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public void updateParametro(Parametro parametro) throws ErroSistemaException {
        try {
            variaveis();
            psUpdateParametros = Conexao.getConnection().prepareStatement(sqlUpdateParametros);
            psUpdateParametros.setString(1, parametro.getValor());
            psUpdateParametros.setString(2, parametro.getDescricao());
            psUpdateParametros.setLong(3, parametro.getUsuarioAlteracao());
            psUpdateParametros.setString(4, data);
            psUpdateParametros.setInt(5, Utilitarios.getHoraNumero(sHora));
            psUpdateParametros.setInt(6, 1);
            psUpdateParametros.setString(7, "0000");
            psUpdateParametros.setString(8, parametro.getParametro());
            psUpdateParametros.execute();
            psUpdateParametros.close();
            Conexao.getConnection().commit();
        } catch (SQLException ex) {
            try {
                Conexao.getConnection().rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            try {
                Conexao.getConnection().rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    private String getWhere(Parametro parametro) {
        String where = "";
        where += ((!parametro.getParametro().equalsIgnoreCase("")) ? " AND UPPER(USU_CHAPAR) LIKE UPPER('" + parametro.getParametro().toUpperCase() + "')" : "");

        where += ((!parametro.getValor().equalsIgnoreCase("")) ? " AND UPPER(USU_VLRPAR) = '" + parametro.getValor().toUpperCase() + "'" : "");

        where += ((!parametro.getDescricao().equalsIgnoreCase("")) ? " AND UPPER(USU_OBSPAR) = '" + parametro.getDescricao().toUpperCase() + "'" : "");

        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
         System.out.println("getWhere = " + where);
        return where;
       
    }

    public Parametro parametro(Parametro parametro) throws ErroSistemaException {
        try {

            sqlSelectParametros = "SELECT USU_CODFLO, \n"
                    + "                   USU_CHAPAR, \n"
                    + "                   USU_VLRPAR, \n"
                    + "                   USU_OBSPAR, \n"
                    + "                   USU_USUGER, \n"
                    + "                   USU_DATGER, \n"
                    + "                   USU_HORGER) \n"
                    + "              FROM USU_T000PGJ" + getWhere(parametro);
            psSelectParametros = Conexao.getConnection().prepareStatement(sqlSelectParametros);
            rsSelectParametros = psSelectParametros.executeQuery();
            while (rsSelectParametros.next()) {
                parametro = new Parametro();
                parametro.setParametro(rsSelectParametros.getString("PARAMETRO"));
                parametro.setValor(rsSelectParametros.getString("VALOR"));
                parametro.setUltimaAtualizacao(rsSelectParametros.getString("ULTIMA_ATUALIZACAO"));
                parametro.setUsuarioAlteracao(rsSelectParametros.getLong("USUARIO_ALTERACAO"));
                parametro.setDescricao(rsSelectParametros.getString("DESCRICAO"));
            }
            return parametro;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public void variaveis() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Cuiaba"));
        hora = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        sHora = hora + ":" + min;
        data = Utilitarios.converteDataString(new Date(), "dd/MM/yyyy");
    }

}
