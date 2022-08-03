DELETE
FROM weather_data;

INSERT INTO `weather_data` (`id`, `city`, `country`, `temperature`, `timestamp`)
VALUES (1, 'Saint-Petersburg', 'Russia', 22, '2022-08-03 12:00:00'),
       (2, 'Moscow', 'Russia', 27, '2022-08-03 12:00:00'),
       (3, 'Minsk', 'Belarus', 25, '2022-08-03 12:00:00'),

       (4, 'Saint-Petersburg', 'Russia', 22, '2022-08-03 13:00:00'),
       (5, 'Moscow', 'Russia', 26, '2022-08-03 13:00:00'),
       (6, 'Minsk', 'Belarus', 24, '2022-08-03 13:00:00');
