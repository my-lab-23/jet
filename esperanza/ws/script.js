// Seleziona la textarea e il form
const messageTextarea = document.querySelector('#message');
const messageForm = document.querySelector('#message-form');

// Funzione per inviare il messaggio
const sendMessage = (message) => {
    
    msg = message
    timestamp = timeStamp()
  
    fetch('http://0.0.0.0:8086/examples/msg', {
    
    method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({msg, timestamp})
    })
    
    .then(response => {
        console.log('Messaggio inviato con successo');
        messageForm.reset();
    })
    
    .catch(error => console.error('Errore durante l\'invio del messaggio:', error));
}

// Sottomissione del form
messageForm.addEventListener('submit', (event) => {
    
    event.preventDefault();
    const message = document.querySelector('#message').value;
    sendMessage(message);
});

// Salva il valore della textarea nel localStorage
messageTextarea.addEventListener('input', () => {
  
  if (localStorage.getItem('consentGiven') === 'true') {
    
    localStorage.setItem('message', messageTextarea.value);
  }
});

// Rimuove il valore salvato dal localStorage quando l'utente invia il messaggio
messageForm.addEventListener('submit', () => {
  
  localStorage.removeItem('message');
});

function acceptLocalStorage() {
  
  localStorage.setItem('consentGiven', 'true');
  document.querySelector('.cookie-banner').style.display = 'none';
}

// Recupera il valore del messaggio dal localStorage quando la pagina viene caricata
window.addEventListener('load', () => {
  
  const savedMessage = localStorage.getItem('message');
  
  if (savedMessage) {
    messageTextarea.value = savedMessage;
  }
  
  if (localStorage.getItem('consentGiven')==='true') {
  
    console.log(localStorage.getItem('consentGiven'))
    document.querySelector('.cookie-banner').style.display = 'none';
  }
});

function timeStamp() {

  const timeStamp = new Date().getTime();
  return timeStamp;
}
