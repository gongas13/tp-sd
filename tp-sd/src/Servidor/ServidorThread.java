package Servidor;


import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServidorThread extends Thread {
    private Socket socket;
    private Dados dados;
    private Utilizador utilizador;
    private String user;
    private HashMap<>;

    public ServidorThread(Socket socket) {
        this.socket = socket;
        this.dados = new Dados();
    }

    public ServidorThread(Socket socket, Dados dados) {
        this.socket = socket;
        this.dados = dados;
    }

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

                if (line.equals("registo")) {
                    String username = (String) in.readObject();
                    String password = (String) in.readObject();
                    String resposta;

                    try {
                        this.dados.registarUtilizador(username, password);
                        resposta = "sucesso";
                        this.user = username;
                        this.utilizador = this.dados.getUtilizador(username);


                    } catch (UtilizadorJaRegistadoException e) {
                        resposta = "jaregistado";
                    } catch (UtilizadorNaoExisteException e) {
                        resposta = "utilizadornaoexiste";
                    } catch (Exception e) {
                        resposta = "erro";
                    }

                    out.writeObject(resposta);
                }

                if (line.equals("login")) {
                    String username = (String) in.readObject();
                    String password = (String) in.readObject();
                    String resp = "insucesso";
                    Boolean logged = false;
                    System.out.println(this.utilizador.getClass());
                    try {
                        logged = this.dados.login(username, password);
                        this.user = username;
                        this.utilizador = this.dados.getUtilizador(username);
                        System.out.println(this.utilizador.getClass());
                    } catch (UtilizadorNaoExisteException e) {
                        resp = "utilizadornaoexiste";
                    } catch (PasswordErradaException e) {
                        resp = "passworderrada";
                    } catch (Exception e) {
                        System.out.println("Erro!");
                        e.printStackTrace();
                    }
                    if (this.utilizador instanceof Condutor && logged)
                        resp = "logadocondutor";
                    else if (logged)
                        resp = "logadocliente";
                    out.writeObject(resp);
                }

                if (line.equals("requisitar")) {
                    Coordenada inicio;
                    Coordenada fim;
                    String response;


                    inicio = (Coordenada) in.readObject();
                    this.utilizador.setCoordenada(inicio);
                    fim = (Coordenada) in.readObject();
                    this.dados.novoPedidoViagem(this.utilizador, fim);

                    this.viagem = this.dados.getViagemCliente(this.user);

                    Condutor condutor = this.viagem.getCondutor();
                    Coordenada lcondutor = condutor.getCoordenada();
                    double tempodechegada = inicio.distanciaCoords(lcondutor) * 1.3;
                    double valordaviagem = this.viagem.getValorViagem();

                    out.writeObject(condutor);

                    out.writeObject(tempodechegada);

                    out.writeObject(valordaviagem);

                    this.dados.removerViagemCliente(this.utilizador.getUsername());
                    this.dados.removerViagemCondutor(condutor.getUsername());

                    /**while (!this.viagem.getChat().equals("cheguei")) {
                        wait(1);
                    }
                    out.writeObject("cheguei");

                    response = (String) in.readObject();
                    this.viagem.setChat(response);
                    double custoviagem = this.viagem.getValorViagem();
                    out.writeObject(custoviagem);**/



                }

                if (line.equals("anunciardisponibilidade")) {
                    Coordenada localizacao;

                    localizacao = (Coordenada) in.readObject();

                    this.utilizador.setCoordenada(localizacao);

                    this.dados.novoAnuncioDisponibilidade((Condutor) this.utilizador);

                    this.viagem = this.dados.getViagemCondutor(user);
                    System.out.println(this.viagem.toString());

                    Utilizador cliente = this.viagem.getCliente();
                    Coordenada inicio = this.viagem.getCInicio();
                    double valordaviagem = this.viagem.getValorViagem();
                    out.writeObject(cliente);
                    out.writeObject(inicio);
                    out.writeObject(valordaviagem);


                    /**String estado = (String) in.readObject();
                    this.viagem.setChat(estado);

                    while(!this.viagem.getChat().equals("fim")){
                        wait(1);
                    }

                    out.writeObject("Viagem concluida");


                    /**String response = viagem.getCInicio().toString();
                     out.writeObject("Início: " + response);
                     response = viagem.getCFim().toString();
                     out.writeObject("Fim: " + response);**/
                }

                if (line.equals("sercondutor")) {
                    String matricula, modelo;

                    matricula = (String) in.readObject();
                    modelo = (String) in.readObject();

                    this.dados.alterarClienteToCondutor(this.utilizador.getUsername(), matricula, modelo);
                    this.utilizador = this.dados.getUtilizador(this.user);

                    String resp = "sucesso";

                    out.writeObject(resp);
                }

                if (line.equals("mudarpassword")) {
                    String novapassword = (String) in.readObject();

                    this.utilizador.setPassword(novapassword);

                    String resp = "sucesso";

                    out.writeObject(resp);
                }

                if (line.equals("sercliente")) {

                    this.dados.alterarCondutorToCliente(this.utilizador.getUsername());
                    this.utilizador = this.dados.getUtilizador(this.user);
                    String resp = "sucesso";

                    out.writeObject(resp);
                }

                if (line.equals("mudardetalhesveiculo")) {
                    String matricula, modelo;
                    matricula = (String) in.readObject();
                    modelo = (String) in.readObject();

                    Condutor ca = (Condutor) this.utilizador;
                    ca.setMatricula(matricula);
                    ca.setModelo(modelo);

                    String resp = "sucesso";

                    out.writeObject(resp);

                }

                if (line.equals("detalhesveiculo")) {
                    try {
                        Condutor ca = (Condutor) this.utilizador;
                        String matricula = ca.getMatricula();
                        String modelo = ca.getModelo();
                        System.out.println(ca.toString());
                        out.writeObject(ca.getUsername());
                        out.writeObject(ca.getPassword());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (IOException | ClassNotFoundException /**| InterruptedException **/| UtilizadorNaoExisteException e) {
            e.printStackTrace();
        }
    }
}
