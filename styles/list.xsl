
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css" href="styles/default.css"/>
        <link rel="stylesheet" type="text/css" href="styles/bootstrap/css/bootstrap.css"/>
      </head>
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
      <div>
        <h5>Hotel name <xsl:value-of select="ID" /></h5>
        <xsl:for-each select="RoomGroup">
            <table style="border:1px solid #ddd;width:100%">
                <tr>
                    <th scope="col" colspan="3" style="float:left;">Room <xsl:value-of select="@index" /></th>
                </tr>
                <xsl:for-each select="Room">
                    <tr>
                        <td style="width:70%">
                            <xsl:value-of select="name" />
                            <xsl:value-of select="board" />
                        </td>
                        <td style="width:20%" class="highlight">
                            <span class="nowrap">
                                EUR <xsl:value-of select="price" />
                            </span>
                        </td>
                        <td style="width:10%">
                            <input type="radio" name="pickroom">
                                <xsl:attribute name="value">
                                    value="<xsl:value-of select="@id"/>"
                                </xsl:attribute>
                            </input>
                        </td>
                    </tr>
                </xsl:for-each>
            </table>
        </xsl:for-each>
        <hr/>
      </div>
    </xsl:for-each>
  </xsl:template>

</xsl:stylesheet>
