interface MyClient { fetch: () => Promise<string> }

export const myClient: MyClient = {
    
    fetch: async function() {

        const url = 'https://2desperados.it/esp32/searchLastNCoords/1'

        try {
            
            const response = await fetch(url, {
              
                headers: { 'X-api-key': '' }
            })
            
            return await response.text()
        
        } catch (error) { console.error(error) }
    }
}
