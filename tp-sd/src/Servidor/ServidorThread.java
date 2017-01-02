package Servidor;


import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorThread extends Thread {
    private Socket socket;
    private Utilizador utilizador;
    private Leiloes leiloes;
    private Utilizadores users;

    public ServidorThread(Socket socket) {
        this.socket = socket;
    }

    public ServidorThread(Socket socket, Utilizadores users, Leiloes leiloes) {
        this.socket = socket;
        this.users = users;
        this.leiloes = leiloes;
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
                } catch (IOException e) {
                    e.printStackTrace();
                }    
                
                if (line.equals("login")) {
                    System.out.println("cheguei login");
                    String username = (String) in.readObject();
                    String password = (String) in.readObject();
                    String resp = "insucesso";
                    Boolean logged = false;         
                    try {
                        logged = this.users.login(username, password);
                        this.utilizador = this.users.getUtilizador(username);
                        this.utilizador.atualizarOos(out);

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
                    if(this.users.existeUtilizador(username)){
                        try{
                            this.utilizador = this.users.getUtilizador(username);
                        }catch (UtilizadorNaoExisteException e) {
                            System.out.println("Erro!");
                            e.printStackTrace();
                        }
                    }
                }

                if (line.equals("registo")) {
                    String username = (String) in.readObject();
                    String password = (String) in.readObject();
                    String resp;
                    System.out.println("REGISTO SV " + username +" "+ password +"\n");
                    try {
                        this.users.registarUtilizador(username, password, out);
                        resp = "sucesso";                
                    } catch (UtilizadorJaRegistadoException e) {
                        resp = "jaregistado";            
                    } catch (Exception e) {
                        resp = "erro";
                    }

                    out.writeObject(resp);
                    if(this.users.existeUtilizador(username)){
                        try{
                            this.utilizador = this.users.getUtilizador(username);
                        }catch (UtilizadorNaoExisteException e) {
                            System.out.println("Erro!");
                            e.printStackTrace();
                        }
                    }
                }

                if (line.equals("licitar")) {
                    int idleilao = (int) in.readObject();
                    System.out.println(idleilao);
                    String resp;
                    float valor = (float) in.readObject();
                    System.out.println(valor);
                    resp = this.leiloes.licitar(idleilao, this.utilizador, valor);
                    out.writeObject(resp);
                }

                if (line.equals("consultar")) {
                    out.writeObject(this.leiloes.listarEmCurso(this.utilizador.getUsername()));
                }

                if (line.equals("criar")) {
                    String detalhes, resp;
                    detalhes = (String) in.readObject();
                    System.out.println(detalhes+"\n");
                    resp = "Inserido com sucesso, id: ";
                    int res = this.leiloes.inserirLeilao(this.utilizador, detalhes);
                    StringBuilder sb = new StringBuilder();
                    sb.append(resp);
                    sb.append(res);
                    out.writeObject(sb.toString());
                }

                if (line.equals("terminar")) {
                    int id = (int) in.readObject();     
                    String resp;
                    resp = this.leiloes.fecharLeilao(id, this.utilizador.getUsername());
                    out.writeObject(resp);
                }
                
                if (line.equals("logout")) {
                    this.utilizador.atualizarOos(null);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServidorThread.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
}
