// Aggiunto gestore eventi per il menu contestuale
window.addEventListener('contextmenu', function (e) {
    e.preventDefault();
    if (e.target.classList.contains('message')) {
        document.getElementById('contextMenu').style.display = 'block';
        document.getElementById('contextMenu').style.left = e.pageX + 'px';
        document.getElementById('contextMenu').style.top = e.pageY + 'px';

        // Memorizza l'elemento cliccato
        window.clickedElement = e.target;
    }
});

document.getElementById('copy').addEventListener('click', function (e) {
    // Ottieni il testo dell'elemento cliccato
    var msg = window.clickedElement.textContent;

    // Crea un elemento di input temporaneo
    var tempInput = document.createElement('input');
    tempInput.value = msg;
    document.body.appendChild(tempInput);

    // Seleziona il testo nell'elemento di input
    tempInput.select();
    tempInput.setSelectionRange(0, 99999); /* For mobile devices */

    // Copia il testo nella clipboard
    document.execCommand('copy');

    // Rimuovi l'elemento di input temporaneo
    document.body.removeChild(tempInput);
});

document.getElementById('delete').addEventListener('click', function (e) {
    // Ottieni il testo dell'elemento cliccato
    var msg = window.clickedElement.textContent;

    // Invia una richiesta POST all'endpoint per eliminare il messaggio
    fetch('http://0.0.0.0:8081/my/post/elimina', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ message: msg }),
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            location.reload();
        })
        .catch((error) => {
            console.error('Error:', error);
            location.reload();
        });
});

window.addEventListener('click', function (e) {
    document.getElementById('contextMenu').style.display = 'none';
});
