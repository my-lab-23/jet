class CustomURLComponent extends HTMLElement {

    private urlInput: HTMLInputElement
    private saveButton: HTMLButtonElement
    private linkSpace: HTMLDivElement

    constructor() {
        
        super()

        const shadow = this.attachShadow({ mode: 'open' })

        this.urlInput = document.createElement('input')
        this.urlInput.type = 'text'
        this.urlInput.placeholder = 'Inserisci l\'URL'

        this.saveButton = document.createElement('button')
        this.saveButton.textContent = 'Salva URL'
        this.saveButton.addEventListener('click', this.saveURL.bind(this))

        this.linkSpace = document.createElement('div')

        this.urlInput.style.marginBottom = '10px'
        this.saveButton.style.marginBottom = '10px'

        shadow.appendChild(this.urlInput)
        shadow.appendChild(this.saveButton)
        shadow.appendChild(this.linkSpace)

        const savedLink = localStorage.getItem('savedLink')
        if (savedLink) { this.displayLink(savedLink) }
    }

    saveURL() {

        const url = this.urlInput.value

        if (url.trim() !== '') {
            
            localStorage.setItem('savedLink', url)
            this.displayLink(url)
        }
    }

    displayLink(url: string) {

        const link = document.createElement('a')
        link.href = url
        link.textContent = url
        this.linkSpace.innerHTML = ''
        this.linkSpace.appendChild(link)
    }
}

customElements.define('my-cuc', CustomURLComponent)
