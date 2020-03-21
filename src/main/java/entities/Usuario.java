package entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.ws.rs.WebApplicationException;
import javax.xml.bind.annotation.XmlRootElement;
import generics.DAO;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity
@Table(name="Usuarios")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u"),
	@NamedQuery(name="Usuario.login", query="SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha")
})
public class Usuario extends DAO<Usuario> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String nome;
	private String email;
	private String senha;
	
	
	public Usuario() {
	}

	public Usuario(int id, String nome, String email, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}
   
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void login() {
		Usuario usuario = null;
		try {
			super.createConnection();
			
			usuario = super.em.createNamedQuery("Usuario.login",Usuario.class)
					.setParameter("email", this.getEmail())
					.setParameter("senha", this.getSenha())
					.getSingleResult();
			
		}catch(NoResultException e) {
			throw new WebApplicationException(401);
		}catch(Exception e) {
			throw new WebApplicationException(500);
		}finally {
			super.em.close();
		}
		
		if(usuario != null) {
			this.setId(usuario.getId());
			this.setNome(usuario.getNome());
			this.setSenha(null);
		}else {
    		throw new WebApplicationException(401);
		}
	}
}
