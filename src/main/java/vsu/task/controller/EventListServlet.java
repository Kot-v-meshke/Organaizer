package vsu.task.controller;

import vsu.task.domain.Event;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/events")
public class EventListServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String dateParam = req.getParameter("date");
        List<Event> events;

        if (dateParam != null && !dateParam.isEmpty()) {
            events = eventService.getEventsByDate(LocalDate.parse(dateParam));
        } else {
            events = eventService.getAllEvents();
        }

        req.setAttribute("events", events);

        req.getRequestDispatcher("/WEB-INF/views/events.jsp").forward(req, resp);
    }
}