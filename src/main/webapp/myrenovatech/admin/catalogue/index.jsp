<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/includes/lang-selector.jspf" %>

<!DOCTYPE html>
<html>
  <% final String pageName = "adminCatalogue"; %>

  <%
    String error = null;
    String par = request.getParameter("error");
    if (par != null)
        switch (par) {
          case "invalid_field":
            error = langBundle.getString(pageName + ".invalidFieldsError");
            break;

          case "error_insert":
            error = langBundle.getString(pageName + ".errorProdInsert");
            break;

          case "internal_error":
            error = langBundle.getString(pageName + ".internalError");
            break;
        };
  %>

  <%@ include file="/WEB-INF/includes/head.jspf" %>
  <body>
    <%@ include file="/WEB-INF/includes/header.jspf" %>

    <main class="main-cont">
      <div class="cat-container">
        <div class="add-product-container">
          <div class="add-product-header">
            <span class="add-product-title"><%= langBundle.getString(pageName + ".addProd") %> <%= langBundle.getString(pageName + ".product") %></span>
          </div>

          <% if (error != null) { %>
          <div class="error-box"><%= error %></div>
          <% } %>

          <form action="../products-handler" method="post" enctype="multipart/form-data">
            <div class="column">
              <div class="item">
                <label for="brand"><%= langBundle.getString(pageName + ".apple") %></label>
                <input type="text" id="brand" name="brand" placeholder="<%= langBundle.getString(pageName + ".applePlaceholder") %>" maxlength="30" />
                <div class="form-error" id="brand-error">
                  <p><%= langBundle.getString(pageName + ".invalidBrand") %></p>
                </div>
              </div>

              <div class="item">
                <label for="description"><%= langBundle.getString(pageName + ".description") %></label>
                <textarea name="description" id="description" rows="4" placeholder="<%= langBundle.getString(pageName + ".descriptionPlaceholder") %>"></textarea>
              </div>
            </div>

            <div class="column">
              <div class="item">
                <label for="model"><%= langBundle.getString(pageName + ".model") %></label>
                <input type="text" id="model" name="model" placeholder="<%= langBundle.getString(pageName + ".modelPlaceholder") %>" maxlength="50" />
                <div class="form-error" id="model-error">
                  <p><%= langBundle.getString(pageName + ".invalidModel") %></p>
                </div>
              </div>

              <div class="item">
                <%@ include file="/WEB-INF/includes/category-selector.jspf" %>
              </div>

              <div class="item">
                <input type="button" value="<%= langBundle.getString(pageName + ".addVariant") %>" id="add-variant" />
              </div>
            </div>

            <div class="column" id="add-variant-column">
            </div>

            <div class="column">
              <div class="item">
                <label for="images"><%= langBundle.getString(pageName + ".uploadImages") %></label>
                <input type="file" id="images" multiple accept="image/*" />
                <ul id="image-list" class="file-list"></ul>
                <div id="hidden-image-inputs"></div>
                <div id="hidden-kept-images"></div>
              </div>

              <div class="item">
                <input type="submit" value="<%= langBundle.getString(pageName + ".addProduct") %>" />
              </div>
            </div>

            <input type="hidden" id="action" name="action" value="add" />
          </form>
        </div>

        <div class="product-list-container">
          <div class="product-list-title-header">
            <span class="product-list-title"><%= langBundle.getString(pageName + ".productCatalogue") %></span>
            <span class="refresh-list-span">
              <label for="refresh-list"><%= langBundle.getString(pageName + ".updateList") %></label>
              <button id="refresh-list"></button>
            </span>
          </div>
          <div class="product-list-header">
            <span><%= langBundle.getString(pageName + ".brand") %></span>
            <span><%= langBundle.getString(pageName + ".model") %></span>
            <span><%= langBundle.getString(pageName + ".description") %></span>
            <span><%= langBundle.getString(pageName + ".variants") %></span>
            <span><%= langBundle.getString(pageName + ".actions") %></span>
          </div>
        </div>
      </div>

      <%@ include file="/WEB-INF/includes/popup.jspf" %>
    </main>

    <script>
      const headerTitleAdd = "<%= langBundle.getString(pageName + ".addProd") %>";
      const headerTitleEdit = "<%= langBundle.getString(pageName + ".editProd") %>";
      const headerTitleProduct = "<%= langBundle.getString(pageName + ".product") %>";

      const variantTitle = "<%= langBundle.getString(pageName + ".variantTitle") %>";
      const popUpTitle = "<%= langBundle.getString(pageName + ".popUpTitle") %>";
      const popUpMessageNoVariants = "<%= langBundle.getString(pageName + ".popUpMessageNoVariants") %>";

      const deletePhrase = "<%= langBundle.getString(pageName + ".deletePhrase") %>";

      const popUpMessageImageLimit = "<%= langBundle.getString(pageName + ".popUpMessageImageLimit") %>";
      const popUpMessageFieldErrors = "<%= langBundle.getString(pageName + ".popUpMessageFieldErrors") %>";

      const storageError = "<%= langBundle.getString(pageName + ".storageError") %>";
      const stockError = "<%= langBundle.getString(pageName + ".stockError") %>";
      const priceError = "<%= langBundle.getString(pageName + ".priceError") %>";
    </script>
    <script src="js/FormChecker.js"></script>
    <script src="js/AddVariantHandler.js"></script>
    <script src="js/FileListHandler.js"></script>
    <script src="js/ProductListHandler.js"></script>

    <%@ include file="/WEB-INF/includes/footer.jspf" %>
  </body>
</html>