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

package mx.bigdata.datalib

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

import org.springframework.jdbc.core.PreparedStatementCreator 

class StreamingStatementCreator(val query: String) 
  extends PreparedStatementCreator {

  def createPreparedStatement(connection: Connection): PreparedStatement = {
    var statement = connection.prepareStatement(query, 
                                                ResultSet.TYPE_FORWARD_ONLY,
                                                ResultSet.CONCUR_READ_ONLY);
    statement.setFetchSize(Integer.MIN_VALUE);
    return statement;
  }
}