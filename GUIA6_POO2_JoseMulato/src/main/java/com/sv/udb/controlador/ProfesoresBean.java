/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.ProfesoresFacadeLocal;
import com.sv.udb.modelo.Profesores;
import com.sv.udb.utils.LOG4J;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Laboratorio
 */
@Named(value = "profesoresBean")
@ViewScoped
public class ProfesoresBean implements Serializable{

    @EJB
    private ProfesoresFacadeLocal FCDEProfesores;
    private Profesores objeProfe;
    private List<Profesores> listProfe;
    private boolean guardar;
    private LOG4J log4j;

    public Profesores getObjeProfe() {
        return objeProfe;
    }

    public void setObjeProfe(Profesores objeProfe) {
        this.objeProfe = objeProfe;
    }

    public List<Profesores> getListProfe() {
        return listProfe;
    }

    public void setListProfe(List<Profesores> listProfe) {
        this.listProfe = listProfe;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public void setGuardar(boolean guardar) {
        this.guardar = guardar;
    }
    
    
    /**
     * Creates a new instance of ProfesoresBean
     */
    public ProfesoresBean() {
    }
    
    @PostConstruct
    public void init()
    {
        this.limpForm();
        this.consTodo();
        log4j = new LOG4J();
    }
    
    public void limpForm()
    {
        this.objeProfe = new Profesores();
        this.guardar = true;        
    }
    
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEProfesores.create(this.objeProfe);
            this.listProfe.add(this.objeProfe);
            log4j.info("Profesor Registrado: "+objeProfe.getNombProf()+" "+objeProfe.getApelProf());
            log4j.debug("Depuracion correcta:"+objeProfe);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            log4j.warm("Evento inesperado:" +objeProfe);
            log4j.fatal("Evento fatal:" +objeProfe);
            log4j.error("Error al Ingresar el profesor:"+ex);
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar ')");
        }
        finally
        {
            
        }
    }
    
    public void modi()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            this.listProfe.remove(this.objeProfe); //Limpia el objeto viejo
            FCDEProfesores.edit(this.objeProfe);
            this.listProfe.add(this.objeProfe); //Agrega el objeto modificado
            log4j.info("Profesor Modificado: "+objeProfe.getNombProf()+" "+objeProfe.getApelProf());
            log4j.debug("Depuracion correcta:"+objeProfe);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            
            log4j.warm("Evento inesperado:" +objeProfe);
            log4j.fatal("Evento fatal:" +objeProfe);
            log4j.error("Error al Modificar el profesor:"+ex);
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar ')");
        }
        finally
        {
            
        }
    }
    
    public void elim()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEProfesores.remove(this.objeProfe);
            this.listProfe.remove(this.objeProfe);
            log4j.info("Profesor Eliminado: "+objeProfe.getNombProf()+" "+objeProfe.getApelProf());
            log4j.debug("Depuracion correcta:"+objeProfe);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            log4j.warm("Evento inesperado:" +objeProfe);
            log4j.fatal("Evento fatal:" +objeProfe);
            log4j.error("Error al Eliminado el profesor:"+ex);
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
        }
        finally
        {
            
        }
    }
    
    public void consTodo()
    {
        try
        {
            this.listProfe = FCDEProfesores.findAll();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            
        }
    }
    
    public void cons()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiProfePara"));
        try
        {
            this.objeProfe = FCDEProfesores.find(codi);
            this.guardar = false;
            log4j.info("Profesor Consultado: "+objeProfe.getNombProf()+" "+objeProfe.getApelProf());
            log4j.debug("Depuracion correcta:"+objeProfe);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                    String.format("%s %s", this.objeProfe.getNombProf(), this.objeProfe.getApelProf()) + "')");
        }
        catch(Exception ex)
        {
            log4j.warm("Evento inesperado:" +objeProfe);
            log4j.fatal("Evento fatal:" +objeProfe);
            log4j.error("Error al Eliminado el profesor:"+ex);
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
        }
        finally
        {
            
        }
    }
}
