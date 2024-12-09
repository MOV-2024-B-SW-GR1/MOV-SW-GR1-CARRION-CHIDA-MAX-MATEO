package data.storage

import domain.model.Facultad
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class JsonStorage(private val filePath: String) {
    private val json = Json { prettyPrint = true }

    fun loadFacultades(): List<Facultad> {
        val file = File(filePath)
        if (!file.exists()) {
            return emptyList()
        }
        val content = file.readText()
        if (content.isBlank()) return emptyList()

        return json.decodeFromString(content)
    }

    fun saveFacultades(facultades: List<Facultad>) {
        val file = File(filePath)
        file.writeText(json.encodeToString(facultades))
    }
}
