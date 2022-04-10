package com.khane.exception

/**
 * Exception throw by the application when a there is a server exception.
 */
class ServerException : RuntimeException {

    var code = 0
        internal set

    constructor() : super() {}

    constructor(message: String) : super(message) {}

    constructor(message: String, code: Int) : super(message) {
        this.code = code
    }

    constructor(message: String, cause: Throwable) : super(message, cause) {}

    constructor(cause: Throwable) : super(cause) {}
}
