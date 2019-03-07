
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
  <xsl:param name="promoted"/>
  <xsl:param name="promotedstr" select="string-join($promoted, ' ')" />
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
        <div style="display:block;" class="ml-5">
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
            <xsl:apply-templates>
                <xsl:sort select="orderelement" order="ascending" data-type="number" />
            </xsl:apply-templates>
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
      <xsl:sort select="orderelement" order="ascending" data-type="number" />
      <xsl:variable name="key" select="ID"/>
      <xsl:copy>
        <xsl:element name="orderelement">
            <xsl:attribute name="style">
                <xsl:value-of select="'display:none;'" />
            </xsl:attribute>
            <xsl:choose>
              <xsl:when test="contains(normalize-space($promotedstr), $key)">
                <xsl:value-of select="0"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="(1 + position())"/>
            </xsl:otherwise>
            </xsl:choose>
          </xsl:element>
        </xsl:copy>
      <xsl:choose>
        <xsl:when test = "$filterstars = 0 or ($mapData(number($key))('stars') = $filterstars)">
          <div class="">
            <xsl:attribute name="class">
                <xsl:value-of select="concat(' pt-4 pl-4 pr-4 mt-5 card col col-10 filter', $mapData(number($key))('stars'))"/>
                <xsl:choose>
                  <xsl:when test="contains(normalize-space($promotedstr), $key)">
                    <xsl:value-of select="' promo'"/>
                  </xsl:when>
                </xsl:choose>
            </xsl:attribute>
            <form method="post">
                <xsl:element name="input">
                    <xsl:attribute name="style">
                        <xsl:value-of select="'display:none;'" />
                    </xsl:attribute>
                    <xsl:attribute name="name">
                        <xsl:value-of select="'hotel'" />
                    </xsl:attribute>
                    <xsl:attribute name="value">
                        <xsl:value-of select="$key" />
                    </xsl:attribute>
                 </xsl:element>
                <div class="row">
                  <div class="col col-2">
                    <img src="$mapData(number($key))('photo')"/>
                  </div>
                  <div class="col col-7">
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
                    <div class="row small">
                        <xsl:value-of select="substring($mapData(number($key))('description'), 1, 70)" /> <a href="#" class="highlight">...more info</a>
                    </div>
                  </div>
                  <div class="col col-3 pl-5 clearfix" >
                      <div class="row nowrap highlight float-right">
                          EUR <span class="totalprice pl-2"></span>
                      </div>
                      <br/>
                      <div class="row float-right">
                          <input type="submit" value="BOOK" class="btn-highlight pl-3 pr-3"/>
                      </div>
                  </div>
                </div>
                <div class="row hotelrooms mt-1">
                <xsl:for-each select="RoomGroup">
                    <xsl:variable name="roomgroup" select="@index"/>
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
                                  EUR <span class="price"><xsl:value-of select="price" /></span>
                                </span>
                              </td>
                              <td style="width:10%">
                                <input type="radio">
                                  <xsl:attribute name="value">
                                    <xsl:value-of select="@id"/>
                                  </xsl:attribute>
                                  <xsl:attribute name="name">
                                    <xsl:value-of select="concat('room',$roomgroup)"/>
                                  </xsl:attribute>
                                  <xsl:attribute name="data-toggle">
                                    <xsl:value-of select="tooltip"/>
                                  </xsl:attribute>
                                  <xsl:attribute name="title">
                                    <xsl:value-of select="@id"/>
                                  </xsl:attribute>
                                  <xsl:attribute name="class">
                                    <xsl:value-of select="'room'"/>
                                  </xsl:attribute>
                                  <xsl:attribute name="hotelid">
                                    <xsl:value-of select="$key"/>
                                  </xsl:attribute>
                                  <xsl:attribute name="onchange">
                                    <xsl:value-of select="'checkout(this)'"/>
                                  </xsl:attribute>
                                  <xsl:choose>
                                    <xsl:when test="position() = 1">
                                      <xsl:attribute name="checked">
                                        <xsl:value-of select="'checked'"/>
                                      </xsl:attribute>
                                    </xsl:when>
                                  </xsl:choose>
                                </input>
                              </td>
                            </tr>
                          </xsl:for-each>
                        </tbody>
                    </table>
                </xsl:for-each>
                </div>
            </form>
          </div>
        </xsl:when>
      </xsl:choose >
    </xsl:for-each>
  </xsl:template>

</xsl:stylesheet>
