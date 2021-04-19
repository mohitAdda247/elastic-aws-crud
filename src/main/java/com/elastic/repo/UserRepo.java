package com.elastic.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.elastic.model.User;

@Repository
public interface UserRepo extends ElasticsearchRepository<User, Integer> {

}
