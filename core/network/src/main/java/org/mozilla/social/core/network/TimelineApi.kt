package org.mozilla.social.core.network

import org.mozilla.social.core.network.model.NetworkStatus
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TimelineApi {
    @GET("/api/v1/timelines/home")
    suspend fun getHomeTimeline(
        // Return results older than ID.
        @Query("max_id") olderThanId: String? = null,
        // Return results newer than ID.
        @Query("since_id") newerThanId: String? = null,
        // Return results immediately newer than ID.
        @Query("min_id") immediatelyNewerThanId: String? = null,
        // Maximum number of results to return. Defaults to 20 statuses. Max 40 statuses.
        @Query("limit") limit: Int? = null,
    ): List<NetworkStatus>

    @GET("/api/v1/timelines/public")
    suspend fun getPublicTimeline(): List<NetworkStatus>

    @GET("/api/v1/timelines/tag/{hashtag}")
    suspend fun getHashTagTimeline(
        @Path("hashtag") hashTag: String,
        // Return results older than ID.
        @Query("max_id") olderThanId: String? = null,
        // Return results newer than ID.
        @Query("since_id") newerThanId: String? = null,
        // Return results immediately newer than ID.
        @Query("min_id") immediatelyNewerThanId: String? = null,
        // Maximum number of results to return. Defaults to 20 statuses. Max 40 statuses.
        @Query("limit") limit: Int? = null,
    ): List<NetworkStatus>
}