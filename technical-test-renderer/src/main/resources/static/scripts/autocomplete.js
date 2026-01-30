document.addEventListener('DOMContentLoaded', (event) => {
    const searchInputs = document.querySelectorAll(".search");

    searchInputs.forEach(input => {
        input.addEventListener('input', function(e) {
            const name = e.target.value;
            const listId = this.getAttribute('list');
            const dataList = document.getElementById(listId);

            if(name.length < 2) {
                dataList.innerHTML = '';
                return;
            }
            fetch(`http://localhost:8086/airport/${encodeURIComponent(name)}`)
                .then(response => response.json())
                .then(data => {
                    dataList.innerHTML = '';
                    data.forEach(item => {
                        const option = document.createElement('option');
                        option.value = item.iata;
                        option.textContent = item.name+" ("+item.iata+") - "+item.country;
                        dataList.appendChild(option);
                    });
                });
        });
    });
});