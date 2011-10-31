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

import net.sf.ehcache.Cache
import net.sf.ehcache.CacheManager
import net.sf.ehcache.Element

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import scala.collection.JavaConversions._

abstract class CachedSqlResultServlet extends SqlResultServlet {

  var manager: CacheManager = null

  
  override def init(config: ServletConfig) = {
    super.init(config)
    manager = new CacheManager()
  }
  
  override def doGetBuilder(sql: String, 
                            request: HttpServletRequest): ResultBuilder = { 
    return getCachedBuilder(sql, request)
  }

  def getCachedBuilder(sql: String, 
                       request: HttpServletRequest): ResultBuilder = {
    var cache = manager.getCache("summary")
    var key = DigestUtils.md5Hex(sql)
    var element = cache.get(key)
    if (element != null) {
        return element.getObjectValue().asInstanceOf[JsonResultBuilder]
    }
    var mapper = newResultBuilder(request)
    mapper.query(sql, jdbcTemplate)
    cache.put(new Element(key, mapper));
    return mapper;
  }

}
