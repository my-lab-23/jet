from my_net import send_data

import urandom
import ujson
import utime

def project0(key):

    url = 'https://2desperados.it/esp32/coordinates'

    x = urandom.randint(-100, 100)
    y = urandom.randint(-100, 100)
    time_stamp = int(utime.time() * 1000)
    #print(time_stamp)

    chiave_x = 'x'
    chiave_y = 'y'
    chiave_time_stamp = 'timeStamp'

    json_data = {chiave_x: x,
                 chiave_y: y,
                 chiave_time_stamp: time_stamp}

    json_string = ujson.dumps(json_data)

    send_data(url, json_string, key)
