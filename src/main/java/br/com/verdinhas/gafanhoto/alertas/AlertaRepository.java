package br.com.verdinhas.gafanhoto.alertas;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlertaRepository extends MongoRepository<Alerta, String> {

}
