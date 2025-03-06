# --- !Ups
-- Tournament Table
CREATE TABLE tournament (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(200),
    description VARCHAR(512),
    enabled BOOLEAN DEFAULT TRUE,
    starttime TIMESTAMP NOT NULL,
    endtime TIMESTAMP NOT NULL,
    owner_id UUID NOT NULL,
    game_id UUID NOT NULL,
    starting_balance DECIMAL(20,2) DEFAULT 0.00,
    last_modified TIMESTAMP(3) NOT NULL,
    group_id UUID,
    disable_group_notifications BOOLEAN DEFAULT FALSE,
    group_message VARCHAR(255),
    disable_nongroup_notifications BOOLEAN DEFAULT FALSE,
    nongroup_message VARCHAR(255),
    private_viewing BOOLEAN DEFAULT FALSE
);

INSERT INTO tournament (
    id,
    name,
    description,
    enabled,
    starttime,
    endtime,
    owner_id,
    game_id,
    starting_balance,
    last_modified,
    group_id,
    disable_group_notifications,
    group_message,
    disable_nongroup_notifications,
    nongroup_message,
    private_viewing
) VALUES
('1108a315-0a4c-41c6-8071-5159090e29a8', 'Spring Championship', 'Annual Spring Tournament', TRUE, '2025-03-01 09:00:00', '2025-03-15 18:00:00', gen_random_uuid(), gen_random_uuid(), 1000.00, '2025-01-08 12:00:00', '0574f12a-8ea5-4634-b0dd-26ca619cee2f', FALSE, 'Group message 1', FALSE, 'Non-group message 1', TRUE),
('2e5884c0-bbcc-493a-93ce-3f80e9d40bbd', 'Summer Clash', 'Beachside Gaming Event', TRUE, '2025-06-15 08:00:00', '2025-06-20 18:00:00', gen_random_uuid(), gen_random_uuid(), 750.00, '2025-01-08 12:10:00', 'ec2db9b7-fd1a-4f1a-a338-f1b52d1a23f0', TRUE, 'Group message 2', FALSE, 'Non-group message 3', TRUE),
('3faf4de0-48bb-4eb4-a904-e8afae85e9ae', 'Fall Frenzy', 'Autumn Special Tournament', TRUE, '2025-09-01 09:00:00', '2025-09-10 18:00:00', gen_random_uuid(), gen_random_uuid(), 2000.00, '2025-01-08 12:15:00', '0574f12a-8ea5-4634-b0dd-26ca619cee2f', FALSE, 'Group message 3', TRUE, 'Non-group message 4', FALSE),
('42f62f5b-7b77-41d8-a5c9-2c567dd001e1', 'Battle Royale', 'Winner takes all event', TRUE, '2025-07-01 09:00:00', '2025-07-15 20:00:00', gen_random_uuid(), gen_random_uuid(), 3000.00, '2025-01-08 12:25:00', '975eaa21-1720-4c80-a610-de5857d6b95a', FALSE, 'Group message 4', FALSE, 'Non-group message 6', TRUE),
('575aabfa-4e41-4991-afab-7e0984150718', 'Champions Cup', 'Elite Tournament for professionals', TRUE, '2025-08-01 12:00:00', '2025-08-10 18:00:00', gen_random_uuid(), gen_random_uuid(), 5000.00, '2025-01-08 12:30:00', 'f52b132f-ba22-432b-933f-755cdc1fde49', TRUE, 'Group message 5', TRUE, 'Non-group message 7', FALSE),
('8916a382-b85c-4760-acce-d9c28d28c869', 'Open League', 'Public tournament with cash prizes', FALSE, '2025-04-01 08:00:00', '2025-04-15 18:00:00', gen_random_uuid(), gen_random_uuid(), 2500.00, '2025-01-08 12:35:00', '0fabedbb-2b42-41c7-b016-0100663ac4fe', TRUE, 'Group message 6', FALSE, 'Non-group message 8', TRUE),
('b975edac-9e8b-4dbd-a9df-e996dc5a8719', 'Grand Prix', 'Grand tournament finale', TRUE, '2025-11-01 10:00:00', '2025-11-10 22:00:00', gen_random_uuid(), gen_random_uuid(), 7000.00, '2025-01-08 12:40:00', '01363211-5fbb-4ac0-8435-625de0cbedf2', TRUE, NULL, TRUE, 'Non-group message 9', TRUE),
('e13ad895-1c4f-49a8-9835-0996074c79d4', 'Pro League', 'Professional tier competition', TRUE, '2025-10-01 09:00:00', '2025-10-15 20:00:00', gen_random_uuid(), gen_random_uuid(), 10000.00, '2025-01-08 12:45:00', '0fabedbb-2b42-41c7-b016-0100663ac4fe', FALSE, NULL, TRUE, 'Non-group message 10', FALSE),
('1e5cf42b-d674-4b9b-96c2-99882c6f2b36', 'Tournament 1', 'Description for Tournament 1', TRUE, '2023-12-01 10:00:00', '2023-12-05 18:00:00', '11111111-2222-3333-4444-555555555555', 'aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee', 100.00, '2023-12-01 09:50:00', 'ec2db9b7-fd1a-4f1a-a338-f1b52d1a23f0', FALSE, 'Group message 1', FALSE, 'Non-group message 1', FALSE),
('2f4ab786-84a7-4387-96e2-93d4e0a94b0d', 'Tournament 2', 'Description for Tournament 2', TRUE, '2023-11-20 14:00:00', '2023-11-25 20:00:00', '22222222-3333-4444-5555-666666666666', 'bbbbbbbb-cccc-dddd-eeee-ffffffffffff', 200.00, '2023-11-20 13:50:00', '0574f12a-8ea5-4634-b0dd-26ca619cee2f', FALSE, 'Group message 2', FALSE, 'Non-group message 2', FALSE),
('3c7e153f-1f46-42db-ae49-8592879c1c5d', 'Tournament 3', 'Description for Tournament 3', TRUE, '2023-10-15 09:00:00', '2023-10-20 19:00:00', '33333333-4444-5555-6666-777777777777', 'cccccccc-dddd-eeee-ffff-000000000000', 150.00, '2023-10-15 08:50:00', '802329a0-1e0a-4f8c-9a0c-0ea4b2663161', FALSE, 'Group message 3', FALSE, 'Non-group message 3', FALSE),
('4d1a5c8e-79c6-49e2-9a76-69d15eb53b9a', 'Tournament 4', 'Description for Tournament 4', TRUE, '2023-09-10 08:00:00', '2023-09-12 16:00:00', '44444444-5555-6666-7777-888888888888', 'dddddddd-eeee-ffff-0000-111111111111', 120.00, '2023-09-10 07:50:00', '2b3c0fb0-52f7-43d1-8876-b4959caa7b54', FALSE, 'Group message 4', FALSE, 'Non-group message 4', FALSE),
('5a8df6e2-d816-4b6e-9417-afe89d3576d1', 'Tournament 5', 'Description for Tournament 5', TRUE, '2025-01-07 09:00:00', '2025-01-10 18:00:00', '55555555-6666-7777-8888-999999999999', 'eeeeeeee-ffff-0000-1111-222222222222', 300.00, '2025-01-07 08:50:00', 'f2ffb562-6bd3-48e4-ad3a-719565dda248', FALSE, 'Group message 5', FALSE, 'Non-group message 5', FALSE);













