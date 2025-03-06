# --- !Ups
-- Tournament Winning Places Table
CREATE TABLE tournament_winning_places (
    tournament_id UUID NOT NULL,
    place INT NOT NULL,
    prize_id UUID,
    consolation_place BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (tournament_id, place),
    FOREIGN KEY (tournament_id) REFERENCES Tournament(id) ON DELETE CASCADE
);

INSERT INTO tournament_winning_places (tournament_id, place, prize_id, consolation_place)
VALUES ('1108a315-0a4c-41c6-8071-5159090e29a8', 1, gen_random_uuid(), FALSE);

INSERT INTO tournament_winning_places (tournament_id, place, prize_id, consolation_place)
VALUES ('2e5884c0-bbcc-493a-93ce-3f80e9d40bbd', 2, gen_random_uuid(), FALSE);

INSERT INTO tournament_winning_places (tournament_id, place, prize_id, consolation_place)
VALUES ('3faf4de0-48bb-4eb4-a904-e8afae85e9ae', 3, gen_random_uuid(), FALSE);

INSERT INTO tournament_winning_places (tournament_id, place, prize_id, consolation_place)
VALUES ('42f62f5b-7b77-41d8-a5c9-2c567dd001e1', 4, gen_random_uuid(), TRUE);

INSERT INTO tournament_winning_places (tournament_id, place, prize_id, consolation_place)
VALUES ('575aabfa-4e41-4991-afab-7e0984150718', 1, gen_random_uuid(), FALSE);

INSERT INTO tournament_winning_places (tournament_id, place, prize_id, consolation_place)
VALUES ('8916a382-b85c-4760-acce-d9c28d28c869', 2, gen_random_uuid(), FALSE);

INSERT INTO tournament_winning_places (tournament_id, place, prize_id, consolation_place)
VALUES ('b975edac-9e8b-4dbd-a9df-e996dc5a8719', 4, gen_random_uuid(), FALSE);

INSERT INTO tournament_winning_places (tournament_id, place, prize_id, consolation_place)
VALUES ('e13ad895-1c4f-49a8-9835-0996074c79d4', 5, NULL, TRUE);
