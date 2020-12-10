package com.streamsets.pipeline.solr.impl;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.solr.client.solrj.impl.SolrHttpClientBuilder;
import org.apache.solr.client.solrj.impl.SolrHttpClientBuilder.CredentialsProviderProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SdcSolrBaicAuthHttpClientBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(SdcSolrBaicAuthHttpClientBuilder.class);

    static SolrHttpClientBuilder create(String basicAuthUser,
                                        String basicAuthPass) {
        LOG.info("BasicAuth Selected for user: " +
                 basicAuthUser);
        SolrHttpClientBuilder solrHttpClientBuilder = SolrHttpClientBuilder.create();

        /*UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(basicAuthUser,
                                                                                  basicAuthPass);
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                                           credentials);
        
        solrHttpClientBuilder.setDefaultCredentialsProvider(() -> credentialsProvider);
        */
        solrHttpClientBuilder.setDefaultCredentialsProvider(new CredentialsProviderProvider() {
            @Override
            public CredentialsProvider getCredentialsProvider() {
                CredentialsProvider credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(AuthScope.ANY,
                                             new UsernamePasswordCredentials(basicAuthUser,
                                                                             basicAuthPass));
                return credsProvider;
            }
        });

        return solrHttpClientBuilder;
    }

}
