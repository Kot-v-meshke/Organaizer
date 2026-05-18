package vsu.task.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete-event")
public class DeleteEventServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        long id = Long.parseLong(req.getParameter("id"));

        try {
            eventService.deleteEvent(id);
        } catch (Exception e) {

        }

        resp.sendRedirect("/events");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}