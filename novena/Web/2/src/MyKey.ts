interface MyKey {

    createApiKey: () => void
    readApiKey: () => string | null
    deleteApiKey: () => void
}

export const myKey: MyKey = {

    createApiKey: function() {

        const apiKey = myKey.readApiKey()

        if (apiKey === null) {

            const userApiKey = prompt('Inserisci la tua API Key:')

            if (userApiKey) {
                localStorage.setItem('apiKey', userApiKey)
            } else {
                alert('API Key non valida.')
            }
        }
    },

    readApiKey: function() {
        
        if (typeof Deno !== 'undefined') {
            // Deno environment
            const apiKey = Deno.env.get('CRUDITES_API_KEY')
            return apiKey
        } else {
            // Browser environment
            const storedApiKey = localStorage.getItem('apiKey')
            return storedApiKey
        }
    },

    deleteApiKey: function() {
        localStorage.removeItem('apiKey')
    }
}
