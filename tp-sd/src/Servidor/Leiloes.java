package Servidor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Leiloes implements Serializable{
  ReentrantLock lock;
  HashMap<String, Leilao> leiloesemcurso;
  HashMap<String, Leilao> leiloesterminados;

  public Leiloes(){
    this.lock = new ReentrantLock();
    leiloesemcurso = new HashMap<>();
    leiloesterminados = new HashMap<>();
  }

  public Leiloes(){
    this.lock = new ReentrantLock();
    leiloesemcurso = new HashMap<>();
    leiloesterminados = new HashMap<>();
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

  
}
