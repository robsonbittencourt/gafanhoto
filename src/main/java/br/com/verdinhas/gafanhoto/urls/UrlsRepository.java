package br.com.verdinhas.gafanhoto.urls;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlsRepository extends MongoRepository<Url, String> {

}
