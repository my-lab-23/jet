import { myKey } from "./MyKey.js"

interface MyClient { 
    
    create: (id: number, data: string) => Promise<string>
    read: (id: number) => Promise<string>
    update: (id: number, data: string) => Promise<string>
    delete: (id: number) => Promise<string>
    list: () => Promise<string>
}

async function makeRequest(method: string, url: string, data?: any): Promise<string> {
    
    const apiKey = myKey.readApiKey()
    const headers: Record<string, string> = { 'X-api-key': apiKey }

    if (method === 'POST' || method === 'PUT') {
        headers['Content-Type'] = 'application/json'
    }

    try {
        const response = await fetch(url, {
            method,
            headers,
            body: data ? JSON.stringify(data) : undefined
        })

        const text = await response.text()
        const status = response.status
        const result = `${text} : ${status}`

        return result

    } catch (error) { console.error(error) }
}

export const myClient: MyClient = {
    
    create: async function(id: number, data: string) {
        const url = 'https://2desperados.it/crudites/data'
        return makeRequest('POST', url, { id, data })
    },
    
    read: async function(id: number) {
        const url = `https://2desperados.it/crudites/data/${id}`
        return makeRequest('GET', url)
    },
    
    update: async function(id: number, data: string) {
        const url = `https://2desperados.it/crudites/data/${id}`
        return makeRequest('PUT', url, { id, data })
    },
    
    delete: async function(id: number) {
        const url = `https://2desperados.it/crudites/data/${id}`
        return makeRequest('DELETE', url)
    },

    list: async function() {
        const url = `https://2desperados.it/crudites/data/list`
        return makeRequest('GET', url)
    }
}