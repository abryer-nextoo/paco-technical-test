/**
 * Autocomplétion des champs aéroport : interroge l'API dès deux caractères saisis
 * et affiche les suggestions dans une datalist associée à chaque input.
 */
document.addEventListener('DOMContentLoaded', () => {
    const searchInputs = document.querySelectorAll('.search');

    searchInputs.forEach(input => {
        input.addEventListener('input', async (e) => {
            const name = e.target.value.trim();
            const dataList = document.getElementById(input.getAttribute('list'));

            if(name.length < 2) {
                dataList.innerHTML = '';
                return;
            }

            try {
                const response = await fetch(`/airport/${encodeURIComponent(name)}`);
                const airports = await response.json();

                dataList.innerHTML = airports
                    .map(airport => `<option value="${airport.iata}">${airport.name} (${airport.iata}) - ${airport.country}</option>`)
                    .join('');
            } catch (error) {
                console.error('Erreur lors de la récupération des aéroports:', error);
                dataList.innerHTML = '';
            }
        });
    });
});