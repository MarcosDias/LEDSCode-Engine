package br.edu.ifes.leds.ledscode.ctrl;

import model.ClassDiagram;
import model.Model;
import model.Specification;

/**
 * Created by marcosdias on 30/11/14.
 */
public class EngineController {
    /**
     * Gera o codigo baseado nas informações e modelos contidos na especificacao
     *
     * @param specification - Especificacao que contem informacoes e
     *                      modelos necessarios para criar o projeto
     * @return
     */
    public boolean criarProjeto(Specification specification) {
        ClassDiagram diagram = (ClassDiagram) specification.getHas().get(0);
        this.metamodeltoNode((ClassDiagram) specification.getHas().get(0));
        return true;
    }

    private void metamodeltoNode(ClassDiagram model) {
        //model.get
    }
}
