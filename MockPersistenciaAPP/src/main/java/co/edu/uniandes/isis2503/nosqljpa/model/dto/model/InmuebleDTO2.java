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
package co.edu.uniandes.isis2503.nosqljpa.model.dto.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ca.mendoza968
 */
@XmlRootElement
public class InmuebleDTO2 {
     @Id
    private String id;
    private String direccion;
    private String cerradura;
    private List<AlarmaDTO> alarmas;
    private HubDTO hub;
    private List<HorarioDTO> horarios;

    public List<HorarioDTO> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioDTO> horarios) {
        this.horarios = horarios;
    }
    
    public InmuebleDTO2() {
        this.alarmas= new ArrayList<>();
        this.horarios=new ArrayList<>();
    }

    public InmuebleDTO2(String id, String direccion, String cerradura, List<AlarmaDTO> alarmas, HubDTO hub,List<HorarioDTO> horarios) {
        this.id = id;
        this.direccion = direccion;
        this.cerradura = cerradura;
        this.alarmas = alarmas;
        this.hub = hub;
        this.horarios=horarios;
    }

    public HubDTO getHub() {
        return hub;
    }

    public void setHub(HubDTO hub) {
        this.hub = hub;
    }

    

    public List<AlarmaDTO> getAlarmas() {
        return alarmas;
    }

    public void setAlarmas(List<AlarmaDTO> alarmas) {
        this.alarmas = alarmas;
    }

    public String getCerradura() {
        return cerradura;
    }

    public void setCerradura(String cerradura) {
        this.cerradura = cerradura;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void addAlarma(AlarmaDTO alarma){
        this.alarmas.add(alarma);
    }
    
    public InmuebleDTO convert(){
        InmuebleDTO i =new InmuebleDTO();
        i.setCerradura(this.cerradura);
        i.setDireccion(this.direccion);
        i.setId(this.id);
        for(AlarmaDTO a:this.alarmas){
            i.addAlarma(a.getId());
        }
        if(this.hub!=null){
            i.setHub(this.hub.getId());
        }
        for(HorarioDTO h:this.horarios){
            i.addHorario(h.getId());
        }
        return i;
    }
}
