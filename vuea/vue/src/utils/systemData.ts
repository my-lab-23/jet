import { getRequest, postRequestWithAuth } from '@/utils/aux';

export interface SystemData {
    os: string;
    network: string;
}

export const fetchSystemData = async (url: string): Promise<SystemData> => {
    const data = await getRequest(url);

    if (isSystemData(data)) {
        return data;
    } else {
        throw new Error('Invalid data format');
    }
};

export const fetchSystemDataWithAuth = async (
    url: string,
    androidKey: string
): Promise<SystemData> => {
    const data = await postRequestWithAuth(url, androidKey);

    if (isSystemData(data)) {
        return data;
    } else {
        throw new Error('Invalid data format');
    }
};

// Funzione di controllo del tipo
const isSystemData = (data: unknown): data is SystemData => {
    return (
        typeof data === 'object' &&
        data !== null &&
        'os' in data &&
        'network' in data
    );
};
