/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.util.integracao;

import java.util.Date;

/**
 *
 * @author rafael.rosar
 */
public class Credencial {

    private String urlConexao;
    private String loja;
    private String token;
    private String usuario;
    private String senha;
    private Date dataExpiracaoToken;

    public String getUrlConexao() {
        return urlConexao;
    }

    public void setUrlConexao(String urlConexao) {
        this.urlConexao = urlConexao;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataExpiracaoToken() {
        return dataExpiracaoToken;
    }

    public void setDataExpiracaoToken(Date dataExpiracaoToken) {
        this.dataExpiracaoToken = dataExpiracaoToken;
    }

}
