/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 *
 * @author Gonçalo
 */
public class Menu
{
    private List<String> opcoes;
    private int op;

    public Menu(String [] opcoes){
        this.opcoes = new ArrayList<String>();
        for(String op:opcoes)this.opcoes.add(op);
        this.op = 0;
    }

    public void executa(){
        do{
            showMenu();
            this.op = lerOpcao();
        }while ( this.op == -1);
    }

    private void showMenu(){
        System.out.println("########  Gestor de Leilões - Cliente  ######## ");
        for(int i=0;i<this.opcoes.size();i++){
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(this.opcoes.get(i));
        }
        System.out.println("0 - Sair");
    }

    private int lerOpcao(){
        int op;
        Scanner is= new Scanner(System.in);

        System.out.print("Opção:");

        try{
            op= is.nextInt();
        }
        catch (InputMismatchException e){
            op = -1;
        }
        if(op<0 || op> this.opcoes.size()){
            System.out.println("Opção Invalida!");
            op = -1;
        }
        return op;
    }

    public int getOpcao(){return this.op;}
}
