package pe.com.bootcamp.domain.aggregate;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Transactions")
public class Transaction {
	private String id;
	private TransactionStatus estatus;
	private TypeMovement typeMov;
	private Currency currency;	
	private Double exchangeRate;
	private Double amount;
	private String debitBankAccount;
	private String beneficiaryBankAccount;
}
@Data
class Currency {
	private String currencyCode;
	private String currencySymbol;
	private String currencyName;
	private Boolean IsDigitalCurrency;
}

enum TransactionStatus{
	Proceso,
	Completado,
	Rechazado,
	Error
}
enum TypeMovement{
	Egreso,
	Ingreso
}