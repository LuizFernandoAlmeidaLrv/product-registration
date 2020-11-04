/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.transients;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author rafael.rosar
 */
public class ProdutoTray {

    private Long produtoId;
    private Long produtoVarianteId;
    private String idPaiExterno;
    private String idVinculoExterno;
    private String sku;
    private String nome;
    private String nomeProdutoPai;
    private String exibirMatrizAtributos;
    private Boolean contraProposta;
    private String fabricante;
    private String autor;
    private String editora;
    private String colecao;
    private String genero;
    private Double precoCusto;
    private Double precoDe;
    private Double precoPor;
    private Double fatorMultiplicadorPreco;
    private Double prazoEntrega;
    private Boolean valido;
    private Boolean exibirSite;
    private String freteGratis;
    private Boolean trocaGratis;
    private Long peso;
    private Long altura;
    private Long comprimento;
    private Long largura;
    private Double garantia;
    private Boolean isTelevendas;
    private String ean;
    private String localizacaoEstoque;
    private List<ListaAtacadoTray> listaAtacado = new LinkedList<>();
    private List<EstoqueTray> estoque = new LinkedList<>();
    private List<ListaAtributosTray> listaAtributos = new LinkedList<>();
    private Long quantidadeMaximaCompraUnidade;
    private Long quantidadeMinimaCompraUnidade;
    private String condicao;
    private String urlVideo;
    private Boolean spot;
    private Boolean paginaProduto;
    private Boolean marketplace;
    private Boolean somenteParceiros;
    private Boolean buyBox;
    private String codigoErp;

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Long getProdutoVarianteId() {
        return produtoVarianteId;
    }

    public void setProdutoVarianteId(Long produtoVarianteId) {
        this.produtoVarianteId = produtoVarianteId;
    }

    public String getIdPaiExterno() {
        return idPaiExterno;
    }

    public void setIdPaiExterno(String idPaiExterno) {
        this.idPaiExterno = idPaiExterno;
    }

    public String getIdVinculoExterno() {
        return idVinculoExterno;
    }

    public void setIdVinculoExterno(String idVinculoExterno) {
        this.idVinculoExterno = idVinculoExterno;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeProdutoPai() {
        return nomeProdutoPai;
    }

    public void setNomeProdutoPai(String nomeProdutoPai) {
        this.nomeProdutoPai = nomeProdutoPai;
    }

    public String getExibirMatrizAtributos() {
        return exibirMatrizAtributos;
    }

    public void setExibirMatrizAtributos(String exibirMatrizAtributos) {
        this.exibirMatrizAtributos = exibirMatrizAtributos;
    }

    public Boolean getContraProposta() {
        return contraProposta;
    }

    public void setContraProposta(Boolean contraProposta) {
        this.contraProposta = contraProposta;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getColecao() {
        return colecao;
    }

    public void setColecao(String colecao) {
        this.colecao = colecao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(Double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public Double getPrecoDe() {
        return precoDe;
    }

    public void setPrecoDe(Double precoDe) {
        this.precoDe = precoDe;
    }

    public Double getPrecoPor() {
        return precoPor;
    }

    public void setPrecoPor(Double precoPor) {
        this.precoPor = precoPor;
    }

    public Double getFatorMultiplicadorPreco() {
        return fatorMultiplicadorPreco;
    }

    public void setFatorMultiplicadorPreco(Double fatorMultiplicadorPreco) {
        this.fatorMultiplicadorPreco = fatorMultiplicadorPreco;
    }

    public Double getPrazoEntrega() {
        return prazoEntrega;
    }

    public void setPrazoEntrega(Double prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }

    public Boolean getValido() {
        return valido;
    }

    public void setValido(Boolean valido) {
        this.valido = valido;
    }

    public Boolean getExibirSite() {
        return exibirSite;
    }

    public void setExibirSite(Boolean exibirSite) {
        this.exibirSite = exibirSite;
    }

    public String getFreteGratis() {
        return freteGratis;
    }

    public void setFreteGratis(String freteGratis) {
        this.freteGratis = freteGratis;
    }

    public Boolean getTrocaGratis() {
        return trocaGratis;
    }

    public void setTrocaGratis(Boolean trocaGratis) {
        this.trocaGratis = trocaGratis;
    }

    public Long getPeso() {
        return peso;
    }

    public void setPeso(Long peso) {
        this.peso = peso;
    }

    public Long getAltura() {
        return altura;
    }

    public void setAltura(Long altura) {
        this.altura = altura;
    }

    public Long getComprimento() {
        return comprimento;
    }

    public void setComprimento(Long comprimento) {
        this.comprimento = comprimento;
    }

    public Long getLargura() {
        return largura;
    }

    public void setLargura(Long largura) {
        this.largura = largura;
    }

    public Double getGarantia() {
        return garantia;
    }

    public void setGarantia(Double garantia) {
        this.garantia = garantia;
    }

    public Boolean getIsTelevendas() {
        return isTelevendas;
    }

    public void setIsTelevendas(Boolean isTelevendas) {
        this.isTelevendas = isTelevendas;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getLocalizacaoEstoque() {
        return localizacaoEstoque;
    }

    public void setLocalizacaoEstoque(String localizacaoEstoque) {
        this.localizacaoEstoque = localizacaoEstoque;
    }

    public List<ListaAtacadoTray> getListaAtacado() {
        return listaAtacado;
    }

    public void setListaAtacado(List<ListaAtacadoTray> listaAtacado) {
        this.listaAtacado = listaAtacado;
    }

    public List<EstoqueTray> getEstoque() {
        return estoque;
    }

    public void setEstoque(List<EstoqueTray> estoque) {
        this.estoque = estoque;
    }

    public List<ListaAtributosTray> getListaAtributos() {
        return listaAtributos;
    }

    public void setListaAtributos(List<ListaAtributosTray> listaAtributos) {
        this.listaAtributos = listaAtributos;
    }

    public Long getQuantidadeMaximaCompraUnidade() {
        return quantidadeMaximaCompraUnidade;
    }

    public void setQuantidadeMaximaCompraUnidade(Long quantidadeMaximaCompraUnidade) {
        this.quantidadeMaximaCompraUnidade = quantidadeMaximaCompraUnidade;
    }

    public Long getQuantidadeMinimaCompraUnidade() {
        return quantidadeMinimaCompraUnidade;
    }

    public void setQuantidadeMinimaCompraUnidade(Long quantidadeMinimaCompraUnidade) {
        this.quantidadeMinimaCompraUnidade = quantidadeMinimaCompraUnidade;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public Boolean getSpot() {
        return spot;
    }

    public void setSpot(Boolean spot) {
        this.spot = spot;
    }

    public Boolean getPaginaProduto() {
        return paginaProduto;
    }

    public void setPaginaProduto(Boolean paginaProduto) {
        this.paginaProduto = paginaProduto;
    }

    public Boolean getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Boolean marketplace) {
        this.marketplace = marketplace;
    }

    public Boolean getSomenteParceiros() {
        return somenteParceiros;
    }

    public void setSomenteParceiros(Boolean somenteParceiros) {
        this.somenteParceiros = somenteParceiros;
    }

    public Boolean getBuyBox() {
        return buyBox;
    }

    public void setBuyBox(Boolean buyBox) {
        this.buyBox = buyBox;
    }

    public String getCodigoErp() {
        return codigoErp;
    }

    public void setCodigoErp(String codigoErp) {
        this.codigoErp = codigoErp;
    }

}
