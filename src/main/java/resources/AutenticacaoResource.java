package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import entities.Usuario;

@Path("autenticacao")
public class AutenticacaoResource {
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario login(Usuario usuario) {
    	try {
    		usuario.login();
    		if(usuario.getId() != 0) {
    			return usuario;
    		}
        	
    		throw new WebApplicationException(401);
    	}catch(WebApplicationException e) {
			throw new WebApplicationException(401);
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
			throw new InternalServerErrorException();
    	}
    }
}
