<%@ page contentType="text/html;charset=UTF-8" %>

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
                        case "wrong_data":
                            desc = langBundle.getString(pageName + ".error0");
                            break;

                        case "invalid_data":
                            desc = langBundle.getString(pageName + ".error1");
                            break;

                        case "contact_us":
                            desc = langBundle.getString(pageName + ".error2");
                            break;

                        case "auth":
                            desc = langBundle.getString(pageName + ".error3");
                            break;

                        default:
                            error = null;
                    }
            %>

            <div class="login-container" id="login" <%= error != null ? (error.equals("wrong_data") || error.equals("auth") ? "" : "style=\"display: none\"") : "" %>>

                <% if (error != null && (error.equals("wrong_data") || error.equals("auth"))) { %>
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

                    <p class="toggle-paragraph"><%= langBundle.getString(pageName + ".notRegistered") %> <span class="pointer" onclick="formToggle()"><%= langBundle.getString(pageName + ".register") %></span></p>

                    <input type="submit" value="<%= langBundle.getString(pageName + ".loginBtn") %>" />
                </form>
            </div>

            <div class="login-container" id="register" <%= error != null ? (error.equals("invalid_data") || error.equals("contact_us") ? "" : "style=\"display: none\"") : "style=\"display: none\"" %>>

                <% if (error != null && (error.equals("invalid_data") || error.equals("contact_us"))) { %>
                    <div class="error-box">
                        <%= desc %>
                    </div>
                <%
                    }
                %>

                <h2><%= langBundle.getString(pageName + ".register") %></h2>
                <form action="${pageContext.request.contextPath}/register" method="post">
                    <div class="div-label"><label for="fullName"><%= langBundle.getString(pageName + ".fullName") %></label><span class="required"></span></div>
                    <input type="text" name="fullName" maxlength="50" id="fullName" required />
                    <div class="form-error" id="fullNameError">
                        <p><%= langBundle.getString(pageName + ".enterBothFirstAndLastName") %></p>
                        <p><%= langBundle.getString(pageName + ".maximum50Characters") %></p>
                    </div>

                    <div class="div-label"><label for="username">Username</label><span class="required"></span></div>
                    <input type="text" name="username" id="username" minlength="3" maxlength="16" required />
                    <div class="form-error" id="usernameAlreadyExistError">
                        <p><%= langBundle.getString(pageName + ".thisUsernameIsNotAvailable") %></p>
                    </div>
                    <div class="form-error" id="usernameError">
                        <p><%= langBundle.getString(pageName + ".theUsernameMustBeBetween3And10CharactersLong") %></p>
                        <p><%= langBundle.getString(pageName + ".onlyLettersNumbersHyphensAndUnderscoresAllowed") %></p>
                    </div>

                    <div class="div-label"><label for="password">Password</label><span class="required"></span></div>
                    <input type="password" name="pass" minlength="8" maxlength="20" id="password" required />
                    <div class="form-error" id="passwordError">
                        <p><%= langBundle.getString(pageName + ".maximum20CharactersAtLeastOneSpecialCharacter") %></p>
                        <p><%= langBundle.getString(pageName + ".noSpaces") %></p>
                    </div>

                    <div class="div-label"><label for="rep-password"><%= langBundle.getString(pageName + ".repeatPassword") %></label><span class="required"></span></div>
                    <input type="password" name="rep-pass" id="rep-password" required />
                    <div class="form-error" id="repPasswordError">
                        <p><%= langBundle.getString(pageName + ".theTwoPasswordsMustMatch") %></p>
                    </div>

                    <div class="div-label"><label for="email">Email</label><span class="required"></span></div>
                    <input type="email" name="email" maxlength="254" id="email" required />
                    <div class="form-error" id="emailAlreadyExistError">
                        <p><%= langBundle.getString(pageName + ".anAccountHasAlreadyBeenCreatedWithThisEmailAddress") %></p>
                    </div>
                    <div class="form-error" id="emailError">
                        <p><%= langBundle.getString(pageName + ".invalidEmail") %></p>
                    </div>

                    <div class="div-label"><label for="cell"><%= langBundle.getString(pageName + ".mobilePhone") %></label><span class="required"></span></div>
                    <input type="tel" name="cell" id="cell" maxlength="15" required />
                    <div class="form-error" id="cellAlreadyExistError">
                        <p><%= langBundle.getString(pageName + ".mobileNumberAlreadyRegistered") %></p>
                    </div>
                    <div class="form-error" id="cellError">
                        <p><%= langBundle.getString(pageName + ".invalidMobileNumber") %></p>
                    </div>

                    <%@ include file="/WEB-INF/includes/country-selector.jspf" %>

                    <p class="toggle-paragraph"><%= langBundle.getString(pageName + ".AreYouAlreadyRegisteredDoYouWantTo") %> <span class="pointer" onclick="formToggle()"><%= langBundle.getString(pageName + ".login") %></span></p>

                    <input type="submit" value="<%= langBundle.getString(pageName + ".registerBtn") %>" />
                </form>
            </div>

            <%@ include file="/WEB-INF/includes/popup.jspf" %>

        </main>

        <script>
            const popupTitle = "<%= langBundle.getString(pageName + ".popupTitle") %>";

            const popupMessageInvalidFields = "<%= langBundle.getString(pageName + ".popupMessageInvalidFields") %>";
            const popupMessageCountrySure = "<%= langBundle.getString(pageName + ".popupMessageCountrySure") %>";
        </script>
        <script src="js/FormChecker.js"></script>
        <script src="js/FormToggler.js"></script>

        <%@ include file="/WEB-INF/includes/footer.jspf" %>
    </body>
</html>