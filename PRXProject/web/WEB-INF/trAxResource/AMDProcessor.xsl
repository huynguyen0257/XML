<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

    <xsl:variable name="ignore" select="'KBMBUptoGHz '"/>
    <xsl:variable name="uppercase" select="'SEXP'"/>
    <xsl:variable name="lowercase" select="'sexp'"/>
    <xsl:variable name="core" select="//div[contains(text(),'CPU Cores')]/parent::node()/div[2]/text()"/>
    <xsl:variable name="thread" select="//div[contains(text(),'Threads')]/parent::node()/div[2]/text()"/>
    <xsl:variable name="baseClock" select="//div[contains(text(),'Base Clock')]/parent::node()/div[2]/text()"/>
    <xsl:variable name="maxClock" select="//div[contains(text(),'Boost Clock')]/parent::node()/div[2]/text()"/>
    <xsl:param name="brand"/>
    <xsl:param name="name"/>
    <xsl:param name="model"/>
    <xsl:param name="cache"/>
<!--    <xsl:variable name="cacheL1KB" select="abs(translate($cacheL1,$ignore,'') , 1)"/>-->
<!--    <xsl:variable name="cacheL1Format" select="contains($cacheL1,'KB')"/>-->
<!--    <xsl:variable name="cache" select=""/>-->
    <!--    <xsl:variable name="model" select="substring-after($formattedName,'-')"/>-->

    <xsl:template match="/">
        <xsl:element name="processor" xmlns="http://huyng/schema/processor">
            <xsl:element name="brand">
                <xsl:value-of select="$brand"/>
            </xsl:element>
            <xsl:element name="name">
                <xsl:value-of select="$name"/>
            </xsl:element>
            <xsl:element name="model">
                <xsl:value-of select="$model"/>
            </xsl:element>
            <xsl:element name="core">
                <xsl:value-of select="$core"/>
            </xsl:element>
            <xsl:element name="thread">
                <xsl:value-of select="$thread"/>
            </xsl:element>
            <xsl:element name="baseClock">
                <xsl:if test="contains($baseClock,'MHz')">
                    <xsl:text>0.</xsl:text>
                </xsl:if>
                <xsl:value-of select="translate($baseClock,$ignore,'')"/>
            </xsl:element>
            <xsl:element name="boostClock">
                <xsl:if test="contains($maxClock,'MHz')">
                    <xsl:text>0.</xsl:text>
                </xsl:if>
                <xsl:value-of select="translate($maxClock,$ignore,'')"/>
                <xsl:text>0</xsl:text>
            </xsl:element>
            <xsl:element name="cache">
                <xsl:value-of select="$cache"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>