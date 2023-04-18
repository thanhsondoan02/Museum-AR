package com.tiger.ar.museum.data.convert

interface IConverter<S, D> {
    fun convert(source: S): D
}
