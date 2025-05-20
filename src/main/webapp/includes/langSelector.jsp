<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%
    final String DEFAULT_LANG = "it";
    final String[] SUPPORTED_LANGUAGES = {"it", "en" };

    HttpSession s = request.getSession();
    String lang = (String) s.getAttribute("lang");
    String reqLang = request.getParameter("lang");
    if (lang == null || lang.isBlank() || (reqLang != null && !lang.equals(reqLang))) {
        lang = reqLang;
        if (lang == null || lang.isBlank())
            lang = DEFAULT_LANG;
        else {
            boolean supp = false;
            for (String ln : SUPPORTED_LANGUAGES)
                if (ln.equals(lang)) {
                    supp = true;
                    break;
                }
            if (!supp)
                lang = DEFAULT_LANG;

            s.setAttribute("lang", lang);
        }
    }

    ResourceBundle langBundle = ResourceBundle.getBundle("lang/messages", Locale.forLanguageTag(lang));
%>