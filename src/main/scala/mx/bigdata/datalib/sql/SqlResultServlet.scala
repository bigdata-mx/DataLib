/*
 *  Copyright 2011 BigData Mx
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package mx.bigdata.datalib.sql;

import java.io.{ IOException, PrintWriter }
import java.sql.{ ResultSet }
import java.util.{ Collection, Date, List => JList, Map => JMap }
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
import mx.bigdata.datalib.ResultBuilder
import mx.bigdata.datalib.ResultBuilderServlet
import javax.naming.InitialContext
import javax.naming.Context

abstract class SqlResultServlet extends ResultBuilderServlet {

  var jdbcTemplate: JdbcTemplate = null
  
  def newResultBuilder(request: HttpServletRequest, jdbcTemplate: JdbcTemplate): ResultBuilder

  override def init(config: ServletConfig) = {
    super.init(config)
    val initCtx = new InitialContext();
    val envCtx = initCtx.lookup("java:comp/env").asInstanceOf[Context];
    val ds = envCtx.lookup("jdbc/cse").asInstanceOf[DataSource];
    jdbcTemplate = new JdbcTemplate(ds)
  }

  override def doGet(request: HttpServletRequest,
    response: HttpServletResponse) = {
    var sql = buildQuery(request)
    var mapper = doGetBuilder(sql, request)
    response.setContentType("application/json");
    var out = response.getWriter()
    out.println(mapper.build(request))
  }

  def doGetBuilder(sql: String, request: HttpServletRequest): ResultBuilder = {
    var mapper = newResultBuilder(request, jdbcTemplate)
    val list = getParameters(request)
    mapper.query(sql, list.toArray: _*)
    return mapper;
  }
}
