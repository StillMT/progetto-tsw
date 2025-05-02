<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/includes/langSelector.jsp" %>

<!DOCTYPE html>
<html>
    <% String pageName = "contactUs"; %>
    <%@ include file="/includes/head.jsp" %>
    <body>
        <%@ include file="/includes/header.jsp" %>

        <main class="main-cont">
            <div class="contact-container">
                <div class="titles">
                    <h2>Contattaci</h2>
                    <h4>Contact Form</h4>
                </div>

                <form action="#" method="post" enctype="multipart/form-data">
                    <div class="div-label"><label for="requestReason">Motivo della richiesta</label><span class="required">*</span></div>
                    <select id="requestReason" name="requestReason" required>
                        <option value="track">Traccia un ordine</option>
                        <option value="modify">Modifica di un ordine</option>
                        <option value="return">Restituire un articolo</option>
                        <option value="functProblems">Problemi funzionali</option>
                        <option value="renovatechCare">ReNovaTech Care</option>
                        <option value="other">Altro</option>
                    </select>

                    <div class="div-label"><label for="orderId">Numero d'ordine</label></div>
                    <input type="number" maxlength="12" inputmode="numeric" placeholder="123-456-7890" pattern="\d{3}-\d{3}-\d{4}" id="orderId" name="orderId" />

                    <div class="div-label"><label for="object">Oggetto</label><span class="required">*</span></div>
                    <input type="text" placeholder="Breve descrizione del tuo problema" maxlength="100" name="object" id="object" required />

                    <div class="div-label"><label for="description">Descrivi il tuo problema</label><span class="required">*</span></div>
                    <textarea name="description" cols="50" rows="10" id="description" placeholder="Aggiungi dettagli riguardo il tuo problema" required></textarea>

                    <div class="personal-informations">
                        <div class="p-info-col">
                            <div>
                                <div class="div-label"><label for="name">Nome e Cognome</label><span class="required">*</span></div>
                                <input type="text" id="name" placeholder="Mario Rossi" required />
                            </div>
                            <div>
                                <div class="div-label"><label for="country">Paese</label><span class="required">*</span></div>
                                <select id="country" name="country" required>
                                    <option value="af">Afghanistan</option>
                                    <option value="al">Albania</option>
                                    <option value="dz">Algeria</option>
                                    <option value="ad">Andorra</option>
                                    <option value="ao">Angola</option>
                                    <option value="ag">Antigua e Barbuda</option>
                                    <option value="ar">Argentina</option>
                                    <option value="am">Armenia</option>
                                    <option value="au">Australia</option>
                                    <option value="at">Austria</option>
                                    <option value="az">Azerbaigian</option>
                                    <option value="bs">Bahamas</option>
                                    <option value="bh">Bahrein</option>
                                    <option value="bd">Bangladesh</option>
                                    <option value="bb">Barbados</option>
                                    <option value="by">Bielorussia</option>
                                    <option value="be">Belgio</option>
                                    <option value="bz">Belize</option>
                                    <option value="bj">Benin</option>
                                    <option value="bt">Bhutan</option>
                                    <option value="bo">Bolivia</option>
                                    <option value="ba">Bosnia ed Erzegovina</option>
                                    <option value="bw">Botswana</option>
                                    <option value="br">Brasile</option>
                                    <option value="bn">Brunei</option>
                                    <option value="bg">Bulgaria</option>
                                    <option value="bf">Burkina Faso</option>
                                    <option value="bi">Burundi</option>
                                    <option value="kh">Cambogia</option>
                                    <option value="cm">Camerun</option>
                                    <option value="ca">Canada</option>
                                    <option value="cv">Capo Verde</option>
                                    <option value="cf">Repubblica Centrafricana</option>
                                    <option value="td">Ciad</option>
                                    <option value="cl">Cile</option>
                                    <option value="cn">Cina</option>
                                    <option value="co">Colombia</option>
                                    <option value="km">Comore</option>
                                    <option value="cg">Congo</option>
                                    <option value="cd">Repubblica Democratica del Congo</option>
                                    <option value="cr">Costa Rica</option>
                                    <option value="ci">Costa d'Avorio</option>
                                    <option value="hr">Croazia</option>
                                    <option value="cu">Cuba</option>
                                    <option value="cy">Cipro</option>
                                    <option value="cz">Repubblica Ceca</option>
                                    <option value="dk">Danimarca</option>
                                    <option value="dj">Gibuti</option>
                                    <option value="dm">Dominica</option>
                                    <option value="do">Repubblica Dominicana</option>
                                    <option value="ec">Ecuador</option>
                                    <option value="eg">Egitto</option>
                                    <option value="sv">El Salvador</option>
                                    <option value="gq">Guinea Equatoriale</option>
                                    <option value="er">Eritrea</option>
                                    <option value="ee">Estonia</option>
                                    <option value="sz">Eswatini</option>
                                    <option value="et">Etiopia</option>
                                    <option value="fj">Figi</option>
                                    <option value="fi">Finlandia</option>
                                    <option value="fr">Francia</option>
                                    <option value="ga">Gabon</option>
                                    <option value="gm">Gambia</option>
                                    <option value="ge">Georgia</option>
                                    <option value="de">Germania</option>
                                    <option value="gh">Ghana</option>
                                    <option value="gr">Grecia</option>
                                    <option value="gd">Grenada</option>
                                    <option value="gt">Guatemala</option>
                                    <option value="gn">Guinea</option>
                                    <option value="gw">Guinea-Bissau</option>
                                    <option value="gy">Guyana</option>
                                    <option value="ht">Haiti</option>
                                    <option value="hn">Honduras</option>
                                    <option value="hu">Ungheria</option>
                                    <option value="is">Islanda</option>
                                    <option value="in">India</option>
                                    <option value="id">Indonesia</option>
                                    <option value="ir">Iran</option>
                                    <option value="iq">Iraq</option>
                                    <option value="ie">Irlanda</option>
                                    <option value="il">Israele</option>
                                    <option value="it">Italia</option>
                                    <option value="jm">Giamaica</option>
                                    <option value="jp">Giappone</option>
                                    <option value="jo">Giordania</option>
                                    <option value="kz">Kazakistan</option>
                                    <option value="ke">Kenya</option>
                                    <option value="ki">Kiribati</option>
                                    <option value="kp">Corea del Nord</option>
                                    <option value="kr">Corea del Sud</option>
                                    <option value="kw">Kuwait</option>
                                    <option value="kg">Kirghizistan</option>
                                    <option value="la">Laos</option>
                                    <option value="lv">Lettonia</option>
                                    <option value="lb">Libano</option>
                                    <option value="ls">Lesotho</option>
                                    <option value="lr">Liberia</option>
                                    <option value="ly">Libia</option>
                                    <option value="li">Liechtenstein</option>
                                    <option value="lt">Lituania</option>
                                    <option value="lu">Lussemburgo</option>
                                    <option value="mg">Madagascar</option>
                                    <option value="mw">Malawi</option>
                                    <option value="my">Malaysia</option>
                                    <option value="mv">Maldive</option>
                                    <option value="ml">Mali</option>
                                    <option value="mt">Malta</option>
                                    <option value="mh">Isole Marshall</option>
                                    <option value="mr">Mauritania</option>
                                    <option value="mu">Mauritius</option>
                                    <option value="mx">Messico</option>
                                    <option value="fm">Micronesia</option>
                                    <option value="md">Moldavia</option>
                                    <option value="mc">Monaco</option>
                                    <option value="mn">Mongolia</option>
                                    <option value="me">Montenegro</option>
                                    <option value="ma">Marocco</option>
                                    <option value="mz">Mozambico</option>
                                    <option value="mm">Myanmar</option>
                                    <option value="na">Namibia</option>
                                    <option value="nr">Nauru</option>
                                    <option value="np">Nepal</option>
                                    <option value="nl">Paesi Bassi</option>
                                    <option value="nz">Nuova Zelanda</option>
                                    <option value="ni">Nicaragua</option>
                                    <option value="ne">Niger</option>
                                    <option value="ng">Nigeria</option>
                                    <option value="mk">Macedonia del Nord</option>
                                    <option value="no">Norvegia</option>
                                    <option value="om">Oman</option>
                                    <option value="pk">Pakistan</option>
                                    <option value="pw">Palau</option>
                                    <option value="pa">Panama</option>
                                    <option value="pg">Papua Nuova Guinea</option>
                                    <option value="py">Paraguay</option>
                                    <option value="pe">Perù</option>
                                    <option value="ph">Filippine</option>
                                    <option value="pl">Polonia</option>
                                    <option value="pt">Portogallo</option>
                                    <option value="qa">Qatar</option>
                                    <option value="ro">Romania</option>
                                    <option value="ru">Russia</option>
                                    <option value="rw">Ruanda</option>
                                    <option value="kn">Saint Kitts e Nevis</option>
                                    <option value="lc">Saint Lucia</option>
                                    <option value="vc">Saint Vincent e Grenadine</option>
                                    <option value="ws">Samoa</option>
                                    <option value="sm">San Marino</option>
                                    <option value="st">São Tomé e Príncipe</option>
                                    <option value="sa">Arabia Saudita</option>
                                    <option value="sn">Senegal</option>
                                    <option value="rs">Serbia</option>
                                    <option value="sc">Seychelles</option>
                                    <option value="sl">Sierra Leone</option>
                                    <option value="sg">Singapore</option>
                                    <option value="sk">Slovacchia</option>
                                    <option value="si">Slovenia</option>
                                    <option value="sb">Isole Salomone</option>
                                    <option value="so">Somalia</option>
                                    <option value="za">Sudafrica</option>
                                    <option value="ss">Sud Sudan</option>
                                    <option value="es">Spagna</option>
                                    <option value="lk">Sri Lanka</option>
                                    <option value="sd">Sudan</option>
                                    <option value="sr">Suriname</option>
                                    <option value="se">Svezia</option>
                                    <option value="ch">Svizzera</option>
                                    <option value="sy">Siria</option>
                                    <option value="tw">Taiwan</option>
                                    <option value="tj">Tagikistan</option>
                                    <option value="tz">Tanzania</option>
                                    <option value="th">Thailandia</option>
                                    <option value="tl">Timor Est</option>
                                    <option value="tg">Togo</option>
                                    <option value="to">Tonga</option>
                                    <option value="tt">Trinidad e Tobago</option>
                                    <option value="tn">Tunisia</option>
                                    <option value="tr">Turchia</option>
                                    <option value="tm">Turkmenistan</option>
                                    <option value="tv">Tuvalu</option>
                                    <option value="ug">Uganda</option>
                                    <option value="ua">Ucraina</option>
                                    <option value="ae">Emirati Arabi Uniti</option>
                                    <option value="gb">Regno Unito</option>
                                    <option value="us">Stati Uniti</option>
                                    <option value="uy">Uruguay</option>
                                    <option value="uz">Uzbekistan</option>
                                    <option value="vu">Vanuatu</option>
                                    <option value="va">Città del Vaticano</option>
                                    <option value="ve">Venezuela</option>
                                    <option value="vn">Vietnam</option>
                                    <option value="ye">Yemen</option>
                                    <option value="zm">Zambia</option>
                                    <option value="zw">Zimbabwe</option>
                                </select>
                            </div>
                        </div>
                        <div class="p-info-col">
                            <div>
                                <div class="div-label"><label for="email">E-Mail</label><span class="required">*</span></div>
                                <input type="email" id="email" placeholder="example@example.com" required />
                            </div>
                            <div>
                                <div class="div-label"><label for="cell">Cellulare</label></div>
                                <input type="tel" id="cell" placeholder="1234567890" />
                            </div>
                        </div>
                    </div>

                    <div class="div-label">Aggiungi un file se necessario</div>
                    <input type="file" name="opt-file" />

                    <input type="submit" />
                </form>
            </div>
        </main>

        <%@ include file="/includes/footer.jsp" %>
    </body>
</html>