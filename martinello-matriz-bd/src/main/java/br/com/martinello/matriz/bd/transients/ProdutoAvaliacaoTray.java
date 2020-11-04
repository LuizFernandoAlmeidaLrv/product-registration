/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.transients;

import java.util.Date;

/**
 *
 * @author rafael.rosar
 */
public class ProdutoAvaliacaoTray {

    private Long produtoVarianteId;
    private String comentario;
    private Long avaliacao;
    private Long usuarioId;
    private Date dataAvaliacao;
    private String nome;
    private String email;
    private String status;

    public Long getProdutoVarianteId() {
        return produtoVarianteId;
    }

    public void setProdutoVarianteId(Long produtoVarianteId) {
        this.produtoVarianteId = produtoVarianteId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Long avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Date getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(Date dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
