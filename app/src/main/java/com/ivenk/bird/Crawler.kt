package com.ivenk.bird

interface Crawler {

    fun gatherData(champion: String): List<Triple<String, String, String>>
}