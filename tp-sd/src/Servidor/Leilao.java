package Servidor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Leilao implements Serializable{
  private int id, maiorofer;
  private String descricao, maioruti;
  private HashMap<String,int> lista;

  public Leilao(int id,String descricao){
    this.id = id;
    this.descricao = descricao;
    this.maiorofer = 0;
    this.maioruti = "";
    this.lista = new HashMap<>();
  }

  public Leilao(Leilao l){
    this.id = l.getId();
    this.descricao = l.getDescricao();
    this.maiorofer = l.getMaiorOferta();
    this.maioruti = l.getMaiorUti();
    this.lista = new HashMap<>(l.getLista());
  }

  public int getId(){
    return this.id;
  }

  public String getDescricao(){
    return this.descricao;
  }

  public Map<String,int> getLista(){
    Map<String,int> aux = new HashMap<>(this.lista);
    return aux;
  }

  public void insereLicitacao(int oferta, String nome){
    this.lista.put(nome,oferta);
    if(oferta>this.maiorofer) {
      this.maiorofer = oferta;
      this.maioruti = nome;
    }
  }

  public int getMaiorOferta(){
    return this.maiorofer;
  }

  public String getMaiorUti(){
    return this.maioruti;
  }

  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("Id: ");
    sb.append(this.id.toString());
    sb.append("\nDescricao: ");
    sb.append(this.descricao);
    sb.append("\n");
    sb.append("\nOferta: ");
    sb.append(this.maiorofer.toString());
    sb.append("\n");

    return sb.toString();
  }

  public Leilao clone(){
    return new Leilao(this);
  }

  public boolean equals(Object l){
    if(this == l) return true;
    if(l == null || this.getClass() != l.getClass()) return false;
    else{
      Leilao aux = (Leilao) l;
      return (this.getId().equals(l.getId()) && this.getDescricao().equals(l.getDescricao()) && this.getMaiorOferta().equals(l.getMaiorOferta()) && this.getMaiorUti().equals(l.getMaiorUti()));
    }
  }
}
