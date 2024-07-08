import { Dropbox } from 'dropbox';
import $ from 'jquery';

import { loadText } from './textManagement';

//

let refreshToken: string | null = null;
let clientId: string | null = null;
let clientSecret: string | null = null;
let accessToken: string | null = null;
let lastTokenTime: string | null = null;
export let dbx: Dropbox | null = null;

//

function getStoredValues() {
    refreshToken = localStorage.getItem('refreshToken') || prompt('Inserisci il tuo refreshToken');
    clientId = localStorage.getItem('clientId') || prompt('Inserisci il tuo clientId');
    clientSecret = localStorage.getItem('clientSecret') || prompt('Inserisci il tuo clientSecret');
}

function storeValues() {
    if (refreshToken) {
        localStorage.setItem('refreshToken', refreshToken);
    }
    if (clientId) {
        localStorage.setItem('clientId', clientId);
    }
    if (clientSecret) {
        localStorage.setItem('clientSecret', clientSecret);
    }
}

function checkAccessToken() {
    accessToken = localStorage.getItem('accessToken');
    lastTokenTime = localStorage.getItem('lastTokenTime');

    if (!accessToken || !lastTokenTime || Date.now() - parseInt(lastTokenTime) > 2 * 60 * 60 * 1000) {

        if (refreshToken && clientId && clientSecret) {
            getAccessToken(refreshToken, clientId, clientSecret)
                .then(token => {
                    accessToken = token;
                    console.log('Access Token (new): ' + accessToken);
                    dbx = new Dropbox({ accessToken: accessToken });
                    loadText();
                })
                .catch(error => {
                    console.log(error);
                });
        }

    } else {

        if (accessToken) {
            dbx = new Dropbox({ accessToken: accessToken });
            console.log('Access Token (old): ' + accessToken);
            loadText();
        }

    }
}

function getAccessToken(refreshToken: string, clientId: string, clientSecret: string): Promise<string> {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'https://api.dropboxapi.com/oauth2/token',
            type: 'post',
            data: {
                grant_type: 'refresh_token',
                refresh_token: refreshToken,
                client_id: clientId,
                client_secret: clientSecret
            },
            success: function (data) {
                localStorage.setItem('accessToken', data.access_token);
                localStorage.setItem('lastTokenTime', Date.now().toString());
                resolve(data.access_token);
            },
            error: function (error) {
                reject('Errore: ' + error);
            }
        });
    });
}

export function initialize() {
    getStoredValues();
    storeValues();
    checkAccessToken();
}
