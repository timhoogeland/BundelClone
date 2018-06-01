package Resource;

import java.util.Random;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import Objects.Adress;
import Objects.Contract;
import Objects.User;
import PdfGenerator.RetrieveData;
import Services.AddressService;
import Services.ServiceProvider;

@Path("/address")
public class AddressResource {
    private AddressService service = ServiceProvider.getAdressService();

	    private JsonObjectBuilder buildJSON(Adress a) {
	        JsonObjectBuilder job = Json.createObjectBuilder();

	        job.add("adressid", a.getAdressId())
	                .add("street", a.getStreet())
	                .add("number", a.getNumber())
	                .add("country", a.getCountry())
	                .add("postalcode", a.getPostalCode())
	        		.add("description", a.getDescription())
	        		.add("location", a.getLocation());

	        return job;
	    }
	    @GET
//	    @RolesAllowed({"beheerder","admin"})
	    @Produces("application/json")
	    public String getAccounts() {
	        JsonArrayBuilder jab = Json.createArrayBuilder();

	        for (Adress a : service.getAllAdresses()) {
	            jab.add(buildJSON(a));
	        }

	        JsonArray array = jab.build();
	        return array.toString();
	    }
	    
	    @GET
	    @Path("/{id}")
//	    @RolesAllowed({"beheerder","admin","user"})
	    @Produces("application/json")
	    public String getAdressByID(@PathParam("id") int id) {
	        Adress c = service.getAdressByID(id);
	        if(c != null) {
	            JsonArrayBuilder jab = Json.createArrayBuilder();
	            jab.add(buildJSON(c));
	            return jab.build().toString();
	        }
	        return Response.status(Response.Status.NOT_FOUND).toString();
	    }
	    
	    @POST
	    @Produces("application/json")
	    public Response addAddress(
	                               @FormParam("street") String street,
	                               @FormParam("number") int number,
	                               @FormParam("country") String country,
	                               @FormParam("postalcode") String postalcode,
	                               @FormParam("description") String description,
	                               @FormParam("location") String location){

	    	RetrieveData data = new RetrieveData();	        
	    	
	        Adress newAdress = new Adress(street, number, country, postalcode, description, location);
	        Adress returnAdress = service.newAddress(newAdress);
	        if (returnAdress != null) {
	        	data.setAdresData(newAdress);
	            String a = buildJSON(returnAdress).build().toString();
	            return Response.ok(a).build();
	        } else {
	            return Response.status(Response.Status.BAD_REQUEST).build();
	        }
	    }	
	    
	    @PUT
	    @Produces("application/json")
	    @Path("/{id}")
	    public Response changeAddress(	@PathParam("id") int addressId,
	    		 						@FormParam("street") String street,
	    		 						@FormParam("number") int number,
	    		 						@FormParam("country") String country,
	    		 						@FormParam("postalcode") String postalcode,
	    		 						@FormParam("description") String description,
	    		 						@FormParam("location") String location){
	    	
	    	Adress updatedAdress = new Adress(addressId, street, number, country, postalcode, description, location);
	    	Adress returnAdress = service.updateAddress(updatedAdress);
	        if (returnAdress != null) {
	            String a = buildJSON(returnAdress).build().toString();
	            return Response.ok(a).build();
	        } else {
	            return Response.status(Response.Status.BAD_REQUEST).build();
	        }
	    }
	    
	    @DELETE
	    @Produces("application/json")
	    @Path("/{id}")
	    public Response deleteAddress(	@PathParam("id") int addressId){
	    
	    	if (service.deleteAdress(addressId)){
	    		return Response.ok().build();
	    	} else {
	    		return Response.status(Response.Status.BAD_REQUEST).build();
	    	}
	    }
}