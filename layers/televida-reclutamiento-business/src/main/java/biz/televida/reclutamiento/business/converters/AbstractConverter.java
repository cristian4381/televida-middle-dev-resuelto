/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biz.televida.reclutamiento.business.converters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author genaro
 * @param <Entity>
 * @param <Dto>
 */
public abstract class AbstractConverter<Entity, Dto> {

    public abstract Entity fromDto(Dto dto);

    public abstract Dto fromEntity(Entity entity);

    public List<Entity> fromDto(List<Dto> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(dto -> fromDto(dto)).collect(Collectors.toList());
    }

    public List<Dto> fromEntity(List<Entity> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(entity -> fromEntity(entity)).collect(Collectors.toList());

    }

    public List<Dto> fromEntity(Iterator<Entity> iterator) {
        if (iterator == null) {
            return null;
        }

        List<Entity> entities = new ArrayList<>();
        iterator.forEachRemaining(entities::add);

        return entities.stream()
                .map(entity -> fromEntity(entity)).collect(Collectors.toList());

    }
}
