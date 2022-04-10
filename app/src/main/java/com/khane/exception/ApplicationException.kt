package com.khane.exception

class ApplicationException(val errorMessage: String) : Throwable() {
    var type: Type? = null

    /*
    override fun getMessage(): String {
        return message
    }
*/

    enum class Type {
        NO_INTERNET, NO_DATA, VALIDATION
    }
}
