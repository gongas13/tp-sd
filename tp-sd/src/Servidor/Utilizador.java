/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

/**
 *
 * @author Gonçalo
 */
public class Utilizador {
    
        private String username;
        private String password;

        
        
        public Utilizador(){
            this.username = "";
            this.password = "";
        }
    
    
        public Utilizador(String username, String password){
            this.username = username;
            this.password = password;
        }
        
        public Utilizador(Utilizador u){
            this.username = u.getUsername();
            this.password = u.getPassword();
        }
    
        
        public String getUsername(){return this.username;}

        public void setUsername(String username){this.username = username;}

        public  String getPassword(){return this.password;}

        public  void setPassword(String password){this.password = password;}
    
    
    
    
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
