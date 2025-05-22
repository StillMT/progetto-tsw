<%@ page import="it.unisa.tsw_proj.utils.SessionSetter" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    if (SessionSetter.isLogged(request)) {
        response.sendRedirect("/");
        return;
    }
%>

<%@ include file="/WEB-INF/includes/lang-selector.jspf" %>

<!DOCTYPE html>
<html>
    <% final String pageName = "login"; %>
    <%@ include file="/WEB-INF/includes/head.jspf" %>
    <body>
        <%@ include file="/WEB-INF/includes/header.jspf" %>

        <main class="main-cont">

            <%
                String error;
                String desc = "";
                if ((error = request.getParameter("error")) != null)
                    switch (error) {
                        case "0":
                            desc = langBundle.getString(pageName + ".error0");
                            break;

                        case "1":
                            desc = langBundle.getString(pageName + ".error1");
                            break;

                        case "2":
                            desc = langBundle.getString(pageName + ".error2");
                            break;

                        default:
                            error = null;
                    }
            %>

            <div class="login-container" id="login" <%= error != null ? (error.equals("0") ? "" : "style=\"display: none\"") : "" %>>

                <% if (error != null && error.equals("0")) { %>
                    <div class="error-box">
                        <%= desc %>
                    </div>
                <%
                    }
                %>

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

            <div class="login-container" id="register" <%= error != null ? (error.equals("1") || error.equals("2") ? "" : "style=\"display: none\"") : "style=\"display: none\"" %>>

                <% if (error != null && (error.equals("1") || error.equals("2"))) { %>
                    <div class="error-box">
                        <%= desc %>
                    </div>
                <%
                    }
                %>

                <h2>Registrati</h2>
                <form action="${pageContext.request.contextPath}/register" method="post">
                    <div class="div-label"><label for="fullName">Nome e cognome</label><span class="required"></span></div>
                    <input type="text" name="fullName" maxlength="50" id="fullName" required />
                    <div class="form-error" id="fullNameError">
                        <p>Immettere sia nome che cognome</p>
                        <p>Massimo 50 caratteri</p>
                    </div>

                    <div class="div-label"><label for="username">Username</label><span class="required"></span></div>
                    <input type="text" name="username" id="username" minlength="3" maxlength="16" required />
                    <div class="form-error" id="usernameAlreadyExistError">
                        <p>Questo username non è disponibile</p>
                    </div>
                    <div class="form-error" id="usernameError">
                        <p>Lo username deve essere lungo dai 3 ai 10 caratteri</p>
                        <p>Consentiti solo lettere, numeri, trattini e trattini bassi</p>
                    </div>

                    <div class="div-label"><label for="password">Password</label><span class="required"></span></div>
                    <input type="password" name="pass" minlength="8" maxlength="20" id="password" required />
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
                    <input type="email" name="email" maxlength="254" id="email" required />
                    <div class="form-error" id="emailAlreadyExistError">
                        <p>È stato creato già un account con questa email</p>
                    </div>
                    <div class="form-error" id="emailError">
                        <p>Email non valida</p>
                    </div>

                    <div class="div-label"><label for="cell">Cellulare</label><span class="required"></span></div>
                    <input type="tel" name="cell" id="cell" maxlength="11" required />
                    <div class="form-error" id="cellError">
                        <p>Numero di cellulare non valido</p>
                    </div>

                    <%@ include file="/WEB-INF/includes/country-selector.jspf" %>

                    <p class="toggle-paragraph">Sei già registrato? Fai il <span class="pointer" onclick="formToggle()">login!</span></p>

                    <input type="submit" value="Registrati" />
                </form>
            </div>

            <%@ include file="/WEB-INF/includes/popup.jspf" %>

        </main>

        <script src="js/FormChecker.js"></script>
        <script src="js/FormToggler.js"></script>

        <%@ include file="/WEB-INF/includes/footer.jspf" %>
    </body>
</html>