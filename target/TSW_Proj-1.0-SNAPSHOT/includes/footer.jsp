<% pageName = "footer"; %>

<footer class="footer">
    <div class="footer-container">
        <div class="upper-footer">
            <div class="footer-column">
                <span class="footer-title"><%= langBundle.getString(pageName + ".paymentMethods") %></span>
                <div class="brands">
                    <img src="${pageContext.request.contextPath}/GeneralData/imgs/brands/paypal.png" alt="PayPal">
                    <img src="${pageContext.request.contextPath}/GeneralData/imgs/brands/postepay.png" alt="PostePay">
                    <img src="${pageContext.request.contextPath}/GeneralData/imgs/brands/visa.png" alt="Visa">
                    <img src="${pageContext.request.contextPath}/GeneralData/imgs/brands/mastercard.png" alt="Mastercard">
                    <img src="${pageContext.request.contextPath}/GeneralData/imgs/brands/visa-electron.png" alt="Visa Electron">
                    <img src="${pageContext.request.contextPath}/GeneralData/imgs/brands/applepay.png" alt="Apple Pay">
                </div>
                <span class="footer-title"><%= langBundle.getString(pageName + ".shipping") %></span>
                <div class="brands">
                    <img src="${pageContext.request.contextPath}/GeneralData/imgs/brands/ups.png" alt="UPS">
                    <img src="${pageContext.request.contextPath}/GeneralData/imgs/brands/brt.png" alt="BRT">
                </div>
            </div>

            <div class="footer-column">
                <span class="footer-title"><%= langBundle.getString(pageName + ".needHelp") %></span>
                <ul>
                    <li><a href="#"><%= langBundle.getString(pageName + ".shipment") %></a></li>
                    <li><a href="#"><%= langBundle.getString(pageName + ".returns") %></a></li>
                    <li><a href="#"><%= langBundle.getString(pageName + ".ReNovaTechCare") %></a></li>
                    <li><a href="#"><%= langBundle.getString(pageName + ".reviews") %></a></li>
                    <li><a href="#"><%= langBundle.getString(pageName + ".paymentMethods") %></a></li>
                </ul>
            </div>

            <div class="footer-column">
                <span class="footer-title"><%= langBundle.getString(pageName + ".moreAbout") %> ReNovaTech</span>
                <ul>
                    <li><a href="#"><%= langBundle.getString(pageName + ".howOurServiceWork") %></a></li>
                    <li><a href="#"><%= langBundle.getString(pageName + ".workWithUs") %></a></li>
                </ul>
            </div>
            <div class="footer-column">
                <span class="footer-title"><%= langBundle.getString(pageName + ".doYouHaveAquestion") %></span>
                <p><%= langBundle.getString(pageName + ".pleaseSeeOur") %> <a href="${pageContext.request.contextPath}/contact-us/" style="text-decoration: underline;"><%= langBundle.getString(pageName + ".contactForm") %></a>.</p>
                <br>
                <div class="language">
                    <label for="lang"><%= langBundle.getString(pageName + ".language") %></label>
                    <select id="lang" name="lang">
                        <%
                            String sessLang = (String) session.getAttribute("lang");
                            for (String lan : SUPPORTED_LANGUAGES) {
                                String str = "\"" + lan + "\"" + (lan.equals(sessLang) ? " selected" : "");
                        %>
                        <option value=<%= str %>><%= langBundle.getString(lan) %></option>
                        <%
                            }
                        %>
                    </select>
                </div>
            </div>
        </div>
    </div>
    <div class="footer-underneath">
        <span>Copyright &copy; 2025 ReNovaTech. <%= langBundle.getString(pageName + ".allRightReserved") %></span>
        <span><a href="#"><%= langBundle.getString(pageName + ".termsAndConditions") %></a></span>
    </div>

    <script src="${pageContext.request.contextPath}/GeneralData/js/FooterLangHandler.js"></script>
</footer>

<script src="${pageContext.request.contextPath}/GeneralData/js/HeaderNavBarHandler.js"></script>