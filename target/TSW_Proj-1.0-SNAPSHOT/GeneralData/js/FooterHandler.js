document.getElementById('lang').addEventListener('change', function () {
    const selectedLang = this.value;
    const url = new URL(window.location.href);

    url.searchParams.set('lang', selectedLang);

    window.location.href = url.toString();
});