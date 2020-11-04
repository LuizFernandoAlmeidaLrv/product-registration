/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.transients;

/**
 *
 * @author rafael.rosar
 */
public class LojaFisicaTray {

    private Long lojaId;
    private String nome;
    private Long ddd;
    private String telefone;
    private String email;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private Long estadoId;
    private Long prazoEntrega;
    private Long prazoMaximoRetirada;
    private Boolean ativo;
    private Boolean valido;
    private String textoComplementar;
    private Boolean retirarNaLoja;
    private Long latitude;
    private Long longitude;
    private Long centroDistribuicaoId;

    public Long getLojaId() {
        return lojaId;
    }

    public void setLojaId(Long lojaId) {
        this.lojaId = lojaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getDdd() {
        return ddd;
    }

    public void setDdd(Long ddd) {
        this.ddd = ddd;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Long getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Long estadoId) {
        this.estadoId = estadoId;
    }

    public Long getPrazoEntrega() {
        return prazoEntrega;
    }

    public void setPrazoEntrega(Long prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }

    public Long getPrazoMaximoRetirada() {
        return prazoMaximoRetirada;
    }

    public void setPrazoMaximoRetirada(Long prazoMaximoRetirada) {
        this.prazoMaximoRetirada = prazoMaximoRetirada;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getValido() {
        return valido;
    }

    public void setValido(Boolean valido) {
        this.valido = valido;
    }

    public String getTextoComplementar() {
        return textoComplementar;
    }

    public void setTextoComplementar(String textoComplementar) {
        this.textoComplementar = textoComplementar;
    }

    public Boolean getRetirarNaLoja() {
        return retirarNaLoja;
    }

    public void setRetirarNaLoja(Boolean retirarNaLoja) {
        this.retirarNaLoja = retirarNaLoja;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getCentroDistribuicaoId() {
        return centroDistribuicaoId;
    }

    public void setCentroDistribuicaoId(Long centroDistribuicaoId) {
        this.centroDistribuicaoId = centroDistribuicaoId;
    }

}
