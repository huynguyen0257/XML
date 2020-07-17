<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

    <xsl:variable name="uppercase" select="'kgKG ,'"/>
    <xsl:variable name="lowercase" select="'     .'"/>
    <xsl:variable name="model" select="//th[contains(text(),'Model')]/following-sibling::td/text()"/>
    <xsl:variable name="cpu" select="//th[contains(text(),'CPU')]/following-sibling::td/text()"/>
    <xsl:variable name="ram" select="//th[contains(text(),'RAM')]/following-sibling::td/text()"/>
    <xsl:variable name="lcd" select="//th[contains(text(),'LCD')]/following-sibling::td/text()"/>
    <xsl:variable name="weight" select="//th[contains(text(),'Weight')]/following-sibling::td/text()"/>
    <xsl:param name="name"/>
    <xsl:param name="price"/>
    <xsl:param name="image"/>

    <xsl:template match="/table/tbody">
        <xsl:element name="laptop" xmlns="http://huyng/schema/laptop">
            <xsl:element name="name">
                <xsl:value-of select="$name"/>
            </xsl:element>
            <xsl:element name="price">
                <xsl:value-of select="$price"/>
            </xsl:element>
            <xsl:element name="model">
                <xsl:value-of select="$model"/>
            </xsl:element>
            <xsl:element name="cpu">
                <xsl:value-of select="$cpu"/>
            </xsl:element>
            <xsl:element name="ram">
                <xsl:value-of select="$ram"/>
            </xsl:element>
            <xsl:element name="lcd">
                <xsl:value-of select="$lcd"/>
            </xsl:element>
            <xsl:element name="weight">
                <xsl:value-of select="translate($weight,$uppercase,$lowercase)"/>
            </xsl:element>
            <xsl:element name="image">
                <xsl:value-of select="$image"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>