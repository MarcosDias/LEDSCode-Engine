package br.edu.ifes.leds.ledscode.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import br.edu.ifes.leds.ledscode.loader.Loader;
import br.edu.ifes.leds.ledscode.metaDominio.Dominio;
import br.edu.ifes.leds.ledscode.metaDominio.grafo.Grafo;
import br.edu.ifes.leds.ledscode.metaDominio.grafo.Node;
import br.edu.ifes.leds.ledscode.springROO.SpringROO;

public class App {

	public static void main(String[] args) {

		SpringROO roo = new SpringROO();
		List<Dominio> classes = null;
		List<Node> nodeDom = null;

		try {
			classes = getListDominio("model/Boliche/boliche.uml");
			nodeDom = Grafo.dominioToNode(classes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * Passos para criar um projeto com o framework Spring Roo
		 */
		roo.criarProjeto("br.edu.leds.ledscode.Boliche");
		roo.configBanco("HIBERNATE");
		roo.configEntidades(nodeDom);
		roo.criarTestIntegracao(nodeDom);
		roo.webSetup("mvc");
		roo.metodosFinder(nodeDom);
		roo.configWebService();
		roo.configLog();
		roo.quit();

		roo.print('A');
		roo.print('T');

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
