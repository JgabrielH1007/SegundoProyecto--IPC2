/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Usuario;

/**
 *
 * @author gabrielh
 */
public class Usuario {
    private String userName;
    private String password;
    private Rol rol;
    private float cartera;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public float getCartera() {
        return cartera;
    }

    public void setCartera(float cartera) {
        this.cartera = cartera;
    }
    
    
    
}
