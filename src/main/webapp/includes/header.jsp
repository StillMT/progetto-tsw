<%@ page import="it.unisa.tsw_proj.model.bean.UserBean" %>
<%@ page import="it.unisa.tsw_proj.model.bean.CartBean" %>
<%
    pageName = "header";

    final String[] PAGES = { langBundle.getString(pageName + ".home"), langBundle.getString(pageName + ".catalogue"),
            langBundle.getString(pageName + ".swap"), langBundle.getString(pageName + ".sell"), langBundle.getString(pageName + ".aboutUs"),
            langBundle.getString(pageName + ".reviews"), langBundle.getString(pageName + ".contactUs") };
    final String[] PAGES_LINKS = { "/", "/catalogue/", "/swap/", "/sell/", "/about-us/", "/reviews/", "/contact-us/" };
%>

<header>
    <div class="top-bar">
        <div class="top-bar-container">
            <span>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-wrench-icon lucide-wrench"><path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"/></svg>
                <%= langBundle.getString(pageName + ".refurbishedBy") %> ReNovaTech
            </span>

            <span>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-star-icon lucide-star"><path d="M11.525 2.295a.53.53 0 0 1 .95 0l2.31 4.679a2.123 2.123 0 0 0 1.595 1.16l5.166.756a.53.53 0 0 1 .294.904l-3.736 3.638a2.123 2.123 0 0 0-.611 1.878l.882 5.14a.53.53 0 0 1-.771.56l-4.618-2.428a2.122 2.122 0 0 0-1.973 0L6.396 21.01a.53.53 0 0 1-.77-.56l.881-5.139a2.122 2.122 0 0 0-.611-1.879L2.16 9.795a.53.53 0 0 1 .294-.906l5.165-.755a2.122 2.122 0 0 0 1.597-1.16z"/></svg>
                <%= langBundle.getString(pageName + ".aimForTop") %>
            </span>

            <span>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-zap-icon lucide-zap"><path d="M4 14a1 1 0 0 1-.78-1.63l9.9-10.2a.5.5 0 0 1 .86.46l-1.92 6.02A1 1 0 0 0 13 10h7a1 1 0 0 1 .78 1.63l-9.9 10.2a.5.5 0 0 1-.86-.46l1.92-6.02A1 1 0 0 0 11 14z"/></svg>
                <%= langBundle.getString(pageName + ".quality") %>
            </span>
        </div>
    </div>

    <div class="main-bar">
        <div class="main-bar-container">
            <div>
                <a href="${pageContext.request.contextPath}/"><svg class="logo" version="1.0" xmlns="http://www.w3.org/2000/svg" width="1516.000000pt" height="221.000000pt" viewBox="0 0 1516.000000 221.000000" preserveAspectRatio="xMidYMid meet"><g transform="translate(0.000000,221.000000) scale(0.100000,-0.100000)" fill="#000000" stroke="none"><path d="M32 1099 l3 -1064 147 -3 148 -3 2 413 3 413 226 3 c261 3 224 19 320 -138 36 -58 150 -237 254 -398 l189 -292 60 0 c119 0 286 13 286 22 0 5 -114 179 -254 386 -139 207 -259 388 -266 401 -14 27 -2 41 37 41 32 0 179 78 249 132 96 74 174 180 210 288 27 78 27 382 1 450 -63 162 -190 296 -338 356 -89 36 -195 43 -767 51 l-512 6 2 -1064z m1118 725 c78 -37 141 -99 178 -174 23 -47 27 -68 27 -140 0 -74 -4 -93 -32 -153 -36 -75 -118 -169 -174 -198 -63 -32 -154 -39 -491 -39 l-328 0 0 371 0 370 378 -3 377 -3 65 -31z"/><path d="M3286 1063 c3 -555 9 -1011 13 -1014 3 -4 68 -9 144 -12 l137 -4 0 637 c0 607 6 850 19 850 8 0 340 -500 706 -1063 l277 -428 132 3 131 3 3 1018 2 1017 -150 0 -150 0 0 -643 c0 -636 -5 -830 -21 -825 -5 2 -165 246 -357 543 -191 297 -404 627 -472 732 l-125 193 -148 0 -148 0 7 -1007z"/><path d="M9310 1928 l0 -141 315 6 315 7 2 -883 3 -882 148 -3 147 -3 0 879 0 879 311 6 c172 4 313 8 314 9 1 2 5 63 8 136 l6 132 -784 0 -785 0 0 -142z"/><path d="M13845 2058 c-3 -7 -4 -465 -3 -1018 l3 -1005 148 -3 147 -3 0 328 c0 181 5 405 10 499 8 157 11 177 35 225 15 29 53 78 84 109 68 69 123 90 230 90 63 0 87 -5 136 -28 65 -31 126 -91 149 -147 34 -81 39 -151 45 -610 l6 -460 138 -3 137 -3 0 443 c0 570 -10 692 -68 818 -33 72 -138 176 -215 213 -219 106 -476 50 -629 -137 l-53 -65 -3 385 -2 384 -145 0 c-107 0 -147 -3 -150 -12z"/><path d="M2335 1550 c-94 -15 -184 -46 -254 -87 -129 -77 -263 -237 -302 -363 -28 -91 -42 -253 -36 -405 9 -197 38 -283 136 -405 70 -87 173 -173 260 -217 l74 -38 205 -3 c235 -4 263 2 372 73 108 71 190 166 259 302 37 74 39 73 -121 73 l-141 0 -74 -79 c-101 -109 -155 -135 -278 -136 -78 0 -98 4 -150 28 -101 47 -168 124 -223 259 -39 94 -40 113 -5 135 25 16 70 18 514 23 410 4 489 7 504 20 16 13 17 28 12 145 -6 145 -20 204 -70 308 -121 255 -408 409 -682 367z m190 -245 c33 -9 81 -30 107 -47 105 -67 198 -207 198 -300 l0 -48 -287 0 c-159 0 -338 3 -400 7 l-111 6 15 51 c22 75 78 181 121 228 93 102 219 139 357 103z"/><path d="M5735 1555 c-5 -2 -44 -11 -85 -20 -351 -80 -573 -373 -572 -755 1 -322 143 -563 407 -690 169 -82 332 -101 505 -60 259 63 448 244 532 514 20 61 23 93 23 236 0 161 -1 167 -33 260 -90 259 -274 431 -530 496 -83 21 -210 31 -247 19z m257 -307 c144 -66 250 -263 249 -463 0 -215 -99 -392 -260 -469 -61 -29 -74 -31 -176 -31 -101 0 -115 2 -169 29 -76 37 -166 129 -206 209 -59 121 -63 327 -9 472 30 81 92 167 154 215 81 63 111 71 245 67 99 -3 123 -7 172 -29z"/><path d="M8593 1549 c-216 -36 -400 -191 -461 -389 -11 -36 -23 -77 -26 -92 l-6 -28 117 1 c156 1 162 3 196 69 36 72 119 155 177 177 54 22 222 24 288 5 82 -25 152 -127 160 -233 4 -52 2 -57 -26 -81 -36 -30 -80 -39 -346 -73 -327 -41 -437 -76 -516 -161 -148 -161 -129 -416 43 -586 99 -98 231 -148 390 -148 164 0 302 53 405 156 l62 62 0 -99 0 -99 150 0 150 0 -1 108 c-2 375 -20 1023 -29 1062 -44 177 -182 305 -373 344 -82 17 -266 20 -354 5z m425 -805 c18 -5 22 -13 22 -48 0 -23 -7 -69 -15 -101 -39 -149 -115 -253 -223 -303 -52 -24 -68 -27 -172 -27 -98 0 -121 3 -155 22 -76 40 -115 107 -115 196 0 97 62 177 169 218 74 28 428 59 489 43z"/><path d="M11263 1550 c-177 -32 -334 -139 -437 -297 -95 -148 -129 -269 -130 -463 -1 -164 17 -247 79 -376 75 -155 234 -304 390 -365 54 -21 76 -23 230 -24 149 0 179 3 240 22 85 27 175 80 233 136 71 69 191 264 177 287 -4 6 -66 10 -142 10 l-136 0 -74 -75 c-51 -51 -95 -85 -141 -107 -60 -30 -75 -33 -152 -33 -71 0 -95 5 -142 27 -132 61 -232 207 -262 380 l-7 37 514 3 c480 3 515 4 541 22 l29 18 -6 117 c-10 212 -68 353 -202 487 -132 133 -267 193 -450 199 -55 2 -123 0 -152 -5z m303 -269 c121 -57 244 -222 244 -328 l0 -45 -372 4 c-389 4 -428 8 -428 47 0 64 113 240 193 300 69 52 105 61 210 56 72 -3 101 -9 153 -34z"/><path d="M12820 1529 c-73 -13 -221 -86 -295 -146 -114 -93 -212 -243 -244 -375 -34 -141 -32 -344 4 -483 46 -178 191 -352 379 -452 l71 -38 245 0 245 0 65 32 c77 38 176 119 225 185 48 64 127 225 129 261 1 26 -4 31 -32 39 -18 5 -84 7 -148 6 l-116 -3 -21 -55 c-14 -39 -38 -73 -81 -116 -86 -85 -132 -105 -242 -105 -188 0 -309 77 -390 246 -68 142 -83 339 -36 470 29 78 102 186 151 219 86 60 202 84 317 66 129 -20 224 -81 270 -172 15 -30 35 -53 50 -58 14 -6 82 -10 150 -10 l126 0 -7 33 c-33 152 -123 284 -256 373 -113 75 -159 87 -344 90 -88 2 -185 -2 -215 -7z"/><path d="M6576 1512 c-3 -6 86 -251 200 -544 113 -293 239 -620 279 -726 40 -107 77 -198 82 -203 5 -5 77 -8 163 -7 l154 3 153 405 c292 773 393 1054 385 1062 -5 5 -66 7 -136 6 l-128 -3 -167 -465 c-216 -601 -254 -701 -262 -692 -4 4 -75 196 -159 427 -200 551 -267 722 -287 729 -33 13 -270 19 -277 8z"/></g></svg></a>
            </div>

            <div>
                <nav>
                    <%for (int i = 0; i < PAGES.length; i++) {%>
                        <a href="<%= PAGES_LINKS[i] %>" <%= request.getRequestURI().equals(PAGES_LINKS[i]) ? "class=\"selected\"" : "" %>><%= PAGES[i] %></a>
                    <%}%>
                </nav>
            </div>

            <div class="user-items">
                <div class="dropdown-menu-wrapper">
                    <a href="${pageContext.request.contextPath}/myrenovatech">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-user-icon lucide-user"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>

                        <%
                            UserBean us = (UserBean) request.getSession().getAttribute("user");
                            if (us != null) {
                        %>
                            <span><%= langBundle.getString(pageName + ".welcome") %>, <%= us.getUsername() %></span>
                            <svg width="20px" height="20px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M5.70711 9.71069C5.31658 10.1012 5.31658 10.7344 5.70711 11.1249L10.5993 16.0123C11.3805 16.7927 12.6463 16.7924 13.4271 16.0117L18.3174 11.1213C18.708 10.7308 18.708 10.0976 18.3174 9.70708C17.9269 9.31655 17.2937 9.31655 16.9032 9.70708L12.7176 13.8927C12.3271 14.2833 11.6939 14.2832 11.3034 13.8927L7.12132 9.71069C6.7308 9.32016 6.09763 9.32016 5.70711 9.71069Z" fill="#0F0F0F"/></svg>
                        <%
                            }
                        %>
                    </a>

                    <%
                        if (us != null) {
                    %>
                        <div class="dropdown-menu">
                            <a href="#">Profilo</a>
                            <a href="#">Ordini</a>
                            <a href="${pageContext.request.contextPath}/logout">Logout</a>
                        </div>
                    <%
                        }
                    %>
                </div>
                <a href="${pageContext.request.contextPath}/myrenovatech/cart" class="cart"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-shopping-cart-icon lucide-shopping-cart"><circle cx="8" cy="21" r="1"/><circle cx="19" cy="21" r="1"/><path d="M2.05 2.05h2l2.66 12.42a2 2 0 0 0 2 1.58h9.78a2 2 0 0 0 1.95-1.57l1.65-7.43H5.12"/></svg></a>
                <span class="cart-items-counter"><%= us != null ? ((CartBean) request.getSession().getAttribute("cart")).getCartItemsCount() : 0 %></span>
            </div>
        </div>
    </div>
</header>