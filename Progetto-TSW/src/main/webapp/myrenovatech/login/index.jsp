<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/includes/langSelector.jsp" %>

<!DOCTYPE html>
<html>
    <% String pageName = "login"; %>
    <%@ include file="/includes/head.jsp" %>
    <body>
        <%@ include file="/includes/header.jsp" %>

        <main class="main-cont">
            <div class="login-container" id="login">
                <h2>Login</h2>
                <form action="${pageContext.request.contextPath}/login-handler" method="post">
                    <div class="div-label"><label for="username">Username</label></div>
                    <input type="text" name="username" id="username" required />
                    <div class="div-label"><label for="pass">Password</label></div>
                    <input type="password" name="pass" id="pass" required />

                    <p>Non sei ancora registrato? <span class="pointer" onclick="formToggle()">Registrati!</span></p>

                    <input type="submit" value="Entra" />
                </form>
            </div>

            <div class="login-container" id="register" style="display: none">
                <h2>Registrati</h2>
                <form action="${pageContext.request.contextPath}/login-handler" method="post">
                    <div class="div-label"><label for="user">Username</label></div>
                    <input type="text" name="username" id="user" required />
                    <div class="div-label"><label for="password">Password</label></div>
                    <input type="password" name="pass" id="password" required />

                    <p>Sei già registrato? Fai il <span class="pointer" onclick="formToggle()">login!</span></p>

                    <input type="submit" value="Entra" />
                </form>
            </div>
        </main>

        <script src="js/formHandler.js"></script>

        <%@ include file="/includes/footer.jsp" %>
    </body>
</html>