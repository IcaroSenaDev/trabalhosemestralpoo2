import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Emprestimo implements Exportavel {
	private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final int PRAZO_DIAS = 7;

	private int id;
	private Pessoa pessoa;
	private Livro livro;
	private String dataEmprestimo;
	private String dataPrevistaDevolucao;
	private boolean devolvido;

	public Emprestimo(Pessoa pessoa, Livro livro, String dataEmprestimo) {
		this.pessoa = pessoa;
		this.livro = livro;
		this.dataEmprestimo = dataEmprestimo;
		LocalDate data = LocalDate.parse(dataEmprestimo, FORMATO);
		this.dataPrevistaDevolucao = data.plusDays(PRAZO_DIAS).format(FORMATO);
		this.devolvido = false;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public String getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(String dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
		recalcularDataPrevista();
	}

	public String getDataPrevistaDevolucao() {
		return dataPrevistaDevolucao;
	}

	public void setDataPrevistaDevolucao(String dataPrevistaDevolucao) {
		this.dataPrevistaDevolucao = dataPrevistaDevolucao;
	}

	public boolean isDevolvido() {
		return devolvido;
	}

	public void setDevolvido(boolean devolvido) {
		this.devolvido = devolvido;
	}

	private void recalcularDataPrevista() {
		LocalDate data = LocalDate.parse(dataEmprestimo, FORMATO);
		dataPrevistaDevolucao = data.plusDays(PRAZO_DIAS).format(FORMATO);
	}

	public void devolver() {
		if (!devolvido) {
			devolvido = true;
			livro.setDisponivel(true);
		}
	}

	@Override
	public String paraLinhaTabela() {
		return String.format("%-4d | %-22s | %-30s | %-10s | %-10s | %-10s", id, pessoa.getNome(), livro.getTitulo(),
				dataEmprestimo, dataPrevistaDevolucao, devolvido ? "Devolvido" : "Em aberto");
	}
}
