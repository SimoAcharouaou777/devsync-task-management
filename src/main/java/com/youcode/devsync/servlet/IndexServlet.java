package com.youcode.devsync.servlet;

import com.youcode.devsync.model.Tag;
import com.youcode.devsync.repository.TagRepository;
import com.youcode.devsync.service.TagService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "IndexServlet", value = "/")
public class IndexServlet extends HttpServlet {

    private TagService tagService;

    @Override
    public void init(){
        TagRepository tagRepository = new TagRepository();
        tagService = new TagService(tagRepository);
        tagService.insertFakeDataIfEmpty();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    public void destroy(){
        tagService.close();
    }

}
