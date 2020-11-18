/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.model.dao.relatorio;

import br.com.martinello.matriz.bd.model.dao.Conexao;
import br.com.martinello.matriz.util.Utilitarios;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author luiz.almeida
 */
public class ProdutoRelatorioDAO {

    private String select;
    private XSSFWorkbook workbook = new XSSFWorkbook();
    private XSSFSheet firstSheet;
    private String PathTillProject, hora, arq;
    private FileOutputStream fileOut;
    private int i, rownum, cellnum;
    private Cell cell;
    private Row row;
    private XSSFDataFormat numberFormat;
    private XSSFDataFormat moedaFormat;
    private XSSFFont headerFont;
    private CellStyle headerStyle, headerStyle2, textStyle, textStyle2, numberStyle, moedaStyle;
    private File file;

    public ProdutoRelatorioDAO() {

    }

    public boolean gerarFichaProduto(String codigo) throws ErroSistemaException {

        select = "SELECT USU_T075CAT.USU_DESCAT,\n"
                + "       USU_T075CAS.USU_DESSCA,\n"
                + "       USU_T075CAD.USU_CODPRO,\n"
                + "       E075PRO.DESPRO,\n"
                + "       USU_T075CAR.USU_DESCAR,\n"
                + "       USU_T075CAP.USU_DESCAI,\n"
                + "       NVL(USU_T075CAD.USU_DESCAD,' ') USU_DESCAD\n"
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
                + "   AND E075PRO.CODPRO = USU_T075CAD.USU_CODPRO\n"
                + " INNER JOIN USU_T075CAP\n"
                + "    ON USU_T075CAP.USU_CODCAT = USU_T075CAD.USU_CODCAT\n"
                + "   AND USU_T075CAP.USU_CODSCA = USU_T075CAD.USU_CODSCA\n"
                + "   AND USU_T075CAP.USU_CODCAR = USU_T075CAD.USU_CODCAR\n"
                + "   AND USU_T075CAP.USU_CODCAI = USU_T075CAD.USU_CODCAI\n"
                + " WHERE USU_T075CAD.USU_CODPRO = '" + codigo + "'"
                + "   AND USU_T075CAD.USU_VISSIT = 'S'"
                + " ORDER BY USU_SEQCAR, USU_SEQCAI";
        try {
            PreparedStatement psProRel = Conexao.getConnection().prepareStatement(select);
            psProRel.executeQuery();
            JRResultSetDataSource jrRS = new JRResultSetDataSource(psProRel.getResultSet());
            HashMap parameters = new HashMap();
            parameters.put("CODIGO", codigo);
            parameters.put("VISSIT", "S");
             String jasper = "\\\\serveraplicacao\\SistemaCadastro\\Relatorios\\Produto.jasper";
            String temp = Utilitarios.dataHoraAtual().replaceAll("[/]", "-").replaceAll("[:]", "-").replaceAll("[.]", "-");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters, jrRS);

            String arquivo = "//serveraplicacao/SistemaCadastro/Relatorios/Produto_" + codigo + "_" + temp + ".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, arquivo);
            File file = new File(arquivo);
            Desktop.getDesktop().open(file);

            file.deleteOnExit();
            return true;
        } catch (SQLException | JRException | IOException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());

        }
    }

    public void relacaoProdutosCadastrado(int idUsuario) throws ErroSistemaException {
        carregarStyleExcel();
        select = "SELECT E075PRO.CODPRO,\n"
                + "       E075PRO.DESPRO Produto,\n"
                + "       E075PRO.USU_CODCAT Codigo_Categoria,\n"
                + "       E075PRO.USU_CODSCA Codigo_SubCategoria,\n"
                + "       USU_T075CAT.USU_DESCAT Categoria,\n"
                + "       USU_T075CAS.USU_DESSCA SubCategoria,\n"
                + "       USU_T075DPR.USU_SITDPR Situacao,\n"
                + "       USU_T075DPR.USU_OBSDPR Observacao,\n"
                + "       (SELECT COUNT(USU_T075ARQ.USU_SEQARQ)\n"
                + "          FROM USU_T075ARQ\n"
                + "         WHERE USU_T075ARQ.USU_CODPRO = E075PRO.CODPRO) FOTOS,\n"
                + "       R999USU.NOMUSU Usuario,\n"
                + "       USU_T075DPR.USU_DATGER Data_Cadastro,\n"
                + "       TO_CHAR(TRUNC((USU_T075DPR.USU_HORGER * 60) / 3600), 'FM9900') || ':' ||\n"
                + "       TO_CHAR(TRUNC(MOD((USU_T075DPR.USU_HORGER * 60), 3600) / 60), 'FM00') || ':' ||\n"
                + "       TO_CHAR(MOD((USU_T075DPR.USU_HORGER * 60), 60), 'FM00') Hora_Cadastro,\n"
                + "       (SELECT SUM(QTDEST)\n"
                + "          FROM E210EST\n"
                + "         WHERE E210EST.CODPRO = E075PRO.CODPRO\n"
                + "           AND CODDEP LIKE 'E%') ESTOQUE\n"
                + "  FROM E075PRO\n"
                + " INNER JOIN USU_T075CAT\n"
                + "    ON E075PRO.USU_CODCAT = USU_T075CAT.USU_CODCAT\n"
                + "   AND E075PRO.CODEMP = USU_T075CAT.USU_CODEMP\n"
                + " INNER JOIN USU_T075CAS\n"
                + "    ON USU_T075CAS.USU_CODEMP = E075PRO.CODEMP\n"
                + "   AND USU_T075CAS.USU_CODCAT = E075PRO.USU_CODCAT\n"
                + "   AND USU_T075CAS.USU_CODSCA = E075PRO.USU_CODSCA\n"
                + " INNER JOIN USU_T075DPR\n"
                + "    ON USU_T075DPR.USU_CODEMP = E075PRO.CODEMP\n"
                + "   AND USU_T075DPR.USU_CODPRO = E075PRO.CODPRO\n"
                + "   AND USU_T075DPR.USU_CODCAT = E075PRO.USU_CODCAT\n"
                + "   AND USU_T075DPR.USU_CODSCA = E075PRO.USU_CODSCA\n"
                + " INNER JOIN R999USU\n"
                + "    ON R999USU.CODUSU = USU_T075DPR.USU_USUGER";

        String idUsu = Integer.toString(idUsuario);

        try {
            //  fileOut = new FileOutputStream(PathTillProject + diretorio + "PlanilhaGrid" + hora + ".xlsx");
            arq = "\\\\serveraplicacao\\SistemaCadastro\\Relatorios\\" + "RelacaoProdutosCadastrado" + idUsu + ".xlsx";
            fileOut = new FileOutputStream(arq);
            /* Definir o Cabeçalho da Planilha */
            row = firstSheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellStyle(headerStyle2);
            cell.setCellValue("Relatório Produtos Cadastrados");
            firstSheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    0, //last row  (0-based)
                    0, //first column (0-based)
                    10 //last column  (0-based)
            ));
            // Configurando Header
            row = firstSheet.createRow(1);
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("CodPro");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Produto");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Categoria");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("SubCategoria");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Situação");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Observação");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Fotos");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Usuário");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Cadastro");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Hora Cadastro");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Estoque");
            i = 1;
            try (PreparedStatement psRelProCad = Conexao.getConnection().prepareStatement(select)) {
                ResultSet rsRelProCad = psRelProCad.executeQuery();
                while (rsRelProCad.next()) {
                    i++;
                    row = firstSheet.createRow(i);
                    row.createCell(0).setCellValue(rsRelProCad.getString("CODPRO"));
                    row.createCell(1).setCellValue(rsRelProCad.getString("Produto"));
                    row.createCell(2).setCellValue(rsRelProCad.getString("Codigo_Categoria") + " " + rsRelProCad.getString("Categoria"));
                    row.createCell(3).setCellValue(rsRelProCad.getString("Codigo_SubCategoria") + " " + rsRelProCad.getString("SubCategoria"));
                    row.createCell(4).setCellValue(rsRelProCad.getString("Situacao"));
                    row.createCell(5).setCellValue(rsRelProCad.getString("Observacao"));
                    row.createCell(6).setCellValue(rsRelProCad.getDouble("FOTOS"));
                    row.createCell(7).setCellValue(rsRelProCad.getString("Usuario"));
                    row.createCell(8).setCellValue(Utilitarios.converteDataString(rsRelProCad.getDate("Data_Cadastro"), "dd/MM/yyyy"));
                    row.createCell(9).setCellValue(rsRelProCad.getString("Hora_Cadastro"));
                    row.createCell(10).setCellValue(rsRelProCad.getDouble("Estoque"));

                }
                firstSheet.setColumnWidth(0, 20 * 150);
                firstSheet.setColumnWidth(1, 20 * 800);
                firstSheet.setColumnWidth(2, 20 * 300);
                firstSheet.setColumnWidth(3, 20 * 300);
                firstSheet.setColumnWidth(4, 20 * 200);
                firstSheet.setColumnWidth(5, 20 * 300);
                firstSheet.setColumnWidth(6, 20 * 100);
                firstSheet.setColumnWidth(7, 20 * 200);
                firstSheet.setColumnWidth(8, 20 * 200);
                firstSheet.setColumnWidth(9, 20 * 200);
                firstSheet.setColumnWidth(10, 20 * 150);
                workbook.write(fileOut);
                file = new File(arq);
                Desktop.getDesktop().open(file);
                file.deleteOnExit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroSistemaException(e.getMessage(), e.getCause());
        } finally {
            try {
                fileOut.flush();
                fileOut.close();

            } catch (Exception e) {
                e.printStackTrace();
                throw new ErroSistemaException(e.getMessage(), e.getCause());
            }
        }
    }

    public void relacaoProdutosNaoCadastrado(int idUsuario) throws ErroSistemaException {
        carregarStyleExcel();
        select = "SELECT CODPRO,\n"
                + "       DESPRO,\n"
                + "       IDEPRO,\n"
                + "       (SELECT SUM(QTDEST)\n"
                + "          FROM E210EST\n"
                + "         WHERE E210EST.CODPRO = E075PRO.CODPRO\n"
                + "           AND CODDEP LIKE 'E%') ESTOQUE\n"
                + "  FROM E075PRO\n"
                + " WHERE USU_STAPRO in ('L')\n"
                + "   AND NOT exists\n"
                + " (SELECT 1 FROM USU_T075DPR WHERE USU_T075DPR.USU_CODPRO = CODPRO)";
        String idUsu = Integer.toString(idUsuario);
        try {
            arq = "\\\\serveraplicacao\\SistemaCadastro\\Relatorios\\" + "RelacaoProdutosNaoCadastrado" + idUsu + ".xlsx";
            fileOut = new FileOutputStream(arq);
            /* Definir o Cabeçalho da Planilha */
            row = firstSheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellStyle(headerStyle2);
            cell.setCellValue("Relatório Produtos Não Cadastrado");
            firstSheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    0, //last row  (0-based)
                    0, //first column (0-based)
                    3 //last column  (0-based)
            ));
            // Configurando Header
            row = firstSheet.createRow(1);
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("CodPro");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Produto");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Código EAN");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Estoque");
            i = 1;
            try (PreparedStatement psRelProCad = Conexao.getConnection().prepareStatement(select)) {
                ResultSet rsRelProCad = psRelProCad.executeQuery();
                while (rsRelProCad.next()) {
                    i++;
                    row = firstSheet.createRow(i);
                    row.createCell(0).setCellValue(rsRelProCad.getString("CODPRO"));
                    row.createCell(1).setCellValue(rsRelProCad.getString("DESPRO"));
                    row.createCell(2).setCellValue(rsRelProCad.getString("IDEPRO"));
                    row.createCell(3).setCellValue(rsRelProCad.getDouble("ESTOQUE"));

                }
                firstSheet.setColumnWidth(0, 20 * 200);
                firstSheet.setColumnWidth(1, 20 * 800);
                firstSheet.setColumnWidth(2, 20 * 200);
                firstSheet.setColumnWidth(3, 20 * 200);
                workbook.write(fileOut);
                file = new File(arq);
                Desktop.getDesktop().open(file);
                file.deleteOnExit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroSistemaException(e.getMessage(), e.getCause());
        } finally {
            try {
                fileOut.flush();
                fileOut.close();

            } catch (Exception e) {
                e.printStackTrace();
                throw new ErroSistemaException(e.getMessage(), e.getCause());
            }
        }
    }

    public void carregarStyleExcel() {
        workbook = new XSSFWorkbook();
        firstSheet = workbook.createSheet("Aba1");
        /* pegar o diretório do usuário e criar um arquivo com o determinado nome */
        PathTillProject = System.getProperty("user.home");
        fileOut = null;
        firstSheet.setDefaultColumnWidth(15);
        firstSheet.setDefaultRowHeight((short) 300);
        rownum = 0;
        cellnum = 0;
        i = 0;

        //Configurando estilos de células (Cores, alinhamento, formatação, etc..)
        numberFormat = workbook.createDataFormat();
        moedaFormat = workbook.createDataFormat();
        headerFont = workbook.createFont();
        headerFont.setBoldweight(headerFont.BOLDWEIGHT_BOLD);
        headerFont.setFontHeightInPoints((short) 14);
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headerStyle2 = workbook.createCellStyle();
        headerStyle2.setFont(headerFont);
        headerStyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerStyle2.setAlignment(CellStyle.ALIGN_CENTER);
        headerStyle2.setVerticalAlignment(CellStyle.ALIGN_CENTER);

        textStyle = workbook.createCellStyle();
        textStyle.setAlignment(CellStyle.ALIGN_CENTER);
        textStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        textStyle2 = workbook.createCellStyle();
        textStyle2.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        textStyle2.setAlignment(CellStyle.ALIGN_LEFT);
        textStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        numberStyle = workbook.createCellStyle();
        numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
        numberStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        moedaStyle = workbook.createCellStyle();
        moedaStyle.setDataFormat(moedaFormat.getFormat("R$#,##0.00;[Red]R$#,##0.00"));
        moedaStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    }
}
