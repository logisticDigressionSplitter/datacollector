package com.streamsets.pipeline.solr.impl;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.solr.client.solrj.impl.SolrHttpClientBuilder;

public class SdcSolrBaicAuthHttpClientBuilder {


    static SolrHttpClientBuilder create(String userName,
                                        String password) {
        SolrHttpClientBuilder solrHttpClientBuilder = SolrHttpClientBuilder.create();

        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userName,
                                                                                  password);
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                                           credentials);
       
        solrHttpClientBuilder.setDefaultCredentialsProvider(() -> credentialsProvider);

        return solrHttpClientBuilder;
    }

}
