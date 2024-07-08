export async function createTestTable() {
    const response = await fetch('/api/createTestTable');
    const data = await response.text();
    return data;
}

export async function insertData(text: string) {
    const response = await fetch('/api/test', {
        method: 'POST',
        headers: {
            'Content-Type': 'text/plain'
        },
        body: text
    });
    const data = await response.text();
    return data;
}

export async function fetchData() {
    const response = await fetch('/api/test');
    const data = await response.text();
    return data;
}
