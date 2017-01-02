/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.ObjectOutputStream;
import java.util.*;

/**
 *
 * @author Gonçalo
 */
public class Utilizador {
    
        private String username;
        private String password;
        private List<Integer> leiloes;
        private LinkedList<String> mensagens;
        private ObjectOutputStream out;
        
        
        public Utilizador(){
            this.username = "";
            this.password = "";
            this.leiloes = new ArrayList<Integer>();
            this.mensagens = new LinkedList<String>();
        }
    
    
        public Utilizador(String username, String password, ObjectOutputStream out){
            this.username = username;
            this.password = password;
            this.leiloes = new ArrayList<Integer>();
            this.mensagens = new LinkedList<String>();
            this.out = out;
        }
        
        public Utilizador(Utilizador u){
            this.username = u.getUsername();
            this.password = u.getPassword();
            this.leiloes = u.getLeiloes();
            this.mensagens = new LinkedList<String>();
            this.out = u.getOos();
        }
        
    
        
        public String getUsername(){return this.username;}

        public void setUsername(String username){this.username = username;}

        public  String getPassword(){return this.password;}

        public  void setPassword(String password){this.password = password;}
        
        public ObjectOutputStream getOos() {
            return this.out;
        }
        
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
        
        public String getMensagens(){
            return this.mensagens.toString();
        }
    
        public void adicionarLeilao(int id) {
            this.leiloes.add(id);
        }
        
        public void atualizarOos(ObjectOutputStream out) {
            this.out = out;
        }
        
        public List<Integer> getLeiloes() {
            List<Integer> lei = new ArrayList<>();

            for (Integer leiloe : this.leiloes) {
                lei.add(leiloe);
            }
            return lei;
        }
        
        public boolean temLeilao(int id) {
            boolean resp = false;
            if (this.leiloes.contains(id))
                resp = true;
            return resp;
        }

    @Override
    public String toString() {
        return "Utilizador{" + "username=" + username + ", password=" + password + ", leiloes=" + leiloes + ", mensagens=" + mensagens + '}';
    }
    
        

        public Utilizador clone(){return new Utilizador(this);}

        @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Utilizador other = (Utilizador) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.leiloes, other.leiloes)) {
            return false;
        }
        if (!Objects.equals(this.mensagens, other.mensagens)) {
            return false;
        }
        return true;
    }

    
    
    
}
