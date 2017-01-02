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
                
                System.out.print("LINE: ");
                System.out.println(line);
                
                if (line.equals("login")) {
                    System.out.println("cheguei login");
                    String username = (String) in.readObject();
                    String password = (String) in.readObject();
                    String resp = "insucesso";
                    Boolean logged = false;                    
                    try {
                        Thread wt = new WorkerThread(out,users,leiloes,line,username,password, this.socket);
                        wt.start();                    
                    } catch (Exception e) {
                        System.out.println("Erro!");
                        e.printStackTrace();
                    }   
                    finally{
                        Thread.sleep(1000);
                        if(this.users.existeUtilizador(username)){
                            try{
                                this.utilizador = this.users.getUtilizador(username);
                            }catch (UtilizadorNaoExisteException e) {
                                System.out.println("Erro!");
                                e.printStackTrace();
                            }
                        }
                    }
                }

                if (line.equals("registo")) {
                    String username = (String) in.readObject();
                    String password = (String) in.readObject();

                    try {
                        Thread wt = new WorkerThread(out,users,leiloes,line,username,password, this.socket);
                        wt.start();                        
                    }catch (Exception e) {
                        System.out.println("Erro!");
                        e.printStackTrace();
                    }
                    finally{
                        Thread.sleep(1000);
                        if(this.users.existeUtilizador(username)){
                            try{
                                this.utilizador = this.users.getUtilizador(username);
                            }catch (UtilizadorNaoExisteException e) {
                                System.out.println("Erro!");
                                e.printStackTrace();
                            }
                        }
                    }
                }

                if (line.equals("licitar")) {
                    int idleilao = (int) in.readObject();
                    System.out.println(idleilao);
                    String resp;
                    float valor = (float) in.readObject();
                    System.out.println(valor);
                    /* try {
                    Thread wt = new WorkerThread(out,users,leiloes,line,this.utilizador.getUsername(),idleilao,valor);
                    wt.start();
                    }catch (Exception e) {
                    System.out.println("Erro!");
                    e.printStackTrace();
                    }*/
                    resp = this.leiloes.licitar(idleilao, this.utilizador, valor);
                    out.writeObject(resp);
                }

                if (line.equals("consultar")) {
                    try {
                        Thread wt = new WorkerThread(out,users,leiloes,line,this.utilizador.getUsername());
                        wt.start();                        
                    }catch (Exception e) {
                        System.out.println("Erro!");
                        e.printStackTrace();
                    }
                }


                if (line.equals("mudarpassword")) {
                    String newpassword = (String) in.readObject();
                    try {
                        Thread wt = new WorkerThread(out,users,leiloes,line,this.utilizador.getUsername(),newpassword);
                        wt.start();                        
                    }catch (Exception e) {
                        System.out.println("Erro!");
                        e.printStackTrace();
                    }
                }

                if (line.equals("criar")) {
                    String detalhes;
                    detalhes = (String) in.readObject();
                    System.out.println(detalhes+"\n");
                    try {
                        Thread wt = new WorkerThread(out,users,leiloes,line,this.utilizador.getUsername(),detalhes);
                        wt.start();                        
                    }catch (Exception e) {
                        System.out.println("Erro!");
                        e.printStackTrace();
                    }
                }

                if (line.equals("terminar")) {
                    int id = (int) in.readObject();                    
                    try {
                        Thread wt = new WorkerThread(out,users,leiloes,line,this.utilizador.getUsername(),id);
                        wt.start();                        
                    }catch (Exception e) {
                        System.out.println("Erro!");
                        e.printStackTrace();
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServidorThread.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
}
