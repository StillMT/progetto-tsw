<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/WEB-INF/includes/lang-selector.jspf" %>

<!DOCTYPE html>
<html>
  <% final String pageName = "adminCatalogue"; %>
  <%@ include file="/WEB-INF/includes/head.jspf" %>
  <body>
    <%@ include file="/WEB-INF/includes/header.jspf" %>

    <main class="main-cont">
      <div class="cat-container">
        <div class="add-product-container">
          <span class="add-product-title">Aggiungi/Modifica prodotto</span>
          <form action="#" method="post" enctype="multipart/form-data">
            <div class="column">
              <div class="item">
                <label for="brand">Brand</label>
                <input type="text" id="brand" name="brand" placeholder="Apple" maxlength="30" />
              </div>

              <div class="item">
                <label for="description">Descrizione</label>
                <textarea name="description" id="description" rows="4" placeholder="Breve descrizione del prodotto"></textarea>
              </div>
            </div>

            <div class="column">
              <div class="item">
                <label for="model">Modello</label>
                <input type="text" id="model" name="model" placeholder="iPhone 14 Pro Max" />
              </div>

              <div class="item">
                <%@ include file="/WEB-INF/includes/category-selector.jspf" %>
              </div>

              <div class="item">
                <input type="button" value="Aggiungi variante" id="add-variant" />
              </div>
            </div>

            <div class="column" id="add-variant-column">
            </div>

            <div class="column">
              <div class="item">
                <label for="images">Carica immagini</label>
                <input type="file" name="images" id="images" multiple accept="image/*" />
              </div>

              <div class="item">
                <input type="submit" value="Aggiungi prodotto" />
              </div>
            </div>
          </form>
        </div>

        <div class="product-list-container">
          <div class="product-list-title-header">
            <span class="product-list-title">Catalogo prodotti</span>
            <span>
              <label for="refresh-list">Aggiorna lista</label>
              <button id="refresh-list"></button>
            </span>
          </div>
          <div class="product-list-header">
            <span>Brand</span>
            <span>Modello</span>
            <span>Descrizione</span>
            <span>Varianti</span>
            <span>Azioni</span>
          </div>
        </div>
      </div>

      <%@ include file="/WEB-INF/includes/popup.jspf" %>
    </main>

    <script src="js/AddVariantHandler.js"></script>
    <script src="js/ProductListHandler.js"></script>

    <%@ include file="/WEB-INF/includes/footer.jspf" %>
  </body>
</html>