from metalpriceapi.client import Client
import json

api_key = ''
client = Client(api_key)

res = client.fetchLive(base='XAG', currencies=['EUR', 'USD', 'CNY', 'RUB'])

json_data = json.dumps(res)

with open('/home/silver/python/result.txt', 'w') as f:
    f.write(str(json_data))

