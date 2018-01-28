package br.com.verdinhas.gafanhoto.alert;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlertRepository extends MongoRepository<Alert, String> {

}
