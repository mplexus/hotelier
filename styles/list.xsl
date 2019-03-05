
<xsl:stylesheet version="3.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:map="http://www.w3.org/2005/xpath-functions/map"
    xmlns:lxslt="http://xml.apache.org/xslt"
    exclude-result-prefixes="#all"
    expand-text="yes"
    >
  <xsl:param name="mapData" />
  <xsl:param name="filterstars" select="0" as="xs:integer"/>
  <xsl:template match="/">
    <html>
      <head>
        <link rel="stylesheet" type="text/css" href="styles/default.css"/>
        <link rel="stylesheet" type="text/css" href="styles/bootstrap/css/bootstrap.css"/>
      </head>
      <body leftmargin="100" style="overflow: auto;">
        <div style="display:block;">
          <div style="float: left;width:20%;"></div>
        </div>
        <div style="display:block;">
          <div style="float: left;width:20%;">
            <strong>Filter hotels</strong>
            <div>
              <input type="checkbox" id="onestar" name="drone" value="1" onclick="filter(this);"/>
              <label for="onestar">1 star</label>
            </div>
            <div>
              <input type="checkbox" id="twostars" name="drone" value="2" onclick="filter(this);"/>
              <label for="twostars">2 stars</label>
            </div>
            <div>
              <input type="checkbox" id="threestars" name="drone" value="3" onclick="filter(this);"/>
              <label for="threestars">3 stars</label>
            </div>
            <div>
              <input type="checkbox" id="fourstars" name="drone" value="4" onclick="filter(this);"/>
              <label for="fourstars">4 stars</label>
            </div>
            <div>
              <input type="checkbox" id="fivestars" name="drone" value="5" onclick="filter(this);"/>
              <label for="fivestars">5 stars</label>
            </div>
          </div>
          <div style="float: right;width:80%;" class="container">
            <xsl:apply-templates/>
          </div>
        </div>
        <script type="text/javascript" src="styles/js/jquery-3.3.1.min.js"></script>
        <script type="text/javascript" src="styles/js/filter.js"></script>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="AvailRsp">
    <h5>
        <span class="highlight visibleCount"></span>
        of
        <span class="highlight"><xsl:value-of select="count(Hotel)"/></span>
        Hotels found
    </h5>
    <xsl:for-each select="Hotel">
      <xsl:variable name="key" select="ID"/>
      <xsl:choose>
        <xsl:when test = "$filterstars = 0 or ($mapData(number($key))('stars') = $filterstars)">
          <div class="mt-5">
            <xsl:attribute name="class">
                <xsl:value-of select="concat('mt-5 filter', $mapData(number($key))('stars'))"/>
            </xsl:attribute>
            <div class="row mt-5">
              <div class="col col-2">
                <img src="$mapData(number($key))('photo')"/>
              </div>
              <div class="col col-6">
                <div class="row">
                    <span class="highlight">
                      <xsl:value-of select="$mapData(number($key))('name')" />
                      <span class="mx-2">
                        <xsl:for-each select="1 to $mapData(number($key))('stars')">
                            <img src="images/star.png"/>
                        </xsl:for-each>
                      </span>
                    </span>
                </div>
                <div class="row text-truncate small">
                    <xsl:value-of select="$mapData(number($key))('description')" /> <span class="highlight"><a href="#">...more info</a></span>
                </div>
              </div>
            </div>
            <div class="row col col-10">
            <xsl:for-each select="RoomGroup">
                <table class="table">
                    <thead class="thead-light">
                        <tr>
                            <th scope="col" colspan="3" >Room <xsl:value-of select="@index" /></th>
                        </tr>
                    </thead>
                    <tbody>
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
                    </tbody>
                </table>
            </xsl:for-each>
            </div>
          </div>
        </xsl:when>
      </xsl:choose >
    </xsl:for-each>
  </xsl:template>

</xsl:stylesheet>
