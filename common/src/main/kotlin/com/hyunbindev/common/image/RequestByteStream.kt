package com.hyunbindev.common.image
/**
 * HTTP 요청 바디에서 원본 바이트 스트림(InputStream) 추출을 트리거하는 어노테이션입니다.
 * 이 어노테이션은 메모리 효율적인 대용량 파일 업로드를 위해 [InputStream] 타입의 파라미터에 적용되어야 합니다.
 * 연결된 Argument Resolver는 멀티파트 파싱을 거치지 않은 순수 요청 바디의 스트림을 제공합니다.
 *
 * 주의: 스트림은 한 번만 소비될 수 있으며, 사용 후 반드시 자원을 해제해야 합니다.
 *
 * ========================================================================
 *
 * Annotation used to trigger the extraction of the raw byte stream (InputStream) from the HTTP request body.
 * This annotation should be applied to a parameter of type [java.io.InputStream] for memory-efficient
 * large file uploads.
 * The associated argument resolver provides the raw request body stream without multipart parsing.
 *
 * Note: The stream can be consumed only once and must be closed after use.
 *
 * @see java.io.InputStream
 * @author hyunbindev
 * @since 2026-04-06
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequestByteStream
