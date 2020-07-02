<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

    <xsl:variable name="uppercase" select="'àảẩậâấãăắáạệêềếồốôộổọớóợỔưứìĩíđABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>
    <xsl:variable name="lowercase" select="'aaaaaaaaaaaeeeeoooooooooouuiiidabcdefghijklmnopqrstuvwxyz'"/>

    <xsl:template match="/table/tbody">
        <xsl:element name="laptop" xmlns="http://huyng/schema/laptop">
            <xsl:for-each select="tr">
                <xsl:choose>
                    <xsl:when test="th[contains(translate(text(),$uppercase,$lowercase),'model')]">
                        <xsl:element name="model">
                            <xsl:value-of select="td/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="th[contains(translate(text(),$uppercase,$lowercase),'cpu')]">
                        <xsl:element name="cpu">
                            <xsl:value-of select="td/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="th[contains(translate(text(),$uppercase,$lowercase),'ram')]">
                        <xsl:element name="ram">
                            <xsl:value-of select="td/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="th[contains(translate(text(),$uppercase,$lowercase),'hdd')]">
                        <xsl:element name="hardDisk">
                            <xsl:value-of select="td/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="th[contains(translate(text(),$uppercase,$lowercase),'lcd')]">
                        <xsl:element name="lcd">
                            <xsl:value-of select="td/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="th[contains(translate(text(),$uppercase,$lowercase),'vga')]">
                        <xsl:element name="vga">
                            <xsl:value-of select="td/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="th[contains(translate(text(),$uppercase,$lowercase),'network')]">
                        <xsl:element name="options">
                            <xsl:value-of select="td/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="th[contains(translate(text(),$uppercase,$lowercase),'giao tiep')]">
                        <xsl:element name="port">
                            <xsl:value-of select="td/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="th[contains(translate(text(),$uppercase,$lowercase),'operating system')]">
                        <xsl:element name="os">
                            <xsl:value-of select="td/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="th[contains(translate(text(),$uppercase,$lowercase),'pin')]">
                        <xsl:element name="battery">
                            <xsl:value-of select="td/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="th[contains(translate(text(),$uppercase,$lowercase),'weight')]">
                        <xsl:element name="weight">
                            <xsl:value-of select="td/text()"/>
                        </xsl:element>
                    </xsl:when>
                    <xsl:when test="th[contains(translate(text(),$uppercase,$lowercase),'mau sac')]">
                        <xsl:element name="color">
                            <xsl:value-of select="td/text()"/>
                        </xsl:element>
                    </xsl:when>
                </xsl:choose>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>