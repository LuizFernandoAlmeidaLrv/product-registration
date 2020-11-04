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
public class SubCategoriaRelatorioDAO {

    public String select;
    public String subCat;
    private XSSFWorkbook workbook = new XSSFWorkbook();
    private XSSFSheet firstSheet;
    private String PathTillProject, hora, arq, idUsu;
    private FileOutputStream fileOut;
    private int i, rownum, cellnum;
    private Cell cell;
    private Row row;
    private XSSFDataFormat numberFormat;
    private XSSFDataFormat moedaFormat;
    private XSSFFont headerFont;
    private CellStyle headerStyle, headerStyle2, textStyle, textStyle2, numberStyle, moedaStyle;
    private File file;

    public void relacaoSubCategoria(int idUsuario) throws ErroSistemaException {
        select = "SELECT USU_T075CAS.USU_CODEMP,\n"
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
                + "    AND USU_T075CAS.USU_CODCAT = USU_T075CAT.USU_CODCAT \n";

        idUsu = Integer.toString(idUsuario);
        carregarStyleExcel();
        try {
            arq = "\\\\srvaplicacao\\SistemaNegativador\\Cadastro\\Relatorios\\" + "RelacaoSubCategoria" + idUsu + ".xlsx";
            fileOut = new FileOutputStream(arq);
            /* Definir o Cabeçalho da Planilha */
            row = firstSheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellStyle(headerStyle2);
            cell.setCellValue("Relatório SubCategoria");
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
            cell.setCellValue("Categoria");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Cód. SubCat");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("SubCategoria");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("DataCadastro");
            i = 1;
            try (PreparedStatement psRelProCad = Conexao.getConnection().prepareStatement(select)) {
                ResultSet rsRelSubCatCad = psRelProCad.executeQuery();
                while (rsRelSubCatCad.next()) {
                    i++;
                    cellnum = 0;
                    row = firstSheet.createRow(i);
                    cell = row.createCell(cellnum++);
                    cell.setCellValue(rsRelSubCatCad.getString("USU_DESCAT"));
                    cell = row.createCell(cellnum++);
                    cell.setCellValue(rsRelSubCatCad.getInt("USU_CODSCA"));
                    cell = row.createCell(cellnum++);
                    cell.setCellValue(rsRelSubCatCad.getString("USU_DESSCA"));
                    cell = row.createCell(cellnum++);
                    cell.setCellValue(Utilitarios.converteDataString(rsRelSubCatCad.getDate("USU_DATGER"), "dd/MM/yyyy"));
                }
                firstSheet.setColumnWidth(0, 20 * 200);
                firstSheet.setColumnWidth(1, 20 * 200);
                firstSheet.setColumnWidth(2, 20 * 800);
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

    public boolean gerarFichaSubCategoria(int idCategoria, int idSubCategoria, String visSit, String sit) throws ErroSistemaException {
        select = "SELECT USU_T075CAP.USU_CODEMP,\n"
                + "                           USU_T075CAP.USU_CODCAI,\n"
                + "                           USU_T075CAP.USU_DESCAI,\n"
                + "                           USU_T075CAP.USU_CODCAT,\n"
                + "                           USU_T075CAT.USU_DESCAT,\n"
                + "                           USU_T075CAP.USU_CODSCA,\n"
                + "                           USU_T075CAS.USU_DESSCA,\n"
                + "                           USU_T075CAP.USU_CODCAR,\n"
                + "                           USU_T075CAR.USU_DESCAR,\n"
                + "                           USU_T075CAP.USU_SEQCAI,\n"
                + "                           USU_T075CAP.USU_SITCAI,\n"
                + "                           USU_T075CAP.USU_OBSCAI,\n"
                + "                           USU_T075CAP.USU_VISSIT,\n"
                + "                           USU_T075CAP.USU_REPITE,\n"
                + "                           USU_T075CAP.USU_USUGER,\n"
                + "                           R999USU.NOMUSU,\n"
                + "                           USU_T075CAP.USU_DATGER,\n"
                + "                           USU_T075CAP.USU_HORGER,\n"
                + "                           USU_T075CAP.USU_USUALT,\n"
                + "                           USU_T075CAP.USU_DATALT,\n"
                + "                           USU_T075CAP.USU_HORALT,\n"
                + "                           USU_T075CAP.USU_OBSALT\n"
                + "                      FROM USU_T075CAP\n"
                + "                     INNER JOIN R999USU\n"
                + "                        ON R999USU.CODUSU = USU_T075CAP.USU_USUGER\n"
                + "                     INNER JOIN USU_T075CAR\n"
                + "                        ON USU_T075CAP.USU_CODCAR = USU_T075CAR.USU_CODCAR\n"
                + "                       AND USU_T075CAP.USU_CODCAT = USU_T075CAR.USU_CODCAT\n"
                + "                       AND USU_T075CAP.USU_CODSCA = USU_T075CAR.USU_CODSCA\n"
                + "                     INNER JOIN USU_T075CAT\n"
                + "                        ON USU_T075CAP.USU_CODCAT = USU_T075CAT.USU_CODCAT\n"
                + "                     INNER JOIN USU_T075CAS\n"
                + "                        ON USU_T075CAP.USU_CODCAR = USU_T075CAR.USU_CODCAR\n"
                + "                       AND USU_T075CAP.USU_CODCAT = USU_T075CAS.USU_CODCAT\n"
                + "                       AND USU_T075CAP.USU_CODSCA = USU_T075CAS.USU_CODSCA\n"
                + "                     WHERE USU_T075CAP.USU_CODCAT = " + idCategoria + "\n"
                + "                       AND USU_T075CAP.USU_CODSCA = " + idSubCategoria + "\n"
                + "                       AND USU_T075CAP.USU_VISSIT = '" + visSit + "'\n"
                + "                       AND USU_T075CAP.USU_SITCAI = '" + sit + "'\n"
                + "                     ORDER BY USU_T075CAP.USU_CODCAR ,\n"
                + "                              USU_T075CAP.USU_SEQCAI";
        try {
            PreparedStatement psProRel = Conexao.getConnection().prepareStatement(select);
            psProRel.executeQuery();
            JRResultSetDataSource jrRS = new JRResultSetDataSource(psProRel.getResultSet());
            HashMap parameters = new HashMap();
            parameters.put("CODIGO_CAT", idCategoria);
            parameters.put("CODIGO_SUBCAT", idSubCategoria);
            parameters.put("VISSIT", visSit);
            parameters.put("SIT", sit);
            subCat = Integer.toString(idSubCategoria);
            //  InputStream caminho = this.getClass().getResourceAsStream("\\\\srvaplicacao\\SistemaNegativador\\Cadastro\\Relatorios\\Produto.jasper");
            String jasper = "\\\\srvaplicacao\\SistemaNegativador\\Cadastro\\Relatorios\\Caracteristicas.jasper";
            String temp = Utilitarios.dataHoraAtual().replaceAll("[/]", "-").replaceAll("[:]", "-").replaceAll("[.]", "-");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters, jrRS);
            String arquivo = "//srvaplicacao/SistemaNegativador/Cadastro/Relatorios/SubCategoria_" + subCat + "_" + temp + ".pdf";
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
        numberStyle.setDataFormat(numberFormat.getFormat("###.###.##0"));
        numberStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        moedaStyle = workbook.createCellStyle();
        moedaStyle.setDataFormat(moedaFormat.getFormat("R$#,##0.00;[Red]R$#,##0.00"));
        moedaStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    }
}
