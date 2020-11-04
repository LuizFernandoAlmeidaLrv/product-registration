/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.integracao.model.dao;

import br.com.martinello.matriz.bd.integracao.control.FiltroControlInt;
import br.com.martinello.matriz.bd.integracao.model.domain.Pendencia;
import br.com.martinello.matriz.bd.model.dao.*;
import br.com.martinello.matriz.bd.transients.FiltroInt;
import br.com.martinello.matriz.util.Utilitarios;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class PendenciaDAOInt {

    private PreparedStatement psSelectPendencia;
    private ResultSet rsSelectPendencia;

    public PendenciaDAOInt() {

    }

    public List<Pendencia> buscarPendenciaIntegrarEcommerce(List<FiltroInt> lFiltroInt) throws ErroSistemaException {
        FiltroControlInt filtroControlInt = new FiltroControlInt();
        List<Pendencia> lPendencia = new LinkedList<>();

        try {
            Pendencia pendencia = new Pendencia();

            String selectPendencia = "SELECT USU_CODPEN,\n"
                    + "                    USU_CHAPEN,\n"
                    + "                    USU_PRCPEN,\n"
                    + "                    USU_OPEPEN,\n"
                    + "                    USU_SITPEN,\n"
                    + "                    USU_TIPPEN,\n"
                    + "                    USU_PRDPEN,\n"
                    + "                    USU_USUGER,\n"
                    + "                    USU_DATGER,\n"
                    + "                    USU_HORGER,\n"
                    + "                    USU_USUALT,\n"
                    + "                    USU_DATALT,\n"
                    + "                    USU_HORALT,\n"
                    + "                    USU_DATPRC,\n"
                    + "                    USU_OBSPEN \n"
                    + "               FROM USU_T000PEN "
                    + filtroControlInt.getMontaFiltro(lFiltroInt)
                    + "              ORDER BY USU_CODPEN DESC ";

            psSelectPendencia = Conexao.getConnection().prepareStatement(selectPendencia);
            rsSelectPendencia = psSelectPendencia.executeQuery();
            while (rsSelectPendencia.next()) {
                pendencia = new Pendencia();
                pendencia.setId(rsSelectPendencia.getLong("USU_CODPEN"));
                pendencia.setChave(rsSelectPendencia.getString("USU_CHAPEN"));
                pendencia.setProcesso(rsSelectPendencia.getString("USU_PRCPEN"));
                pendencia.setOperacao(rsSelectPendencia.getString("USU_OPEPEN"));
                pendencia.setSituacao(rsSelectPendencia.getString("USU_SITPEN"));
                pendencia.setTipo(rsSelectPendencia.getString("USU_TIPPEN"));
                pendencia.setPrioridade(rsSelectPendencia.getLong("USU_PRDPEN"));
                pendencia.setUsuarioGeracao(rsSelectPendencia.getLong("USU_USUGER"));
                pendencia.setDataGeracao(rsSelectPendencia.getDate("USU_DATGER"));
                pendencia.setHoraGeracao(rsSelectPendencia.getLong("USU_HORGER"));
                pendencia.setUsuarioAlteracao(rsSelectPendencia.getLong("USU_USUALT"));
                pendencia.setDataAlteracao(rsSelectPendencia.getDate("USU_DATALT"));
                pendencia.setHoraAlteracao(rsSelectPendencia.getLong("USU_HORALT"));
                pendencia.setDataProcessamento(rsSelectPendencia.getDate("USU_DATPRC"));
                pendencia.setObservacao(rsSelectPendencia.getString("USU_OPEPEN"));

                lPendencia.add(pendencia);
            }
            psSelectPendencia.close();
            rsSelectPendencia.close();
        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lPendencia;
    }

    public void inserirPendencia(Pendencia pendencia) throws ErroSistemaException {
        String inserirPendencia = "INSERT INTO USU_T000PEN (USU_CODPEN,\n"
                + "                                         USU_CHAPEN,\n"
                + "                                         USU_PRCPEN,\n"
                + "                                         USU_OPEPEN,\n"
                + "                                         USU_SITPEN,\n"
                + "                                         USU_TIPPEN,\n"
                + "                                         USU_PRDPEN,\n"
                + "                                         USU_USUGER,\n"
                + "                                         USU_DATGER,\n"
                + "                                         USU_HORGER,\n"
                + "                                         USU_USUALT,\n"
                + "                                         USU_DATALT,\n"
                + "                                         USU_HORALT,\n"
                + "                                         USU_DATPRC,\n"
                + "                                         USU_OBSPEN)\n"
                + "                                  VALUES (SEQ_USU_T000PEN.NEXTVAL, "
                + "                                          ?, "
                + "                                          ?, "
                + "                                          ?, "
                + "                                          ?, "
                + "                                          ?, "
                + "                                          ?, "
                + "                                          ?, "
                + "                                          ?, "
                + "                                          ?, "
                + "                                          ?, "
                + "                                          ?, "
                + "                                          ?, "
                + "                                          ?, "
                + "                                          ?)";
        Connection conexao = Conexao.getConnection();
        try {
            PreparedStatement psInserirPendencia = conexao.prepareStatement(inserirPendencia);
            Calendar cal = new GregorianCalendar();

            psInserirPendencia.setString(1, pendencia.getChave());
            psInserirPendencia.setString(2, pendencia.getProcesso());
            psInserirPendencia.setString(3, pendencia.getOperacao());
            psInserirPendencia.setString(4, pendencia.getSituacao());
            psInserirPendencia.setString(5, pendencia.getTipo());
            psInserirPendencia.setLong(6, pendencia.getPrioridade());
            psInserirPendencia.setLong(7, pendencia.getUsuarioGeracao());
            psInserirPendencia.setDate(8, Utilitarios.converteData(pendencia.getDataGeracao()));
            psInserirPendencia.setLong(9, (cal.get(Calendar.HOUR_OF_DAY) * 60) + cal.get(Calendar.MINUTE));
            psInserirPendencia.setNull(10, Types.INTEGER);
            psInserirPendencia.setNull(11, Types.DATE);
            psInserirPendencia.setNull(12, Types.INTEGER);
            psInserirPendencia.setNull(13, Types.DATE);
            psInserirPendencia.setNull(14, Types.VARCHAR);

            int retorno = psInserirPendencia.executeUpdate();

            if (retorno != 1) {
                conexao.rollback();
                throw new ErroSistemaException("1 - Erro ao realizar a inserção da pendência.");
            } else {
                conexao.commit();
            }

// Hora Inteira para Hora e Minuto.
//            int horas = 1030;
//            int hora = 0;
//            int minuto = 0;
//
//            hora = (horas / 60);
//            minuto = horas - (hora * 60);
//
//            System.out.println(hora + ":" + minuto);
        } catch (ErroSistemaException | SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public void atualizarSituacaoPendencia(Pendencia pendencia) throws ErroSistemaException {

        pendencia.setDataProcessamento(new Date());

        String updatePendencia = "UPDATE sapiens.USU_T000PEN "
                + "                SET USU_SITPEN = ?,"
                + "                    USU_OBSPEN = ?,"
                + "                    USU_DATPRC = ?"
                + "              WHERE USU_CODPEN = ?";
        Connection conexao = Conexao.getConnection();

        try {
            PreparedStatement psUpdatePendencia = conexao.prepareStatement(updatePendencia);

            psUpdatePendencia.setString(1, pendencia.getSituacao());
            psUpdatePendencia.setString(2, pendencia.getObservacao() != null && pendencia.getObservacao().length() > 500 ? pendencia.getObservacao().substring(0, 499) : pendencia.getObservacao());
            psUpdatePendencia.setDate(3, Utilitarios.converteData(pendencia.getDataProcessamento()));
            psUpdatePendencia.setLong(4, pendencia.getId());

            int retorno = psUpdatePendencia.executeUpdate();

            psUpdatePendencia.close();
            if (retorno != 1) {
                conexao.rollback();
                throw new ErroSistemaException("1 - Erro ao realizar a atualização da pendência.");
            } else {
                conexao.commit();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException("2 - Erro ao realizar a atualização da pendência.", ex);
        }
    }
}
