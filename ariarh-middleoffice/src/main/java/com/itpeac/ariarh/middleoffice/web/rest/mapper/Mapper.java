package com.itpeac.ariarh.middleoffice.web.rest.mapper;

public interface Mapper<Entity, Dto>  {

    Dto fromEntityToDto(Entity entity);
    Entity fromDTOtoEntity(Dto dto);
}
