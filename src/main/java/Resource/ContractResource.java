package Resource;

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

import Objects.Contract;
import Objects.User;
import PdfGenerator.GeneratePage;
import Services.ServiceProvider;
import Services.ContractService;


@Path("/contract")
public class ContractResource {
	
	    private ContractService service = ServiceProvider.getContractService();

	    private JsonObjectBuilder buildJSON(Contract c) {
	        JsonObjectBuilder job = Json.createObjectBuilder();

	        job.add("contractid", c.getContractID())
	                .add("status", c.getStatus())
	                .add("description", c.getDescription())
	                .add("contractpdf", c.getContractPDF())
	                .add("useridfk", c.getUserIDFK());

	        return job;
	    }
	    @GET
//	    @RolesAllowed({"beheerder","admin"})
	    @Produces("application/json")
	    public String getAccounts() {
	    	JsonArrayBuilder jab = Json.createArrayBuilder();

	        for (Contract c : service.getAllContracts()) {
	            jab.add(buildJSON(c));
	        }

	        JsonArray array = jab.build();
	        return array.toString();
	    }

	    @GET
	    @Path("/{id}")
//	    @RolesAllowed({"beheerder","admin","user"})
	    @Produces("application/json")
	    public String getAccountByID(@PathParam("id") int id) {
	        Contract c = service.getContractByID(id);
	        if(c != null) {
	            JsonArrayBuilder jab = Json.createArrayBuilder();
	            jab.add(buildJSON(c));
	            return jab.build().toString();
	        }
	        return Response.status(Response.Status.NOT_FOUND).toString();
	    }
	    
	    @GET
	    @Path("/userContracts/{id}")
	    @Produces("application/json")
	    public String getContractsByFK(@PathParam("id") int id) {
	    	JsonArrayBuilder jab = Json.createArrayBuilder();
	    	
	        for (Contract c : service.getContractsByFK(id)) {
	            jab.add(buildJSON(c));
	        }

	        JsonArray array = jab.build();
	        return array.toString();
	    }
	    
	    @POST
	    @Produces("application/json")
	    public Response addContract(@FormParam("contractid") int contractId,
	                             @FormParam("status") String status,
	                             @FormParam("description") String desc,
	                             @FormParam("contractpdf") String pdf,
	                             @FormParam("userid") int userId
	                             )
	    {
	    	ContractService service = ServiceProvider.getContractService();
	    	
	        Contract newContract = new Contract(contractId, status, desc, pdf, userId);
	        if (service.newContract(newContract)){
	            return Response.ok().build();
	        } else {
	            return Response.status(Response.Status.BAD_REQUEST).build();
	        }
	    }
}
