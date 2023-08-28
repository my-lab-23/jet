import network
import utime

#

def _wait_for_connection(wlan):
    while not wlan.isconnected():
        utime.sleep(0.1)

#

def connect(ssid, pwd):
    print('Connecting to WiFi...', end='')
    wlan = network.WLAN(network.STA_IF)
    wlan.active(True)
    wlan.connect(ssid, pwd)
    _wait_for_connection(wlan)
    print(' connected!')
    print(wlan.ifconfig())

#

def get_wifi_signal_strength(ssid, pwd):
    wlan = network.WLAN(network.STA_IF)
    wlan.active(True)
    wlan.connect(ssid, pwd)
    _wait_for_connection(wlan)
    rssi = wlan.status('rssi')
    signal_strength = int((rssi + 100) * (100 / 70))
    wlan.disconnect()
    return signal_strength
