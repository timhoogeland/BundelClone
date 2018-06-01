package Services;

import java.util.List;

import DAOS.TransactionDAO;
import Objects.Transaction;

public class TransactionService {
	private TransactionDAO transactionDAO = new TransactionDAO();
	
	public List<Transaction> getAllTransactions(){
		return transactionDAO.getAllTransactions();
	}
	
	public List<Transaction> getTransactionById(int transactionId){
		return transactionDAO.getTransactionById(transactionId);
	}
	
	public List<Transaction> getTransactionByLoanId(int loanId){
		return transactionDAO.getTransactionByLoanId(loanId);
	}
	
	public boolean addTransaction(Transaction newTransaction){
		return transactionDAO.addTransaction(newTransaction);
	}
}
