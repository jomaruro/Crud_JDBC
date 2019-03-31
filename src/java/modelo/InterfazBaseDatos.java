package modelo;

import java.sql.Connection;

/**
 * Interfaz para conectar con la base de datos.
 * @author User
 *
 */
public interface InterfazBaseDatos {

	public abstract Connection getConnection();

	public abstract String getError();
}