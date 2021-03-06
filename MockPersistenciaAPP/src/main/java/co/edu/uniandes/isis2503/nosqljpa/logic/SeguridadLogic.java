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
package co.edu.uniandes.isis2503.nosqljpa.logic;

import static co.edu.uniandes.isis2503.nosqljpa.model.dto.converter.SeguridadConverter.CONVERTER;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.ISeguridadLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.SeguridadDTO;
import co.edu.uniandes.isis2503.nosqljpa.persistence.SeguridadPersistence;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author mf.mena
 */
public class SeguridadLogic implements ISeguridadLogic{
    
    private final SeguridadPersistence persistence;

    public SeguridadLogic() {
        this.persistence= new SeguridadPersistence();
    }
    
    @Override
    public SeguridadDTO add(SeguridadDTO dto) {
         if(dto.getId()==null){
            dto.setId(UUID.randomUUID().toString());
         }
        SeguridadDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public SeguridadDTO update(SeguridadDTO dto) {
        SeguridadDTO result = CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public SeguridadDTO find(String id) {
        return CONVERTER.entityToDto(persistence.find(id));
    }
    
    public SeguridadDTO findCorreo(String correo){
        return CONVERTER.entityToDto(persistence.findCorreo(correo));
    }

    @Override
    public List<SeguridadDTO> all() {
        return CONVERTER.listEntitiesToListDTOs(persistence.all());
    }

    @Override
    public Boolean delete(String id) {
        return persistence.delete(id);
    }

   
}
