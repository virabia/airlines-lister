package edu.elte.airlines.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import edu.elte.airlines.dao.interfaces.ICrudDao;
import edu.elte.airlines.domain.ModelInterface;
import edu.elte.airlines.dto.DtoInterface;
import edu.elte.airlines.service.interfaces.ICrudService;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractCrudServiceImpl<EntityType extends ModelInterface<IdType>, DtoType extends DtoInterface<IdType>, IdType extends Serializable>
        implements ICrudService<EntityType, DtoType, IdType>{

    @Autowired
    private ConversionService conversionService;

    private final ICrudDao<EntityType, IdType> dao;
    private final Class<EntityType> entityTypeClass;
    private final Class<DtoType> dtoTypeClass;

    public AbstractCrudServiceImpl(Class<EntityType> entityTypeClass, Class<DtoType> dtoTypeClass, ICrudDao<EntityType, IdType> dao) {
        this.dao = dao;
        this.entityTypeClass = entityTypeClass;
        this.dtoTypeClass = dtoTypeClass;
    }

    @Override
    public IdType create(DtoType dto) {
        Objects.requireNonNull(dto, "The DTO to be created must not be null");
        EntityType entity = conversionService.convert(dto, entityTypeClass);
        dao.createEntity(entity);
        return entity.getId();
    }

    @Override
    public void delete(DtoType dto) {
        Objects.requireNonNull(dto, "The DTO to be deleted must not be null");
        EntityType entity = conversionService.convert(dto, entityTypeClass);
        dao.deleteEntity(entity);
    }

    @Override
    public void update(DtoType dto) {
        Objects.requireNonNull(dto, "The DTO to be updated must not be null");
        EntityType entity = conversionService.convert(dto, entityTypeClass);
        dao.updateEntity(entity);
    }


    @Override
    public DtoType findById(IdType id) {
        Objects.requireNonNull(id, "The id to be found must not be null");
        EntityType entity = dao.findById(id);
        DtoType result = conversionService.convert(entity, dtoTypeClass);
        return result;
    }

    @Override
    public boolean exists(IdType id) {
        return dao.exists(id);
    }

    @Override
    public List<DtoType> list() {
        List<EntityType> entityList = dao.list();
        List<DtoType> result = entityList.stream().map(entity -> conversionService.convert(entity, dtoTypeClass)).collect(Collectors.toList());
        return result;
    }
}