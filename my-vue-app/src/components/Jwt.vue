<script setup lang="ts">
import { ref } from "vue";
import axios from "axios";

// Stato reattivo per gestire i dati
const username = ref("");
const password = ref("");
const token = ref("");
const helloMessage = ref("");

// Funzione per effettuare il login
const login = async () => {
  try {
    const response = await axios.post("http://192.168.168.93:8088/login", {
      username: username.value,
      password: password.value,
    });
    token.value = response.data.token;
    alert("Login effettuato con successo!");
  } catch (error) {
    console.error("Errore durante il login:", error);
    alert("Login fallito!");
  }
};

// Funzione per accedere all'endpoint protetto
const getHelloMessage = async () => {
  try {
    const response = await axios.get("http://192.168.168.93:8088/hello", {
      headers: {
        Authorization: `Bearer ${token.value}`,
      },
    });
    helloMessage.value = response.data;
  } catch (error) {
    console.error("Errore durante la richiesta protetta:", error);
    alert("Accesso negato!");
  }
};
</script>

<template>
  <div>
    <h1>Test API JWT</h1>

    <div>
      <label for="username">Username:</label>
      <input id="username" v-model="username" type="text" placeholder="Inserisci username" />
    </div>

    <div>
      <label for="password">Password:</label>
      <input id="password" v-model="password" type="password" placeholder="Inserisci password" />
    </div>

    <button @click="login">Login</button>

    <div v-if="token">
      <p>Token ricevuto:</p>
      <textarea readonly rows="4" cols="50">{{ token }}</textarea>
      <button @click="getHelloMessage">Accedi all'endpoint protetto</button>
    </div>

    <div v-if="helloMessage">
      <p>Risposta dall'endpoint protetto:</p>
      <pre>{{ helloMessage }}</pre>
    </div>
  </div>
</template>

<style scoped>
div {
  margin: 10px 0;
}

label {
  display: inline-block;
  width: 100px;
}

input {
  margin-bottom: 10px;
}

button {
  margin-top: 10px;
}

textarea {
  width: 100%;
  margin: 10px 0;
}
</style>
