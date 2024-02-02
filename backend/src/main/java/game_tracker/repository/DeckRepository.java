package game_tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import game_tracker.model.Deck;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Integer> {

	@Query(value = "SELECT * FROM deck WHERE player_id = ?1", nativeQuery = true)
	public List<Deck> getAllDecksFromPlayerById(int playerId);
	
	@Query(value = "SELECT * FROM deck JOIN user ON deck.player_id = player.id WHERE player.playerName = ?1", nativeQuery = true)
	public List<Deck> getAllDecksFromPlayerByPlayerName(String playerName);
	
	@Query(value = "SELECT * FROM deck WHERE name = ?1", nativeQuery = true)
	public Optional<Deck> findByName(String name);
	
	@Query(value = "SELECT * FROM deck WHERE player_id = ?1 AND commander_id = ?2", nativeQuery = true)
	public Optional<Deck> getDeckByPlayerAndCommander(int playerId, int commanderId);
	
	// TODO: Figure out why this isn't working correctly
	@Query(value = "SELECT COUNT(*) FROM game " +
	        "WHERE ?1 IN (game.deck1_name, game.deck2_name, game.deck3_name, game.deck4_name) " +
	        "AND NOT EXISTS (" +
	        "    SELECT 1 FROM deck " +
	        "    WHERE ?1 IN (deck.name) " +
	        "    AND game.winner_id = deck.id)", nativeQuery = true)
	Integer countLossesForDeckByName(String name);
	
	@Query(value = "SELECT COUNT(*) FROM game " +
			"WHERE ?1 IN (game.deck1_id, game.deck2_id, game.deck3_id, game.deck4_id)", nativeQuery = true)
	Integer countTotalGamesForDeckById(int id);
	
	@Query(value = "SELECT COUNT(*) FROM game WHERE winner_id = ?1", nativeQuery = true)
	Integer countWinsForDeckById(int id);
}
