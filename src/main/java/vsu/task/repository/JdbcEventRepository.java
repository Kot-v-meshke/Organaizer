package vsu.task.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import vsu.task.domain.*;
import vsu.task.config.DatabaseConfig;
import vsu.task.exeption.EventNotFoundException;
import vsu.task.exeption.EventRepositoryException;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class JdbcEventRepository implements EventRepository {

    private static final Logger logger = Logger.getLogger(JdbcEventRepository.class.getName());
    private final JdbcTemplate jdbcTemplate;

    public JdbcEventRepository() {
        try {
            this.jdbcTemplate = new DatabaseConfig().jdbcTemplate();
            initDatabase();
        } catch (DataAccessException e) {
            logger.severe("Не удалось инициализировать репозиторий: " + e.getMessage());
            throw new EventRepositoryException("Ошибка инициализации базы данных", e);
        }
    }

    private void initDatabase() {
        String sql = """
        CREATE TABLE IF NOT EXISTS events (
            id BIGINT PRIMARY KEY AUTO_INCREMENT,
            type VARCHAR(50) NOT NULL,
            date DATE NOT NULL,
            description VARCHAR(255),
            extra_data VARCHAR(255)
        )
        """;

        try {
            jdbcTemplate.execute(sql);
            logger.info("База данных успешно инициализирована");
        } catch (DataAccessException e) {
            logger.warning("Ошибка при инициализации таблицы: " + e.getMessage());
            throw new EventRepositoryException("Не удалось создать таблицу events", e);
        }
    }

    @Override
    public void addEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event не может быть null");
        }

        String sql = "INSERT INTO events (type, date, description, extra_data) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, event.getType().name());
                ps.setDate(2, java.sql.Date.valueOf(event.getDate()));
                ps.setString(3, event.getDescription());
                ps.setString(4, getExtraData(event));
                return ps;
            }, keyHolder);

            Long generatedId = keyHolder.getKey().longValue();

            event.setId(generatedId.longValue());
            logger.info("Добавлено событие с ID: " + generatedId);

        } catch (DataAccessException e) {
            logger.severe("Ошибка при добавлении события: " + e.getMessage());
            throw new EventRepositoryException("Не удалось добавить событие", e);
        }
    }

    @Override
    public Event getEventById(long id) {
        String sql = "SELECT * FROM events WHERE id = ?";

        try {
            Event event = jdbcTemplate.queryForObject(sql, eventRowMapper(), id);
            logger.info("Найдено событие с ID: " + id);
            return event;

        } catch (EmptyResultDataAccessException e) {
            logger.warning("Событие с ID " + id + " не найдено");
            throw new EventNotFoundException("Событие с ID " + id + " не существует");

        } catch (DataAccessException e) {
            logger.severe("Ошибка при получении события ID " + id + ": " + e.getMessage());
            throw new EventRepositoryException("Ошибка доступа к данным при получении события", e);
        }
    }

    @Override
    public List<Event> getAllEvents() {
        String sql = "SELECT * FROM events ORDER BY date";

        try {
            List<Event> events = jdbcTemplate.query(sql, eventRowMapper());
            logger.info("Получено событий: " + events.size());
            return events;

        } catch (DataAccessException e) {
            logger.severe("Ошибка при получении всех событий: " + e.getMessage());
            throw new EventRepositoryException("Не удалось получить список событий", e);
        }
    }

    @Override
    public List<Event> getEventsByDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Дата не может быть null");
        }

        String sql = "SELECT * FROM events WHERE date = ?";

        try {
            List<Event> events = jdbcTemplate.query(sql, eventRowMapper(), date);
            logger.info("Найдено событий на дату " + date + ": " + events.size());
            return events;

        } catch (DataAccessException e) {
            logger.severe("Ошибка при получении событий за дату " + date + ": " + e.getMessage());
            throw new EventRepositoryException("Не удалось получить события за указанную дату", e);
        }
    }

    @Override
    public void updateEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event не может быть null");
        }

        String sql = "UPDATE events SET date = ?, description = ?, extra_data = ? WHERE id = ?";

        try {
            int affectedRows = jdbcTemplate.update(sql,
                    event.getDate(),
                    event.getDescription(),
                    getExtraData(event),
                    event.getId()
            );

            if (affectedRows == 0) {
                throw new EventNotFoundException("Не удалось обновить: событие с ID " + event.getId() + " не найдено");
            }

            logger.info("Обновлено событие с ID: " + event.getId());

        } catch (DataAccessException e) {
            logger.severe("Ошибка при обновлении события ID " + event.getId() + ": " + e.getMessage());
            throw new EventRepositoryException("Не удалось обновить событие", e);
        }
    }

    @Override
    public void removeEvent(long id) {
        String sql = "DELETE FROM events WHERE id = ?";

        try {
            int affectedRows = jdbcTemplate.update(sql, id);

            if (affectedRows == 0) {
                throw new EventNotFoundException("Не удалось удалить: событие с ID " + id + " не найдено");
            }

            logger.info("Удалено событие с ID: " + id);

        } catch (DataAccessException e) {
            logger.severe("Ошибка при удалении события ID " + id + ": " + e.getMessage());
            throw new EventRepositoryException("Не удалось удалить событие", e);
        }
    }

    private String getExtraData(Event event) {
        if (event instanceof Birthday) {
            return ((Birthday) event).getName();
        } else if (event instanceof Meeting) {
            return ((Meeting) event).getLocation();
        }
        return null;
    }

    private RowMapper<Event> eventRowMapper() {
        return (rs, rowNum) -> {
            try {
                long id = rs.getLong("id");
                EventType type = EventType.valueOf(rs.getString("type"));
                LocalDate date = rs.getDate("date").toLocalDate();
                String description = rs.getString("description");
                String extraData = rs.getString("extra_data");

                Event event = switch (type) {
                    case BIRTHDAY -> new Birthday(date, description, extraData);
                    case MEETING -> new Meeting(date, description, extraData);
                };

                event.setId(id);
                return event;

            } catch (Exception e) {
                logger.warning("Ошибка при маппинге строки результата: " + e.getMessage());
                throw new EventRepositoryException("Ошибка преобразования данных события", e);
            }
        };
    }
}