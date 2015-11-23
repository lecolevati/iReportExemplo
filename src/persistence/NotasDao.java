package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Materias;
import model.Notas;

public class NotasDao {

	private Connection c;
	
	public NotasDao() throws ClassNotFoundException, SQLException {
		GenericDao gDao = new GenericDao();
		c = gDao.getConnection();
	}
	
	public List<Notas> listaNotasPorMateria(Materias m) throws SQLException{
		List<Notas> lista = new ArrayList<Notas>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT "); 
		sql.append("mat.nome as materia, SUBSTRING(al.ra,1,9)+'-'+SUBSTRING(al.ra,10,1) AS ra, ");
		sql.append("al.nome, CAST(SUM(av.peso * nt.nota) AS DECIMAL(7,2)) AS nota_final, "); 
		sql.append("CASE ");
		sql.append("WHEN CAST(SUM(av.peso * nt.nota) AS DECIMAL(7,2)) >= 6.0 "); 
		sql.append("THEN 'APROVADO'  ");
		sql.append("ELSE 'REPROVADO'  ");
		sql.append("END AS conceito, ");
		sql.append("CASE	 ");
		sql.append("WHEN CAST(SUM(av.peso * nt.nota) AS DECIMAL(7,2)) >= 6.0 "); 
		sql.append("THEN 0  ");
		sql.append("ELSE CAST(6.0 - SUM(av.peso * nt.nota) AS DECIMAL(7,2)) "); 
		sql.append("END AS faltante, ");
		sql.append("CASE	 ");
		sql.append("WHEN CAST(SUM(av.peso * nt.nota) AS DECIMAL(7,2)) >= 6.0 "); 
		sql.append("THEN 0  ");
		sql.append("ELSE			CASE "); 
		sql.append("					WHEN CAST(SUM (av.peso * nt.nota) AS DECIMAL(7,2)) <= 3.0 ");
		sql.append("					THEN 0 ");
		sql.append("					ELSE CAST(12.0 - SUM(av.peso * nt.nota) AS DECIMAL(7,2)) "); 
		sql.append("END "); 
		sql.append("END AS min_exame ");
		sql.append("FROM alunos al "); 
		sql.append("INNER JOIN notas nt ");
		sql.append("ON al.ra = nt.ra_aluno ");
		sql.append("INNER JOIN avaliacoes av ");
		sql.append("ON av.id = nt.id_avaliacao ");
		sql.append("INNER JOIN materias mat ");
		sql.append("ON mat.id = nt.id_materia ");
		sql.append("WHERE mat.id = ? ");
		sql.append("GROUP BY ra, mat.nome,al.nome ");
		sql.append("ORDER BY al.nome ");
		PreparedStatement ps = c.prepareStatement(sql.toString());
		ps.setInt(1, m.getId());
		ResultSet rs = ps.executeQuery();
		while (rs.next()){
			Notas n = new Notas();
			n.setConceito(rs.getString("conceito"));
			n.setFaltante(rs.getDouble("faltante"));
			n.setMinExame(rs.getDouble("min_exame"));
			n.setNome(rs.getString("nome"));
			n.setNotaFinal(rs.getDouble("nota_final"));
			n.setRa(rs.getString("ra"));
			m.setNome(rs.getString("materia"));
			n.setMat(m);
			lista.add(n);
		}
		return lista;
	}
}
