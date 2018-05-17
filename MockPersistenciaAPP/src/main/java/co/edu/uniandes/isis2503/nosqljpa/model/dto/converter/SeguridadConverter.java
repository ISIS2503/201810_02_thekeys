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
package co.edu.uniandes.isis2503.nosqljpa.model.dto.converter;

import co.edu.uniandes.isis2503.nosqljpa.interfaces.ISeguridadConverter;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.SeguridadDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.SeguridadEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mf.mena
 */
public class SeguridadConverter implements ISeguridadConverter{

    public static ISeguridadConverter CONVERTER = new SeguridadConverter();
    
    public SeguridadConverter() {
    }
    
    @Override
    public SeguridadDTO entityToDto(SeguridadEntity entity){
        SeguridadDTO dto = new SeguridadDTO();
        dto.setId(entity.getId());
        dto.setConjunto(entity.getConjunto());
        dto.setCorreo(entity.getCorreo());
        return dto;
    }
    
    @Override
    public SeguridadEntity dtoToEntity(SeguridadDTO dto){
        SeguridadEntity entity=new SeguridadEntity();
        entity.setId(dto.getId());
        entity.setConjunto(dto.getConjunto());
        entity.setCorreo(dto.getCorreo());
        return entity;
    }
    
    @Override
    public List<SeguridadEntity> listDTOsToListEntities(List<SeguridadDTO> dtos){
        List<SeguridadEntity> entities=new ArrayList<>();
        for(SeguridadDTO s:dtos){
            entities.add(dtoToEntity(s));
        }
        return entities;
    }
    
    @Override
    public List<SeguridadDTO> listEntitiesToListDTOs(List<SeguridadEntity> entities){
        List<SeguridadDTO> dtos=new ArrayList<>();
        for(SeguridadEntity s:entities){
            dtos.add(entityToDto(s));
        }

        return dtos;
    }
    
}
