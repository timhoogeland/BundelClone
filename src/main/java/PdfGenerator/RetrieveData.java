package PdfGenerator;

import Objects.Adress;
import Objects.Loan;
import Objects.User;

public class RetrieveData {
	private User user;
	private Adress adress;
	private Loan loan;
	public void setUserData(User userData){
		user = userData;
		System.out.println(userData.toString());
	}
	public User getUserData(){
		return user;
	}
	public void setAdresData(Adress adressData){
		adress = adressData;
	}
	public Adress getAdressData(){
		return adress;
	}
	public void setLoanData(Loan loanData){
		loan = loanData;
	}
	public Loan getLoanData(){
		return loan;
	}
}
