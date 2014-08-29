package br.edu.ifes.leds.ledscode.metaDominio.grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {
	private boolean visitado;
	private boolean enumTipo;
	private String nome;
	private List<Node> adjacentes;
	private HashMap<String, String> propriedades = new HashMap<String, String>();

	public Node() {
		this.visitado = false;
		this.enumTipo = false;
		this.nome = new String();
		this.adjacentes = new ArrayList<Node>();
		this.propriedades = new HashMap<String, String>();
	}
}
