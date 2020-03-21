package resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.Usuario;




/**
 * Root resource (exposed at "myresource" path)
 */
@Path("usuario")
public class UsuarioResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario store(Usuario usuario) {
    	try {
    		usuario.save();
        	return usuario;
    	}catch(Exception e) {
			throw new InternalServerErrorException();
    	}
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> list() {
    	return new Usuario().list();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario find(@PathParam( "id" ) int id) {
    	Usuario usuario = new Usuario();
    	usuario = usuario.find(id);
      	
    	return usuario;
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response delete(@PathParam( "id" ) int id) {
    	Usuario usuario = new Usuario();
    	usuario.setId(id);
    	try {
    		if(usuario.delete()) {
                return Response.status(Response.Status.OK).entity("Usuário deletado com sucesso!").build();
        	}else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Não foi possivel deletar este usuário.").build();
        	}
    	}catch(Exception e) {
    		throw new InternalServerErrorException();
    	}
    	
    }
    
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario update(@PathParam("id") int id, Usuario usuario) {
    	usuario.setId(id);
    	try {
    		usuario.save();	
    	}catch(Exception e) {
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
    	}
    	
    	return usuario;
    }
}
