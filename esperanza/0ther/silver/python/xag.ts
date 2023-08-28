import api from "npm:metalpriceapi"

api.setAPIKey('')
const res = await api.fetchLive('XAG', ['EUR', 'USD', 'CNY', 'RUB'])
const dataToSave = JSON.stringify(res.data, null, 2)
await Deno.writeTextFile('/home/silver/python/result.txt', dataToSave)
