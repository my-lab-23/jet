export const createTableButton = document.getElementById('createTableButton');
export const insertDataButton = document.getElementById('insertDataButton');
export const fetchDataButton = document.getElementById('fetchDataButton');
export const tableContainer = document.getElementById('tableContainer');
export const dataDiv = document.getElementById('data');

export function removeExistingTable() {
  const existingTable = document.querySelector('table');
  if (existingTable && tableContainer) {
    tableContainer.style.padding = '0'; // Rimuovi lo spazio interno
    tableContainer.style.margin = '0'; // Rimuovi lo spazio esterno
    tableContainer.removeChild(existingTable);
  }
}

export function createTable(data: string) {
  const table = document.createElement('table');
  const regex = /{"id":(\d+),"text":"([^"]*)"}/g;
  let match;
  let hasData = false;
  while ((match = regex.exec(data)) !== null) {
    hasData = true;
    const row = document.createElement('tr');
    const idCell = document.createElement('td');
    idCell.textContent = match[1];
    row.appendChild(idCell);
    const textCell = document.createElement('td');
    textCell.textContent = match[2];
    row.appendChild(textCell);
    table.appendChild(row);
  }
  return hasData ? table : null;
}
