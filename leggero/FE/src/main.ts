import * as gui from './gui';
import * as net from './net';

if (
  gui.createTableButton &&
  gui.insertDataButton &&
  gui.fetchDataButton &&
  gui.dataDiv &&
  gui.tableContainer
) {

  gui.createTableButton.addEventListener('click', async () => {
    let confirmAction = confirm("Stai per rimuovere la tabella esistente. Vuoi continuare?");
    if (confirmAction && gui.dataDiv) {
      gui.removeExistingTable();
      gui.dataDiv.textContent = '';
      const data = await net.createTestTable();
      gui.dataDiv.textContent = data;
    } else {
      console.log("Azione annullata dall'utente");
    }
  });

  gui.insertDataButton.addEventListener('click', async () => {
    const text = prompt('Enter the text you want to insert');
    if (text && gui.dataDiv) {
      gui.removeExistingTable();
      gui.dataDiv.textContent = '';
      const data = await net.insertData(text);
      gui.dataDiv.textContent = data;
    }
  });

  gui.fetchDataButton.addEventListener('click', async () => {
    gui.removeExistingTable();
    if (gui.dataDiv && gui.tableContainer) {
      gui.dataDiv.textContent = '';
      const response = await fetch('/api/test');
      const data = await response.text();
      gui.dataDiv.textContent = data;
      const table = gui.createTable(data);
      if (table) {
        gui.tableContainer.appendChild(table);
      }
    }
  });
}
