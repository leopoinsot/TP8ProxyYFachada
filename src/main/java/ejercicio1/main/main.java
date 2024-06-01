package ejercicio1.main;

import ejercicio1.accesos.PersonaDao;
import ejercicio1.modelo.Persona;
import ejercicio1.modelo.Telefono;

public class main {
	public static void main(String args[]) {
		PersonaDao dao = new PersonaDao(
				"jdbc:mysql://localhost:3306/tp8proxyyfachada",
				"LeonelAriel",
				"villa2015");

		Persona p = dao.personaPorId(1);
		System.out.println(p.nombre());
		for (Telefono telefono : p.telefonos()) {
			System.out.println(telefono);
		}
	}
}
