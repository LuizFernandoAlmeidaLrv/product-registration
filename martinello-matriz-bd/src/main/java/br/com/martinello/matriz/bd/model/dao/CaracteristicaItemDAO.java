/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.model.dao;

import br.com.martinello.matriz.bd.model.domain.Caracteristica;
import br.com.martinello.matriz.bd.model.domain.CaracteristicaItem;
import br.com.martinello.matriz.bd.model.domain.Produto;
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
public class CaracteristicaItemDAO {

    private String selectCarItem, insertCarItem, deleteCarItem, updateCarItem;
    private PreparedStatement psSelectCarItem, psInsertCarItem, psUpdateCarItem, psDeleteCarItem;
    private ResultSet rsSelectCarItem, rsInsertCarItem, rsUpdateCarItem, rsDeleteCarItem;
    private List<CaracteristicaItem> lCaracteristicaItem;
    private List<Caracteristica> lCaracteristica;
    private CaracteristicaItem caracteristicaItem;
    private long idCarItem = 0;
    private long idSeqCarItem = 0;
    private int resultado, min, hora;
    private String data, sHora, codPro;

    public CaracteristicaItemDAO() {

    }

    public List<CaracteristicaItem> selectCarItem(CaracteristicaItem caracteristicaItem) throws ErroSistemaException {
        try {
            selectCarItem = "SELECT USU_T075CAP.USU_CODEMP,\n"
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
                    + "       USU_T075CAP.USU_REPITE,\n"
                    + "       USU_T075CAP.USU_USUGER,\n"
                    + "       R999USU.NOMUSU,\n"
                    + "       USU_T075CAP.USU_DATGER,\n"
                    + "       USU_T075CAP.USU_HORGER,\n"
                    + "       USU_T075CAP.USU_USUALT,\n"
                    + "       USU_T075CAP.USU_DATALT,\n"
                    + "       USU_T075CAP.USU_HORALT,\n"
                    + "       USU_T075CAP.USU_OBSALT\n"
                    + "  FROM USU_T075CAP\n"
                    + " INNER JOIN R999USU\n"
                    + "    ON R999USU.CODUSU = USU_T075CAP.USU_USUGER\n"
                    + " INNER JOIN USU_T075CAR\n"
                    + "    ON USU_T075CAP.USU_CODCAR = USU_T075CAR.USU_CODCAR\n"
                    + "   AND USU_T075CAP.USU_CODCAT = USU_T075CAR.USU_CODCAT\n"
                    + "   AND USU_T075CAP.USU_CODSCA = USU_T075CAR.USU_CODSCA\n"
                    + " INNER JOIN USU_T075CAT\n"
                    + "    ON USU_T075CAP.USU_CODCAT = USU_T075CAT.USU_CODCAT\n"
                    + " INNER JOIN USU_T075CAS\n"
                    + "    ON USU_T075CAP.USU_CODCAR = USU_T075CAR.USU_CODCAR\n"
                    + "   AND USU_T075CAP.USU_CODCAT = USU_T075CAS.USU_CODCAT\n"
                    + "   AND USU_T075CAP.USU_CODSCA = USU_T075CAS.USU_CODSCA \n" + getWhere(caracteristicaItem);
            System.out.println("Select:" + selectCarItem);
            psSelectCarItem = Conexao.getConnection().prepareStatement(selectCarItem);
            rsSelectCarItem = psSelectCarItem.executeQuery();
            lCaracteristicaItem = new ArrayList<>();
            while (rsSelectCarItem.next()) {
                caracteristicaItem = new CaracteristicaItem();
                caracteristicaItem.setIdCaracteristica(rsSelectCarItem.getInt("USU_CODCAR"));
                caracteristicaItem.setIdSubCategoria(rsSelectCarItem.getInt("USU_CODSCA"));
                caracteristicaItem.setIdCaracteristicaItem(rsSelectCarItem.getInt("USU_CODCAI"));
                caracteristicaItem.setSeqCatIte(rsSelectCarItem.getInt("USU_SEQCAI"));
                caracteristicaItem.setCaracteristica(rsSelectCarItem.getString("USU_DESCAR"));
                caracteristicaItem.setSubCategoria(rsSelectCarItem.getString("USU_DESSCA"));
                caracteristicaItem.setCaracteristicaItem(rsSelectCarItem.getString("USU_DESCAI"));
                caracteristicaItem.setSituacao(rsSelectCarItem.getString("USU_SITCAI"));
                caracteristicaItem.setVisivel(rsSelectCarItem.getString("USU_VISSIT"));
                caracteristicaItem.setReplicavel(rsSelectCarItem.getString("USU_REPITE"));
                caracteristicaItem.setIdUsuario(rsSelectCarItem.getLong("USU_USUGER"));
                caracteristicaItem.setNomeUsuario(rsSelectCarItem.getString("NOMUSU"));
                caracteristicaItem.setObservacao(rsSelectCarItem.getString("USU_OBSCAI"));
                caracteristicaItem.setDataCadastro(rsSelectCarItem.getDate("USU_DATGER"));
                lCaracteristicaItem.add(caracteristicaItem);
            }
            resultado = psSelectCarItem.executeUpdate();
            if (resultado == -1) {
                psSelectCarItem.close();
                rsSelectCarItem.close();
                return lCaracteristicaItem;
            } else {
                psSelectCarItem.close();
                rsSelectCarItem.close();
                return lCaracteristicaItem;
            }

        } catch (ErroSistemaException | SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public CaracteristicaItem buscarCarItem(String codigo) throws ErroSistemaException {
        try {
            selectCarItem = "SELECT USU_T075CAR.USU_CODEMP,\n"
                    + "       USU_T075CAR.USU_CODCAR,\n"
                    + "       USU_T075CAR.USU_CODSCA,\n"
                    + "       USU_T075CAR.USU_DESCAR,\n"
                    + "       USU_T075CAR.USU_SITCAR,\n"
                    + "       USU_T075CAR.USU_OBSCAR,\n"
                    + "       USU_T075CAR.USU_VISSIT,\n"
                    + "       USU_T075CAR.USU_SEQCAR,\n"
                    + "       USU_T075CAR.USU_USUGER,\n"
                    + "       USU_T075CAR.USU_DATGER,\n"
                    + "       USU_T075CAR.USU_HORGER,\n"
                    + "       USU_T075CAR.USU_USUALT,\n"
                    + "       USU_T075CAR.USU_DATALT,\n"
                    + "       USU_T075CAR.USU_HORALT,\n"
                    + "       USU_T075CAR.USU_OBSALT,\n"
                    + "       R999USU.NOMUSU,\n"
                    + "       USU_T075CAS.USU_DESSCA\n"
                    + "  FROM USU_T075CAR\n"
                    + " INNER JOIN R999USU\n"
                    + "    ON CODUSU = USU_T075CAR.USU_USUGER\n"
                    + " INNER JOIN USU_T075CAS\n"
                    + "    ON USU_T075CAS.USU_CODEMP = USU_T075CAR.USU_CODEMP\n"
                    + "   AND USU_T075CAS.USU_CODSCA = USU_T075CAR.USU_CODSCA \n"
                    + "   WHERE USU_CODCAR = " + codigo;
            psSelectCarItem = Conexao.getConnection().prepareStatement(selectCarItem);
            rsSelectCarItem = psSelectCarItem.executeQuery();
            lCaracteristicaItem = new ArrayList<>();
            if (rsSelectCarItem.next()) {
                caracteristicaItem = new CaracteristicaItem();
                caracteristicaItem.setIdCaracteristica(rsSelectCarItem.getInt("USU_CODCAR"));
                caracteristicaItem.setIdSubCategoria(rsSelectCarItem.getInt("USU_CODSCA"));
                caracteristicaItem.setSeqCatIte(rsSelectCarItem.getInt("USU_SEQCAR"));
                caracteristicaItem.setCaracteristica(rsSelectCarItem.getString("USU_DESCAR"));
                caracteristicaItem.setSubCategoria(rsSelectCarItem.getString("USU_DESSCA"));
                caracteristicaItem.setSituacao(rsSelectCarItem.getString("USU_SITCAR"));
                caracteristicaItem.setVisivel(rsSelectCarItem.getString("USU_VISSIT"));
                caracteristicaItem.setIdUsuario(rsSelectCarItem.getLong("USU_USUGER"));
                caracteristicaItem.setNomeUsuario(rsSelectCarItem.getString("NOMUSU"));
                caracteristicaItem.setObservacao(rsSelectCarItem.getString("USU_OBSCAR"));
                caracteristicaItem.setDataCadastro(rsSelectCarItem.getDate("USU_DATGER"));
            }
            resultado = psSelectCarItem.executeUpdate();
            if (resultado == -1) {
                psSelectCarItem.close();
                rsSelectCarItem.close();
                return caracteristicaItem;
            } else {
                psSelectCarItem.close();
                rsSelectCarItem.close();
                return caracteristicaItem;
            }
        } catch (ErroSistemaException | SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public boolean insertCarItem(CaracteristicaItem caracteristicaItem) throws ErroSistemaException {
        try {
            insertCarItem = " INSERT INTO USU_T075CAR\n"
                    + "              (USU_CODEMP,\n"
                    + "               USU_CODCAR,\n"
                    + "               USU_CODCAT,\n"
                    + "               USU_CODSCA,\n"
                    + "               USU_DESCAR,\n"
                    + "               USU_SITCAR,\n"
                    + "               USU_OBSCAR,\n"
                    + "               USU_VISSIT,\n"
                    + "               USU_SEQCAR,\n"
                    + "               USU_USUGER,\n"
                    + "               USU_DATGER, \n"
                    + "               USU_HORGER)\n"
                    + "               VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            selectCarItem = "SELECT (SELECT NVL(MAX(USU_CODCAR), 0)\n"
                    + "          FROM USU_T075CAR\n"
                    + "         WHERE USU_CODSCA = " + caracteristicaItem.getIdSubCategoria() + "\n"
                    + "           AND USU_CODEMP = 1) USU_CODCAR,\n"
                    + "       (SELECT NVL(MAX(USU_SEQCAR), 0)\n"
                    + "          FROM USU_T075CAR\n"
                    + "         WHERE USU_CODSCA = " + caracteristicaItem.getIdSubCategoria() + " \n"
                    + "           AND USU_CODEMP = 1) USU_SEQCAR\n"
                    + "  FROM DUAL";
            psSelectCarItem = Conexao.getConnection().prepareStatement(selectCarItem);
            rsSelectCarItem = psSelectCarItem.executeQuery();
            if (rsSelectCarItem.next()) {
                idCarItem = rsSelectCarItem.getLong("USU_CODCAR");
                idCarItem = idCarItem + 1;
                idSeqCarItem = rsSelectCarItem.getLong("USU_SEQCAR");
                idSeqCarItem = idSeqCarItem + 1;
            }

            psInsertCarItem = Conexao.getConnection().prepareStatement(insertCarItem);
            psInsertCarItem.setLong(1, 1);
            psInsertCarItem.setLong(2, idCarItem);
            psInsertCarItem.setLong(3, caracteristicaItem.getIdCategoria());
            psInsertCarItem.setLong(4, caracteristicaItem.getIdSubCategoria());
            psInsertCarItem.setString(5, caracteristicaItem.getCaracteristica());
            psInsertCarItem.setString(6, caracteristicaItem.getSituacao());
            psInsertCarItem.setString(7, caracteristicaItem.getObservacao());
            psInsertCarItem.setString(8, caracteristicaItem.getVisivel());
            psInsertCarItem.setLong(9, idSeqCarItem);
            psInsertCarItem.setLong(10, caracteristicaItem.getIdUsuario());
            data = Utilitarios.converteDataString(new Date(), "dd/MM/yyyy");
            psInsertCarItem.setString(11, data);
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Cuiaba"));
            hora = calendar.get(Calendar.HOUR_OF_DAY);
            min = calendar.get(Calendar.MINUTE);
            sHora = hora + ":" + min;
            psInsertCarItem.setInt(12, Utilitarios.getHoraNumero(sHora));
            System.out.println("" + psInsertCarItem.getMetaData());
            resultado = psInsertCarItem.executeUpdate();
            if (resultado == -1) {
                psInsertCarItem.close();
                psSelectCarItem.close();
                Conexao.getConnection().rollback();
                return false;
            } else {
                psInsertCarItem.close();
                psSelectCarItem.close();
                Conexao.getConnection().commit();
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public boolean updateCarItem(CaracteristicaItem caracteristicaItem) throws ErroSistemaException {
        try {
            updateCarItem = "UPDATE USU_T075CAR SET"
                    + "          USU_DESCAR = ?,\n"
                    + "          USU_SITCAR = ?,\n"
                    + "          USU_OBSCAR = ?,\n"
                    + "          USU_CODSCA = ?,\n"
                    + "          USU_USUALT = ?,\n"
                    + "          USU_DATALT = ?,\n"
                    + "          USU_HORALT = ?\n"
                    + "          WHERE \n"
                    + "          USU_CODCAR = ? \n"
                    + "          AND USU_CODEMP = ?";
            psUpdateCarItem = Conexao.getConnection().prepareStatement(updateCarItem);
            psUpdateCarItem.setString(1, caracteristicaItem.getSubCategoria());
            psUpdateCarItem.setString(2, caracteristicaItem.getSituacao());
            psUpdateCarItem.setString(3, caracteristicaItem.getObservacao());
            psUpdateCarItem.setLong(4, caracteristicaItem.getIdCaracteristica());
            psUpdateCarItem.setLong(5, caracteristicaItem.getIdUsuario());
            data = Utilitarios.converteDataString(new Date(), "dd/MM/yyyy");
            psUpdateCarItem.setString(6, data);
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Cuiaba"));
            hora = calendar.get(Calendar.HOUR_OF_DAY);
            min = calendar.get(Calendar.MINUTE);
            sHora = hora + ":" + min;
            psUpdateCarItem.setInt(7, Utilitarios.getHoraNumero(sHora));
            psUpdateCarItem.setLong(8, caracteristicaItem.getIdSubCategoria());
            psUpdateCarItem.setInt(9, 1);

            resultado = psUpdateCarItem.executeUpdate();
            if (resultado == -1) {
                psUpdateCarItem.close();
                psUpdateCarItem.close();
                Conexao.getConnection().rollback();
                return false;
            } else {
                psUpdateCarItem.close();
                psUpdateCarItem.close();
                Conexao.getConnection().commit();
                return true;
            }
        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public String getWhere(CaracteristicaItem caracteristicaItem) {
        String where = "";
        where += ((caracteristicaItem.getIdCategoria() != 0) ? " AND USU_T075CAP.USU_CODCAT = " + caracteristicaItem.getIdCategoria() + "" : "");
        where += ((caracteristicaItem.getIdCaracteristica() != 0) ? " AND USU_T075CAP.USU_CODCAR = " + caracteristicaItem.getIdCaracteristica() + "" : "");
        where += ((caracteristicaItem.getIdSubCategoria() != 0) ? " AND USU_T075CAP.USU_CODSCA = " + caracteristicaItem.getIdSubCategoria() + "" : "");
        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }

    public String validaAltCategoria(String codigo) throws ErroSistemaException {
        try {
            selectCarItem = "SELECT CODPRO\n"
                    + "  FROM E075PRO\n"
                    + " WHERE EXISTS (SELECT 1 FROM E075PRO WHERE USU_CODSCA = " + codigo + ")";
            psSelectCarItem = Conexao.getConnection().prepareStatement(selectCarItem);
            rsSelectCarItem = psSelectCarItem.executeQuery();
            codPro = "";
            while (rsSelectCarItem.next()) {
                if (codPro.equalsIgnoreCase("")) {
                    codPro = rsSelectCarItem.getString("");
                } else {
                    codPro = codPro + " ," + rsSelectCarItem.getString("");
                }

            }
            resultado = psSelectCarItem.executeUpdate();
            if (resultado == -1) {
                psSelectCarItem.close();
                rsSelectCarItem.close();
                return codPro;
            } else {
                psSelectCarItem.close();
                rsSelectCarItem.close();
                return codPro;
            }
        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public List<Caracteristica> listarJcbCarItens(Caracteristica caracteristica) throws ErroSistemaException {
        try {
            lCaracteristica = new ArrayList<>();
            selectCarItem = "SELECT 0 USU_CODSCA, 0 USU_CODCAR, 'TODAS' USU_DESCAR, 'TODAS' USU_DESSCA\n"
                    + "  FROM DUAL\n"
                    + "UNION ALL\n"
                    + "SELECT USU_T075CAR.USU_CODSCA, USU_T075CAR.USU_CODCAR, USU_T075CAR.USU_DESCAR, USU_T075CAS.USU_DESSCA\n"
                    + "  FROM USU_T075CAR\n"
                    + " INNER JOIN USU_T075CAS\n"
                    + "    ON USU_T075CAR.USU_CODSCA = USU_T075CAS.USU_CODSCA\n"
                    + "   AND USU_T075CAR.USU_CODCAT = USU_T075CAS.USU_CODCAT\n" + getWhereJcb(caracteristica) + " ORDER BY USU_CODSCA, USU_CODCAR ASC";
            psSelectCarItem = Conexao.getConnection().prepareStatement(selectCarItem);
            rsSelectCarItem = psSelectCarItem.executeQuery();
            while (rsSelectCarItem.next()) {
                caracteristica = new Caracteristica();
                caracteristica.setIdSubCategoria(rsSelectCarItem.getInt("USU_CODSCA"));
                caracteristica.setIdCaracteristica(rsSelectCarItem.getInt("USU_CODCAR"));
                caracteristica.setCaracteristica(rsSelectCarItem.getString("USU_DESCAR"));
                caracteristica.setSubCategoria(rsSelectCarItem.getString("USU_DESSCA"));
                lCaracteristica.add(caracteristica);
            }
            psSelectCarItem.close();
            rsSelectCarItem.close();
        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

        return lCaracteristica;
    }

    public String getWhereJcb(Caracteristica caracteristica) {
        String where = "";
        where += ((caracteristica.getIdCaracteristica() != 0) ? " AND USU_T075CAR.USU_CODCAR = " + caracteristica.getIdCaracteristica() + "" : "");
        where += ((caracteristica.getIdSubCategoria() != 0) ? " AND USU_T075CAR.USU_CODSCA = " + caracteristica.getIdSubCategoria() + "" : "");
        where += ((caracteristica.getCaracteristica() != null) ? " AND UPPER(USU_T075CAR.USU_DESCAR) LIKE '" + caracteristica.getCaracteristica() + "'" : "");

        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }

    public List<CaracteristicaItem> listarCaracteristicaProduto(Produto produto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
