import urequests

#

def check_internet_connection(url):
    response = urequests.get(url)
    if response.status_code == 200:
        response.close()  # Chiudi esplicitamente la connessione
        return True
    else:
        response.close()  # Chiudi esplicitamente la connessione
        return False

#

def send_data(url, data, key):

    #print("Tento l'invio...")

    headers = {'X-API-Key': key}

    response = urequests.post(url, json=data, headers=headers)
    response.close()
