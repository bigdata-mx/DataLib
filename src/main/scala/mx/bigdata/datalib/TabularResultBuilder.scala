package mx.bigdata.datalib;

import java.io.{IOException, PrintWriter}
import java.sql.{ResultSet, ResultSetMetaData, SQLException}
import java.util.{Collection, Date, List => JList, Map => JMap}

import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import javax.sql.DataSource

import org.springframework.jdbc.core.JdbcTemplate

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import scala.collection.JavaConversions._

class TabularResultBuilder extends JsonResultBuilder {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  var data: JList[JMap[String,Object]] = null
  
  def query(query: String, jdbcTemplate: JdbcTemplate) = {
    data = jdbcTemplate.queryForList(query)
  }

  def build(request: HttpServletRequest) = mapper.writeValueAsString(data)
  
}
