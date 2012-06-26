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
import java.sql.{ ResultSet, ResultSetMetaData, SQLException }
import java.util.{ Collection, Date, List => JList, Map => JMap }
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import javax.sql.DataSource
import org.springframework.jdbc.core.JdbcTemplate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.collection.JavaConversions._
import org.springframework.jdbc.core.PreparedStatementSetter
import java.sql.PreparedStatement
import mx.bigdata.datalib.JsonResultBuilder

class SingleQueryResultBuilder(jdbcTemplate: JdbcTemplate) extends JsonResultBuilder {

  val logger: Logger = LoggerFactory.getLogger(getClass)

  var data: JMap[String, Object] = null

  def query(query: String, list: String*) = {
    Console.println(list)
    data =
      if (list.size == 0)
        jdbcTemplate.queryForMap(query)
      else
        jdbcTemplate.queryForMap(query, list.toArray: _*)
  }

  def build(request: HttpServletRequest) = mapper.writeValueAsString(data)

}
