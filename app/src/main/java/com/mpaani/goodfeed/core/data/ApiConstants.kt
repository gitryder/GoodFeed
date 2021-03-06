@file:JvmName("ApiConstants")

package com.mpaani.goodfeed.core.data

const val FEED_BASE_URL = "http://jsonplaceholder.typicode.com/"

const val POSTS_ENDPOINT = "posts"
const val USERS_ENDPOINT = "users"
const val COMMENTS_ENDPOINT = "comments"

/**
 * Return the URL for fetching an Avatar for given user's Email.
 */
fun getAvatarUrl(userEmail: String) = "https://api.adorable.io/avatars/$userEmail"
