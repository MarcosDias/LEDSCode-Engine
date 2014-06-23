package br.edu.ifes.leds.ledscode.metadominio;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Class;

public class ClassDom {
	private Class classDom; 
	private EList<Property> atributos;
	
	
	public Class getClassDom() {
		return classDom;
	}
	public void setClassDom(Class classDom) {
		this.classDom = classDom;
	}
	public EList<Property> getAtributos() {
		return atributos;
	}
	public void setAtributos(EList<Property> atributos) {
		this.atributos = atributos;
	}
}
