/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.utils;
import org.apache.log4j.*;

/**
 *
 * @author joseph
 */
public class LOG4J {
    private static Logger log = Logger.getLogger(LOG4J.class);
    public LOG4J() {
       try{
           PropertyConfigurator.configure(this.getClass().getClassLoader().getResource("/log4j.properties").getPath());
          }
       catch(Exception ex)
          {
           System.out.println("Error con LOG: "+ex);
          }
        }
     //Mensajes de depuracion    
    public void debug(String mens){
       try{
          log.debug(mens);
          }
        catch(Exception ex)
          {
            System.out.println("Error con LOG: "+ex);
          }
        }
     //Mensajes de informacion de progreso y estado
     public void info(String mens){
        try{
            log.info(mens);
            System.out.println("LOG Generado");
        }
        catch(Exception ex)
        {
            System.out.println("Error con LOG: "+ex);
        }
       }
     //Advertencia acerca de un evento inesperado
     public void warm(String mens){
        try{
            log.warn(mens);
        }
        catch(Exception ex)
        {
            System.out.println("Error con LOG: "+ex);
        }
       }
     //Mensaje de informacion sobre un error grave de desbordaje
     public void error(String mens){
        try{
            log.error(mens);
        }
        catch(Exception ex)
        {
            System.out.println("Error con LOG: "+ex);
        }
       }
     //Mensajes de funcionamiento erroneo de la aplicacion
     public void fatal(String mens){
        try{
            log.fatal(mens);
        }
        catch(Exception ex)
        {
            System.out.println("Error con LOG: "+ex);
        }
      }    
}
