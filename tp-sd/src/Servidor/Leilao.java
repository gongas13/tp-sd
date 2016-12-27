package Servidor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Leilao implements Serializable{
  private int id, maiorofer;
  private String descricao, maioruti, dono;
  private HashSet<String> lista;

  public Leilao(int id, String dono, String descricao){
    this.id = id;
    this.dono = dono;
    this.descricao = descricao;
    this.maiorofer = 0;
    this.maioruti = "";
    this.lista = new HashSet();
  }

  public Leilao(Leilao l){
    this.id = l.getId();
    this.dono = l.getDono();
    this.descricao = l.getDescricao();
    this.maiorofer = l.getMaiorOferta();
    this.maioruti = l.getMaiorUti();
    this.lista = new HashSet(l.getLista());
  }

  public int getId(){
    return this.id;
  }

  public int getDono(){
    return this.dono;
  }

  public String getDescricao(){
    return this.descricao;
  }

  public Set<String> getLista(){
    Set<String> aux = new HashSet(this.lista);
    return aux;
  }

  public void insereLicitacao(int oferta, String nome){
    if(oferta>this.maiorofer) {
      this.lista.put(this.maiorouti,this.maiorofer);
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

  public String getDetalhes(){
    StringBuilder sb = new StringBuilder();
    sb.append("Id: ");
    sb.append(this.id.toString());
    sb.append("\nDescricao: ");
    sb.append(this.descricao);
    sb.append("\nOferta: ");
    sb.append(this.maiorofer.toString());
    sb.append("\n");

    return sb.toString();
  }

  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("Id: ");
    sb.append(this.id.toString());
    sb.append("\nDono");
    sb.append(this.dono);
    sb.append("\nDescricao: ");
    sb.append(this.descricao);
    sb.append("\nOferta: ");
    sb.append(this.maiorofer.toString());
    sb.append("\nLicitador: ");
    sb.append(this.maioruti);
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
      return (this.getId().equals(l.getId()) && this.getDono().equals(l.getDono()) && this.getDescricao().equals(l.getDescricao()) && this.getMaiorOferta().equals(l.getMaiorOferta()) && this.getMaiorUti().equals(l.getMaiorUti()));
    }
  }
}
