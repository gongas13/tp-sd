/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.util.concurrent.locks.Condition;

/**
 *
 * @author Miguel
 */
public class Mensagens {
        String msg;
        Condition jaLeu;
        
        public void doWait() {
            synchronized(jaLeu){
                try{
                 jaLeu.wait();
                } catch(InterruptedException e){                    
                }
            }
        }
        
        public void doNotify(){
            synchronized(jaLeu){
                jaLeu.notify();
            }
        }
    } 