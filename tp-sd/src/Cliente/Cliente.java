/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Gonçalo
 */
public class Cliente extends Thread {

    public void run() {
        try {
            Socket socket = new Socket("localhost",12345);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());


            ClienteApp ca = new ClienteApp(out,in);

            ca.iniciaAppCliente();

        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String [] args ){

        Cliente c = new Cliente();
        c.start();
    }
}
