package com.hyunbindev.common.image


/**
 * octet-stream 방식으로 raw data를 받아 처리하기 위한 메타데이터를 담는 data class 입니다.
 * 이는 client 와 back-end의 통신 규약을 설정하기 위해 작성되었습니다.
 * 캡슐화된 파일 정보를 custom http header에서 추출합니다.
 *
 * ---
 * ---
 *
 * Metadata for image uploads using raw binary streams (application/octet-stream).
 * This class serves as a contract between the client and the server,
 * encapsulating essential file information extracted from custom HTTP headers.
 *
 * @author hyunbindev
 * @since 2026-04-05
 *
 * @param contentType  The validated image media type
 * @param contentLength The exact size of the binary payload in bytes
 *
 * @see com.hyunbindev.common.image
 *
 *
 */
data class ImageUploadMetadata(
    val contentType: ImageExtension,
    val contentLength: Long,
){
    object Headers{
        const val CONTENT_TYPE:String = "Content-Type"
        const val CONTENT_LENGTH:String = "Content-Length"
    }
    fun getImageExtension():String{
        return this.contentType.mimeType
    }
}

enum class ImageExtension(val mimeType:String){
    JPEG("image/jpeg"),
    JPG("image/jpg"),
    PNG("image/png"),
    WEBP("image/webp");

    companion object {
        fun from(extension: String): ImageExtension{
            return ImageExtension.entries.find { it.mimeType.equals(extension, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown mimeType: $extension")
        }
    }
}