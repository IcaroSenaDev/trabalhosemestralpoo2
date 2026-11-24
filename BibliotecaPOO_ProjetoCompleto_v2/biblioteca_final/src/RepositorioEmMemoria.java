import java.util.ArrayList;
import java.util.List;

class RepositorioEmMemoria<T extends Exportavel> implements Repositorio<T> {
	private final ArrayList<T> itens = new ArrayList<>();
	private int proximoId = 1;

	public void adicionar(T item) {
		if (item == null)
			throw new IllegalArgumentException("Item não pode ser nulo.");
		item.setId(proximoId++);
		itens.add(item);
	}

	public boolean remover(int id) {
		T item = buscarPorId(id);
		return item != null && itens.remove(item);
	}

	public T buscarPorId(int id) {
		for (T item : itens)
			if (item.getId() == id)
				return item;
		return null;
	}

	public List<T> listarTodos() {
		return new ArrayList<>(itens);
	}
}
