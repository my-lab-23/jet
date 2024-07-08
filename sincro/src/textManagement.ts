import { dbx } from './dropboxConfig'
import { disableButton, enableButton } from './buttonManagement';
import { DropboxResponse, files } from 'dropbox';

//

export function loadText() {
    disableButton();
    const currentPage = getCurrentPage();
    const filePath = determineFilePath(currentPage);
    if (filePath) {
        downloadFile(filePath);
    }
}

function getCurrentPage() {
    // Ottieni l'URL della pagina corrente
    const currentPage = window.location.href;
    console.log(currentPage);
    return currentPage;
}

function determineFilePath(currentPage: string | string[]) {
    // Determina il percorso del file in base alla pagina corrente
    let filePath;
    if (currentPage.includes('other')) {
        filePath = '/other_upload.txt';
    } else {
        filePath = '/upload.txt';
    }
    return filePath;
}

function downloadFile(filePath: string) {

    const startDownloadTime = performance.now();

    if (dbx) {
        dbx.filesDownload({ path: filePath })
        .then(function (data) {
            processFile(data);
            const endDownloadTime = performance.now();
            calculateDownloadTime(startDownloadTime, endDownloadTime);
            setTimeout(enableButton, 1000);
        })
        .catch(function (error) {
            console.error(error.error || error);
            setTimeout(enableButton, 1000);
        });
    }
}

function processFile(data: DropboxResponse<files.FileMetadata>) {
    var reader = new FileReader();
    reader.onload = function () {
        var text = reader.result;
        if (typeof text === 'string') {
            (document.getElementById('textArea') as HTMLTextAreaElement).value = text;
        }
    };
    reader.readAsText((<any>data).result.fileBlob);
}

function calculateDownloadTime(startDownloadTime: number, endDownloadTime: number) {

    let downloadTime = endDownloadTime - startDownloadTime;
    downloadTime = Math.floor(downloadTime); // arrotonda per difetto

    // Ottieni i tempi di download più alti e più bassi dal localStorage
    var highestDownloadTime = Number(localStorage.getItem('highestDownloadTime')) || 0;
    var lowestDownloadTime = Number(localStorage.getItem('lowestDownloadTime')) || Infinity;

    // Aggiorna i tempi di download più alti e più bassi se necessario
    if (downloadTime > highestDownloadTime) {
        highestDownloadTime = downloadTime;
        localStorage.setItem('highestDownloadTime', String(highestDownloadTime));
    }
    if (downloadTime < lowestDownloadTime) {
        lowestDownloadTime = downloadTime;
        localStorage.setItem('lowestDownloadTime', String(lowestDownloadTime));
    }

    console.log(downloadTime);

    (document.getElementById('downloadTime') as HTMLParagraphElement).innerText = 'Tempo di download del file: ' + downloadTime + ' millisecondi' +
        '\nTempo di download più alto: ' + highestDownloadTime + ' millisecondi' +
        '\nTempo di download più basso: ' + lowestDownloadTime + ' millisecondi';
}

//

export function saveText(text: string) {
    disableButton();

    const fileBlob = new Blob([text], { type: 'text/plain' });

    // Ottieni l'URL della pagina corrente
    const currentPage = window.location.href;

    // Determina il nome del file in base alla pagina corrente
    let fileName;

    if (currentPage.includes('other')) {
        fileName = '/other_upload.txt';
    } else {
        fileName = '/upload.txt';
    }

    if (dbx && fileName) {
        dbx.filesUpload({ path: fileName, contents: fileBlob, mode: { '.tag': 'overwrite' } })
            .then(function (response) {
                console.log(response);
                setTimeout(enableButton, 2000);
            })
            .catch(function (error) {
                console.error(error);
                setTimeout(enableButton, 2000);
            });
    }
}
