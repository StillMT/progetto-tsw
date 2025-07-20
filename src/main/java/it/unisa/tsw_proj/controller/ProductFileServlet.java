package it.unisa.tsw_proj.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@WebServlet(urlPatterns = { "/myrenovatech/admin/productFileServlet", "/products/imgs/*" })
@MultipartConfig
public class ProductFileServlet extends HttpServlet {
    final int MAX_IMAGES = 6;
    final String FINAL_PATH = "C:/Users/mario/IdeaProjects/TSW_Proj/external-images/";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!request.getServletPath().equals("/myrenovatech/admin/productFileServlet")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Object idAttr = request.getAttribute("id");
        if (idAttr == null)
            return;
        String productId = String.valueOf(idAttr);
        File productDir = new File(FINAL_PATH, productId);

        String[] keptImages = request.getParameterValues("keptImages[]");
        Set<String> keptSet = cleanUnkeptImages(productDir, keptImages);
        int remainingSlots = MAX_IMAGES - keptSet.size();
        if (remainingSlots <= 0)
            return;

        int nextIndex = keptSet.size() + 1;

        for (Part part : request.getParts()) {
            if ("uploadedImages[]".equals(part.getName()) && part.getSize() > 0 && remainingSlots > 0) {
                String submittedFileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                String ext = getFileExtension(submittedFileName);
                if (submittedFileName.contains("..") || submittedFileName.contains("/") || submittedFileName.contains("\\"))
                    continue;

                File dest = new File(productDir, nextIndex + ext.toLowerCase());
                part.write(dest.getAbsolutePath());
                nextIndex++;
                remainingSlots--;
            }
        }

        response.sendRedirect("/myrenovatech/admin/catalogue/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getServletPath().equals("/products/productFileServlet")) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }

        final String[] EXTENSIONS = {".jpg", ".jpeg", ".png", ".webp"};

        final String FALLBACK_IMAGE = "no_image_fallback.png";
        final String requestedPath = request.getPathInfo();

        if ("/getFileList".equals(requestedPath)) {
            String pId = request.getParameter("productId");
            if (pId == null || !pId.matches("\\d+")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            File pDir = new File(FINAL_PATH, pId);
            if (pDir.exists() && pDir.isDirectory()) {
                JSONArray jsonArray = new JSONArray();
                File[] files = pDir.listFiles();
                if (files != null)
                    if (request.getAttribute("productView") == Boolean.TRUE) {
                        request.setAttribute("productImgsCount", files.length);
                        request.getRequestDispatcher("/products/view-product.jsp").forward(request, response);
                    } else {
                        for (File file : files)
                            if (file.isFile() && !file.isHidden())
                                jsonArray.put(file.getName());

                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write(new JSONObject().put("products", jsonArray).toString());
                    }
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        }

        File file = null;
        File baseDir = new File(FINAL_PATH).getCanonicalFile();

        if (requestedPath == null || requestedPath.contains("..")) {
            file = new File(FINAL_PATH, FALLBACK_IMAGE);
        } else {
            String[] parts = requestedPath.substring(1).split("/");
            if (parts.length == 2) {
                String productId = parts[0];
                String imageName = parts[1];
                File productDir = new File(FINAL_PATH, productId);

                for (String ext : EXTENSIONS) {
                    File candidate = new File(productDir, imageName + ext);
                    if (candidate.exists() && candidate.isFile()) {
                        file = candidate.getCanonicalFile();
                        break;
                    }
                }
            }
            if (file == null || !file.getPath().startsWith(baseDir.getPath()))
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
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1)
                os.write(buffer, 0, bytesRead);
        }
    }

    private Set<String> cleanUnkeptImages(File productDir, String[] keptImages) {
        Set<String> keptSet = keptImages != null ? new HashSet<>(Arrays.asList(keptImages)) : new HashSet<>();

        if (!productDir.exists())
            productDir.mkdirs();
        else {
            File[] files = productDir.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (!keptSet.contains(f.getName()))
                        f.delete();
                }

                File[] remainingFiles = productDir.listFiles((dir, name) -> new File(dir, name).isFile());
                if (remainingFiles != null) {
                    Arrays.sort(remainingFiles);
                    for (int i = 0; i < remainingFiles.length; i++) {
                        File oldFile = remainingFiles[i];
                        String ext = getFileExtension(oldFile.getName());
                        File newFile = new File(productDir, (i + 1) + ext);
                        if (!oldFile.equals(newFile)) {
                            oldFile.renameTo(newFile);
                        }
                        keptSet.add(newFile.getName());
                    }
                }
            }
        }

        return keptSet;
    }

    private String getFileExtension(String filename) {
        int index = filename.lastIndexOf(".");
        return (index >= 0) ? filename.substring(index) : "";
    }
}