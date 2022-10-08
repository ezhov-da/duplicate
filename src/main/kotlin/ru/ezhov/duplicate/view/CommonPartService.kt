package ru.ezhov.duplicate.view

class CommonPartService {
    fun commonPart(names: List<String>): String? =
        names
            .minOf { it.length }
            .let { minSize ->
                val lengthCommonPart = (0 until minSize)
                    .firstOrNull { c ->
                        val dif = names
                            .map { it.substring(c, c + 1) }
                            .distinct()
                        dif.size > 1
                    } ?: -1

                when (lengthCommonPart == -1) {
                    true -> null
                    false ->
                        names.first().substring(0, lengthCommonPart)
                }
            }
}
