/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.util.*;

/**
 *
 * @author Gonçalo
 */
public class Utilizador {
    
        private String username;
        private String password;
        private LinkedList<String> mensagens;

        
        
        public Utilizador(){
            this.username = "";
            this.password = "";
            this.mensagens = new LinkedList<String>();
        }
    
    
        public Utilizador(String username, String password){
            this.username = username;
            this.password = password;
            this.mensagens = new LinkedList<String>();
        }
        
        public Utilizador(Utilizador u){
            this.username = u.getUsername();
            this.password = u.getPassword();
            this.mensagens = new LinkedList<String>();
        }
        
    
        
        public String getUsername(){return this.username;}

        public void setUsername(String username){this.username = username;}

        public  String getPassword(){return this.password;}

        public  void setPassword(String password){this.password = password;}
        
        
        
        public void ultrapassou(int leilao){
            String resp="";
            StringBuilder sb = new StringBuilder(resp);
            sb.append("Acorde!! A sua licitação no leilão ");
            sb.append(leilao);
            sb.append(" foi ultrapassada!! ");
            
            this.mensagens.addFirst(resp);
        
        }
        
        public void terminou(int leilao, String vencedor, float valor){
        
            String resp="";
            StringBuilder sb = new StringBuilder(resp);
            sb.append("O leilão ");
            sb.append(leilao);
            sb.append("terminou, o vencedor foi");
            sb.append(vencedor);
            sb.append("com uma licitação de €");
            sb.append(valor);
            
            this.mensagens.addFirst(resp);
        
        }
        
        public void lerMensagens(){
        
            while(this.mensagens.size() > 0){
                System.out.println(this.mensagens.pollFirst());
            }
        
        }
    
    
    
        public String toString(){
            StringBuilder sb = new StringBuilder();

            sb.append("Cliente:" +this.getUsername());

            return sb.toString();
        }

        public Utilizador clone(){return new Utilizador(this);}

        public boolean equals(Object u){
            if(this==u) return true;
            if((u==null) || this.getClass()!= u.getClass()) return false;

            else{
                Utilizador pa = (Utilizador) u;

                return ((this.getUsername().equals(pa.getUsername())) && (this.getPassword().equals(pa.getPassword())));
            }
        }
    
    
}
