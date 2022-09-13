package pe.com.bootcamp.domain.repository;

import pe.com.bootcamp.domain.aggregate.Transaction;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionRepository{
	Mono<UnitResult<Transaction>> create(Transaction entity);
	
	Mono<UnitResult<Transaction>> update(Transaction entity);
	
	Mono<UnitResult<Transaction>> saveAll(Flux<Transaction> entities);
	
	Mono<UnitResult<Transaction>> findById(String id);
	
	Mono<UnitResult<Transaction>> findByClientIdentNum(String dni);
	
	Mono<UnitResult<Transaction>> findByAccountNumber(String accountNumber);
	
	Mono<UnitResult<Transaction>> findAll();	
	
	Mono<ResultBase> deleteById(String id);
}
