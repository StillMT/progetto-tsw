<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/includes/lang-selector.jspf" %>

<!DOCTYPE html>
<html>
  <% final String pageName = "orders"; %>
  <%@ include file="/WEB-INF/includes/head.jspf" %>
  <body>
    <%@ include file="/WEB-INF/includes/header.jspf" %>

    <main class="main-cont">
      <div class="list">
        <div class="list-header">
          <h2><%= langBundle.getString(pageName + ".ordersList") %></h2>
          <input type="text" id="search-bar" placeholder="<%= langBundle.getString(pageName + ".searchOrders") %>" />
        </div>

        <div class="actual-list" id="list">
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
    </main>

    <script>
      const canceled = "<%= langBundle.getString("CANCELED") %>";
      const toShip = "<%= langBundle.getString("TO_SHIP") %>";
      const shipped = "<%= langBundle.getString("SHIPPED") %>";
      const delivered = "<%= langBundle.getString("DELIVERED") %>";

      const noElements = "<%= langBundle.getString(pageName + ".noElements") %>";

      const viewOrder = "<%= langBundle.getString(pageName + ".viewOrder") %>";
      const trackOrder = "<%= langBundle.getString(pageName + ".trackOrder") %>";
      const lang = "<%= lang %>";
    </script>
    <script src="js/OrdersHandler.js"></script>

    <%@ include file="/WEB-INF/includes/footer.jspf" %>
  </body>
</html>