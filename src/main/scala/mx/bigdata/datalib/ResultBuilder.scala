package mx.bigdata.datalib;

import javax.servlet.http.HttpServletRequest

import org.springframework.jdbc.core.JdbcTemplate

trait ResultBuilder {

  def query(query: String, jdbcTemplate: JdbcTemplate)

  def build(request: HttpServletRequest): String

}
