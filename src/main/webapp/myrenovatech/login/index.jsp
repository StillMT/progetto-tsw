<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/includes/langSelector.jsp" %>

<!DOCTYPE html>
<html>
    <% String pageName = "login"; %>
    <%@ include file="/includes/head.jsp" %>
    <body>
        <%@ include file="/includes/header.jsp" %>

        <main class="main-cont">
/////////// Inserire ?error
            <div class="login-container" id="login">
                <h2>Login</h2>
                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div class="div-label"><label for="username">Username</label></div>
                    <input type="text" name="username" id="user" required />

                    <div class="div-label"><label for="pass">Password</label></div>
                    <input type="password" name="pass" id="pass" required />

                    <p class="toggle-paragraph">Non sei ancora registrato? <span class="pointer" onclick="formToggle()">Registrati!</span></p>

                    <input type="submit" value="Entra" />
                </form>
            </div>

            <div class="login-container" id="register" style="display: none">
                <h2>Registrati</h2>
                <form action="${pageContext.request.contextPath}/register" method="post">
                    <div class="div-label"><label for="fullName">Nome e cognome</label><span class="required"></span></div>
                    <input type="text" name="fullName" maxlength="50" id="fullName" required />
                    <div class="form-error" id="fullNameError">
                        <p>Immettere sia nome che cognome</p>
                        <p>Massimo 50 caratteri</p>
                    </div>

                    <div class="div-label"><label for="username">Username</label><span class="required"></span></div>
                    <input type="text" name="username" id="username" minlength="3" maxlength="10" required />
                    <div class="form-error" id="usernameAlreadyExistError">
                        <p>Questo username non è disponibile</p>
                    </div>
                    <div class="form-error" id="usernameError">
                        <p>Lo username deve essere lungo dai 3 ai 10 caratteri</p>
                        <p>Consentiti solo lettere, numeri, trattini e trattini bassi</p>
                    </div>

                    <div class="div-label"><label for="password">Password</label><span class="required"></span></div>
                    <input type="password" name="pass" minlength="8" maxlength="16" id="password" required />
                    <div class="form-error" id="passwordError">
                        <p>Massimo 20 caratteri, almeno un carattere speciale</p>
                        <p>No spazi</p>
                    </div>

                    <div class="div-label"><label for="rep-password">Ripeti Password</label><span class="required"></span></div>
                    <input type="password" name="rep-pass" id="rep-password" required />
                    <div class="form-error" id="repPasswordError">
                        <p>Le due password devono coincidere</p>
                    </div>

                    <div class="div-label"><label for="email">Email</label><span class="required"></span></div>
                    <input type="email" name="email" maxlength="50" id="email" required />
                    <div class="form-error" id="emailError">
                        <p>Email non valida</p>
                    </div>

                    <div class="div-label"><label for="cell">Cellulare</label><span class="required"></span></div>
                    <input type="tel" name="cell" id="cell" maxlength="11" required />
                    <div class="form-error" id="cellError">
                        <p>Numero di cellulare non valido</p>
                    </div>

                    <%@ include file="/includes/countrySelector.jsp" %>

                    <p class="toggle-paragraph">Sei già registrato? Fai il <span class="pointer" onclick="formToggle()">login!</span></p>

                    <input type="submit" value="Registrati" />
                </form>
            </div>

            <%@ include file="/includes/popup.jsp" %>

        </main>

        <script src="js/FormChecker.js"></script>
        <script src="js/FormToggler.js"></script>

        <%@ include file="/includes/footer.jsp" %>
    </body>
</html>