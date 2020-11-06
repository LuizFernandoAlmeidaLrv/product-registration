/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.transients;

import java.util.List;

/**
 *
 * @author rafael.rosar
 */
public class PedidoItemTray {

    private Long produtoVarianteId;
    private String sku;
    private String nome;
    private Long quantidade;
    private Double precoCusto;
    private Double precoVenda;
    private Boolean isBrinde;
    private Double valorAliquota;
    private Boolean isMarketPlace;
    private Double precoPor;
    private Double desconto;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoItemAjusteTray> ajustes;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoItemCentroDistribuicaoTray> centroDistribuicao;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoItemValorAdicionalTray> valoresAdicionais;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoItemAtributoTray> atributos;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoItemEmbalagemTray> embalagens;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoItemPersonalizacaoTray> personalizacoes;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private List<PedidoItemFreteTray> frete;
    /*Nome precisa ser assim para a conversão correta do GSON */
    private String dadosProdutoEvento;
    private List<PedidoItemFormulaTray> formulas;
    private List<PedidoItemSellerTray> seller;

}
