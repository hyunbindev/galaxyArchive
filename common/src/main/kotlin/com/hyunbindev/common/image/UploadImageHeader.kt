package com.hyunbindev.common.image



/**
 * HTTP 헤더에서 이미지 메타데이터 추출을 트리거하는 어노테이션입니다.
 * 이 어노테이션은 반드시 [ImageUploadMetadata] 타입의 파라미터에 적용되어야 합니다.
 * 연결된 Argument Resolver는 다음의 커스텀 헤더들을 참조합니다:
 * - [ImageUploadMetadata.Headers.ORIGINAL_NAME]
 * - [ImageUploadMetadata.Headers.CONTENT_TYPE]
 * - [ImageUploadMetadata.Headers.CONTENT_LENGTH]
 *
 ** ========================================================================
 *
 * Annotation used to trigger the extraction of image metadata from HTTP headers.
 * * This annotation must be applied to a parameter of type [ImageUploadMetadata].
 * The associated argument resolver will look for the following custom headers:
 * - [ImageUploadMetadata.Headers.ORIGINAL_NAME]
 * - [ImageUploadMetadata.Headers.CONTENT_TYPE]
 * - [ImageUploadMetadata.Headers.CONTENT_LENGTH]
 *
 * @see ImageUploadMetadata
 * @author hyunbindev
 * @since 2026-04-05
 *
 *
 *
 *
 */


@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class UploadImageHeader()
