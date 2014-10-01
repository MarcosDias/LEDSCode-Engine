package br.edu.ifes.leds.ledscode.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import br.edu.ifes.leds.ledscode.frameworks.forge.DbType;
import br.edu.ifes.leds.ledscode.frameworks.forge.Forge;
import br.edu.ifes.leds.ledscode.frameworks.springROO.SpringROO;
import br.edu.ifes.leds.ledscode.loader.Loader;
import br.edu.ifes.leds.ledscode.metaDominio.Dominio;
import br.edu.ifes.leds.ledscode.metaDominio.grafo.Grafo;
import br.edu.ifes.leds.ledscode.metaDominio.grafo.Node;

public class App {
	static Scanner leitor = new Scanner(System.in);

	public static void main(String[] args) {

		List<Dominio> classes = null;
		List<Node> nodeDom = null;
		Map<String, String> dic = null;

		int opcao = 0;
		boolean exec = true;

		try {
			classes = getListDominio("model/Biblioteca/biblio.uml");
			nodeDom = Grafo.dominioToNode(classes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("============== LEDSCode ================\n");

		do {
			try {
				dic = menu();
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Arranjo tecnico
			if (dic.get("framework").equalsIgnoreCase("Spring Roo"))
				opcao = 1;
			else
				opcao = 2;

			switch (opcao) {
			case 1:
				/*
				 * Passos para criar um projeto com o framework Spring Roo
				 */
				SpringROO roo = new SpringROO();
				roo.criarProjeto("br.edu.leds.ledscode.Boliche");
				roo.configBanco("HIBERNATE");
				// TODO Enuns nao foram implementados
				roo.configEntidades(nodeDom);
				roo.criarTestIntegracao(nodeDom);
				roo.metodosFinder(nodeDom);
				roo.webSetup("mvc");
				roo.configWebService();
				roo.configLog();
				roo.quit();

				roo.print(SpringROO.ARQUIVO, SpringROO.EXTENSAO);
				roo.print(SpringROO.TERMINAL, null);

				exec = false;

				break;

			case 2:
				// http://forge.jboss.org/document/write-a-java-ee-web-application-advanced
				// http://forge.jboss.org/document/write-a-java-ee-web-application-basic
				// http://forge.jboss.org/document/write-a-java-ee-rest-application-basic
				/*
				 * Passos para criar um projeto com o framework Forge2
				 */
				Forge forge = new Forge();
				forge.criarProjeto("boliche", "br.edu.leds.ledscode");
				forge.configBanco("HIBERNATE", DbType.HSQLDB_IN_MEMORY);
				// TODO Enuns nao foram implementados
				forge.configEntidades(nodeDom);
				forge.gerarInterfaceWeb();
				forge.configRest();
				forge.build();
				forge.exit();

				forge.print(Forge.ARQUIVO, Forge.EXTENSAO);
				forge.print(Forge.TERMINAL, null);

				exec = false;
				break;

			case 0:
				exec = false;
				break;

			default:
				exec = true;
				break;
			}
		} while (exec);

		System.out.println("Aplicação finalizada!");
	}

	/**
	 * Baseado em um modelo retorna seus componentes.
	 * 
	 * @return
	 * @throws IOException
	 */
	public static List<Dominio> getListDominio(String modelo)
			throws IOException {
		Loader loader = new Loader();

		Model model = loader.load(modelo);

		return getListDominioToModel(model);
	}

	/**
	 * Monta a lista com os componetes do UML
	 * 
	 * @param model
	 *            - Modelo UML
	 * @param list
	 *            - Lista que sera preenchida
	 * @return
	 */
	public static List<Dominio> getListDominioToModel(Model model) {
		EList<Type> listaComponentes = model.getOwnedTypes();
		EList<Profile> profile1 = model.getAllAppliedProfiles();
		EList<ProfileApplication> profile2 = model.getAllProfileApplications();
		List<Dominio> listDominios = new ArrayList<Dominio>();

		for (Type type : listaComponentes) {
			if (type instanceof org.eclipse.uml2.uml.Class) {
				Dominio dom = new Dominio();

				org.eclipse.uml2.uml.Class classx = (org.eclipse.uml2.uml.Class) type;
				EList<Property> atributos = classx.getAllAttributes();
				EList<Stereotype> esteriotipos = classx.getApplicableStereotypes();

				dom.setClassDom(classx);
				dom.setAtributos(atributos);
				dom.setEsteriotipos(esteriotipos);

				listDominios.add(dom);
			}
		}
		return listDominios;
	}

	/**
	 * Metodo que realiza o menu da aplicação O metodo percorre o arquivo xml e
	 * monta dinamicamente o dicionario com as propriedades desejadas para o
	 * projeto que serah criado
	 * 
	 * @return Retorn o dicionario montado
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String, String> menu() throws JDOMException, IOException {
		Map<String, String> dic = new HashMap<String, String>();
		String opcao;
		SAXBuilder builder = new SAXBuilder();
		Document jdomDoc = builder.build(new File("config/config.xml"));
		Element projeto = jdomDoc.getRootElement();
		List<Element> listFilhos = projeto.getChildren();

		percorreXML(dic, listFilhos);
		return dic;
	}

	/**
	 * Dado uma lista de Elementos de um XML, percorre adicionando no
	 * dicionario(map). O metodo só aceita os elementos que não tem o atributo
	 * "filho", pois, isso indica que ele (o atributo) eh uma propriedade de um
	 * projeto
	 * 
	 * @param dic
	 * @param filhosInferiores
	 */
	private static void percorreXML(Map<String, String> dic,
			List<Element> filhosInferiores) {
		String opcao;
		Element superInferior;
		for (Element filho : filhosInferiores) {
			if (filho.getAttribute("filho") != null) {
				do {
					System.out.println("\nSelecione o(a) "
							+ filho.getAttributeValue("filho")
							+ " dentre o(a)s possíveis: ");
					for (Element elem : filho.getChildren()) {
						System.out.println(" - "
								+ elem.getAttributeValue("nome"));
					}
					System.out.println("Digite sua opção: ");
					opcao = leitor.nextLine();
					if (opcao.equalsIgnoreCase("sair"))
						; // TODO Fazer momento de parada
					superInferior = pesquisaFilhoByNome(filho.getChildren(),
							opcao);
				} while (superInferior == null);

				dic.put(superInferior.getName(),
						superInferior.getAttributeValue("nome"));

				percorreXML(dic, superInferior.getChildren());
			}
		}
	}

	/**
	 * Dado uma lista de elementos, pesquisa se alguem da lista tem o nome desejado
	 * @param children - Lista dos elementos que será percorrida
	 * @param nome - Nome deseja para o elemento
	 * @return
	 */
	private static Element pesquisaFilhoByNome(List<Element> children,
			String nome) {
		for (Element elem : children) {
			System.out.println(elem.getAttribute("nome").getValue());
			if (elem.getAttribute("nome").getValue().equalsIgnoreCase(nome))
				return elem;
		}
		return null;
	}

}
