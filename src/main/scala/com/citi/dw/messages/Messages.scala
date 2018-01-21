package com.citi.dw.messages

case class MapData(dataList: List[WordCount])

case class ReduceData(reduceDataList: Map[String, Int])

case class Result()

case class WordCount(key: String, count: Int)

