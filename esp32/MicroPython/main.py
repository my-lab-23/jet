from my_wifi import connect, get_wifi_signal_strength
from my_net import check_internet_connection, send_data
from project0 import project0

import ujson
import utime
import ntptime

#

pwd = ''
key = ''

#

esp32_ext = get_wifi_signal_strength('ESP32_EXT', pwd)
time_stamp_esp32_ext = int(utime.time() * 1000)
print('ESP32_EXT: ' + str(esp32_ext))

esp32 = get_wifi_signal_strength('ESP32', pwd)
time_stamp_esp32 = int(utime.time() * 1000)
print('ESP32: ' + str(esp32))

#

if (esp32 >= esp32_ext):
    print('Mi connetto ad ESP32.')
    connect('ESP32', pwd)
else:
    print('Mi connetto ad ESP32_EXT.')
    connect('ESP32_EXT', pwd)

#

url = 'https://2desperados.it'

if check_internet_connection(url):
    print('Connessione Internet attiva.')
else:
    print('Connessione Internet non disponibile.')

#

url = 'https://2desperados.it/esp32/signalstrength'

chiave_ssid = 'ssid'
chiave_signal_strength = 'signalStrength'
chiave_time_stamp = 'timeStamp'

#

ssid = 'esp32_ext'

json_data = {chiave_ssid: ssid,
             chiave_signal_strength: esp32_ext,
             chiave_time_stamp : time_stamp_esp32_ext}

json_string = ujson.dumps(json_data)
send_data(url, json_string, key)

#

ssid = 'esp32'

json_data = {chiave_ssid: ssid,
             chiave_signal_strength: esp32,
             chiave_time_stamp : time_stamp_esp32}

json_string = ujson.dumps(json_data)
send_data(url, json_string, key)

#

ntptime.settime()

while True:
    project0(key)
    utime.sleep(2)
