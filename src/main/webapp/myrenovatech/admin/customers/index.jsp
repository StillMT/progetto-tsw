<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/includes/lang-selector.jspf" %>

<!DOCTYPE html>
<html>
  <% final String pageName = "customers"; %>
  <%@ include file="/WEB-INF/includes/head.jspf" %>
  <body>
    <%@ include file="/WEB-INF/includes/header.jspf" %>

    <main class="main-cont">
      <div class="list">
        <div class="list-header">
          <h2><%= langBundle.getString(pageName + ".clientList") %></h2>
          <span class="refresh-list-span">
            <button id="cust-refresh-list"></button>
            <input id="search-input" type="text" placeholder="<%= langBundle.getString(pageName + ".searchBar") %>" />
          </span>
        </div>

        <div class="actual-list" id="customers-list">
          <div class="actual-list-header">
            <span><%= langBundle.getString(pageName + ".id") %></span>
            <span><%= langBundle.getString(pageName + ".fullName") %></span>
            <span><%= langBundle.getString(pageName + ".username") %></span>
            <span><%= langBundle.getString(pageName + ".email") %></span>
            <span><%= langBundle.getString(pageName + ".role") %></span>
            <span><%= langBundle.getString(pageName + ".actions") %></span>
          </div>
        </div>
      </div>

      <div class="list">
        <div class="list-header">
          <h2><%= langBundle.getString(pageName + ".ordersList") %> <span id="active-view"><%= langBundle.getString(pageName + ".for") %> <span id="active-view-username"></span></span></h2>
          <button class="remove-btn" id="active-view-close-btn"></button>
        </div>

        <div class="actual-list" id="orders-list">
          <div class="actual-list-header">
            <span><%= langBundle.getString(pageName + ".nr") %></span>
            <span><%= langBundle.getString(pageName + ".date") %></span>
            <span><%= langBundle.getString(pageName + ".totalPaid") %></span>
            <span><%= langBundle.getString(pageName + ".address") %></span>
            <span><%= langBundle.getString(pageName + ".status") %></span>
            <span><%= langBundle.getString(pageName + ".actions") %></span>
          </div>
        </div>
      </div>

      <%@ include file="/WEB-INF/includes/popup.jspf" %>
    </main>

    <script>
      const canceled = "<%= langBundle.getString("CANCELED") %>";
      const toShip = "<%= langBundle.getString("TO_SHIP") %>";
      const shipped = "<%= langBundle.getString("SHIPPED") %>";
      const delivered = "<%= langBundle.getString("DELIVERED") %>";

      const noProductsInList = "<%= langBundle.getString(pageName + ".noProductsInList") %>";

      const popUpTitle = "<%= langBundle.getString(pageName + ".popUpTitle") %>";
      const popUpMessageToggleLimitationFailure = "<%= langBundle.getString(pageName + ".popUpMessageToggleLimitationFailure") %>";
      const popUpMessageCancelOrderFailure = "<%= langBundle.getString(pageName + ".popUpMessageCancelOrderFailure") %>";

      const viewOrdersButton = "<%= langBundle.getString(pageName + ".viewOrdersButton") %>";
      const limitUser = "<%= langBundle.getString(pageName + ".limitUser") %>";
      const unlockUser = "<%= langBundle.getString(pageName + ".unlockUser") %>";
      const viewOrderButton = "<%= langBundle.getString(pageName + ".viewOrderButton") %>";
      const trackOrder = "<%= langBundle.getString(pageName + ".trackOrder") %>";
      const cancelOrder = "<%= langBundle.getString(pageName + ".cancel") %>";
      const lang = "<%= lang %>";
    </script>
    <script src="js/CustomersHandler.js"></script>

    <%@ include file="/WEB-INF/includes/footer.jspf" %>
  </body>
</html>