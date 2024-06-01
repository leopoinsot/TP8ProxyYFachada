package ejercicio1.accesos;
import ejercicio1.modelo.Persona;
import ejercicio1.modelo.ProxySet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
public class PersonaDao {
	private String urlBD; //jdbc:mysql://localhost:3306/tp8proxyyfachadas
	private String usuarioBD; //LeonelAriel
	private String contrasenaBD; //villa2015

	public PersonaDao(String urlBD,String usuarioBD, String contrasenaBD){
		this.urlBD = urlBD;
		this.usuarioBD = usuarioBD;
		this.contrasenaBD = contrasenaBD;
	}
	private Connection obtenerConexion() {
		try {
			return DriverManager.getConnection(urlBD, usuarioBD, contrasenaBD);
		} catch (SQLException e) {
			throw new RuntimeException("Error al obtener la conexión a la base de datos", e);
		}
	}

	public Persona personaPorId(int id) {
		String sql = "SELECT p.nombre, t.numero FROM personas p, telefonos t WHERE p.id = t.idpersona AND p.id = ?";
		try (Connection conn = obtenerConexion();
			 PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			String nombrePersona = null;
			while (result.next()) {
				nombrePersona = result.getString("nombre");
			}
			return new Persona(id, nombrePersona, new ProxySet(new TelefonoDao(urlBD, usuarioBD, contrasenaBD), id));
		} catch (SQLException e) {
			throw new RuntimeException("Error al ejecutar la consulta SQL", e);
		}
	}
}