/*
 * The MIT License
 *
 * Copyright 2018 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
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

import co.edu.uniandes.isis2503.nosqljpa.interfaces.ISeguridadLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.ConjuntoLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.SeguridadLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.ConjuntoDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.SeguridadDTO;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author mf.mena
 */
@Path("/seguridad")
@Produces(MediaType.APPLICATION_JSON)
public class SeguridadService {
    private final SeguridadLogic seguridadLogic;
    private final ConjuntoLogic conjuntoLogic;

    public SeguridadService() {
        this.seguridadLogic = new SeguridadLogic();
        this.conjuntoLogic = new ConjuntoLogic();
    }
    
    @POST
    public SeguridadDTO add(SeguridadDTO dto) {
        return seguridadLogic.add(dto);
    }
    
    @GET
    public List<SeguridadDTO> all() {
        return seguridadLogic.all();
    }
    
    @PUT
    public SeguridadDTO update(SeguridadDTO dto) {
        return seguridadLogic.update(dto);
    }
    
   @GET
   @Path("/{correo}")
   public List<String> porcorreo(@PathParam("correo")String correo) {
        List<String> x= null;
        SeguridadDTO s = seguridadLogic.findCorreo(correo);
        if(s!=null){
        ConjuntoDTO c = conjuntoLogic.find(s.getConjunto());
         x=c.getInmuebles();
        }
        return x;
   } 
}
