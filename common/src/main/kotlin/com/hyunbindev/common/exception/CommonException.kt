package com.hyunbindev.common.exception

abstract class CommonException<T> : RuntimeException(){
    val exceptionCode: T
}