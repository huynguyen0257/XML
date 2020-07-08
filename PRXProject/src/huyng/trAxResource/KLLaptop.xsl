<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

    <xsl:variable name="uppercase" select="'àảẩậâấãăắáạệêềếồốôộổọớóợỔưứìĩíđABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>
    <xsl:variable name="lowercase" select="'aaaaaaaaaaaeeeeoooooooooouuiiidabcdefghijklmnopqrstuvwxyz'"/>
    <xsl:variable name="model" select="//th[contains(text(),'Model')]/following-sibling::td/text()"/>
    <xsl:variable name="cpu" select="//th[contains(text(),'CPU')]/following-sibling::td/text()"/>
    <xsl:variable name="ram" select="//th[contains(text(),'RAM')]/following-sibling::td/text()"/>
    <xsl:variable name="hardDisk" select="//th[contains(text(),'HDD')]/following-sibling::td/text()"/>
    <xsl:variable name="lcd" select="//th[contains(text(),'LCD')]/following-sibling::td/text()"/>
    <xsl:variable name="vga" select="//th[contains(text(),'VGA')]/following-sibling::td/text()"/>
    <xsl:variable name="options" select="//th[contains(text(),'Network')]/following-sibling::td/text()"/>
    <xsl:variable name="port" select="//th[contains(text(),'Giao T')]/following-sibling::td/text()"/>
    <xsl:variable name="os" select="//th[contains(text(),'Operating System')]/following-sibling::td/text()"/>
    <xsl:variable name="battery" select="//th[contains(text(),'Pin')]/following-sibling::td/text()"/>
    <xsl:variable name="weight" select="//th[contains(text(),'Weight')]/following-sibling::td/text()"/>
    <xsl:variable name="color" select="//th[contains(translate(text(),$uppercase,$lowercase),'mau sac')]/following-sibling::td/text()"/>
    <xsl:param name="name"/>
    <xsl:param name="price"/>

    <xsl:template match="/table/tbody">
        <xsl:element name="laptop" xmlns="http://huyng/schema/laptop">
            <xsl:element name="model">
                <xsl:value-of select="$model"/>
            </xsl:element>
            <xsl:element name="cpu">
                <xsl:value-of select="$cpu"/>
            </xsl:element>
            <xsl:element name="vga">
                <xsl:value-of select="$vga"/>
            </xsl:element>
            <xsl:element name="ram">
                <xsl:value-of select="$ram"/>
            </xsl:element>
            <xsl:element name="hardDisk">
                <xsl:value-of select="$hardDisk"/>
            </xsl:element>
            <xsl:element name="lcd">
                <xsl:value-of select="$lcd"/>
            </xsl:element>
            <xsl:element name="options">
                <xsl:value-of select="$options"/>
            </xsl:element>
            <xsl:element name="port">
                <xsl:value-of select="$port"/>
            </xsl:element>
            <xsl:element name="os">
                <xsl:value-of select="$os"/>
            </xsl:element>
            <xsl:element name="battery">
                <xsl:value-of select="$battery"/>
            </xsl:element>
            <xsl:element name="weight">
                <xsl:value-of select="$weight"/>
            </xsl:element>
            <xsl:element name="color">
                <xsl:value-of select="$color"/>
            </xsl:element>
            <xsl:element name="name">
                <xsl:value-of select="$name"/>
            </xsl:element>
            <xsl:element name="price">
                <xsl:value-of select="$price"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>