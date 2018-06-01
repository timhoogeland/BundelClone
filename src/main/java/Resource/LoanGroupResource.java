package Resource;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import Objects.Group;
import Objects.Loan;
import Objects.LoanGroup;
import Objects.LoanGroupInformation;
import Services.LoanGroupService;
import Services.LoanService;
import Services.ServiceProvider;


@Path("/loangroup")
public class LoanGroupResource {
	private LoanGroupService service = ServiceProvider.getLoanGroupService();
	
	private JsonObjectBuilder buildJSON(LoanGroup l){
		JsonObjectBuilder job = Json.createObjectBuilder();
		
		job.add("loanGroupId", l.getGroupId());
		job.add("loanId", l.getLoanId());
		
		return job;
	}
	
	@GET
	@Path("/loanofficer/{loanofficerId}")
	@Produces("application/json")
	public String getGroupByLoanOfficer(@PathParam("loanofficerId") int loanofficerId){
		JsonArrayBuilder jab = Json.createArrayBuilder();
		JsonArrayBuilder secondJab = Json.createArrayBuilder();
		
		for(Integer groupId : service.getAllLoanGroupsByLoanOfficer(loanofficerId)){
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("groupid", groupId);
			for(LoanGroupInformation l :service.getAllApplicantsByLoanGroupId(groupId)){		
				JsonObjectBuilder secondJob = Json.createObjectBuilder();
				secondJob.add("firstname", l.getFirstname());
				secondJob.add("lastname", l.getLastname());
				secondJob.add("userid", l.getUserId());
				secondJob.add("paidamount", l.getPaidAmount());
				secondJob.add("amount", l.getAmount());
				secondJob.add("loanid", l.getLoanId());
				secondJob.add("groupid", l.getGroupId());
				
				secondJab.add(secondJob);
			}
			job.add("groupinformation", secondJab);
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}
	
	@GET
	@Path("/{groupId}")
	@Produces("application/json")
	public String getLoanGroupByGroupId(@PathParam("groupId") int groupId){
		JsonArrayBuilder jab = Json.createArrayBuilder();
		JsonArrayBuilder secondJab = Json.createArrayBuilder();
		LoanService loanService = ServiceProvider.getLoanService();
		LoanResource loanResource = new LoanResource();
		
		for(LoanGroup l : service.getLoanGroupById(groupId)){
			JsonObjectBuilder job = Json.createObjectBuilder();
			
			job.add("loanid", l.getLoanId());
			
			for (Loan i : loanService.getLoanById(l.getLoanId())){
				secondJab.add(loanResource.getLoanJson(i));
			}
			
			job.add("loaninformation", secondJab);			
			
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}
}
