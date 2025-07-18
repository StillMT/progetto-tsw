package it.unisa.tsw_proj.controller;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.List;

import it.unisa.tsw_proj.model.bean.ProductBean;
import it.unisa.tsw_proj.model.bean.ProductVariantBean;
import it.unisa.tsw_proj.model.dao.CategoryDAO;
import it.unisa.tsw_proj.model.dao.ProductDAO;
import it.unisa.tsw_proj.utils.FieldValidator;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/myrenovatech/admin/products-handler")
@MultipartConfig
public class ProductsHandlerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Writer w = response.getWriter();

        switch(action) {
            case "getList":
                JSONArray productsJson = new JSONArray();

                for (ProductBean p : ProductDAO.doGetAllProducts())
                    productsJson.put(new JSONObject()
                            .put("id", p.getId())
                            .put("brand", p.getBrand())
                            .put("model", p.getModel())
                            .put("description", p.getDescription())
                            .put("id_category", p.getIdCategory())
                            .put("variants", getJSONVariants(p.getProductVariants())));

                w.write(new JSONObject().put("products", productsJson).toString());
                break;

            case "deleteProduct":
                w.write("{\"result\": " + ProductDAO.doDeleteProduct(request.getParameter("id")) + "}");
                break;

            case "add":
            case "edit":
                int prodId;

                String brand = request.getParameter("brand");
                String model = request.getParameter("model");
                String description = request.getParameter("description");
                String idCategory = request.getParameter("category");
                String[] vColors = request.getParameterValues("variantColor[]");
                String[] vStorages = request.getParameterValues("variantStorage[]");
                String[] vStocks = request.getParameterValues("variantStock[]");
                String[] vPrices = request.getParameterValues("variantPrice[]");
                String[] vSalePercentages = request.getParameterValues("variantSalePercentage[]");
                String[] vSalePrices = request.getParameterValues("variantSalePrice[]");
                String[] vSaleExpDates = request.getParameterValues("variantSaleDate[]");

                if (!checkParams(brand, model, description, idCategory, vColors, vStorages, vStocks, vPrices)) {
                    sendToCatalogue(response, "invalid_field");
                    return;
                }

                ProductBean pb;
                if ("add".equals(action)) {
                    pb = new ProductBean(brand, model, description, Integer.parseInt(idCategory));
                    for (int i = 0; i < vColors.length; i++)
                        pb.addVariant(new ProductVariantBean(vColors[i], Integer.parseInt(vStorages[i]), Integer.parseInt(vStocks[i]),
                                Double.parseDouble(vPrices[i]), Integer.parseInt(vSalePercentages[i]), Double.parseDouble(vSalePrices[i]),
                                LocalDateTime.parse(vSaleExpDates[i])));

                    prodId = ProductDAO.doInsertProduct(pb);
                    if (prodId < 0) {
                        sendToCatalogue(response, "error_insert");
                        return;
                    }
                } else {
                    try {
                        prodId = Integer.parseInt(request.getParameter("productId"));
                    } catch (NumberFormatException _) {
                        sendToCatalogue(response, "internal_error");
                        return;
                    }

                    String[] vIds = request.getParameterValues("variantId[]");

                    try {
                        pb = new ProductBean(prodId, brand, model, description, Integer.parseInt(idCategory));
                        for (int i = 0; i < vColors.length; i++)
                            pb.addVariant(new ProductVariantBean(vIds.length > i ? Integer.parseInt(vIds[i]) : -1, prodId,
                                    vColors[i], Integer.parseInt(vStorages[i]), Integer.parseInt(vStocks[i]), Double.parseDouble(vPrices[i]),
                                    Integer.parseInt(vSalePercentages[i]), Double.parseDouble(vSalePrices[i]), LocalDateTime.parse(vSaleExpDates[i])));
                    } catch (NumberFormatException _) {
                        sendToCatalogue(response, "internal_error");
                        return;
                    }

                    if (!ProductDAO.doUpdateProduct(pb)) {
                        sendToCatalogue(response, "internal_error");
                        return;
                    }
                }

                request.setAttribute("id", prodId);
                RequestDispatcher rd = request.getRequestDispatcher("/products/productFileServlet");
                rd.forward(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void sendToCatalogue(HttpServletResponse response, String error) throws IOException {
        response.sendRedirect("/myrenovatech/admin/catalogue" + (error != null && !error.isEmpty() ? "?error=" + error : ""));
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
                    .put("price", v.getPrice())
                    .put("salePrice", v.getSalePrice())
                    .put("salePercentage", v.getSalePercentage())
                    .put("saleExpireDate", v.getSaleExpireDate());

            jsonArray.put(variantJson);
        }

        return jsonArray;
    }

    private static boolean checkParams(String brand, String model, String description, String idCategory, String[] vColors, String[] vStorages, String[] vStocks, String[] vPrices) {

        // Validate brand
        if (!FieldValidator.brandValidate(brand) || FieldValidator.containsBadWord(brand))
            return false;

        // Validate model
        if (!FieldValidator.modelValidate(model) || FieldValidator.containsBadWord(model))
            return false;

        if (FieldValidator.containsBadWord(description))
            return false;

        // Validate idCategory
        try {
            if (!CategoryDAO.doCheckCategory(Integer.parseInt(idCategory)))
                return false;
        } catch (NumberFormatException _) {
            return false;
        }

        if (vColors.length == 0 || vStorages.length == 0 || vStocks.length == 0 || vPrices.length == 0)
            return false;

        //Validate colors, storages, stocks and prices
        for (int i = 0; i < vColors.length; i++) {
            if (!FieldValidator.colorValidate(vColors[i]))
                return false;
            if (!FieldValidator.storageStockValidate(vStorages[i]))
                return false;
            if (!FieldValidator.storageStockValidate(vStocks[i]))
                return false;
            if (!FieldValidator.priceValidate(vPrices[i]))
                return false;
        }

        return true;
    }
}