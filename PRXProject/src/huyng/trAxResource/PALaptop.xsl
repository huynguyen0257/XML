<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

    <xsl:variable name="uppercase" select="'àảẩậâấãăắáạệêềếồốôộổọớóợỔưứìĩíđABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>
    <xsl:variable name="lowercase" select="'aaaaaaaaaaaeeeeoooooooooouuiiidabcdefghijklmnopqrstuvwxyz'"/>
    <xsl:param name="price"/>
    <xsl:param name="name"/>

    <xsl:template match="/div/table/tbody">
        <xsl:element name="laptop" xmlns="http://huyng/schema/laptop">
            <xsl:for-each select="tr">
                <!--<editor-fold desc="Real td">-->
                <xsl:choose>
                    <xsl:when test="td[1][contains(translate(text(),$uppercase,$lowercase),'model')]">
                        <xsl:element name="model">
                            <xsl:value-of select="td[2]/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="td[1][contains(translate(text(),$uppercase,$lowercase),'vxl')]">
                        <xsl:element name="cpu">
                            <xsl:value-of select="td[2]/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="td[1][contains(translate(text(),$uppercase,$lowercase),'cac d')]">
                        <xsl:element name="vga">
                            <xsl:value-of select="td[2]/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="td[1][contains(translate(text(),$uppercase,$lowercase),'bo nho')]">
                        <xsl:element name="ram">
                            <xsl:value-of select="td[2]/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="td[1][contains(translate(text(),$uppercase,$lowercase),'o cung')]">
                        <xsl:element name="hardDisk">
                            <xsl:value-of select="td[2]/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="td[1][contains(translate(text(),$uppercase,$lowercase),'man hinh')]">
                        <xsl:element name="lcd">
                            <xsl:value-of select="td[2]/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="td[1][contains(translate(text(),$uppercase,$lowercase),' noi')]">
                        <xsl:element name="options">
                            <xsl:value-of select="td[2]/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="td[1][contains(translate(text(),$uppercase,$lowercase),' giao ')]">
                        <xsl:element name="port">
                            <xsl:value-of select="td[2]/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="td[1][contains(translate(text(),$uppercase,$lowercase),'he dieu hanh')]">
                        <xsl:element name="os">
                            <xsl:value-of select="td[2]/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="td[1][contains(translate(text(),$uppercase,$lowercase),'pin')]">
                        <xsl:element name="battery">
                            <xsl:value-of select="td[2]/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="td[1][contains(translate(text(),$uppercase,$lowercase),'ng l')]">
                        <xsl:element name="weight">
                            <xsl:value-of select="td[2]/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="td[1][contains(translate(text(),$uppercase,$lowercase),'/ ch')]">
                        <xsl:element name="color">
                            <xsl:value-of select="td[2]/text()"/>
                        </xsl:element>
                    </xsl:when>
                </xsl:choose>
            </xsl:for-each>
            <xsl:element name="name">
                <xsl:value-of select="$name"/>
            </xsl:element>
            <xsl:element name="price">
                <xsl:value-of select="$price"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>