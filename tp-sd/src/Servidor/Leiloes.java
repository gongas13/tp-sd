package Servidor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Leiloes implements Serializable{
  private ReentrantLock lock;
  private int ids;
  private HashMap<int,Leilao> leiloesemcurso;
  private HashMap<int,Leilao> leiloesterminados;

  public Leiloes(){
    this.lock = new ReentrantLock();
    this.leiloesemcurso = new HashMap<>();
    this.leiloesterminados = new HashMap<>();
  }

  public Leiloes(Leiloes l){
    this.lock = new ReentrantLock();
    this.leiloesemcurso = l.getLeiloesEmCurso();
    this.leiloesterminados = l.getLeiloesTerminados();
  }

  public void insereLeilao(String utilizador, String descricao){
    this.lock.lock();
    try{
      Leilao l = new Leilao(this.ids, utilizador, descricao);
      this.leiloesemcurso.put(this.ids++,l);
    }
    finally{
      this.lock.unlock();
    }
  }

  public String fechaLeilao(int id, String utilizador){
    this.lock.lock();
    try{

    }
    finally{
      this.lock.unlock();
    }
  }

  public void licitar(int id, String utilizador, int oferta){
    this.lock.lock();
    try{
      String aux;
      Leilao l = this.leiloesemcurso.get(id).clone();
      if(l.getMaiorOferta() < oferta) aux = "A oferta tem de ser maior que a existente\n";
      else {
        l.insereLicitacao(oferta,utilizador);
        aux = "Inserido com sucesso";
      }
    }
    finally{
      this.lock.unlock();
      return aux;
    }
  }

  public String listaEmCurso(String utilizador){
    this.lock.lock();
    try{
      TreeSet aux = this.leiloesemcurso.values();
      Iterator it = aux.iterator();
      StringBuilder sb = new StringBuilder();
        while(it.hasNext()){
          Leilao l = it.next();
          sb.append(l.getDetalhes());
          if(l.getMaiorUti().equals(utilizador)) sb.append("Actualmente tem a maior oferta!\n");
          else if(l.getDono().equals(utilizador)) sb.append("É o dono!\n");
        }
    }
    finally{
      this.lock.unlock();
      return sb.toString();
    }
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
