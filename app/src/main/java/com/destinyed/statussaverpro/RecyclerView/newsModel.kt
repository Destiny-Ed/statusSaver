package com.destinyed.statussaverpro.RecyclerView

//data class newsModel(var title : String, var source : String, var description : String, var publishedDate : String,
//                var urlToImage : String, var url : String) {
//
//    constructor() : this("", "", "", "", "", "")
//}

data class newsModel (
        val status: String,
        val totalResults: Long,
        val articles: List<Article>
)

data class Article (
        val source: Source,
        val author: String? = null,
        val title: String,
        val description: String,
        val url: String,
        val urlToImage: String,
        val publishedAt: String,
        val content: String? = null
)

data class Source (
        val id: String? = null,
        val name: String
)