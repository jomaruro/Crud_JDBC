package modelo;

import java.io.Serializable;
import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Clase OracleBaseDatos, controla la conexión con la base de datos.
 *
 * @author User.
 *
 */
public class OracleBaseDatos implements InterfazBaseDatos, Serializable {

    private static final long serialVersionUID = 1L;
//	private static final Logger log = LoggerFactory.getLogger(OracleBaseDatos.class);

    private String sError;
    DataSource ds;

    /**
     * Inicializa la conexión con la base de datos.
     *
     * @throws Exception	Lanza excepción si no se puede abrir la base de datos.
     */
    public OracleBaseDatos() throws Exception {
        sError = null;
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/CrudJDBC");
        } catch (NamingException e) {
            System.err.println("No se pudo abrir la base de datos : " + e.getMessage());
        }
    }

    /**
     * Establece la conexión con la base de datos.
     * @return 
     */
    @Override
    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (Exception e) {
            sError = e.getMessage();
            System.err.println("OracleBaseDatos - No puedo dar mas conexiones " + e.getMessage());
            return null;
        }
    }

    @Override
    public String getError() {
        return sError;
    }
}
