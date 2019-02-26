
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/">
    <html>
      <body leftmargin="100" style="overflow: auto;">
        <div style="display:block;">
          <div style="float: left;width:20%;"></div>
          <div style="float: right;width:80%;"><h3>Hotels found</h3></div>
        </div>
        <div style="display:block;">
          <div style="float: left;width:20%;">
            <strong>Filter hotels</strong>
            <div>
              <input type="checkbox" id="onestar" name="drone" value="1"/>
              <label for="onestar">1 star</label>
            </div>
            <div>
              <input type="checkbox" id="twostars" name="drone" value="2"/>
              <label for="twostars">2 stars</label>
            </div>
            <div>
              <input type="checkbox" id="threestars" name="drone" value="3"/>
              <label for="threestars">3 stars</label>
            </div>
            <div>
              <input type="checkbox" id="fourstars" name="drone" value="4"/>
              <label for="fourstars">4 stars</label>
            </div>
            <div>
              <input type="checkbox" id="fivestars" name="drone" value="5"/>
              <label for="fivestars">5 stars</label>
            </div>
          </div>
          <div style="float: right;width:80%;">
            <xsl:apply-templates/>
          </div>
        </div>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="AvailRsp">
    <xsl:for-each select="Hotel">
      <div style="background-color:#ccc">
        <h5>ID: <xsl:value-of select="ID" /></h5>
        <hr/>
      </div>
    </xsl:for-each>
  </xsl:template>

</xsl:stylesheet>
