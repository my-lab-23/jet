import wikipediaapi

def converti_mese_in_numero(mese):
    mese = mese.lower()  # trasforma il mese in minuscolo per facilitare la comparazione
    mesi = {
        "gennaio": 1,
        "febbraio": 2,
        "marzo": 3,
        "aprile": 4,
        "maggio": 5,
        "giugno": 6,
        "luglio": 7,
        "agosto": 8,
        "settembre": 9,
        "ottobre": 10,
        "novembre": 11,
        "dicembre": 12
    }
    if mese in mesi:
        return mesi[mese]
    else:
        return None

def getSign(day, month, year):
    # Controlla che i parametri siano validi
    month = converti_mese_in_numero(month)
    day = int(day)
    year = int(year)
    if not (1 <= month <= 12) or not (1 <= day <= 31):
        return None
    
    # Se day = 29 e month = 2
    if(month == 2 and day == 29):
        return "Pesci"
    
    # Definisce le date di inizio e fine di ogni segno zodiacale
    sign_names = ["Capricorno", "Acquario", "Pesci", "Ariete", "Toro", "Gemelli",
                  "Cancro", "Leone", "Vergine", "Bilancia", "Scorpione", "Sagittario", 
                  "Capricorno"]
    signs = [(1, 19), (20, 50), (51, 80), (81, 111), (112, 142), (143, 173), (174, 204), 
             (205, 235), (236, 266), (267, 296), (297, 326), (327, 356), (357, 365)]
    
    # Calcola il giorno dell'anno
    #is_leap_year = (year % 4 == 0) and (year % 100 != 0 or year % 400 == 0)
    days_in_month = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    day_of_year = sum(days_in_month[:month-1]) + day
    
    #print(day_of_year)

    # Determina il segno zodiacale corrispondente
    for i, sign in enumerate(signs):
        if sign[0] <= day_of_year <= sign[1]:
            return sign_names[i]
    
    # Se non viene trovato alcun segno zodiacale, ritorna None
    return None

def estrai_data_di_nascita(nome, cognome):
    wiki = wikipediaapi.Wikipedia("it")
    nome_completo = f"{nome} {cognome}"
    pagina = wiki.page(nome_completo)

    if not pagina.exists():
        return f"Impossibile trovare la pagina di {nome_completo}"

    testo = pagina.summary

    import re
    match = re.search(r"\b(\d{1,2})[ .](\w+)[ .](\d{4})", testo)

    sign = getSign(match.group(1), match.group(2), match.group(3))

    if match:
        return f"{nome_completo} è nato/a il {match.group(1)} {match.group(2)} {match.group(3)} sotto il segno: {sign}"
    else:
        return f"Impossibile trovare la data di nascita di {nome_completo}"

# Leggi nome e cognome dagli argomenti della riga di comando
import sys
nome = sys.argv[1]
cognome = sys.argv[2]
result = estrai_data_di_nascita(nome, cognome)
print(result)
# Scrivi result su un file di testo
with open("/home/ema/result.txt", "w") as f:
    f.write(result)
