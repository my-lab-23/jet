interface MyUtility {

    isPositiveAlert: (id: number) => boolean
    newP: (s: string, e: Element) => void
}

function isPositiveNumber(value: number): boolean {
    return !isNaN(value) && value > 0
}

export const myUtility: MyUtility = {

    isPositiveAlert: function(id: number): boolean {

        const idValue = Number(id)

        if (!isPositiveNumber(idValue)) {
            alert("Il valore dell'ID deve essere un numero positivo.")
            return false
        }

        return true
    },

    newP: function (s: string, e: Element) {
        e.textContent = s
    }
}
