# Projeto POO — Biblioteca

## Funcionalidade de prazo de devolução

O sistema calcula automaticamente uma **data prevista de devolução sete dias após a data do empréstimo**. O prazo é calculado na classe `Emprestimo`, usando `LocalDate` e `DateTimeFormatter` para interpretar e exibir as datas no formato `dd/MM/yyyy`.

- `Emprestimo.java`: armazena `dataEmprestimo` e `dataPrevistaDevolucao`; o construtor calcula a previsão; o método `paraLinhaTabela()` mostra ambas.
- `EmprestimoServico.java`: valida a data digitada, registra o empréstimo e informa ao usuário a data prevista no retorno da operação.
- `Programa.java`: a listagem de empréstimos inclui as colunas **Emprestado em**, **Devolução prevista** e **Situação**.
- Prazo: 7 dias corridos. Este projeto não implementa multa nem bloqueio automático após o vencimento; a data é informativa e visível para controle.

## Como demonstrar

1. No menu principal, acesse Empréstimos e escolha Registrar.
2. Informe o ID de uma pessoa, o ID de um livro disponível e a data no formato `dd/MM/yyyy`.
3. O sistema confirma o registro e mostra a data prevista (sete dias depois).
4. Escolha Listar para ver as duas datas na tabela.
5. Devolva o empréstimo para demonstrar a liberação do livro.

## Conceitos de Programação Orientada a Objetos

- **Abstração:** `Pessoa` define o modelo comum a alunos e funcionários e possui métodos abstratos.
- **Herança:** `Aluno` e `Funcionario` estendem `Pessoa`.
- **Polimorfismo:** cada subclasse implementa `getTipo()` e `getLimiteEmprestimos()`; a formatação de tabela também é sobrescrita.
- **Interfaces:** `Exportavel` define identificação/formatação; `Repositorio<T>` declara operações de armazenamento.
- **Composição/associação:** `Emprestimo` mantém referências à pessoa e ao livro envolvidos.
- **Encapsulamento:** atributos privados e acesso por getters/setters.
- **Generics:** repositórios usam `T extends Exportavel` para servir a diferentes tipos.
- **Coleções:** `RepositorioEmMemoria` usa `ArrayList` e retorna cópia da lista.
- **Injeção de dependência:** os serviços recebem repositórios e dependências; `Programa` monta a estrutura.
- **Ordenação:** listas usam `Comparator`; empréstimos são ordenados convertendo a data para `LocalDate`.
- **Validação:** disponibilidade, limites de empréstimo e impedimento de exclusão de pessoa/livro com empréstimo em aberto.

## Classes

- `Pessoa`, `Aluno`, `Funcionario`: dados e especialização das pessoas.
- `Livro`: dados bibliográficos e disponibilidade.
- `Emprestimo`: associação, datas, status e devolução.
- `Exportavel`, `Repositorio`: contratos por interface.
- `RepositorioEmMemoria`: implementação genérica de armazenamento em memória.
- `DadosIniciais`: população de registros demonstrativos.
- `Entrada`: leitura e validação de entrada do console.
- `TabelaUtil`: apresentação de listas.
- `PessoaServico`, `LivroServico`, `EmprestimoServico`: regras de negócio.
- `Programa`: montagem das dependências e menus.

## Limitações

Os dados são armazenados apenas na memória e são reiniciados quando o programa é encerrado. O prazo padrão é de sete dias corridos. Não há cálculo de multa, alerta automático de atraso nem persistência em arquivo/banco de dados.
