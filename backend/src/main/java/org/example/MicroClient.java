package org.example;

import io.netty.channel.ChannelOption;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

public class MicroClient {

    private WebClient webClient = null;

    private WebClient.Builder webClientBuilder = null;

    public void init(){
        this.webClientBuilder = WebClient.builder();
        webClientBuilder.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(200 * 1024 * 1024));

        HttpClient httpClient = HttpClient.create();
        httpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
        webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient));
        webClient =
                this.webClientBuilder.baseUrl("http://localhost:9000").build();
    }

    public String getDataFromEndpoint(String endpoint){
        System.out.println("Retrieving GET data from endpoint: " + endpoint);
        return this.webClient.get().uri(endpoint).retrieve().bodyToMono(String.class).block();
    }



}
