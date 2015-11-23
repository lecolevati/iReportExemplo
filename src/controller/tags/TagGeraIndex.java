package controller.tags;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import persistence.MateriasDao;
import model.Materias;

public class TagGeraIndex extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		StringBuffer sb = new StringBuffer();
		try {
			MateriasDao mDao = new MateriasDao();
			List<Materias> lista = mDao.listaMaterias();
			sb.append("<form action=\"buscanotas\" method=\"post\" target=\"_blank\">");
			sb.append("<table border = \"1\">");
			sb.append("<tr>");
			sb.append("<td>");
			sb.append("Disciplina:");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("<select name = \"disciplina\">");
			for (Materias m : lista){
				sb.append("<option value=\"");
				sb.append(m.getId());
				sb.append("\">");
				sb.append(m.getNome());
				sb.append("</option>");
			}
			sb.append("</select>");
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td colspan = \"2\">");
			sb.append("<input type=\"submit\"  name=\"buscar\" value=\"Buscar\" />");
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("</table>");
			sb.append("</form>");
		} catch (SQLException | ClassNotFoundException e) {
			sb.append("Erro na requisição do Banco de Dados");
		} finally {
			out.println(sb.toString());
		}
	}

}
