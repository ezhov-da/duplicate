package ru.ezhov.duplicate.view

class CommonPartService {
    fun commonPart(names: List<String>): String? {
        val minSize = names.minOfOrNull { it.length }

        return minSize?.let {
            val lengthCommonPart = (0 until minSize)
                    .firstOrNull { c ->
                        val dif = names
                                .map { it.substring(c, c + 1) }
                                .distinct()
                        dif.size > 1
                    } ?: -1

            if (lengthCommonPart == -1) {
                null
            } else {
                names.first().substring(0, lengthCommonPart)
            }
        }
    }
}