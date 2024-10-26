/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.DataBase;

import Backend.Usuario.AdministradorUsuario;
import Backend.Usuario.Perfil;
import Backend.Usuario.Rol;
import Backend.Usuario.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 *
 * @author gabrielh
 */
public class ClaseDBUsuario {

    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/sistema_revista";
    private static final String USER = "root";
    private static final String PASSWORD = "1007";
    private Connection connection;
    private DataSource datasource;

    public ClaseDBUsuario() {
    }

    // Método para crear un nuevo usuario en la base de datos
    public void crearUsuario(Usuario usuario) {
        String query = "INSERT INTO usuario (user_name, password, rol, cartera) VALUES (?, ?, ?, ?)";
        try (Connection con = DataSourceDBSingleton.getInstance().getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, usuario.getUserName());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setString(3, usuario.getRol().name());
            pstmt.setFloat(4, usuario.getCartera());

            pstmt.executeUpdate();
            System.out.println("Usuario creado con éxito");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean usuarioExiste(Usuario usuario) {
        String query = "SELECT COUNT(*) FROM usuario WHERE user_name = ?";
        try (Connection con = DataSourceDBSingleton.getInstance().getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, usuario.getUserName());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si hay al menos un registro
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false si ocurre una excepción o no se encuentra el usuario
    }

    // Método para obtener un usuario por nombre de usuario y contraseña
    public Usuario obtenerUsuario(String userName, String password) {
        Usuario user = null;
        String query = "SELECT * FROM usuario WHERE user_name = ? AND password = ?";

        try (Connection con = DataSourceDBSingleton.getInstance().getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new Usuario();
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setRol(Rol.valueOf(rs.getString("rol")));
                user.setCartera(rs.getFloat("cartera"));
                System.out.println("Usuario encontrado: " + userName);
            } else {
                System.out.println("Usuario no encontrado o contraseña incorrecta");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Método para obtener el perfil de un usuario
    public Perfil obtenerPerfil(String userName) {
        Perfil perfil = null;
        String query = "SELECT * FROM perfil WHERE user_name = ?";

        try (Connection con = DataSourceDBSingleton.getInstance().getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                perfil = new Perfil();
                perfil.setUserName(rs.getString("user_name"));
                perfil.setFotoPerfil(rs.getBytes("foto"));
                perfil.setTemasInteres(rs.getString("tema_interes"));
                perfil.setHobbies(rs.getString("hobbies"));
                perfil.setGustos(rs.getString("gustos"));
                perfil.setDescripcion(rs.getString("descripcion"));
                System.out.println("Perfil encontrado para usuario: " + userName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return perfil;
    }

    private boolean comprobarContrasenaActual(String userName, String contrasenaActual) {
        String query = "SELECT password FROM usuario WHERE user_name = ? AND password = ?";
        boolean esCorrecta = false;

        try (Connection con = DataSourceDBSingleton.getInstance().getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, userName);
            pstmt.setString(2, contrasenaActual);

            try (ResultSet rs = pstmt.executeQuery()) {
                esCorrecta = rs.next();  // Si hay un resultado, la contraseña es correcta
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return esCorrecta;
    }

    public void cambiarPassword(String userName, String contrasenaActual, String newPassword) {
        if (comprobarContrasenaActual(userName, contrasenaActual)) {
            String query = "UPDATE usuario SET password = ? WHERE user_name = ?";

            try (Connection con = DataSourceDBSingleton.getInstance().getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {

                pstmt.setString(1, newPassword);
                pstmt.setString(2, userName);

                int filasActualizadas = pstmt.executeUpdate();
                if (filasActualizadas > 0) {
                    System.out.println("Contraseña actualizada correctamente");
                } else {
                    System.out.println("Usuario no encontrado");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("La contraseña actual es incorrecta");
        }
    }

}
