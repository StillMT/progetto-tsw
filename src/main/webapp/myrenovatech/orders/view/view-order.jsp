<%@ page import="java.util.List" %>
<%@ page import="it.unisa.tsw_proj.model.bean.*" %>
<%@ page import="java.util.Objects" %>
<%@ page import="it.unisa.tsw_proj.model.OrderState" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/includes/lang-selector.jspf" %>

<%!
  public String printPrice(Double price) {
    return String.format("€%,.2f", price);
  }
%>

<!DOCTYPE html>
<html>
  <% final String pageName = "viewOrder"; %>
  <%@ include file="/WEB-INF/includes/head.jspf" %>
  <body>
    <%@ include file="/WEB-INF/includes/header.jspf" %>

    <main class="main-cont">
      <%
        OrderBean o = (OrderBean) request.getAttribute("order");
        if (o == null) {
      %>

      <div class="error-box">
        <h2><%= langBundle.getString(pageName + ".error") %></h2>
        <span><%= langBundle.getString(pageName + ".orderNotFound") %></span>
      </div>

      <%
        } else {
      %>

      <%
        if (Objects.equals(request.getParameter("error"), "cancel_failed")) {
      %>

      <div class="error-box">
        <h2><%= langBundle.getString(pageName + ".error") %></h2>
        <span>Annullamento ordine fallito</span>
      </div>

      <% } %>

      <div class="order-wrapper">
        <div class="order-info-wrapper">
          <div class="order-info-wrapper-header">
            <h2>Dettagli dell'ordine</h2>
            <% if (o.getIdUser() != us.getId()) { %>

            <span>Ordine creato da: <%= o.getUserName() %></span>

            <% } %>
          </div>

          <div class="order-info">
            <div class="order-info-header">
              <span>Numero d'ordine: <%= o.getOrderNr() %></span>
              <span>Creato il: <%= o.getOrderDate() %></span>
            </div>

            <div class="order-info-content">
              <div class="order-info-address">
                <h4>Invia a</h4>
                <span><%= o.getAddress().toString() %></span>
              </div>

              <div class="order-info-status">
                <h4>Stato ordine</h4>
                <span><%= langBundle.getString(o.getState().toString()) %></span>
              </div>

              <div class="order-info-summary">
                <h4>Riepilogo ordine</h4>
                <span>
                  <span>Subtotale articoli:</span>
                  <span><%= printPrice(o.getTotalPrice()) %></span>
                </span>

                <span>
                  <span>Costi di spedizione:</span>
                  <span><%= printPrice(o.getShippingCost()) %></span>
                </span>

                <span>
                  <span class="total-price">Totale:</span>
                  <span><%= printPrice(o.getTotalPrice() + o.getShippingCost()) %></span>
                </span>
              </div>
            </div>

            <div class="order-info-footer">

              <%
                OrderState os = o.getState();

                if (os == OrderState.TO_SHIP) {
              %>
              <span><a href="../orderServlet?action=cancelOrder&orderNr=<%= o.getOrderNr() %>" id="cancel-order">Annulla ordine</a></span>
              <% } %>

              <%
                String tracking = o.getTracking();
                if (!tracking.isBlank()) {
              %>
              <span><a target="_blank" href="https://t.17track.net/<%= lang %>#nums=<%= tracking %>">Traccia ordine</a> (<%= tracking %>)</span>
              <% } %>
            </div>
          </div>
        </div>

        <div class="order-list-wrapper">
          <h3>Prodotti acquistati</h3>
          <div class="order-list">
            <%
              List<OrderItemBean> l = o.getOrderItems();

              for (OrderItemBean i : l) {
                ProductBean p = i.getProduct();
                ProductVariantBean pv = p.getProductVariants().getFirst();
            %>

            <div class="order-list-item">
              <img src="/products/imgs/<%= p.getId() %>/1" alt="<%= p.getModel() %>" />

              <div class="order-list-item-info">
                <span><a href="${pageContext.request.contextPath}/products/?prodId=<%= p.getId() %>&pvId=<%= pv.getId() %>"><%= p.getBrand() %>, <%= p.getModel() %></a></span>
                <span><div class="vColor" style="background-color: <%= pv.getHexColor() %>"></div> <%= pv.getStorage() %> GB, <%= printPrice(i.getPrice()) %>, <%= i.getQt() %> pz.</span>
              </div>

              <div class="order-list-item-total-price">
                <span><%= printPrice(i.getTotal()) %></span>
              </div>
            </div>

            <% } %>
          </div>
        </div>
      </div>

      <% } %>

      <%@ include file="/WEB-INF/includes/popup.jspf" %>

    </main>

    <script>
      const popUpTitle = "Attenzione";
      const popUpMessageSureToCancel = "Clicca di nuovo il tasto annulla se sei sicuro di annullare questo ordine";
    </script>
    <script src="js/CancelOrderHandler.js"></script>

    <%@ include file="/WEB-INF/includes/footer.jspf" %>
  </body>
</html>