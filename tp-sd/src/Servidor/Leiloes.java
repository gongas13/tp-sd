package Servidor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Leiloes implements Serializable{
  private ReentrantLock lock;
  private Condition maiorOferta;
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

  public void inserirLeilao(Utilizador utilizador, String descricao){
    this.lock.lock();
    try{
      Leilao l = new Leilao(this.ids, utilizador.getUsername(), descricao);
      this.leiloesemcurso.put(this.ids++,l);
      utilizador.adicionarLeilao(ids);
    }
    finally{
      this.lock.unlock();
    }
  }

    public String fecharLeilao(int id, Utilizador utilizador){
        String resp = "Utilizador não é dono deste leilão";
        if (utilizador.temLeilao(id)) {
          this.lock.lock();
          try{
            StringBuilder sb = new StringBuilder();
            Leilao aux = this.leiloesemcurso.remove(id);
            this.leiloesterminados.put(id,aux);
            sb.append(aux.toString());
            resp = sb.toString();
          }
          finally{
            this.lock.unlock();
          }
        }
        return resp;
    }

  public String licitar(int id, Utilizador utilizador, int oferta){
    this.lock.lock();
    try{
      String aux;
      Leilao l = this.leiloesemcurso.get(id);
      if(l.getMaiorOferta() < oferta) aux = "A oferta tem de ser maior que a existente\n";
      else {
        l.insereLicitacao(oferta,utilizador.getUsername());
        aux = "Inserido com sucesso";
      }
      return aux;
    }
    finally{
      this.lock.unlock();
    }
  }

  public String listarEmCurso(String utilizador){
    this.lock.lock();
    try{
      Iterator it = this.leiloesemcurso.values().iterator();
      StringBuilder sb = new StringBuilder();
        while(it.hasNext()){
          Leilao l = (Leilao) it.next();
          sb.append(l.getDetalhes());
          if(l.getMaiorUti().equals(utilizador)) sb.append("Actualmente tem a maior oferta!\n");
          else if(l.getDono().equals(utilizador)) sb.append("É o dono!\n");
        }
        return sb.toString();
    }
    finally{
      this.lock.unlock();
      
    }
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
        return "Leiloes{" + "lock=" + lock + ", maiorOferta=" + maiorOferta + ", ids=" + ids + ", leiloesemcurso=" + leiloesemcurso + ", leiloesterminados=" + leiloesterminados + '}';
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
        if (!Objects.equals(this.maiorOferta, other.maiorOferta)) {
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
