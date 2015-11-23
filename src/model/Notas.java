package model;

public class Notas {

	private String ra;
	private String nome;
	private double notaFinal;
	private String conceito;
	private double faltante;
	private double minExame;
	private Materias mat;
	
	public Materias getMat() {
		return mat;
	}
	public void setMat(Materias mat) {
		this.mat = mat;
	}
	public String getRa() {
		return ra;
	}
	public void setRa(String ra) {
		this.ra = ra;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getNotaFinal() {
		return notaFinal;
	}
	public void setNotaFinal(double notaFinal) {
		this.notaFinal = notaFinal;
	}
	public String getConceito() {
		return conceito;
	}
	public void setConceito(String conceito) {
		this.conceito = conceito;
	}
	public double getFaltante() {
		return faltante;
	}
	public void setFaltante(double faltante) {
		this.faltante = faltante;
	}
	public double getMinExame() {
		return minExame;
	}
	public void setMinExame(double minExame) {
		this.minExame = minExame;
	}

}
