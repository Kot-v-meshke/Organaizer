package vsu.task.controller;

import vsu.task.domain.Event;
import vsu.task.domain.EventType;
import vsu.task.domain.Birthday;
import vsu.task.domain.Meeting;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/event-form")
public class EventFormServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");

        if (idParam != null) {
            long id = Long.parseLong(idParam);
            Event event = eventService.getEventById(id);
            req.setAttribute("event", event);
            req.setAttribute("mode", "edit");
        } else {
            req.setAttribute("mode", "add");
        }

        req.getRequestDispatcher("/WEB-INF/views/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String idParam = req.getParameter("id");
        String typeParam = req.getParameter("type");
        String dateParam = req.getParameter("date");
        String description = req.getParameter("description");
        String extraData = req.getParameter("extraData");

        EventType type = EventType.valueOf(typeParam);
        LocalDate date = LocalDate.parse(dateParam);

        if (idParam != null && !idParam.isEmpty()) {
            long id = Long.parseLong(idParam);
            Event event = eventService.getEventById(id);

            event.setDate(date);
            event.setDescription(description);

            if (event instanceof Birthday) {
                ((Birthday) event).setName(extraData);
            } else if (event instanceof Meeting) {
                ((Meeting) event).setLocation(extraData);
            }

            eventService.updateEvent(event);
        } else {
            eventService.addEvent(type, date, description, extraData);
        }

        resp.sendRedirect("/events");
    }
}