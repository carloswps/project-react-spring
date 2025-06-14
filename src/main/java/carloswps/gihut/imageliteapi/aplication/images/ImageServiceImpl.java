package carloswps.gihut.imageliteapi.aplication.images;

import carloswps.gihut.imageliteapi.domain.entity.Image;
import carloswps.gihut.imageliteapi.domain.enums.ImageExtension;
import carloswps.gihut.imageliteapi.domain.services.ImageServices;
import carloswps.gihut.imageliteapi.infra.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    @Override
    public Optional<Image> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Image> search(ImageExtension extension, String query) {
        if (extension != null && !isValidExtension(extension)) {
            return Collections.emptyList();
        }
        return repository.findByExtensionAndNameOrTagsLike(extension, query)
                .stream()
                .limit(10)
                .collect(Collectors.toList());
    }

    private boolean isValidExtension(ImageExtension extension) {
        try {
            return Arrays.asList(ImageExtension.values()).contains(extension);
        } catch (Exception e) {
            return false;
        }
    }


    private void validateImage(Image image) {
        if (image == null || image.getFile() == null || image.getTags().isEmpty()) {
            throw new IllegalArgumentException("Please provide a valid image.");
        }
    }

    private String formatFileTypeImage(long bytes) {
        double megabytes = bytes / (1024.0 * 1024.0);
        return String.format("%.2f MB", megabytes);
    }
}
