import java.util.*;

public class LivroServico {
	private final Repositorio<Livro> repo;
	private EmprestimoServico emprestimos;

	public LivroServico(Repositorio<Livro> r, EmprestimoServico e) {
		repo = r;
		emprestimos = e;
	}

	public void setEmprestimoServico(EmprestimoServico e) {
		emprestimos = e;
	}

	public void cadastrar(String t, String a, int ano) {
		repo.adicionar(new Livro(t, a, ano));
	}

	public boolean alterar(int id, String t, String a, int ano) {
		Livro l = repo.buscarPorId(id);
		if (l == null)
			return false;
		l.setTitulo(t);
		l.setAutor(a);
		l.setAnoPublicacao(ano);
		return true;
	}

	public boolean remover(int id) {
		Livro l = repo.buscarPorId(id);
		if (l == null || !l.isDisponivel() || (emprestimos != null && emprestimos.temEmprestimoAbertoLivro(id)))
			return false;
		return repo.remover(id);
	}

	public List<Livro> listar() {
		return repo.listarTodos();
	}

	public Livro buscarPorId(int id) {
		return repo.buscarPorId(id);
	}

	public List<Livro> buscarPorTitulo(String titulo) {
		String q = titulo.toLowerCase(Locale.ROOT);
		List<Livro> r = new ArrayList<>();
		for (Livro l : listar())
			if (l.getTitulo().toLowerCase(Locale.ROOT).contains(q))
				r.add(l);
		return r;
	}

	public List<Livro> listarOrdenadoPorTitulo() {
		List<Livro> l = listar();
		l.sort(Comparator.comparing(Livro::getTitulo, String.CASE_INSENSITIVE_ORDER));
		return l;
	}

	public List<Livro> listarOrdenadoPorAno() {
		List<Livro> l = listar();
		l.sort(Comparator.comparingInt(Livro::getAnoPublicacao));
		return l;
	}
}
