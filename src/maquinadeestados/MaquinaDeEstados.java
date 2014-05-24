/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinadeestados;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author leandropessoa
 */
public class MaquinaDeEstados {
    
    private String estadoInicial;
    private String estadoFinal;
    private int tamPrefixo;
    private ArrayList<Estado> Estados;
    
    public MaquinaDeEstados(String arquivo)
    {
        Estados = new ArrayList<Estado>();
        try {
            //Abre arquivo xml e o normaliza para leitura.
            File fXmlFile = new File(arquivo);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            //System.out.println("Raiz :" + doc.getDocumentElement().getNodeName());

            //Cria nó para identificar campos do xml que especificam estado inicial e final
            NodeList noMaquina = doc.getElementsByTagName("maquinadeestados");
            Node noMaquinaNode = noMaquina.item(0);
            Element elementoMaquina = (Element) noMaquinaNode;
            estadoInicial = (String) elementoMaquina.getAttribute("estadoinicial");
            estadoFinal = (String) elementoMaquina.getAttribute("estadofinal");
            tamPrefixo = Integer.parseInt(elementoMaquina.getAttribute("tamprefixo"));
            System.out.println("Estado Inicial : " + elementoMaquina.getAttribute("estadoinicial"));
            System.out.println("Estado Final : " + elementoMaquina.getAttribute("estadofinal"));

            //cria nnos para extrair informaçoes de cada estado da maquina de estados
            NodeList estado = doc.getElementsByTagName("estado");
            //Estados.add(novo);
            //loop para a criação de cada estado pertencente a maquina de estados
            for (int temp = 0; temp < estado.getLength(); temp++) {
                Estado novo = new Estado();	

                Node nNode = estado.item(temp);
                //System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    novo.setNome(eElement.getAttribute("nome"));
                    //System.out.println(eElement.getElementsByTagName("pdoc.getDocumentElement().normalize();refixo").getLength());
                    for(int a = 0; a < eElement.getElementsByTagName("prefixo").getLength(); a++)
                    {
                        novo.addPrefixo(eElement.getElementsByTagName("prefixo").item(a).getTextContent());
                        // System.out.println("Peso : " + eElement.getElementsByTagName("peso").item(a).getTextContent());
                        novo.setProxEstado(eElement.getElementsByTagName("proximoestado").item(a).getTextContent());
                        // System.out.println("Proximo estado : " + eElement.getElementsByTagName("proximoestado").item(a).getTextContent());
                        novo.setEmpilha(eElement.getElementsByTagName("empilha").item(a).getTextContent());
                        //System.out.println("empilha: " + eElement.getElementsByTagName("empilha").item(a).getTextContent());
                        novo.setDesempilha(eElement.getElementsByTagName("desempilha").item(a).getTextContent());
                        //System.out.println("desempilha: " + eElement.getElementsByTagName("desempilha").item(a).getTextContent());

                    }
                }
                Estados.add(novo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Estado getEstado(String nomeEstado)
    {
        Estado estado = new Estado();
        for(int a = 0; a < Estados.size(); a++)
        {
            Estado estadoCorrente = Estados.get(a);
            if(estadoCorrente.getNome().equals(nomeEstado))
            {
                estado = Estados.get(a);
            }
        }
      return estado;      
    }
        
    public boolean computarCadeia(String cadeia)
    {    
    	Stack<String> Pilha = new Stack(); // cria a pilha
    	 
    	Estado estadoAtual = this.getEstado(this.estadoInicial);
        //estadoAtual.getProxEstado
        //System.out.println(estadoAtual.getProxEstado(0));
         
    	char prefixo [] = new char[this.tamPrefixo];
    	String nomeProxEstado;
    	 
    	int posPrefixo;
    	 
    	for (int i = 0; i < cadeia.length(); i = i + this.tamPrefixo) {             
            cadeia.getChars(i, i + this.tamPrefixo, prefixo, 0);
            if(!Pilha.empty()) 
                posPrefixo = estadoAtual.posPrefixo(String.copyValueOf(prefixo), Pilha.peek());
            else
    		posPrefixo = estadoAtual.posPrefixo(String.copyValueOf(prefixo), "");
            
            if(posPrefixo == -1)
            {
            	return false;
            }
            
            nomeProxEstado = estadoAtual.getProxEstado(posPrefixo);
    	
            if (nomeProxEstado.equals(""))  // verifica se tem um próximo Estado
            {
                System.out.println(nomeProxEstado); 
                return false;
            }
            if(!estadoAtual.getDesempilha(posPrefixo).equals(""))  // verifica se quer desempilhar algo
            {
                Pilha.pop(); // desempilha       
            }
            if(!estadoAtual.getEmpilha(posPrefixo).equals(""))   // verifica se eu qro empilhar algo
            {
                Pilha.push(estadoAtual.getEmpilha(posPrefixo));    // empilha
            }
            estadoAtual = this.getEstado(nomeProxEstado);
        }
        // verifica se o estado atual eh igual ao estado final e se a pilha esta vazia
        return (estadoAtual.getNome().equals(this.estadoFinal) && Pilha.empty());
    }
}
    
    

