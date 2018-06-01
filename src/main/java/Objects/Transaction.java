package Objects;

import java.sql.Date;

public class Transaction {
	private int transactionId;
	private int amount;
	private String sender;
	private String receiver;
	private Date timeStamp;
	private int loanIdFk;
	private int airtimeIdFk;
	
	public Transaction(int transactionId, int amount, String sender, String receiver, Date timeStamp, int loanIdFk,
			int airtimeIdFk) {
		this.setTransactionId(transactionId);
		this.setAmount(amount);
		this.setSender(sender);
		this.setReceiver(receiver);
		this.setTimeStamp(timeStamp);
		this.setLoanIdFk(loanIdFk);
		this.setAirtimeIdFk(airtimeIdFk);
	}
	
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public int getLoanIdFk() {
		return loanIdFk;
	}
	public void setLoanIdFk(int loanIdFk) {
		this.loanIdFk = loanIdFk;
	}
	public int getAirtimeIdFk() {
		return airtimeIdFk;
	}
	public void setAirtimeIdFk(int airtimeIdFk) {
		this.airtimeIdFk = airtimeIdFk;
	}
	
}
