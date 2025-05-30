package carloswps.gihut.imageliteapi.infra.repository.specs;

import carloswps.gihut.imageliteapi.domain.entity.Image;
import carloswps.gihut.imageliteapi.domain.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;

public class ImagesSpecs {
    private ImagesSpecs() {
    }

    public static Specification<Image> extensionEqual(ImageExtension extension) {
        return (root, query, cb) -> cb.equal(root.get("extension"), extension);
    }

    public static Specification<Image> nameLike(String name) {
        return (root, query, cb)
                -> cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%");

    }

    public static Specification<Image> tagsLike(String tags) {
        return (root, query, cb)
                -> cb.like(cb.upper(root.get("tags")), "%" + tags.toUpperCase() + "%");
    }

    public static Specification<Image> nameOrTagsLike(String name, String tags) {
        return Specification.anyOf(nameLike(name), tagsLike(tags));
    }
}
