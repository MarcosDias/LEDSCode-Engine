package br.edu.ifes.leds.ledscode.frameworks.springROO;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import br.edu.ifes.leds.ledscode.frameworks.BaseFramework;
import br.edu.ifes.leds.ledscode.metaDominio.grafo.Node;

@Getter
@Setter
public class SpringROO extends BaseFramework {
	public final static String EXTENSAO = "roo";

	/**
	 * Etapa inicial para criação do projeto
	 * 
	 * @param string
	 *            - Nome do projeto que sera criado
	 */
	public void criarProjeto(String string) {
		this.script += "// CRIANDO O NOVO PROJETO\n";
		this.script += "project --topLevelPackage " + string + "\n\n";
		this.nomeProjeto = string;
	}

	/**
	 * Configuracoes do banco de dados
	 * 
	 * @param string
	 *            - Define qual ORM sera usado
	 */
	public void configBanco(String string) {
		this.script += "// CONFIGURANDO O BANCO DE DADOS COM JPA\n";
		this.script += "jpa setup --provider " + string
				+ " --database HYPERSONIC_IN_MEMORY\n\n";
	}

	/**
	 * Cria as entidades que serao persistidas no banco de dados
	 * 
	 * @param listaEntidades
	 *            - Lista de entidades ja mapeadas para os no do grafo
	 */
	public void configEntidades(List<Node> listaEntidades) {
		this.script += "// CRIANDO AS ENTIDADES DE DOMINIO\n";

		for (Node nodeDom : listaEntidades) {
			this.script += "entity jpa --class ~.domain."
					+ separarNomesStereotypes(nodeDom.getNome())
					+ " --activeRecord true --testAutomatically\n";
			
			HashMap<String, String> dicNomeTipoPropiedade = nodeDom
					.getPropriedades();
			Set<String> chaves = dicNomeTipoPropiedade.keySet();
			for (String chave : chaves) {
				String field = new String();
				field = "field ";
				if (TiposBasicosRoo.temTraducao(dicNomeTipoPropiedade
						.get(chave).toLowerCase()) == null) {
					field += "set ";
				} else {
					field += TiposBasicosRoo.temTraducao(dicNomeTipoPropiedade
							.get(chave).toLowerCase());
				}

				field += " --fieldName " + separarNomesStereotypes(chave) + " ";

				if (TiposBasicosRoo.temTraducao(dicNomeTipoPropiedade
						.get(chave).toLowerCase()) == null) {
					field += "--type ~.domain."
							+ separarNomesStereotypes(dicNomeTipoPropiedade
									.get(chave));
				} else {
					if (dicNomeTipoPropiedade.get(chave).equals("number")) {
						field += " --type int";
					}
				}

				field += this.notNullField(chave);
				field += this.maxMinField(dicNomeTipoPropiedade, chave);
				field += "\n";

				this.script += field;
			}
		}
		this.script += "\n";
	}

	/**
	 * Cria os testes de integração do sistema
	 * 
	 * @param nodeDom
	 *            - Lista de entidades ja mapeadas para os no do grafo
	 */
	public void criarTestIntegracao(List<Node> nodeDom) {
		this.script += "// CRIANDO OS TESTES DE INTEGRACAO\n";

		for (Node dom : nodeDom) {
			this.script += "test integration --entity ~.domain."
					+ separarNomesStereotypes(dom.getNome()) + "\n";
		}

		this.script += "\n";
	}

	/**
	 * Comando que gera toda a da parte web
	 * 
	 * @param tipoWeb
	 */
	public void webSetup(String tipoWeb) {
		this.script += "// CRIANDO A PARTE WEB\n";
		this.script += "web " + tipoWeb + " setup\n";
		this.script += "web " + tipoWeb + " all --package ~.web\n";

		if (tipoWeb.equals("mvc")) {
			this.script += "web mvc finder all\n\n";
			this.script += "// INTERNACIONALIZACAO\n";
			this.script += "web " + tipoWeb + " language --code de\n";
			this.script += "web " + tipoWeb + " language --code es\n\n";
		}
		this.script += "web mvc finder all\n";
		this.script += "\n";
	}

	/**
	 * Monta os metodos de busca (FINDER)
	 * 
	 * @param nodeDom
	 *            - Lista de entidades ja mapeadas para os no do grafo
	 */
	public void metodosFinder(List<Node> nodeDom) {
		// finder list --depth 2 --class ~.domain.Liga
		this.script += "// Metodos de busca (FINDER)\n";
		for (Node node : nodeDom) {
			this.script += "finder list --depth "
					+ node.getPropriedades().size() + " --class ~.domain."
					+ separarNomesStereotypes(node.getNome());
			this.script += "\n";
		}
		this.script += "\n\n";
	}

	/**
	 * Configuracoes para criar um web service
	 */
	public void configWebService() {
		this.script += "// CRIANDO WEB SERVICE\n";
		this.script += "json all\n";
		this.script += "web mvc json all\n\n";
	}

	/**
	 * Configuracoes do log do sistema
	 */
	public void configLog() {
		this.script += "// CONFIGURACAO DO LOG\n";
		this.script += "logging setup --level INFO\n\n";
	}

	/**
	 * Comando para sair do terminal do Spring Roo
	 */
	public void quit() {
		this.script += "quit";
	}

	/**
	 * Metodo trata stereotypes @Max e @Min aplicados em atributos
	 * 
	 * @param dicNomeTipoPropiedade
	 *            - Propriedades da classe (nó) que está sendo percorrido
	 * @param chave
	 *            - nome da propriedade, que contem também os stereotypos
	 *            aplicados
	 * @return
	 */
	private String maxMinField(HashMap<String, String> dicNomeTipoPropiedade,
			String chave) {
		if (chave.contains(" ")
				&& dicNomeTipoPropiedade.get(chave).equals("int")) {

			String[] componentesNome = chave.split(" ");

			for (String contemMin : componentesNome) {
				if (dicNomeTipoPropiedade.get(chave).equals("int")) {
					if (contemMin.contains("@Min")) {
						if (dicNomeTipoPropiedade.get(chave).equals("int")) {
							return " --min "
									+ getNumeroStereotype("@Min", contemMin);
						} else if (dicNomeTipoPropiedade.get(chave).equals(
								"string")) {
							return " --sizeMin"
									+ getNumeroStereotype("@Min", contemMin);
						}
					}
					if (contemMin.contains("@Max")) {
						if (dicNomeTipoPropiedade.get(chave).equals("int")) {
							return " --min "
									+ getNumeroStereotype("@Max", contemMin);
						} else if (dicNomeTipoPropiedade.get(chave).equals(
								"string")) {
							return " --sizeMax"
									+ getNumeroStereotype("@Max", contemMin);
						}
					}
				}
			}
		}
		return "";

	}

	/**
	 * Metodo auxiliar que devolve o valor contido no Stereotype passado, ex.:
	 * 
	 * @Min(25)
	 * 
	 * @param stereotype
	 *            - Typo do Stereotype que sera obito o seu valor
	 * @param palavra
	 *            - Stereotype que sera obito o seu valor
	 * @return
	 */
	private String getNumeroStereotype(String stereotype, String palavra) {
		String[] subString = palavra.split(stereotype + "\\(");
		return subString[1].split("\\)")[0];
	}

	/**
	 * Metodo trata Stereotype @NotNull aplicados em atributos
	 * 
	 * @param chave
	 * @return
	 */
	private String notNullField(String chave) {
		if (chave.contains("@NotNull")) {
			return " --notNull";
		}
		return "";
	}

	public SpringROO() {
		super();
	}

	public String separarNomesStereotypes(String nome) {
		if (nome.contains(" ")) {
			String[] componentesNome = nome.split(" ");
			return componentesNome[componentesNome.length - 1];
		}

		return nome;
	}
}
