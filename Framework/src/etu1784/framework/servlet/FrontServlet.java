package etu1784.framework.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.InvocationTargetException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import etu1784.framework.Mapping;
import util.Util;
import etu1784.framework.MethodAnnotation;
import etu1784.framework.ModelView;

public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;
    protected Util util;

    @Override
    public void init() throws ServletException {
        super.init();
        try {

            this.util = new Util();
            this.mappingUrls = new HashMap<>();

            final String tomPath = "/WEB-INF/classes/";
            String path = getServletContext().getRealPath(tomPath);
            List<Class<?>> allClass = util.getAllClass(path, tomPath);

            Mapping mapping;
            Method[] allMethods;
            for(Class<?> c : allClass) {
                allMethods = c.getMethods();

                for(Method m : allMethods) {
                    if(m.isAnnotationPresent(MethodAnnotation.class)) {
                        mapping = new Mapping();
                        mapping.setClassName(c.getName());
                        mapping.setMethod(m.getName());
                        mappingUrls.put(m.getAnnotation(MethodAnnotation.class).url(), mapping);

                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURL().toString();
        url = util.processUrl(url, request.getContextPath());

        try {
            Mapping map = mappingUrls.get(url);

            if (map == null) {
                throw new Exception("Not Found");
            }

            Class<?> clazz = Class.forName(map.getClassName());
            Object o = clazz.getDeclaredConstructor().newInstance();
            ModelView mv = (ModelView) o.getClass().getMethod(map.getMethod()).invoke(o);

            HashMap<String, Object> donne = mv.getData();
            for(String key : donne.keySet()) {
                System.out.println(key);
                request.setAttribute(key, donne.get(key));
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(mv.getView());
            dispatcher.forward(request, response);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ignored) { } catch (
                Exception e) {
            throw new RuntimeException(e);
        }
    }
}
