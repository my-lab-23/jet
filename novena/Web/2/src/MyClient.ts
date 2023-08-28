import { myKey } from "./MyKey.js"

interface MyClient { 
    read: () => Promise<string>
    write: (s: string) => Promise<string> 
}

export const myClient: MyClient = {
    
    read: async function() {

        const apiKey = myKey.readApiKey()
        const url = 'https://2desperados.it/novena/read'

        try {
            
            const response = await fetch(url, {
              
                headers: { 'X-api-key': apiKey }
            })
            
            return await response.text()
        
        } catch (error) { console.error(error) }
    },

    write: async function(s: string): Promise<string> {
        
        const apiKey = myKey.readApiKey()
        const url = 'https://2desperados.it/novena/write/1'
    
        try {
            const response = await fetch(url, {
                method: 'POST',
                body: s,
                headers: {
                    'Content-Type': 'text/plain',
                    'X-Api-Key': apiKey
                }
            })
    
            const responseBody = await response.text();
            
            return responseBody;
        
        } catch (error) { console.error(error) }
    }    
}
