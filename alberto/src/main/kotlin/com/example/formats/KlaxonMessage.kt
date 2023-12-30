package com.example.formats

import org.http4k.core.Body
import org.http4k.format.Klaxon.auto

data class KlaxonMessage(val sha256: String, val titolo: String, val descrizione: String)

val klaxonMessageLens = Body.auto<KlaxonMessage>().toLens()
