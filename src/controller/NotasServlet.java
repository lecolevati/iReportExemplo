package controller;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import persistence.GenericDao;
import model.Materias;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

@WebServlet("/buscanotas")
public class NotasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NotasServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String erro = "";
		Materias mat = new Materias();
		mat.setId(Integer.parseInt(request.getParameter("disciplina")));
		byte[] bytes = null;
		ServletContext context = getServletContext();

		try {

			HashMap<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("materia", mat.getId());

			String pathJasper = "/WEB-INF/reports/NotasFinais.jasper";

			JasperReport relatorioJasper = (JasperReport) JRLoader
					.loadObjectFromFile(context.getRealPath(pathJasper));
			bytes = JasperRunManager.runReportToPdf(relatorioJasper,
					parametros, new GenericDao().getConnection());
		} catch (JRException | ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {
			if (bytes != null) {
				response.setContentType("application/pdf");
				response.setContentLength(bytes.length);
				ServletOutputStream ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
				ouputStream.close();
			} else {
				PrintStream tela = new PrintStream(response.getOutputStream());
				tela.println("<HTML><BODY>");
				tela.println("<script>alert('" + erro + ");history.back();</script>");
				tela.println("<BR><P>");
				tela.println("</HTML></BODY>");
			}
		}
	}
}
