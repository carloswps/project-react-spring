package carloswps.gihut.imageliteapi.infra.repository;

import carloswps.gihut.imageliteapi.domain.entity.Image;
import carloswps.gihut.imageliteapi.domain.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {

    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query) {
        Specification<Image> conjuction = (root, q, cb) -> cb.conjunction();
        Specification<Image> spec = Specification.where(conjuction);

        if (extension != null) {
            Specification<Image> extensionEqual = (root, q, cb) ->
                    cb.equal(root.get("extension"), extension);
            spec = spec.and(extensionEqual);
        }
        if (StringUtils.hasText(query)) {
            Specification<Image> nameLike = (root, q, cb) ->
                    cb.like(cb.upper(root.get("Name")), "%" + query.toUpperCase() + "%");
            Specification<Image> tagLike = (root, q, cb) ->
                    cb.or(
                            cb.like(cb.upper(root.get("tags")), "%" + query.toUpperCase() + "%"),
                            cb.like(cb.upper(root.get("name")), "%" + query.toUpperCase() + "%")
                    );

            Specification<Image> nameOrTagsLike = Specification.anyOf(nameLike, tagLike);
            spec = spec.and(nameOrTagsLike);
        }
        return findAll(spec);
    }
}
