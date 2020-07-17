<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

    <xsl:variable name="ignore" select="' '"/>
    <xsl:variable name="uppercase" select="'SEXP'"/>
    <xsl:variable name="lowercase" select="'sexp'"/>
    <xsl:variable name="core" select="//a[@data-wap_ref='CoreCount']/parent::node()/parent::node()/div[2]"/>
    <xsl:variable name="thread" select="//a[@data-wap_ref='ThreadCount']/parent::node()/parent::node()/div[2]"/>
    <xsl:variable name="baseClock" select="//a[@data-wap_ref='ClockSpeed']/parent::node()/parent::node()/div[2]"/>
    <xsl:variable name="maxClock" select="//a[@data-wap_ref='ClockSpeedMax']/parent::node()/parent::node()/div[2]"/>
    <xsl:variable name="cache" select="//a[@data-wap_ref='Cache']/parent::node()/parent::node()/div[2]"/>
    <xsl:param name="brand"/>
    <xsl:param name="name"/>
    <xsl:param name="lowerName" select="translate($name,$uppercase,$lowercase)"/>
    <xsl:variable name="formattedNormalName" select="substring-before($lowerName,'processor')"/>
    <xsl:variable name="formattedXlName" select="substring-before($lowerName,'x-')"/>
    <xsl:variable name="formattedExtremeName" select="substring-before($lowerName,'extreme')"/>
<!--    <xsl:variable name="model" select="substring-after($formattedName,'-')"/>-->

    <xsl:template match="/">
        <xsl:element name="processor" xmlns="http://huyng/schema/processor">
<!--        <xsl:element name="processor" xmlns="http://huyng/schema/laptop">-->
            <xsl:element name="brand">
                <xsl:value-of select="$brand"/>
            </xsl:element>
            <xsl:element name="name">
                <xsl:choose>
                    <xsl:when test="contains($lowerName,'x-series')">
                        <xsl:value-of select="$formattedXlName"/>
                    </xsl:when>
                    <xsl:when test="contains($lowerName,'extreme')">
                        <xsl:value-of select="$formattedExtremeName"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$formattedNormalName"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:element>
            <xsl:element name="model">
                <xsl:choose>
                    <xsl:when test="contains($lowerName,'x-series')">
                        <xsl:value-of select="substring-after($formattedXlName,'-')"/>
                        <xsl:value-of select="substring-after($formattedXlName,'+')"/>
                    </xsl:when>
                    <xsl:when test="contains($lowerName,'extreme')">
                        <xsl:value-of select="substring-after($formattedExtremeName,'-')"/>
                        <xsl:value-of select="substring-after($formattedExtremeName,'+')"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="substring-after($formattedNormalName,'-')"/>
                        <xsl:value-of select="substring-after($formattedNormalName,'+')"/>
                    </xsl:otherwise>
                </xsl:choose>
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
                <xsl:value-of select="substring-before($baseClock,$ignore)"/>
            </xsl:element>
            <xsl:element name="boostClock">
                <xsl:if test="contains($maxClock,'MHz')">
                    <xsl:text>0.</xsl:text>
                </xsl:if>
                <xsl:value-of select="substring-before($maxClock,$ignore)"/>
                <xsl:text>0</xsl:text>
            </xsl:element>
            <xsl:element name="cache">
                <xsl:value-of select="substring-before($cache,$ignore)"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>