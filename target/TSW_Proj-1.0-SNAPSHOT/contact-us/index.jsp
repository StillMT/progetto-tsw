<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/includes/lang-selector.jspf" %>

<!DOCTYPE html>
<html>
<% final String pageName = "contactUs"; %>
<%@ include file="/WEB-INF/includes/head.jspf" %>
<body>
<%@ include file="/WEB-INF/includes/header.jspf" %>

<main class="main-cont">
    <div class="contact-container">
        <div class="titles">
            <h2><%= langBundle.getString(pageName + ".contactUs") %></h2>
            <h4>Contact Form</h4>
        </div>

        <form action="#" method="post" enctype="multipart/form-data" id="contact-form">
            <div class="div-label"><label for="requestReason"><%= langBundle.getString(pageName + ".reasonForRequest") %></label><span class="required"></span></div>
            <select id="requestReason" name="requestReason" required>
                <option value="track"><%= langBundle.getString(pageName + ".trackAnOrder") %></option>
                <option value="modify"><%= langBundle.getString(pageName + ".editAnOrder") %></option>
                <option value="return"><%= langBundle.getString(pageName + ".returnItem") %></option>
                <option value="functProblems"><%= langBundle.getString(pageName + ".functionalProblems") %></option>
                <option value="renovatechCare"><%= langBundle.getString(pageName + ".ReNovaTechCare") %></option>
                <option value="other"><%= langBundle.getString(pageName + ".other") %></option>
            </select>
            <div class="form-error" id="requestReasonError">Seleziona una motivazione valida.</div>

            <div class="div-label"><label for="orderId"><%= langBundle.getString(pageName + ".orderNumber") %></label></div>
            <input type="text" maxlength="12" inputmode="numeric" placeholder="123-456-7890" id="orderId" name="orderId" />
            <div class="form-error" id="orderIdError">
                Numero d'ordine non valido. Usa il formato 123-456-7890.
            </div>

            <div class="div-label"><label for="object"><%= langBundle.getString(pageName + ".object") %></label><span class="required"></span></div>
            <input type="text" placeholder="<%= langBundle.getString(pageName + ".shortDesc") %>" maxlength="40" name="object" id="object" required />

            <div class="div-label"><label for="description"><%= langBundle.getString(pageName + ".describeYourProblem") %></label><span class="required"></span></div>
            <textarea name="description" cols="50" rows="10" id="description" placeholder="<%= langBundle.getString(pageName + ".longDesc") %>" maxlength="400" required></textarea>

            <div class="personal-informations">
                <div class="p-info-col">
                    <div>
                        <div class="div-label"><label for="name"><%= langBundle.getString(pageName + ".nameAndSurname") %></label><span class="required"></span></div>
                        <input type="text" id="name" placeholder="<%= langBundle.getString(pageName + ".nameAndSurnamePlaceholder") %>" maxlength="30" required />
                        <div class="form-error" id="nameError">Inserisci nome e cognome validi.</div>
                    </div>
                    <div>
                        <%@ include file="/WEB-INF/includes/country-selector.jspf" %>
                    </div>
                </div>
                <div class="p-info-col">
                    <div>
                        <div class="div-label"><label for="email"><%= langBundle.getString(pageName + ".email") %></label><span class="required"></span></div>
                        <input type="email" id="email" placeholder="example@example.com"  maxlength="60" required />
                        <div class="form-error" id="emailError">Inserisci un'email valida.</div>
                    </div>
                    <div>
                        <div class="div-label"><label for="cell"><%= langBundle.getString(pageName + ".mobilePhone") %></label></div>
                        <input type="tel" id="cell" placeholder="1234567890" />
                        <div class="form-error" id="cellError">
                            Numero non valido
                        </div>
                    </div>
                </div>
            </div>

            <div class="div-label"><%= langBundle.getString(pageName + ".addFile") %></div>
            <input type="file" name="opt-file" />

            <input type="submit" value="<%= langBundle.getString(pageName + ".send") %>" />
        </form>
    </div>
</main>

<%@ include file="/WEB-INF/includes/footer.jspf" %>
<script src="js/contactFormValidator.js"></script>
</body>
</html>
