/*
 * The MIT License
 *
 * Copyright 2017 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.auth.AuthorizationFilter.Role;
import co.edu.uniandes.isis2503.nosqljpa.auth.Secured;
import co.edu.uniandes.isis2503.nosqljpa.logic.InmuebleLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.AlarmaLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.InmuebleDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AlarmaDTO;
import com.sun.istack.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IAlarmaLogic;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IHorarioLogic;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IHubLogic;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IInmuebleLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.HorarioLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.HubLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AlarmaDTO2;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.HorarioDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.HorarioDTO2;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.HubDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.InmuebleDTO2;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ca.mendoza968
 */
@Path("/inmuebles")
@Secured({})
@Produces(MediaType.APPLICATION_JSON)
public class InmuebleService {
    
    private final IInmuebleLogic inmuebleLogic;
    private final IAlarmaLogic alarmaLogic;
    private final IHorarioLogic horarioLogic;
    private final IHubLogic hubLogic;

    public InmuebleService() {
        this.inmuebleLogic = new InmuebleLogic();
        this.alarmaLogic = new AlarmaLogic();
        this.hubLogic = new HubLogic();
        this.horarioLogic=new HorarioLogic();
    }

    @POST
    @Secured({Role.yale})
    public InmuebleDTO add(InmuebleDTO2 dto){
        for(AlarmaDTO a:dto.getAlarmas()){
            alarmaLogic.add(a);
        }
        if(dto.getHub()!=null){
            hubLogic.add(dto.getHub());
        }
        return inmuebleLogic.add(dto.convert());
    }
    
    
    @POST
    @Path("{id}/alarmas")
    @Secured({Role.cerradura})
    public AlarmaDTO addAlarma(@PathParam("id") String id, AlarmaDTO dto) {
        InmuebleDTO inmueble = inmuebleLogic.find(id);
        AlarmaDTO result = alarmaLogic.add(dto);
        inmueble.addAlarma(dto.getId());
        update(inmueble);
        return result;
    }
    
    
    @POST
    @Path("{id}/hub")
    @Secured({Role.yale})
    public HubDTO addHub(@PathParam("id") String id, HubDTO dto) {
        InmuebleDTO inmueble = inmuebleLogic.find(id);
        HubDTO result = hubLogic.add(dto);
        inmueble.setHub(dto.getId());
        update(inmueble);
        return result;
    }
    
    @POST
    @Path("{id}/horario")
    @Secured({Role.yale,Role.propietario})
    public HorarioDTO addHorario(@PathParam("id") String id, HorarioDTO2 dto) {
        InmuebleDTO inmueble = inmuebleLogic.find(id);
        HorarioDTO result = horarioLogic.add(dto.convert());
        if(inmueble.getHorarios()==null){
            inmueble.setHorarios(new ArrayList<String>());
        }
        inmueble.addHorario(result.getId());
        update(inmueble);
        return result;
    }

    @PUT
    public InmuebleDTO update(InmuebleDTO dto) {
        return inmuebleLogic.update(dto);
    }

    @GET
    @Path("/{id}")
    public InmuebleDTO find(@PathParam("id") String id) {
        return inmuebleLogic.find(id);
    }
    
    
    
    @GET
    @Path("/{id}/hub")
    public HubDTO findHub(@PathParam("id") String id) {
        HubDTO h;
        InmuebleDTO i= null;
        try{
            i=inmuebleLogic.find(id);
            h=hubLogic.find(i.getHub());
        }catch(Exception e){
            h=null;
        }
        return h;
    }

    @GET
    @Secured({Role.yale,Role.administrador,Role.seguridad})
    public List<InmuebleDTO> all() {
        return inmuebleLogic.all();
    }
    
    @GET
    @Path("/{id}/alarmas")
    public List<AlarmaDTO> findAlarmas(@PathParam("id") String id) {
        List<AlarmaDTO> alarmas=new ArrayList<>();
        InmuebleDTO i=inmuebleLogic.find(id);
        if(i!=null){
            for(String a:i.getAlarmas()){
                alarmas.add(alarmaLogic.find(a));
            }
        }
        return alarmas;
    }
    
    @GET
    @Path("/{id}/alarmas/{mes}")
    public List<AlarmaDTO> findAlarmasPorMes(@PathParam("id") String id,@PathParam("mes") String mes) {
        List<AlarmaDTO> alarmas=new ArrayList<>();
        InmuebleDTO i=inmuebleLogic.find(id);
        if(i!=null){
            for(String a:i.getAlarmas()){
                
                AlarmaDTO aAct=alarmaLogic.find(a);
                    SimpleDateFormat s =new SimpleDateFormat("MM");
                    if(s.format(aAct.getFecha()).equals(mes)){
                    alarmas.add(aAct);
                    }
            }
        }
        return alarmas;
    }

    @DELETE
    @Path("/{id}")
    @Secured({Role.yale})
    public Response delete(@PathParam("id") String id) {
        try {
            inmuebleLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Room was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(InmuebleService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }    
    
     @GET
    @Path("/{id}/open")
    public boolean findApertura(@PathParam("id") String id) {
        InmuebleDTO i=inmuebleLogic.find(id);
        boolean x=false;
        if(i!=null){
            Date h1=new Date(System.currentTimeMillis());
            for(String h:i.getHorarios()){
                HorarioDTO hAct=horarioLogic.find(h);
                if(hAct!=null){
                    SimpleDateFormat s =new SimpleDateFormat("hh:mm");
                    int inic=Integer.parseInt(s.format(hAct.getInicio()).substring(0, 2)+s.format(hAct.getInicio()).substring(3, 5));
                    int intento=Integer.parseInt(s.format(h1).substring(0, 2)+s.format(h1).substring(3, 5));
                    int fin=Integer.parseInt(s.format(hAct.getFin()).substring(0, 2)+s.format(hAct.getFin()).substring(3, 5));
                    if(inic>=intento && intento<=fin){
                        x=true;
                    }
                }    
            }
        }
        return x;
    }

    
}
