package com.elastic.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Service;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

@Service
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.elastic.repo")
public class JestClientService {
	JestClient client = null;

	public JestClient getClient() {

		System.out.println("setting up connection with jedis");
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder(
				"https://search-ytsearch-staging-vflomzxcm3c4pklej6nwyomxfm.us-east-1.es.amazonaws.com/")
						.multiThreaded(true).defaultMaxTotalConnectionPerRoute(2).maxTotalConnection(10).build());
		return factory.getObject();
	}
}
