package mx.bigdata.datalib.controller;

import javax.servlet.http.HttpServletRequest
import javax.servlet.ServletConfig
import mx.bigdata.datalib.BaseResultServlet
import java.net.URLDecoder

abstract class AbstractSearchController extends BaseResultServlet {

  def getParameters(request: HttpServletRequest): Array[String] = {
    var arr = request.getPathInfo().split("/")
    return arr.slice(1, arr.size).map(URLDecoder.decode(_, "UTF-8"));
  }

  def buildQuery(request: HttpServletRequest): String = {
    val name = request.getPathInfo().split("/")(1)
    return ""
  }
}
