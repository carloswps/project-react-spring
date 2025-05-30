package carloswps.gihut.imageliteapi.infra.repository.specs;

import org.springframework.data.jpa.domain.Specification;

public class GenericSpecs {
    private GenericSpecs() {
    }

    public static <T> Specification<T> Specification() {
        return (root, q, cb) -> cb.conjunction();
    }
}
