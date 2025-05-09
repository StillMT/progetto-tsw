const topBar = document.querySelector('.top-bar');
const mainBar = document.querySelector('.main-bar');
const main = document.querySelector('.main-cont');

window.addEventListener('scroll', () => {
    if (window.scrollY >= topBar.offsetHeight) {
        mainBar.classList.add('fixed');
        main.classList.add('fixed-bar');
    }
    else {
        mainBar.classList.remove('fixed');
        main.classList.remove('fixed-bar');
    }
});