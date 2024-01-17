package game_tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import game_tracker.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer>{

	public Optional<Player> findByName(String name);
	
	@Query(value = "SELECT * FROM player WHERE player.user_id = ?1", nativeQuery = true)
	List<Player> getAllPlayersByUser(int id);
	
	@Query(value = "SELECT * FROM player WHERE player.user_id = ?1 AND player.name = ?2", nativeQuery = true)
    Optional<Player> findPlayerByUserAndName(int userId, String playerName);
}
