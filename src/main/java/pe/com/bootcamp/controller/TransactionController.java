package pe.com.bootcamp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pe.com.bootcamp.domain.aggregate.Transaction;
import pe.com.bootcamp.domain.repository.ITransactionRepository;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/Transaction")
public class TransactionController {
	@Autowired
	ITransactionRepository TransactionRepository; 	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	Mono<UnitResult<Transaction>> create(@RequestBody Transaction entity){
		return TransactionRepository.create(entity);
	}
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	Mono<UnitResult<Transaction>> update(@RequestBody Transaction entity){
		return TransactionRepository.update(entity);
	}
	@RequestMapping(value = "/batch", method = RequestMethod.POST)
	Mono<UnitResult<Transaction>> saveAll(@RequestBody Flux<Transaction> entities){
		return TransactionRepository.saveAll(entities);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Mono<UnitResult<Transaction>> findById(@PathVariable String id){
		return TransactionRepository.findById(id);
	}
	@RequestMapping(value = "/{dni}", method = RequestMethod.GET)
	Mono<UnitResult<Transaction>> findByClientIdentNum(@PathVariable String dni){
		return TransactionRepository.findByClientIdentNum(dni);
	}
	@RequestMapping(value = "/{accountNumber}", method = RequestMethod.POST)
	Mono<UnitResult<Transaction>> findByAccountNumber(@PathVariable String accountNumber){
		return TransactionRepository.findByAccountNumber(accountNumber);
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	Mono<UnitResult<Transaction>> findAll(){
		return TransactionRepository.findAll();
	}	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	Mono<ResultBase> deleteById(@PathVariable String id){
		return TransactionRepository.deleteById(id);
	}
}
