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
public class UtilizadorNaoExisteException extends Throwable {

    public UtilizadorNaoExisteException(){
        super();
    }

    public UtilizadorNaoExisteException(String s){
        super(s);
    }
}
