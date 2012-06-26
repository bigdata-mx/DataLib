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

package mx.bigdata.datalib;

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.ServletConfig

abstract class BaseResultServlet extends ResultBuilderServlet {

  override def init(config: ServletConfig) = {
    super.init(config)
  }
    
  def newResultBuilder(request: HttpServletRequest): ResultBuilder

  override def doGet(request: HttpServletRequest,
    response: HttpServletResponse) = {
    var query = buildQuery(request)
    var mapper = doGetBuilder(query, request)
    response.setContentType("application/json")
    var out = response.getWriter()
    out.println(mapper.build(request))
  }

  def doGetBuilder(query: String, request: HttpServletRequest): ResultBuilder = {
    var mapper = newResultBuilder(request)
    val list = getParameters(request)
    mapper.query(query, list.toArray: _*)
    return mapper;
  }
}
