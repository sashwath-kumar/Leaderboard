# --- !Ups
CREATE TABLE game_session (
    tournament_id UUID NOT NULL,
    game_session_url varchar NOT NULL
);
INSERT INTO game_session (tournament_id, game_session_url)
VALUES
('1108a315-0a4c-41c6-8071-5159090e29a8', 'http://game-session.com/makeGameSession'),
('2e5884c0-bbcc-493a-93ce-3f80e9d40bbd', 'http://game-session.com/makeGameSession'),
('3faf4de0-48bb-4eb4-a904-e8afae85e9ae', 'http://game-session.com/makeGameSession'),
('42f62f5b-7b77-41d8-a5c9-2c567dd001e1', 'http://game-session.com/makeGameSession'),
('575aabfa-4e41-4991-afab-7e0984150718', 'http://game-session.com/makeGameSession'),
('8916a382-b85c-4760-acce-d9c28d28c869', 'http://game-session.com/makeGameSession'),
('b975edac-9e8b-4dbd-a9df-e996dc5a8719', 'http://game-session.com/makeGameSession'),
('e13ad895-1c4f-49a8-9835-0996074c79d4', 'http://game-session.com/makeGameSession'),
('1e5cf42b-d674-4b9b-96c2-99882c6f2b36', 'http://game-session.com/makeGameSession'),
('2f4ab786-84a7-4387-96e2-93d4e0a94b0d', 'http://game-session.com/makeGameSession'),
('3c7e153f-1f46-42db-ae49-8592879c1c5d', 'http://game-session.com/makeGameSession'),
('4d1a5c8e-79c6-49e2-9a76-69d15eb53b9a', 'http://game-session.com/makeGameSession'),
('5a8df6e2-d816-4b6e-9417-afe89d3576d1', 'http://game-session.com/makeGameSession');