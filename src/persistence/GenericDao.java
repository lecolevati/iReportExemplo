package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Criado pelo Prof. M.Sc. Leandro Colevati dos Santos
 */
public class GenericDao {

	private static Connection con;

	public Connection getConnection() throws SQLException,
			ClassNotFoundException {

		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		con = DriverManager
				.getConnection(
						"jdbc:jtds:sqlserver://127.0.0.1:1433;DatabaseName=aulajoin10;namedPipe=true",
						"leandro", "123456");
		return con;
	}

	public void fechaConexao() {
		try {
			if (con != null)
				con.close();
			con = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
