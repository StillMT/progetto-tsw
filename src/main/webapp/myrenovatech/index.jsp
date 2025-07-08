<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/includes/lang-selector.jspf" %>

<!DOCTYPE html>
<html>
  <% final String pageName = "reservedArea"; %>
  <%@ include file="/WEB-INF/includes/head.jspf" %>
  <body>
    <%@ include file="/WEB-INF/includes/header.jspf" %>

    <main class="main-cont">
      <div class="cards-container">
        <a href="profile/">
          <div class="card">
            <img src="/GeneralData/imgs/myrenovatech/profile.svg" />
            <span class="card-title"><%= langBundle.getString(pageName + ".profile") %></span>
          </div>
        </a>
        <a href="orders/">
          <div class="card">
            <img src="/GeneralData/imgs/myrenovatech/orders.svg" />
            <span class="card-title"><%= langBundle.getString(pageName + ".orders") %></span>
          </div>
        </a>

        <% if (((UserBean) request.getSession().getAttribute("user")).isAdmin()) { %>
        <a href="admin/catalogue/">
          <div class="card">
            <img src="admin/imgs/catalogue.svg" />
            <span class="card-title"><%= langBundle.getString(pageName + ".catalogue") %></span>
          </div>
        </a>
        <a href="admin/customers/">
          <div class="card">
            <img src="admin/imgs/customers-orders.svg" />
            <span class="card-title"><%= langBundle.getString(pageName + ".ordersByCustomer") %></span>
          </div>
        </a>
        <% } %>

        <a href="${pageContext.request.contextPath}/logout">
          <div class="card">
            <img src="${pageContext.request.contextPath}/GeneralData/imgs/myrenovatech/logout.svg" />
            <span class="card-title"><%= langBundle.getString(pageName + ".logout") %></span>
          </div>
        </a>
      </div>
    </main>

    <%@ include file="/WEB-INF/includes/footer.jspf" %>
  </body>
</html>