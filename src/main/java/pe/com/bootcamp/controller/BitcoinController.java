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
@RequestMapping("/Bitcoint")
public class BitcoinController {
	@Autowired
	ITransactionRepository TransactionRepository; 	
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendMessage(String msg) {
      kafkaTemplate.send("topic1", msg);
	}
	
	@RequestMapping(value = "/exchangeCurrency", method = RequestMethod.POST)
	Mono<UnitResult<Transaction>> exchangeCurrency(@RequestBody Transaction entity){
		return TransactionRepository.create(entity);
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	Mono<UnitResult<Transaction>> listBankAccount(){
		return TransactionRepository.findAll();
	}
	@RequestMapping(value = "/{CurrencyCode}", method = RequestMethod.GET)
	Mono<UnitResult<Transaction>> consultSpecialExchangeRate(@PathVariable String currencyCode){
		return TransactionRepository.findByAccountNumber(currencyCode);
	}
	@RequestMapping(value = "/createAccount", method = RequestMethod.POST)
	Mono<UnitResult<Transaction>> createAccount(@RequestBody Transaction entity){
		return TransactionRepository.create(entity);
	}
}
