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
          <h2>Lista clienti</h2>
          <span class="refresh-list-span">
            <button id="cust-refresh-list"></button>
            <input id="search-input" type="text" placeholder="<%= langBundle.getString(pageName + ".searchBar") %>" />
          </span>
        </div>

        <div class="actual-list" id="customers-list">
          <div class="actual-list-header">
            <span>ID</span>
            <span>Nome completo</span>
            <span>Username</span>
            <span>E-mail</span>
            <span>Ruolo</span>
            <span>Azioni</span>
          </div>
        </div>
      </div>

      <div class="list">
        <div class="list-header">
          <h2>Lista ordini <span id="active-view">per <span id="active-view-username"></span></span></h2>
          <button class="remove-btn" id="active-view-close-btn"></button>
        </div>

        <div class="actual-list" id="orders-list">
          <div class="actual-list-header">
            <span>Nr.</span>
            <span>Data</span>
            <span>Totale pagato</span>
            <span>Indirizzo</span>
            <span>Stato</span>
            <span>Azioni</span>
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