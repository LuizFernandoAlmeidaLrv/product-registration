/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.model.dao;

import br.com.martinello.matriz.bd.model.domain.Arquivo;
import br.com.martinello.matriz.util.Utilitarios;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

/**
 *
 * @author luiz.almeida
 */
public class ArquivoDAO {

    private String selectArquivo, insertArquivo, deleteArquivo, updateArquivo;
    private PreparedStatement psSelectArquivo, psInsertArquivo, psUpdateArquivo, psDeleteArquivo;
    private ResultSet rsSelectArquivo, rsInsertArquivo, rsUpdateArquivo, rsDeleteArquivo;
    private List<Arquivo> lArquivo;
    private Arquivo arquivo;

    private int resultado, min, hora;
    private String data, sHora, codPro;

    public ArquivoDAO() {

    }

    public List<Arquivo> listarArquivos(String codigo) throws ErroSistemaException {
        try {
            lArquivo = new LinkedList<>();
            selectArquivo = "SELECT USU_CODEMP, \n"
                    + "             USU_CODPRO, \n"
                    + "             USU_CODDER, \n"
                    + "             USU_SEQARQ, \n"
                    + "             USU_DESARQ, \n"
                    + "             USU_ARQPRI, \n"
                    + "             USU_VISSIT, \n"
                    + "             USU_DESANT, \n"
                    + "             USU_EXTARQ  \n"
                    + "             FROM USU_T075ARQ \n"
                    + "             WHERE USU_CODPRO = '" + codigo + "'\n";
            psSelectArquivo = Conexao.getConnection().prepareStatement(selectArquivo);
            rsSelectArquivo = psSelectArquivo.executeQuery();
            while (rsSelectArquivo.next()) {
                arquivo = new Arquivo();
                arquivo.setAlterado("N");
                arquivo.setDerProduto(rsSelectArquivo.getString("USU_CODDER"));
                arquivo.setProduto(rsSelectArquivo.getString("USU_CODPRO"));
                arquivo.setNome(rsSelectArquivo.getString("USU_DESANT"));
                arquivo.setNovoNome(rsSelectArquivo.getString("USU_DESARQ"));
                arquivo.setVisivel(rsSelectArquivo.getString("USU_VISSIT"));
                arquivo.setPrincipal(rsSelectArquivo.getString("USU_ARQPRI"));
                arquivo.setExtencao(rsSelectArquivo.getString("USU_EXTARQ"));
                arquivo.setSequencia(rsSelectArquivo.getInt("USU_SEQARQ"));
                lArquivo.add(arquivo);
            }
        } catch (SQLException ex) {
           throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (ErroSistemaException ex) {
           throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lArquivo;
    }

    public void variaveis() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Cuiaba"));
        hora = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        sHora = hora + ":" + min;
        data = Utilitarios.converteDataString(new Date(), "dd/MM/yyyy");
    }
}
