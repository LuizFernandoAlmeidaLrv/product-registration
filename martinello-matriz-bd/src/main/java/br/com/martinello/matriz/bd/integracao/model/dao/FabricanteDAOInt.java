/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.integracao.model.dao;

import br.com.martinello.matriz.bd.model.dao.Conexao;
import br.com.martinello.matriz.bd.transients.ChavePendencia;
import br.com.martinello.matriz.bd.transients.FabricanteTray;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.nio.file.Path;
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
public class FabricanteDAOInt {

    private PreparedStatement psSelectFabricante;
    private ResultSet rsSelectFabricante;
    private String selectFabricante, updateFabricante;

    public FabricanteDAOInt() {

    }

    public FabricanteTray buscarFabricanteIntegrarEcommerce(ChavePendencia chavePendencia) throws ErroSistemaException {
        FabricanteTray fabricanteTray = new FabricanteTray();

        try {
            selectFabricante = "SELECT CODMAR,"
                    + "                NOMMAR,"
                    + "                USU_CODECO "
                    + "           FROM sapiens.E076MAR "
                    + "          WHERE CODMAR = ? ";
            psSelectFabricante = Conexao.getConnection().prepareStatement(selectFabricante);
            psSelectFabricante.setString(1, chavePendencia.getMarcaErp());
            
            rsSelectFabricante = psSelectFabricante.executeQuery();
            while (rsSelectFabricante.next()) {
                fabricanteTray = new FabricanteTray();
                fabricanteTray.setCodigoErp(rsSelectFabricante.getString("CODMAR"));
                fabricanteTray.setFabricanteId(rsSelectFabricante.getLong("USU_CODECO"));
                fabricanteTray.setNome(rsSelectFabricante.getString("NOMMAR"));
            }
            psSelectFabricante.close();
            rsSelectFabricante.close();
        } catch (SQLException | ErroSistemaException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return fabricanteTray;
    }

    public void atualizarCodigoEcommerce(FabricanteTray fabricanteTray) throws ErroSistemaException {
        updateFabricante = "UPDATE sapiens.E076MAR "
                + "            SET USU_CODECO = ? "
                + "          WHERE CODMAR = ?";

        Connection conexao = Conexao.getConnection();
        try {
            PreparedStatement psUpdateMarca = conexao.prepareStatement(updateFabricante);
            psUpdateMarca.setLong(1, fabricanteTray.getFabricanteId());
            psUpdateMarca.setString(2, fabricanteTray.getCodigoErp());
            int retorno = psUpdateMarca.executeUpdate();

            psUpdateMarca.close();
            if (retorno != 1) {
                conexao.rollback();
                throw new ErroSistemaException("1 - Erro ao realizar a atualização do produto.");
            } else {
                conexao.commit();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException("2 - Erro ao realizar a atualização da marca.", ex);
        }
    }

}
