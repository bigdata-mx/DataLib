package mx.bigdata.datalib;

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest

trait ResultBuilderServlet extends HttpServlet {
  
  def buildQuery(request: HttpServletRequest): String

  def newResultBuilder(request: HttpServletRequest): ResultBuilder

}
