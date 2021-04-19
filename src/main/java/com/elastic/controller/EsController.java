package com.elastic.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.RestController;

import com.elastic.model.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Delete;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Update;

@RestController
public class EsController {
	JestClient client = null;

	public JestClient getClient() {
		if (this.client == null) {
			JestClientFactory factory = new JestClientFactory();
			factory.setHttpClientConfig(new HttpClientConfig.Builder(
					"https://search-ytsearch-staging-vflomzxcm3c4pklej6nwyomxfm.us-east-1.es.amazonaws.com/")
							.multiThreaded(true).defaultMaxTotalConnectionPerRoute(2).maxTotalConnection(10).build());
			this.client = factory.getObject();
			return factory.getObject();
		}
		return this.client;

	}

	@PostMapping("/save")
	public String saveUser(@RequestBody User user) throws IOException {
	
		JestClient client = this.getClient();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode userNode = mapper.createObjectNode().put("name", user.getName()).put("email", user.getEmail());
		JestResult postResult = client
				.execute(new Index.Builder(userNode.toString()).index("es_user").type("employee").build());

		return postResult.toString();
	}

	@GetMapping("/find/{id}")
	public String findUser(@PathVariable final String id) throws IOException {

		JestClient client = this.getClient();
		JestResult getResult = client.execute(new Get.Builder("es_user", id).type("employee").build());
		return getResult.toString();
	}

	@PutMapping("/update/{id}")
	public String updateUser(@PathVariable final String id, @RequestBody User user) throws IOException {
	
		JestClient client = this.getClient();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode userNode = mapper.createObjectNode().put("name", user.getName()).put("email", user.getEmail());
		JestResult putResult = client.execute(new Update.Builder(userNode.toString()).index("es_user").id(id).build());

		return putResult.toString();
	}

	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable final String id) throws IOException {
	
		JestClient client = this.getClient();

		JestResult deleteResult = client.execute(new Delete.Builder(id).index("es_user").type("employee").build());
		return deleteResult.toString();
	}
}
