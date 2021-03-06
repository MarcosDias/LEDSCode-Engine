package br.edu.ifes.leds.ledscode.ctrl;

import ctrl.ForgeController;
import ctrl.SpringRooController;
import ctrl.XmlController;
import model.*;

import javax.xml.bind.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Marcos Dias
 */
public class EngineController {
    XmlController xmlController = new XmlController();

    /**
     * Dado um projeto, gera o código
     * @param specification modelo tem os parametros e diagramas para geracao de codigo
     * @return returna true caso nao haja nenhum erro
     */
    // TODO - Projeto gerado estaticamente em Spring Roo
    public boolean generateCodeProject(Specification specification) throws IOException {

        SpringRooController springROO = new SpringRooController();
        springROO.createProject(specification);
        
//        ForgeController forge = new ForgeController();
//        forge.createProject(specification);

        return true;
    }

    /**
     * Metodo usado apos a escrita do model.model.arquivo xml
     * necessario para "acordar" a engine e continuar o processo
     * @param nomeArquivo Nome do model.model.arquivo que sera lido
     * @return returna true caso nao haja nenhum erro
     */
    public boolean acordarEngine(String nomeArquivo) throws JAXBException, IOException {
        Specification specification = (Specification) xmlController.lerArquivoXML(nomeArquivo);
        
        this.generateCodeProject(specification);
        
        return true;
    }

    /**
     * TODO - REMOVER ATRIBUTO E METODOS SEGUINTES
     * Estes atributos e metodos seguintes, seram obitido de forma dinamica nas proximas versoes,
     * portanto, por agora utilizaremos valores estaticos
     */
    private ArrayList<String> languages;
    private HashMap<String, ArrayList<String>> frameworks;
    private HashMap<String, ArrayList<String>> dataBase;
    private HashMap<String, ArrayList<String>> orms;
    private HashMap<String, ArrayList<String>> applicationTypes;
    
    public ArrayList<String> getLanguages(){
        return this.languages;
    }

    public ArrayList<String> getFrameworks(String language) {
        return this.frameworks.get(language);
    }

    public ArrayList<String> getDataBase(String framework) {
        return this.dataBase.get(framework);
    }

    public ArrayList<String> getOrms(String framework) {
        return this.orms.get(framework);
    }

    public ArrayList<String> getApplicationType(String framework) {
        return this.applicationTypes.get(framework);
    }

    public EngineController() {
        this.languages = new ArrayList<String>();
        this.languages.add("Java");

        ArrayList<String> listFramewoksJava = new ArrayList<String>();
        listFramewoksJava.add("Spring_Roo");
        listFramewoksJava.add("Forge");
        this.frameworks = new HashMap<String, ArrayList<String>>();
        this.frameworks.put("Java", listFramewoksJava);

        ArrayList<String> listDataBaseROO = new ArrayList<String>();
        ArrayList<String> listDataBaseForge = new ArrayList<String>();
        listDataBaseROO.add("HYPERSONIC_IN_MEMORY");
        listDataBaseForge.add("HSQLDB_IN_MEMORY");
        this.dataBase = new HashMap<String, ArrayList<String>>();
        this.dataBase.put("Spring_Roo", listDataBaseROO);
        this.dataBase.put("Forge", listDataBaseForge);

        ArrayList<String> listOrmsROO = new ArrayList<String>();
        ArrayList<String> listOrmsForge = new ArrayList<String>();
        listOrmsROO.add("HIBERNATE");
        listOrmsForge.add("HIBERNATE");
        this.orms = new HashMap<String, ArrayList<String>>();
        this.orms.put("Spring_Roo", listOrmsROO);
        this.orms.put("Forge", listOrmsForge);

        ArrayList<String> listApplicationTypes = new ArrayList<String>();
        listApplicationTypes.add("WEB");
        this.applicationTypes = new HashMap<String, ArrayList<String>>();
        this.applicationTypes.put("Spring_Roo", listApplicationTypes);
        this.applicationTypes.put("Forge", listApplicationTypes);
    }
}
