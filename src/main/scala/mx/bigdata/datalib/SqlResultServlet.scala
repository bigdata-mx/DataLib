package mx.bigdata.datalib;

import java.io.{IOException, PrintWriter}
import java.sql.{ResultSet}
import java.util.{Collection, Date, List => JList, Map => JMap}

import javax.annotation.Resource
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.ServletConfig
import javax.servlet.ServletException
import javax.sql.DataSource

import org.apache.commons.codec.digest.DigestUtils

import org.springframework.jdbc.core.JdbcTemplate

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import scala.collection.JavaConversions._

abstract class SqlResultServlet extends ResultBuilderServlet {
  
  @Resource(name="jdbc/data") 
  var datasource: DataSource = null

  var jdbcTemplate: JdbcTemplate = null

  override def init(config: ServletConfig) = {
    super.init(config)
    jdbcTemplate = new JdbcTemplate(datasource)
  }
  
  override def doGet(request: HttpServletRequest,
                     response: HttpServletResponse) = {
    var sql = buildQuery(request)
    var mapper = doGetBuilder(sql, request)
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", getServletContext()
                       .getInitParameter("Access-Control-Allow-Origin"));
    var out = response.getWriter()
    out.println(mapper.build(request))
  }
  
  def doGetBuilder(sql: String, request: HttpServletRequest): ResultBuilder = { 
    return newResultBuilder(request)
  }
}
