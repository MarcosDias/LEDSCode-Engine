package br.edu.ifes.leds.ledscode.springROO;

import lombok.Getter;

/**
 * Tipo EMBEDDED, LIST e OTHER ainda nao implementados.
 * 
 * @author marcosdias
 */
@Getter
public enum TiposBasicos {
	BOOLEAN("boolean"), 
	DATE("date"),
	// EMBEDDED,
	ENUM("enum"),
	// FILE("file"),
	// LIST,
	NUMBER_INT("int"), NUMBER_DOUBLE("double"), NUMBER_FLOAT("float"),
	// OTHER,
	STRING("string");
	// REFERENCE(""),
	// SET("");

	private final String nome;

	private TiposBasicos(String nome) {
		this.nome = nome;
	}

	/**
	 * Busca se existe um tipo, dentro das possibilidades mapeadas no enum
	 * 
	 * @param tipo
	 * @return
	 */
	public static String temTraducao(String tipo) {
		for (TiposBasicos basico : TiposBasicos.values()) {
			if (basico.getNome().equals(tipo)
					&& !basico.getNome().equals("enum")) {
				return basico.getNome();
			}
		}
		return null;
	}
}
