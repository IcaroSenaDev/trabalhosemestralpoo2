import java.util.*;

public class PessoaServico {
	private final Repositorio<Pessoa> repo;
	private EmprestimoServico emprestimos;

	public PessoaServico(Repositorio<Pessoa> repo, EmprestimoServico e) {
		this.repo = repo;
		emprestimos = e;
	}

	public void setEmprestimoServico(EmprestimoServico e) {
		emprestimos = e;
	}

	public void cadastrarAluno(String n, String c, String m, String curso) {
		repo.adicionar(new Aluno(n, c, m, curso));
	}

	public void cadastrarFuncionario(String n, String c, String cargo, String setor) {
		repo.adicionar(new Funcionario(n, c, cargo, setor));
	}

	public boolean alterar(int id, String n, String cpf) {
		Pessoa p = repo.buscarPorId(id);
		if (p == null)
			return false;
		p.setNome(n);
		p.setCpf(cpf);
		return true;
	}

	public boolean remover(int id) {
		Pessoa p = repo.buscarPorId(id);
		if (p == null || (emprestimos != null && emprestimos.temEmprestimoAbertoPessoa(id)))
			return false;
		return repo.remover(id);
	}

	public List<Pessoa> listar() {
		return repo.listarTodos();
	}

	public Pessoa buscarPorId(int id) {
		return repo.buscarPorId(id);
	}

	public List<Pessoa> buscarPorNome(String nome) {
		String q = nome.toLowerCase(Locale.ROOT);
		List<Pessoa> r = new ArrayList<>();
		for (Pessoa p : listar())
			if (p.getNome().toLowerCase(Locale.ROOT).contains(q))
				r.add(p);
		return r;
	}

	public List<Pessoa> listarOrdenadoPorNome() {
		List<Pessoa> l = listar();
		l.sort(Comparator.comparing(Pessoa::getNome, String.CASE_INSENSITIVE_ORDER));
		return l;
	}
}
