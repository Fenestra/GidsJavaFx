package com.westat

import scala.collection.mutable.ListBuffer


case class CellObject(col : Int, row : Int, value : String)

object CellObject {

  def fromString(value : String) : CellObject = {
    if (value.contains("{row:")) {
       var values = value.split(",")
       val row = values(0).substring(values(0).indexOf(":'")+2).dropRight(1)
       val col = values(1).substring(values(1).indexOf(":'")+2).dropRight(1)
       val cellValue = values(2).substring(values(2).indexOf(":'")+2).dropRight(2)
       val cell = CellObject(col.toInt, row.toInt, cellValue)
       cell
    }
     else
      null
  }

  def convertToClipboardFormat(value : String) : List[List[String]] = {
     val lines = value.lines
     var result = new ListBuffer[List[String]]
     lines.foreach(s => {
        result += s.split("\t").toList
     })
     result.toList
  }

  // extract value and put tabs between each
  private def makeTabSeparatedString(list : List[String]) : String = {
    list.map(s => s.drop(13)).mkString("\t")
  }

  def rawToCFString(value : String) : String = {
    var raw = value.lines.toList
    var result = new ListBuffer[String]

    // 1. make list of distinct rows
    var rows = raw.map(s => s.substring(0, 4)).toList.distinct
    var line = ""

    // 2. for each rownumber, call function to make rowstring from big list
    rows.foreach(row => {
      val sub = raw.filter(s => s.indexOf(row) == 0)
      line = makeTabSeparatedString(sub)

      // 3. add rowstring to resultlist
      result += line
    })

    // 4. return result as string with newline
    result.mkString("\n")
  }

}

