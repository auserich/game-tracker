package game_tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import game_tracker.model.Commander;

@Repository
public interface CommanderRepository extends JpaRepository<Commander, Integer>{
	
	public Optional<Commander> findByName(String name);
}
