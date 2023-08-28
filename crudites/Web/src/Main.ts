import { myKey } from "./MyKey.js"
import { myClient } from "./MyClient.js"
import { myUtility } from "./MyUtility.js"

//

myKey.createApiKey()

const dApiKey = document.getElementById("dApiKey")
dApiKey.addEventListener("click", deleteApiKey)

function deleteApiKey() {
    myKey.deleteApiKey()
}

//

const c = document.getElementById("c")
const r = document.getElementById("r")
const u = document.getElementById("u")
const d = document.getElementById("d")
const l = document.getElementById("l")
const resultText = document.getElementById("resultText")
const id = document.getElementById("id") as HTMLInputElement
const data = document.getElementById("data") as HTMLInputElement

//

c.addEventListener("click", myCreate)
r.addEventListener("click", myRead)
u.addEventListener("click", myUpdate)
d.addEventListener("click", myDelete)
l.addEventListener("click", myList)

//

async function myCreate() {
    
    const idValue = Number(id.value)
    
    if(myUtility.isPositiveAlert(idValue)) {
        
        const result = await myClient.create(idValue, data.value)
        myUtility.newP(result, resultText)
    }
}

async function myRead() {
    
    const idValue = Number(id.value)

    if(myUtility.isPositiveAlert(idValue)) {
        
        const result = await myClient.read(idValue)
        myUtility.newP(result, resultText)
    }
}

async function myUpdate() {
    
    const idValue = Number(id.value)

    if(myUtility.isPositiveAlert(idValue)) {
        
        const result = await myClient.update(idValue, data.value)
        myUtility.newP(result, resultText)
    }
}

async function myDelete() {
    
    const idValue = Number(id.value)

    if(myUtility.isPositiveAlert(idValue)) {
        
        const result = await myClient.delete(idValue)
        myUtility.newP(result, resultText)
    }
}

async function myList() {
        
        const result = await myClient.list()
        myUtility.newP(result, resultText)
}
