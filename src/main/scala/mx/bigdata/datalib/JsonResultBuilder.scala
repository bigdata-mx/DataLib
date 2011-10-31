package mx.bigdata.datalib;

import org.codehaus.jackson.map.ObjectMapper

trait JsonResultBuilder extends ResultBuilder {
  
  val mapper: ObjectMapper = new ObjectMapper()

}
