package carloswps.gihut.imageliteapi.aplication.images;

import carloswps.gihut.imageliteapi.domain.entity.Image;
import carloswps.gihut.imageliteapi.domain.enums.ImageExtension;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class ImagesMapper {

    private String formatFileTypeImage(long bytes) {
        if (bytes < 1024) {
            return bytes + "B";
        } else if (bytes < 1024 * 1024) {
            double kilobytes = bytes / 1024.0;
            return String.format("%.2f KB", kilobytes);
        } else {
            double megabytes = bytes / (1024.0 * 1024.0);
            return String.format("%.2f MB", megabytes);
        }
    }

    public Image mapToImage(MultipartFile file, String name, List<String> tags) throws IOException {
        return Image.builder()
                .name(name)
                .tags(String.join(",", tags))
                .size(file.getSize())
                .extension(ImageExtension.valueOf(MediaType.valueOf(file.getContentType())))
                .file(file.getBytes())
                .build();
    }

    public ImageDTO imageToDto(Image image, String url) {
        return ImageDTO.builder()
                .url(url)
                .extension(image.getExtension().name())
                .name(image.getName())
                .size(formatFileTypeImage(image.getSize()))
                .uploadDate(image.getUploadDate().toLocalDate())
                .build();
    }
}
