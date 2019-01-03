package com.example.administrator.loadmorerecyclerview.bean


data class Top250(var count: Int?, var start: Int?, var subjects: List<Subjects>?) {
    data class Subjects(var index: Int?, var title: String?, var year: String?, var rating: Rating?, var images: Images?, var genres: List<String>?) {
        data class Rating(var average: Double?)
        data class Images(var small: String?)
    }
}



