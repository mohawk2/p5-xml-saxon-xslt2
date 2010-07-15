#!/usr/bin/perl

use lib "lib";
use XML::Saxon::XSLT2;

my $xml = <<'XML';
<?xml version="1.0" encoding="UTF-8"?>
<books>
    <book name="Programming Ruby"
      author="Dave Thomas"/>
    <book name="Code Generation in Action"
      author="Jack Herrington"/>
    <book name="Pragmatic Programmer"
      author="Dave Thomas"/>
</books>
XML

my $xslt = <<'XSLT';
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
</xsl:variable>
<xsl:copy-of select="$allauthors"/>
</xsl:template>
</xsl:stylesheet>
XSLT

my $transformation = XML::Saxon::XSLT2->new($xslt);
$transformation->parameters('bar' => [date=>'2010-02-28']);
print $transformation->transform($xml) . "\n";