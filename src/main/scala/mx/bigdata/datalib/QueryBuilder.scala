package mx.bigdata.datalib;

class QueryBuilder {
  
  val builder = new StringBuilder()
  
  def select(fields: List[String]) = {
    builder.append("SELECT ")      
    builder.append(fields.mkString(", "))
  }
  
  def from(from: String) = {
    builder.append(" FROM ")
    builder.append(from)
  }
  
  def where(clauses: String*) = {      
    var where = clauses.filter { d => d.length != 0 }.mkString(" AND");
    if (where.length != 0) {
      builder.append(" WHERE ")
      builder.append(where)
    }
  }
  
  def groupBy(fields: String*) {
    builder.append(" GROUP BY ")      
    builder.append(fields.mkString(", "))   
  }

  def orderBy(fields: String*) {
    builder.append(" ORDER BY ")      
    builder.append(fields.mkString(", "))   
  }

  def in(key: String, values: Array[String]): String = {
    return if (values != null) {
      var inner = new StringBuilder()
      inner.append(" ")
      inner.append(key)
      inner.append(" IN (")
      inner.append(values.map { "'" + _ + "'"}.mkString(", "))
      inner.append(")")
      inner.toString()
    } else {
      ""
    }
  }

  def between(key: String, from: String, to: String): String = {
    var inner = new StringBuilder()
    inner.append(" ")
    inner.append(key)
    inner.append(" BETWEEN ")
    inner.append(String.format("'%s'", from))
    inner.append(" AND ")
    inner.append(String.format("'%s'", to))
    inner.toString()
  }
  
  def limit(offset: Int, rows: Int) {
    builder.append(" LIMIT ")      
    builder.append(List(offset, rows).mkString(", "))   
  }

  def build = builder.toString()
}
