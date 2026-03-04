package com.hyunbindev.auth.oauth2.model

import com.hyunbindev.common.constant.oauth2.OAuth2Provider
import com.hyunbindev.common.constant.oauth2.OAuth2Provider.*
import com.hyunbindev.user.data.UserInfoDto
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.UUID

/**
 * OAuth2 인증을 통해 로드된 사용자 정보를 담는 [OAuth2User] 및 [OAuth2UserPrincipal]의 구현체.
 * * Spring Security의 인증 주체(Principal)로 사용되며,
 * 각 소셜 제공자(Github, Google 등)로부터 받은 원본 속성 데이터를
 * 우리 서비스의 공통 규격으로 추상화하여 제공합니다.
 *
 * @property providerId 소셜 제공자에서 부여한 고유 식별자 (예: Github의 id, Google의 sub)
 * @property nickname 사용자 별명
 * @property profileImageUrl 사용자 프로필 이미지 URL (null 가능)
 * @property email 사용자 이메일 (null 가능)
 * @property provider 소셜 로그인 제공자 구분 ([OAuth2Provider])
 * @property attributes 소셜 제공자로부터 받은 날것의 전체 속성 맵
 * @property authorities 사용자에게 부여된 권한 목록
 */
class OAuth2UserPrincipalImpl(
    override var userId: UUID? = null,
    override val providerId: String,
    override val nickname: String,
    override val profileImageUrl: String?,
    override val email: String?,
    override val provider: OAuth2Provider,
    private val attributes: Map<String, Any>,
    private val authorities: Collection<GrantedAuthority> = emptyList()
) : OAuth2User, OAuth2UserPrincipal {

    /**
     * @return 소셜 서비스에서 제공하는 모든 속성 정보
     */
    override fun getAttributes(): Map<String, Any> = attributes

    /**
     * @return 현재 사용자에게 부여된 Spring Security 권한 목록
     */
    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    /**
     * Spring Security의 Principal 식별자로 [providerId]를 반환합니다.
     * * @return 중복되지 않는 소셜 고유 ID
     */
    override fun getName(): String = userId.toString()


    override fun toUserDto(): UserInfoDto{
        return UserInfoDto(
            oAuth2Provider=this.provider,
            nickName=this.nickname,
            providerId = this.providerId,
            email = this.email,
            profileImageUrl = this.profileImageUrl,
        )
    }

    companion object {
        /**
         * 소셜 제공자의 응답 데이터를 바탕으로 [OAuth2UserPrincipalImpl] 객체를 생성합니다.
         * * @param registrationId 클라이언트 등록 ID (예: "github", "google")
         * @param oAuth2User [DefaultOAuth2UserService]를 통해 로드된 기본 사용자 객체
         * @return 우리 서비스 규격에 맞춰 파싱된 인증 객체
         * @throws IllegalArgumentException 지원하지 않는 [registrationId]일 경우 발생
         */
        fun of(registrationId: String, oAuth2User: OAuth2User): OAuth2UserPrincipalImpl {
            val provider = OAuth2Provider.from(registrationId)
            val attr = oAuth2User.attributes

            return when (provider) {
                GITHUB -> OAuth2UserPrincipalImpl(
                    providerId = attr["id"].toString(),
                    nickname = attr["login"] as String,
                    profileImageUrl = attr["avatar_url"] as? String,
                    email = attr["email"] as? String,
                    provider = provider,
                    attributes = attr,
                    authorities = oAuth2User.authorities
                )

                GOOGLE -> OAuth2UserPrincipalImpl(
                    providerId = attr["sub"].toString(),
                    nickname = attr["name"] as String,
                    profileImageUrl = attr["picture"] as? String,
                    email = attr["email"] as? String,
                    provider = provider,
                    attributes = attr,
                    authorities = oAuth2User.authorities
                )
            }
        }
    }
}