import $ from 'jquery';
import log from './logger';
import { Chart, LineController, LineElement, PointElement, CategoryScale, LinearScale, Title, Tooltip, Legend } from 'chart.js';
Chart.register(LineController, LineElement, PointElement, CategoryScale, LinearScale, Title, Tooltip, Legend);

//

import { fetchDataForGraph, fetchDataForLinks } from './client';

//

window.onload = () => {
    const messageElement = document.getElementById('message');

    if (messageElement) {
        messageElement.textContent = 'Caricamento in corso...';
    }

    let query = window.prompt("Inserisci il tuo parametro:") as string;
    log.debug(`Il parametro inserito è: ${query}`);

    fetchDataForGraph(query, (data) => {
        const dailyCounts = data.dailyCounts;

        const labels = Object.keys(dailyCounts).map((dateString) => {
            const date = new Date(dateString);
            date.setDate(date.getDate() + 0); // Nel caso fosse necessario un offset
            return date.toISOString().split('T')[0];
        });

        //

        // Dopo aver ottenuto le etichette (labels)
        createDropdownWithDates('selector', labels, onDayClick);

        //

        const counts = Object.values(dailyCounts);

        const canvas = document.getElementById('app') as HTMLCanvasElement;
        if (canvas) {
            const ctx = canvas.getContext('2d');
            if (ctx) {
                canvas.width = window.innerWidth * 0.8;
                canvas.height = window.innerHeight * 0.5;

                var chart = new Chart(ctx, {
                    type: 'line',
                    data: {
                        labels: labels,
                        datasets: [{
                            label: 'Notizie giornaliere su ' + query,
                            data: counts,
                            fill: false,
                            borderColor: 'rgb(75, 192, 192)',
                            tension: 0.1,
                            borderWidth: 2
                        }]
                    },
                    options: {
                        responsive: false,
                        onClick: function (event) {
                            var margin = 34; // Margine del grafico
                            var anticipation = 34; // Anticipazione del giorno successivo
                            var dayWidth = (chart.canvas.width - 2 * margin) / 7; // Larghezza di un giorno

                            log.debug(chart.canvas.width)
                            log.debug(event.x)

                            if (event.x) {
                                var adjustedX = event.x - margin; // Sottrai il margine
                                var dayIndex = Math.floor((adjustedX + anticipation) / dayWidth); // Calcola l'indice del giorno anticipando l'inizio del giorno successivo
                                var selectedDay = labels[dayIndex];

                                log.debug('Giorno:', selectedDay);

                                onDayClick(selectedDay);
                            }
                        }
                    }
                });

                if (messageElement) {
                    messageElement.textContent = '';
                }
            } else {
                log.error('Impossibile ottenere il contesto 2D del canvas.');
            }
        } else {
            log.error('Elemento con id "app" non trovato.');
        }
    }, (jqXHR) => {
        if (jqXHR.status === 429) {
            if (messageElement) {
                messageElement.textContent = 'Hai raggiunto il limite di richieste. Riprova più tardi.';
            }
        } else {
            if (messageElement) {
                messageElement.textContent = 'Si è verificato un errore durante il caricamento dei dati.';
            }
        }
    });
};

//

function onDayClick(day: string) {
    fetchDataForLinks(day, (response) => {
        $('#news').empty();

        let i = 0;

        $.each(response, function (_: number, item) {
            let title = item.title;
            let url = item.url;

            let dayDate = new Date(day);
            let publishedAtDate = new Date(item.publishedAt);

            if (dayDate.getUTCFullYear() === publishedAtDate.getUTCFullYear() &&
                dayDate.getUTCMonth() === publishedAtDate.getUTCMonth() &&
                dayDate.getUTCDate() === publishedAtDate.getUTCDate()) {

                let link = $('<a></a>').text(`${i + 1} - ${item.publishedAt} - ${title}`).attr('href', url);
                i++

                $('#news').append(link);
            }
        });
    }, (jqXHR) => {
        log.error('Errore durante l\'invio del giorno al server:', jqXHR);
        $('#news').empty();
        if (jqXHR.status === 429) {
            $('#news').append($('<p></p>').text('Hai superato il limite di richieste. Riprova più tardi.'));
        } else {
            $('#news').append($('<p></p>').text('Si è verificato un errore durante l\'elaborazione della tua richiesta'));
        }
    });
}

//

function createDropdownWithDates(selectorId: string, labels: string[], onDayClick: (value: string) => void): void {
    const selectorDiv = document.getElementById(selectorId);

    if (selectorDiv) {
        const dateDropdown = document.createElement('select');
        dateDropdown.className = 'my-custom-dropdown'; // Aggiungi la classe qui

        // Aggiungi un'opzione di segnaposto
        const placeholderOption = document.createElement('option');
        placeholderOption.textContent = 'Clicca sul grafico, oppure seleziona la data';
        placeholderOption.value = '';
        placeholderOption.selected = true;
        placeholderOption.disabled = true;
        dateDropdown.appendChild(placeholderOption);

        labels.forEach((label) => {
            const dateOption = document.createElement('option');
            dateOption.textContent = label;
            dateOption.value = label;
            dateDropdown.appendChild(dateOption);
        });

        dateDropdown.addEventListener('change', (event) => {
            const selectElement = event.target as HTMLSelectElement;
            onDayClick(selectElement.value);
        });

        selectorDiv.appendChild(dateDropdown);
    } else {
        log.error(`Elemento con id "${selectorId}" non trovato.`);
    }
}
