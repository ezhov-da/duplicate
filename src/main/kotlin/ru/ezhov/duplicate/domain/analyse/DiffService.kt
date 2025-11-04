package ru.ezhov.duplicate.domain.analyse

import com.github.difflib.text.DiffRow
import com.github.difflib.text.DiffRowGenerator
import org.springframework.stereotype.Service

// TODO ezhov завершить
@Service
class DiffService {
    fun diff(rows: List<String>): List<String> {
        val generator: DiffRowGenerator = DiffRowGenerator.create()
            .showInlineDiffs(true)
            .mergeOriginalRevised(true)
            .inlineDiffByWord(true)
            .oldTag { f -> "~" } // Introduce markdown style for strikethrough
            .newTag { f -> "**" } // Introduce markdown style for bold
            .build()

        val rows: List<DiffRow> = generator.generateDiffRows(
            listOf(rows.first()),
            rows.subList(1, rows.size)
        )

        println("|original|new|")
        println("|--------|---|")
        for (row in rows) {
            println("|" + row.oldLine + "|" + row.newLine + "|")
        }

        return emptyList()
    }
}
