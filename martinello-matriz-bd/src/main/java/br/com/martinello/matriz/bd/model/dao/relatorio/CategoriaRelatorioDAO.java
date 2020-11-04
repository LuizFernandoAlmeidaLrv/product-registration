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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class CategoriaRelatorioDAO {

    public String select;
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

    public void relacaoCategoria(int idUsuario) throws ErroSistemaException {
        carregarStyleExcel();
        select = "SELECT USU_CODEMP,\n"
                + "          USU_CODCAT,\n"
                + "          USU_DESCAT,\n"
                + "          USU_SITCAT,\n"
                + "          USU_OBSCAT,\n"
                + "          USU_USUGER,\n"
                + "          USU_DATGER,\n"
                + "          USU_HORGER,\n"
                + "          USU_USUALT,\n"
                + "          USU_DATALT,\n"
                + "          USU_HORALT,\n"
                + "          USU_OBSALT,\n"
                + "          NOMUSU\n"
                + "     FROM USU_T075CAT\n"
                + "    INNER JOIN R999USU\n"
                + "       ON CODUSU = USU_USUGER \n";

        idUsu = Integer.toString(idUsuario);
        try {
            arq = "\\\\srvaplicacao\\SistemaNegativador\\Cadastro\\Relatorios\\" + "RelacaoCategoria" + idUsu + ".xlsx";
            fileOut = new FileOutputStream(arq);
            /* Definir o Cabeçalho da Planilha */
            row = firstSheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellStyle(headerStyle2);
            cell.setCellValue("Relatório Categorias");
            firstSheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    0, //last row  (0-based)
                    0, //first column (0-based)
                    2 //last column  (0-based)
            ));
            // Configurando Header
            row = firstSheet.createRow(1);
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Cód. Cat");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Categoria");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("DataCadastro");
            i = 1;
            try (PreparedStatement psRelProCad = Conexao.getConnection().prepareStatement(select)) {
                ResultSet rsRelCatCad = psRelProCad.executeQuery();
                while (rsRelCatCad.next()) {
                    i++;
                    cellnum = 0;
                    row = firstSheet.createRow(i);
                    cell = row.createCell(cellnum++);                  
                    cell.setCellValue(rsRelCatCad.getInt("USU_CODCAT"));
                    cell = row.createCell(cellnum++);
                    cell.setCellValue(rsRelCatCad.getString("USU_DESCAT"));
                    cell = row.createCell(cellnum++);
                    cell.setCellValue(Utilitarios.converteDataString(rsRelCatCad.getDate("USU_DATGER"), "dd/MM/yyyy"));
                }
                firstSheet.setColumnWidth(0, 20 * 200);
                firstSheet.setColumnWidth(1, 20 * 800);
                firstSheet.setColumnWidth(2, 20 * 200);
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
        numberStyle.setDataFormat(numberFormat.getFormat("###.###.##0"));
        numberStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        moedaStyle = workbook.createCellStyle();
        moedaStyle.setDataFormat(moedaFormat.getFormat("R$#,##0.00;[Red]R$#,##0.00"));
        moedaStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    }

}
