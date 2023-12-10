package game_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import game_tracker.model.Commander;

@Repository
public interface CommanderRepository extends JpaRepository<Commander, Integer>{

}
