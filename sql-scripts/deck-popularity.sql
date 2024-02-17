SELECT
    d.id AS deck_id,
    d.name AS deck_name,
    COUNT(*) AS deck_count
FROM
    (
        SELECT deck1_id AS deck_id FROM game WHERE deck1_id IS NOT NULL
        UNION ALL
        SELECT deck2_id AS deck_id FROM game WHERE deck2_id IS NOT NULL
        UNION ALL
        SELECT deck3_id AS deck_id FROM game WHERE deck3_id IS NOT NULL
        UNION ALL
        SELECT deck4_id AS deck_id FROM game WHERE deck4_id IS NOT NULL
    ) AS all_decks
JOIN deck d ON all_decks.deck_id = d.id
WHERE
    d.player_id = 1
GROUP BY
    deck_id, deck_name
ORDER BY
    deck_count DESC;