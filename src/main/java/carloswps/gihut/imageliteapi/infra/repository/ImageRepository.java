package carloswps.gihut.imageliteapi.infra.repository;

import carloswps.gihut.imageliteapi.domain.entity.Image;
import carloswps.gihut.imageliteapi.domain.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.List;

import static carloswps.gihut.imageliteapi.infra.repository.specs.GenericSpecs.Specification;
import static carloswps.gihut.imageliteapi.infra.repository.specs.ImagesSpecs.*;
import static org.springframework.data.jpa.domain.Specification.anyOf;
import static org.springframework.data.jpa.domain.Specification.where;

public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {

    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query) {
        Specification<Image> spec = where(Specification());

        if (extension != null) {
            spec = spec.and(extensionEqual(extension));
        }

        if (StringUtils.hasText(query)) {
            Specification<Image> nameOrTagsLike = anyOf(nameLike(query), tagsLike(query));
            spec = spec.and(nameOrTagsLike);
        }
        return findAll(spec);
    }
}
