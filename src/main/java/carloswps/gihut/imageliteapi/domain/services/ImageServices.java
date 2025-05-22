package carloswps.gihut.imageliteapi.domain.services;

import carloswps.gihut.imageliteapi.domain.entity.Image;

import java.util.Optional;

public interface ImageServices {
    Image save(Image image);

    Optional<Image> getById(String id);
}
