import axios from 'axios';

//

export const getRequest = async (url: string): Promise<unknown> => {
    const response = await axios.get(url);
    return response.data;
};

//

export const postRequestWithAuth = async (
    url: string,
    androidKey: string
): Promise<unknown> => {

    //

    const response = await axios.post(url,
        new URLSearchParams({
            androidKey: androidKey.trim()
        }).toString(),
        { headers: { "Content-Type": "application/x-www-form-urlencoded" } });

    //

    return response.data;
};

// Funzione per recuperare la chiave di autenticazione dal localStorage

export const loadAndroidKey = (): string | null => {
    return localStorage.getItem('androidKey');
};
