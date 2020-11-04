/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.model.dao;

import br.com.martinello.matriz.bd.control.UsuarioControl;
import br.com.martinello.matriz.bd.model.domain.Usuario;
import br.com.martinello.matriz.bd.model.domain.UsuarioLogin;
import br.com.martinello.matriz.util.Utilitarios;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;

import java.util.Date;
import java.util.List;

//import sun.misc.BASE64Encoder;
/**
 *
 * @author Sidnei
 */
public class UsuarioLoginDAO {

    public PreparedStatement psLogin, psSelectUser, psInsertLogin, psSelectUsuarios;
    public ResultSet rsLogin, rsSelectUser, rsInsertLogin, rsSelectUsuarios;
    public boolean login;
    public List<Usuario> lUsuario;
    public List<Usuario> lUsuarioModel;
    public String sqlSelectUser, sqlInsertLogin, sqlUpdateLogin, sqlSelectLogins, sqlSelectUsuarios;
    int resultado;
    private long idLog;
    protected UsuarioControl usuarioControl;

    public UsuarioLoginDAO() {
        idLog = 0;
    }

    public Usuario UsuarioLogin(String usuario) throws ErroSistemaException {
        sqlSelectUser = "SELECT R999USU_CODUSU, \n"
                + "             R910USU_NOMCOM, \n"
                + "             R999USU_NOMUSU \n"
                + "        FROM EW99USU \n"
                + "       WHERE R999USU_NOMUSU = ?";
        Usuario usuarioModel = new Usuario();
        try {
            psSelectUser = Conexao.getConnection().prepareStatement(sqlSelectUser);
            psSelectUser.setString(1, usuario);
            System.out.println("Slect:" + sqlSelectUser);
            rsSelectUser = psSelectUser.executeQuery();
            if (rsSelectUser.next()) {
                usuarioModel = new Usuario();
                usuarioModel.setNome(rsSelectUser.getString("R910USU_NOMCOM"));
                usuarioModel.setLogin(rsSelectUser.getString("R999USU_NOMUSU"));
                usuarioModel.setIdUsuario(rsSelectUser.getInt("R999USU_CODUSU"));
                UsuarioLogin login = new UsuarioLogin();
                login.setUsuario(usuarioModel);
                login.setDataLogin(new Date());
                login.setSistemaOperacional(System.getProperty("os.name"));
                login.setNomeUsuarioSO(System.getProperty("user.name"));
                login.setNomeEstacao(InetAddress.getLocalHost().getHostName());
                usuarioModel.getLogins().add(login);
                usuarioControl = new UsuarioControl();
                usuarioControl.setUsuario(usuarioModel);
            }
            psSelectUser.close();
            rsSelectUser.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLRecoverableException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return usuarioModel;

    }

    public Usuario InsetLogin(Usuario usuario) throws ErroSistemaException {
        Connection connection = Conexao.getConnection();
        sqlInsertLogin = "INSERT INTO USU_T999USU (USU_USULOG, USU_DATLOG, USU_DESEST, USU_DESSOP, USU_USUSOP) VALUES(?, ?, ?, ?, ?)";
        try {
            connection.setAutoCommit(false);
            psInsertLogin = connection.prepareStatement(sqlInsertLogin);
            psInsertLogin.setLong(1, usuario.getIdUsuario());
            psInsertLogin.setDate(2, Utilitarios.converteData(usuario.getLogins().get(0).getDataLogin()));
            psInsertLogin.setString(3, usuario.getLogins().get(0).getNomeEstacao());
            psInsertLogin.setString(4, usuario.getLogins().get(0).getSistemaOperacional());
            psInsertLogin.setString(5, usuario.getLogins().get(0).getNomeUsuarioSO());
            resultado = psInsertLogin.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
            try {
                if (resultado == -1) {
                    psInsertLogin.close();
                    connection.rollback();
                } else if (resultado == 1) {
                    psInsertLogin.close();
                    connection.commit();
                }
            } catch (SQLException ex) {
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
        }
        return usuario;
    }

    public Usuario encerrarLogin(Usuario usuario) throws ErroSistemaException {
        sqlUpdateLogin = "DELETE FROM USU_T999USU WHERE USU_USULOG = ?";
        try {
            psLogin = Conexao.getConnection().prepareStatement(sqlUpdateLogin);
            psLogin.setLong(1, usuario.getIdUsuario());
            psLogin.executeUpdate();
            Conexao.getConnection().commit();
            psLogin.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
        }

        return usuario;
    }

    public Usuario AtualizarLogin(Usuario usuario) throws ErroSistemaException {
        String sqlAtualizaLogin = "UPDATE USU_T999USU SET USU_DATLOG = ? WHERE USU_USULOG = ?";
        try {
            psLogin = Conexao.getConnection().prepareStatement(sqlAtualizaLogin);
            psLogin.setDate(1, Utilitarios.converteData(new Date()));
            psLogin.setLong(2, usuario.getIdUsuario());
            psLogin.executeUpdate();
            Conexao.getConnection().commit();
            psLogin.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
        }

        return usuario;
    }

}
