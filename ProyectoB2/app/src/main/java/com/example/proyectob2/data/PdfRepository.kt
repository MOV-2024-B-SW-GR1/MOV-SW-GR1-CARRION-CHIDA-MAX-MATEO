package com.example.proyectob2.util

import android.content.Context
import com.lowagie.text.pdf.PdfReader
import com.lowagie.text.pdf.parser.PdfTextExtractor
import com.example.proyectob2.R

fun loadPdfContext(context: Context): String {
    return try {
        // Abre el PDF desde res/raw
        context.resources.openRawResource(R.raw.context).use { inputStream ->
            val reader = PdfReader(inputStream)
            val extractor = PdfTextExtractor(reader)
            val sb = StringBuilder()
            // Recorre todas las páginas y extrae el texto
            for (page in 1..reader.numberOfPages) {
                sb.append(extractor.getTextFromPage(page))
                sb.append("\n")  // Salto de línea entre páginas
            }
            reader.close()
            sb.toString()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}
