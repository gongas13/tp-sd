package Servidor;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkerThread extends Thread{
  private Socket socket;
  private Utilizador utilizador;
  private Leiloes leiloes;
  private Utilizadores users;
  private String line;

  public WorkerThread(Socket socket, Utilizadores users, Leiloes leiloes, String line){
      this.socket = socket;
      this.users = users;
      this.leiloes = leiloes;
      this.line = line;
  }

  @Override
  public void run() {
    try {
      ObjectOutputStream out = new ObjectOutputStream(this.socket.getOutputStream());
      out.flush();
      ObjectInput in = new ObjectInputStream(this.socket.getInputStream());

      while (true) {
        String line = null;
        try {
            line = (String) in.readObject();
            Thread wt = new WorkerThread(socket,users,leiloes,line);
            wt.start();
        } catch (IOException e) {
            e.printStackTrace();
        }  

        if (line.equals("login")) {
            String username = (String) in.readObject();
            String password = (String) in.readObject();
            String resp = "insucesso";
            Boolean logged = false;
            System.out.println(this.utilizador.getClass());
            try {
                logged = this.users.login(username, password);
                this.utilizador = this.users.getUtilizador(username);
                System.out.println(this.utilizador.getClass());
            } catch (UtilizadorNaoExisteException e) {
                resp = "utilizadornaoexiste";
            } catch (PasswordErradaException e) {
                resp = "passworderrada";
            } catch (Exception e) {
                System.out.println("Erro!");
                e.printStackTrace();
            }
            if (logged)
                resp = "logado";
            out.writeObject(resp);
        }

        if (line.equals("registo")) {
            String username = (String) in.readObject();
            String password = (String) in.readObject();
            String resposta;

            try {
                this.users.registarUtilizador(username, password);
                resposta = "sucesso";
                this.utilizador = this.users.getUtilizador(username);
            } catch (UtilizadorJaRegistadoException e) {
                resposta = "jaregistado";
            } catch (UtilizadorNaoExisteException e) {
                resposta = "utilizadornaoexiste";
            } catch (Exception e) {
                resposta = "erro";
            }

            out.writeObject(resposta);
        }

        if (line.equals("licitar")) {
            String response;
            int idleilao = in.readInt();
            int valor = in.readInt();

            response = this.leiloes.licitar(idleilao, this.utilizador, valor);

            out.writeObject(response);
        }

        if (line.equals("consultar")) {
            out.writeObject(this.leiloes.listarEmCurso(line));
        }


        if (line.equals("mudarpassword")) {
            String newpassword = in.readLine();
            this.utilizador.setPassword(newpassword);
            out.writeObject("sucesso");
        }

        if (line.equals("criar")) {
            String detalhes;
            detalhes = in.readLine();

            this.leiloes.inserirLeilao(this.utilizador, detalhes);
        }

        if (line.equals("terminar")) {
            int id = in.readInt();
            String resp;
            resp = this.leiloes.fecharLeilao(id, utilizador);
            out.writeObject(resp);
        }
      }
    }catch (IOException e) {
      e.printStackTrace();
    }catch (ClassNotFoundException ex) {
      Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}

