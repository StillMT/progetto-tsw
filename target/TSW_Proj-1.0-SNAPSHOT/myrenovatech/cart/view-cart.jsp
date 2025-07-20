<%@ page import="java.util.List" %>
<%@ page import="it.unisa.tsw_proj.model.bean.*" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="it.unisa.tsw_proj.utils.FieldValidator" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/includes/lang-selector.jspf" %>

<%!
    boolean isDiscounted(LocalDateTime expireDate) {
        return FieldValidator.validateDiscount(expireDate);
    }
%>

<%
    double totalPrice = 0;
%>

<!DOCTYPE html>
<html>
    <% final String pageName = "cart"; %>
    <%@ include file="/WEB-INF/includes/head.jspf" %>
    <body>
        <%@ include file="/WEB-INF/includes/header.jspf" %>

        <main class="main-cont">
            <%
                String result = request.getParameter("result");
                if (result != null)
                    if ("adding_succesfully".equals(result)) { %>
            <div class="info add_success">
                <%= langBundle.getString(pageName + ".productInsertedCorrectly") %>
            </div>
            <% } else if ("add_to_cart_failed".equals(result)) { %>
            <div class="info err_adding">
                <%= langBundle.getString(pageName + ".productInsertionFailed") %>
            </div>
            <% } %>

            <div class="cart-wrapper">
                <div class="cart-wrapper-header">
                    <h2>  <%= langBundle.getString(pageName + ".cart") %></h2>
                </div>

                <div class="cart-wrapper-items">
                    <%
                        if (cart != null && !cart.getProductList().isEmpty()) {
                            List<ProductBean> pList = (List<ProductBean>) request.getAttribute("prod_list");
                            List<CartedProduct> cpList = cart.getProductList();
                            for (int i = 0; i < pList.size() && i < cpList.size(); i++) {
                                CartedProduct currentCP = cpList.get(i);
                                ProductBean currentP = pList.get(i);
                                ProductVariantBean currentPV = currentP.getProductVariants().getFirst();

                                double price = isDiscounted(currentPV.getSaleExpireDate()) ? currentPV.getSalePrice() : currentPV.getPrice();
                                totalPrice += price;
                    %>

                    <div class="cart-item <%= i == pList.size() - 1 || i == cpList.size() - 1 ? "last" : "" %>"
                         data-id="<%= currentCP.getId() %>"
                         data-qty="<%= currentCP.getQty() %>"
                         data-price="<%= price %>">
                        <div class="cart-item-checkbox-wrapper">
                            <input class="checkbox-cart-selected" data-id="<%= currentCP.getId() %>" type="checkbox" <%= currentCP.getSelected() ? "checked" : "" %> />
                        </div>

                        <div class="cart-item-image-wrapper">
                            <img src="/products/imgs/<%= currentP.getId() %>/1" />
                        </div>

                        <div class="cart-item-details">
                            <span><a href="${pageContext.request.contextPath}/products/?prodId=<%= currentP.getId() %>&pvId=<%= currentPV.getId() %>"><%= currentP.getBrand() %>, <%= currentP.getModel() %></a></span>

                            <span>
                                    <span class="cart-item-color" style="background-color: <%= currentPV.getHexColor() %>"></span>
                                    <span><%= currentPV.getStorage() %> GB - <%= FieldValidator.formatEuroPrice(price) %></span>
                                </span>

                            <span class="cart-item-options">
                                    <span><input class="qty-cart-item" data-id="<%= currentCP.getId() %>" type="number" value="<%= currentCP.getQty() %>" min="0" max="<%= currentPV.getStock() %>" /></span>
                                    <span class="cart-item-options-option"><a class="remove-item" data-id="<%= currentCP.getId() %>"><%= langBundle.getString(pageName + ".remove") %></a></span>
                                </span>
                        </div>
                    </div>
                    <%
                        }
                    } else {    %>
                            <div class="no-cart-items">
                                <%= langBundle.getString(pageName + ".emptyCart") %>
                            </div>
                    <%  }
                    %>
                </div>

                <div class="cart-wrapper-summary">
                    <span><%= langBundle.getString(pageName + ".provisionalTotal") %>(<%= cart != null ? cart.getSelectedItemsCount() : "0" %> <%= langBundle.getString(pageName + ".articles") %>):</span>
                    <span id="cart-total-price"><%= FieldValidator.formatEuroPrice(totalPrice) %></span>
                    <a href="checkout/"><button><%= langBundle.getString(pageName + ".proceedToOrder") %></button></a>
                </div>
            </div>
        </main>

        <script>
            const provisionalTotal = "<%= langBundle.getString(pageName + ".provisionalTotal") %>";
            const articles = "<%= langBundle.getString(pageName + ".articles") %>";
            const emptyCart = "<%= langBundle.getString(pageName + ".emptyCart") %>";
        </script>
        <script src="js/CartItemsHandler.js"></script>

        <%@ include file="/WEB-INF/includes/footer.jspf" %>
    </body>
</html>