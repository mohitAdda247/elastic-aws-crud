package com.elastic.model;




import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(indexName="es_user",shards=2)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
	@Id
	public String id;
	
	public String name;
	
	public String email;
	

}
