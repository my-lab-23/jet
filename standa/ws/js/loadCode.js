function loadCode(url, codeId) {
    fetch(url)
        .then(response => response.text())
        .then(data => {
            data = data.trim();
            document.getElementById(codeId).textContent = data;
            Prism.highlightAll();
        });
}
