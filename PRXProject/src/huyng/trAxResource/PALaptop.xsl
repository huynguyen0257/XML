<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

    <xsl:variable name="uppercase" select="'àảẩậâấãăắáạệêềếồốôộổọớóợỔưứìĩíđABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>
    <xsl:variable name="lowercase" select="'aaaaaaaaaaaeeeeoooooooooouuiiidabcdefghijklmnopqrstuvwxyz'"/>
    <xsl:variable name="model" select="//td[contains(translate(text(),$uppercase,$lowercase),'model')]/parent::node()/td[2]"/>
    <xsl:variable name="cpu" select="//td[contains(translate(text(),$uppercase,$lowercase),'vxl')]/parent::node()/td[2]"/>
    <xsl:variable name="ram" select="//td[contains(translate(text(),$uppercase,$lowercase),'bo nho')]/parent::node()/td[2]"/>
    <xsl:variable name="lcd" select="//td[contains(translate(text(),$uppercase,$lowercase),'man hinh')]/parent::node()/td[2]"/>
    <xsl:variable name="weight" select="//td[contains(translate(text(),$uppercase,$lowercase),'ng l')]/parent::node()/td[2]"/>
    <xsl:param name="price"/>
    <xsl:param name="name"/>
    <xsl:param name="image"/>

    <xsl:template match="/div/table/tbody">
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
                <xsl:value-of select="translate($weight,'kgKG ,','     .')"/>
            </xsl:element>
            <xsl:element name="image">
                <xsl:value-of select="$image"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>