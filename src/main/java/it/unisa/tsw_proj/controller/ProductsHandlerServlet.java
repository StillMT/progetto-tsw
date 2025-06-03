package it.unisa.tsw_proj.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import it.unisa.tsw_proj.model.bean.ProductBean;
import it.unisa.tsw_proj.model.bean.ProductVariantBean;
import it.unisa.tsw_proj.model.dao.ProductDAO;
import it.unisa.tsw_proj.model.dao.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/myrenovatech/admin/products-handler")
public class ProductsHandlerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Writer w = response.getWriter();

        if (request.getParameter("getList") != null) {
            JSONArray productsJson = new JSONArray();

            for (ProductBean p : ProductDAO.doGetAllProducts())
                productsJson.put(new JSONObject()
                        .put("id", p.getId())
                        .put("brand", p.getBrand())
                        .put("model", p.getModel())
                        .put("description", p.getDescription())
                        .put("base_price", p.getBasePrice())
                        .put("id_category", p.getIdCategory())
                        .put("variants", getJSONVariants(p.getProductVariants())));

            w.write(new JSONObject().put("products", productsJson).toString());
        } else if (request.getParameter("deleteProduct") != null)
            w.write("{\"result\": " + ProductDAO.doDeleteProduct(request.getParameter("id")) + "}");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private JSONArray getJSONVariants(List<ProductVariantBean> variants) {
        JSONArray jsonArray = new JSONArray();

        for (ProductVariantBean v : variants) {
            JSONObject variantJson = new JSONObject()
                    .put("id", v.getId())
                    .put("id_product", v.getIdProduct())
                    .put("color", v.getHexColor())
                    .put("storage", v.getStorage())
                    .put("stock", v.getStock())
                    .put("price", v.getPrice());

            jsonArray.put(variantJson);
        }

        return jsonArray;
    }
}