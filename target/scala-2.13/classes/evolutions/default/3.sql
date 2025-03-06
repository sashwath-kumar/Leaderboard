# --- !Ups
-- Create table: tournament_winners
CREATE TABLE tournament_winners (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tournament_id UUID NOT NULL,
    user_id UUID NOT NULL,
    prize_id UUID NOT NULL
);

-- Create table: tournament_leaderboards
CREATE TABLE tournament_leaderboards (
    tournament_id UUID NOT NULL,
    user_id UUID NOT NULL,
    score DECIMAL(20, 2) DEFAULT 0.00,
    remaining_balance DECIMAL(20, 2) DEFAULT 0.00,
    rank INT NOT NULL,
    PRIMARY KEY (tournament_id, user_id)
);

-- Sample inserts for tournament_winners
INSERT INTO tournament_winners (tournament_id, user_id, prize_id)
VALUES (gen_random_uuid(), gen_random_uuid(), gen_random_uuid());

INSERT INTO tournament_winners (tournament_id, user_id, prize_id)
VALUES (gen_random_uuid(), gen_random_uuid(), gen_random_uuid());

INSERT INTO tournament_winners (tournament_id, user_id, prize_id)
VALUES (gen_random_uuid(), gen_random_uuid(), gen_random_uuid());

-- Sample inserts for tournament_leaderboards
INSERT INTO tournament_leaderboards (tournament_id, user_id, score, remaining_balance, rank)
VALUES (gen_random_uuid(), gen_random_uuid(), 1500.50, 500.00, 1);

INSERT INTO tournament_leaderboards (tournament_id, user_id, score, remaining_balance, rank)
VALUES (gen_random_uuid(), gen_random_uuid(), 1200.75, 400.00, 2);

INSERT INTO tournament_leaderboards (tournament_id, user_id, score, remaining_balance, rank)
VALUES (gen_random_uuid(), gen_random_uuid(), 800.00, 300.00, 3);
