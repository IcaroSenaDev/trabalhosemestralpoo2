import java.util.List;

public class TabelaUtil {
	public static void imprimirCabecalho(String... colunas) {
		System.out.println(String.join(" | ", colunas));
		System.out.println("-".repeat(Math.max(12, String.join(" | ", colunas).length())));
	}

	public static <T extends Exportavel> void imprimirLista(List<T> lista) {
		if (lista.isEmpty()) {
			System.out.println("Nenhum registro.");
			return;
		}
		for (T item : lista)
			System.out.println(item.paraLinhaTabela());
	}
}
