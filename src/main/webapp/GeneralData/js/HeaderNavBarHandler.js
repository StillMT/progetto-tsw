const topBar = document.querySelector('.top-bar');
const mainBar = document.querySelector('.main-bar');
const main = document.querySelector('.main-cont');

let lastFixedState = false;
let barHeight = 0;

function measureBarHeight() {
    barHeight = mainBar.offsetHeight;
}

function updateFixedState() {
    const topBarVisible = topBar && window.getComputedStyle(topBar).display !== 'none';
    const threshold = topBarVisible ? topBar.offsetHeight : 0;
    const shouldBeFixed = window.scrollY >= threshold;

    if (shouldBeFixed !== lastFixedState) {
        mainBar.classList.toggle('fixed', shouldBeFixed);
        main.style.paddingTop = shouldBeFixed ? `${barHeight}px` : '';
        lastFixedState = shouldBeFixed;
    }
}

window.addEventListener('scroll', updateFixedState);
window.addEventListener('resize', () => {
    measureBarHeight();
    updateFixedState();
});
window.addEventListener('DOMContentLoaded', () => {
    measureBarHeight();
    updateFixedState();
});

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('mobile-slide-overlay').addEventListener('click', () => toggleMobileMenu());
    document.getElementById('hamburger-icon').addEventListener('click', () => toggleMobileMenu());
});

function toggleMobileMenu() {
    const menu = document.getElementById('mobileMenu');
    const overlay = document.getElementById('mobile-slide-overlay');
    const isActive = menu.classList.contains('active');

    menu.classList.toggle('active', !isActive);
    overlay.classList.toggle('active', !isActive);
}