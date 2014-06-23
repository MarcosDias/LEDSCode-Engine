package br.edu.ifes.leds.ledscode.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import br.edu.ifes.leds.ledscode.loader.Loader;
import br.edu.ifes.leds.ledscode.metadominio.ClassDom;
import br.edu.ifes.leds.ledscode.springROO.SpringROO;

public class App {

	public static void main(String[] args) {
		
		Loader loader = new Loader();
		List<ClassDom> classes = new ArrayList<ClassDom>();
		SpringROO roo = new SpringROO();
		
		try {
			Model model = loader.load("model/Boliche.uml");
			getClassModel(model, classes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		roo.criarProjeto("br.edu.leds.ledscode.Boliche");
		roo.configBanco("HIBERNATE");
		roo.configEntidades(classes);
		roo.criarTestIntegracao(classes);
		roo.webMvcSetup("mvc");
		roo.testSelenium(classes, "mvc");
		roo.configLog();
		roo.quit();
		
		roo.print('A');
		roo.print('T');
		
		System.out.println("Aplicação finalizada!");

	}
	
	public static void getClassModel(Model model, List<ClassDom> list){
		EList<Type> listaComponentes = model.getOwnedTypes();
		
		for (Type type:listaComponentes){
			if (type instanceof org.eclipse.uml2.uml.Class){
				ClassDom dom = new ClassDom();
				
				Class classx = (org.eclipse.uml2.uml.Class)type;
				EList<Property> atributos = classx.getAllAttributes();
				
				dom.setClassDom(classx);
				dom.setAtributos(atributos);
				
				list.add(dom);
			}
		}
	}

}
