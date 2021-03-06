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
package co.edu.uniandes.isis2503.nosqljpa.model.dto.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mf.mena
 */
@XmlRootElement
public class HorarioDTO2 {
     private String id;

    private String inicio;
    private String fin;
    
    private String inmueble;
    private String usuario;

    public String getInmueble() {
        return inmueble;
    }

    public void setInmueble(String inmueble) {
        this.inmueble = inmueble;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public HorarioDTO2() {
    }

    public HorarioDTO2(String id,String inicio,String fin,String u,String i) {
        this.id = id;
        this.inicio=inicio;
        this.fin=fin;
        this.usuario=u;
        this.inmueble=i;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }
      public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }
    
    public HorarioDTO convert(){
        HorarioDTO h=new HorarioDTO();
        Date inic=new Date(2018, 05, 11, Integer.parseInt(this.inicio.substring(0, 2)), Integer.parseInt(this.inicio.substring(3, 5)));
        Date fin=new Date(2018, 05, 11, Integer.parseInt(this.fin.substring(0, 2)), Integer.parseInt(this.fin.substring(3, 5)));
        h.setInicio(inic);
        h.setFin(fin);
        h.setUsuario(this.usuario);
        h.setInmueble(this.inmueble);
        return h;
    } 
}

