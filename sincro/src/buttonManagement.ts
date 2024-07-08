import { saveText } from './textManagement';

//

export function disableButton() {
    const button = document.getElementById('saveButton') as HTMLButtonElement;

    if (button) {
        button.disabled = true;
        button.style.backgroundColor = 'grey';
    }
}

export function enableButton() {
    const button = document.getElementById('saveButton') as HTMLButtonElement;

    if (button) {
        button.disabled = false;
        button.style.backgroundColor = '';
    }
}

export function addEventListenerToSaveButton() {
    const saveButton = document.getElementById('saveButton');

    if (saveButton) {
        saveButton.addEventListener('click', function () {

            const textArea = document.getElementById('textArea') as HTMLTextAreaElement;

            if (textArea) {
                const text = textArea.value;
                saveText(text);
            }
        });
    }
}
