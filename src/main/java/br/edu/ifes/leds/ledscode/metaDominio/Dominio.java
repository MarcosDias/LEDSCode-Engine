package br.edu.ifes.leds.ledscode.metaDominio;

import lombok.Getter;
import lombok.Setter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Property;

@Getter
@Setter
public class Dominio {
	private org.eclipse.uml2.uml.Class classDom;
	private EList<Property> atributos;
}
