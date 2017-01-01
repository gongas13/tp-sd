/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;
import java.net.Socket;
import java.net.SocketAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

/**
 *
 * @author Gonçalo
 */
public class LeilaoThread extends Thread {

    private Utilizador utilizador;
    private Leilao leilao;
    
    
    public LeilaoThread(Utilizador utilizador, Leilao leilao) {
        this.utilizador = utilizador;
        this.leilao = leilao;
    }
    
        
    public void run(){
        try{
            
            Socket socket = this.utilizador.getSocket();
            
            if(socket!=null){
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            
            String msg="";
            StringBuilder sb = new StringBuilder(msg);
            sb.append("O leilão ");
            sb.append(this.leilao.getId());
            sb.append("terminou, o vencedor foi");
            sb.append(this.leilao.getMaiorUti());
            sb.append("com uma licitação de €");
            sb.append(this.leilao.getMaiorOferta());
            
            out.writeObject(msg);
            }else{
                this.utilizador.terminou(this.leilao.getId(),this.leilao.getMaiorUti(),this.leilao.getMaiorOferta());
            }
            
            
        }catch (Exception e) {
            System.out.println("Erro!");
            e.printStackTrace();
        }
    }
}