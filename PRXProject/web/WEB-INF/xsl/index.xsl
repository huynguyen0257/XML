<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="html" encoding="UTF-8"/>
    <xsl:template match="/">
        <div class="items">
            <xsl:for-each select="laptops/laptop">
                <div class="item">
                    <img>
                        <xsl:attribute name="src">
                            <xsl:value-of select="image"/>
                        </xsl:attribute>
                    </img>
                    <div class="item-info">
                        <h3>
                            <xsl:value-of select="name"/>
                        </h3>
                        <h4>
                            <xsl:text>Price :</xsl:text><xsl:value-of select="price"/><xsl:text> &#x0110;</xsl:text>
                        </h4>
                        <p>
                            <xsl:text>Model :</xsl:text><xsl:value-of select="model"/>
                        </p>
                    </div>
                </div>
            </xsl:for-each>
        </div>
    </xsl:template>
</xsl:stylesheet>