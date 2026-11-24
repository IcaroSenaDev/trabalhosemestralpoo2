import java.util.List;

public class Programa {
	private static PessoaServico ps;
	private static LivroServico ls;
	private static EmprestimoServico es;

	public static void main(String[] args) {
		Repositorio<Pessoa> rp = new RepositorioEmMemoria<>();
		Repositorio<Livro> rl = new RepositorioEmMemoria<>();
		Repositorio<Emprestimo> re = new RepositorioEmMemoria<>();
		DadosIniciais.carregarPessoas(rp);
		DadosIniciais.carregarLivros(rl);
		ps = new PessoaServico(rp, null);
		ls = new LivroServico(rl, null);
		es = new EmprestimoServico(re, ps, ls);
		ps.setEmprestimoServico(es);
		ls.setEmprestimoServico(es); 
		DadosIniciais.carregarEmprestimos(re, rp, rl);
		menuPrincipal();
	}

	private static void menuPrincipal() {
		int op;
		do {
			System.out.println("\n=== BIBLIOTECA ===\n1-Pessoas  2-Livros  3-Empréstimos  0-Sair");
			op = Entrada.lerInteiro("Opção: ");
			switch (op) {
			case 1 -> menuPessoas();
			case 2 -> menuLivros();
			case 3 -> menuEmprestimos();
			case 0 -> System.out.println("Encerrado.");
			default -> System.out.println("Opção inválida.");
			}
		} while (op != 0);
	}

	private static void menuPessoas() {
		int op;
		do {
			System.out.println(
					"\n-- Pessoas --\n1-Incluir aluno 2-Incluir funcionário 3-Alterar 4-Excluir 5-Listar 6-Buscar nome 7-Ordenar nome 0-Voltar");
			op = Entrada.lerInteiro("Opção: ");
			switch (op) {
			case 1 -> ps.cadastrarAluno(Entrada.lerTexto("Nome: "), Entrada.lerTexto("CPF: "),
					Entrada.lerTexto("Matrícula: "), Entrada.lerTexto("Curso: "));
			case 2 -> ps.cadastrarFuncionario(Entrada.lerTexto("Nome: "), Entrada.lerTexto("CPF: "),
					Entrada.lerTexto("Cargo: "), Entrada.lerTexto("Setor: "));
			case 3 -> {
				int id = Entrada.lerInteiro("ID: ");
				System.out.println(
						ps.alterar(id, Entrada.lerTexto("Novo nome: "), Entrada.lerTexto("Novo CPF: ")) ? "Alterado."
								: "ID não encontrado.");
			}
			case 4 -> {
				int id = Entrada.lerInteiro("ID: ");
				System.out
						.println(ps.remover(id) ? "Removido." : "Não removido (ID inválido ou empréstimo em aberto).");
			}
			case 5 -> mostrarPessoas(ps.listar());
			case 6 -> mostrarPessoas(ps.buscarPorNome(Entrada.lerTexto("Nome ou trecho: ")));
			case 7 -> mostrarPessoas(ps.listarOrdenadoPorNome());
			case 0 -> {
			}
			default -> System.out.println("Inválido.");
			}
		} while (op != 0);
	}

	private static void mostrarPessoas(List<Pessoa> lista) {
		TabelaUtil.imprimirCabecalho("ID | Tipo | Nome | CPF | Complementos");
		if (lista.isEmpty()) {
			System.out.println("Nenhum registro.");
			return;
		}
		for (Pessoa p : lista) {
			String extra = p instanceof Aluno ? ((Aluno) p).getMatricula() + " / " + ((Aluno) p).getCurso()
					: ((Funcionario) p).getCargo() + " / " + ((Funcionario) p).getSetor();
			System.out.println(p.paraLinhaTabela() + " | " + extra);
		}
	}

	private static void menuLivros() {
		int op;
		do {
			System.out.println(
					"\n-- Livros --\n1-Incluir 2-Alterar 3-Excluir 4-Listar 5-Buscar título 6-Ordenar título 7-Ordenar ano 0-Voltar");
			op = Entrada.lerInteiro("Opção: ");
			switch (op) {
			case 1 ->
				ls.cadastrar(Entrada.lerTexto("Título: "), Entrada.lerTexto("Autor: "), Entrada.lerInteiro("Ano: "));
			case 2 -> {
				int id = Entrada.lerInteiro("ID: ");
				System.out.println(ls.alterar(id, Entrada.lerTexto("Título: "), Entrada.lerTexto("Autor: "),
						Entrada.lerInteiro("Ano: ")) ? "Alterado." : "ID não encontrado.");
			}
			case 3 -> {
				int id = Entrada.lerInteiro("ID: ");
				System.out.println(
						ls.remover(id) ? "Removido." : "Não removido (indisponível, em aberto ou ID inválido).");
			}
			case 4 -> mostrarLivros(ls.listar());
			case 5 -> mostrarLivros(ls.buscarPorTitulo(Entrada.lerTexto("Título ou trecho: ")));
			case 6 -> mostrarLivros(ls.listarOrdenadoPorTitulo());
			case 7 -> mostrarLivros(ls.listarOrdenadoPorAno());
			case 0 -> {
			}
			default -> System.out.println("Inválido.");
			}
		} while (op != 0);
	}

	private static void mostrarLivros(List<Livro> lista) {
		TabelaUtil.imprimirCabecalho("ID | Título | Autor | Ano | Disponível");
		TabelaUtil.imprimirLista(lista);
	}

	private static void menuEmprestimos() {
		int op;
		do {
			System.out.println(
					"\n-- Empréstimos --\n1-Registrar 2-Devolver 3-Excluir 4-Listar 5-Buscar pessoa 6-Ordenar data 0-Voltar");
			op = Entrada.lerInteiro("Opção: ");
			switch (op) {
			case 1 -> System.out.println(es.registrar(Entrada.lerInteiro("ID pessoa: "),
					Entrada.lerInteiro("ID livro: "), Entrada.lerTexto("Data dd/MM/yyyy: ")));
			case 2 -> System.out.println(es.devolver(Entrada.lerInteiro("ID empréstimo: ")));
			case 3 -> System.out
					.println(es.remover(Entrada.lerInteiro("ID empréstimo: ")) ? "Removido." : "ID não encontrado.");
			case 4 -> mostrarEmprestimos(es.listar());
			case 5 -> mostrarEmprestimos(es.buscarPorNomePessoa(Entrada.lerTexto("Nome ou trecho: ")));
			case 6 -> mostrarEmprestimos(es.listarOrdenadoPorData());
			case 0 -> {
			}
			default -> System.out.println("Inválido.");
			}
		} while (op != 0);
	}

	private static void mostrarEmprestimos(List<Emprestimo> lista) {
		TabelaUtil.imprimirCabecalho("ID | Pessoa | Livro | Emprestado em | Devolução prevista | Situação");
		TabelaUtil.imprimirLista(lista);
	}
}
