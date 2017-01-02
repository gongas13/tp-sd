package Servidor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Leilao implements Serializable{
  private int id;
  private float maiorofer;
  private Utilizador dono;
  private String descricao, maioruti, ultimouti;
  private List<Utilizador> utilizadores;
  protected Condition maiorOferta;

  public Leilao(int id, Utilizador dono, String descricao, ReentrantLock lock){
    this.id = id;
    this.dono = dono;
    this.descricao = descricao;
    this.maiorofer = 0;
    this.maioruti = "";
    this.ultimouti = "";
    this.maiorOferta = lock.newCondition();
    this.utilizadores = new ArrayList<>();
  }

  public Leilao(Leilao l){
    this.id = l.getId();
    this.dono = l.getDono();
    this.descricao = l.getDescricao();
    this.maiorofer = l.getMaiorOferta();
    this.maioruti = l.getMaiorUti();
    this.ultimouti = l.getUltimoUti();
    this.utilizadores = l.getUtilizadores();
  }

  public int getId(){
    return this.id;
  }

  public Utilizador getDono(){
    return this.dono;
  }

  public String getDescricao(){
    return this.descricao;
  }

  public String getUltimoUti(){
    return this.ultimouti;
  }

  public void insereLicitacao(float oferta, Utilizador utilizador){
    if(oferta>this.maiorofer) {
      this.ultimouti = this.maioruti;
      this.maiorofer = oferta;
      this.maioruti = utilizador.getUsername();
      this.utilizadores.add(utilizador);
    }
  }

  public float getMaiorOferta(){
    return this.maiorofer;
  }

  public String getMaiorUti(){
    return this.maioruti;
  }

  public String getDetalhes(){
    StringBuilder sb = new StringBuilder();
    sb.append("Id: ");
    sb.append(this.id);
    sb.append("\nDescricao: ");
    sb.append(this.descricao);
    sb.append("\nOferta: ");
    sb.append(this.maiorofer);
    sb.append("\n");

    return sb.toString();
  }
  
    public List<Utilizador> getUtilizadores() {
        return this.utilizadores;
    }

  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("Id: ");
    sb.append(this.id);
    sb.append("\nDono: ");
    sb.append(this.dono);
    sb.append("\nDescricao: ");
    sb.append(this.descricao);
    sb.append("\nMaior oferta: ");
    sb.append(this.maiorofer);
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
      return ((this.getId() == aux.getId()) && this.getDono().equals(aux.getDono()) && this.getDescricao().equals(aux.getDescricao()) && (this.getMaiorOferta() == aux.getMaiorOferta()) && this.getMaiorUti().equals(aux.getMaiorUti()) && this.getUltimoUti().equals(aux.getUltimoUti()));
    }
  }

    
}
