/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.GruposFacadeLocal;
import com.sv.udb.modelo.Grupos;
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
 * @author Mauricio
 */
@Named(value = "gruposBean")
@ViewScoped
public class GruposBean implements Serializable{

    @EJB
    private GruposFacadeLocal FCDEGrupos;
    
    private List<Grupos> listGrup;
    private Grupos objeGrup;
    private boolean guardar;
    private LOG4J log4j;
    
    /**
     * Creates a new instance of GruposBean
     */
    public GruposBean() {
    }
    
    @PostConstruct
    public void init()
    {
        this.listGrup = FCDEGrupos.findAll();
        this.limpForm();
        log4j= new LOG4J();
    }

    public GruposFacadeLocal getFCDEGrupos() {
        return FCDEGrupos;
    }

    public void setFCDEGrupos(GruposFacadeLocal FCDEGrupos) {
        this.FCDEGrupos = FCDEGrupos;
    }

    public Grupos getObjeGrup() {
        return objeGrup;
    }

    public void setObjeGrup(Grupos objeGrup) {
        this.objeGrup = objeGrup;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public void setGuardar(boolean guardar) {
        this.guardar = guardar;
    }

    public List<Grupos> getListGrup() {
        return listGrup;
    }

    public void setListGrup(List<Grupos> listGrup) {
        this.listGrup = listGrup;
    }
    
    public void limpForm()
    {
        this.objeGrup = new Grupos();
        this.guardar = true;        
    }
    
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEGrupos.create(this.objeGrup);
            this.listGrup.add(this.objeGrup);
            log4j.info("Grupo Registrado: "+objeGrup.getNombGrup());
            log4j.debug("Depuracion correcta:"+objeGrup);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            
            log4j.warm("Evento inesperado:" +objeGrup);
            log4j.fatal("Evento fatal:" +objeGrup);
            log4j.error("Error al Ingresar el grupo:"+ex);
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
            this.listGrup.remove(this.objeGrup); //Limpia el objeto viejo
            FCDEGrupos.edit(this.objeGrup);
            this.listGrup.add(this.objeGrup); //Agrega el objeto modificado
            log4j.info("Grupo Modificado: "+objeGrup.getNombGrup());
            log4j.debug("Depuracion correcta:"+objeGrup);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            log4j.warm("Evento inesperado:" +objeGrup);
            log4j.fatal("Evento fatal:" +objeGrup);
            log4j.error("Error al Modificar el grupo:"+ex);
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
            
            FCDEGrupos.remove(this.objeGrup);
            this.listGrup.remove(this.objeGrup);
            log4j.info("Grupo Eliminado: "+objeGrup.getNombGrup());
            log4j.debug("Depuracion correcta:"+objeGrup);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            log4j.warm("Evento inesperado:" +objeGrup);
            log4j.fatal("Evento fatal:" +objeGrup);
            log4j.error("Error al Eliminar el grupo:"+ex);
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
            this.listGrup = FCDEGrupos.findAll();
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
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiGrupPara"));
        try
        {
            this.objeGrup = FCDEGrupos.find(codi);
            this.guardar = false;
            log4j.info("Grupo Consultado: "+objeGrup.getNombGrup());
            log4j.debug("Depuracion correcta:"+objeGrup);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                    String.format("%s", this.objeGrup.getNombGrup()) + "')");
        }
        catch(Exception ex)
        {
            log4j.warm("Evento inesperado:" +objeGrup);
            log4j.fatal("Evento fatal:" +objeGrup);
            log4j.error("Error al Consultar el grupo:"+ex);
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
        }
        finally
        {
            
        }
    }
}
