import matplotlib.pyplot as plt
import numpy as np
import hashlib
import json

def draw_sine():
    x = np.linspace(-np.pi, np.pi, 100)
    y = np.sin(x)
    plt.plot(x, y)
    plt.savefig('sine.jpg')
    plt.show()
    return 'sine.jpg', 'Seno', 'Una rappresentazione grafica della funzione seno.'

def draw_cosine():
    x = np.linspace(-np.pi, np.pi, 100)
    y = np.cos(x)
    plt.plot(x, y)
    plt.savefig('cosine.jpg')
    plt.show()
    return 'cosine.jpg', 'Coseno', 'Una rappresentazione grafica della funzione coseno.'

def sha256_hash(filename):
    with open(filename,"rb") as f:
        bytes = f.read()
        hash = hashlib.sha256(bytes).hexdigest()
    return hash

def generate_json(filename, title, description):
    hash = sha256_hash(filename)
    data = {
        'sha256': hash,
        'titolo': title,
        'descrizione': description
    }
    with open(f'{filename}.json', 'w') as f:
        json.dump(data, f)

def main():
    choice = input("Inserisci 's' per il seno o 'c' per il coseno: ")
    if choice == 's':
        filename, title, description = draw_sine()
        generate_json(filename, title, description)
    elif choice == 'c':
        filename, title, description = draw_cosine()
        generate_json(filename, title, description)
    else:
        print("Scelta non valida. Inserisci 's' per disegnare la funzione seno o 'c' per disegnare la funzione coseno.")

if __name__ == "__main__":
    main()
