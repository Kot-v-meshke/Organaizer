package vsu.task;

import vsu.task.AppBuilder.Application;
import vsu.task.AppBuilder.ApplicationBuilder;

public class Main {
    public static void main(String[] args) {
        Application app = new ApplicationBuilder()
                .build();
        app.start();
    }
}