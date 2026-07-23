package com.hyunbindev.common.auth


@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class LoginUserId(val required:Boolean=true)
