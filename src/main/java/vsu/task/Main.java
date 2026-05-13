package vsu.task;

import vsu.task.AppBuilder.Application;
import vsu.task.AppBuilder.ApplicationBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public class Main {
    public static void main(String[] args) {
        try (InputStream is = Main.class.getResourceAsStream("/logging.properties")) {
            if (is != null) {
                LogManager.getLogManager().readConfiguration(is);
            }
        } catch (IOException e) {
            System.err.println("Не удалось загрузить logging.properties");
        }
        Application app = new ApplicationBuilder()
                .build();
        app.start();
    }
}