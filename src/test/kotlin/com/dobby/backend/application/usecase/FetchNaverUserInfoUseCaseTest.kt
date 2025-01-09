import com.dobby.backend.application.usecase.FetchNaverUserInfoUseCase
import com.dobby.backend.domain.gateway.MemberGateway
import com.dobby.backend.domain.gateway.feign.NaverAuthGateway
import com.dobby.backend.domain.gateway.TokenGateway
import com.dobby.backend.domain.model.member.Member
import com.dobby.backend.infrastructure.database.entity.enum.MemberStatus
import com.dobby.backend.infrastructure.database.entity.enum.ProviderType
import com.dobby.backend.infrastructure.database.entity.enum.RoleType
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class FetchNaverUserInfoUseCaseTest : BehaviorSpec({
    val naverAuthGateway = mockk<NaverAuthGateway>()
    val memberGateway = mockk<MemberGateway>()
    val tokenGateway = mockk<TokenGateway>()

    val fetchNaverUserInfoUseCase = FetchNaverUserInfoUseCase(
        naverAuthGateway,
        memberGateway,
        tokenGateway
    )

    given("Naver OAuth 요청이 들어왔을 때") {
        val input = FetchNaverUserInfoUseCase.Input(authorizationCode = "valid-auth-code", state = "valid-state")
        val mockMember = Member(
            id = 1L,
            oauthEmail = "test@example.com",
            name = "Test User",
            status = MemberStatus.ACTIVE,
            role = RoleType.PARTICIPANT,
            contactEmail = "contact@example.com",
            provider = ProviderType.NAVER
        )

        val mockEmptyMember = null
        every { naverAuthGateway.getAccessToken(any(), any()) } returns "mock-access-token"
        every { naverAuthGateway.getUserInfo("mock-access-token") } returns mockk {
            every { email } returns "test@example.com"
            every { name } returns "Test User"
        }

        every { tokenGateway.generateAccessToken(any()) } returns "mock-jwt-access-token"
        every { tokenGateway.generateRefreshToken(any()) } returns "mock-jwt-refresh-token"

        // 테스트 1: 등록된 멤버가 있을 경우
        every { memberGateway.findByOauthEmailAndStatus("test@example.com", MemberStatus.ACTIVE) } returns mockMember

        `when`("정상적으로 등록된 유저가 있는 경우") {
            val result: FetchNaverUserInfoUseCase.Output = fetchNaverUserInfoUseCase.execute(input)

            then("유저 정보를 포함한 OauthLoginResponse를 반환해야 한다") {
                result.isRegistered shouldBe true
                result.accessToken shouldBe "mock-jwt-access-token"
                result.refreshToken shouldBe "mock-jwt-refresh-token"
                result.memberId shouldBe 1L
                result.oauthEmail shouldBe "test@example.com"
            }
        }

        // 테스트 2: 등록된 멤버가 없는 경우
        every { memberGateway.findByOauthEmailAndStatus("test@example.com", MemberStatus.ACTIVE) } returns mockEmptyMember

        `when`("등록되지 않은 유저가 있는 경우") {
            val result: FetchNaverUserInfoUseCase.Output = fetchNaverUserInfoUseCase.execute(input)

            then("isRegistered는 false, memberId는 null이어야 한다") {
                result.isRegistered shouldBe false
                result.memberId shouldBe null
                result.oauthEmail shouldBe "test@example.com"
            }
        }
    }
})
