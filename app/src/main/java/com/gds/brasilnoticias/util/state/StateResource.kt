package com.gds.brasilnoticias.util.state

sealed class StateResource<out T>(
    val data :T? = null,
    val message : String? = null
) {
    class Sucesso<T>(data: T) : StateResource<T>(data)
    class  Error<T>(message: String,data: T? = null) : StateResource<T>(data, message)
    class Loanding<T>(data: T? = null): StateResource<T>(data)
}