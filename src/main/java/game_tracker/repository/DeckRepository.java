package game_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import game_tracker.model.Deck;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Integer> {

	@Query(value = "SELECT * FROM deck WHERE user_id = ?1", nativeQuery = true)
	public List<Deck> getAllDecksFromUserById(int userId);
	
	@Query(value = "SELECT * FROM deck WHERE user_id = ?1 AND commander_id = ?2", nativeQuery = true)
	public Deck getDeckByUserAndCommander(int userId, int commanderId);
}
