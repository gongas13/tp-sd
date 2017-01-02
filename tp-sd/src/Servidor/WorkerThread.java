package Servidor;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkerThread extends Thread{  
  private Utilizador utilizador;
  private Leiloes leiloes;
  private Utilizadores users;
  private String line,user,pass;
  private int id;
  private float valor;
  private ObjectOutputStream out;
  private Socket socket;

  public WorkerThread(ObjectOutputStream out, Utilizadores users, Leiloes leiloes, String line, String user){      
      this.users = users;
      this.leiloes = leiloes;
      this.line = line;
      this.user = user;
      this.out = out;
  }
  
  public WorkerThread(ObjectOutputStream out, Utilizadores users, Leiloes leiloes, String line, String user, String pass){
      this.out = out;
      this.users = users;
      this.leiloes = leiloes;
      this.line = line;
      this.user = user;
      this.pass = pass;
  }
  
  public WorkerThread(ObjectOutputStream out, Utilizadores users, Leiloes leiloes, String line, String user, String pass, Socket socket){
      this.out = out;
      this.users = users;
      this.leiloes = leiloes;
      this.line = line;
      this.user = user;
      this.pass = pass;
      this.socket = socket;
  }
  
  public WorkerThread(ObjectOutputStream out, Utilizadores users, Leiloes leiloes, String line, String user, int id){
      this.out = out;
      this.users = users;
      this.leiloes = leiloes;
      this.line = line;
      this.user = user;
      this.id = id;
  }
  
  public WorkerThread(ObjectOutputStream out, Utilizadores users, Leiloes leiloes, String line, String user, int id, float valor){
      this.out = out;
      this.users = users;
      this.leiloes = leiloes;
      this.line = line;
      this.user = user;
      this.id = id;
      this.valor = valor;
  }

  @Override
  public void run() {
    try {            
      String resp = "";
      out.flush();     
      
        if (line.equals("login")) {
            boolean logged = false;
            try {
                logged = this.users.login(this.user, this.pass);
                this.utilizador = this.users.getUtilizador(this.user);
                this.utilizador.atualizarSocket(socket);
                
            } catch (UtilizadorNaoExisteException e) {
                resp = "utilizadornaoexiste";
            } catch (PasswordErradaException e) {
                resp = "passworderrada";
            }catch (Exception e) {
                System.out.println("Erro!");
                e.printStackTrace();
            }
            if (logged)
                resp = "logado";
            out.writeObject(resp);
        }

        if (line.equals("registo")){
            System.out.println("REGISTO SV " + this.user +" "+ this.pass+"\n");
            try {
                this.users.registarUtilizador(this.user, this.pass, this.socket);
                resp = "sucesso";                
            } catch (UtilizadorJaRegistadoException e) {
                resp = "jaregistado";            
            } catch (Exception e) {
                resp = "erro";
            }

            out.writeObject(resp);
        }

        if (line.equals("licitar")) {
            resp = this.leiloes.licitar(this.id, this.utilizador, this.valor);
            out.writeObject(resp);
        }

        if (line.equals("consultar")) {
            out.writeObject(this.leiloes.listarEmCurso(this.user));
        }

        if (line.equals("criar")){             
            resp = "Inserido com sucesso";
            this.leiloes.inserirLeilao(this.user, this.pass);
            out.writeObject(resp);
        }

        if (line.equals("terminar")) {
            
            resp = this.leiloes.fecharLeilao(this.id, this.user);
            out.writeObject(resp);
        }
    }catch (IOException e) {
      e.printStackTrace();
    }
  }
}

