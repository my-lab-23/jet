const timestamp_input = Deno.args[0]

if (timestamp_input) {
  
  let timestamp: number

  if (timestamp_input.includes('Z')) {
    timestamp = new Date(timestamp_input).getTime() / 1000
  } else {
    timestamp = parseInt(timestamp_input)
  }

  const utcDate = new Date(timestamp * 1000)
  const localTimezoneOffset = -utcDate.getTimezoneOffset()
  const systemDate = new Date(utcDate.getTime() + localTimezoneOffset * 60000)
  const romeDate = new Date(utcDate.getTime() + 120 * 60000)

  const formatDate = (date) => {
    const year = date.getUTCFullYear()
    const month = String(date.getUTCMonth() + 1).padStart(2, '0')
    const day = String(date.getUTCDate()).padStart(2, '0')
    const hours = String(date.getUTCHours()).padStart(2, '0')
    const minutes = String(date.getUTCMinutes()).padStart(2, '0')
    return `${day}-${month}-${year} ${hours}:${minutes}`
  }

  console.log(`Ora UTC: ${formatDate(utcDate)}`)
  console.log(`Ora di sistema: ${formatDate(systemDate)}`)
  console.log(`Ora di Roma: ${formatDate(romeDate)}`)

} else { console.log('Specificare un timestamp come argomento.') }
