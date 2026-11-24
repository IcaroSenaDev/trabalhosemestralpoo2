import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class EmprestimoServico {
    private final Repositorio<Emprestimo> repo;
    private final PessoaServico pessoas;
    private final LivroServico livros;
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public EmprestimoServico(Repositorio<Emprestimo> repo, PessoaServico pessoas, LivroServico livros) {
        this.repo = repo;
        this.pessoas = pessoas;
        this.livros = livros;
    }

    private LocalDate parse(String data) {
        try {
            return LocalDate.parse(data, FORMATO);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data inválida. Use dd/MM/yyyy.");
        }
    }

    public String registrar(int idPessoa, int idLivro, String data) {
        LocalDate dataValida = parse(data);
        Pessoa pessoa = pessoas.buscarPorId(idPessoa);
        Livro livro = livros.buscarPorId(idLivro);
        if (pessoa == null) return "Pessoa não encontrada.";
        if (livro == null) return "Livro não encontrado.";
        if (!livro.isDisponivel()) return "Livro indisponível.";

        int abertos = 0;
        for (Emprestimo emprestimo : listar()) {
            if (!emprestimo.isDevolvido() && emprestimo.getPessoa().getId() == idPessoa) abertos++;
        }
        if (abertos >= pessoa.getLimiteEmprestimos()) {
            return "Limite de empréstimos atingido (" + pessoa.getLimiteEmprestimos() + ").";
        }

        livro.setDisponivel(false);
        repo.adicionar(new Emprestimo(pessoa, livro, dataValida.format(FORMATO)));
        return "Empréstimo registrado. Devolução prevista para "
                + dataValida.plusDays(7).format(FORMATO) + ".";
    }

    public String devolver(int id) {
        Emprestimo emprestimo = repo.buscarPorId(id);
        if (emprestimo == null) return "Empréstimo não encontrado.";
        if (emprestimo.isDevolvido()) return "Este empréstimo já foi devolvido.";
        emprestimo.devolver();
        return "Devolução registrada.";
    }

    public boolean remover(int id) {
        Emprestimo emprestimo = repo.buscarPorId(id);
        if (emprestimo == null) return false;
        if (!emprestimo.isDevolvido()) emprestimo.devolver();
        return repo.remover(id);
    }

    public List<Emprestimo> listar() { return repo.listarTodos(); }

    public boolean temEmprestimoAbertoPessoa(int id) {
        for (Emprestimo e : listar()) if (!e.isDevolvido() && e.getPessoa().getId() == id) return true;
        return false;
    }

    public boolean temEmprestimoAbertoLivro(int id) {
        for (Emprestimo e : listar()) if (!e.isDevolvido() && e.getLivro().getId() == id) return true;
        return false;
    }

    public List<Emprestimo> buscarPorNomePessoa(String nome) {
        String termo = nome.toLowerCase(Locale.ROOT);
        List<Emprestimo> encontrados = new ArrayList<>();
        for (Emprestimo e : listar()) {
            if (e.getPessoa().getNome().toLowerCase(Locale.ROOT).contains(termo)) encontrados.add(e);
        }
        return encontrados;
    }

    public List<Emprestimo> listarOrdenadoPorData() {
        List<Emprestimo> lista = listar();
        lista.sort(Comparator.comparing(e -> parse(e.getDataEmprestimo())));
        return lista;
    }
}
