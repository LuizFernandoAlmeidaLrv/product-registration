/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.control;

import br.com.martinello.matriz.bd.model.dao.UsuarioLoginDAO;
import br.com.martinello.matriz.bd.model.domain.Usuario;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import java.sql.SQLException;
import java.time.ZoneId;

/**
 *
 * @author luiz.almeida
 */
public class UsuarioControl {

    ZoneId fusoHorarioDeSaoPaulo;
    protected Usuario usuario;
    private UsuarioLoginDAO usuarioDAO = new UsuarioLoginDAO();
    protected Usuario usuarioLogin;

//    public Usuario find(Usuario usuarioFind) throws ErroSistemaException {
//        EntityManager em = ConexaoPostgres.getEntityManager();
//        try {
//            CrudDao<Usuario> usuarioDao = new CrudDaoImpl<>(new UsuarioDaoImpl(em));
//
//            usuarioFind = usuarioDao.find(usuarioFind);
//
//            return usuarioFind;
//        } catch (PersistenceException e) {
//            throw new ErroSistemaException(e.getMessage());
//        } finally {
//            em.close();
//        }
//    }
  

   

    public Usuario login(String usuario) throws ErroSistemaException {
        try {
            UsuarioLoginDAO usuarioDao = new UsuarioLoginDAO();
            Usuario usuarioModel = usuarioDao.UsuarioLogin(usuario);

            return usuarioModel;

        } catch (ErroSistemaException e) {
            throw new ErroSistemaException(e.getMessage());
        } finally {
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void salvarAtualizarLogin() throws ErroSistemaException, SQLException {
        boolean novoUsuario = usuario.getIdUsuario() == 0;
        usuario = usuarioDAO.InsetLogin(usuario);
    }

    public void AtualizarLogin() throws ErroSistemaException, SQLException {
        boolean novoUsuario = usuario.getIdUsuario() == 0;
        usuario = usuarioDAO.encerrarLogin(usuario);
    }

    public void AtualizaLogin() throws ErroSistemaException {
        usuario = usuarioDAO.AtualizarLogin(usuario);
    }

}
