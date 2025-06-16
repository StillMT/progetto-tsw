package it.unisa.tsw_proj.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;

@WebServlet("/products/imgs/*")
public class ProductFileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String FINAL_PATH = "C:/Users/mario/IdeaProjects/TSW_Proj/external-images/";

        if (Boolean.TRUE.equals(request.getAttribute("storeFiles"))) {
            for (Part part : request.getParts())
                if ("uploadedImages[]".equals(part.getName()) && part.getSize() > 0) {
                    String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    File dest = new File(FINAL_PATH + (int) request.getAttribute("id"), filename);
                    part.write(dest.getAbsolutePath());
                }
        } else {
            final String requestedPath = request.getPathInfo();

            if (requestedPath != null && requestedPath.equals("/getFileList")) {
                String pId = request.getParameter("productId");

                try {
                    Integer.parseInt(pId);
                } catch (NumberFormatException _) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                File pDir = new File(FINAL_PATH, pId);
                if (pDir.exists() && pDir.isDirectory()) {
                    JSONArray jsonArray = new JSONArray();

                    File[] files = pDir.listFiles();
                    if (files != null)
                        for (File file : files)
                            if (file.isFile() && !file.isHidden())
                                jsonArray.put(file.getName());

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(new JSONObject().put("products", jsonArray).toString());
                } else
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } else {
                final String FALLBACK_IMAGE = "no_image_fallback.png";

                File file;

                if (requestedPath == null || requestedPath.contains("..") || requestedPath.endsWith("/"))
                    file = new File(FINAL_PATH, FALLBACK_IMAGE);
                else {
                    file = new File(FINAL_PATH, requestedPath);

                    if (!file.exists() || !file.isFile())
                        file = new File(FINAL_PATH, FALLBACK_IMAGE);
                }

                if (!file.exists() || !file.isFile()) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Fallback image not found");
                    return;
                }

                String mimeType = getServletContext().getMimeType(file.getName());
                if (mimeType == null)
                    mimeType = "application/octet-stream";

                response.setContentType(mimeType);
                response.setContentLengthLong(file.length());

                try (FileInputStream fis = new FileInputStream(file);
                     OutputStream os = response.getOutputStream()) {
                    byte[] buffer = new byte[8192];     // 8KB
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1)
                        os.write(buffer, 0, bytesRead);
                }
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}