document.addEventListener("DOMContentLoaded", function() {
  const form = document.querySelector("form");
  const updateButton = document.querySelector("#updateButton");

  form.addEventListener("submit", (event) => {
    event.preventDefault();
    updateButton.disabled = true; // Disabilita il pulsante durante l'operazione di rete
    updateButton.style.backgroundColor = "gray"; // Cambia il colore del pulsante

    const numValues = document.querySelector("#numValues").value;
    console.log("numValues:", numValues);
    fetch(`http://localhost:9090/other/reader/last/${numValues}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.text();
      })
      .then((text) => {
        // Convert the text into an array of JSON objects
        const data = text.trim().split("\n").map(JSON.parse);

        // Generate the content of the HTML table
        const tbody = document.querySelector("#last-data");
        tbody.innerHTML = ""; // Clear the table before regenerating it
        data.forEach((obj, index) => {
          const tr = document.createElement("tr");
          tr.innerHTML = `
            <td>${index}</td>
            <td>${obj.msg}</td>
            <td>${obj.timestamp}</td>
          `;
          tbody.appendChild(tr);
        });
      })
      .finally(() => {
        updateButton.disabled = false; // Riabilita il pulsante dopo l'operazione di rete
        updateButton.style.backgroundColor = ""; // Ripristina il colore del pulsante
      });
  });

  // Aggiungi un listener per cambiare il tema
  window.addEventListener("keydown", (event) => {
    if (event.key === "t") {
      const body = document.querySelector("body");
      body.dataset.theme = body.dataset.theme === "light" ? "dark" : "light";
    }
  });
});
