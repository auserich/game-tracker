package game_tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import game_tracker.model.Deck;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Integer> {

	@Query(value = "SELECT * FROM deck WHERE user_id = ?1", nativeQuery = true)
	public List<Deck> getAllDecksFromUserById(int userId);
	
	@Query(value = "SELECT * FROM deck JOIN user ON deck.user_id = user.id WHERE user.username = ?1", nativeQuery = true)
	public List<Deck> getAllDecksFromUserByUsername(String username);
	
	@Query(value = "SELECT * FROM deck WHERE name = ?1", nativeQuery = true)
	public Optional<Deck> findByName(String name);
	
	@Query(value = "SELECT * FROM deck WHERE user_id = ?1 AND commander_id = ?2", nativeQuery = true)
	public Optional<Deck> getDeckByUserAndCommander(int userId, int commanderId);
	
	@Query(value = "SELECT * FROM game WHERE game.deck1_name = ?1 OR game.deck2_name = ?1 OR game.deck3_name = ?1 OR game.deck4_name = ?1 ORDER BY game.date ASC, game.game_number ASC", nativeQuery = true)
	public List<Deck> findByDeckNameOrderByDateAndGameNumber(String name);
	
	// TODO: Figure out why this isn't working correctly
	@Query(value = "SELECT COUNT(*) FROM game " +
	        "WHERE ?1 IN (game.deck1_name, game.deck2_name, game.deck3_name, game.deck4_name) " +
	        "AND NOT EXISTS (" +
	        "    SELECT 1 FROM deck " +
	        "    WHERE ?1 IN (deck.name) " +
	        "    AND game.winner_id = deck.id)", nativeQuery = true)
	Integer countLossesForDeckByName(String name);
	
	@Query(value = "SELECT COUNT(*) FROM game " +
			"WHERE ?1 IN (game.deck1_name, game.deck2_name, game.deck3_name, game.deck4_name)", nativeQuery = true)
	Integer countTotalGamesForDeckByName(String name);
	
	@Query(value = "SELECT COUNT(*) FROM game WHERE winner_name = ?1", nativeQuery = true)
	Integer countWinsForDeckByName(String name);
}
