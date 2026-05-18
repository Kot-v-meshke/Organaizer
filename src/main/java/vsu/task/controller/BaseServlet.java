package vsu.task.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vsu.task.repository.EventRepository;
import vsu.task.repository.JdbcEventRepository;
import vsu.task.service.EventService;

import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {
    protected EventService eventService;

    @Override
    public void init() {
        EventRepository repository = new JdbcEventRepository();
        this.eventService = new EventService(repository);
    }

    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException;

    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;
}