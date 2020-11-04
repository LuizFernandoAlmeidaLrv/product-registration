/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.integracao.model.dao;

import br.com.martinello.matriz.bd.model.dao.Conexao;
import br.com.martinello.matriz.bd.model.domain.Produto;
import br.com.martinello.matriz.bd.transients.CategoriaTray;
import br.com.martinello.matriz.bd.transients.ChavePendencia;
import br.com.martinello.matriz.bd.transients.ProdutoTray;
import br.com.martinello.matriz.bd.transients.TabelaPrecoProdutoTray;
import br.com.martinello.matriz.bd.transients.TabelaPrecoTray;
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
public class TabelaPrecoDAOInt {

    private PreparedStatement psSelectTabelaPreco;
    private ResultSet rsSelectTabelaPreco;
    private String selectTabelaPreco, updateTabelaPreco;

    public TabelaPrecoDAOInt() {

    }

    public List<TabelaPrecoProdutoTray> buscarCategoriaIntegrarEcommerce() throws ErroSistemaException {
        List<TabelaPrecoProdutoTray> lTabelaPrecoTray = new LinkedList<>();

        try {
            TabelaPrecoProdutoTray tabelaPrecoTray = new TabelaPrecoProdutoTray();

            selectTabelaPreco = "SELECT USU_T075ECD.USU_CODCAT, \n"
                    + "               USU_T075ECD.USU_CATPAI,\n"
                    + "               CATPAI.USU_DESCAT DESPAI,\n"
                    + "               USU_T075ECD.USU_DESCAT,\n"
                    + "               USU_T075ECD.USU_SITCAT,\n"
                    + "               USU_T075ECD.USU_VISSIT,\n"
                    + "               USU_T075ECD.USU_OBSALT,\n"
                    + "               USU_T075ECD.USU_DATGER\n"
                    + "          FROM USU_T075ECD\n"
                    + "          LEFT JOIN USU_T075ECD CATPAI\n"
                    + "            ON CATPAI.USU_CODCAT = USU_T075ECD.USU_CATPAI"
                    + "         ORDER BY USU_CODCAT ASC ";

            psSelectTabelaPreco = Conexao.getConnection().prepareStatement(selectTabelaPreco);
            rsSelectTabelaPreco = psSelectTabelaPreco.executeQuery();
            while (rsSelectTabelaPreco.next()) {

                tabelaPrecoTray = new TabelaPrecoProdutoTray();

                tabelaPrecoTray.setPrecoDe(Double.NaN);
                tabelaPrecoTray.setPrecoPor(Double.NaN);
                tabelaPrecoTray.setSku(selectTabelaPreco);

                lTabelaPrecoTray.add(tabelaPrecoTray);
            }
            psSelectTabelaPreco.close();
            rsSelectTabelaPreco.close();
        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lTabelaPrecoTray;
    }

//    public CategoriaTray buscarPrecoAtualIntegrarEcommerce(ChavePendencia chavePendencia) throws ErroSistemaException {
//        TabelaPrecoTray categoriaTray = new CategoriaTray();
//
//        try {
//            selectTabelaPreco = "SELECT USU_T621TPR.USU_CODPRO,\n"
//                    + "                 USU_T621TPR.USU_PREVEN,\n"
//                    + "                 USU_T621TPR.USU_CUSATU,\n"
//                    + "                 NVL((SELECT MAX(TPR_PRO.USU_PREVEN)\n"
//                    + "                        FROM USU_T621TPR TPR_PRO, USU_T621TPE TPE_PRO\n"
//                    + "                       WHERE TPR_PRO.USU_CODTAB = TPE_PRO.USU_CODTAB\n"
//                    + "                         AND ((SYSDATE BETWEEN TPR_PRO.USU_DATINI AND\n"
//                    + "                             TPR_PRO.USU_DATFIN) OR\n"
//                    + "                             (SYSDATE >= USU_T621TPR.USU_DATINI AND\n"
//                    + "                             TPR_PRO.USU_DATFIN = '31/12/1900'))\n"
//                    + "                         AND TPE_PRO.USU_TIPTAB = 'P'\n"
//                    + "                         AND TPR_PRO.USU_CODPRO = USU_T621TPR.USU_CODPRO),\n"
//                    + "                     0) USU_PREPRO\n"
//                    + "            FROM USU_T621TPR, USU_T621TPE\n"
//                    + "           WHERE USU_T621TPR.USU_CODTAB = USU_T621TPE.USU_CODTAB\n"
//                    + "             AND ((SYSDATE BETWEEN USU_T621TPR.USU_DATINI AND USU_T621TPR.USU_DATFIN) OR\n"
//                    + "                 (SYSDATE >= USU_T621TPR.USU_DATINI AND\n"
//                    + "                 USU_T621TPR.USU_DATFIN = '31/12/1900'))\n"
//                    + "             AND USU_T621TPE.USU_TIPTAB = 'N'\n"
//                    + "             AND USU_CODPRO = ?";
//
//            psSelectTabelaPreco = Conexao.getConnection().prepareStatement(selectTabelaPreco);
//            psSelectTabelaPreco.setString(1, chavePendencia.getProdutoErp());
//
//            rsSelectTabelaPreco = psSelectTabelaPreco.executeQuery();
//            if (rsSelectTabelaPreco.next()) {
//                categoriaTray = new CategoriaTray();
//
//                categoriaTray.setAtivo(rsSelectTabelaPreco.getString("USU_VISSIT").equals("S"));
//                categoriaTray.setCategoriaERPId(rsSelectTabelaPreco.getString("USU_CODCAT"));
//                categoriaTray.setCategoriaERPPaiId(rsSelectTabelaPreco.getString("USU_CATPAI"));
//                categoriaTray.setCategoriaPaiId(rsSelectTabelaPreco.getLong("CODECOPAI"));
//                categoriaTray.setExibeMenu(true);
//                /* Equivale a permitir demonstrar os dados do produto, no nosso caso será controlado por produto. */
//                categoriaTray.setExibirMatrizAtributos("NEUTRO");
//                categoriaTray.setIsReseller(Boolean.TRUE);
//                categoriaTray.setNome(rsSelectTabelaPreco.getString("USU_DESCAT"));
//                categoriaTray.setQuantidadeMaximaCompraUnidade(0l);
//                categoriaTray.setValorMinimoCompra(0d);
//
//            }
//            psSelectTabelaPreco.close();
//            rsSelectTabelaPreco.close();
//        } catch (SQLException | ErroSistemaException ex) {
//            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
//        }
//        return categoriaTray;
//    }

    public void atualizarCodigoEcommerce(CategoriaTray categoriaTray) throws ErroSistemaException {
        updateTabelaPreco = "UPDATE sapiens.USU_T621TPR "
                + "             SET USU_CODECO = ? "
                + "           WHERE USU_CODCAT = ?";

        Connection conexao = Conexao.getConnection();
        try {
            PreparedStatement psUpdateCategoria = conexao.prepareStatement(updateTabelaPreco);
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
