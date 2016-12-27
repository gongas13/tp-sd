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
public class UtilizadorJaRegistadoException extends Throwable {
    public UtilizadorJaRegistadoException(){
        super();
    }
    public UtilizadorJaRegistadoException(String s){
        super(s);
    }
}
