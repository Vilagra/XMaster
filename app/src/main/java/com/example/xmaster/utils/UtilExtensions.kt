package com.example.xmaster.utils

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.location.Location
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import retrofit2.Response
import okhttp3.Call
import okhttp3.Callback
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

const val SUCCESS_CODE = 200
const val ERROR_CODE_400 = 400
const val ERROR_CODE_401 = 401
const val ERROR_CODE_404 = 404
const val ERROR_CODE_500 = 500
private const val NAME_PATTERN = "^[A-Za-zА-ЯҐЄІЇа-яґєії`'’-]+"

fun <VM : ViewModel> Fragment.obtainViewModel(factory: ViewModelProvider.Factory?, cls: Class<VM>): VM {
    return ViewModelProviders.of(this, factory).get(cls)
}

fun <A, B, Result> LiveData<A>.combineIfBothSet(
    other: LiveData<B>,
    combiner: (A, B) -> Result
): LiveData<Result> {
    val result = MediatorLiveData<Result>()
    result.addSource(this) { a ->
        val b = other.value
        if (b != null) {
            result.postValue(combiner(a, b))
        }
    }
    result.addSource(other) { b ->
        val a = this@combineIfBothSet.value
        if (a != null) {
            result.postValue(combiner(a, b))
        }
    }
    return result
}


fun <A, B, Result> LiveData<A>.combineIfAtLeastOneSet(
    other: LiveData<B>,
    combiner: (A?, B?) -> Result
): LiveData<Result> {
    val result = MediatorLiveData<Result>()
    result.addSource(this) { a ->
        val b = other.value
        result.postValue(combiner(a, b))
    }
    result.addSource(other) { b ->
        val a = this@combineIfAtLeastOneSet.value
        result.postValue(combiner(a, b))
    }
    return result
}

fun <A, B, C, Result> LiveData<A>.combine(
    other1: LiveData<B>,
    other2: LiveData<C>,
    combiner: (A, B, C) -> Result
): LiveData<Result> {
    val result = MediatorLiveData<Result>()
    result.addSource(this) { a ->
        val b = other1.value
        val c = other2.value
        if (b != null && c != null) {
            result.postValue(combiner(a, b, c))
        }
    }
    result.addSource(other1) { b ->
        val a = this@combine.value
        val c = other2.value
        if (a != null && c != null) {
            result.postValue(combiner(a, b, c))
        }
    }
    result.addSource(other2) { c ->
        val a = this@combine.value
        val b = other1.value
        if (a != null && b != null) {
            result.postValue(combiner(a, b, c))
        }
    }
    return result
}

fun <X, Y> LiveData<X>.map(body: (X) -> Y): LiveData<Y> {
    return Transformations.map(this, body)
}

fun <X, Y> LiveData<X>.switchMap(body: (X) -> LiveData<Y>): LiveData<Y> {
    return Transformations.switchMap(this, body)
}

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(this, provider).get(VM::class.java)

inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(this, provider).get(VM::class.java)

inline fun <reified VM : ViewModel> Fragment.activityViewModelProvider(
    provider: ViewModelProvider.Factory
) =
    ViewModelProviders.of(requireActivity(), provider).get(VM::class.java)


fun EditText.requestFocusShowKeyboard() = run {
    if (!hasFocus()) {
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun EditText.hideKeyboard() = run {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(getWindowToken(), 0)
}

fun EditText.requestFocusIfDoesnt() = run {
    if (!hasFocus()) {
        requestFocus()
    }
}

suspend fun Call.suspendEnqueue(): okhttp3.Response {
    return suspendCoroutine {
        enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                it.resumeWithException(e)
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                it.resume(response)
            }
        })
    }
}


fun <T> MutableLiveData<T>.postValueIfNew(newValue: T) {
    if (this.value != newValue) postValue(newValue)
}

fun Throwable.generalOr(value: GeneralError) = if (this is GeneralError) this else value

suspend fun <T> executeWithTryCatchHandle(error: GeneralError = Unexpected(), block: suspend () -> Response<T>): Response<T> {
    return try {
        block()
    } catch (t: Throwable) {
        t.printStackTrace()
        throw t.generalOr(error)
    }
}

fun Context?.isMIUI(): Boolean {
    return this?.run {
        val installedPackages = packageManager.getInstalledPackages(PackageManager.MATCH_SYSTEM_ONLY)
        installedPackages.forEach {
            if (it.packageName.startsWith("com.miui.")) {
                true
            }
        }
        false
    } ?: false
}

inline fun String?.isDigitOrNull() = this?.matches(".*\\d+.*".toRegex()) ?: true

inline fun <T, S> MediatorLiveData<T>.addSourceWithRemoving(source: LiveData<S>, noinline onChanged: (S) -> Unit) {
    removeSource(source)
    addSource(source, onChanged)
}

