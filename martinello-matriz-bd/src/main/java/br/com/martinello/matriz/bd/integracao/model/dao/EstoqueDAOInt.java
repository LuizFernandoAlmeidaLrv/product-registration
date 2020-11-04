/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.integracao.model.dao;

import br.com.martinello.matriz.bd.model.dao.Conexao;
import br.com.martinello.matriz.bd.transients.CategoriaTray;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
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
public class EstoqueDAOInt {

    private PreparedStatement psSelectCategoria;
    private ResultSet rsSelectCategoria;
    private String selectCategoria, updateCategoria;

    public EstoqueDAOInt() {

    }

    public List<CategoriaTray> buscarCategoriaIntegrarEcommerce() throws ErroSistemaException {
        List<CategoriaTray> lCategoriaTray = new LinkedList<>();

        try {
            CategoriaTray categoriaTray = new CategoriaTray();

            selectCategoria = "SELECT USU_T075ECD.USU_CODCAT, \n"
                    + "               USU_T075ECD.USU_CATPAI,\n"
                    + "               USU_T075ECD.USU_CODECO,\n"
                    + "               CATPAI.USU_CODECO CODECOPAI,\n"
                    + "               USU_T075ECD.USU_DESCAT,\n"
                    + "               USU_T075ECD.USU_SITCAT,\n"
                    + "               USU_T075ECD.USU_VISSIT,\n"
                    + "               USU_T075ECD.USU_OBSALT,\n"
                    + "               USU_T075ECD.USU_DATGER\n"
                    + "          FROM USU_T075ECD\n"
                    + "          LEFT JOIN USU_T075ECD CATPAI\n"
                    + "            ON CATPAI.USU_CODCAT = USU_T075ECD.USU_CATPAI"
                    + "         WHERE USU_T075ECD.USU_CODECO IS NULL OR USU_T075ECD.USU_CODECO = 0"
                    + "         ORDER BY USU_CODCAT ASC ";

            psSelectCategoria = Conexao.getConnection().prepareStatement(selectCategoria);
            rsSelectCategoria = psSelectCategoria.executeQuery();
            while (rsSelectCategoria.next()) {
                categoriaTray = new CategoriaTray();

                categoriaTray.setAtivo(rsSelectCategoria.getString("USU_VISSIT").equals("S"));
                categoriaTray.setCategoriaERPId(rsSelectCategoria.getString("USU_CODCAT"));
                categoriaTray.setCategoriaPaiId(rsSelectCategoria.getLong("USU_CATPAI"));
                categoriaTray.setCategoriaPaiId(rsSelectCategoria.getLong("CODECOPAI"));
                categoriaTray.setExibeMenu(true);
                categoriaTray.setId(rsSelectCategoria.getLong("USU_CODECO"));
                /* Equivale a permitir demonstrar os dados do produto, no nosso caso será controlado por produto. */
                categoriaTray.setExibirMatrizAtributos("NEUTRO");
                categoriaTray.setIsReseller(Boolean.TRUE);
                categoriaTray.setNome(rsSelectCategoria.getString("USU_DESCAT"));
                categoriaTray.setQuantidadeMaximaCompraUnidade(0l);
                categoriaTray.setValorMinimoCompra(0d);

                lCategoriaTray.add(categoriaTray);
            }
            psSelectCategoria.close();
            rsSelectCategoria.close();
        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lCategoriaTray;
    }

    public CategoriaTray buscarCategoriaIntegrarEcommerce(Long codCat) throws ErroSistemaException {
        CategoriaTray categoriaTray = new CategoriaTray();

        try {
            selectCategoria = "SELECT USU_T075ECD.USU_CODCAT, \n"
                    + "               USU_T075ECD.USU_CODECO,\n"
                    + "               USU_T075ECD.USU_CATPAI,\n"
                    + "               CATPAI.USU_CODECO CODECOPAI,\n"
                    + "               USU_T075ECD.USU_DESCAT,\n"
                    + "               USU_T075ECD.USU_SITCAT,\n"
                    + "               USU_T075ECD.USU_VISSIT,\n"
                    + "               USU_T075ECD.USU_OBSALT,\n"
                    + "               USU_T075ECD.USU_DATGER\n"
                    + "          FROM USU_T075ECD\n"
                    + "          LEFT JOIN USU_T075ECD CATPAI\n"
                    + "            ON CATPAI.USU_CODCAT = USU_T075ECD.USU_CATPAI"
                    + "         WHERE USU_T075ECD.USU_CODCAT = ?"
                    + "         ORDER BY USU_CODCAT ASC ";

            psSelectCategoria = Conexao.getConnection().prepareStatement(selectCategoria);
            psSelectCategoria.setLong(1, codCat);

            rsSelectCategoria = psSelectCategoria.executeQuery();
            if (rsSelectCategoria.next()) {
                categoriaTray = new CategoriaTray();

                categoriaTray.setAtivo(rsSelectCategoria.getString("USU_VISSIT").equals("S"));
                categoriaTray.setCategoriaERPId(rsSelectCategoria.getString("USU_CODCAT"));
                categoriaTray.setCategoriaERPPaiId(rsSelectCategoria.getString("USU_CATPAI"));
                categoriaTray.setCategoriaPaiId(rsSelectCategoria.getLong("CODECOPAI"));
                categoriaTray.setExibeMenu(true);
                categoriaTray.setId(rsSelectCategoria.getLong("USU_CODECO"));
                /* Equivale a permitir demonstrar os dados do produto, no nosso caso será controlado por produto. */
                categoriaTray.setExibirMatrizAtributos("NEUTRO");
                categoriaTray.setIsReseller(Boolean.TRUE);
                categoriaTray.setNome(rsSelectCategoria.getString("USU_DESCAT"));
                categoriaTray.setQuantidadeMaximaCompraUnidade(0l);
                categoriaTray.setValorMinimoCompra(0d);

            }
            psSelectCategoria.close();
            rsSelectCategoria.close();
        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return categoriaTray;
    }

    public void atualizarCodigoEcommerce(CategoriaTray categoriaTray) throws ErroSistemaException {
        updateCategoria = "UPDATE sapiens.USU_T075ECD "
                + "           SET USU_CODECO = ? "
                + "         WHERE USU_CODCAT = ?";

        Connection conexao = Conexao.getConnection();
        try {
            PreparedStatement psUpdateCategoria = conexao.prepareStatement(updateCategoria);
            psUpdateCategoria.setLong(1, categoriaTray.getId());
            psUpdateCategoria.setString(2, categoriaTray.getCategoriaERPId());
            int retorno = psUpdateCategoria.executeUpdate();

            psUpdateCategoria.close();
            if (retorno != 1) {
                conexao.rollback();
                throw new ErroSistemaException("1 - Erro ao realizar a atualização da categoria.");
            } else {
                conexao.commit();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException("2 - Erro ao realizar a atualização da categoria.", ex);
        }
    }

}
