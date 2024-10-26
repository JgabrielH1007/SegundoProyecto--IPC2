/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Usuario;

import Backend.DataBase.ClaseDBUsuario;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author gabrielh
 */
public class AdministradorUsuario {
    ClaseDBUsuario dbUser = new ClaseDBUsuario();
    
    public void crearUsuario(Usuario usuario){
        if(verificacionDatosNewUser(usuario) || dbUser.usuarioExiste(usuario)){
            
        }
        
        usuario.setPassword(hashPassword(usuario.getPassword()));
        dbUser.crearUsuario(usuario);
    }
    
    public void crearPerfil(Usuario usuario, Perfil perfil){
        
    }
    
    public Usuario iniciarUsuario(String userName, String password){
        password = hashPassword(password);
        return dbUser.obtenerUsuario(userName, password);
    }
    
    public Perfil obtenerPerfil(String userName){
        return dbUser.obtenerPerfil(userName);
    }
    
    public void cambiarPassword(String user, String OldPassword, String newPassword){
         dbUser.cambiarPassword(user, OldPassword, newPassword);
    }
    
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12)); 
    }
    
    private boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
    
    private boolean verificacionDatosNewUser(Usuario usuario){
        return usuario.getUserName() != null || usuario.getRol() != null || usuario.getCartera() > 0;
    }
    
}
