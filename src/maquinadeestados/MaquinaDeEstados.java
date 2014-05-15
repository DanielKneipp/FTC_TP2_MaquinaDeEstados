/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinadeestados;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

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
        NodeList proximoEstado = doc.getElementsByTagName("peso");
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
         Estado estadoAtual = this.getEstado(this.estadoInicial);
         //estadoAtual.getProxEstado
//         System.out.println(estadoAtual.getProxEstado(0));
         
    	 char prefixo [] = new char[this.tamPrefixo];
    	 String nomeProxEstado;
    	 
    	 int posPrefixo;
    	 
    	 for (int i = 0; i < cadeia.length(); i = i + this.tamPrefixo) {
    	
             
                 cadeia.getChars(i, i + this.tamPrefixo, prefixo, 0);
    		 
    		 posPrefixo = estadoAtual.posPrefixo(String.copyValueOf(prefixo));
    		
                 nomeProxEstado = estadoAtual.getProxEstado(posPrefixo);
    		 
    		 if (nomeProxEstado.equals(""))
                 {
    			System.out.println(nomeProxEstado) ; 
                     return false;
                 }
    		 estadoAtual = this.getEstado(nomeProxEstado);
    	 }
        
    	 if (estadoAtual.getNome().equals(this.estadoFinal))
    		 return true;
    	 else
    		 return false;
         
     }
    }
    
    

