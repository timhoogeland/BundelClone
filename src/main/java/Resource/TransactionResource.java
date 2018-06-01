package Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import Objects.Transaction;
import Services.ServiceProvider;
import Services.TransactionService;

@Path("/transaction")
public class TransactionResource {
	private TransactionService service = ServiceProvider.getTransactionService();
	
	private JsonObjectBuilder buildJSON(Transaction transaction) {
		JsonObjectBuilder job = Json.createObjectBuilder();
		
		job.add("transactionid", transaction.getTransactionId());
		job.add("amount", transaction.getAmount());
		job.add("sender", transaction.getSender());
		job.add("receiver", transaction.getReceiver());
		job.add("timestamp", transaction.getTimeStamp().toString());
		job.add("loanidfk", transaction.getLoanIdFk());
		job.add("airtimeidfk", transaction.getAirtimeIdFk());
		
		return job;
	}
	
	@GET
	@Produces("application/json")
	public String getAllTransactions(){
		JsonArrayBuilder jab = Json.createArrayBuilder();
		
		for (Transaction t : service.getAllTransactions()) {
			jab.add(buildJSON(t));
		}
		
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public String getTransactionById(@PathParam("id") int transactionId) {
		if (service.getTransactionById(transactionId) != null){
			JsonArrayBuilder jab = Json.createArrayBuilder();
			for (Transaction t : service.getTransactionById(transactionId)){
				jab.add(buildJSON(t));
			}
			return jab.build().toString();
		}else{
			return Response.status(Response.Status.NOT_FOUND).toString();
		}
	}
	
	@GET
	@Path("loanid/{id}")
	@Produces("application/json")
	public String getTransactionByLoanId(@PathParam("id") int loanId) {
		if (service.getTransactionByLoanId(loanId) != null){
			JsonArrayBuilder jab = Json.createArrayBuilder();
			for (Transaction t : service.getTransactionByLoanId(loanId)) {
				jab.add(buildJSON(t));
			}
			return jab.build().toString();
		}else {
			return Response.status(Response.Status.NOT_FOUND).toString();
		}
	}
	
	@POST
	@Produces("application/json")
	public Response addTransaction(	@FormParam("amount") String amount,
									@FormParam("sender") String sender,
									@FormParam("receiver") String receiver,
									@FormParam("timestamp") String timeStamp,
									@FormParam("loanidfk") String loanIdFk,
									@FormParam("airtimeidfk") String airtimeIdFk) throws ParseException {
		java.util.Date utilTimeStamp = new SimpleDateFormat("yyyy-MM-dd").parse(timeStamp);
		java.sql.Date sqlTimeStamp = new java.sql.Date(utilTimeStamp.getTime());
		
		Transaction newTransaction = new Transaction(0, Integer.parseInt(amount), sender, receiver, sqlTimeStamp, Integer.parseInt(loanIdFk), Integer.parseInt(airtimeIdFk));
		
		if (service.addTransaction(newTransaction)) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
				
	}
	
	
	
}
