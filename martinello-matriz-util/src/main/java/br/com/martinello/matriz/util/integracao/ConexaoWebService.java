package br.com.martinello.matriz.util.integracao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author rafael.rosar
 */
public class ConexaoWebService {

    private Client client;
    private String json;
    private Retorno retorno;
    private WebResource webResource;
    private ClientResponse clientResponse;
    public static final String OK = "ok";
    public static final String ERRO = "erro";
    public static final String GET = "GET";

    public ConexaoWebService() {
    }

    public Retorno executarServico(String url, String servico, String json, String tipoServico, String token) {
        this.json = json;

        retorno = new Retorno();
        clientResponse = null;
        webResource = null;
        client = null;

        ClientConfig clientConfig = new DefaultClientConfig();
        client = Client.create(clientConfig);
        //client.addFilter(new GZIPContentEncodingFilter());

        if (!tipoServico.equals("GET")) {
            webResource = client.resource(url + servico);
        } else {
            webResource = client.resource(url);
        }
        /* 15 Segundos */
        client.setConnectTimeout(15000);
        /* 30 Segundos */
        client.setReadTimeout(30000);

        //token = "Bearer " + token;
        
        token = "Basic marti-a5c6a7eb-62bf-4867-9706-471010814b3c";
       // token = "Basic marti-af536a46-b813-4d17-b5c4-cd34f1598c73";
        try {
            if (tipoServico.equals("POST")) {
                clientResponse = webResource.header("Authorization", token).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
            } else if (tipoServico.equals("PUT")) {
                clientResponse = webResource.path(servico).header("Authorization", token).type(MediaType.APPLICATION_JSON).put(ClientResponse.class, json);
            } else if (tipoServico.equals("DELETE")) {
                clientResponse = webResource.path(servico).header("Authorization", token).type(MediaType.APPLICATION_JSON).delete(ClientResponse.class, json);
            } else if (tipoServico.equals(GET)) {
                System.out.println("URL de Conexão " + webResource.getURI() + servico);
                clientResponse = webResource.path(servico).header("Authorization", token).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            }
        } catch (ClientHandlerException e) {
            if (clientResponse == null) {
                retorno.setCodigo(999);
                retorno.setStatus("erro");
                if (e.getMessage() != null && (e.getMessage().contains("Connection refused") || e.getMessage().contains("Connection refused") || e.getMessage().contains("timed out"))) {
                    retorno.setRetorno("Falha ao se comunicar com o servidor. Isso geralmente ocorre quando há um problema de "
                            + "conexão com a internet. Aguarde até que a conexão seja restabelecida ou "
                            + "comunique ao setor responsável.");
                } else {
                    retorno.setRetorno("Erro de Conexão." + e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "" + " - " + e.getMessage() != null ? e.getMessage() : "");
                }
                return retorno;
            } else {
                retorno.setCodigo(0);
                retorno.setStatus("erro");
                retorno.setRetorno(e.getMessage() + e.getCause());
                return retorno;
            }
        } catch (UniformInterfaceException e) {
            retorno.setCodigo(0);
            retorno.setStatus("erro");
            retorno.setRetorno(e.getMessage() + e.getCause());
            return retorno;
        } finally {
            client.destroy();
        }
        return formataRetornoWebService(clientResponse);
    }

    public Retorno executarServico(String url, String servico, byte[] json, String tipoServico, String token) {
        retorno = new Retorno();
        clientResponse = null;
        webResource = null;
        client = null;

        ClientConfig clientConfig = new DefaultClientConfig();
        client = Client.create(clientConfig);
        //client.addFilter(new GZIPContentEncodingFilter());
        //webResource = client.resource("http://" + "localhost:8080" + "/servico-matriz/recursos/" + servico);

        if (!tipoServico.equals("GET")) {
            webResource = client.resource(url + servico);
        } else {
            webResource = client.resource(url);
        }

        token = "Bearer " + token;
        try {
            if (tipoServico.equals("POST")) {
                clientResponse = webResource.header("Authorization", token).type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class, json);

            }
            System.out.println("URL de Conexão " + webResource.getURI());
        } catch (ClientHandlerException e) {
            if (clientResponse == null) {
                retorno.setCodigo(999);
                retorno.setStatus("erro");
                if (e.getMessage() != null && (e.getMessage().contains("Connection refused") || e.getMessage().contains("Connection refused"))) {
                    retorno.setRetorno("Falha ao se comunicar com o servidor. Isso geralmente ocorre quando há um problema de "
                            + "conexão com a internet. Aguarde até que a conexão seja restabelecida ou "
                            + "comunique ao setor responsável.");
                } else {
                    retorno.setRetorno("Erro de Conexão." + e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "" + " - " + e.getMessage() != null ? e.getMessage() : "");
                }
                return retorno;
            } else {
                retorno.setCodigo(0);
                retorno.setStatus(ERRO);
                retorno.setRetorno(e.getMessage() + e.getCause());
                return retorno;
            }
        } catch (UniformInterfaceException e) {
            retorno.setCodigo(0);
            retorno.setStatus(ERRO);
            retorno.setRetorno(e.getMessage() + e.getCause());
            return retorno;
        } finally {
            client.destroy();
        }
        return formataRetornoWebService(clientResponse);
    }

    public Retorno executaServicoOobj(String header, String token, String tipoServico, String urlConexao, String servico, String json) {

        clientResponse = null;
        ClientConfig clientConfig = new DefaultClientConfig();
        client = Client.create(clientConfig);
        //webResource = client.resource("http://rest.oobj-dfe.com.br/" + servico);
        webResource = client.resource(urlConexao + servico);
        try {
            if (tipoServico.equals("POST")) {
                clientResponse = webResource.header(header, token).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, getJsonTratado(json));
            } else if (tipoServico.equals("GET")) {
                clientResponse = webResource.header(header, token).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            }
            System.out.println("URL de Conexão " + webResource.getURI());

        } catch (ClientHandlerException e) {
            System.out.println(e.getCause() + e.getMessage());
        }
        return formataRetornoWebService(clientResponse);
    }

    public String getJsonTratado(String dados) {
        json = "";
        if ((dados != null) && dados.length() > 0) {
            if (dados.startsWith("[", 0)) {
                json = dados.replace("[", "").replace("]", "");
            } else {
                json = dados;
            }
        } else {
            json = dados;
        }
        return json;
    }

    public Retorno formataRetornoWebService(ClientResponse clientResponse) {
        retorno = new Retorno();
        if (clientResponse != null) {
            retorno.setCodigo(clientResponse.getStatus());
            switch (clientResponse.getStatus()) {
                case 200:
                    retorno.setStatus(OK);
                    retorno.setRetorno(clientResponse.getEntity(String.class).trim());
                    return retorno;
                case 201:
                    retorno.setStatus(OK);
                    retorno.setRetorno(clientResponse.getEntity(String.class).trim());
                    return retorno;
                case 400:
                    retorno.setStatus(ERRO);
                    retorno.setRetorno("400 - Requisição inválida. " + clientResponse.getEntity(String.class).trim());
                    return retorno;
                case 401:
                    retorno.setStatus(ERRO);
                    retorno.setRetorno("401 - Login não Autorizado.");
                    return retorno;
                case 404:
                    retorno.setStatus(ERRO);
                    retorno.setRetorno("404 - Houve Comunicação com o servidor, porém ele não conseguiu interpretar a solicitação. " + clientResponse.getEntity(String.class).trim());
                    return retorno;
                case 415:
                    retorno.setStatus(ERRO);
                    retorno.setRetorno("415 - Houve Comunicação com o servidor, porém ele não conseguiu interpretar a solicitação. " + clientResponse.getEntity(String.class).trim());
                    return retorno;
                default:
                    retorno.setStatus(ERRO);
                    retorno.setRetorno("Erro não tratado, retorno: " + clientResponse.getEntity(String.class).trim());
                    return retorno;
            }
        } else {
            retorno.setCodigo(0);
            retorno.setStatus(ERRO);
            retorno.setRetorno("Erro de Conexão, Matriz não está respondendo.");
            return retorno;
        }
    }
}
