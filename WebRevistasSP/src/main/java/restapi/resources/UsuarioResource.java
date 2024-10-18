/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restapi.resources;
import Backend.DataBase.ClaseDBUsuario;
import Backend.Usuario.AdministradorUsuario;
import Backend.Usuario.Usuario;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author gabrielh
 */
@Path("Usuarios")
public class UsuarioResource {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(Usuario usuario) {
        AdministradorUsuario adminUser = new AdministradorUsuario();
        adminUser.crearUsuario(usuario);
        return Response.status(Response.Status.CREATED).build();
    }
    
    
    
}
