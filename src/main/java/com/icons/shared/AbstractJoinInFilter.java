package com.icons.shared;

import jakarta.persistence.criteria.*;
import org.springframework.util.CollectionUtils;
import java.util.Collection;

public abstract class AbstractJoinInFilter<T, D, X> {

    /**
     * Define la propiedad de la entidad Root a la cual se debe hacer el JOIN.
     * Ejemplo: "icons" para CountryEntity, "countries" para IconEntity.
     */
    protected abstract String getJoinProperty();

    /**
     * Retorna la colección de ID (la lista de filtrado) del DTO de filtro.
     * Ejemplo: filters.iconsNames() o filters.countries().
     */
    protected abstract Collection<String> getFilterIds(D filters);

    /**
     * Metodo central que realiza el JOIN y aplica el predicado IN.
     * @param filters El DTO de filtro (CountryFilterRequestDTO o IconFilterRequestDTO).
     * @param root La entidad principal de la consulta (CountryEntity o IconEntity).
     * @param criteriaBuilder El constructor de criterios.
     * @return Predicate IN o null.
     */
    public Predicate apply(D filters, Root<T> root, CriteriaBuilder criteriaBuilder) {
        Collection<String> filterIds = getFilterIds(filters);

        if (CollectionUtils.isEmpty(filterIds)) {
            return null;
        }

        // 1. Realizar el JOIN genérico usando la propiedad definida por la subclase
        Join<X, T> join = root.join(getJoinProperty(), JoinType.INNER);

        // 2. Aplicar el predicado IN sobre la columna "id" de la entidad unida
        Expression<String> joinedId = join.get("id");

        return joinedId.in(filterIds);
    }
}