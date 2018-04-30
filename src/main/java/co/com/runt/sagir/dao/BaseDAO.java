package co.com.runt.sagir.dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Camilo Alvarez Duran <camilo.alvarez at runt.com.co>
 */
public class BaseDAO {
    

    private static final String DATSOURCE_JNDI_NAME = "jdbc/SAGIR";

    /**
     *
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static Connection getConnection() throws NamingException, SQLException {
        return getDS().getConnection();
    }

    private static DataSource getDS() throws NamingException {
        final InitialContext iContext = new InitialContext();
        return (DataSource) iContext.lookup(DATSOURCE_JNDI_NAME);
    }
}
