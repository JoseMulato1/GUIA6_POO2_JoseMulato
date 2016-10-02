/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.GruposAlumnosFacadeLocal;
import com.sv.udb.modelo.GruposAlumnos;
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
 * @author REGISTRO
 */
@Named(value = "gruposAlumnosBean")
@ViewScoped
public class GruposAlumnosBean implements Serializable{
    @EJB
    private GruposAlumnosFacadeLocal FCDEGruposAlumnos;    
    private GruposAlumnos objeGrupAlum;
    private List<GruposAlumnos> listGrupAlum;
    private boolean guardar;
    private LOG4J log4j;
    
    /**
     * Creates a new instance of GruposAlumnosBean
     */

    public GruposAlumnos getObjeGrupAlum() {
        return objeGrupAlum;
    }

    public void setObjeGrupAlum(GruposAlumnos objeGrupAlum) {
        this.objeGrupAlum = objeGrupAlum;
    }

    public List<GruposAlumnos> getListGrupAlum() {
        return listGrupAlum;
    }

    public void setListGrupAlum(List<GruposAlumnos> listGrupAlum) {
        this.listGrupAlum = listGrupAlum;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public void setGuardar(boolean guardar) {
        this.guardar = guardar;
    }
    public GruposAlumnosBean() {
        
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
        this.objeGrupAlum = new GruposAlumnos();
        this.guardar = true;        
    }
    
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDEGruposAlumnos.create(this.objeGrupAlum);
            this.listGrupAlum.add(this.objeGrupAlum);
            log4j.info("Ingresando informacion Alumno "+objeGrupAlum.getCodiAlum().getNombAlum()+" "+objeGrupAlum.getCodiAlum().getApelAlum()+" Ingresado en el grupo "+ " "+objeGrupAlum.getCodiGrup().getNombGrup()+"");
            log4j.debug("Depuracion correcta:"+objeGrupAlum);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            
            log4j.warm("Evento inesperado:" +objeGrupAlum);
            log4j.fatal("Evento fatal:" +objeGrupAlum);
            log4j.error("Error al Ingresar el Grupo-Alumno:"+ex);
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
            this.listGrupAlum.remove(this.objeGrupAlum); //Limpia el objeto viejo
            FCDEGruposAlumnos.edit(this.objeGrupAlum);
            this.listGrupAlum.add(this.objeGrupAlum); //Agrega el objeto modificado
            log4j.info("Modificada informacion: Alumno "+objeGrupAlum.getCodiAlum().getNombAlum()+" "+objeGrupAlum.getCodiAlum().getApelAlum()+"grupo "+ " "+objeGrupAlum.getCodiGrup().getNombGrup()+"");
            log4j.debug("Depuracion correcta:"+objeGrupAlum);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            log4j.warm("Evento inesperado:" +objeGrupAlum);
            log4j.fatal("Evento fatal:" +objeGrupAlum);
            log4j.error("Error al Modificar el Grupo-Alumno:"+ex);
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
            FCDEGruposAlumnos.remove(this.objeGrupAlum);
            this.listGrupAlum.remove(this.objeGrupAlum);
            log4j.info("Eliminada informacion: Alumno "+objeGrupAlum.getCodiAlum().getNombAlum()+" "+objeGrupAlum.getCodiAlum().getApelAlum()+"grupo "+ " "+objeGrupAlum.getCodiGrup().getNombGrup()+"");
            log4j.debug("Depuracion correcta:"+objeGrupAlum);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            log4j.warm("Evento inesperado:" +objeGrupAlum);
            log4j.fatal("Evento fatal:" +objeGrupAlum);
            log4j.error("Error al Eliminar el Grupo-Alumno:"+ex);
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
            this.listGrupAlum = FCDEGruposAlumnos.findAll();
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
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiGrupAlumPara"));
        try
        {
            this.objeGrupAlum = FCDEGruposAlumnos.find(codi);
            this.guardar = false;
            log4j.info("Consultada informacion: Alumno "+objeGrupAlum.getCodiAlum().getNombAlum()+" "+objeGrupAlum.getCodiAlum().getApelAlum()+"grupo "+ " "+objeGrupAlum.getCodiGrup().getNombGrup()+"");
            log4j.debug("Depuracion correcta:"+objeGrupAlum);
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado')");
        }
        catch(Exception ex)
        {
            log4j.warm("Evento inesperado:" +objeGrupAlum);
            log4j.fatal("Evento fatal:" +objeGrupAlum);
            log4j.error("Error al Consultar el Grupo-Alumno:"+ex);
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
        }
        finally
        {
            
        }
    }
    
}
