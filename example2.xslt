<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
<xsl:param name="bar"/>
<xsl:template match="/">
<xsl:variable name="allauthors">
    <authors foo="{$bar}">
        <xsl:for-each select="/books/book">
				<xsl:sort select="@author"/>
            <author id="{@author}"/>
        </xsl:for-each>
    </authors>
    <xsl:message><foo>Hello world</foo></xsl:message>
</xsl:variable>
<xsl:copy-of select="$allauthors"/>
</xsl:template>
</xsl:stylesheet>
