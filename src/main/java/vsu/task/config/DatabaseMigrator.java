package vsu.task.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;

public class DatabaseMigrator {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseMigrator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void migrate() {
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver()
                    .getResources("classpath:db/migration/*.sql");

            if (resources.length == 0) {
                System.out.println("Миграции не найдены в classpath:db/migration/");
                return;
            }

            Arrays.sort(resources, Comparator.comparing(Resource::getFilename));

            for (Resource resource : resources) {
                String filename = resource.getFilename();
                String sql = new String(
                        resource.getInputStream().readAllBytes(),
                        StandardCharsets.UTF_8
                );

                System.out.println("Загружается миграция: " + filename);
                jdbcTemplate.execute(sql);
                System.out.println("Миграция " + filename + " успешно применена");
            }

            System.out.println("Все миграции применены");

        } catch (IOException e) {
            System.err.println("Ошибка применения миграций: " + e.getMessage());
            throw new RuntimeException("Не удалось применить миграции", e);
        }
    }
}