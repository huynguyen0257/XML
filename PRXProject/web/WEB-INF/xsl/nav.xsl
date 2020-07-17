<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="html" encoding="UTF-8"/>
    <xsl:template match="/">
        <div class="navBar">
            <a href="index.jsp">
                <h2>HOME</h2>
            </a>
            <h2>
                <a style="color: inherit;">
                    <xsl:attribute name="href"><xsl:text>MainServlet?btAction=showAllLaptop</xsl:text></xsl:attribute>
                    Brand
                </a>
            </h2>
            <ul>
                <xsl:for-each select="brands/brand">

                    <li>
                        <xsl:attribute name="value">
                            <xsl:value-of select="id"/>
                        </xsl:attribute>
                        <a>
                            <xsl:attribute name="href"><xsl:text>MainServlet?btAction=showAllLaptop&amp;brandId=</xsl:text><xsl:value-of select="id"/></xsl:attribute>
                            <xsl:value-of select="name"/>
                        </a>
                    </li>
                </xsl:for-each>
            </ul>
        </div>
    </xsl:template>
</xsl:stylesheet>