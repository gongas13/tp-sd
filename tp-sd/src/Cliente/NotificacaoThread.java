/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.IOException;
import java.io.ObjectInput;

/**
 *
 * @author Gonçalo
 */
public class NotificacaoThread extends Thread {
    
    private ObjectInput in;
    private Mensagens msg;
    
    
     public NotificacaoThread(ObjectInput in, Mensagens msg) {
        this.in = in;
        this.msg = msg;
    }
     
     
    public void run(){
        try{       
            msg.    
            String response;
            while (true){
                response = (String) in.readObject();
                String parts[] = response.split("|");
                if (parts[0].equals("1")) {
                    System.out.println("Tem uma notificação:\n" + parts[1]);                    
                } else msg.msg = response;
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            }
    }
}
