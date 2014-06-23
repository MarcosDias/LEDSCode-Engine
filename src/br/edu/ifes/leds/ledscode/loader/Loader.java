package br.edu.ifes.leds.ledscode.loader;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;


public class Loader {
	
	private EObject loader(String pathXML, String ext) throws
		IOException {

			UMLPackage.eINSTANCE.eClass();
			
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;

			Map<String, Object> m = reg.getExtensionToFactoryMap();

			m.put(ext, new XMIResourceFactoryImpl());

			ResourceSet resSet = new ResourceSetImpl();

			Resource resource = resSet.getResource(URI.createURI(pathXML), true);

			return resource.getContents().get(0);

	}

	public Model load(String modelPath) throws IOException {	

		return (Model) this.loader(modelPath,"uml");
	}

}
