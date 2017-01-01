package Servidor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import Servidor.Leilao;

public class Leiloes implements Serializable{
  protected ReentrantLock lock;
  private int ids;
  private HashMap<Integer,Leilao> leiloesemcurso;
  private HashMap<Integer,Leilao> leiloesterminados;

  public Leiloes(){
    this.lock = new ReentrantLock();
    this.ids = 1;
    this.leiloesemcurso = new HashMap<>();
    this.leiloesterminados = new HashMap<>();
  }

  public Leiloes(Leiloes l){
    this.lock = new ReentrantLock();
    this.leiloesemcurso = l.getLeiloesEmCurso();
    this.leiloesterminados = l.getLeiloesTerminados();
  }

  public void inserirLeilao(String utilizador, String descricao){
    this.lock.lock();
    try{
      Leilao l = new Leilao(this.ids, utilizador, descricao, lock);
      this.leiloesemcurso.put(this.ids++,l);      
    }
    finally{
      this.lock.unlock();
    }
  }

    public String fecharLeilao(int id, String utilizador){
        String resp = "Utilizador não é dono deste leilão";        
          this.lock.lock();
          Leilao aux = null;
          boolean flag = false;

          try{
            if(flag = this.leiloesemcurso.containsKey(id)){
              StringBuilder sb = new StringBuilder();
              aux = this.leiloesemcurso.get(id);
              if(aux.getDono().equals(utilizador)){
                this.leiloesemcurso.remove(id);
                this.leiloesterminados.put(id,aux);
                sb.append(aux.toString());
                resp = sb.toString();
              }
            }
          }
          finally{
            if(flag){
              aux.maiorOferta.signalAll();
            }
            this.lock.unlock();
          }        
        return resp;
    }

  public String licitar(int id, Utilizador utilizador, int oferta){
    this.lock.lock();
    String aux = "";
    boolean flag = false;
    Leilao l = null;
    try{
      if(flag = this.leiloesemcurso.containsKey(id)){
        l = this.leiloesemcurso.get(id);
        if(flag = (l.getMaiorOferta() < oferta)) aux = "A oferta tem de ser maior que a existente\n";
        else {
          l.insereLicitacao(oferta,utilizador.getUsername());
        }
      }
    }
    finally{
      try{
        if(flag){
            l.maiorOferta.await();
            if(this.leiloesterminados.containsKey(id)){
              aux = "O leilão terminou com a sua licitação de " + oferta + " a vencer.\n ";
            }
            else{
              aux = "A sua oferta no item "+id+" foi ultrapassada.\n ";
            }
        }
      }
      catch(InterruptedException e) {}
      this.lock.unlock();
    }
    return aux;
  }

  public String listarEmCurso(String utilizador){
    this.lock.lock();
    StringBuilder sb = new StringBuilder();
    try{
      Iterator it = this.leiloesemcurso.values().iterator();
        while(it.hasNext()){
          Leilao l = (Leilao) it.next();
          sb.append(l.getDetalhes());
          if(l.getMaiorUti().equals(utilizador)) sb.append("Actualmente tem a maior oferta!\n");
          else if(l.getDono().equals(utilizador)) sb.append("É o dono!\n");
        }
    }
    finally{
      this.lock.unlock();
    }
    return sb.toString();
  }

    public int getIds() {
        return ids;
    }

    public HashMap<Integer, Leilao> getLeiloesEmCurso() {
        return leiloesemcurso;
    }

    public HashMap<Integer, Leilao> getLeiloesTerminados() {
        return leiloesterminados;
    }

    @Override
    public String toString() {
        return "Leiloes{ids=" + ids + ", leiloesemcurso=" + leiloesemcurso + ", leiloesterminados=" + leiloesterminados + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Leiloes other = (Leiloes) obj;
        if (this.ids != other.ids) {
            return false;
        }
        if (!Objects.equals(this.leiloesemcurso, other.leiloesemcurso)) {
            return false;
        }
        if (!Objects.equals(this.leiloesterminados, other.leiloesterminados)) {
            return false;
        }
        return true;
    }




}
