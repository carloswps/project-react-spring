package carloswps.gihut.imageliteapi.aplication.images;

import carloswps.gihut.imageliteapi.domain.entity.Image;
import carloswps.gihut.imageliteapi.domain.services.ImageServices;
import carloswps.gihut.imageliteapi.infra.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageServices {
    private static final Logger logger = Logger.getLogger(ImageServiceImpl.class.getName());
    private final ImageRepository repository;

    @Override
    @Transactional
    public Image save(Image image) {
        validateImage(image);
        try {
            Image savedImage = repository.save(image);
            logger.info("Image saved successfully");
            return savedImage;
        } catch (Exception e) {
            logger.severe("Error saving image: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private void validateImage(Image image) {
        if (image == null || image.getFile() == null || image.getTags().isEmpty()) {
            throw new IllegalArgumentException("Please provide a valid image.");
        }
    }
}
