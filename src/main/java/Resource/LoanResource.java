package Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Objects.Loan;
import Objects.User;
import PdfGenerator.GeneratePage;
import PdfGenerator.RetrieveData;
import Services.LoanService;
import Services.ServiceProvider;

@Path("/loan")
public class LoanResource {
	private LoanService service = ServiceProvider.getLoanService();
	
	private JsonObjectBuilder buildJson(Loan loan) {
		JsonObjectBuilder job = Json.createObjectBuilder();
		
		job.add("loanId", loan.getLoanId());
		job.add("loantype",loan.getLoanType());
		job.add("amount", loan.getAmount());
		job.add("status", loan.getStatus());
		job.add("startdate", loan.getStartDate().toString());
		job.add("duration", loan.getDuration());
		if (loan.getClosingDate() != null){
			job.add("closingdate", loan.getClosingDate().toString());
		}else{
			job.add("closingdate", "");
		}
		job.add("paidamount", loan.getPaidAmount());
		job.add("contractpdf", loan.getContractPdf());
		job.add("description", loan.getDescription());
		job.add("useridfk", loan.getUserIdFk());
		return job;
	}
	
	@GET
//	@RolesAllowed("admin")
	@Produces("application/json")
	public String getAllLoans(){
		
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for(Loan l : service.getAllLoans()){
			jab.add(buildJson(l));
		}
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
	@Path("/{loanId}")
	@Produces("application/json")
	public String getLoan(@PathParam("loanId") int loanId){

		JsonObjectBuilder job = null;

		for(Loan l :service.getLoanById(loanId)){
			job = buildJson(l);
		}
		
		JsonObject object = job.build();
		return object.toString();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newLoan(@FormParam("loantype") String loanType,
							@FormParam("amount") String amount,
							@FormParam("startdate") String startDate,
							@FormParam("duration") String duration,
							@FormParam("loandescription") String description,
							@FormParam("useridfk") String userIdFk) throws ParseException{

		RetrieveData data = new RetrieveData();
		//LoanService service = LoanServiceProvider.getLoanService();

		LoanService service = ServiceProvider.getLoanService();

		
		String status = "Pending";
				
		java.util.Date utilStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
		java.util.Date utilClosingDate = new SimpleDateFormat("yyyy-MM-dd").parse("00-00-0000");
		java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
		java.sql.Date sqlClosingDate = new java.sql.Date(utilClosingDate.getTime());
		Random rand = new Random();
		Loan newLoan = new Loan(rand.nextInt(1000), loanType, Integer.parseInt(amount), status, sqlStartDate, Integer.parseInt(duration), sqlClosingDate, 0, "", description, Integer.parseInt(userIdFk));
		if (service.newLoan(newLoan)){
			data.setLoanData(newLoan);
			GeneratePage pdf = new GeneratePage();
	    	pdf.main();
			return Response.ok().build();
		}else{
			return Response.status(Response.Status.FOUND).build();
		}
	}
	
	@PUT
    @Path("/{id}")
    public Response updateLoan(@PathParam("id") int id,
                                @FormParam("loan-status") String status,
                                @FormParam("loan-type") String type,
                                @FormParam("paidamount") String paidamount,
                                @FormParam("duration") String duration,
                                @FormParam("closing-date") String closingdate) throws ParseException{

		java.util.Date utilClosingDate = new SimpleDateFormat("yyyy-MM-dd").parse(closingdate);
		java.sql.Date sqlClosingDate = new java.sql.Date(utilClosingDate.getTime());

		int paid = Integer.parseInt(paidamount);
		int dur = Integer.parseInt(duration);
    	
        Loan loan = service.findById(id);
        if (loan != null) {
            loan.setStatus(status);
            loan.setLoanType(type);
            loan.setPaidAmount(paid);
            loan.setDuration(dur);
            loan.setClosingDate(sqlClosingDate);
            

            Loan updatedLoan = service.updateLoan(loan);

            return Response.ok(buildJson(updatedLoan)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
	public JsonObjectBuilder getLoanJson(Loan loan){
		return buildJson(loan);
	}
}
	

