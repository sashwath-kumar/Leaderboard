# --- !Ups
-- Create table: tournament_winners
CREATE TABLE tournament_participants (
    id UUID,
    tournament_id UUID NOT NULL,
    user_id UUID NOT NULL,
    status varchar NOT NULL,
    PRIMARY KEY(tournament_id, user_id)
);