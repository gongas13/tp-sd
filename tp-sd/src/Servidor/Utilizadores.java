/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;
import java.net.*;
import java.io.*;
import java.lang.*;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/**
 *
 * @author Gonçalo
 */
public class Utilizadores implements Serializable{

    private HashMap<String, Utilizador> utilizadores;
    private ReentrantLock lock;


    public Utilizadores()
    {
        this.utilizadores = new HashMap<String, Utilizador>();
        this.lock = new ReentrantLock();
    }

    public Utilizadores(HashMap<String,Utilizador> utilizadores)
    {
        Map res = new HashMap<String,Utilizador>();
        for(String key :utilizadores.keySet()){
            res.put(key,utilizadores.get(key));
        }
        this.lock = new ReentrantLock();
    }

    public Utilizadores(Utilizadores e)
    {
        this.utilizadores = (HashMap<String,Utilizador>)e.getUtilizadores();
        this.lock = new ReentrantLock();
    }


    public Map<String,Utilizador> getUtilizadores()
    {
        Map res = new HashMap<String,Utilizador>();
        this.lock.lock();
        try {
            for(String key: this.utilizadores.keySet()){
                res.put(key,this.utilizadores.get(key));
            }
        }finally {
            this.lock.unlock();
        }
        return res;
    }

    @Override
    public String toString()
    {
        this.lock.lock();

        try{
            StringBuilder sb = new StringBuilder();
            int i=1;

            for(String username : this.utilizadores.keySet()){
                sb.append("Utilizador ");
                sb.append(i);
                sb.append(": ");
                sb.append(username);

                i++;
            }

            sb.append("\n");

            return sb.toString();
        }finally {
            this.lock.lock();
        }
    }

    @Override
    public Utilizadores clone()
    {
        this.lock.lock();
        try{
            return new Utilizadores(this);
        }finally {
            this.lock.unlock();
        }
    }

    public boolean login(String username, String password) throws UtilizadorNaoExisteException, PasswordErradaException {
        this.lock.lock();
        try {
            if (this.utilizadores.get(username).getPassword().equals(password)) {
                return true;
            } else throw new PasswordErradaException();
        } catch (NullPointerException e) {
            throw new UtilizadorNaoExisteException();
        } finally {
            this.lock.unlock();
        }
    }

    public boolean existeUtilizador(String username){
        return this.utilizadores.containsKey(username);
    }
    
    public Utilizador getUtilizador(String username) throws UtilizadorNaoExisteException{
        this.lock.lock();

        try{
            return this.utilizadores.get(username);
        }catch (NullPointerException e){
            throw new UtilizadorNaoExisteException();
        }finally {
            this.lock.unlock();
        }
    }



    public void registarUtilizador(String username, String password, ObjectOutputStream out) throws UtilizadorJaRegistadoException
    {
        this.lock.lock();
        try{
            Utilizador u = new Utilizador(username,password, out);
            if(this.utilizadores.containsKey(username)){
                throw new UtilizadorJaRegistadoException();
            }
            else{
                this.utilizadores.put(username,u);
            }
        }finally {
            this.lock.unlock();
        }
    }
    
}
