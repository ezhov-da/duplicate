package ru.ezhov.duplicate.domain.analyse

class FingerprintParserException : RuntimeException {
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(message: String?) : super(message)

}