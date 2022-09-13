package pe.com.bootcamp.domain.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import pe.com.bootcamp.domain.aggregate.Transaction;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionRepository implements ITransactionRepository {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	@Override
	public Mono<UnitResult<Transaction>> create(Transaction entity) {		
		Query query = new Query(Criteria.where("id").is(entity.getId())); 
		Mono<UnitResult<Transaction>> result = mongoTemplate.exists(query, Transaction.class).flatMap(i->
		{								
			if(!i.booleanValue())
				return mongoTemplate.insert(entity).map(ii-> new UnitResult<Transaction>(ii))
						.onErrorResume(e->{
							logger.error(e.getMessage());
							return Mono.just(new UnitResult<Transaction>(true,e.getMessage()));
						});
			else
				return Mono.just(new UnitResult<Transaction>(true,"exists Transaction!"));					
		});
		return result;
	}

	@Override
	public Mono<UnitResult<Transaction>> update(Transaction entity) {		
		Query query = new Query(Criteria.where("id").is(entity.getId())); 
		Mono<UnitResult<Transaction>> result = mongoTemplate.exists(query, Transaction.class).flatMap(i->
		{								
			if(i.booleanValue())
				return mongoTemplate.save(entity).map(ii-> new UnitResult<Transaction>(ii))
						.onErrorResume(e->{
							logger.error(e.getMessage());
							return Mono.just(new UnitResult<Transaction>(true,e.getMessage()));
						});
			else
				return Mono.just(new UnitResult<Transaction>(true,"bank account not exists!"));					
		});		
		return result;
	}

	@Override
	public Mono<UnitResult<Transaction>> saveAll(Flux<Transaction> entities) {
		Mono<UnitResult<Transaction>> result = mongoTemplate.insertAll(entities.collectList(), Transaction.class).collectList().map(i-> new UnitResult<Transaction>(i))
				.onErrorResume(e->{
					logger.error(e.getMessage());
					return Mono.just(new UnitResult<Transaction>(true,e.getMessage()));
				});
		return result;
	}

	@Override
	public Mono<UnitResult<Transaction>> findById(String id) {
		Mono<UnitResult<Transaction>> result = mongoTemplate.findById(id, Transaction.class).map(i-> new UnitResult<Transaction>(i))
				.onErrorResume(e->{
					logger.error(e.getMessage());
					return Mono.just(new UnitResult<Transaction>(true,e.getMessage()));
				});
		return result;
	}

	@Override
	public Mono<UnitResult<Transaction>> findByClientIdentNum(String dni) {
		Query query = new Query(Criteria.where("clientIdentNum").is(dni));
		Mono<UnitResult<Transaction>> result = mongoTemplate.find(query, Transaction.class).collectList().map(i-> new UnitResult<Transaction>(i))
				.onErrorResume(e->{
					logger.error(e.getMessage());
					return Mono.just(new UnitResult<Transaction>(true,e.getMessage()));
				});
		return result;
	}

	@Override
	public Mono<UnitResult<Transaction>> findByAccountNumber(String accountNumber) {		
		Query query = new Query(Criteria.where("accountNumber").is(accountNumber));			
		Mono<UnitResult<Transaction>> result = mongoTemplate.findOne(query, Transaction.class).map(i-> new UnitResult<Transaction>(i))
				.onErrorResume(e->{
					logger.error(e.getMessage());
					return Mono.just(new UnitResult<Transaction>(true,e.getMessage()));
				});
		return result;
	}

	@Override
	public Mono<UnitResult<Transaction>> findAll() {
		Mono<UnitResult<Transaction>> result = mongoTemplate.findAll(Transaction.class).collectList().map(i-> new UnitResult<Transaction>(i))
				.onErrorResume(e->{
					logger.error(e.getMessage());
					return Mono.just(new UnitResult<Transaction>(true,e.getMessage()));
				});
		return result;
	}

	@Override
	public Mono<ResultBase> deleteById(String id) {		
		Query query = new Query(Criteria.where("Id").is(id));
		Mono<ResultBase> result = mongoTemplate.remove(query,Transaction.class).flatMap(i-> Mono.just(new ResultBase(i.getDeletedCount() > 0, null)))
				.onErrorResume(e->{
					logger.error(e.getMessage());
					return Mono.just(new UnitResult<Transaction>(true,e.getMessage()));
				});
		return result;
	}
}