package Servidor;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.Scanner;
import java.net.SocketAddress;

public class Servidor {

    private Servidor(){};

    private static ServerSocket servidorSocket;
    private static Utilizadores users;
    private static Leiloes leiloes;

    public static void main(String[] args) {
        try{
            Servidor.servidorSocket = new ServerSocket(12345);
            users = new Utilizadores();
            leiloes = new Leiloes();
            try{
                users.registarUtilizador("mets", "22", null);
                users.registarUtilizador("1", "22",  null);
                users.registarUtilizador("2", "22", null);
                users.registarUtilizador("3", "22", null);
            }
            catch(UtilizadorJaRegistadoException e) {}
            System.out.println("Servidor criado!");
            while (true){
                try {
                    Socket socket = servidorSocket.accept();                    
                    Thread servidorThread = new ServidorThread(socket,users,leiloes);

                    System.out.println("Ligacão aceite!");
                    servidorThread.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}