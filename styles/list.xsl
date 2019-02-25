<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:variable name="now" select="current-dateTime()"/>

  <xsl:param name="title">Available Hotels List</xsl:param>

  <xsl:template match="/">
    <html>
      <xsl:comment>Generated at <xsl:value-of select="$now"/></xsl:comment>
      <body leftmargin="100" >
        <xsl:apply-templates/>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="AvailRsp">
    <div>
      <xsl:for-each-group select="Hotel/RoomGroup" group-by="index">
        <h3>ID: <xsl:value-of select="@ID" /></h3>
        <hr/>
      </xsl:for-each-group>
    </div>
  </xsl:template>

</xsl:stylesheet>
