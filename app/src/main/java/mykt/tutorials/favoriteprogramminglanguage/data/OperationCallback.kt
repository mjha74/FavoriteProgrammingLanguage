package mykt.tutorials.favoriteprogramminglanguage.data

interface OperationCallback<T> {

    fun onSuccess(data: T?)
    fun onError(error: String?)
}