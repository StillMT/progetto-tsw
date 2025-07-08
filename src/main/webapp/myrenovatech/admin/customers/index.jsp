<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/includes/lang-selector.jspf" %>

<!DOCTYPE html>
<html>
  <% final String pageName = "customers"; %>
  <%@ include file="/WEB-INF/includes/head.jspf" %>
  <body>
    <%@ include file="/WEB-INF/includes/header.jspf" %>

    <main class="main-cont">
      <div id="customers-list">
        <div class="list-header">
          <h2>Lista clienti</h2>
          <input type="text" placeholder="<%= langBundle.getString(pageName + ".searchBar") %>" />
        </div>
        <div class="actual-list">

        </div>
      </div>
    </main>

    <%@ include file="/WEB-INF/includes/footer.jspf" %>
  </body>
</html>