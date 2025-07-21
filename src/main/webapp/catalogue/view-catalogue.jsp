<%@ page import="java.util.List" %>
<%@ page import="it.unisa.tsw_proj.model.bean.*" %>
<%@ page import="it.unisa.tsw_proj.utils.FieldValidator" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/includes/lang-selector.jspf" %>

<!DOCTYPE html>
<html>
    <% final String pageName = "publicCatalogue"; %>
    <%@ include file="/WEB-INF/includes/head.jspf" %>
    <body>
        <%@ include file="/WEB-INF/includes/header.jspf" %>

        <main class="main-cont">
            <div class="catalogue-wrapper">
                <h2><%= langBundle.getString(pageName + ".catalogue") %></h2>

                <%
                    List<ProductBean> pList = (List<ProductBean>) request.getAttribute("pList");
                    List<CategoryBean> catList = (List<CategoryBean>) request.getAttribute("catList");
                    if (pList != null && !pList.isEmpty() && catList != null && !catList.isEmpty()) {
                %>

                <% for (CategoryBean c : catList) { %>
                <div class="category-wrapper">
                    <h3><%= c.getName() %></h3>

                    <%
                        int count = 0;
                        for (ProductBean p : pList) {
                            if (p.getIdCategory() == c.getId()) {
                                count++;
                    %>
                    <div class="product-wrapper">
                        <a href="${pageContext.request.contextPath}/products/?prodId=<%= p.getId() %>">
                            <div class="product-image-wrapper"><img src="/products/imgs/<%= p.getId() %>/1" /></div>
                            <span class="product-title"><%= p.getBrand() %>, <%= p.getModel() %></span>
                        </a>

                        <div class="product-variants-wrapper">
                            <% for (ProductVariantBean pv : p.getProductVariants()) {
                                boolean isDiscounted = FieldValidator.validateDiscount(pv.getSaleExpireDate());
                            %>
                            <a href="${pageContext.request.contextPath}/products/?prodId=<%= p.getId() %>?pvId=<%= pv.getId() %>">
                                <div class="product-variant-wrapper" style="background-color: <%= pv.getHexColor() %>;">
                                    <% if (isDiscounted) { %><span class="discount-percentage">-<%= pv.getSalePercentage() %>%</span><% } %>
                                    <span><%= FieldValidator.formatEuroPrice(isDiscounted ? pv.getSalePrice() : pv.getPrice()) %></span>
                                    <% if (pv.getStock() < 10) { %><span><%= pv.getStock() %> pcs.</span><% } %>
                                </div>
                            </a>
                            <% } %>
                        </div>
                    </div>
                    <% }
                    } if (count == 0) { %>
                    <div class="no-products">
                        <div class="no-products-wrapper"><%= langBundle.getString(pageName + ".noProductsInCat") %></div>
                    </div>
                    <% } %>
                </div>
                <% } %>

                <% } else { %>
                <div class="no-products-wrapper"><%= langBundle.getString(pageName + ".noProducts") %></div>
                <% } %>
            </div>
        </main>

        <%@ include file="/WEB-INF/includes/footer.jspf" %>
    </body>
</html>