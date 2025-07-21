<%@ page import="it.unisa.tsw_proj.model.bean.ProductBean" %>
<%@ page import="it.unisa.tsw_proj.model.bean.ProductVariantBean" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unisa.tsw_proj.utils.FieldValidator" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/includes/lang-selector.jspf" %>

<%!
    String getTextColor(String hexColor) {
        int r = Integer.parseInt(hexColor.substring(1, 3), 16);
        int g = Integer.parseInt(hexColor.substring(3, 5), 16);
        int b = Integer.parseInt(hexColor.substring(5, 7), 16);
        double luminance = 0.299 * r + 0.587 * g + 0.114 * b;
        return luminance > 140 ? "#000000" : "#ffffff";
    }

    boolean hasDiscount(LocalDateTime expireDate) {
        return  FieldValidator.validateDiscount(expireDate);
    }

    String formatPrice(double price) {
        return FieldValidator.formatEuroPrice(price);
    }

    String stockMessage(int stock, ResourceBundle langBundle, String pageName) {
        return stock > 10 ? langBundle.getString(pageName + ".available") : langBundle.getString(pageName + ".availableOnly") + ": " + stock;
    }
%>

<!DOCTYPE html>
<html>
<% final String pageName = "viewProduct"; %>
<%@ include file="/WEB-INF/includes/head.jspf" %>
    <body>
    <%@ include file="/WEB-INF/includes/header.jspf" %>

        <main class="main-cont">
            <%
                ProductBean p = (ProductBean) request.getAttribute("product");
                if (p != null) {
                    List<ProductVariantBean> pvl = p.getProductVariants();
                    ProductVariantBean selPv = null;
                    Integer pvId = (Integer) request.getAttribute("pVariantId");
                    if (pvId != null) {
                        for (ProductVariantBean pv : pvl) {
                            if (pv.getId() == pvId) {
                                selPv = pv;
                                break;
                            }
                        }
                    }
                    if (selPv == null) selPv = pvl.getFirst();

                    boolean selHasDiscount = hasDiscount(selPv.getSaleExpireDate());
                    Integer imgsCount = (Integer) request.getAttribute("productImgsCount");
            %>

            <div class="product-wrapper">
                <div class="slideshow-wrapper">
                    <div class="slideshow-imgs">
                        <div class="slides-container" id="slidesContainer">
                            <% if (imgsCount != null && imgsCount > 0) {
                                for (int i = 1; i <= imgsCount; i++) { %>
                            <div class="slide"><img src="/products/imgs/<%= p.getId() %>/<%= i %>" /></div>
                            <% } %>
                        </div>
                        <% if (imgsCount > 1) { %>
                        <a class="prev">❮</a>
                        <a class="next">❯</a>
                        <% }
                        } else { %>
                        <div class="slide"><img src="${pageContext.request.contextPath}/products/imgs/fallback-image" /></div>
                        <% } %>
                    </div>
                    <div class="dots">
                        <% if (imgsCount != null && imgsCount > 0) {
                            for (int i = 1; i <= imgsCount; i++) { %>
                        <span class="dot" onclick="currentSlide(<%= i %>)"></span>
                        <% } } else { %>
                        <span class="dot"></span>
                        <% } %>
                    </div>
                </div>

                <div class="product-info">
                    <span class="product-info-title"><%= p.getBrand() %>, <%= p.getModel() %></span>
                    <div class="product-info-price-box">
                                <span class="discount-wrapper">
                                    <span id="discount-perc" <%= !selHasDiscount ? "style='display:none'" : "" %>>-<%= selPv.getSalePercentage() %>%</span>
                                    <span class="view-price" id="main-view-price"><%= formatPrice(selHasDiscount ? selPv.getSalePrice() : selPv.getPrice()) %></span>
                                </span>
                        <span class="actual-price-wrapper" <%= !selHasDiscount ? "style='display:none'" : "" %>><%= langBundle.getString(pageName + ".originalPrice") %>: <span id="actual-price"><%= formatPrice(selPv.getPrice()) %></span></span>
                        <span class="stock-info<%= selPv.getStock() < 1 ? " oos" : "" %>" id="stock-info-main"><%= stockMessage(selPv.getStock(), langBundle, pageName) %></span>
                    </div>

                    <div class="product-info-variants-wrapper">
                        <span class="product-info-variants-wrapper-header"><%= langBundle.getString(pageName + ".variants") %>:</span>
                        <div class="product-info-variants-wrapper-variants">
                            <% for (ProductVariantBean pv : pvl) {
                                boolean discount = hasDiscount(pv.getSaleExpireDate());
                                boolean isSelected = pv.equals(selPv);
                                double priceToShow = (isSelected && selHasDiscount) || discount ? pv.getSalePrice() : pv.getPrice();
                            %>
                            <div class="variant <%= isSelected ? "selected" : "" %>"
                                 data-id="<%= pv.getId() %>"
                                 data-discount="<%= discount %>"
                                 data-discount-perc="<%= pv.getSalePercentage() %>"
                                 data-sale-price="<%= pv.getSalePrice() %>"
                                 data-price="<%= pv.getPrice() %>"
                                 data-stock="<%= pv.getStock() %>"
                                 style="background-color:<%= pv.getHexColor() %>; color:<%= getTextColor(pv.getHexColor()) %>">
                                <% if ((isSelected && selHasDiscount) || discount) { %>
                                <span class="variant-discount-perc">-<%= pv.getSalePercentage() %>%</span>
                                <% } %>
                                <span><%= formatPrice(priceToShow) %></span>
                                <span><%= pv.getStorage() %> GB</span>
                                <% if (pv.getStock() < 10) { %><span><%= pv.getStock() %> pcs.</span><% } %>
                            </div>
                            <% } %>
                        </div>
                    </div>

                    <div class="product-info-description last">
                        <span class="product-category"><%= langBundle.getString(pageName + ".category") %>: <span class="product-category-name"><%= request.getAttribute("catName") %></span></span>
                        <span class="product-info-description-desc"><%= p.getDescription() %></span>
                    </div>
                </div>

                <div class="product-checkout-options">
                    <span class="view-price" id="checkout-view-price"><%= formatPrice(selHasDiscount ? selPv.getSalePrice() : selPv.getPrice()) %></span>
                    <span class="stock-info<%= selPv.getStock() < 1 ? " oos" : "" %>" id="stock-info-checkout"><%= stockMessage(selPv.getStock(), langBundle, pageName) %></span>

                    <form action="${pageContext.request.contextPath}/myrenovatech/cart/cartServlet" method="post" id="checkout-form">
                        <div>
                            <label for="qty"><%= langBundle.getString(pageName + ".amount") %>:</label>
                            <select name="qty" id="qty">
                                <% if (selPv.getStock() < 1) { %>
                                <option value="0">0</option>
                                <% } else {
                                    for (int i = 1; i <= selPv.getStock(); i++) { %>
                                <option value="<%= i %>"><%= i %></option>
                                <% }} %>
                            </select>
                        </div>
                        <button id="add-to-cart"><%= langBundle.getString(pageName + ".addToCart") %></button>
                        <button id="buy-now"><%= langBundle.getString(pageName + ".buyNow") %></button>

                        <input type="hidden" name="prodId" value="<%= p.getId() %>" />
                        <input type="hidden" id="p-var-id" name="pVariantId" value="<%= selPv.getId() %>" />
                        <input type="hidden" id="post-action" name="action" value="" />
                    </form>
                </div>
            </div>
            <% } %>
        </main>

    <script>
        const available = "<%= langBundle.getString(pageName + ".available") %>";
        const availableOnly = "<%= langBundle.getString(pageName + ".availableOnly") %>";
    </script>
    <script src="js/SlideshowHandler.js"></script>
    <script src="js/VariantSelector.js"></script>

    <%@ include file="/WEB-INF/includes/footer.jspf" %>
    </body>
</html>