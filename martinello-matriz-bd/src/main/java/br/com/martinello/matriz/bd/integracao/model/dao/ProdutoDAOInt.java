/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.integracao.model.dao;

import br.com.martinello.matriz.bd.model.dao.*;
import br.com.martinello.matriz.bd.transients.CatalogoErp;
import br.com.martinello.matriz.bd.transients.CategoriaTray;
import br.com.martinello.matriz.bd.transients.ChavePendencia;
import br.com.martinello.matriz.bd.transients.ProdutoImagemTray;
import br.com.martinello.matriz.bd.transients.ProdutoInformacaoTray;
import br.com.martinello.matriz.bd.transients.ProdutoTray;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class ProdutoDAOInt {

    private PreparedStatement psSelectProduto;
    private ResultSet rsSelectProduto;

    public ProdutoDAOInt() {

    }

    public List<ProdutoTray> buscarProdutoIntegrarEcommerce() throws ErroSistemaException {
        List<ProdutoTray> lProdutoTray = new LinkedList<>();

        try {
            ProdutoTray produtoTray = new ProdutoTray();

            String selectProduto = "SELECT USU_T075DPR.USU_CODPRO, "
                    + "             USU_T075DPR.USU_DESPRO, "
                    + "             USU_T075DPR.USU_VISSIT, "
                    + "             E075PRO.USU_AGRSIT, "
                    + "             E075PRO.USU_CODECO, "
                    + "             E075PRO.IDEPRO, "
                    + "             E076MAR.NOMMAR "
                    + "        FROM sapiens.USU_T075DPR, sapiens.E075PRO, sapiens.E076MAR "
                    + "       WHERE USU_T075DPR.USU_CODEMP = E075PRO.CODEMP "
                    + "         AND USU_T075DPR.USU_CODPRO = E075PRO.CODPRO "
                    + "         AND E075PRO.CODMAR = E076MAR.CODMAR ";
            psSelectProduto = Conexao.getConnection().prepareStatement(selectProduto);
            rsSelectProduto = psSelectProduto.executeQuery();
            while (rsSelectProduto.next()) {
                produtoTray = new ProdutoTray();
                produtoTray.setProdutoId(rsSelectProduto.getLong("USU_CODECO"));
                produtoTray.setSku(rsSelectProduto.getString("USU_CODPRO"));
                produtoTray.setCodigoErp(rsSelectProduto.getString("USU_CODPRO"));
                produtoTray.setNome(rsSelectProduto.getString("USU_DESPRO"));
                produtoTray.setIdVinculoExterno(rsSelectProduto.getString("USU_AGRSIT"));
                produtoTray.setFabricante(rsSelectProduto.getString("NOMMAR"));
                produtoTray.setExibirSite(rsSelectProduto.getString("USU_VISSIT").equalsIgnoreCase("S"));
                produtoTray.setEan(rsSelectProduto.getString("IDEPRO"));
                lProdutoTray.add(produtoTray);
            }
            psSelectProduto.close();
            rsSelectProduto.close();
        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lProdutoTray;
    }

    public ProdutoTray buscarProdutoIntegrarEcommerce(ChavePendencia chavePendencia) throws ErroSistemaException {
        ProdutoTray produtoTray = new ProdutoTray();

        String selectProduto = "SELECT USU_T075DPR.USU_CODPRO, "
                + "             USU_T075DPR.USU_DESPRO, "
                + "             USU_T075DPR.USU_VISSIT, "
                + "             E075PRO.USU_AGRSIT, "
                + "             E075PRO.USU_CODECO, "
                + "             E075PRO.IDEPRO, "
                + "             E076MAR.NOMMAR "
                + "        FROM sapiens.USU_T075DPR, sapiens.E075PRO, sapiens.E076MAR "
                + "       WHERE USU_T075DPR.USU_CODEMP = E075PRO.CODEMP "
                + "         AND USU_T075DPR.USU_CODPRO = E075PRO.CODPRO "
                + "         AND E075PRO.CODMAR = E076MAR.CODMAR "
                + "         AND E075PRO.CODEMP = ? "
                + "         AND E075PRO.CODPRO = ? ";

        try (PreparedStatement psSelectProduto = Conexao.getConnection().prepareStatement(selectProduto);) {
            psSelectProduto.setLong(1, chavePendencia.getEmpresaErp());
            psSelectProduto.setString(2, chavePendencia.getProdutoErp());

            try (ResultSet rsSelectProduto = psSelectProduto.executeQuery();) {

                if (rsSelectProduto.next()) {
                    produtoTray = new ProdutoTray();
                    produtoTray.setProdutoId(rsSelectProduto.getLong("USU_CODECO"));
                    produtoTray.setSku(rsSelectProduto.getString("USU_CODPRO"));
                    produtoTray.setCodigoErp(rsSelectProduto.getString("USU_CODPRO"));
                    produtoTray.setNome(rsSelectProduto.getString("USU_DESPRO"));
                    produtoTray.setIdVinculoExterno(rsSelectProduto.getString("USU_AGRSIT"));
                    produtoTray.setFabricante(rsSelectProduto.getString("NOMMAR"));
                    produtoTray.setExibirSite(rsSelectProduto.getString("USU_VISSIT").equalsIgnoreCase("S"));
                    produtoTray.setEan(rsSelectProduto.getString("IDEPRO"));

                    String selectTabelaPreco = ""
                            + "SELECT USU_T621TPR.USU_CODPRO,\n"
                            + "       USU_T621TPR.USU_PREVEN,\n"
                            + "       USU_T621TPR.USU_CUSATU,\n"
                            + "       NVL((SELECT MAX(TPR_PRO.USU_PREVEN)\n"
                            + "             FROM USU_T621TPR TPR_PRO, USU_T621TPE TPE_PRO\n"
                            + "            WHERE TPR_PRO.USU_CODTAB = TPE_PRO.USU_CODTAB\n"
                            + "              AND ((SYSDATE BETWEEN TPR_PRO.USU_DATINI AND\n"
                            + "                  TPR_PRO.USU_DATFIN) OR\n"
                            + "                  (SYSDATE >= USU_T621TPR.USU_DATINI AND\n"
                            + "                  TPR_PRO.USU_DATFIN = '31/12/1900'))\n"
                            + "              AND TPE_PRO.USU_TIPTAB = 'P'\n"
                            + "              AND TPR_PRO.USU_CODPRO = USU_T621TPR.USU_CODPRO),\n"
                            + "           USU_T621TPR.USU_PREVEN) USU_PREPRO\n"
                            + "  FROM USU_T621TPR, USU_T621TPE\n"
                            + " WHERE USU_T621TPR.USU_CODTAB = USU_T621TPE.USU_CODTAB\n"
                            + "   AND ((SYSDATE BETWEEN USU_T621TPR.USU_DATINI AND USU_T621TPR.USU_DATFIN) OR\n"
                            + "       (SYSDATE >= USU_T621TPR.USU_DATINI AND\n"
                            + "       USU_T621TPR.USU_DATFIN = '31/12/1900'))\n"
                            + "   AND USU_T621TPE.USU_TIPTAB = 'N'"
                            + "   AND USU_CODPRO = ?";

                    try (PreparedStatement psSelectTabelaPreco = Conexao.getConnection().prepareStatement(selectTabelaPreco);) {
                        psSelectTabelaPreco.setString(1, chavePendencia.getProdutoErp());

                        try (ResultSet rsSelectTabelaPreco = psSelectTabelaPreco.executeQuery();) {
                            if (rsSelectTabelaPreco.next()) {
                                produtoTray.setPrecoCusto(rsSelectTabelaPreco.getDouble("USU_CUSATU"));
                                produtoTray.setPrecoDe(rsSelectTabelaPreco.getDouble("USU_PREVEN"));
                                produtoTray.setPrecoPor(rsSelectTabelaPreco.getDouble("USU_PREPRO"));
                            }
                        }

                    }

                }
            }
        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return produtoTray;
    }

    public ProdutoImagemTray buscarProdutoImagensIntegrarEcommerce(ChavePendencia chavePendencia) throws ErroSistemaException {
        ProdutoImagemTray produtoImagemTray = new ProdutoImagemTray();

        String selectArquivos = "SELECT USU_DESARQ,"
                + "                     USU_EXTARQ,"
                + "                     USU_ORDARQ,"
                + "                     USU_CODEMP,"
                + "                     USU_CODPRO,"
                + "                     USU_CODDER,"
                + "                     USU_SEQARQ,"
                + "                     USU_DESARQ,"
                + "                     USU_ARQPRI,"
                + "                     USU_VISSIT,"
                + "                     USU_SITARQ"
                + "               FROM sapiens.USU_T075ARQ "
                + "              WHERE USU_CODEMP = ? "
                + "                AND USU_CODDER = ? "
                + "                AND USU_CODPRO = ? "
                + "                AND USU_SEQARQ = ?"
                + "                AND USU_DESARQ = ?";

        try (PreparedStatement psSelectArquivos = Conexao.getConnection().prepareStatement(selectArquivos);) {
            /* Passa os parametros para o select e executa*/
            psSelectArquivos.setLong(1, chavePendencia.getEmpresaErp());
            psSelectArquivos.setString(2, chavePendencia.getDerivacaoErp());
            psSelectArquivos.setString(3, chavePendencia.getProdutoErp());
            psSelectArquivos.setLong(4, chavePendencia.getSequenciaArquivoErp());
            psSelectArquivos.setString(5, chavePendencia.getDescricaArquivoErp());

            ResultSet rsArquivos = psSelectArquivos.executeQuery();
            while (rsArquivos.next()) {
                produtoImagemTray = new ProdutoImagemTray();

                /* Converte os arquivos para base64. */
                File inputFile = new File("/media/rafael/imagens_site/Cadastro/Banco_de-Imagens/" + chavePendencia.getProdutoErp() + "/" + rsArquivos.getString("USU_DESARQ") + "." + rsArquivos.getString("USU_EXTARQ"));
                String encodedString = "";
                FileInputStream inputStream = new FileInputStream("/media/rafael/imagens_site/Cadastro/Banco_de-Imagens/" + chavePendencia.getProdutoErp() + "/" + rsArquivos.getString("USU_DESARQ") + "." + rsArquivos.getString("USU_EXTARQ"));

                byte[] bytes = new byte[(int) inputFile.length()];
                inputStream.read(bytes);

                if (inputFile.exists()) {
                    encodedString = Base64
                            .getEncoder()
                            .encodeToString(bytes);
                    produtoImagemTray.setBase64(encodedString);
                }

                produtoImagemTray.setEstampa(rsArquivos.getString("USU_EXTARQ").equals("S"));
                produtoImagemTray.setExibirMiniatura(true);
                produtoImagemTray.setFormato(rsArquivos.getString("USU_EXTARQ"));
                produtoImagemTray.setOrdem(rsArquivos.getLong("USU_ORDARQ"));
                produtoImagemTray.setVisivelSite(rsArquivos.getString("USU_VISSIT"));
                produtoImagemTray.setSituacaoRegistro(rsArquivos.getString("USU_SITARQ"));

                /* Chave da tabela.... */
                produtoImagemTray.setEmpresaErp(rsArquivos.getLong("USU_CODEMP"));
                produtoImagemTray.setProdutoErp(rsArquivos.getString("USU_CODPRO"));
                produtoImagemTray.setDerivacaoErp(rsArquivos.getString("USU_CODDER"));
                produtoImagemTray.setSequenciaErp(rsArquivos.getLong("USU_SEQARQ"));
                produtoImagemTray.setDescricaoErp(rsArquivos.getString("USU_DESARQ"));
            }
            rsArquivos.close();

        } catch (SQLException | ErroSistemaException | IOException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

        return produtoImagemTray;

    }

    public ProdutoInformacaoTray buscarProdutoInformacoesIntegrarEcommerce(ChavePendencia chavePendencia) throws ErroSistemaException {
        ProdutoInformacaoTray produtoInformacaoTray = new ProdutoInformacaoTray();
        String tr = "";
        String td = "";
        String html = "";

        /* Buscar as caracteristicas agrupadas. */
        String selectCaracteristicas = "SELECT USU_T075CAD.USU_CODEMP,\n"
                + "                            USU_T075CAD.USU_CODCAT,\n"
                + "                            USU_T075CAD.USU_CODSCA,\n"
                + "                            USU_T075CAD.USU_CODCAR,\n"
                + "                            USU_T075CAR.USU_DESCAR,\n"
                + "                            USU_T075CAR.USU_SEQCAR\n"
                + "                       FROM USU_T075CAD,USU_T075CAR\n"
                + "                      WHERE USU_T075CAD.USU_CODEMP = USU_T075CAR.USU_CODEMP\n"
                + "                        AND USU_T075CAD.USU_CODCAT = USU_T075CAR.USU_CODCAT\n"
                + "                        AND USU_T075CAD.USU_CODSCA = USU_T075CAR.USU_CODSCA\n"
                + "                        AND USU_T075CAD.USU_CODCAR = USU_T075CAR.USU_CODCAR\n"
                + "                        AND USU_T075CAD.USU_CODEMP = ?\n"
                + "                        AND USU_T075CAD.USU_CODPRO = ?\n"
                + "                        AND USU_T075CAD.USU_VISSIT = 'S'\n"
                + "                      GROUP BY USU_T075CAD.USU_CODEMP,\n"
                + "                               USU_T075CAD.USU_CODCAT,\n"
                + "                               USU_T075CAD.USU_CODSCA,\n"
                + "                               USU_T075CAD.USU_CODCAR,\n"
                + "                               USU_T075CAR.USU_DESCAR,\n"
                + "                               USU_T075CAR.USU_SEQCAR\n"
                + "                      ORDER BY USU_SEQCAR ASC";

        try (PreparedStatement psSelectCaracteristicas = Conexao.getConnection().prepareStatement(selectCaracteristicas);) {
            /* Passa os parametros para o select e executa*/
            psSelectCaracteristicas.setLong(1, chavePendencia.getEmpresaErp());
            psSelectCaracteristicas.setString(2, chavePendencia.getProdutoErp());

            int cont = 0;
            try (ResultSet rsCaracteristicas = psSelectCaracteristicas.executeQuery()) {
                while (rsCaracteristicas.next()) {
                    td = "";

                    String selectFichaPreenchida = "SELECT USU_T075CAP.USU_DESCAI,\n"
                            + "                            USU_T075CAD.USU_DESCAD,\n"
                            + "                            USU_T075CAR.USU_REGUNI\n"
                            + "                       FROM USU_T075CAD,USU_T075CAP,USU_T075CAR\n"
                            + "                      WHERE USU_T075CAD.USU_CODEMP = USU_T075CAP.USU_CODEMP\n"
                            + "                        AND USU_T075CAD.USU_CODCAT = USU_T075CAP.USU_CODCAT\n"
                            + "                        AND USU_T075CAD.USU_CODSCA = USU_T075CAP.USU_CODSCA\n"
                            + "                        AND USU_T075CAD.USU_CODCAR = USU_T075CAP.USU_CODCAR\n"
                            + "                        AND USU_T075CAD.USU_CODCAI = USU_T075CAP.USU_CODCAI\n"
                            + "                        AND USU_T075CAD.USU_CODEMP = USU_T075CAR.USU_CODEMP\n"
                            + "                        AND USU_T075CAD.USU_CODCAT = USU_T075CAR.USU_CODCAT\n"
                            + "                        AND USU_T075CAD.USU_CODSCA = USU_T075CAR.USU_CODSCA\n"
                            + "                        AND USU_T075CAD.USU_CODCAR = USU_T075CAR.USU_CODCAR\n"
                            + "                        AND USU_T075CAD.USU_CODEMP = ?\n"
                            + "                        AND USU_T075CAD.USU_CODCAT = ?\n"
                            + "                        AND USU_T075CAD.USU_CODSCA = ?\n"
                            + "                        AND USU_T075CAD.USU_CODCAR = ?\n"
                            + "                        AND USU_T075CAD.USU_CODPRO = ?\n"
                            + "                        AND USU_T075CAD.USU_VISSIT = 'S'\n"
                            + "                      ORDER BY USU_T075CAP.USU_SEQCAI ASC";

                    try (PreparedStatement psSelectFichaPreenchida = Conexao.getConnection().prepareStatement(selectFichaPreenchida);) {

                        /* Passa os parametros para o select e executa*/
                        psSelectFichaPreenchida.setLong(1, rsCaracteristicas.getLong("USU_CODEMP"));
                        psSelectFichaPreenchida.setLong(2, rsCaracteristicas.getLong("USU_CODCAT"));
                        psSelectFichaPreenchida.setLong(3, rsCaracteristicas.getLong("USU_CODSCA"));
                        psSelectFichaPreenchida.setLong(4, rsCaracteristicas.getLong("USU_CODCAR"));
                        psSelectFichaPreenchida.setString(5, chavePendencia.getProdutoErp());

                        String segundaColuna = "";
                        String terceiraConluna = "";

                        try (ResultSet rsFichaPreenchida = psSelectFichaPreenchida.executeQuery()) {
                            while (rsFichaPreenchida.next()) {
                                if (rsFichaPreenchida.getString("USU_REGUNI").equals("S")) {
                                    segundaColuna += (segundaColuna.length() == 0 ? "" : "</br>") + "&nbsp;";
                                } else {
                                    segundaColuna += (segundaColuna.length() == 0 ? "" : "</br>") + rsFichaPreenchida.getString("USU_DESCAI");
                                }
                                terceiraConluna += (terceiraConluna.length() == 0 ? "" : "</br>") + rsFichaPreenchida.getString("USU_DESCAD");
                            }
                            rsFichaPreenchida.close();
                        }

                        if (cont % 2 == 0) {
                            td += "<tr><td class=\"tg-ul56\">" + rsCaracteristicas.getString("USU_DESCAR") + "</td>";
                            td += "<td class=\"tg-az4d\">" + segundaColuna + "</td>";
                            td += "<td class=\"tg-az4d\">" + terceiraConluna + "</td>";
                        } else {
                            td += "<tr><td class=\"tg-xt70\">" + rsCaracteristicas.getString("USU_DESCAR") + "</td>";
                            td += "<td class=\"tg-iks7\">" + segundaColuna + "</td>";
                            td += "<td class=\"tg-iks7\">" + terceiraConluna + "</td>";
                        }
                        td += "</tr>";
                    } catch (SQLException | ErroSistemaException ex) {
                        throw new ErroSistemaException(ex.getMessage(), ex.getCause());
                    }

                    html += td;
                    cont++;
                }
            }

            if (html.length() > 0) {
                html = "<style type=\"text/css\">\n"
                        + "  .tg {\n"
                        + "    border-collapse: collapse;\n"
                        + "    border-spacing: 0;\n"
                        + "    width: 100%;\n"
                        + "  }\n"
                        + "  .tg td,\n"
                        + "  .tg th {\n"
                        + "    line-height: 22px;\n"
                        + "  }\n"
                        + "  .tg td {\n"
                        + "    font-family: Arial, sans-serif;\n"
                        + "    font-size: 14px;\n"
                        + "    overflow: hidden;\n"
                        + "    padding: 10px 5px;\n"
                        + "    word-break: normal;\n"
                        + "  }\n"
                        + "  .tg th {\n"
                        + "    font-family: Arial, sans-serif;\n"
                        + "    font-size: 14px;\n"
                        + "    font-weight: normal;\n"
                        + "    overflow: hidden;\n"
                        + "    padding: 10px 5px;\n"
                        + "    word-break: normal;\n"
                        + "  }\n"
                        + "  .tg .tg-xt70 {\n"
                        + "    background-color: #ffffff;\n"
                        + "    border-color: #000000;\n"
                        + "    font-size: 14px;\n"
                        + "    font-weight: bold;\n"
                        + "    text-align: left;\n"
                        + "    vertical-align: top;\n"
                        + "  }\n"
                        + "  .tg .tg-4qm2 {\n"
                        + "    background-color: #f4f4f4;\n"
                        + "    border-color: #000000;\n"
                        + "    color: #343434;\n"
                        + "    text-align: left;\n"
                        + "    vertical-align: top;\n"
                        + "  }\n"
                        + "  .tg .tg-ul56 {\n"
                        + "    background-color: #f4f4f4;\n"
                        + "    border-color: #000000;\n"
                        + "    font-size: 14px;\n"
                        + "    font-weight: bold;\n"
                        + "    text-align: left;\n"
                        + "    vertical-align: top;\n"
                        + "  }\n"
                        + "  .tg .tg-az4d {\n"
                        + "    background-color: #f4f4f4;\n"
                        + "    border-color: #000000;\n"
                        + "    text-align: left;\n"
                        + "    vertical-align: top;\n"
                        + "  }\n"
                        + "  .tg .tg-iks7 {\n"
                        + "    background-color: #ffffff;\n"
                        + "    border-color: #000000;\n"
                        + "    text-align: left;\n"
                        + "    vertical-align: top;\n"
                        + "  }\n"
                        + "\n"
                        + "  @media(max-width: 768px){\n"
                        + "    .tg th,\n"
                        + "    .tg td {\n"
                        + "      display: block;\n"
                        + "    }\n"
                        + "\n"
                        + "    .tg .tg-ul56,\n"
                        + "    .tg .tg-xt70 {\n"
                        + "      background-color: #f4f4f4;\n"
                        + "      /* padding: 10px; */\n"
                        + "    }\n"
                        + "\n"
                        + "    .tg .tg-4qm2,\n"
                        + "    .tg .tg-az4d,\n"
                        + "    .tg .tg-iks7{\n"
                        + "      background-color: #fff;\n"
                        + "      /* padding: 0 10px; */\n"
                        + "    }\n"
                        + "\n"
                        + "    .tg th,\n"
                        + "    .tg td {\n"
                        + "      padding: 0;\n"
                        + "    }\n"
                        + "\n"
                        + "    .tg tr {\n"
                        + "      display: block;\n"
                        + "      padding: 10px 0;\n"
                        + "    }\n"
                        + "\n"
                        + "    .tg tr th:nth-child(2),\n"
                        + "    .tg tr th:nth-child(3),\n"
                        + "    .tg tr td:nth-child(2),\n"
                        + "    .tg tr td:nth-child(3) {\n"
                        + "      display: inline-block;\n"
                        + "    }\n"
                        + "\n"
                        + "    .tg tr th:empty,\n"
                        + "    .tg tr td:empty {\n"
                        + "      display: none;\n"
                        + "    }\n"
                        + "\n"
                        + "    .tg tr * {\n"
                        + "      padding-left: 6px;\n"
                        + "    }\n"
                        + "  }\n"
                        + "</style> <table cellspacing=\"0\" cellpadding=\"0\" class=\"tg\"> <tbody> " + html + "</tbody> </table>";
            }

            produtoInformacaoTray = new ProdutoInformacaoTray();
            produtoInformacaoTray.setTitulo("Informações do produto");
            produtoInformacaoTray.setTexto(html);
            produtoInformacaoTray.setTipoInformacao("Informacoes");
            

        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

        return produtoInformacaoTray;

    }

    public CatalogoErp buscarProdutoCategoriaRelacionada(ChavePendencia chavePendencia) throws ErroSistemaException {
        CatalogoErp catalogoErp = new CatalogoErp();

        String selectCategoriaRelacionada = "SELECT USU_T075ECI.USU_CODEMP, "
                + "                                 USU_T075ECI.USU_CODPRO, "
                + "                                 USU_T075ECI.USU_CODCAT, "
                + "                                 USU_T075ECI.USU_SITREL, "
                + "                                 USU_T075ECI.USU_VISSIT, "
                + "                                 USU_T075ECI.USU_USUGER, "
                + "                                 USU_T075ECI.USU_DATGER, "
                + "                                 USU_T075ECI.USU_HORGER, "
                + "                                 USU_T075ECI.USU_USUALT, "
                + "                                 USU_T075ECI.USU_DATALT, "
                + "                                 USU_T075ECI.USU_HORALT,"
                + "                                 USU_T075ECI.USU_CODECO, "
                + "                                 USU_T075ECI.USU_OBSALT, "
                + "                                 USU_T075ECD.USU_CODECO ECOCAT, "
                + "                                 USU_T075ECI.USU_CATPRI "
                + "                            FROM USU_T075ECI "
                + "                       LEFT JOIN USU_T075ECD "
                + "                              ON USU_T075ECD.USU_CODCAT = USU_T075ECI.USU_CODCAT"
                + "                           WHERE USU_T075ECI.USU_CODEMP = ? "
                + "                             AND USU_T075ECI.USU_CODPRO = ? "
                + "                             AND USU_T075ECI.USU_CODCAT = ?";

        try (PreparedStatement psSelectCaracteristicas = Conexao.getConnection().prepareStatement(selectCategoriaRelacionada);) {
            /* Passa os parametros para o select e executa*/
            psSelectCaracteristicas.setLong(1, chavePendencia.getEmpresaErp());
            psSelectCaracteristicas.setString(2, chavePendencia.getProdutoErp());
            psSelectCaracteristicas.setLong(3, chavePendencia.getCatalogoErp());

            try (ResultSet rsCatalogo = psSelectCaracteristicas.executeQuery()) {
                if (rsCatalogo.next()) {
                    catalogoErp.setCatalogoErp(rsCatalogo.getLong("USU_CODCAT"));
                    catalogoErp.setCategoriaPrincipal(rsCatalogo.getString("USU_CATPRI"));
                    catalogoErp.setCodigoEcommerce(rsCatalogo.getLong("USU_CODECO"));
                    catalogoErp.setEmpresaErp(rsCatalogo.getLong("USU_CODEMP"));
                    catalogoErp.setObservacao(rsCatalogo.getString("USU_OBSALT"));
                    catalogoErp.setProdutoErp(rsCatalogo.getString("USU_CODPRO"));
                    catalogoErp.setSituacaoRelacionamento(rsCatalogo.getString("USU_SITREL"));
                    catalogoErp.setVisivelSite(rsCatalogo.getString("USU_VISSIT"));
                    catalogoErp.setCodigoCatalogoEcomerce(rsCatalogo.getLong("ECOCAT"));
                }

                rsCatalogo.close();
            }

        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

        return catalogoErp;
    }

    public void atualizarCatalogoCodigoEcommerce(CatalogoErp catalogoErp) throws ErroSistemaException {
        String updateProduto = "UPDATE sapiens.USU_T075ECI "
                + "                SET USU_CODECO = ? "
                + "              WHERE USU_CODEMP = ? "
                + "                AND USU_CODPRO = ? "
                + "                AND USU_CODCAT = ?";
        Connection conexao = Conexao.getConnection();
        try {
            PreparedStatement psUpdateProduto = conexao.prepareStatement(updateProduto);

            psUpdateProduto.setLong(1, catalogoErp.getCodigoEcommerce());
            psUpdateProduto.setLong(2, catalogoErp.getEmpresaErp());
            psUpdateProduto.setString(3, catalogoErp.getProdutoErp());
            psUpdateProduto.setLong(4, catalogoErp.getCatalogoErp());
            int retorno = psUpdateProduto.executeUpdate();

            psUpdateProduto.close();
            if (retorno != 1) {
                conexao.rollback();
                throw new ErroSistemaException("1 - Erro ao realizar a atualização do produto.");
            } else {
                conexao.commit();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException("2 - Erro ao realizar a atualização do produto.", ex);
        }
    }

    public void atualizarCodigoEcommerce(ProdutoTray produtoTray) throws ErroSistemaException {
        String updateProduto = "UPDATE sapiens.E075PRO "
                + "                SET USU_CODECO = ? "
                + "              WHERE CODPRO = ?";
        Connection conexao = Conexao.getConnection();
        try {
            PreparedStatement psUpdateProduto = conexao.prepareStatement(updateProduto);

            psUpdateProduto.setLong(1, produtoTray.getProdutoId());
            psUpdateProduto.setString(2, produtoTray.getCodigoErp());
            int retorno = psUpdateProduto.executeUpdate();

            psUpdateProduto.close();
            if (retorno != 1) {
                conexao.rollback();
                throw new ErroSistemaException("1 - Erro ao realizar a atualização do produto.");
            } else {
                conexao.commit();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException("2 - Erro ao realizar a atualização do produto.", ex);
        }
    }

    public void atualizarImagemCodigoEcommerce(ProdutoImagemTray produtoImageTray) throws ErroSistemaException {
        String updateProdutoImagem = "UPDATE sapiens.USU_T075ARQ "
                + "                      SET USU_CODECO = ? \n"
                + "                    WHERE USU_CODEMP = ? \n"
                + "                      AND USU_CODPRO = ? \n"
                + "                      AND USU_CODDER = ? \n"
                + "                      AND USU_SEQARQ = ? \n"
                + "                      AND USU_DESARQ = ?";
        Connection conexao = Conexao.getConnection();
        try {
            PreparedStatement psUpdateProdutoImagem = conexao.prepareStatement(updateProdutoImagem);

            psUpdateProdutoImagem.setLong(1, produtoImageTray.getProdutoImagemId());
            psUpdateProdutoImagem.setLong(2, produtoImageTray.getEmpresaErp());
            psUpdateProdutoImagem.setString(3, produtoImageTray.getProdutoErp());
            psUpdateProdutoImagem.setString(4, produtoImageTray.getDerivacaoErp());
            psUpdateProdutoImagem.setLong(5, produtoImageTray.getSequenciaErp());
            psUpdateProdutoImagem.setString(6, produtoImageTray.getDescricaoErp());

            int retorno = psUpdateProdutoImagem.executeUpdate();

            psUpdateProdutoImagem.close();
            if (retorno != 1) {
                conexao.rollback();
                throw new ErroSistemaException("1 - Erro ao realizar a atualização do produto.");
            } else {
                conexao.commit();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException("2 - Erro ao realizar a atualização do produto.", ex);
        }
    }

    public void atualizarInformacoesCodigoEcommerce(ChavePendencia chavePendencia, ProdutoInformacaoTray produtoInformacaoTray) throws ErroSistemaException {
        String updateProdutoInformacoes = ""
                + "                   UPDATE sapiens.USU_T075DPR "
                + "                      SET USU_INFECO = ? "
                + "                    WHERE USU_CODEMP = ? "
                + "                      AND USU_CODPRO = ? ";
        Connection conexao = Conexao.getConnection();
        try {
            PreparedStatement psUpdateProdutoInformacao = conexao.prepareStatement(updateProdutoInformacoes);

            psUpdateProdutoInformacao.setLong(1, produtoInformacaoTray.getInformacaoId());
            psUpdateProdutoInformacao.setLong(2, chavePendencia.getEmpresaErp());
            psUpdateProdutoInformacao.setString(3, chavePendencia.getProdutoErp());
            int retorno = psUpdateProdutoInformacao.executeUpdate();

            psUpdateProdutoInformacao.close();
            if (retorno != 1) {
                conexao.rollback();
                throw new ErroSistemaException("1 - Erro ao realizar a atualização do produto informações.");
            } else {
                conexao.commit();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException("2 - Erro ao realizar a atualização do produto informações.", ex);
        }
    }

}
