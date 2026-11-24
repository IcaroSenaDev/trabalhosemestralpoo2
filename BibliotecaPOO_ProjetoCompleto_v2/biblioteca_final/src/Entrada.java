import java.util.Scanner;

public class Entrada {
	private static final Scanner SCANNER = new Scanner(System.in);

	public static int lerInteiro(String msg) {
		while (true) {
			System.out.print(msg);
			String s = SCANNER.nextLine();
			try {
				return Integer.parseInt(s.trim());
			} catch (NumberFormatException e) {
				System.out.println("Digite um número inteiro válido.");
			}
		}
	}

	public static String lerTexto(String msg) {
		System.out.print(msg);
		return SCANNER.nextLine().trim();
	}
}
