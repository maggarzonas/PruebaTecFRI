package quicktype

data class CamposFoto (
        val id: String,
        val createdAt: String,
        val updatedAt: String,
        val promotedAt: Any? = null,
        val width: Long,
        val height: Long,
        val color: String,
        val blurHash: String,
        val description: String,
        val alt_description: String,
        val urls: Urls,
        val links: CamposFotoLinks,
        val categories: List<Any?>,
        val likes: Long,
        val likedByUser: Boolean,
        val currentUserCollections: List<Any?>,
        val sponsorship: Sponsorship,
        val user: User
)

data class CamposFotoLinks (
        val self: String,
        val html: String,
        val download: String,
        val downloadLocation: String
)

data class Sponsorship (
        val impressionUrls: List<String>,
        val tagline: String,
        val taglineURL: Any? = null,
        val sponsor: User
)

data class User (
        val id: String,
        val updatedAt: String,
        val username: String,
        val name: String,
        val firstName: String,
        val lastName: Any? = null,
        val twitter_username: String? = "",
        val portfolio_url: String? = "",
        val bio: String? = "",
        val location: Any? = null,
        val links: UserLinks,
        val profile_image: ProfileImage,
        val instagram_username: String? = "",
        val total_collections: Long,
        val total_likes: Long,
        val total_photos: Long,
        val acceptedTos: Boolean,
        val forHire: Boolean
)

data class UserLinks (
        val self: String,
        val html: String,
        val photos: String,
        val likes: String,
        val portfolio: String,
        val following: String,
        val followers: String
)

data class ProfileImage (
        val small: String,
        val medium: String,
        val large: String
)

data class Urls (
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
)
