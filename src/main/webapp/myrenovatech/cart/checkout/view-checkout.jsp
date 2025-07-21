<%@ page import="java.util.List" %>
<%@ page import="it.unisa.tsw_proj.model.bean.*" %>
<%@ page import="it.unisa.tsw_proj.utils.FieldValidator" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/includes/lang-selector.jspf" %>

<!DOCTYPE html>
<html>
    <% final String pageName = "checkout"; %>
    <%@ include file="/WEB-INF/includes/head.jspf" %>
    <body>
        <%@ include file="/WEB-INF/includes/header.jspf" %>

        <main class="main-cont">
            <div class="checkout-wrapper">
                <h2><%= langBundle.getString(pageName + ".checkout") %></h2>

                <%
                    List<AddressBean> addresses = (List<AddressBean>) request.getAttribute("addresses");
                    List<CartedProduct> selCartProd = (List<CartedProduct>) request.getAttribute("selCartProd");
                    List<ProductBean> selProd = (List<ProductBean>) request.getAttribute("selProd");

                    if (addresses != null && !addresses.isEmpty() && selProd != null && !selProd.isEmpty() && selCartProd != null && !selCartProd.isEmpty()) {
                %>
                <form action="${pageContext.request.contextPath}/myrenovatech/orders/orderCheckoutServlet" method="post">
                    <div class="order-details">
                        <div class="addresses-wrapper">
                            <h3><%= langBundle.getString(pageName + ".address") %></h3>

                            <%
                                int count = 0;
                                for (AddressBean a : addresses) { %>
                            <div class="address-item">
                                <input type="radio" value="<%= a.getId() %>" id="address-radio-<%= count %>" name="addressId" <%= count == 0 ? "checked" : "" %> /><label for="address-radio-<%= count++ %>"><%= a.toString() %></label>
                            </div>
                            <% } %>
                        </div>

                        <div class="products-wrapper">
                            <h3><%= langBundle.getString(pageName + ".productsToBuy") %></h3>

                            <%
                                double totalCost = 0;

                                for (int i = 0; i < selCartProd.size() && i < selProd.size(); i++) {
                                    ProductBean p = selProd.get(i);
                                    ProductVariantBean pv = p.getProductVariants().getFirst();
                                    CartedProduct cp = selCartProd.get(i);
                                    double price = FieldValidator.validateDiscount(pv.getSaleExpireDate()) ? pv.getSalePrice() : pv.getPrice();
                                    double totalPrice = price * cp.getQty();
                                    totalCost += totalPrice;
                            %>
                            <input type="hidden" name="pVIds[]" value="<%= pv.getId() %>" />

                            <div class="product-item">
                                <div class="product-image-wrapper"><img src="/products/imgs/<%= p.getId() %>/1" /></div>

                                <div class="product-item-details">
                                    <span><%= p.getBrand() %>, <%= p.getModel() %></span>
                                    <span class="product-item-details-color"><span class="product-item-color" style="background-color: <%= pv.getHexColor() %>"></span><%= FieldValidator.formatEuroPrice(price) %> - <%= cp.getQty() %> pcs.</span>
                                </div>

                                <div>
                                    <%= FieldValidator.formatEuroPrice(totalPrice) %>
                                </div>
                            </div>
                            <% } %>
                        </div>
                    </div>

                    <div class="summary-wrapper">
                        <div>
                            <span><%= langBundle.getString(pageName + ".totalOrder") %>:</span>
                            <span class="total-price"><%= FieldValidator.formatEuroPrice(totalCost) %></span>
                            <span><%= langBundle.getString(pageName + ".shippingCosts") %>: <span class="shipping-price">€3,99</span></span>
                        </div>

                        <button type="submit"><%= langBundle.getString(pageName + ".buyNow") %></button>
                    </div>
                </form>

                <% } else { %>
                <div class="no-result">
                    <%= langBundle.getString(pageName + ".errorCheckout") %>
                </div>
                <% } %>
            </div>
        </main>

        <%@ include file="/WEB-INF/includes/footer.jspf" %>
    </body>
</html>