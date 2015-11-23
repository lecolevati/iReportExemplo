package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Materias;

public class MateriasDao {

	private Connection c;
	
	public MateriasDao() throws ClassNotFoundException, SQLException {
		GenericDao gDao = new GenericDao();
		c = gDao.getConnection();
	}
	
	public List<Materias> listaMaterias() throws SQLException{
		List<Materias> lista = new ArrayList<Materias>();
		String sql = "SELECT id, nome, carga_horaria FROM materias";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()){
			Materias m = new Materias();
			m.setCargaHoraria(rs.getInt("carga_horaria"));
			m.setId(rs.getInt("id"));
			m.setNome(rs.getString("nome"));
			lista.add(m);
		}
		return lista;
	}
}
