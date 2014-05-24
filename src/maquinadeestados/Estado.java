/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maquinadeestados;

import java.util.ArrayList;

/**
 *
 * @author leandropessoa
 */
public class Estado {
    
    private String nome;
    private ArrayList<String> preFixos;
    private ArrayList<String> proxEstados;
    private ArrayList<String> empilha;
    private ArrayList<String> desempilha;
    
   //Método construtor que inicializa arrays de prefixos e respectivos proximos estados.
    public Estado()
    {
        preFixos = new ArrayList<String>();
        proxEstados = new ArrayList<String>();
        empilha = new ArrayList<String>();
        desempilha = new ArrayList<String>();
    }
    //Seta nome do estado
    public void setNome(String NOME)
    {
        this.nome = NOME;        
    }
    //Retorna nome do estado
    public String getNome()
    {
        return this.nome;
    }
    //Adiciona prefixo ao array de prefixos do estado
    public void addPrefixo(String prefixo)
    {
        this.preFixos.add(prefixo);
    }
    //Determina posição de prefixo dentro da matriz de prefixos caso o prefixo exista
    //levando em concideração o que será retirado da pilha seja compatível com o topo, 
    //caso seja retirado.
    public int posPrefixo(String nomePrefixo, String TopoPilha)
    {
       int _return = -1;
        boolean alguemqrdesempilhar = false;
        
        for(int a = 0; a < preFixos.size(); a++)
        {
            if(preFixos.get(a).equals(nomePrefixo))
            {
            	if(desempilha.get(a).equals(TopoPilha))
            	{
                    alguemqrdesempilhar = true;
                    _return = a;
            	}   
            	else
            	{
            	    if(!desempilha.get(a).equals(""))
            	    {
            		if(!alguemqrdesempilhar)
            		_return = a;
            	    }   
                }       
            } 
        }
        return _return;
    }
    
  
    //Retorna proximo estado do respectivo indice
    public String getProxEstado(int estado)
    {
        return proxEstados.get(estado);
    }
    
    public void setProxEstado(String proxEstado)
    {
       this.proxEstados.add(proxEstado);
    }
    
    public void setEmpilha(String data)
    {
        this.empilha.add(data);
    }
    
    public String getEmpilha(int pos)
    {
        return this.empilha.get(pos);
    }   
    
    public void setDesempilha(String data)
    {
        this.desempilha.add(data);       
    }
    
    public String getDesempilha(int pos)
    {
      return this.desempilha.get(pos);
    }
    
}
