package br.edu.ifes.leds.ledscode.mdl.loader;

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

/**
 * Le um modelo *.uml
 */
public class Loader {
    /**
     * Converte o arquivo *.uml em um modelo
     * <p/>
     * CODIGO ADAPTADO DO LINK:
     * http://www.eclipse.org/forums/index.php/t/353225/
     *
     * @param pathXML - Caminho do arquivo
     * @param ext     - Extensão do Arquivo
     * @return
     * @throws IOException
     */
    private EObject loader(String pathXML, String ext) throws IOException {

        UMLPackage.eINSTANCE.eClass();
        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put(ext, new XMIResourceFactoryImpl());
        ResourceSet resSet = new ResourceSetImpl();
        Resource resource = resSet.getResource(URI.createURI(pathXML), true);
        return resource.getContents().get(0);

        //http://www.eclipse.org/forums/index.php/t/354631/
        /*Resource resource = null;

		ResourceSet resourceSet = new ResourceSetImpl();

		resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI,
				UMLPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				"http:\\www.eclipse.org/uml2/2.0.0/UML", UMLPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				"http:\\www.eclipse.org/uml2/2.1.0/UML", UMLPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				"http:\\www.eclipse.org/uml2/1.0.0/UML", UMLPackage.eINSTANCE);

		resourceSet.getPackageRegistry().put(EcorePackage.eNS_URI,
				EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				"http:\\www.eclipse.org/emf/2003/XMLType",
				XMLTypePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				"http:\\www.w3.org/XML/1998/namespace",
				XMLNamespacePackage.eINSTANCE);

		resourceSet.getPackageRegistry().put(
				"http:\\www.eclipse.org/uml2/schemas/Ecore/5",
				EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(Ecore2XMLPackage.eNS_URI,
				Ecore2XMLPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(Ecore2EcorePackage.eNS_URI,
				Ecore2EcorePackage.eINSTANCE);

		resourceSet.getPackageRegistry().put(
				"http:\\schema.omg.org/spec/UML/2.2", UMLPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				"http:\\schema.omg.org/spec/UML/2.1", UMLPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				"http:\\schema.omg.org/spec/UML/2.1.1", UMLPackage.eINSTANCE);

		resourceSet.getPackageRegistry().put(
				"http:\\schema.omg.org/spec/MOF/2.0/cmof.xml",
				UMLPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				"http:\\schema.omg.org/spec/mof/2.0/emof.xml",
				UMLPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(
				"http:\\schema.omg.org/spec/MOF/2.0/emof.xml",
				UMLPackage.eINSTANCE);

		Map<String, Object> extensionToFactoryMap = resourceSet
				.getResourceFactoryRegistry().getExtensionToFactoryMap();

		extensionToFactoryMap.put(UMLResource.FILE_EXTENSION,
				UMLResource.Factory.INSTANCE);
		extensionToFactoryMap.put(UMLResource.PROFILE_FILE_EXTENSION,
				UMLResource.Factory.INSTANCE);

		extensionToFactoryMap.put(Ecore2XMLResource.FILE_EXTENSION,
				Ecore2XMLResource.Factory.INSTANCE);
		extensionToFactoryMap.put(UML22UMLResource.FILE_EXTENSION,
				UML22UMLResource.Factory.INSTANCE);
		// extensionToFactoryMap.put(UML212UMLResource.FILE_EXTENSION,UML212UMLResource.Factory.INSTANCE);

		extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
				UMLResource.Factory.INSTANCE);

		Map<URI, URI> uriMap = resourceSet.getURIConverter().getURIMap();

		URI uri = URI.createURI(pathXML);
		uriMap.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP), uri
				.appendSegment("libraries").appendSegment(""));
		uriMap.put(URI.createURI(UMLResource.METAMODELS_PATHMAP), uri
				.appendSegment("metamodels").appendSegment(""));
		uriMap.put(URI.createURI(UMLResource.PROFILES_PATHMAP), uri
				.appendSegment("profiles").appendSegment(""));

		resource = resourceSet.getResource(URI.createFileURI("model/configuracao/model.profile.uml"), true);
		resource = resourceSet.getResource(URI.createFileURI(pathXML), true);

		EcoreUtil.resolveAll(resourceSet);

		/*TreeIterator itr = EcoreUtil.getAllContents(resource, true);

		while (itr.hasNext()) {

			Object type_1 = itr.next();
			if (type_1 instanceof EObject)
				EcoreUtil.resolveAll((EObject) type_1);
			if (type_1 instanceof NamedElement) {
				NamedElement type = (NamedElement) type_1;
				System.out.println(type.getName() + " : "
						+ type.getAppliedStereotypes().size());
			}
			if (type_1 instanceof ProfileApplication) {
				ProfileApplication type = (ProfileApplication) type_1;
				System.out
						.println(type.getAppliedProfile().getName()
								+ ":"
								+ type.getAppliedProfile().eIsProxy()
								+ " : "
								+ type.getAppliedStereotypes().size()
								+ " : "
								+ type.getAppliedProfile()
										.getOwnedStereotypes().size());
			}

			if (type_1 instanceof AnyTypeImpl) {

				System.out.println("AnyType : "
						+ ((AnyTypeImpl) type_1).toString());
			}

			System.out.println(type_1.toString());
		}

		return resource.getContents().get(0); */
    }

    public Model load(String modelPath) throws IOException {

        return (Model) this.loader(modelPath, "uml");
    }
}
