package br.edu.ifes.leds.ledscode.springROO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import br.edu.ifes.leds.ledscode.arquivo.Arquivo;
import br.edu.ifes.leds.ledscode.metaDominio.grafo.Node;

@Getter
@Setter
public class SpringROO {
	private String scriptRoo;
	private String nomeProjeto;

	/**
	 * Etapa inicial para criação do projeto
	 * 
	 * @param string
	 *            - Nome do projeto que sera criado
	 */
	public void criarProjeto(String string) {
		this.scriptRoo += "// CRIANDO O NOVO PROJETO\n";
		this.scriptRoo += "project --topLevelPackage " + string + "\n\n";
		this.nomeProjeto = string;
	}

	/**
	 * Configuracoes do banco de dados
	 * 
	 * @param string
	 *            - Define qual ORM sera usado
	 */
	public void configBanco(String string) {
		this.scriptRoo += "// CONFIGURANDO O BANCO DE DADOS COM JPA\n";
		this.scriptRoo += "jpa setup --provider " + string
				+ " --database HYPERSONIC_IN_MEMORY\n\n";
	}

	/**
	 * Joga o script do Spring Roo para uma saida, seja terminal ou arquivo
	 * texto
	 * 
	 * @param saida
	 *            - Tipo de saida: - T ~> Terminal - A ~> Arquivo
	 */
	public void print(char saida) {
		switch (saida) {
		case 'T':
			System.out.println(this.scriptRoo);
			break;

		case 'A':
			try {
				Arquivo arquivo = new Arquivo(this.nomeProjeto, ".roo");
				arquivo.escreverArquivo(this.scriptRoo);
				arquivo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}

	}

	/**
	 * Cria as entidades que serao persistidas no banco de dados
	 * 
	 * @param listaEntidades
	 *            - Lista de entidades ja mapeadas para os no do grafo
	 */
	public void configEntidades(List<Node> listaEntidades) {
		this.scriptRoo += "// CRIANDO AS ENTIDADES DE DOMINIO\n";

		for (Node nodeDom : listaEntidades) {
			this.scriptRoo += "entity jpa --class ~.domain."
					+ nodeDom.getNome()
					+ " --activeRecord true --testAutomatically\n";
			HashMap<String, String> prop = nodeDom.getPropriedades();
			Set<String> chaves = prop.keySet();
			for (String chave : chaves) {
				String field = new String();
				field = "field " + chave + " --fieldName "
						+ prop.get(chave).toLowerCase() + " ";

				if (TiposBasicos.temTraducao(prop.get(chave).toLowerCase()) == null)
					field += "--type ~.domain." + prop.get(chave);

				field += " --notNull"; //

				if (chave.equals("int"))
					field += " --sizeMin 2 --sizeMax 100\n";
				else
					field += "\n";

				this.scriptRoo += field;

			}
		}
		this.scriptRoo += "\n";
	}

	/**
	 * Cria os testes de integração do sistema
	 * 
	 * @param nodeDom
	 *            - Lista de entidades ja mapeadas para os no do grafo
	 */
	public void criarTestIntegracao(List<Node> nodeDom) {
		this.scriptRoo += "// CRIANDO OS TESTES DE INTEGRACAO\n";

		for (Node dom : nodeDom) {
			this.scriptRoo += "test integration --entity ~.domain."
					+ dom.getNome() + "\n";
		}

		this.scriptRoo += "\n";
	}

	/**
	 * Comando que gera toda a construcao da parte web
	 * 
	 * @param tipoWeb
	 */
	public void webSetup(String tipoWeb) {
		this.scriptRoo += "// CRIANDO A PARTE WEB\n";
		this.scriptRoo += "web " + tipoWeb + " setup\n";
		this.scriptRoo += "web " + tipoWeb + " all --package ~.web\n";

		if (tipoWeb.equals("mvc")) {
			this.scriptRoo += "web mvc finder all\n\n";
			this.scriptRoo += "// INTERNACIONALIZACAO\n";
			this.scriptRoo += "web " + tipoWeb + " language --code de\n";
			this.scriptRoo += "web " + tipoWeb + " language --code es\n\n";
		}

		this.scriptRoo += "\n";
	}

	/**
	 * Monta os metodos de busca (FINDER)
	 * 
	 * @param nodeDom
	 *            - Lista de entidades ja mapeadas para os no do grafo
	 */
	public void metodosFinder(List<Node> nodeDom) {
		// finder list --depth 2 --class ~.domain.Liga
		this.scriptRoo += "// Metodos de busca (FINDER)\n";
		for (Node node : nodeDom) {
			this.scriptRoo += "finder list --depth 3 --class ~.domain."
					+ node.getNome();
			this.scriptRoo += "\n";
		}
		this.scriptRoo += "\n\n";
	}

	/**
	 * Configuracoes para criar um web service
	 */
	public void configWebService() {
		this.scriptRoo += "// CRIANDO WEB SERVICE\n";
		this.scriptRoo += "json all\n";
		this.scriptRoo += "web mvc json all\n\n";
	}

	/**
	 * Configuracoes do log do sistema
	 */
	public void configLog() {
		this.scriptRoo += "// CONFIGURACAO DO LOG\n";
		this.scriptRoo += "logging setup --level INFO\n\n";
	}

	/**
	 * Comando de saida da tela do Spring roo
	 */
	public void quit() {
		this.scriptRoo += "quit";
	}

	public SpringROO() {
		this.scriptRoo = "";
	}
}
