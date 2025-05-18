package carloswps.gihut.imageliteapi.infra.repository;

import carloswps.gihut.imageliteapi.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}
