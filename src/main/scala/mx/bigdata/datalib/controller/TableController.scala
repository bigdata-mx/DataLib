/*
 *  Copyright 2012 BigData Mx
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

package mx.bigdata.datalib.controller;

import org.springframework.jdbc.core.JdbcTemplate

import javax.servlet.http.HttpServletRequest
import javax.servlet.ServletConfig
import mx.bigdata.anyobject.impl.SnakeYAMLLoader
import mx.bigdata.anyobject.AnyObject
import mx.bigdata.datalib.sql.SqlResultServlet
import mx.bigdata.datalib.sql.TabularQueryResultBuilder

class TableController extends SqlResultServlet {

  var queries: AnyObject = null

  override def init(config: ServletConfig) = {
    super.init(config)
    val in = config.getServletContext().getResourceAsStream("/WEB-INF/queries/queries.yaml")
    queries = SnakeYAMLLoader.getInstance().load(in)
  }

  def newResultBuilder(request: HttpServletRequest, jdbcTemplate: JdbcTemplate) = new TabularQueryResultBuilder(jdbcTemplate)

  def getParameters(request: HttpServletRequest): Array[String] = {
    var arr = request.getPathInfo().split("/")
    val k: Array[String] = if (arr.size > 2) arr.slice(2, arr.size) else Array();
    return k
  }

  def buildQuery(request: HttpServletRequest): String = {
    val name = request.getPathInfo().split("/")(1)
    return queries.getString(name)
  }
}
