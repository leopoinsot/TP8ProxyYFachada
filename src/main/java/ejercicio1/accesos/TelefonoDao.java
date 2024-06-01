package ejercicio1.accesos;

import ejercicio1.modelo.Telefono;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TelefonoDao {
	private String urlBD; //jdbc:mysql://localhost:3306/tp8proxyyfachadas
	private String usuarioBD; //LeonelAriel
	private String contrasenaBD; //villa2015

	public TelefonoDao(String urlBD,String usuarioBD, String contrasenaBD){
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
	public Telefono[] obtenerTelefonosDeUnaPersona(int idPersona) {
		String sql = "SELECT t.numero FROM telefonos t WHERE t.idPersona = ?";
		try (Connection conn = obtenerConexion();
			 PreparedStatement statement = conn.prepareStatement(sql)) {
			statement.setInt(1, idPersona);
			ResultSet result = statement.executeQuery();
			List<Telefono> telefonos = new ArrayList<>();
			while (result.next()) {
				telefonos.add(new Telefono(result.getString("numero")));
			}
			return telefonos.toArray(new Telefono[0]);
		} catch (SQLException e) {
			throw new RuntimeException("Error al ejecutar la consulta SQL", e);
		}
	}
}
