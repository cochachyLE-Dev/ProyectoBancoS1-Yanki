package pe.com.bootcamp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pe.com.bootcamp.domain.aggregate.Transaction;
import pe.com.bootcamp.domain.repository.ITransactionRepository;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/Yanki")
public class YankiController {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendMessage(String msg) {
      kafkaTemplate.send("topic1", msg);
	}
	
	@Autowired
	ITransactionRepository TransactionRepository; 	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	Mono<UnitResult<Transaction>> send(@RequestBody Transaction entity){
		return TransactionRepository.create(entity);
	}
	@RequestMapping(value = "/{accountNumber}", method = RequestMethod.POST)
	Mono<UnitResult<Transaction>> consultBankAccount(@PathVariable String accountNumber){
		return TransactionRepository.findByAccountNumber(accountNumber);
	}
}
