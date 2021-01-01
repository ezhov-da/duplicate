package ru.ezhov.duplicate.view.filter

class FilterParams private constructor(
        private val params: MutableMap<String, Any>
) {
    companion object {
        fun create() = FilterParams(mutableMapOf())
    }

    fun add(key: String?, value: Any?): FilterParams {
        key?.let {
            value?.let {
                params[key] = value
            }
        }
        return this
    }

    fun query() = params
            .takeIf { it.isNotEmpty() }
            ?.map { e ->
                "${e.key}=${e.value}"
            }
            ?.joinToString(separator = "&", prefix = "?")
            .orEmpty()
}