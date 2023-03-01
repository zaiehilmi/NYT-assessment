package com.auzaie.jetnotes.model

import android.util.Log
import com.auzaie.jetnotes.model.Statez.Companion.mostViewedResult
import com.auzaie.jetnotes.model.Statez.Companion.searchResult
import com.auzaie.jetnotes.model.dto.NewsArticle
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class NewsApiClient {
    companion object {
        private const val BASE_URL = "https://api.nytimes.com/svc/"
        private const val API_KEY = "7RVO6GWxoJuWtjMJnztrtyx3OK1l13Y1"
        private val client = OkHttpClient()
        var query: String? = null

        fun searchArticles(query: String) {
            var result: List<NewsArticle>? = null

            val url = "$BASE_URL/search/v2/articlesearch.json".toHttpUrlOrNull()
                ?.newBuilder()
                ?.addQueryParameter("api-key", API_KEY)
                ?.addQueryParameter("q", query)
                ?.build().toString()

            val request = Request.Builder()
                .url(url)
                .get()
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("ada error")
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData: String? = response.body?.string()

                    if (response.isSuccessful) {
                        result = responseData?.let { parseSearchArticles(it) }
                        Log.d("SIZE", result?.size.toString())

                        searchResult = result
                    }
                }
            })
        }

        fun mostViewedArticle() {
            val url = "$BASE_URL/mostpopular/v2/viewed/1.json".toHttpUrlOrNull()
                ?.newBuilder()
                ?.addQueryParameter("api-key", API_KEY)
                ?.build().toString()

            val request = Request.Builder()
                .url(url)
                .get()
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("MOST VIEWED", "Ada error ni")
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData: String? = response.body?.use { it.string() }

                    if (response.isSuccessful) {
                        mostViewedResult = responseData?.let { parseMostViewedArticles(it) }
                    }
                }
            })

        }

        private fun parseSearchArticles(responseData: String): List<NewsArticle>? {
            val jsonObject = JSONObject(responseData)
            val response = jsonObject.getJSONObject("response")
            val docs = response.getJSONArray("docs")
            val articles = mutableListOf<NewsArticle>()

            for (i in 0 until docs.length()) {
                val doc = docs.getJSONObject(i)
                val headline = doc.getJSONObject("headline").getString("main")
                val snippet = doc.getString("snippet")
                val article = NewsArticle(headline, snippet = snippet)
                articles.add(article)
            }

            return articles
        }

        private fun parseMostViewedArticles(responseData: String): List<NewsArticle>? {
            val jsonObject = JSONObject(responseData)
            val results = jsonObject.getJSONArray("results")
            val articles = mutableListOf<NewsArticle>()

            for (i in 0 until results.length()) {
                val result = results.getJSONObject(i)
                val title = result.getString("title")
                val abstract = result.getString("abstract")
                val article = NewsArticle(title, abstract = abstract)
                articles.add(article)
            }

            return articles
        }
    }
}
