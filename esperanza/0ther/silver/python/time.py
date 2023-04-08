import sys
import datetime
import pytz

# prendi l'input dal primo argomento sulla riga di comando
timestamp_input = sys.argv[1]

# converti il timestamp in formato ISO 8601 in un oggetto datetime
if 'Z' in timestamp_input:
    timestamp = datetime.datetime.fromisoformat(timestamp_input.replace('Z', '+00:00')).timestamp()
else:
    timestamp = int(timestamp_input)

# ottieni la data e l'ora UTC corrispondenti al timestamp
data_e_ora_utc = datetime.datetime.utcfromtimestamp(timestamp)

# ottieni il fuso orario di Roma
fuso_orario_roma = pytz.timezone('Europe/Rome')

# ottieni il fuso orario di sistema
fuso_orario_sistema = datetime.datetime.now(datetime.timezone.utc).astimezone().tzinfo

# convergi la data e l'ora UTC nella timezone del fuso orario di sistema
data_e_ora_sistema = pytz.utc.localize(data_e_ora_utc).astimezone(fuso_orario_sistema)

# convergi la data e l'ora UTC nella timezone del fuso orario di Roma
data_e_ora_roma = pytz.utc.localize(data_e_ora_utc).astimezone(fuso_orario_roma)

# formatta la data e l'ora in un formato leggibile
formato = '%d-%m-%Y %H:%M'
data_e_ora_utc_formattata = data_e_ora_utc.strftime(formato)
data_e_ora_sistema_formattata = data_e_ora_sistema.strftime(formato)
data_e_ora_roma_formattata = data_e_ora_roma.strftime(formato)

# stampa i risultati
print('Ora UTC:', data_e_ora_utc_formattata)
print('Ora di sistema:', data_e_ora_sistema_formattata)
print('Ora di Roma:', data_e_ora_roma_formattata)
