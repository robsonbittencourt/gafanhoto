package br.com.verdinhas.gafanhoto.monitor;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MonitorRepository extends MongoRepository<Monitor, String> {

	public List<Monitor> findByMainKeyWord(String mainKeyWord);

	public List<Monitor> findByUserId(int userId);

}
