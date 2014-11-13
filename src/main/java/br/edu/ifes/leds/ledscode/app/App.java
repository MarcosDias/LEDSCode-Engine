package br.edu.ifes.leds.ledscode.app;

public class App {
//    static Scanner leitor = new Scanner(System.in);

    public static void main(String[] args) {

//        List<Dominio> classes = null;
//        List<Node> nodeDom = null;
//        Map<String, String> dic = null;
//
//        int opcao = 0;
//        boolean exec = true;
//
//        try {
//            classes = getListDominio("model/Boliche/old.uml");
//            nodeDom = Grafo.dominioToNode(classes);
//
//            System.out.println("============== LEDSCode ================\n");
//
//            do {
//                //dic = menu();
//
//                //if (dic.get("framework").equalsIgnoreCase("Spring Roo"))
//                //opcao = 1;
//                //else
//                opcao = 1;
//
//                switch (opcao) {
//                    case 1:
//                    /*
//                     * Passos para criar um projeto com o framework Spring Roo
//					 */
//                        SpringROO roo = new SpringROO();
//                        roo.criarProjeto("br.edu.leds.ledscode.Boliche");
//                        roo.configBanco("HIBERNATE");
//                        // TODO Enuns nao foram implementados
//                        roo.configEntidades(nodeDom);
//                        roo.criarTestIntegracao(nodeDom);
//                        roo.metodosFinder(nodeDom);
//                        roo.webSetup("mvc");
//                        roo.configWebService();
//                        roo.configLog();
//                        roo.quit();
//
//                        roo.print(SpringROO.ARQUIVO, SpringROO.EXTENSAO);
//                        roo.print(SpringROO.TERMINAL, null);
//
//                        exec = false;
//
//                        break;
//
//                    case 2:
//                        // http://forge.jboss.org/document/write-a-java-ee-web-application-advanced
//                        // http://forge.jboss.org/document/write-a-java-ee-web-application-basic
//                        // http://forge.jboss.org/document/write-a-java-ee-rest-application-basic
//					/*
//					 * Passos para criar um projeto com o framework Forge2
//					 */
//                        Forge forge = new Forge();
//                        forge.criarProjeto("boliche", "br.edu.leds.ledscode");
//                        forge.configBanco("HIBERNATE", DbType.HSQLDB_IN_MEMORY);
//                        // TODO Enuns nao foram implementados
//                        forge.configEntidades(nodeDom);
//                        forge.gerarInterfaceWeb();
//                        forge.configRest();
//                        forge.build();
//                        forge.exit();
//
//                        forge.print(Forge.ARQUIVO, Forge.EXTENSAO);
//                        forge.print(Forge.TERMINAL, null);
//
//                        exec = false;
//                        break;
//
//                    case 0:
//                        exec = false;
//                        break;
//
//                    default:
//                        exec = true;
//                        break;
//                }
//            } while (exec);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ModeloValidoException e) {
//            System.out.println("Erro! Há algum erro no modelo.");
//            System.out.println("Tente revisar os nomes das classes e de suas propriedades");
//        }
//
//        System.out.println("Aplicação finalizada!");
//    }
//
//    /**
//     * Baseado em um modelo retorna seus componentes.
//     *
//     * @return
//     * @throws IOException
//     * @throws ModeloValidoException
//     */
//    public static List<Dominio> getListDominio(String modelo)
//            throws IOException, ModeloValidoException {
//        Loader loader = new Loader();
//
//        Model model = loader.load(modelo);
//
//        return getListDominioToModel(model);
//    }
//
//    /**
//     * Monta a lista com os componetes do UML
//     *
//     * @param model - Modelo UML
//     * @return
//     * @throws ModeloValidoException
//     */
//    public static List<Dominio> getListDominioToModel(Model model) throws ModeloValidoException {
//        EList<Type> listaComponentes = model.getOwnedTypes();
//        List<Dominio> listDominios = new ArrayList<Dominio>();
//
//        for (Type type : listaComponentes) {
//            if (type instanceof org.eclipse.uml2.uml.Class) {
//                Dominio dom = new Dominio();
//
//                org.eclipse.uml2.uml.Class classx = (org.eclipse.uml2.uml.Class) type;
//                EList<Property> atributos = classx.getAllAttributes();
//
//                dom.setClassDom(classx);
//                dom.setAtributos(atributos);
//
//                listDominios.add(dom);
//            }
//        }
//        //validaDominio(listDominios);
//        return listDominios;
//    }
//
//    private static void validaDominio(List<Dominio> listDominios) throws ModeloValidoException {
//        String padraoClass = "(@[A-Z][a-z]*\\(\\d+\\))?\\s*[A-Z][a-zA-Z]*";
//        String padraoPropriedade = "(<<[A-Z][a-z]*>>\\s)*[a-zA-Z]+";
//        ;
//
//        for (Dominio dom : listDominios) {
//            if (Pattern.matches(padraoClass, dom.getClassDom().getName())) {
//                throw new ModeloValidoException();
//            }
//            for (Property prop : dom.getAtributos()) {
//                if (Pattern.matches(padraoPropriedade, prop.getName())) {
//                    throw new ModeloValidoException();
//                }
//            }
//        }
//    }
//
//    /**
//     * Metodo que realiza o menu da aplicação O metodo percorre o arquivo xml e
//     * monta dinamicamente o dicionario com as propriedades desejadas para o
//     * projeto que serah criado
//     *
//     * @return Retorn o dicionario montado
//     * @throws JDOMException
//     * @throws IOException
//     */
//    public static Map<String, String> menu() throws JDOMException, IOException {
//        Map<String, String> dic = new HashMap<String, String>();
//        String opcao;
//        SAXBuilder builder = new SAXBuilder();
//        Document jdomDoc = builder.build(new File("config/config.xml"));
//        Element projeto = jdomDoc.getRootElement();
//        List<Element> listFilhos = projeto.getChildren();
//
//        percorreXML(dic, listFilhos);
//        return dic;
//    }
//
//    /**
//     * Dado uma lista de Elementos de um XML, percorre adicionando no
//     * dicionario(map). O metodo só aceita os elementos que não tem o atributo
//     * "filho", pois, isso indica que ele (o atributo) eh uma propriedade de um
//     * projeto
//     *
//     * @param dic
//     * @param filhosInferiores
//     */
//    private static void percorreXML(Map<String, String> dic,
//                                    List<Element> filhosInferiores) {
//        String opcao;
//        Element superInferior;
//        for (Element filho : filhosInferiores) {
//            if (filho.getAttribute("filho") != null) {
//                do {
//                    System.out.println("\nSelecione o(a) "
//                            + filho.getAttributeValue("filho")
//                            + " dentre o(a)s possíveis: ");
//                    for (Element elem : filho.getChildren()) {
//                        System.out.println(" - "
//                                + elem.getAttributeValue("nome"));
//                    }
//                    System.out.println("Digite sua opção: ");
//                    opcao = leitor.nextLine();
//                    if (opcao.equalsIgnoreCase("sair"))
//                        ; // TODO Fazer momento de parada
//                    superInferior = pesquisaFilhoByNome(filho.getChildren(),
//                            opcao);
//                } while (superInferior == null);
//
//                dic.put(superInferior.getName(),
//                        superInferior.getAttributeValue("nome"));
//
//                percorreXML(dic, superInferior.getChildren());
//            }
//        }
//    }
//
//    /**
//     * Dado uma lista de elementos, pesquisa se alguem da lista tem o nome desejado
//     *
//     * @param children - Lista dos elementos que será percorrida
//     * @param nome     - Nome deseja para o elemento
//     * @return
//     */
//    private static Element pesquisaFilhoByNome(List<Element> children,
//                                               String nome) {
//        for (Element elem : children) {
//            System.out.println(elem.getAttribute("nome").getValue());
//            if (elem.getAttribute("nome").getValue().equalsIgnoreCase(nome))
//                return elem;
//        }
//        return null;
    }

}
