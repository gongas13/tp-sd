/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Gonçalo
 */
public class ClienteApp {
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    public static String username;
    String mensagemServidor;


    public ClienteApp(ObjectOutputStream out, ObjectInputStream in){
        this.out = out;
        this.in = in;
    };

    private static Menu menuInit,menuLoggedIn, menuLoggedInVendedor, menuChoose;


    public static void carregarMenus(){
        String [] minit = {
                "Login",
                "Registar novo utilizador"
        };
        
        String [] mChoose = {
                "Comprador",
                "Vendedor"
        };

        String [] mLogged = {
                "Licitar",
                "Consultar Leilões",
        };

        String [] mLoggedVendedor = {
                "Criar Leilão",
                "Terminar Leilão",
                "Consultar Leilões",
        };

        menuInit = new Menu(minit);
        menuLoggedIn = new Menu(mLogged);
        menuLoggedInVendedor = new Menu (mLoggedVendedor);
        menuChoose = new Menu (mChoose);
    }
    
    
    public void iniciaAppCliente(){
        carregarMenus();

        do{
            menuInit.executa();
            switch (menuInit.getOpcao()){
                case 1: LoginCliente();
                    break;
                case 2: registaCliente();
                    break;
            }
        } while (menuInit.getOpcao()!=0);

        System.out.println("Bye!");
    }
    
    
    public void LoginCliente(){
        Scanner input = new Scanner(System.in);
        String password;

        System.out.print("Username:");
        username = input.nextLine();
        System.out.print("Password:");
        password = input.nextLine();

        try{
            ClienteApp.out.writeObject("login");

            System.out.println("Cheguei");
            out.writeObject(username);
            out.writeObject(password);

            
            String response = (String) in.readObject();

            if(response.equals("logado")){
                System.out.println("Logado com sucesso!");
                initMenuChoose();
                
            }
            if(response.equals("utilizadornaoexiste")){
                System.out.println("Utilizador não registado");
            }
            if(response.equals("passworderrada")){
                System.out.println("Password errada");
            }

        } catch (IOException e){
            System.out.println("Não foi possível obter ligação ao servidor!");
        } catch (ClassNotFoundException e){
            System.out.println("Erro nos dados recebidos");
        }       
    }    
    
    public void initMenuChoose(){
        do{
            menuChoose.executa();
            switch (menuChoose.getOpcao()){
                case 1: initMenuComprador();
                    break;
                case 2: initMenuVendedor();
                    break;
            }
        } while (menuChoose.getOpcao()!=0);
        try {
            out.writeObject("logout");
        } catch (IOException ex) {
            Logger.getLogger(ClienteApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void registaCliente(){
        Scanner input = new Scanner(System.in);
        String username, password;

        System.out.print("Username:");
        username = input.nextLine();
        System.out.print("Password:");
        password = input.nextLine();

        System.out.println("lol\n");
        try{
            ClienteApp.out.writeObject("registo");

            out.writeObject(username);
            out.writeObject(password);

            String response = (String) in.readObject();

            if(response.equals("sucesso")){
                System.out.println("Registo feito com sucesso!");
                System.out.println("Logado com sucesso");
                initMenuChoose();
            }
            if(response.equals("jaregistado")){
                System.out.println("Utilizador já registado");
            }
            if(response.equals("erro")){
                System.out.println("Erro ao registar utilizador!");
            }

        } catch (IOException e){
            System.out.println(e.toString()+"\nNão foi possível obter ligação ao servidor!");
        } catch (ClassNotFoundException e){
            System.out.println("Erro nos dados recebidos");
        }
    }
    
    public void initMenuComprador(){
        int vendedor=0;
        do{
            menuLoggedIn.executa();
            switch (menuLoggedIn.getOpcao()){
                case 1:licitar();
                    break;
                case 2:consultarLeiloes();
                    break;
            }
        } while (menuLoggedIn.getOpcao()!=0 && vendedor == 0);
    }
    


    public void licitar(){
        Scanner input = new Scanner(System.in);
        int idleilao;
        float valor;

        System.out.print("Leilão a licitar:");
        idleilao=input.nextInt();
        System.out.println(idleilao);
        System.out.print("Valor da licitação:");
        valor=input.nextFloat();
        System.out.println(valor);

        try{
            out.writeObject("licitar");
            out.writeObject(idleilao);
            out.writeObject(valor);

            boolean notificacao;
            String resp;
            do {
                resp = (String) in.readObject();
                String parts[] = resp.split("|");
                if (parts[0].equals("1")) {
                    System.out.println("Tem uma notificação:\n" + resp.substring(2));
                    notificacao = true;
                } else notificacao = false;                    
            } while (notificacao == true);

            System.out.println(resp);
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
        
    public void consultarLeiloes(){

        try{

            out.writeObject("consultar");

            boolean notificacao;
            String resp;
            do {
                resp = (String) in.readObject();
                String parts[] = resp.split("|");
                if (parts[0].equals("1")) {
                    System.out.println("Tem uma notificação:\n"+ resp.substring(2));
                    notificacao = true;
                } else notificacao = false;                    
            } while (notificacao == true);

            System.out.println(resp);

        }catch (IOException | ClassNotFoundException e){
        e.printStackTrace();
            }
    }

    public void mudarPassword(){
        Scanner input = new Scanner(System.in);
        String novapassword;

        System.out.print("Nova password:");
        novapassword=input.nextLine();

        try{
            out.writeObject("mudarpassword");
            out.writeObject(novapassword);

            boolean notificacao;
            String resp;
            do {
                resp = (String) in.readObject();
                String parts[] = resp.split("|");
                if (parts[0].equals("1")) {
                    System.out.println("Tem uma notificação:\n" + resp.substring(2));
                    notificacao = true;
                } else notificacao = false;                    
            } while (notificacao == true);
            
            System.out.println(resp);
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    
    public void initMenuVendedor(){
        int comprador = 0;
        do{
            menuLoggedInVendedor.executa();
            switch (menuLoggedInVendedor.getOpcao()){
                case 1:criarLeilao();
                    break;
                case 2:terminarLeilao();
                    break;
                case 3:consultarLeiloes();
                    break;
            }
        } while (menuLoggedInVendedor.getOpcao()!=0 && comprador == 0);
    }
        
            
    public void criarLeilao(){
        String detalhes;
        Scanner input = new Scanner (System.in);
        System.out.println("Inserir Detalhes do leilao:");
        detalhes = input.nextLine();
        try{
            out.writeObject("criar");
            out.writeObject(detalhes);

            boolean notificacao;
            String resp;
            do {
                resp = (String) in.readObject();
                String parts[] = resp.split("|");
                if (parts[0].equals("1")) {
                    System.out.println("Tem uma notificação:\n" + resp.substring(2));
                    notificacao = true;
                } else notificacao = false;                    
            } while (notificacao == true);

            System.out.println(resp);

        } catch (IOException | ClassNotFoundException e){
        e.printStackTrace();
        }
    }
            
    public void terminarLeilao(){
        Scanner input = new Scanner(System.in);
        int idleilao;

        System.out.print("Leilão a terminar:");
        idleilao=input.nextInt();

        try{
            out.writeObject("terminar");
            out.writeObject(idleilao);

            boolean notificacao;
            String resp;
            do {
                resp = (String) in.readObject();
                String parts[] = resp.split("|");
                if (parts[0].equals("1")) {
                    System.out.println("Tem uma notificação:\n" + resp.substring(2));
                    notificacao = true;
                } else notificacao = false;                    
            } while (notificacao == true);

            System.out.println(resp);
        } catch (IOException | ClassNotFoundException e){
        e.printStackTrace();
        }
    }
}

