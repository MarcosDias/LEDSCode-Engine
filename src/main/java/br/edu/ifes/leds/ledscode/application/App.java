package br.edu.ifes.leds.ledscode.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import br.edu.ifes.leds.ledscode.frameworks.forge.DbType;
import br.edu.ifes.leds.ledscode.frameworks.forge.Forge;
import br.edu.ifes.leds.ledscode.frameworks.springROO.SpringROO;
import br.edu.ifes.leds.ledscode.loader.Loader;
import br.edu.ifes.leds.ledscode.metaDominio.Dominio;
import br.edu.ifes.leds.ledscode.metaDominio.grafo.Grafo;
import br.edu.ifes.leds.ledscode.metaDominio.grafo.Node;

public class App {

	public static void main(String[] args) {

		List<Dominio> classes = null;
		List<Node> nodeDom = null;
		
		int opcao = 0;
		boolean exec = true;
		Scanner leitor = new Scanner (System.in);

		try {
			classes = getListDominio("model/Boliche/boliche.uml");
			nodeDom = Grafo.dominioToNode(classes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("============== LEDSCode ================\n");
		
		do{
			System.out.println("Digite a opção para framework.");
			System.out.println("0 - Sair da Aplicação");
			System.out.println("1 - Spring Roo");
			System.out.println("2 - Forge");
			System.out.println("\nOpção: ");
			opcao = leitor.nextInt();
			
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
					//http://forge.jboss.org/document/write-a-java-ee-web-application-advanced
					//http://forge.jboss.org/document/write-a-java-ee-web-application-basic
					//http://forge.jboss.org/document/write-a-java-ee-rest-application-basic
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
		}
		while(exec);
		
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
		List<Dominio> listDominios = new ArrayList<Dominio>();

		for (Type type : listaComponentes) {
			if (type instanceof org.eclipse.uml2.uml.Class) {
				Dominio dom = new Dominio();

				org.eclipse.uml2.uml.Class classx = (org.eclipse.uml2.uml.Class) type;
				EList<Property> atributos = classx.getAllAttributes();

				dom.setClassDom(classx);
				dom.setAtributos(atributos);

				listDominios.add(dom);
			}
		}
		return listDominios;
	}

}
