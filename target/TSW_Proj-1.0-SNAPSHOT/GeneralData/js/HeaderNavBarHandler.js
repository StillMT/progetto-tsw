const topBar = document.querySelector('.top-bar');
const mainBar = document.querySelector('.main-bar');
const main = document.querySelector('.main-cont');

function updateMainOffset() {
    if (mainBar.classList.contains('fixed')) {
        const barHeight = mainBar.offsetHeight;
        main.style.paddingTop = `${barHeight}px`;
    } else {
        main.style.paddingTop = '';
    }
}

window.addEventListener('scroll', () => {
    if (window.scrollY >= topBar.offsetHeight)
        mainBar.classList.add('fixed');
    else
        mainBar.classList.remove('fixed');

    updateMainOffset();
});

window.addEventListener('resize', updateMainOffset);
window.addEventListener('DOMContentLoaded', updateMainOffset);



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