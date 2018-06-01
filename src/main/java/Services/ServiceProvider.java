package Services;

public class ServiceProvider {
    private static UserService userService = new UserService();
    private static ContractService contractService = new ContractService();
    private static AddressService adressService = new AddressService();
    private static GroupService groupService = new GroupService();
    private static LoginService loginService = new LoginService();
	private static LoanGroupService loanGroupService= new LoanGroupService();  
	private static LoanService loanService= new LoanService();
	private static TransactionService transactionService = new TransactionService();
	
    
    public static TransactionService getTransactionService() {
		return transactionService;
	}

	public static UserService getUserService() { 
    	return userService; 
    	}
    
    public static ContractService getContractService() { 
    	return contractService; 
    	}
    
    public static AddressService getAdressService() { 
    	return adressService; 
    	}
    
    public static GroupService getGroupService() {
    	return groupService;
    	}
    
    public static LoginService getLoginService() {
    	return loginService; 
    	}
    
	public static LoanGroupService getLoanGroupService() {
		return loanGroupService; 
		}
	
	public static LoanService getLoanService() {
		return loanService;
		}
}
