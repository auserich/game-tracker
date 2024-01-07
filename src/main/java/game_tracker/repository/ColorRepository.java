package game_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import game_tracker.model.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {

}
