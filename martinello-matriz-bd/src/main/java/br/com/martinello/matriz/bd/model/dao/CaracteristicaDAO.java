/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.model.dao;

import br.com.martinello.matriz.bd.model.domain.Arquivo;
import br.com.martinello.matriz.bd.model.domain.Caracteristica;
import br.com.martinello.matriz.bd.model.domain.CaracteristicaItem;
import br.com.martinello.matriz.bd.model.domain.Produto;
import br.com.martinello.matriz.util.Utilitarios;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
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
public class CaracteristicaDAO {

    private String selectCaracteristica, selectIdCaracteristica, selectIdCaracteristicaitem, insertCaracteristica, insertCaracteristicaItem,
            deletCaracteristica, updateCaracteristica, updateCaracteristicaItem, selectCarItem;
    private PreparedStatement psSelectCaracteristica, psInsertCaracteristica, psInsertCaracteristicaItem, psUpdateCaracteristica, psUpdateCaracteristicaItem, psDeleteCaracteristica, psSelectCarItem;
    private ResultSet rsSelectCaracteristica, rsInsertCaracteristica, rsInsertCaracteristicaItem, rsUpdateCaracteristica, rsUpdateCaracteristicaItem, rsDeleteCaracteristica, rsSelectCarItem;
    private List<Caracteristica> lCaracteristica;
    private List<CaracteristicaItem> lCaracteristicaItem;
    private List<Produto> lProduto;
    private List<Arquivo> lAqruivos;
    private Produto produto;
    private ProdutoDAO produtoDAO;
    private Caracteristica caracteristica;
    private CaracteristicaItem caracteristicaItem;
    private int idCaracteristica = 0, idSeqCar = 0, idCarItem = 0, idSeqCarItem = 0;
    private int resultado, min, hora;
    private String data, sHora, codPro;

    public CaracteristicaDAO() {

    }

    public String getInsertCaracteristica() {
        insertCaracteristica = " INSERT INTO USU_T075CAR\n"
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
                + "               USU_HORGER, \n"
                + "               USU_REGUNI)\n"
                + "               VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return insertCaracteristica;
    }

    public String getInsertCaracteristicaItem() {
        insertCaracteristicaItem = " INSERT INTO USU_T075CAP\n"
                + "              (USU_CODEMP,\n"
                + "               USU_CODCAT,\n"
                + "               USU_CODSCA,\n"
                + "               USU_CODCAR,\n"
                + "               USU_CODCAI,\n"
                + "               USU_DESCAI,\n"
                + "               USU_SITCAI,\n"
                + "               USU_OBSCAI,\n"
                + "               USU_VISSIT,\n"
                + "               USU_REPITE,\n"
                + "               USU_SEQCAI,\n"
                + "               USU_USUGER,\n"
                + "               USU_DATGER, \n"
                + "               USU_HORGER)\n"
                + "               VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return insertCaracteristicaItem;
    }

    public String getUpdateCaracteristica() {
        updateCaracteristica = "UPDATE USU_T075CAR SET"
                + "          USU_SEQCAR = ?,\n"
                + "          USU_DESCAR = ?,\n"
                + "          USU_SITCAR = ?,\n"
                + "          USU_OBSCAR = ?,\n"
                + "          USU_USUALT = ?,\n"
                + "          USU_DATALT = ?,\n"
                + "          USU_HORALT = ?\n"                
                + "          WHERE USU_CODCAT = ? \n"
                + "          AND USU_CODSCA = ? \n"
                + "          AND USU_CODCAR = ? \n"
                + "          AND USU_CODEMP = ?";

        return updateCaracteristica;
    }

    public String getUpdateCaracteristicaItem() {
        updateCaracteristicaItem = "UPDATE USU_T075CAP SET"
                + "          USU_SEQCAI = ?,\n"
                + "          USU_DESCAI = ?,\n"
                + "          USU_SITCAI = ?,\n"
                + "          USU_VISSIT = ?,\n"
                + "          USU_REPITE = ?,\n"
                + "          USU_OBSCAI = ?,\n"
                + "          USU_USUALT = ?,\n"
                + "          USU_DATALT = ?,\n"
                + "          USU_HORALT = ?\n"
                + "          WHERE USU_CODCAT = ? \n"
                + "          AND USU_CODSCA = ? \n"
                + "          AND USU_CODCAR = ? \n"
                + "          AND USU_CODCAI = ? \n"
                + "          AND USU_CODEMP = ?";

        return updateCaracteristicaItem;
    }

    public String getSelectIdCaracteristica(Caracteristica caracteristica) {
        selectIdCaracteristica = "SELECT (SELECT NVL(MAX(USU_CODCAR), 0)\n"
                + "                 FROM USU_T075CAR\n"
                + "                WHERE USU_CODCAT = " + caracteristica.getIdCategoria() + "\n"
                + "                  AND USU_CODSCA = " + caracteristica.getIdSubCategoria() + "\n"
                + "                  AND USU_CODEMP = 1) USU_CODCAR,\n"
                + "              (SELECT NVL(MAX(USU_SEQCAR), 0)\n"
                + "                 FROM USU_T075CAR\n"
                + "                WHERE USU_CODCAT = " + caracteristica.getIdCategoria() + "\n"
                + "                  AND USU_CODSCA = " + caracteristica.getIdSubCategoria() + "\n"
                + "                  AND USU_CODEMP = 1) USU_SEQCAR\n"
                + "                 FROM DUAL";
        return selectIdCaracteristica;
    }

    public String getSelectIdCaracteristicaItem(CaracteristicaItem caracteristicaItem) {
        selectIdCaracteristicaitem = "SELECT (SELECT NVL(MAX(USU_CODCAI), 0)\n"
                + "                     FROM USU_T075CAP\n"
                + "                    WHERE USU_CODCAT = " + caracteristicaItem.getIdCategoria() + "\n"
                + "                      AND USU_CODSCA = " + caracteristicaItem.getIdSubCategoria() + "\n"
                + "                      AND USU_CODCAR = " + caracteristicaItem.getIdCaracteristica() + "\n"
                + "                      AND USU_CODEMP = 1) USU_CODCAI,\n"
                + "                  (SELECT NVL(MAX(USU_SEQCAI), 0)\n"
                + "                     FROM USU_T075CAP\n"
                + "                    WHERE USU_CODCAT = " + caracteristicaItem.getIdCategoria() + "\n"
                + "                      AND USU_CODSCA = " + caracteristicaItem.getIdSubCategoria() + "\n"
                + "                      AND USU_CODCAR = " + caracteristicaItem.getIdCaracteristica() + "\n"
                + "                      AND USU_CODEMP = 1) USU_SEQCAI\n"
                + "                     FROM DUAL";
        return selectIdCaracteristicaitem;
    }

    public List<Caracteristica> selectCaracteristicas(Caracteristica caracteristica) throws ErroSistemaException {
        try {
            selectCaracteristica = "SELECT USU_T075CAR.USU_CODEMP,\n"
                    + "       USU_T075CAR.USU_CODCAR,\n"
                    + "       USU_T075CAR.USU_CODCAT,\n"
                    + "       USU_T075CAR.USU_CODSCA,\n"
                    + "       USU_T075CAR.USU_DESCAR,\n"
                    + "       USU_T075CAR.USU_SITCAR,\n"
                    + "       USU_T075CAR.USU_OBSCAR,\n"
                    + "       USU_T075CAR.USU_VISSIT,\n"
                    + "       USU_T075CAR.USU_SEQCAR,\n"
                    + "       USU_T075CAR.USU_USUGER,\n"
                    + "       USU_T075CAR.USU_REGUNI,\n"
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
                    + "   AND USU_T075CAS.USU_CODSCA = USU_T075CAR.USU_CODSCA\n"
                    + "   AND USU_T075CAS.USU_CODCAT = USU_T075CAR.USU_CODCAT\n" + getWhere(caracteristica);

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
                    + " INNER JOIN USU_T075CAT\n"
                    + "    ON USU_T075CAP.USU_CODCAT = USU_T075CAT.USU_CODCAT\n"
                    + " INNER JOIN USU_T075CAR\n"
                    + "    ON USU_T075CAP.USU_CODCAR = USU_T075CAR.USU_CODCAR\n"
                    + "   AND USU_T075CAP.USU_CODCAT = USU_T075CAR.USU_CODCAT\n"
                    + "   AND USU_T075CAP.USU_CODSCA = USU_T075CAR.USU_CODSCA\n"
                    + " INNER JOIN USU_T075CAS\n"
                    + "    ON USU_T075CAR.USU_CODCAT = USU_T075CAS.USU_CODCAT\n"
                    + "   AND USU_T075CAR.USU_CODSCA = USU_T075CAS.USU_CODSCA\n";
            System.out.println("Select:" + selectCaracteristica);
            psSelectCaracteristica = Conexao.getConnection().prepareStatement(selectCaracteristica);
            rsSelectCaracteristica = psSelectCaracteristica.executeQuery();
            lCaracteristica = new LinkedList<>();
            while (rsSelectCaracteristica.next()) {
                caracteristica = new Caracteristica();
                caracteristicaItem = new CaracteristicaItem();
                caracteristicaItem.setIdCategoria(rsSelectCaracteristica.getInt("USU_CODCAT"));
                caracteristicaItem.setIdSubCategoria(rsSelectCaracteristica.getInt("USU_CODSCA"));
                caracteristicaItem.setIdCaracteristica(rsSelectCaracteristica.getInt("USU_CODCAR"));
                psSelectCarItem = Conexao.getConnection().prepareStatement(selectCarItem + getWhereCarItem(caracteristicaItem));
                rsSelectCarItem = psSelectCarItem.executeQuery();
                lCaracteristicaItem = new LinkedList<>();
                System.out.println("Select Item:" + selectCarItem + getWhereCarItem(caracteristicaItem));
                while (rsSelectCarItem.next()) {
                    caracteristicaItem = new CaracteristicaItem();
                    caracteristicaItem.setIdCaracteristica(rsSelectCarItem.getInt("USU_CODCAR"));
                    caracteristicaItem.setIdCategoria(rsSelectCarItem.getInt("USU_CODCAT"));
                    caracteristicaItem.setIdSubCategoria(rsSelectCarItem.getInt("USU_CODSCA"));
                    caracteristicaItem.setIdCaracteristicaItem(rsSelectCarItem.getInt("USU_CODCAI"));
                    caracteristicaItem.setSeqCatIte(rsSelectCarItem.getInt("USU_SEQCAI"));
                    caracteristicaItem.setCaracteristica(rsSelectCarItem.getString("USU_DESCAR"));
                    caracteristicaItem.setCaracteristicaItem(rsSelectCarItem.getString("USU_DESCAI"));
                    caracteristicaItem.setSubCategoria(rsSelectCarItem.getString("USU_DESSCA"));
                    caracteristicaItem.setSituacao(rsSelectCarItem.getString("USU_SITCAI"));
                    caracteristicaItem.setVisivel(rsSelectCarItem.getString("USU_VISSIT"));
                    caracteristicaItem.setReplicavel(rsSelectCarItem.getString("USU_REPITE"));
                    caracteristicaItem.setIdUsuario(rsSelectCarItem.getLong("USU_USUGER"));
                    caracteristicaItem.setNomeUsuario(rsSelectCarItem.getString("NOMUSU"));
                    caracteristicaItem.setObservacao(rsSelectCarItem.getString("USU_OBSCAI"));
                    caracteristicaItem.setDataCadastro(rsSelectCarItem.getDate("USU_DATGER"));
                    caracteristicaItem.setAlterado("N");
                    lCaracteristicaItem.add(caracteristicaItem);
                }
                caracteristica.setIdCaracteristica(rsSelectCaracteristica.getInt("USU_CODCAR"));
                caracteristica.setIdCategoria(rsSelectCaracteristica.getInt("USU_CODCAT"));
                caracteristica.setIdSubCategoria(rsSelectCaracteristica.getInt("USU_CODSCA"));
                caracteristica.setSeqCategoria(rsSelectCaracteristica.getInt("USU_SEQCAR"));
                caracteristica.setCaracteristica(rsSelectCaracteristica.getString("USU_DESCAR"));
                caracteristica.setSubCategoria(rsSelectCaracteristica.getString("USU_DESSCA"));
                caracteristica.setSituacao(rsSelectCaracteristica.getString("USU_SITCAR"));
                caracteristica.setVisivel(rsSelectCaracteristica.getString("USU_VISSIT"));
                caracteristica.setUnico(rsSelectCaracteristica.getString("USU_REGUNI"));
                caracteristica.setIdUsuario(rsSelectCaracteristica.getLong("USU_USUGER"));
                caracteristica.setNomeUsuario(rsSelectCaracteristica.getString("NOMUSU"));
                caracteristica.setObsCaracteristica(rsSelectCaracteristica.getString("USU_OBSCAR"));
                caracteristica.setDataCadastro(rsSelectCaracteristica.getDate("USU_DATGER"));
                caracteristica.setCaracteristicaItens(lCaracteristicaItem);
                caracteristica.setAlterado("N");
                lCaracteristica.add(caracteristica);
            }
            resultado = psSelectCaracteristica.executeUpdate();
            if (resultado == -1) {
                psSelectCaracteristica.close();
                rsSelectCaracteristica.close();
                return lCaracteristica;
            } else {
                psSelectCaracteristica.close();
                rsSelectCaracteristica.close();
                return lCaracteristica;
            }

        } catch (ErroSistemaException | SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public List<Caracteristica> selectCaracteristica(Caracteristica caracteristica) throws ErroSistemaException {
        try {
            selectCaracteristica = "SELECT USU_T075CAR.USU_CODEMP,\n"
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
                    + "    ON USU_T075CAR.USU_CODEMP = USU_T075CAS.USU_CODEMP\n"
                    + "   AND USU_T075CAR.USU_CODCAT = USU_T075CAS.USU_CODCAT\n"
                    + "   AND USU_T075CAR.USU_CODSCA = USU_T075CAS.USU_CODSCA\n" + getWhere(caracteristica);
            System.out.println("Select:" + selectCaracteristica);
            psSelectCaracteristica = Conexao.getConnection().prepareStatement(selectCaracteristica);
            rsSelectCaracteristica = psSelectCaracteristica.executeQuery();
            lCaracteristica = new ArrayList<>();
            while (rsSelectCaracteristica.next()) {
                caracteristica = new Caracteristica();
                caracteristica.setIdCaracteristica(rsSelectCaracteristica.getInt("USU_CODCAR"));
                caracteristica.setIdSubCategoria(rsSelectCaracteristica.getInt("USU_CODSCA"));
                caracteristica.setSeqCategoria(rsSelectCaracteristica.getInt("USU_SEQCAR"));
                caracteristica.setCaracteristica(rsSelectCaracteristica.getString("USU_DESCAR"));
                caracteristica.setSubCategoria(rsSelectCaracteristica.getString("USU_DESSCA"));
                caracteristica.setSituacao(rsSelectCaracteristica.getString("USU_SITCAR"));
                caracteristica.setVisivel(rsSelectCaracteristica.getString("USU_VISSIT"));
                caracteristica.setIdUsuario(rsSelectCaracteristica.getLong("USU_USUGER"));
                caracteristica.setNomeUsuario(rsSelectCaracteristica.getString("NOMUSU"));
                caracteristica.setObsCaracteristica(rsSelectCaracteristica.getString("USU_OBSCAR"));
                caracteristica.setDataCadastro(rsSelectCaracteristica.getDate("USU_DATGER"));
                lCaracteristica.add(caracteristica);
            }
            resultado = psSelectCaracteristica.executeUpdate();
            if (resultado == -1) {
                psSelectCaracteristica.close();
                rsSelectCaracteristica.close();
                return lCaracteristica;
            } else {
                psSelectCaracteristica.close();
                rsSelectCaracteristica.close();
                return lCaracteristica;
            }

        } catch (ErroSistemaException | SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public Caracteristica buscarCaracteristica(String codigo) throws ErroSistemaException {
        try {
            selectCaracteristica = "SELECT USU_T075CAR.USU_CODEMP,\n"
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
            psSelectCaracteristica = Conexao.getConnection().prepareStatement(selectCaracteristica);
            rsSelectCaracteristica = psSelectCaracteristica.executeQuery();
            lCaracteristica = new ArrayList<>();
            if (rsSelectCaracteristica.next()) {
                caracteristica = new Caracteristica();
                caracteristica.setIdCaracteristica(rsSelectCaracteristica.getInt("USU_CODCAR"));
                caracteristica.setIdSubCategoria(rsSelectCaracteristica.getInt("USU_CODSCA"));
                caracteristica.setSeqCategoria(rsSelectCaracteristica.getInt("USU_SEQCAR"));
                caracteristica.setCaracteristica(rsSelectCaracteristica.getString("USU_DESCAR"));
                caracteristica.setSubCategoria(rsSelectCaracteristica.getString("USU_DESSCA"));
                caracteristica.setSituacao(rsSelectCaracteristica.getString("USU_SITCAR"));
                caracteristica.setVisivel(rsSelectCaracteristica.getString("USU_VISSIT"));
                caracteristica.setIdUsuario(rsSelectCaracteristica.getLong("USU_USUGER"));
                caracteristica.setNomeUsuario(rsSelectCaracteristica.getString("NOMUSU"));
                caracteristica.setObsCaracteristica(rsSelectCaracteristica.getString("USU_OBSCAR"));
                caracteristica.setDataCadastro(rsSelectCaracteristica.getDate("USU_DATGER"));
            }
            resultado = psSelectCaracteristica.executeUpdate();
            if (resultado == -1) {
                psSelectCaracteristica.close();
                rsSelectCaracteristica.close();
                return caracteristica;
            } else {
                psSelectCaracteristica.close();
                rsSelectCaracteristica.close();
                return caracteristica;
            }

        } catch (ErroSistemaException | SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

    }

    public String getWhere(Caracteristica caracteristica) {
        String where = "";
        where += ((caracteristica.getIdCaracteristica() != 0) ? " AND USU_T075CAR.USU_CODCAR = " + caracteristica.getIdCaracteristica() + "" : "");
        where += ((caracteristica.getIdCategoria() != 0) ? " AND USU_T075CAR.USU_CODCAT = " + caracteristica.getIdCategoria() + "" : "");
        where += ((caracteristica.getIdSubCategoria() != 0) ? " AND USU_T075CAR.USU_CODSCA = " + caracteristica.getIdSubCategoria() + "" : "");
        where += ((caracteristica.getCaracteristica() != null) ? " AND UPPER(USU_T075CAR.USU_DESCAR) LIKE '" + caracteristica.getCaracteristica() + "'" : "");
        where += ((!caracteristica.getSituacao().trim().equalsIgnoreCase("")) ? " AND USU_T075CAR.USU_SITCAR IN (" + caracteristica.getSituacao() + ")" : "");
        if (!where.isEmpty()) {
            where = " WHERE  0 = 0 " + where;
        }
        return where;
    }

    public String getWhereCarItem(CaracteristicaItem caracteristicaItem) {
        String where = "";
        where += ((caracteristicaItem.getIdCategoria() != 0) ? " AND USU_T075CAP.USU_CODCAT = " + caracteristicaItem.getIdCategoria() + "" : "");
        where += ((caracteristicaItem.getIdCaracteristica() != 0) ? " AND USU_T075CAP.USU_CODCAR = " + caracteristicaItem.getIdCaracteristica() + "" : "");
        where += ((caracteristicaItem.getIdSubCategoria() != 0) ? " AND USU_T075CAP.USU_CODSCA = " + caracteristicaItem.getIdSubCategoria() + "" : "");
        //  where += ((caracteristicaItem.getCaracteristicaItem() != null) ? " AND UPPER(USU_T075CAP.USU_DESCAI) LIKE '" + caracteristicaItem.getCaracteristicaItem() + "'" : "");

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

    public String validaAltCategoria(String codigo) throws ErroSistemaException {
        try {
            selectCaracteristica = "SELECT CODPRO\n"
                    + "  FROM E075PRO\n"
                    + " WHERE EXISTS (SELECT 1 FROM E075PRO WHERE USU_CODSCA = " + codigo + ")";
            psSelectCaracteristica = Conexao.getConnection().prepareStatement(selectCaracteristica);
            rsSelectCaracteristica = psSelectCaracteristica.executeQuery();
            codPro = "";
            while (rsSelectCaracteristica.next()) {
                if (codPro.equalsIgnoreCase("")) {
                    codPro = rsSelectCaracteristica.getString("");
                } else {
                    codPro = codPro + " ," + rsSelectCaracteristica.getString("");
                }

            }
            resultado = psSelectCaracteristica.executeUpdate();
            if (resultado == -1) {
                psSelectCaracteristica.close();
                rsSelectCaracteristica.close();
                return codPro;
            } else {
                psSelectCaracteristica.close();
                rsSelectCaracteristica.close();
                return codPro;
            }
        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

    public void insertCaracteristicas(List<Caracteristica> lcaracteristica) throws ErroSistemaException {
        Connection connection = Conexao.getConnection();
        try {
            connection.setAutoCommit(false);

            psInsertCaracteristica = connection.prepareStatement(getInsertCaracteristica());
            psInsertCaracteristicaItem = connection.prepareStatement(getInsertCaracteristicaItem());
            variaveis();
            /**
             * Percorre a lista de Caracteristica.
             */
            for (Caracteristica caracteristica : lcaracteristica) {

                psSelectCaracteristica = connection.prepareStatement(getSelectIdCaracteristica(caracteristica));
                rsSelectCaracteristica = psSelectCaracteristica.executeQuery();
                if (rsSelectCaracteristica.next()) {
                    idCaracteristica = rsSelectCaracteristica.getInt("USU_CODCAR");
                    idCaracteristica = idCaracteristica + 1;
                    idSeqCar = rsSelectCaracteristica.getInt("USU_SEQCAR");
                    idSeqCar = idSeqCar + 1;
                }
                psSelectCaracteristica.close();
                rsSelectCaracteristica.close();

                psInsertCaracteristica.setInt(1, 1);
                psInsertCaracteristica.setInt(2, idCaracteristica);
                psInsertCaracteristica.setLong(3, caracteristica.getIdCategoria());
                psInsertCaracteristica.setLong(4, caracteristica.getIdSubCategoria());
                psInsertCaracteristica.setString(5, caracteristica.getCaracteristica());
                psInsertCaracteristica.setString(6, caracteristica.getSituacao());
                psInsertCaracteristica.setString(7, caracteristica.getObsCaracteristica());
                psInsertCaracteristica.setString(8, caracteristica.getVisivel());
                psInsertCaracteristica.setInt(9, idSeqCar);
                psInsertCaracteristica.setLong(10, caracteristica.getIdUsuario());
                psInsertCaracteristica.setString(11, data);
                psInsertCaracteristica.setInt(12, Utilitarios.getHoraNumero(sHora));
                psInsertCaracteristica.setString(13, caracteristica.getUnico());
                psInsertCaracteristica.execute();
                /**
                 * Percorre a lista de de CaracteristicaItens
                 */
                for (CaracteristicaItem caracteristicaItem : caracteristica.getCaracteristicaItens()) {
                    caracteristicaItem.setIdCaracteristica(idCaracteristica);
                    psSelectCarItem = connection.prepareStatement(getSelectIdCaracteristicaItem(caracteristicaItem));
                    rsSelectCarItem = psSelectCarItem.executeQuery();
                    if (rsSelectCarItem.next()) {
                        idCarItem = rsSelectCarItem.getInt("USU_CODCAI");
                        idCarItem = idCarItem + 1;
                        idSeqCarItem = rsSelectCarItem.getInt("USU_SEQCAI");
                        idSeqCarItem = idSeqCarItem + 1;
                    }
                    psSelectCarItem.close();
                    rsSelectCarItem.close();

                    psInsertCaracteristicaItem.setInt(1, 1);
                    psInsertCaracteristicaItem.setInt(2, caracteristicaItem.getIdCategoria());
                    psInsertCaracteristicaItem.setLong(3, caracteristicaItem.getIdSubCategoria());
                    psInsertCaracteristicaItem.setLong(4, caracteristicaItem.getIdCaracteristica());
                    psInsertCaracteristicaItem.setLong(5, idCarItem);
                    psInsertCaracteristicaItem.setString(6, caracteristicaItem.getCaracteristicaItem());
                    psInsertCaracteristicaItem.setString(7, caracteristicaItem.getSituacao());
                    psInsertCaracteristicaItem.setString(8, caracteristicaItem.getObservacao());
                    psInsertCaracteristicaItem.setString(9, caracteristicaItem.getVisivel());
                    psInsertCaracteristicaItem.setString(10, caracteristicaItem.getReplicavel());
                    psInsertCaracteristicaItem.setInt(11, caracteristicaItem.getSeqCatIte());
                    psInsertCaracteristicaItem.setLong(12, caracteristicaItem.getIdUsuario());
                    psInsertCaracteristicaItem.setString(13, data);
                    psInsertCaracteristicaItem.setInt(14, Utilitarios.getHoraNumero(sHora));
                    psInsertCaracteristicaItem.execute();

                }
            }

            connection.commit();
        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                connection.rollback();
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            } catch (SQLException ex1) {
                throw new ErroSistemaException(ex1.getMessage(), ex1.getCause());
            }
        } finally {
            try {
                psInsertCaracteristica.close();
                psInsertCaracteristicaItem.close();
            } catch (SQLException ex) {
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
        }

    }

    public void updateCaracteristica(List<Caracteristica> lcaracteristica) throws ErroSistemaException {
        Connection connection = Conexao.getConnection();
        try {
            connection.setAutoCommit(false);
            /**
             * Percorre a lista de Caracteristica.
             */
            psInsertCaracteristica = connection.prepareStatement(getInsertCaracteristica());
            psUpdateCaracteristica = connection.prepareStatement(getUpdateCaracteristica());
            psInsertCaracteristicaItem = connection.prepareStatement(getInsertCaracteristicaItem());
            psUpdateCaracteristicaItem = connection.prepareStatement(getUpdateCaracteristicaItem());
            variaveis();
            for (Caracteristica caracteristica : lcaracteristica) {
                if (caracteristica.getIdCaracteristica() == 0) {
                    psSelectCaracteristica = connection.prepareStatement(getSelectIdCaracteristica(caracteristica));
                    rsSelectCaracteristica = psSelectCaracteristica.executeQuery();
                    if (rsSelectCaracteristica.next()) {
                        idCaracteristica = rsSelectCaracteristica.getInt("USU_CODCAR");
                        idCaracteristica = idCaracteristica + 1;
                        idSeqCar = rsSelectCaracteristica.getInt("USU_SEQCAR");
                        idSeqCar = idSeqCar + 1;
                    }
                    psSelectCaracteristica.close();
                    rsSelectCaracteristica.close();

                    psInsertCaracteristica.setInt(1, 1);
                    psInsertCaracteristica.setInt(2, idCaracteristica);
                    psInsertCaracteristica.setLong(3, caracteristica.getIdCategoria());
                    psInsertCaracteristica.setLong(4, caracteristica.getIdSubCategoria());
                    psInsertCaracteristica.setString(5, caracteristica.getCaracteristica());
                    psInsertCaracteristica.setString(6, caracteristica.getSituacao());
                    psInsertCaracteristica.setString(7, caracteristica.getObsCaracteristica());
                    psInsertCaracteristica.setString(8, caracteristica.getVisivel());
                    psInsertCaracteristica.setInt(9, idSeqCar);
                    psInsertCaracteristica.setLong(10, caracteristica.getIdUsuario());
                    psInsertCaracteristica.setString(11, data);
                    psInsertCaracteristica.setInt(12, Utilitarios.getHoraNumero(sHora));
                    psInsertCaracteristica.setString(13, caracteristica.getUnico());
                    psInsertCaracteristica.execute();
                    /**
                     * Percorre a lista de de CaracteristicaItens
                     */
                    for (CaracteristicaItem caracteristicaItem : caracteristica.getCaracteristicaItens()) {
                        caracteristicaItem.setIdCaracteristica(idCaracteristica);
                        psSelectCarItem = connection.prepareStatement(getSelectIdCaracteristicaItem(caracteristicaItem));
                        rsSelectCarItem = psSelectCarItem.executeQuery();
                        if (rsSelectCarItem.next()) {
                            idCarItem = rsSelectCarItem.getInt("USU_CODCAI");
                            idCarItem = idCarItem + 1;
                            idSeqCarItem = rsSelectCarItem.getInt("USU_SEQCAI");
                            idSeqCarItem = idSeqCarItem + 1;
                            caracteristicaItem.setIdCaracteristicaItem(idCarItem);
                        }
                        psSelectCarItem.close();
                        rsSelectCarItem.close();

                        psInsertCaracteristicaItem.setInt(1, 1);
                        psInsertCaracteristicaItem.setInt(2, caracteristicaItem.getIdCategoria());
                        psInsertCaracteristicaItem.setLong(3, caracteristicaItem.getIdSubCategoria());
                        psInsertCaracteristicaItem.setLong(4, caracteristicaItem.getIdCaracteristica());
                        psInsertCaracteristicaItem.setLong(5, idCarItem);
                        psInsertCaracteristicaItem.setString(6, caracteristicaItem.getCaracteristicaItem());
                        psInsertCaracteristicaItem.setString(7, caracteristicaItem.getSituacao());
                        psInsertCaracteristicaItem.setString(8, caracteristicaItem.getObservacao());
                        psInsertCaracteristicaItem.setString(9, caracteristicaItem.getVisivel());
                        psInsertCaracteristicaItem.setString(10, caracteristicaItem.getReplicavel());
                        psInsertCaracteristicaItem.setInt(11, caracteristicaItem.getSeqCatIte());
                        psInsertCaracteristicaItem.setLong(12, caracteristicaItem.getIdUsuario());
                        psInsertCaracteristicaItem.setString(13, data);
                        psInsertCaracteristicaItem.setInt(14, Utilitarios.getHoraNumero(sHora));
                        psInsertCaracteristicaItem.execute();

                        inserirDescricaoProduto(caracteristicaItem, connection);
                    }

                } else {
                    if (caracteristica.getAlterado().equalsIgnoreCase("S")) {
                        psUpdateCaracteristica.setInt(1, caracteristica.getSeqCategoria());
                        psUpdateCaracteristica.setString(2, caracteristica.getCaracteristica());
                        psUpdateCaracteristica.setString(3, caracteristica.getSituacao());
                        psUpdateCaracteristica.setString(4, caracteristica.getObsCaracteristica());
                        psUpdateCaracteristica.setLong(5, caracteristica.getIdUsuario());
                        psUpdateCaracteristica.setString(6, data);
                        psUpdateCaracteristica.setInt(7, Utilitarios.getHoraNumero(sHora));
                        psUpdateCaracteristica.setLong(8, caracteristica.getIdCategoria());
                        psUpdateCaracteristica.setLong(9, caracteristica.getIdSubCategoria());
                        psUpdateCaracteristica.setLong(10, caracteristica.getIdCaracteristica());
                        psUpdateCaracteristica.setInt(11, 1);
                        psUpdateCaracteristica.execute();
                    }
                    for (CaracteristicaItem caracteristicaItem : caracteristica.getCaracteristicaItens()) {
                        if (caracteristicaItem.getAlterado().equalsIgnoreCase("S")) {
                            psUpdateCaracteristicaItem.setInt(1, caracteristicaItem.getSeqCatIte());
                            psUpdateCaracteristicaItem.setString(2, caracteristicaItem.getCaracteristicaItem());
                            psUpdateCaracteristicaItem.setString(3, caracteristicaItem.getSituacao());
                            psUpdateCaracteristicaItem.setString(4, caracteristicaItem.getVisivel());
                            psUpdateCaracteristicaItem.setString(5, caracteristicaItem.getReplicavel());
                            psUpdateCaracteristicaItem.setString(6, caracteristicaItem.getObservacao());
                            psUpdateCaracteristicaItem.setLong(7, caracteristicaItem.getIdUsuario());
                            psUpdateCaracteristicaItem.setString(8, data);
                            psUpdateCaracteristicaItem.setInt(9, Utilitarios.getHoraNumero(sHora));
                            psUpdateCaracteristicaItem.setLong(10, caracteristicaItem.getIdCategoria());
                            psUpdateCaracteristicaItem.setLong(11, caracteristicaItem.getIdSubCategoria());
                            psUpdateCaracteristicaItem.setLong(12, caracteristicaItem.getIdCaracteristica());
                            psUpdateCaracteristicaItem.setLong(13, caracteristicaItem.getIdCaracteristicaItem());
                            psUpdateCaracteristicaItem.setInt(14, 1);
                            psUpdateCaracteristicaItem.execute();
                        } else if (caracteristicaItem.getIdCaracteristicaItem() == 0) {
                            psSelectCarItem = connection.prepareStatement(getSelectIdCaracteristicaItem(caracteristicaItem));
                            rsSelectCarItem = psSelectCarItem.executeQuery();
                            if (rsSelectCarItem.next()) {
                                idCarItem = rsSelectCarItem.getInt("USU_CODCAI");
                                idCarItem = idCarItem + 1;
                                idSeqCarItem = rsSelectCarItem.getInt("USU_SEQCAI");
                                idSeqCarItem = idSeqCarItem + 1;
                                caracteristicaItem.setIdCaracteristicaItem(idCarItem);
                            }
                            psSelectCarItem.close();
                            rsSelectCarItem.close();

                            psInsertCaracteristicaItem.setInt(1, 1);
                            psInsertCaracteristicaItem.setInt(2, caracteristicaItem.getIdCategoria());
                            psInsertCaracteristicaItem.setLong(3, caracteristicaItem.getIdSubCategoria());
                            psInsertCaracteristicaItem.setLong(4, caracteristicaItem.getIdCaracteristica());
                            psInsertCaracteristicaItem.setLong(5, idCarItem);
                            psInsertCaracteristicaItem.setString(6, caracteristicaItem.getCaracteristicaItem());
                            psInsertCaracteristicaItem.setString(7, caracteristicaItem.getSituacao());
                            psInsertCaracteristicaItem.setString(8, caracteristicaItem.getObservacao());
                            psInsertCaracteristicaItem.setString(9, caracteristicaItem.getVisivel());
                            psInsertCaracteristicaItem.setString(10, caracteristicaItem.getReplicavel());
                            psInsertCaracteristicaItem.setInt(11, caracteristicaItem.getSeqCatIte());
                            psInsertCaracteristicaItem.setLong(12, caracteristicaItem.getIdUsuario());
                            psInsertCaracteristicaItem.setString(13, data);
                            psInsertCaracteristicaItem.setInt(14, Utilitarios.getHoraNumero(sHora));
                            psInsertCaracteristicaItem.execute();
                            inserirDescricaoProduto(caracteristicaItem, connection);
                        }
                    }
                }
            }
            connection.commit();
        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                connection.rollback();
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            } catch (SQLException ex1) {
                throw new ErroSistemaException(ex1.getMessage(), ex1.getCause());
            }
        } finally {
            try {
                psInsertCaracteristica.close();
                psInsertCaracteristicaItem.close();
                psUpdateCaracteristica.close();
                psUpdateCaracteristicaItem.close();
            } catch (SQLException ex) {
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
        }
    }

    private void inserirDescricaoProduto(CaracteristicaItem caracteristicaItem, Connection connection) throws ErroSistemaException {
        try {

            String selectProduto = "SELECT USU_CODPRO FROM USU_T075CAD \n"
                    + "              WHERE USU_CODCAT = " + caracteristicaItem.getIdCategoria() + " \n"
                    + "                AND USU_CODSCA = " + caracteristicaItem.getIdSubCategoria() + " \n "
                    + "              GROUP BY USU_CODPRO";
            psSelectCaracteristica = connection.prepareStatement(selectProduto);
            rsSelectCaracteristica = psSelectCaracteristica.executeQuery();
            while (rsSelectCaracteristica.next()) {
                lProduto = new LinkedList<>();
                lAqruivos = new LinkedList<>();
                produto = new Produto();
                produtoDAO = new ProdutoDAO();
                produto.setCodigo(rsSelectCaracteristica.getString("USU_CODPRO"));
                produto.setIdCategoria(caracteristicaItem.getIdCategoria());
                produto.setIdSubCategoria(caracteristicaItem.getIdSubCategoria());
                produto.setIdCaracteristica(caracteristicaItem.getIdCaracteristica());
                produto.setIdCaracteristicaItem(caracteristicaItem.getIdCaracteristicaItem());
                produto.setDescCaracteristica("");
                produto.setSituacao("I");
                produto.setVisivel("N");
                produto.setIdUsuario(caracteristicaItem.getIdUsuario());
                lProduto.add(produto);
                produtoDAO.InserirProduto(produto, lProduto, "CadCar", connection, lAqruivos);
            }
        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
    }

}
