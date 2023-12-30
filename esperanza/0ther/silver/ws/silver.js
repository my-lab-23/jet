
// Definiamo i valori di default
const defaultData = {
    success: true,
    base: 'XAG',
    timestamp: 0,
    rates: {
        CNY: 0.0,
        EUR: 0.0,
        RUB: 0.0,
        USD: 0.0
    }
};

// Funzione per visualizzare i dati ricevuti o i valori di default in caso di errore
function displayData(data) {
    const ratesDiv = document.getElementById('exchange-rates');
    const rates = data.rates || defaultData.rates;
    const timestamp = new Date((data.timestamp || defaultData.timestamp) * 1000);
    const options = {
        timeZone: 'Europe/Rome',
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    };
    const formatter = new Intl.DateTimeFormat('it-IT', options);
    const formattedTimestamp = formatter.format(timestamp);
    const html = Object.entries(rates).map(([currency, rate]) => {
        const flagClass = `flag ${currency}`;
        return `
      <div>
        <span class="${flagClass}"></span>
        <span>${currency}</span>
        <span>${rate.toFixed(2)}</span>
      </div>
    `;
    }).join('');
    const errorMessage = data.message ? `Error: ${data.message}. Using default data.` : '';
    ratesDiv.innerHTML = `
    <p>${errorMessage}</p>
    <p id="time">Last updated: ${formattedTimestamp}</p>
    ${html}
  `;
}

// Interrogazione dell'endpoint
fetch('/silver/ws_values/silver')
    .then(response => {
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        return response.json();
    })
    .then(data => {
        displayData(data);
    })
    .catch(error => {
        displayData({ message: error.message });
    });

// Definiamo i valori di default
const defaultData2 = {
    price: 0.0,
    timestamp: new Date(0)
};

// Funzione per visualizzare i dati ricevuti o i valori di default in caso di errore
function displayData2(data) {
    const priceDiv = document.getElementById('price');
    const price = data.price || defaultData2.price;
    const timestamp = new Date(data.timestamp || defaultData2.timestamp);
    const options = {
        timeZone: 'Europe/Rome',
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    };
    const formatter = new Intl.DateTimeFormat('it-IT', options);
    const formattedTimestamp = formatter.format(timestamp);

    const currency = 'EUR';

    const html = `
    <div>
        <span class="flag ${currency}"></span>
        <span>${currency}</span>
        <span>${price.toFixed(2)}</span>
    </div>
    `;

    const errorMessage = data.message ? `Error: ${data.message}. Using default data.` : '';
    priceDiv.innerHTML = `
    <p>${errorMessage}</p>
    <p id="time">Last updated: ${formattedTimestamp}</p>
    ${html}
  `;
}

// Interrogazione dell'endpoint
fetch('/silver/ws_values/ada')
    .then(response => {
        if (!response.ok) {
            throw new Error(response.statusText);
        }
        return response.json();
    })
    .then(data => {
        displayData2(data);
    })
    .catch(error => {
        displayData2({ message: error.message });
    });
