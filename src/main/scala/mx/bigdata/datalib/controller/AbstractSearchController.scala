package mx.bigdata.datalib.controller;

import javax.servlet.http.HttpServletRequest
import javax.servlet.ServletConfig
import mx.bigdata.datalib.BaseResultServlet

abstract class AbstractSearchController extends BaseResultServlet {

  override def init(config: ServletConfig) = {
    super.init(config)
  }

  def getParameters(request: HttpServletRequest): Array[String] = {
    var arr = request.getPathInfo().split("/")
    return arr.slice(2, arr.size);
  }

  def buildQuery(request: HttpServletRequest): String = {
    val name = request.getPathInfo().split("/")(1)
    return ""
  }
}
