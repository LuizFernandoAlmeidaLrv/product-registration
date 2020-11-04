/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.imatriz.ntegrador.ecommerce.control;

import br.com.martinello.imatriz.ntegrador.ecommerce.CategoriaRelacionarTray;
import br.com.martinello.imatriz.ntegrador.ecommerce.RetornoTray;
import br.com.martinello.matriz.bd.integracao.control.ProdutoControlInt;
import br.com.martinello.matriz.bd.transients.CatalogoErp;
import br.com.martinello.matriz.bd.transients.ChavePendencia;
import br.com.martinello.matriz.bd.transients.EstoqueTray;
import br.com.martinello.matriz.bd.transients.ListaAtacadoTray;
import br.com.martinello.matriz.bd.transients.ListaAtributosTray;
import br.com.martinello.matriz.bd.transients.ProdutoImagemTray;
import br.com.martinello.matriz.bd.transients.ProdutoInformacaoTray;
import br.com.martinello.matriz.bd.transients.ProdutoTray;
import br.com.martinello.matriz.util.excessoes.ErroSistemaException;
import br.com.martinello.matriz.util.integracao.ConexaoWebService;
import br.com.martinello.matriz.util.integracao.Retorno;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafael
 */
public class EnvProdutoControl {

    public EnvProdutoControl() {
    }

    public void enviarProduto(ChavePendencia chavePendencia) throws ErroSistemaException {
        Gson gson = new Gson();

        List<ListaAtacadoTray> listaAtacado = new LinkedList<>();
        List<EstoqueTray> lEstoque = new LinkedList<>();
        List<ListaAtributosTray> listaAtributos = new LinkedList<>();

        ProdutoControlInt produtoControl = new ProdutoControlInt();

        ProdutoTray produto = produtoControl.buscarProdutoIntegrarEcommerce(chavePendencia);

        produto.setExibirMatrizAtributos("Sim");
        produto.setContraProposta(false);

        /* Equivale ao Ativo - Inativo */
        produto.setValido(true);

        /* O que pode ir aqui : Desconsiderar Regras, Neutro, Nunca, Sempre  */
        produto.setFreteGratis("Sempre");
        produto.setTrocaGratis(true);

        /* Em gramas */
        produto.setPeso(1l);

        /* Em centimetros. */
        produto.setAltura(2l);

        /* Em centimetros. */
        produto.setComprimento(10l);

        /* Em centimetros. */
        produto.setLargura(11l);

        produto.setGarantia(90d);
        produto.setIsTelevendas(false);

        produto.setLocalizacaoEstoque("");

        Long quantidadeMaximaCompraUnidade = 5l;
        Long quantidadeMinimaCompraUnidade = 1l;

        produto.setQuantidadeMaximaCompraUnidade(quantidadeMaximaCompraUnidade);
        produto.setQuantidadeMinimaCompraUnidade(quantidadeMinimaCompraUnidade);
        produto.setCondicao("Novo");
        produto.setUrlVideo("");
        produto.setSpot(true);
        produto.setPaginaProduto(true);
        produto.setMarketplace(false);

        produto.setSomenteParceiros(false);
        produto.setBuyBox(false);

        produto.setFatorMultiplicadorPreco(0d);
        produto.setPrazoEntrega(7d);

        EstoqueTray estoque = new EstoqueTray();
        estoque.setEstoqueFisico(5l);
        estoque.setEstoqueReservado(0l);
        estoque.setCentroDistribuicaoId(25l);
        lEstoque.add(estoque);
        produto.getEstoque().addAll(lEstoque);

//            produto.setListaAtributos(listaAtributos);
//            produto.setListaAtacado(listaAtacado);
        //private List<Estoque> estoque("");
        //private List<ListaAtributos> listaAtributos("");
        //private List<ListaAtributos> listaAtacado("");            
////                ListaAtributosTray atributo = new ListaAtributosTray();
////                atributo.setNome("COR");
////                atributo.setValor("PRETO");
////                atributo.setExibir(Boolean.TRUE);
////                listaAtributos.add(atributo);
////                produto.setListaAtributos(listaAtributos);
        ConexaoWebService conexaoWebService = new ConexaoWebService();
        Retorno retorno = new Retorno();

        //System.out.println(gson.toJson(produto));
        if (produto.getProdutoId() != null && produto.getProdutoId() > 0) {
            /* Atualizar o produto */
            retorno = conexaoWebService.executarServico("https://api.fbits.net/produtos/" + produto.getCodigoErp() + "?tipoIdentificador=Sku", "", gson.toJson(produto), "PUT", "");
            if (!retorno.getStatus().equals(ConexaoWebService.OK) || !retorno.getRetorno().equalsIgnoreCase("true")) {
                throw new ErroSistemaException(retorno.getRetorno());
            }

            try {
                enviarInformacoes(chavePendencia);
            } catch (InterruptedException ex) {
                throw new ErroSistemaException("Erro ao executar a integração de informações. ", ex);
            }
        } else {
            /* Consultar se o produto já existe. Pode ocorrer de ter acontecido um erro e nao ter gravado o ID no Sapiens. */
            retorno = conexaoWebService.executarServico("https://api.fbits.net/produtos/" + produto.getCodigoErp() + "?tipoIdentificador=Sku", "", gson.toJson(produto), "GET", "");

            if (retorno.getStatus().equals(ConexaoWebService.OK)) {
                ProdutoTray produtoTrayRecebido = gson.fromJson(retorno.getRetorno(), ProdutoTray.class);
                produto.setProdutoId(produtoTrayRecebido.getProdutoId());

                produtoControl.atualizarCodigoEcommerce(produto);

                /* Se existe atualiza. */
                retorno = conexaoWebService.executarServico("https://api.fbits.net/produtos/" + produto.getCodigoErp() + "?tipoIdentificador=Sku", "", gson.toJson(produto), "PUT", "");
                if (!retorno.getStatus().equals(ConexaoWebService.OK) || !retorno.getRetorno().equalsIgnoreCase("true")) {
                    throw new ErroSistemaException(retorno.getRetorno());
                }
            } else {
                RetornoTray retornoTray = gson.fromJson(retorno.getRetorno().replace("Erro não tratado, retorno:", ""), RetornoTray.class);
                // 15012 - Produto nÃo encontrado. 
                if (retornoTray.getCodigo() == 15012) {
                    /* Se nÃo existe inserir */

                    retorno = conexaoWebService.executarServico("https://api.fbits.net/produtos/", "", gson.toJson(produto), "POST", "");
                    if (retorno.getStatus().equals(ConexaoWebService.OK)) {

                        if (retorno.getStatus().equals(ConexaoWebService.OK)) {
                            produto.setProdutoId(Long.parseLong(retorno.getRetorno()));
                            produtoControl.atualizarCodigoEcommerce(produto);
                        } else {
                            throw new ErroSistemaException(retorno.getRetorno());
                        }
                    }
                } else {
                    throw new ErroSistemaException(retornoTray.getMensagem());
                }
            }

            try {
                enviarInformacoes(chavePendencia);
            } catch (InterruptedException ex) {
                throw new ErroSistemaException("Erro ao executar a integração de informações. ", ex);
            }

            /* O que devolve e o ID. Variante */
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                throw new ErroSistemaException("Erro ao executar o Sleep", ex);
            }

        }
    }

    public void enviarInformacoes(ChavePendencia chavePendencia) throws ErroSistemaException, InterruptedException {
        Gson gson = new Gson();

        /* Enviar informações do produto. */
        ProdutoControlInt produtoControl = new ProdutoControlInt();
        ConexaoWebService conexaoWebService = new ConexaoWebService();

        ProdutoInformacaoTray produtoInformacaoTray = produtoControl.buscarProdutoInformacaoIntegrarEcommerce(chavePendencia);
        Retorno retorno;

        // Aqui tratar..........,...
        if (produtoInformacaoTray.getInformacaoId() != null && produtoInformacaoTray.getInformacaoId() > 0) {
            /* Atualizar o produto */
            retorno = conexaoWebService.executarServico("https://api.fbits.net/produtos/" + chavePendencia.getProdutoErp() + "/informacoes/" + produtoInformacaoTray.getInformacaoId() + "?tipoIdentificador=Sku", "", gson.toJson(produtoInformacaoTray), "PUT", "");
            if (!retorno.getStatus().equals(ConexaoWebService.OK) || !retorno.getRetorno().equalsIgnoreCase("true")) {
                throw new ErroSistemaException(retorno.getRetorno());
            }
        } else {
            /* Consultar se o produto já existe. Pode ocorrer de ter acontecido um erro e nao ter gravado o ID no Sapiens. */

            retorno = conexaoWebService.executarServico("https://api.fbits.net/produtos/" + chavePendencia.getProdutoErp() + "/informacoes?tipoIdentificador=Sku", "", "", "GET", "");

            if (retorno.getStatus().equals(ConexaoWebService.OK)) {
                Type produtoInformacaoType = new TypeToken<List<ProdutoInformacaoTray>>() {
                }.getType();

                List<ProdutoInformacaoTray> lProdutoInformacaoTray = gson.fromJson(retorno.getRetorno(), produtoInformacaoType);

                for (ProdutoInformacaoTray produtoInformacaoTrayRecebido : lProdutoInformacaoTray) {

                    /*   Informações. */
                    if (produtoInformacaoTrayRecebido.getTipoInformacao().equals("1")) {
                        produtoInformacaoTray.setInformacaoId(produtoInformacaoTrayRecebido.getInformacaoId());
                        produtoControl.atualizarInformacoesCodigoEcommerce(chavePendencia, produtoInformacaoTrayRecebido);
                    }
// Por hora temos condição de tratar apenas um campo, pensar na forma de vincular e gerar a pendências para mais de um tipo posteriormente. 
//                    /*   Especificações. */
//                    if (produtoInformacaoTrayRecebido.getTipoInformacao().equals("3")) {
//                        produtoInformacaoTray.setInformacaoId(produtoInformacaoTrayRecebido.getInformacaoId());
//                        produtoControl.atualizarInformacoesCodigoEcommerce(chavePendencia, produtoInformacaoTrayRecebido);
//                    }
                }

                /* Se existe atualiza. */
                retorno = conexaoWebService.executarServico("https://api.fbits.net/produtos/" + chavePendencia.getProdutoErp() + "/informacoes/" + produtoInformacaoTray.getInformacaoId() + "?tipoIdentificador=Sku", "", gson.toJson(produtoInformacaoTray), "PUT", "");

                if (!retorno.getStatus().equals(ConexaoWebService.OK) || !retorno.getRetorno().equalsIgnoreCase("true")) {
                    throw new ErroSistemaException(retorno.getRetorno());
                }
            } else {
                    /* Se nÃo existe inserir */

                    retorno = conexaoWebService.executarServico("https://api.fbits.net/produtos/" + chavePendencia.getProdutoErp() + "/informacoes?tipoIdentificador=Sku&tipoRetorno=Id", "", gson.toJson(produtoInformacaoTray), "POST", "");
                    if (retorno.getStatus().equals(ConexaoWebService.OK)) {

                        if (retorno.getStatus().equals(ConexaoWebService.OK)) {
                            produtoInformacaoTray.setInformacaoId(Long.parseLong(retorno.getRetorno()));
                            produtoControl.atualizarInformacoesCodigoEcommerce(chavePendencia, produtoInformacaoTray);
                        } else {
                            throw new ErroSistemaException(retorno.getRetorno());
                        }
                    }
               
            }
        }
        if (!retorno.getStatus().equals(ConexaoWebService.OK)) {
            throw new ErroSistemaException(retorno.getRetorno());
        } else {

        }
        System.out.println("Produto: " + chavePendencia.getProdutoErp());
        System.out.println("Retorno: " + retorno.getStatus() + " Msg: " + retorno.getRetorno());
    }

    public void enviarRelacionamento(ChavePendencia chavePendencia) throws ErroSistemaException, InterruptedException {
        Gson gson = new Gson();

        /* Enviar informações do produto. */
        ProdutoControlInt produtoControl = new ProdutoControlInt();
        ConexaoWebService conexaoWebService = new ConexaoWebService();
        CatalogoErp catalogoErp = produtoControl.buscarProdutoCatalogoEcommerce(chavePendencia);

        CategoriaRelacionarTray categoriaRelacionarTray = new CategoriaRelacionarTray();
        categoriaRelacionarTray.getListaCategoriaId().add(catalogoErp.getCodigoCatalogoEcomerce());

        Retorno retorno = new Retorno();
        if (catalogoErp.getSituacaoRelacionamento().equals("I") || catalogoErp.getVisivelSite().equals("N")) {
            // Ainda não validei aqui iiiiiii Implenetar o Delete 
            retorno = conexaoWebService.executarServico("https://api.fbits.net/produtos/" + catalogoErp.getProdutoErp() + "/categorias?tipoIdentificador=Sku", "", gson.toJson(categoriaRelacionarTray), "POST", "");
            if (!retorno.getStatus().equals(ConexaoWebService.OK)) {
                throw new ErroSistemaException(retorno.getRetorno());
            }
        } else {
            retorno = conexaoWebService.executarServico("https://api.fbits.net/produtos/" + catalogoErp.getProdutoErp() + "/categorias?tipoIdentificador=Sku", "", gson.toJson(categoriaRelacionarTray), "POST", "");
            if (retorno.getStatus().equals(ConexaoWebService.OK)) {
                if (retorno.getRetorno().equals("true")) {
                    catalogoErp.setCodigoEcommerce(1l);
                } else {
                    throw new ErroSistemaException("Erro ao cadastrar o relacionamento: " + retorno.getRetorno());
                }
                produtoControl.atualizarCatalogoCodigoEcommerce(catalogoErp);
            } else {
                throw new ErroSistemaException(retorno.getRetorno());
            }
        }
    }

    public void enviarImagens(ChavePendencia chavePendencia) throws ErroSistemaException {
        Gson gson = new Gson();
        Retorno retorno = new Retorno();
        ProdutoControlInt produtoControl = new ProdutoControlInt();
        ConexaoWebService conexaoWebService = new ConexaoWebService();

        /* Buscar as Imagens para Conciliar */
        retorno = conexaoWebService.executarServico("https://api.fbits.net/produtos/" + chavePendencia.getProdutoErp() + "/imagens?tipoIdentificador=Sku&produtosIrmaos=false", "", "", "GET", "");

        /* Enviar as imagens. */
        ProdutoImagemTray produtoImagemTray = produtoControl.buscarProdutoImagemIntegrarEcommerce(chavePendencia);

        /* Envio separado pois ocorreu de dar quebra no envio e perder a lógica da sequência da imagem, assim minimiza. */
        if (produtoImagemTray.getProdutoImagemId() != null && produtoImagemTray.getProdutoImagemId() > 0) {
            if (produtoImagemTray.getVisivelSite().equalsIgnoreCase("N")) {
                retorno = conexaoWebService.executarServico("https://api.fbits.net/produtos/" + chavePendencia.getProdutoErp() + "/imagens/" + produtoImagemTray.getProdutoImagemId() + "?tipoIdentificador=Sku", "", "", "DELETE", "");
                if (retorno.getStatus().equals(ConexaoWebService.OK) && !retorno.getRetorno().equalsIgnoreCase("true")) {
                    throw new ErroSistemaException("Erro ao realizar a exclusão: " + retorno.getRetorno());
                }
            }
        } else {
            /* Precisa ser em formato de lista para enviar. */
            List<ProdutoImagemTray> lProdutoImagemTrayAux = new LinkedList<>();
            lProdutoImagemTrayAux.add(produtoImagemTray);

            retorno = conexaoWebService.executarServico("https://api.fbits.net/produtos/", chavePendencia.getProdutoErp() + "/imagens?tipoIdentificador=Sku&tipoRetorno=ListaIds", gson.toJson(lProdutoImagemTrayAux), "POST", "");
            if (retorno.getStatus().equals(ConexaoWebService.OK)) {
                String[] retImagem = gson.fromJson(retorno.getRetorno(), String[].class);
                produtoImagemTray.setProdutoImagemId(Long.parseLong(retImagem[0]));
                produtoControl.atualizarImagemCodigoEcommerce(produtoImagemTray);
            } else {
                throw new ErroSistemaException(retorno.getRetorno());
            }
        }
        System.out.println("" + retorno.getCodigo() + " Retorno " + retorno.getRetorno());
    }

}
