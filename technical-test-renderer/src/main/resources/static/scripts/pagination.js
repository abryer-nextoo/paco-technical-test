/**
 * Gère la navigation entre les pages en interceptant les clics sur les liens de pagination.
 * Empêche la navigation si le lien est désactivé, sinon redirige vers la page ciblée
 * en préservant les paramètres de recherche existants.
 */
document.addEventListener('DOMContentLoaded', () => {
    const paginationLinks = document.querySelectorAll('.pagination-link');

    paginationLinks.forEach(link => {
        link.addEventListener('click', (event) => {
            event.preventDefault();
            if (link.closest('.disabled')) {
                return;
            }
            const targetPage = link.dataset.page;
            navigateToPage(targetPage);
        });
    });
});

function navigateToPage(page) {
    const url = new URL(globalThis.location);
    url.searchParams.set('page', page);
    globalThis.location.href = url.toString();
}